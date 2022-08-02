package user.payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import user.product.Product;





public class PaymentDAO {
	
	private Connection con;
	
	public PaymentDAO(){
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
	
	
	public int insertItemSale(Product pd) {
		
		String sql = "INSERT INTO sale (sale_code, sale_cost, sale_pcs) VALUES (?,?,?)";
		PreparedStatement ps;
		int result = 0;
	
				
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pd.getItem_code());
			ps.setInt(2, pd.getAmount());
			ps.setInt(3, pd.getPcs());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	


	public int updateItemCount(SaleDTO saledto) {
		
	
		String sql = "UPDATE item SET PCS = (SELECT pcs FROM item WHERE item_code = ?) -? WHERE item_code=?";
		
		PreparedStatement ps;
		int result = 0;
				
		
		try {
			
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, saledto.getSale_code());
			ps.setInt(2, saledto.getSale_pcs());
			ps.setString(3, saledto.getSale_code());
			

			result = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}



	
}
	



