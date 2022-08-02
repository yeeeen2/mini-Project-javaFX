package administrator.manage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import main.Controller;

public class MemberManageController implements Initializable {
	@FXML TextField memberSearchId;
	private Parent MemberManage;
	private MemberManageServiceImpl memberManageSvc;
	private Controller controller;
	
	public void setMemberManage(Parent MemberManage) {
		this.MemberManage = MemberManage;
	}
	public void setController(Controller controller) {
		this.controller = controller;
		memberManageSvc.setController(controller);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.memberManageSvc = new MemberManageServiceImpl();
	}
	
	//전체회원검색
	public void memberSelectAllProc() {
		MemberManageDTO memberManageDto = memberManageSvc.memberSelectAllProc(MemberManage);
	}
	
	//회원검색
	public void memberSelectProc() {
		MemberManageDTO memberManageDto = memberManageSvc.memberSelectProc(MemberManage);	
	}
	
	//수정
	public void memberUpdateProc() {
		int memberManageDto = memberManageSvc.memberUpdateProc(MemberManage);
	}
	
	//삭제
	public void memberDeleteProc() {
		int memberManageDto = memberManageSvc.memberDeleteProc(MemberManage);
	}

}
