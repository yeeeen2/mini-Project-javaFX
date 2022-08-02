package user;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Controller;
import main.LoginDAO;
import main.LoginDTO;
import user.menu.MenuController;
import user.product.Product;

public class UserServiceImp implements UserService {
	private Controller controller;
	private Parent userMainForm;
	private int sum = 0;
	private StringProperty time;
	private UserInfo info;
	private int remainTime;
	private Label UMId;
	private Label UMName;
	private Label UMTime;
	private Timer timer;
	private LoginDAO loginDao;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setUserMainForm(Parent userMainForm) {
		this.userMainForm = userMainForm;
		UMId = (Label) userMainForm.lookup("#UMId");
		UMName = (Label) userMainForm.lookup("#UMName");
		UMTime = (Label) userMainForm.lookup("#UMTime");
	}
	public UserServiceImp() {
		loginDao = new LoginDAO();
		timer = new Timer();
		time = new SimpleStringProperty();
	}
	
	// 메뉴별 창연결
	public void menuWindowOpen(String menu) {
		controller.getMainSvc().windowClose(controller.getUserController().getUserMainForm());
		switch (menu) {
		case "식사":
			windowOpen("/user/menu/UserMenuForm1.fxml", "메뉴선택");
			break;
		case "라면":
			windowOpen("/user/menu/UserMenuForm2.fxml", "메뉴선택");
			break;
		case "분식":
			windowOpen("/user/menu/UserMenuForm3.fxml", "메뉴선택");
			break;
		case "간식":
			windowOpen("/user/menu/UserMenuForm4.fxml", "메뉴선택");
			break;
		case "과자":
			windowOpen("/user/menu/UserMenuForm5.fxml", "메뉴선택");
			break;
		case "음료":
			windowOpen("/user/menu/UserMenuForm6.fxml", "메뉴선택");
			break;
		}

	}

	// 창오픈
	private void windowOpen(String path, String Title) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		Parent form;
		try {
			form = loader.load();
			if (Title.equals("메뉴선택")) {
				MenuController menuController = new MenuController();
				loader.setController(menuController);
				controller.setMenuController(loader.getController());
				menuController.setMenuForm(form);
				menuController.setList(controller.getUserController().getList());
				menuController.init();
				menuController.setInfo(UMId.getText(), UMName.getText());
				loginDao.updateId(remainTime, info.getId());
				menuController.userInfoProc();
				char category = path.charAt(23);
				menuController.productListProc(category);// 카테고리넣기
			} else if (Title.equals("시간충전")) {
				controller.setTCController(loader.getController());
				controller.getTCController().setRoot(form);
			} else if (Title.equals("결제")) {
				controller.setPaymentController(loader.getController());
				controller.getPaymentController().setRoot(form);
				controller.getPaymentController().setMyList(controller.getUserController().getList());
			}
			Stage stage = new Stage();
			stage.setTitle(Title);
			stage.setScene(new Scene(form));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 유저정보
	public void userInfo(String id) {
		timer.cancel();
		LoginDTO loginDto = loginDao.SelectId(id);
		info = controller.getUserList().get(id);
		info.setRemain_time(loginDto.getRemain_time());
		remainTime = info.getRemain_time();
		UMId.setText(info.getId());
		UMName.setText(info.getName());
		// 타이머 내부에 타이머테스크를 넣으면 내부에서 어떤 기능을 할지 돌아감(Thread사용)
		// 타이머테스크는 (구현, delay, period)로 이루어져있음. delay는 타이머가 작동하기까지 대기시간. period 는 반복주기
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(remainTime <= 0) logout();
				Platform.runLater(() -> setTime());
				}
		}, 0, 1000);
		// 바인딩 : 데이터를 설정해주는것(?) 정적바인딩 -> 초기설정값, 동적바인딩 -> 상황에 따라 다르게 받아오기
		UMTime.textProperty().bind(timeProperty());
	}
	
	// 타이머에 들어갈 텍스트프로퍼티 설정하기
	private void setTime() {
		remainTime -= 1;
		int hour = remainTime / (60 * 60);
		int minute = remainTime / 60 % 60;
		int second = remainTime % 60;
		time.set(String.format("%02d:%02d:%02d", hour, minute, second));
	}
	//StringProperty 반환
	private StringProperty timeProperty() { return time; }
	
	// 로그아웃
	public void logout() {
		// 남은시간 DTO접근으로 반환 -> 창종료 및 어드민 좌석 갱신
		LoginDAO loginDao = new LoginDAO();
		if(loginDao.updateId(remainTime, info.getId()) != 0) {
			controller.getMainSvc().msg("로그아웃 되었습니다.");
			controller.getAdminController().nowLogoutProc(info.getId());
			controller.getUserList().remove(info.getId());
			if(controller.getUserController().getList() != null) {
				controller.getUserController().setList(null);
				if(controller.getMenuController() != null)
					controller.getMenuController().setList(null);
			}
			controller.getMainSvc().windowClose(userMainForm);
		}else {
			System.out.println("로그아웃 DB 오류");
		}
		
	}

	// 시간충전 창 오픈
	public void tcOpen() {
		windowOpen("/user/timecharge/TimeCharge.fxml", "시간충전");
	}

	// 결제창 오픈
	public void payOpen() {
		windowOpen("/user/payment/PaymentForm.fxml", "결제");
	}

	// 장바구니 갱신
	public void putTable(ArrayList<Product> list) {
		TableView<Product> menuList = (TableView<Product>) userMainForm.lookup("#UMList");
		TableColumn<Product, Integer> menuListNum = (TableColumn<Product, Integer>) menuList.getColumns().get(0);
		TableColumn<Product, String> menuListMenu = (TableColumn<Product, String>) menuList.getColumns().get(1);
		TableColumn<Product, Integer> menuListCount = (TableColumn<Product, Integer>) menuList.getColumns().get(2);
		TableColumn<Product, Integer> menuListPrice = (TableColumn<Product, Integer>) menuList.getColumns().get(3);
		Label menuTotal = (Label) userMainForm.lookup("#UMTotal");
		// 컬럼값 내부에 Product의 어떤값과 대응시킬지 넣기
		menuListNum.setCellValueFactory(new PropertyValueFactory("num"));
		menuListMenu.setCellValueFactory(new PropertyValueFactory("name"));
		menuListCount.setCellValueFactory(new PropertyValueFactory("pcs"));
		menuListPrice.setCellValueFactory(new PropertyValueFactory("amount"));
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setNum(i + 1);
			menuList.getItems().add(list.get(i));
		}
		sum = 0;
		for (Product pd : list) {
			sum += (pd.getPcs() * pd.getAmount());
		}
		menuTotal.setText("" + sum);
	}

	// 메뉴취소
	public void menuCancel() {
		if (sum != 0) {
			TableView<Product> menuList = (TableView<Product>) userMainForm.lookup("#UMList");
			Label menuTotal = (Label) userMainForm.lookup("#UMTotal");
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
			TableView<Product> menuList = (TableView<Product>) userMainForm.lookup("#UMList");
			Label menuTotal = (Label) userMainForm.lookup("#UMTotal");
			menuList.getItems().clear();
			menuTotal.setText("0");
			controller.getMenuController().getList().clear();
		}
	}
}
