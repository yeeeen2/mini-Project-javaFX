package user.payment;


import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Controller;
import user.product.Product;




public class PaymentServiceImpl implements PaymentService{
	
	
	@FXML private Label paymentTotal;
	@FXML private TableView<Product> paymentList;
	@FXML private TableColumn<Product, String> paymentListMenu;
	@FXML private TableColumn<Product, Integer> paymentListCount;
	@FXML private TableColumn<Product, Integer> paymentListPrice;
	
	private Product product;	
	private Controller controller;
	private ArrayList<Product> myList;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}	
	public Controller getController() {
		return controller;
	}
	

	public void setList(ArrayList<Product> myList) {
		this.myList = myList;
	}

	
	// 결제 버튼 클릭 시 이벤트
	public void payProc(Parent root) {

		TableView<Product> protable = (TableView<Product>) root.lookup("#paymentList");

		for(int i = 0; i < protable.getItems().size(); i++) {
			Product pd = new Product();

			pd.setItem_code(myList.get(i).getItem_code());

			int a = protable.getItems().get(i).getPcs();
			int b = protable.getItems().get(i).getAmount();	//단가
			int sum = a*b;
			pd.setAmount(sum);	// 단가가 아니라 총합으로 사용

			pd.setPcs(protable.getItems().get(i).getPcs());

			PaymentDAO paymentdao = new PaymentDAO();

			int saleIteminfo = paymentdao.insertItemSale(pd);



			if(saleIteminfo == 1) {

				SaleDTO saledto1 = new SaleDTO();

				saledto1.setSale_code(myList.get(i).getItem_code());
				saledto1.setSale_pcs(protable.getItems().get(i).getPcs());
				int updateCount = paymentdao.updateItemCount(saledto1);

				if(updateCount == 1) {
					if(i == (protable.getItems().size() -1)) {
						String id = controller.getId();
						controller.getUserList().get(id).setPurchaseList(myList);
						controller.getAdminController().orderListProc(controller.getUserList().get(id));
						controller.getMainSvc().msg("결제 완료되었습니다");
						myList.clear();
						controller.getUserController().putListProc();
						controller.getMenuController().putListProc();
						controller.getMainSvc().windowClose(root);
					}
				}else {
					controller.getMainSvc().msg("결제 실패되었습니다");
				}
			}

		}

	}

}





