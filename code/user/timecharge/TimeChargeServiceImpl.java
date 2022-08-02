package user.timecharge;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Controller;


public class TimeChargeServiceImpl implements TimeChargeService{
	private Controller controller;
	private TimeChargeDAO timechargedao;

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@FXML private Button TC1hourButton;
	@FXML private Button TC2hourButton;
	@FXML private Button TC3hourButton;
	@FXML private Button TC5hourButton;
	@FXML private Button TC12hourButton;
	@FXML private Button TC24hourButton;
	@FXML private Button TCPayButton;
	@FXML private Label TCIdView;

	ArrayList<String> idList1 = new ArrayList<String>();

	public TimeChargeServiceImpl() {
		timechargedao = new TimeChargeDAO();
	}
	// 아이디 검색 버튼 클릭 시 호출

	public void TCIdSearchProc(Parent root) {
		ListView<String> tci = (ListView<String>)root.lookup("#TCIdSearchList");
		tci.getItems().clear();

		TextField idText = (TextField)root.lookup("#TCIdSearch");
		String strInput = idText.getText();
		if(strInput.equals("")) {

			return;
		}else {
			ArrayList<String> idList = timechargedao.SelectId(strInput);

			if(idList.size() != 0) {
				for(int i=0;i<idList.size();i++) {

					String a = idList.get(i);
					idList1.add(a);
					int length = a.length();
					length = length - 2;
					String maskId = a.substring(0, length);

					maskId = maskId + "**";
					tci.getItems().add(i, maskId);
				}
			}
		}

	}

	// 결제 버튼 클릭 시 호출
	public void payOpen(Parent root){

		Label TCIdView = (Label) root.lookup("#TCIdView");
		Label TCTimeView = (Label) root.lookup("#TCTimeView");
		Label TCPriceView = (Label) root.lookup("#TCPriceView");

		ListView<String> tci = (ListView<String>)root.lookup("#TCIdSearchList");
		int selectIdx = tci.getSelectionModel().getSelectedIndex();

		String selectId = selectIdx != -1 ?  idList1.get(selectIdx) : "";
		String selectTime = TCTimeView.getText();
		int selectPrice = TCPriceView.getText().equals("0") ? 0 : Integer.parseInt(TCPriceView.getText());

		if((selectId.isEmpty() && selectIdx == -1) || selectTime.isEmpty() || selectPrice == 0) {
			controller.getMainSvc().msg("결제 실패");
			return;
		} else {
			int insertinfo = timechargedao.insertTimeSale(selectPrice);

			if(insertinfo == 1) {
				int updateinfo = timechargedao.updateTime(selectId, selectTime);
				controller.getMainSvc().msg("결제 완료되었습니다");
				if(controller.getUserController() != null) {
					controller.getUserController().userInfoProc(selectId);
				}
				if(controller.getMenuController() != null) {
					controller.getMenuController().userInfoProc();
				}
				controller.getMainSvc().windowClose(root);
			}
		}
	}
}



