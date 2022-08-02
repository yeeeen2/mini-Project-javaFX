package main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	private Controller controller;
	private LoginServiceImpl loginSvc;
	private Parent loginForm;
	
	public void setController(Controller controller) {
		this.controller = controller;
		loginSvc.setController(controller);
	}
	public void setLoginForm(Parent loginForm) {
		this.loginForm = loginForm;
	}
	public Parent getLoginForm() {
		return loginForm;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.loginSvc = new LoginServiceImpl();
	}
	
	//#loginProc
	public void loginProc() {
		loginSvc.loginProc(loginForm);
	}
	
	public void regOpenProc() {
		loginSvc.registerOpen();
	}
	
	public void timeChargeOpenProc() {
		loginSvc.timeChargeOpen();
	}
}
