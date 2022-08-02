package administrator.inven.register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemRegDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public ItemRegDAO() {
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
	public ItemRegDTO SelectName(String name) {
		String sql = "SELECT * FROM item WHERE menu_name=?";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				ItemRegDTO itemRegDTO = new ItemRegDTO();
				
				itemRegDTO.setMenu_name(rs.getString("menu_name"));
				return itemRegDTO;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//DB에 입력한 정보 넣기
	public int insert(ItemRegDTO itemRegDTO) {
		String sql = "INSERT INTO item VALUES(?,?,?,?,?,?)";
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, itemRegDTO.getItem_code());
			ps.setString(2, itemRegDTO.getMenu_category());
			ps.setString(3, itemRegDTO.getMenu_name());
			ps.setInt(4, itemRegDTO.getPrice());
			ps.setInt(5, itemRegDTO.getPcs());
			ps.setString(6, itemRegDTO.getItem_pic());
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
