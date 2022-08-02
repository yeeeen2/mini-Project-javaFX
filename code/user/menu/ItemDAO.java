package user.menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAO {
	private ArrayList<ItemDTO> itemList;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public ItemDAO() {
		itemList = new ArrayList<ItemDTO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","oracle","oracle");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ItemDTO selectItem(String name) {
		ItemDTO itemDTO = null;
		String sql = "select * from item where menu_name=?";
		try{
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next()) {
				itemDTO = new ItemDTO(rs.getString("item_code"), rs.getString("menu_category"), rs.getString("menu_name"), rs.getInt("price"), rs.getInt("pcs"), rs.getString("item_pic"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return itemDTO;
	}
	
	public ArrayList<String> selectItemList(char n) {
		ArrayList<String> itemNameList = new ArrayList<String>();
		String sql = "select menu_name from item where item_code like ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, "i" + n + "%");
			rs = ps.executeQuery();
			while(rs.next()) {
				itemNameList.add(rs.getString("menu_name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return itemNameList;
	}
	
}
