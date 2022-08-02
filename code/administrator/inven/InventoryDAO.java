package administrator.inven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.ListView;

public class InventoryDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	public InventoryDAO() {
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

	// 상품삭제
	public int delete(String name) {
		String sql = "delete from item WHERE menu_name=?";
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 모든 정보 불러오기
	public ArrayList<String> selectAll() {
		String sql = "SELECT menu_name FROM item";
		ArrayList<String> list = new ArrayList<String>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("menu_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 선택된 정보 불러오기
	public InventoryDTO selectItem(String menu_name) {
		String sql = "SELECT * FROM item where menu_name=?";
		InventoryDTO inventoryDto = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, menu_name);
			rs = ps.executeQuery();
			if (rs.next()) {
				inventoryDto = new InventoryDTO(rs.getString("item_code"), rs.getString("menu_category"),
						rs.getString("menu_name"), rs.getInt("price"), rs.getInt("pcs"), rs.getString("item_pic"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return inventoryDto;

	}
}
