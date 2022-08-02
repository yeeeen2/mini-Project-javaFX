package user.product;

import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.control.Spinner;
import main.Controller;
import user.menu.ItemDTO;

public class ProductService {
	private Controller controller;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	//담기
	public void putList(Parent productForm, ItemDTO item) {
		ArrayList<Product> list = controller.getMenuController().getList();
		Spinner<Integer> psc = (Spinner<Integer>)productForm.lookup("#MICFpsc");
		//같은아이템 확인
		for(Product tmp : list) {
			if(tmp.getName().equals(item.getMenuName())) {
				int pcs = tmp.getPcs() + psc.getValue();
				if(pcsCheck(pcs)) {
					tmp.setPcs(pcs);
					controller.getMenuController().putListProc();
					controller.getMainSvc().windowClose(productForm);
				}else {
					controller.getMainSvc().msg("수량이 부족합니다.");
				}
				return;
			}
		}
		if(pcsCheck(psc.getValue())) {
			Product put = new Product();
			put.setItem_code(item.getItemCode());
			put.setName(item.getMenuName());
			put.setPcs(psc.getValue());
			put.setAmount(item.getPrice());
			//프로덕트 리스트에 보내기
			list.add(put);
			controller.getMenuController().putListProc();//메뉴선택보내기
			controller.getMainSvc().windowClose(productForm);
		}else {
			controller.getMainSvc().msg("수량이 부족합니다.");
		}
	}
	
	private boolean pcsCheck(int pcs) {
		int inven = controller.getProductController().getItem().getPcs();
		if(pcs > inven) {
			return false;
		}
		return true;
	}
}
