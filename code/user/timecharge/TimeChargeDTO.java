package user.timecharge;

/*create table time (
time_code varchar2(20),
charge_time number,
time_price number,
 );


ALTER TABLE time ADD PRIMARY KEY (time_code);*/


public class TimeChargeDTO {
	
	private String time_code;
	private int charge_time;
	private int time_price;
	
	
	public String getTime_code() {
		return time_code;
	}
	public void setTime_code(String time_code) {
		this.time_code = time_code;
	}
	public int getCharge_time() {
		return charge_time;
	}
	public void setCharge_time(int charge_time) {
		this.charge_time = charge_time;
	}
	public int getTime_price() {
		return time_price;
	}
	public void setTime_price(int time_price) {
		this.time_price = time_price;
	}
	
	
	
}
