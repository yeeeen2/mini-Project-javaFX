package joinForm;

import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Controller;

public class JoinFormService {
	private Controller controller;
	private JoinFormDAO joinFormDAO;
	private JoinFormDTO joinFormDTO;
	@FXML Button regIdCheckButton;
	@FXML Button regRegButton;
	
	
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public JoinFormService() {
		joinFormDAO = new JoinFormDAO();
	}
	public void setDto(JoinFormDTO joinFormDTO) {
		this.joinFormDTO = joinFormDTO;
	}
	
	public void insert(Parent joinForm) {
		//TextField 
		TextField regName = (TextField)joinForm.lookup("#regName");
		TextField regId = (TextField)joinForm.lookup("#regId");
		PasswordField regPw = (PasswordField)joinForm.lookup("#regPw");
		PasswordField regConfirmPw = (PasswordField)joinForm.lookup("#regConfirmPw");
		TextField regEmail = (TextField)joinForm.lookup("#regEmail");
		TextField regTel = (TextField)joinForm.lookup("#regTel");
		
		String name = regName.getText();
		
		String id = regId.getText();
		String pw = regPw.getText();
		String ckPw = regConfirmPw.getText();
		
		//Email 정규표현식
		String pattern = "^[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		String email = regEmail.getText();
		boolean email_check = Pattern.matches(pattern, email);
		
		//tel 정규표현식
		String telPatturn = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}$";
		String tel = regTel.getText();
		boolean tel_check = Pattern.matches(telPatturn, tel);
			
		//pw, ckPw 같은지확인
		if(pw.equals(ckPw)) {
			JoinFormDTO joinDTO = joinFormDAO.SelectId(id);
			if(joinDTO==null) {
				if(id.length()>2) {	//		<<id 3글자 이상은 쓰게
					if(email_check) {//		<<email 정규식으로 형식맞추기
						if(tel_check) {//	<<tel 정규식으로 형식 맞추기
							joinDTO = new JoinFormDTO();
							joinDTO.setId(id);
							joinDTO.setPw(pw);
							joinDTO.setName(name);
							joinDTO.setEmail(email);
							joinDTO.setTel(tel);
							joinFormDAO.insert(joinDTO);
							controller.getMainSvc().msg("계정이 등록 완료되었습니다.");
							controller.getMainSvc().windowClose(joinForm);
						}else {
							controller.getMainSvc().msg("전화번호 형식에 맞지 않습니다 -를 추가해서 넣어주세요. ex)010-****-****");
						}
					}else {
						controller.getMainSvc().msg("Email이 형식에 맞지 않습니다. 다시 입력해주세요.");
					}
				}else {
					controller.getMainSvc().msg("id는 세글자 이상이어야 합니다.");
				}
			}else {
				controller.getMainSvc().msg("이미 등록된 아이디입니다.");
			}
		}else {
			controller.getMainSvc().msg("입력한 두 패스워드가 일치하지 않습니다.");
		}
		
		
		
	}
	
	
	public int idCheck(Parent joinForm) {
		TextField regId = (TextField)joinForm.lookup("#regId");
		String id = regId.getText();
		int result=0;
		
		joinFormDTO = joinFormDAO.SelectId(id);
		
		if(id.length()>2) {
			if(joinFormDTO!=null && id.equals(joinFormDTO.getId())) {
				controller.getMainSvc().msg("아이디가 중복되어 사용하실 수 없습니다. 다른 id를 입력해주세요.");
			}else {
				controller.getMainSvc().msg("아이디가 사용가능!");
				return result=1;
			}
		}else {
			controller.getMainSvc().msg("id는 세글자 이상이어야 합니다.");
		}
		return result;
		
	}

}
