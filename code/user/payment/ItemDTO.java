package user.payment;

/*[item]


create table item (
item_code varchar2(20),
menu_category varchar2(20),
menu_name varchar2(20),
price number,
pcs number,
item_pic varchar2(50)
 );*/


public class ItemDTO {
	
	private String item_code;
	private String menu_category;
	private String menu_name;
	private int price;
	private int pcs;
	private String item_pic;
	
	public ItemDTO() {}
	public ItemDTO(String item_code, String menu_category, String menu_name, int price, int pcs, String item_pic) {
		super();
		this.item_code = item_code;
		this.menu_category = menu_category;
		this.menu_name = menu_name;
		this.price = price;
		this.pcs = pcs;
		this.item_pic = item_pic;
	}
	
	
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getMenu_category() {
		return menu_category;
	}
	public void setMenu_category(String menu_category) {
		this.menu_category = menu_category;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPcs() {
		return pcs;
	}
	public void setPcs(int pcs) {
		this.pcs = pcs;
	}
	public String getItem_pic() {
		return item_pic;
	}
	public void setItem_pic(String item_pic) {
		this.item_pic = item_pic;
	}
	
	

}
