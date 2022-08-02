package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
	
	private Connection con;
	
	public LoginDAO() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginDTO SelectId(String id) {
		String sql = "SELECT * FROM member_info WHERE id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				LoginDTO loginDto = new LoginDTO();
				loginDto.setId(rs.getString("id"));
				loginDto.setPw(rs.getString("pw"));
				loginDto.setName(rs.getString("name"));
				loginDto.setEmail(rs.getString("email"));
				loginDto.setTel(rs.getString("tel"));
				loginDto.setRemain_time(rs.getInt("remain_time"));
				loginDto.setRegister_time(rs.getDate("register_time"));
				return loginDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//로그아웃 구현
	public int updateId(int time, String id) {
		String sql = "update member_info set remain_time=? where id=?";
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setString(2, id);
			result = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null) ps.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
