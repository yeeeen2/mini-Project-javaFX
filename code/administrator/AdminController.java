package administrator;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import main.Controller;
import user.UserInfo;

public class AdminController implements Initializable{
	private AdminServiceImp adminSvc;
	private Controller controller;
	private Parent adminMainForm;
	
	public Parent getAdminMainForm() {
		return adminMainForm;
	}
	public void setAdminMainForm(Parent adminMainForm) {
		this.adminMainForm = adminMainForm;
		adminSvc.setAdminMainForm(adminMainForm);
	}
	public void setController(Controller controller) {
		this.controller = controller;
		adminSvc.setController(controller);
	}
	public Controller getController() {
		return controller;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		adminSvc = new AdminServiceImp();
	}
	//로그인 map값으로 받아 연동
	public void nowLoginProc(){
		adminSvc.nowLogin();
	}
	//로그아웃
	public void nowLogoutProc(String id) {
		adminSvc.nowLogout(id);
	}
	//주문현황체크(리스트작성)
	public void orderListProc(UserInfo orderlist) {
		adminSvc.orderCheck(orderlist);
	}
	//주문삭제
	public void orderDeleteProc() {
		adminSvc.orderDelete();
	}
	//재고관리창 연결
	public void invenOpenProc() {
		adminSvc.invenOpen();
	}
	//회원관리창 연결
	public void memberOpenProc() {
		adminSvc.memberOpen();
	}
	
}
