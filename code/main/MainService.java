package main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import user.UserController;

public class MainService {
	private Controller controller;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	//알림창
	public void msg(String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림");
		alert.setContentText(content);
		alert.show();
	}
	//창닫기
	public void windowClose(Parent form) {
		Stage stage = (Stage)form.getScene().getWindow();
		stage.close();
	}
	//사용자메인 open
	public void userMainOpen() {
		FXMLLoader userloader = new FXMLLoader(getClass().getResource("/user/UserMainForm.fxml"));
		Parent userMainForm;
		try {
			userMainForm = userloader.load();
			controller.setUserController(userloader.getController());
			UserController userCon = controller.getUserController();
			userCon.setUserMainForm(userMainForm);
			userCon.userInfoProc(controller.getId());
			if(controller.getMenuController() != null) {//메뉴컨트롤러가 있으면 list복사
				if(controller.getMenuController().getList() != null)
					controller.getUserController().setList(controller.getMenuController().getList());
			}
			controller.getAdminController().nowLoginProc();//어드민계정 없을 시 로그인 불가능
			Stage stage = new Stage();
			stage.setScene(new Scene(userMainForm));
			stage.setTitle("Menu");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//관리자메인 open
	public void adminMainOpen() {
		FXMLLoader adminloader = new FXMLLoader(getClass().getResource("/administrator/MasterMainForm.fxml"));
		Parent adminForm;
		
		try {
			adminForm = adminloader.load();
			controller.setAdminController(adminloader.getController());
			controller.getAdminController().setAdminMainForm(adminForm);

			Stage stage = new Stage();
			stage.setTitle("Admin Main");
			stage.setScene(new Scene(adminForm));
			stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
