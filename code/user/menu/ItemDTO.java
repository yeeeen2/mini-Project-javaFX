package user.menu;

public class ItemDTO {
	private String itemCode;
	private String menuCategory;
	private String menuName;
	private int price;
	private int pcs;
	private String itemPic;
	
	public ItemDTO() {}
	public ItemDTO(String itemCode, String menuCategory, String menuName, int price, int pcs, String itemPic) {
		super();
		this.itemCode = itemCode;
		this.menuCategory = menuCategory;
		this.menuName = menuName;
		this.price = price;
		this.pcs = pcs;
		this.itemPic = itemPic;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getMenuCategory() {
		return menuCategory;
	}
	public void setMenuCategory(String menuCategory) {
		this.menuCategory = menuCategory;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
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
	public String getItemPic() {
		return itemPic;
	}
	public void setItemPic(String itemPic) {
		this.itemPic = itemPic;
	}
	
	
}
