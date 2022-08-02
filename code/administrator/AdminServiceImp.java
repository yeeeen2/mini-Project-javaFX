package administrator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Controller;
import user.UserInfo;
import user.product.Product;

/*
 * insert into member_info values('user1','user123','홍길동','user111@pc.com','010-1111-1111',3600000,'2022-02-13');
 * 식사카테고리 4개메뉴 등록(i1003, i1004 재고없는상태)
 */

public class AdminServiceImp implements AdminService{
	private Controller controller;
	private Parent adminMainForm;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public void setAdminMainForm(Parent adminMainForm) {
		this.adminMainForm = adminMainForm;
	}
	//로그인현황
	public void nowLogin() {
		if(controller.getUserList().size() != 0) {
			for(UserInfo user : controller.getUserList().values()) {
				String seat = String.format("#MMSeat%02d", user.getSeat());
				BorderPane seatPane = (BorderPane)adminMainForm.lookup(seat);
				seatPane.setStyle("-fx-background-color:gold");
			}
		}
	}
	//로그아웃
	public void nowLogout(String id) {
		String seat = String.format("#MMSeat%02d", controller.getUserList().get(id).getSeat());
		BorderPane seatPane = (BorderPane)adminMainForm.lookup(seat);
		seatPane.setStyle("-fx-background-color: #6C4874");
		controller.getUserList().remove(id);
	}
	
	public void orderCheck(UserInfo userOrder) {
		String menu = "";
		for(Product product : userOrder.getPurchaseList()) {
			menu += product.getName() + product.getPcs() + " ";
		}
		String order = userOrder.getSeat() + "번 좌석 "+ menu;
		ListView<String> list = (ListView<String>)adminMainForm.lookup("#MMOrderList");
		if(list != null) {
			list.getItems().add(order);
		}
	}

	public void orderDelete() {
		ListView<String> list = (ListView<String>)adminMainForm.lookup("#MMOrderList");
		list.getItems().remove(list.getSelectionModel().getSelectedItem());
	}

	public void invenOpen() {
		windowOpen("/administrator/inven/MasterInven.fxml", "재고관리");
	}

	public void memberOpen() {
		windowOpen("/administrator/manage/MemberManage.fxml", "회원관리");
	}
	//창오픈
	private void windowOpen(String path, String Title) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		Parent form;

		try {
			form = loader.load();
			if(Title.equals("재고관리")) {
				controller.setInvenController(loader.getController());
				controller.getInvenController().setInvenForm(form);
			}else if(Title.equals("회원관리")) {
				controller.setManageController(loader.getController());
				controller.getManageController().setMemberManage(form);
			}
			Stage stage = new Stage();
			stage.setTitle(Title);
			stage.setScene(new Scene(form));
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
