package administrator.manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberManageDAO {
	
	private Connection con;
	
	public MemberManageDAO() {
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
	
	public ArrayList<MemberManageDTO> SelectAll() {
		ArrayList<MemberManageDTO> ALMMD = new ArrayList<>();
		String sql = "SELECT * FROM member_info";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				MemberManageDTO memberManageDto = new MemberManageDTO();
				memberManageDto.setId(rs.getString("id"));
				memberManageDto.setPw(rs.getString("pw"));
				memberManageDto.setName(rs.getString("name"));
				memberManageDto.setEmail(rs.getString("email"));
				memberManageDto.setTel(rs.getString("tel"));
				memberManageDto.setRemain_time(rs.getString("remain_time"));
				memberManageDto.setRegister_time(rs.getString("register_time"));
				ALMMD.add(memberManageDto);
				}
			} catch (Exception e) {
				e.printStackTrace();
				}
		return ALMMD;
	}
	
	public MemberManageDTO Select(String id) {
		String sql = "SELECT * FROM member_info WHERE id = ?";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				MemberManageDTO memberManageDto = new MemberManageDTO();
				memberManageDto.setId(rs.getString("id"));
				memberManageDto.setPw(rs.getString("pw"));
				memberManageDto.setName(rs.getString("name"));
				memberManageDto.setEmail(rs.getString("email"));
				memberManageDto.setTel(rs.getString("tel"));
				memberManageDto.setRemain_time(rs.getString("remain_time"));
				memberManageDto.setRegister_time(rs.getString("register_time"));
				return memberManageDto;
				}
			} catch (Exception e) {
				e.printStackTrace();
				}
		return null;
	}
	
	public int Update(MemberManageDTO memberManageDto) {
		String sql = "UPDATE member_info SET pw = ?, email = ?, tel = ? WHERE id = ?";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, memberManageDto.getPw());
			ps.setString(2, memberManageDto.getEmail());
			ps.setString(3, memberManageDto.getTel());
			ps.setString(4, memberManageDto.getId());
			result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				}
		return result;
	}
	 
	public int Delete(MemberManageDTO memberManageDto) {
		String sql = "DELETE FROM member_info WHERE id = ?";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, memberManageDto.getId());	
			result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				}
		return result;
	}

}
