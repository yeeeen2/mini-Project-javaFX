package user.menu;

import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import main.Controller;
import user.product.Product;

public class MenuController {
	private Controller controller;
	private MenuServiceImp menuSvc;
	private ArrayList<Product> list;
	private Parent menuForm;
	private ItemDAO itemDAO;

	public void setController(Controller controller) {
		this.controller = controller;
		menuSvc.setController(controller);
	}

	public Parent getMenuForm() {
		return menuForm;
	}

	public void setMenuForm(Parent menuForm) {
		this.menuForm = menuForm;
		menuSvc.setMenuForm(menuForm);
	}

	public MenuServiceImp getMenuSvc() {
		return menuSvc;
	}

	public void setList(ArrayList<Product> list) {
		this.list = list;
		if (list != null)
			putListProc();
	}

	public ArrayList<Product> getList() {
		return list;
	}

	private Button menuBackButton;
	private Button selectButton;
	private Button menuTcButton;
	private Button menuPayButton;
	private Button menuCancelButton;
	private Button menuClearButton;
	private ListView<String> productList;
	private TableView<Product> menuList;
	private Label menuId;
	private Label menuName;
	private Label menuTime;

	public MenuController() {
		menuSvc = new MenuServiceImp();
		itemDAO = new ItemDAO();
	}
	
	public void setInfo(String id, String name) {
		menuId.setText(id);
		menuName.setText(name);
	}
	
	// init
	public void init() {
		menuBackButton = (Button) menuForm.lookup("#menuBackButton");
		selectButton = (Button) menuForm.lookup("#selectButton");
		menuTcButton = (Button) menuForm.lookup("#menuTcButton");
		menuPayButton = (Button) menuForm.lookup("#menuPayButton");
		menuCancelButton = (Button) menuForm.lookup("#menuCancelButton");
		menuClearButton = (Button) menuForm.lookup("#menuClearButton");
		productList = (ListView<String>) menuForm.lookup("#productList");
		menuList = (TableView<Product>) menuForm.lookup("#menuList");
		menuId = (Label) menuForm.lookup("#menuId");
		menuName = (Label) menuForm.lookup("#menuName");
		menuTime = (Label) menuForm.lookup("#menuTime");
		ImageView image = new ImageView(getClass().getResource("/pic/back_64.png").toExternalForm());
		
		menuBackButton.setGraphic(image);
		menuBackButton.setOnAction(event -> backProc());
		selectButton.setOnAction(event -> productOpenProc());
		menuTcButton.setOnAction(event -> TcOpenProc());
		menuPayButton.setOnAction(event -> payProc());
		menuCancelButton.setOnAction(event -> listCancleProc());
		menuClearButton.setOnAction(event -> listClearProc());
	}

	// 뒤로가기(카테고리창)
	public void backProc() {
		menuSvc.backHome();
	}

	// 상품창 연결
	public void productOpenProc() {
		String name = productList.getSelectionModel().getSelectedItem();
		if(name == null) return;
		ItemDTO item = itemDAO.selectItem(name);
		menuSvc.productOpen(item);
	}

	// 메뉴선택창 연동
	public void productListProc(char n) {
		menuSvc.setMenuForm(menuForm);
		ArrayList<String> itemList = itemDAO.selectItemList(n);
		menuSvc.makeGoodsLine(itemList);
	}

	// 사용자 정보 표시
	public void userInfoProc() {
		menuSvc.userInfo(menuId.getText());
	}

	// 시간충전창open
	public void TcOpenProc() {
		menuSvc.tcOpen();
	}

	// 주문내역list연동
	public void putListProc() {
		menuSvc.putTable(list);
	}

	// 결제버튼 연동
	public void payProc() {
		menuSvc.payOpen();
	}

	// 주문내역list 삭제
	public void listCancleProc() {
		menuSvc.menuCancel();
	}

	// 주문내역list 비우기
	public void listClearProc() {
		menuSvc.menuClear();
	}
}
