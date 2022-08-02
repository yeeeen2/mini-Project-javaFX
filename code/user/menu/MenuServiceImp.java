package user.menu;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Controller;
import main.LoginDAO;
import main.LoginDTO;
import user.UserInfo;
import user.product.Product;
import user.product.ProductController;

public class MenuServiceImp implements MenuService {
	private Controller controller;
	private Parent menuForm;
	private ItemDTO item;
	private int sum = 0;
	private StringProperty time;
	private UserInfo info;
	private int remainTime;
	private Label menuTime;
	private Timer timer;
	private LoginDAO loginDao;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}

	public void setMenuForm(Parent menuForm) {
		this.menuForm = menuForm;
	}

	public MenuServiceImp() {
		loginDao = new LoginDAO();
		timer = new Timer();
		time = new SimpleStringProperty();
	}
	
	// 메뉴판창 뒤로가기
	public void backHome() {
		loginDao.updateId(remainTime, info.getId());
		controller.getMainSvc().userMainOpen();
		Parent form = controller.getMenuController().getMenuForm();
		controller.getMainSvc().windowClose(form);
	}

	// 유저정보
	public void userInfo(String id) {
		timer.cancel();
		LoginDTO loginDto = loginDao.SelectId(id);
		info = controller.getUserList().get(id);
		info.setRemain_time(loginDto.getRemain_time());
		remainTime = info.getRemain_time();
		menuTime = (Label)menuForm.lookup("#menuTime");
		// 타이머 내부에 타이머테스크를 넣으면 내부에서 어떤 기능을 할지 돌아감(Thread사용)
		// 타이머테스크는 (구현, delay, period)로 이루어져있음. delay는 타이머가 작동하기까지 대기시간. period 는 반복주기
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {Platform.runLater(() -> setTime());}
		}, 0, 1000);
		// 바인딩 : 데이터를 설정해주는것(?) 정적바인딩 -> 초기설정값, 동적바인딩 -> 상황에 따라 다르게 받아오기
		menuTime.textProperty().bind(timeProperty());
	}

	// 타이머에 들어갈 텍스트프로퍼티 설정하기
	private void setTime() {
		remainTime -= 1;
		int hour = remainTime / (60 * 60);
		int minute = remainTime / 60 % 60;
		int second = remainTime % 60;
		time.set(String.format("%02d:%02d:%02d", hour, minute, second));
	}

	// StringProperty 반환
	private StringProperty timeProperty() {
		return time;
	}

	// 상품종류 넣기
	public void makeGoodsLine(ArrayList<String> itemList) {
		ListView<String> productList = (ListView<String>) menuForm.lookup("#productList");
		if (productList != null) {
			for (String menu : itemList)
				productList.getItems().add(menu);
		}
	}

	// 장바구니 갱신
	public void putTable(ArrayList<Product> list) {
		TableView<Product> menuList = (TableView<Product>) menuForm.lookup("#menuList");
		menuList.getItems().clear();
		TableColumn<Product, Integer> menuListNum = (TableColumn<Product, Integer>) menuList.getColumns().get(0);
		TableColumn<Product, String> menuListMenu = (TableColumn<Product, String>) menuList.getColumns().get(1);
		TableColumn<Product, Integer> menuListCount = (TableColumn<Product, Integer>) menuList.getColumns().get(2);
		TableColumn<Product, Integer> menuListPrice = (TableColumn<Product, Integer>) menuList.getColumns().get(3);
		Label menuTotal = (Label) menuForm.lookup("#menuTotal");
		// 컬럼값 내부에 Product의 어떤값과 대응시킬지 넣기
		menuListNum.setCellValueFactory(new PropertyValueFactory("num"));
		menuListMenu.setCellValueFactory(new PropertyValueFactory("name"));
		menuListCount.setCellValueFactory(new PropertyValueFactory("pcs"));
		menuListPrice.setCellValueFactory(new PropertyValueFactory("amount"));
		// 테이블에 값 넣기
		for (int i = 0; i < list.size(); i++) {
			// 리스트 내부 번호갱신
			list.get(i).setNum(i + 1);
			menuList.getItems().add(list.get(i));
		}
		sum = 0;
		for (Product pd : list) {
			sum += (pd.getPcs() * pd.getAmount());
		}
		menuTotal.setText("" + sum);
	}

	// 상품정보창 오픈
	public void productOpen(ItemDTO item) {
		this.item = item;
		windowOpen("/user/product/MenuInfoConfirm.fxml", "상품정보");
	}

	// 시간충전 창 오픈
	public void tcOpen() {
		windowOpen("/user/timecharge/TimeCharge.fxml", "시간충전");
	}

	// 결제창 오픈
	public void payOpen() {
		windowOpen("/user/payment/PaymentForm.fxml", "결제");
	}

	// 창오픈
	private void windowOpen(String path, String Title) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		Parent form;

		try {
			form = loader.load();
			if (Title.equals("시간충전")) {
				controller.setTCController(loader.getController());
				controller.getTCController().setRoot(form);
			} else if (Title.equals("결제")) {
				controller.setPaymentController(loader.getController());
				controller.getPaymentController().setRoot(form);
				TableView<Product> menuList = (TableView<Product>) menuForm.lookup("#menuList");
				controller.getPaymentController().setMyList(controller.getMenuController().getList());
			} else if (Title.equals("상품정보")) {
				ProductController productController = new ProductController();
				loader.setController(productController);
				controller.setProductController(loader.getController());
				productController.setProductForm(form);
				productController.setItem(item);
				productController.init();
			}
			Stage stage = new Stage();
			stage.setTitle(Title);
			stage.setScene(new Scene(form));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 메뉴취소
	public void menuCancel() {
		if (sum != 0) {
			TableView<Product> menuList = (TableView<Product>) menuForm.lookup("#menuList");
			Label menuTotal = (Label) menuForm.lookup("#menuTotal");
			Product pd = menuList.getSelectionModel().getSelectedItem();
			if(pd == null) return;
			controller.getMenuController().getList().remove(pd);
			int index = menuList.getSelectionModel().getSelectedIndex();
			int minus = menuList.getItems().get(index).getPcs() * menuList.getItems().get(index).getAmount();
			menuList.getItems().remove(index);
			menuTotal.setText("" + (sum - minus));
		}
	}

	// 메뉴비우기
	public void menuClear() {
		if (sum != 0) {
			TableView<Product> menuList = (TableView<Product>) menuForm.lookup("#menuList");
			Label menuTotal = (Label) menuForm.lookup("#menuTotal");
			menuList.getItems().clear();
			menuTotal.setText("0");
			controller.getMenuController().getList().clear();
		}
	}
}
