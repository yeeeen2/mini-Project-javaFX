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

	// ????????????(???????????????)
	public void backProc() {
		menuSvc.backHome();
	}

	// ????????? ??????
	public void productOpenProc() {
		String name = productList.getSelectionModel().getSelectedItem();
		if(name == null) return;
		ItemDTO item = itemDAO.selectItem(name);
		menuSvc.productOpen(item);
	}

	// ??????????????? ??????
	public void productListProc(char n) {
		menuSvc.setMenuForm(menuForm);
		ArrayList<String> itemList = itemDAO.selectItemList(n);
		menuSvc.makeGoodsLine(itemList);
	}

	// ????????? ?????? ??????
	public void userInfoProc() {
		menuSvc.userInfo(menuId.getText());
	}

	// ???????????????open
	public void TcOpenProc() {
		menuSvc.tcOpen();
	}

	// ????????????list??????
	public void putListProc() {
		menuSvc.putTable(list);
	}

	// ???????????? ??????
	public void payProc() {
		menuSvc.payOpen();
	}

	// ????????????list ??????
	public void listCancleProc() {
		menuSvc.menuCancel();
	}

	// ????????????list ?????????
	public void listClearProc() {
		menuSvc.menuClear();
	}
}
