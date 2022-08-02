package administrator.inven;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import main.Controller;

public class InvenController implements Initializable {
	private InventoryService invenSvc;
	private Controller controller;
	private Parent invenForm;
	private Parent confirmForm;
	
	public void setController(Controller controller) {
		this.controller = controller;
		invenSvc.setController(controller);
	}
	public void setInvenForm(Parent invenForm) {
		this.invenForm = invenForm;
		invenSvc.register(invenForm);
	}
	public Parent getInvenForm() {
		return invenForm;
	}
	public void setConfirmForm(Parent confirmForm) {
		this.confirmForm = confirmForm;
	}
	public Parent getConfirmForm() {
		return confirmForm;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		invenSvc = new InventoryService();
	}
	//리스트 등록
	public void listReg() {
		invenSvc.register(invenForm);
	}
	// 상품등록 버튼 클릭 시 동작.
	public void MIItemregButtonProc() {
		invenSvc.regOpen();
	}
	public void confirmInit() {
		Button infoConfirmDelButton = (Button)confirmForm.lookup("#infoConfirmDelButton");
		Button infoConfirmEditButton = (Button)confirmForm.lookup("#infoConfirmEditButton");
		
		infoConfirmEditButton.setOnAction(event -> itemEditProc());
		infoConfirmDelButton.setOnAction(event -> itemDeleteProc());
	}
	// 확인버튼 클릭 시 동작.
	public void MIConfirmbtProc() {
		invenSvc.confirmOpen(invenForm);
	}
	
	//수정확인창연결
	public void editOpenProc() {
		invenSvc.editOpen(confirmForm);
	}
	//상품확인창 수정버튼 클릭시 동작
	public void itemEditProc() {
		invenSvc.editOpen(confirmForm);
	}
	//상품확인창 삭제버튼 클릭시 동작
	public void itemDeleteProc() {
		invenSvc.delete(confirmForm);
	}
}

			
