package main;

import java.util.HashMap;

import administrator.AdminController;
import administrator.inven.InvenController;
import administrator.inven.edit.ItemEditController;
import administrator.inven.register.ItemRegController;
import administrator.manage.MemberManageController;
import joinForm.JoinFormController;
import user.UserController;
import user.UserInfo;
import user.menu.MenuController;
import user.payment.PaymentController;
import user.product.ProductController;
import user.timecharge.TimeChargeController;

public class Controller {
	private UserController userCon;
	private MenuController menuCon;
	private LoginController loginCon;
	private JoinFormController joinContoller;
	private MainService mainSvc;
	private AdminController adminController;
	private ProductController productController;
	private PaymentController paymentController;
	private TimeChargeController TCController;
	private MemberManageController manageController;
	private InvenController invenController;
	private ItemEditController itemEditController;
	private ItemRegController itemRegController;
	private String id;
	private HashMap<String, UserInfo> userList;
	private boolean[] seatlist;
	
	public Controller() {
		mainSvc = new MainService();
		mainSvc.setController(this);
		userList = new HashMap<String, UserInfo>();
		seatlist = new boolean[25];
		for(boolean bool : seatlist)
			bool = true;
	}
	
	public void setUserController(UserController userCon) {
		this.userCon = userCon;
		this.userCon.setController(this);
	}
	
	public UserController getUserController() {
		return userCon;
	}

	public MenuController getMenuController() {
		return menuCon;
	}
	public void setMenuController(MenuController menuCon) {
		this.menuCon = menuCon;
		menuCon.setController(this);
	}
	
	public void setLoginCon(LoginController loginCon) {
		this.loginCon = loginCon;
		loginCon.setController(this);
	}
	public LoginController getLoginCon() {
		return loginCon;
	}
	public void setJoinContoller(JoinFormController joinContoller) {
		this.joinContoller = joinContoller;
		joinContoller.setController(this);
	}
	public JoinFormController getJoinContoller() {
		return joinContoller;
	}
	public void setAdminController(AdminController adminController) {
		this.adminController = adminController;
		adminController.setController(this);
	}
	public AdminController getAdminController() {
		return adminController;
	}
	public ProductController getProductController() {
		return productController;
	}
	public void setProductController(ProductController productController) {
		this.productController = productController;
		productController.setController(this);
	}
	public PaymentController getPaymentController() {
		return paymentController;
	}
	public void setPaymentController(PaymentController paymentController) {
		this.paymentController = paymentController;
		paymentController.setController(this);
	}
	public TimeChargeController getTCController() {
		return TCController;
	}
	public void setTCController(TimeChargeController tCController) {
		TCController = tCController;
		TCController.setController(this);
	}
	public MemberManageController getManageController() {
		return manageController;
	}
	public void setManageController(MemberManageController manageController) {
		this.manageController = manageController;
		manageController.setController(this);
	}
	public InvenController getInvenController() {
		return invenController;
	}
	public void setInvenController(InvenController invenController) {
		this.invenController = invenController;
		invenController.setController(this);
	}
	public ItemEditController getItemEditController() {
		return itemEditController;
	}
	public void setItemEditController(ItemEditController itemEditController) {
		this.itemEditController = itemEditController;
		itemEditController.setController(this);
	}
	public ItemRegController getItemRegController() {
		return itemRegController;
	}
	public void setItemRegController(ItemRegController itemRegController) {
		this.itemRegController = itemRegController;
		itemRegController.setController(this);
	}
	
	public MainService getMainSvc() {
		return mainSvc;
	}
	public HashMap<String, UserInfo> getUserList() {
		return userList;
	}
	public boolean[] getSeatlist() {
		return seatlist;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//구분값을 받아 창 오픈
	public void open(String division) {
		switch (division) {
		case "user":
			mainSvc.userMainOpen();
			break;
		case "admin":
			mainSvc.adminMainOpen();
			break;
		}
		
	}
}
