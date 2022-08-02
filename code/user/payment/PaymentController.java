package user.payment;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Controller;
import user.product.Product;



public class PaymentController implements Initializable {
	private Controller controller;
	private Parent root;
	private PaymentServiceImpl paymentSvc;
	private ArrayList<Product> myList;
	private int sum = 0;
	
	@FXML private Label paymentTotal;
	@FXML private TableView<Product> paymentList;
	@FXML private TableColumn<Product, Integer> paymentNum;
	@FXML private TableColumn<Product, String> paymentItem;
	@FXML private TableColumn<Product, Integer> paymentCount;
	@FXML private TableColumn<Product, Integer> paymentPrice;
	
	public void setController(Controller controller) {
		this.controller = controller;
		paymentSvc.setController(controller);
	}
	public void setRoot(Parent root) {
		this.root = root;
	}
	public void setMyList(ArrayList<Product> myList) {
		this.myList = myList;
		tableSetProc();
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.paymentSvc = new PaymentServiceImpl();
	}
	
	public void tableSetProc() {
		paymentNum.setCellValueFactory(new PropertyValueFactory("num"));
		paymentItem.setCellValueFactory(new PropertyValueFactory("name"));
		paymentCount.setCellValueFactory(new PropertyValueFactory("pcs"));
		paymentPrice.setCellValueFactory(new PropertyValueFactory("amount"));
		for(int i = 0; i < myList.size(); i++) {
			myList.get(i).setNum(i + 1);
			paymentList.getItems().add(myList.get(i));
		}
		for(Product pd : myList) {
			sum += (pd.getPcs() * pd.getAmount());
		}
		paymentTotal.setText("" + sum);
	}
	
	public void payProc() {
		paymentSvc.setList(myList);
		paymentSvc.payProc(root);//아이디값 받기
	}
	
	public void cancelProc() {
		controller.getMainSvc().msg("결제를 취소하였습니다.");
		controller.getMainSvc().windowClose(paymentList);
	}

}
