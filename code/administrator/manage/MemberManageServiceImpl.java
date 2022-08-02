package administrator.manage;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Controller;

public class MemberManageServiceImpl implements MemberManageService {
	private Controller controller;
	private MemberManageDAO memberManageDao;
	private MemberManageDTO memberManageDto;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public MemberManageServiceImpl() {
		this.memberManageDao = new MemberManageDAO();
	}

	public MemberManageDTO memberSelectAllProc(Parent root) {
		ListView<String> memberRegDate = (ListView<String>) root.lookup("#memberRegDate");
		ListView<String> memberName = (ListView<String>) root.lookup("#memberName");
		ListView<String> memberId = (ListView<String>) root.lookup("#memberId");
		ListView<String> memberPw = (ListView<String>) root.lookup("#memberPw");
		ListView<String> memberEmail = (ListView<String>) root.lookup("#memberEmail");
		ListView<String> memberTel = (ListView<String>) root.lookup("#memberTel");
		ListView<String> memberHoldTime = (ListView<String>) root.lookup("#memberHoldTime");
		ArrayList<MemberManageDTO> ALMMD = memberManageDao.SelectAll();
		memberRegDate.getItems().clear();
		memberName.getItems().clear();
		memberId.getItems().clear();
		memberPw.getItems().clear();
		memberEmail.getItems().clear();
		memberTel.getItems().clear();
		memberHoldTime.getItems().clear();
		for (MemberManageDTO memberManageDto : ALMMD) {
			memberRegDate.getItems().add(memberManageDto.getRegister_time());
			memberName.getItems().add(memberManageDto.getName());
			memberId.getItems().add(memberManageDto.getId());
			memberPw.getItems().add(memberManageDto.getPw());
			memberEmail.getItems().add(memberManageDto.getEmail());
			memberTel.getItems().add(memberManageDto.getTel());
			memberHoldTime.getItems().add(memberManageDto.getRemain_time());
		}
		return null;
	}

	public MemberManageDTO memberSelectProc(Parent root) {
		TextField memberSearchId = (TextField) root.lookup("#memberSearchId");
		TextField memberModifyPw = (TextField) root.lookup("#memberModifyPw");
		TextField memberModifyEmail = (TextField) root.lookup("#memberModifyEmail");
		TextField memberModifyTel = (TextField) root.lookup("#memberModifyTel");
		memberManageDto = memberManageDao.Select(memberSearchId.getText());
		if (memberManageDto != null) {
			memberModifyPw.textProperty().setValue(memberManageDto.getPw());
			memberModifyEmail.textProperty().setValue(memberManageDto.getEmail());
			memberModifyTel.textProperty().setValue(memberManageDto.getTel());
		} else {
			controller.getMainSvc().msg("회원정보가 없습니다.");
		}
		return memberManageDto;
	}

	public int memberUpdateProc(Parent root) {
		int result = 0;
		TextField memberSearchId = (TextField) root.lookup("#memberSearchId");
		TextField memberModifyPw = (TextField) root.lookup("#memberModifyPw");

		TextField memberModifyEmail = (TextField) root.lookup("#memberModifyEmail");
		String patternEmail = "^[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		String email = memberModifyEmail.getText();
		boolean email_check = Pattern.matches(patternEmail, email);

		TextField memberModifyTel = (TextField) root.lookup("#memberModifyTel");
		String telPatturn = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}$";
		String tel = memberModifyTel.getText();
		boolean tel_check = Pattern.matches(telPatturn, tel);

		if (email_check) {
			if (tel_check) {
				if (memberManageDto != null) {
					memberManageDto.setPw(memberModifyPw.getText());
					memberManageDto.setEmail(memberModifyEmail.getText());
					memberManageDto.setTel(memberModifyTel.getText());
					result = memberManageDao.Update(memberManageDto);
				}
			} else {
				controller.getMainSvc().msg("Tel이 형식에 맞지 않습니다. 다시 입력해주세요.");
			}
		} else {
			controller.getMainSvc().msg("Email이 형식에 맞지 않습니다. 다시 입력해주세요.");
		}
		if (result != 0) {
			memberManageDto = null;
			memberSearchId.clear();
			memberModifyPw.clear();
			memberModifyEmail.clear();
			memberModifyTel.clear();
			controller.getMainSvc().msg("수정이 완료되었습니다.");
		}
		return result;
	}

	public int memberDeleteProc(Parent root) {
		int result = 0;
		TextField memberSearchId = (TextField) root.lookup("#memberSearchId");
		TextField memberModifyPw = (TextField) root.lookup("#memberModifyPw");
		TextField memberModifyEmail = (TextField) root.lookup("#memberModifyEmail");
		TextField memberModifyTel = (TextField) root.lookup("#memberModifyTel");
		if (memberManageDto != null) {
			result = memberManageDao.Delete(memberManageDto);
		}
		if (result != 0) {
			memberManageDto = null;
			memberSearchId.clear();
			memberModifyPw.clear();
			memberModifyEmail.clear();
			memberModifyTel.clear();
			controller.getMainSvc().msg("삭제가 완료되었습니다.");
		}
		return result;
	}

}
