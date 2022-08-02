package administrator.inven.edit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ItemEditDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public ItemEditDAO() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//이름으로 찾기
	public ItemEditDTO SelectName(String name) {
		String sql = "SELECT * FROM item WHERE menu_name=?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				ItemEditDTO itemEditDTO = new ItemEditDTO();
				
				itemEditDTO.setMenu_name(rs.getString("menu_name"));
				return itemEditDTO;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//DB에 입력한 수정하기							
	public int update(ItemEditDTO itemEditDTO) {
		String sql = "UPDATE item SET item_code=?, menu_category=?, menu_name=?, price=?, pcs=? WHERE menu_name=?";
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, itemEditDTO.getItem_code());
			ps.setString(2, itemEditDTO.getMenu_category());
			ps.setString(3, itemEditDTO.getMenu_name());
			ps.setInt(4, itemEditDTO.getPrice());
			ps.setInt(5, itemEditDTO.getPcs());
			ps.setString(6, itemEditDTO.getMenu_name());
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	public ArrayList<String> codeInput(int n) {
		String sql = "SELECT item_code FROM item WHERE item_code LIKE ?";
		ArrayList<String> codeNumlist = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, "i" + n + "%");
			rs = ps.executeQuery();
			codeNumlist = setList(rs);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return codeNumlist;
	}
	
	
	private ArrayList<String> setList(ResultSet rs) {
        ArrayList<String> itemCodeList = new ArrayList<String>();
        try {
            while(rs.next()) {
                String itemcode = rs.getString("item_code");
                itemCodeList.add(itemcode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemCodeList;
    }
	
}
