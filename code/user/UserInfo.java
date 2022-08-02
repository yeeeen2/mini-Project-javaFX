package user;

import java.util.ArrayList;

import user.product.Product;

public class UserInfo {
	private String id;
	private String name;
	private int seat;
	private int remain_time;
	private ArrayList<Product> purchaseList;
	
	public UserInfo(String id, String name, int seat, int remain_time) {
		this.id = id;
		this.name = name;
		this.seat = seat;
		this.remain_time = remain_time;
	}
	
	public String getId() {return id;}
	public int getSeat() {return seat;}
	public int getRemain_time() {return remain_time;}
	public void setRemain_time(int remain_time) {this.remain_time = remain_time;}
	public ArrayList<Product> getPurchaseList() {return purchaseList;}
	public void setPurchaseList(ArrayList<Product> purchaseList) {this.purchaseList = purchaseList;}
	public String getName() {return name;}
}
