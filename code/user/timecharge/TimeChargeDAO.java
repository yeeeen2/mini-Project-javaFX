package user.timecharge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeChargeDAO {
	
	private Connection con;
	
	public TimeChargeDAO(){
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
	
	public ArrayList<String> SelectId(String id) {
		String sql = "select id from member_info where id like ?";
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<String> idList = new ArrayList<String>();

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + id + "%");
			rs = ps.executeQuery();
			while(rs.next()) {
				idList.add(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idList;
	}
	
	public int selectTime(String id) {
		String sql = "select remain_time from member_info where id=?";
		PreparedStatement ps;
		ResultSet rs;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("remain_time");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int insertTimeSale(int selectPrice) {
		
		String sql = "INSERT INTO sale (sale_code, sale_cost, sale_pcs) VALUES (?,?,?)";
		PreparedStatement ps;
		int result = 0;
		
		String code;
		if(selectPrice == 1000) {
			code = "t001";
		}else if(selectPrice == 2000) {
			code = "t002";
		}else if(selectPrice == 3000) {
			code = "t003";
		}else if(selectPrice == 5000) {
			code = "t005";
		}else if(selectPrice == 10000) {
			code = "t012";
		}else {
			code = "t024";	
		}
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, code);
			ps.setInt(2, selectPrice);
			ps.setInt(3, 1);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	


	public int updateTime(String selectId, String selectTime) {

		String sql = "UPDATE member_info SET remain_time=? WHERE id=?";
		
		PreparedStatement ps;
		int result = 0;
		
		int time = selectTime(selectId);
		
		if(selectTime == "1시간") {
			time += 3600;
		}else if(selectTime == "2시간") {
			time += 7200; 
		}else if(selectTime == "3시간") {
			time += 10800; 
		}else if(selectTime == "5시간") {
			time += 18000; 
		}else if(selectTime == "12시간") {
			time += 43200; 
		}else {
			time += 86400;
		}
		
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setString(2, selectId);

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	
	
}
	



