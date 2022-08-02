package user.timecharge;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Controller;


public class TimeChargeController implements Initializable{

	@FXML private Button TCIdSearchButton;
	@FXML private TextField TCIdSearch;
	@FXML private ListView<String> TCIdSearchList;
	@FXML private Label TCIdView;
	@FXML private Label TCTimeView;
	@FXML private Label TCPriceView;
	@FXML private Button TC1hourButton;
	@FXML private Button TC2hourButton;
	@FXML private Button TC3hourButton;
	@FXML private Button TC5hourButton;
	@FXML private Button TC12hourButton;
	@FXML private Button TC24hourButton;

	private Controller controller;
	private Parent root;
	private TimeChargeServiceImpl timeChargeSvc;

	public void setController(Controller controller) {
		this.controller = controller;
		timeChargeSvc.setController(controller);
	}
	
	public void setRoot(Parent root) {
		this.root = root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.timeChargeSvc = new TimeChargeServiceImpl();
		TC1hourButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleBtn1Action(event);
			}
		});
		
		TC2hourButton.setOnAction(event->handleBtn2Action(event));
		TC3hourButton.setOnAction(event->handleBtn3Action(event));
		TC5hourButton.setOnAction(event->handleBtn4Action(event));
		TC12hourButton.setOnAction(event->handleBtn5Action(event));
		TC24hourButton.setOnAction(event->handleBtn6Action(event));
	}


	private void handleBtn1Action(ActionEvent event) {
		TCTimeView.setText("1시간");
		TCPriceView.setText("1000");
	}
	
	private void handleBtn2Action(ActionEvent event) {
		TCTimeView.setText("2시간");
		TCPriceView.setText("2000");
	}
	
	private void handleBtn3Action(ActionEvent event) {
		TCTimeView.setText("3시간");
		TCPriceView.setText("3000");
	}
	
	private void handleBtn4Action(ActionEvent event) {
		TCTimeView.setText("5시간");
		TCPriceView.setText("5000");
	}
	

	private void handleBtn5Action(ActionEvent event) {
		TCTimeView.setText("12시간");
		TCPriceView.setText("10000");
	}
	

	private void handleBtn6Action(ActionEvent event) {
		TCTimeView.setText("24시간");
		TCPriceView.setText("20000");
	}

	
	public void TCIdSearchProc() {
		timeChargeSvc.TCIdSearchProc(root);

	}
	
	public void idClicked() {
		ListView<String> tci = (ListView<String>)root.lookup("#TCIdSearchList");
		String item = tci.getSelectionModel().getSelectedItem();
		TCIdView.setText(item);
	}
	
	public void payOpen(){
		timeChargeSvc.payOpen(root);
	
	}
	
}
	


