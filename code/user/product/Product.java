package user.product;

public class Product {
	private int num;
	private String item_code;
	private String name;
	private int pcs;
	private int amount;//단가금액
	
	public Product() {}
	
	public Product(String item_code, String name, int pcs, int amount) {
		super();
		this.item_code = item_code;
		this.name = name;
		this.pcs = pcs;
		this.amount = amount;
	}
	
	public int getNum() {return num;}
	public void setNum(int num) {this.num = num;}
	public String getItem_code() {return item_code;}
	public void setItem_code(String item_code) {this.item_code = item_code;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getPcs() {return pcs;}
	public void setPcs(int pcs) {this.pcs = pcs;}
	public int getAmount() {return amount;}
	public void setAmount(int amount) {this.amount = amount;}
	
}
