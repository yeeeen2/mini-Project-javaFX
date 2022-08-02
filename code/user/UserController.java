package user;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import main.Controller;
import user.product.Product;

public class UserController implements Initializable{
	private Parent userMainForm;
	private Controller controller;
	private UserServiceImp userSvc;
	private ArrayList<Product> list;
	@FXML private Label UMTime;
	
	public void setUserMainForm(Parent userMainForm) {
		this.userMainForm = userMainForm;
		userSvc.setUserMainForm(userMainForm);
	}
	public Parent getUserMainForm() {
		return userMainForm;
	}
	public void setController(Controller controller) {
		this.controller = controller;
		userSvc.setController(controller);
	}
	public UserServiceImp getUserSvc() {
		return userSvc;
	}
	public void setList(ArrayList<Product> list) {
		this.list = list;
		if(list !=  null)
			putListProc();
	}
	public ArrayList<Product> getList() {
		return list;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userSvc = new UserServiceImp();
		list = new ArrayList<Product>();
	}
	
	public void menuOpen1Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("식사");
	}
	public void menuOpen2Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("라면");
	}
	public void menuOpen3Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("분식");
	}
	public void menuOpen4Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("간식");
	}
	public void menuOpen5Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("과자");
	}
	public void menuOpen6Proc() {
		userSvc.setController(controller);
		userSvc.menuWindowOpen("음료");
	}
	//사용자 정보 표시
	public void userInfoProc(String id) {
		userSvc.userInfo(id);
	}
	//로그아웃
	public void logoutProc() {
		userSvc.logout();
	}
	//시간충전창open
	public void TcOpenProc() {
		userSvc.tcOpen();
	}
	//주문내역list연동
	public void putListProc() {
		userSvc.putTable(list);
	}
	//결제버튼 연동
	public void payProc() {
		userSvc.payOpen();
	}
	//주문내역list 삭제
	public void listCancleProc() {
		userSvc.menuCancel();
	}
	//주문내역list 비우기
	public void listClearProc() {
		userSvc.menuClear();
	}
}
