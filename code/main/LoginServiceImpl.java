package main;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.UserInfo;

public class LoginServiceImpl implements LoginService {
	private Controller controller;
	private LoginDAO loginDao = new LoginDAO();
	public void setController(Controller controller) {
		this.controller = controller;
	}
	//로그인
	public void loginProc(Parent loginForm) {
		TextField loginId = (TextField) loginForm.lookup("#loginId");
		PasswordField loginPw = (PasswordField) loginForm.lookup("#loginPw");
		
		//검증
		LoginDTO loginDto = loginDao.SelectId(loginId.getText());
		if(loginDto != null && loginDto.getId().equals(loginId.getText()) && loginDto.getPw().equals(loginPw.getText())) {
			if(loginDto.getRemain_time() <= 0) {
				controller.getMainSvc().msg("이용 시간을 충전해주세요.");
			}else {
				//controller.getMainSvc().windowClose(loginForm);
				if(loginDto.getId().equals("admin")) {
					controller.getMainSvc().adminMainOpen();
				}else {
					int seat = randomSeat();
					if (seat != 0) {
						UserInfo user = new UserInfo(loginDto.getId(), loginDto.getName(), seat, loginDto.getRemain_time());
						controller.setId(loginDto.getId());
						controller.getUserList().put(loginDto.getId(), user);
						controller.getMainSvc().userMainOpen();
					}else {
						controller.getMainSvc().msg("자리가 부족합니다.");
					}
				}
			}
		}else {
			controller.getMainSvc().msg("아이디 혹은 비밀번호가 틀립니다.");
		}
	}
	//좌석지정
	private int randomSeat() {
		boolean[] list = controller.getSeatlist();
		int seat;
		Random rd = new Random();
		for(int i = 0; i < list.length; i++) {
			seat = rd.nextInt(25);
			if(list[seat] == false) {
				list[seat] = true;
				return ++seat;
			}
		}
		return 0;
	}
	//회원가입창 오픈
	public void registerOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/joinForm/JoinForm.fxml"));
		Parent register;
		try {
			register = loader.load();
			
			controller.setJoinContoller(loader.getController());
			controller.getJoinContoller().setJoinForm(register);
			
			Stage stage = new Stage();
			stage.setScene(new Scene(register));
			stage.setTitle("Register");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//시간충전
	public void timeChargeOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/timecharge/TimeCharge.fxml"));
		Parent timeCharge;
		try {
			timeCharge = loader.load();
			
			controller.setTCController(loader.getController());
			controller.getTCController().setRoot(timeCharge);
			
			Stage stage = new Stage();
			stage.setScene(new Scene(timeCharge));
			stage.setTitle("TimeCharge");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
