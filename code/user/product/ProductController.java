package user.product;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Controller;
import user.menu.ItemDTO;

public class ProductController {
	private Controller controller;
	private ProductService productSvc;
	private Parent productForm;
	private Spinner<Integer> MICFpsc;
	private ImageView MICFImage;
	private Label MICFName;
	private Label MICFPrice;
	private Button MICFPutButton;
	private ItemDTO item;

	public void setController(Controller controller) {
		this.controller = controller;
		productSvc.setController(controller);
	}
	public void setItem(ItemDTO item) {//아이템 정보 받을때 사용
		this.item = item;
	}
	public ItemDTO getItem() {
		return item;
	}
	public void setProductForm(Parent productForm) {
		this.productForm = productForm;
	}
	public ProductController() {
		productSvc = new ProductService();
	}
	
	
	//init
	public void init() {
		SpinnerValueFactory<Integer> valueFactoryFrom = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
		MICFpsc = (Spinner<Integer>)productForm.lookup("#MICFpsc");
		MICFImage = (ImageView)productForm.lookup("#MICFImage");
		MICFName = (Label)productForm.lookup("#MICFName");
		MICFPrice = (Label)productForm.lookup("#MICFPrice");
		MICFPutButton = (Button)productForm.lookup("#MICFPutButton");
		MICFpsc.setValueFactory(valueFactoryFrom);
		String image2 = item.getItemPic().split("/")[1];
		try {
			if(image2.equals("pic")) {
				MICFImage.setImage(new Image(item.getItemPic()));
			}else {
				FileInputStream fis = new FileInputStream(item.getItemPic());
				BufferedInputStream bis = new BufferedInputStream(fis);
				MICFImage.setImage(new Image(bis));
				try {
					bis.close();
					fis.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			MICFImage.setImage(new Image("/pic/notFound.jpg"));
		}
		MICFName.setText(item.getMenuName());
		MICFPrice.setText("" + item.getPrice());
		MICFPutButton.setOnAction(event -> {
			putProc(item);
		});
	}
	//담기
	public void putProc(ItemDTO item) {
		productSvc.putList(productForm, item);
	}
	
}
