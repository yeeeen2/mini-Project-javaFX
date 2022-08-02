package joinForm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Controller;

public class JoinFormController implements Initializable {
	private Controller controller;
	private Parent joinForm;
	private JoinFormService joinformService;
	@FXML TextField regId;
	@FXML Button regIdCheckButton;
	@FXML Button regRegButton;

	public void setController(Controller controller) {
		this.controller = controller;
		joinformService.setController(controller);
	}

	public void setJoinForm(Parent joinForm) {
		this.joinForm = joinForm;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.joinformService = new JoinFormService();
		
		regRegButton.setDisable(true);
		
		
		regIdCheckButton.setOnAction(event->{
			if(regId.getText().length()>2) {
				idCheck();
				if(idCheck()==1) {
					regRegButton.setDisable(false);
				}else {
					regRegButton.setDisable(true);
				}
			}else {
				controller.getMainSvc().msg("ID는 세글자 이상이어야 합니다.");
				regRegButton.setDisable(true);
			}
		});
	}
	
	
	//회원가입(fx_id : regRegButton)
	public void register(){
		joinformService.insert(joinForm);
	}
	
	//취소버튼 클릭시 동작(fx_id : regCancelButton)
	public void JoinCancel() {
		controller.getMainSvc().windowClose(joinForm);
	}
	
	public int idCheck() {
		return joinformService.idCheck(joinForm);
	}

}