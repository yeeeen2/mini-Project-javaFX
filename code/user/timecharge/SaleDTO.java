package user.timecharge;

import java.sql.Date;

/*[sale]


create table sale(
sale_code varchar2(20),
sale_cost number,
sale_pcs number,
sale_time date,
foreign key(sale_code) references time(time_code),
foreign key(sale_code) references item(item_code));*/


public class SaleDTO {

	private String sale_code;
	private int sale_cost;
	private int sale_pcs;
	private Date sale_time;
	
	
	
	public String getSale_code() {
		return sale_code;
	}
	public void setSale_code(String sale_code) {
		this.sale_code = sale_code;
	}
	public int getSale_cost() {
		return sale_cost;
	}
	public void setSale_cost(int sale_cost) {
		this.sale_cost = sale_cost;
	}
	public int getSale_pcs() {
		return sale_pcs;
	}
	public void setSale_pcs(int sale_pcs) {
		this.sale_pcs = sale_pcs;
	}
	public Date getSale_time() {
		return sale_time;
	}
	public void setSale_time(Date sale_time) {
		this.sale_time = sale_time;
	}
	
	
	
	
}
