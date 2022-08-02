package administrator.inven;

public class InventoryDTO {
		private String item_code;//기본키코드
		private String menu_category;//제품 카테고리
		private String menu_name;//제품이름
		private int price;//제품가격
		private int pcs;//제품수량
		private String item_pic;//사진
		
		public InventoryDTO(String item_code, String menu_category, String menu_name, int price, int pcs,
				String item_pic) {
			this.item_code = item_code;
			this.menu_category = menu_category;
			this.menu_name = menu_name;
			this.price = price;
			this.pcs = pcs;
			this.item_pic = item_pic;
		}
		
		public InventoryDTO() {
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

