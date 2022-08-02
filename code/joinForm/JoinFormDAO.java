package joinForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JoinFormDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public JoinFormDAO() {
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
	
	public JoinFormDTO SelectId(String id) {
		String sql = "SELECT * FROM member_info WHERE id=?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				JoinFormDTO joinFormDTO = new JoinFormDTO();
				joinFormDTO.setId(rs.getString("id"));
				return joinFormDTO;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public int insert(JoinFormDTO joinDTO) {
		String sql = "INSERT INTO member_info (id, pw, name, email, tel) VALUES(?,?,?,?,?)";
		int result=0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, joinDTO.getId());
			ps.setString(2, joinDTO.getPw());
			ps.setString(3, joinDTO.getName());
			ps.setString(4, joinDTO.getEmail());
			ps.setString(5, joinDTO.getTel());
			ps.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
