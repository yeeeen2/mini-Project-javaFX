package administrator.inven.edit;

import java.io.File;
import java.util.ArrayList;

import administrator.inven.register.ItemRegDTO;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.Controller;

public class ItemEditService {

	private Controller controller;
	private ItemEditDAO itemEditDAO = new ItemEditDAO();
	private ItemEditDTO itemEditDTO;
	private ItemRegDTO itemRegDTO;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setDto(ItemEditDTO itemEditDTO) {
		this.itemEditDTO = itemEditDTO;
	}
	public ItemRegDTO getItemRegDTO() {
		return itemRegDTO;
	}

	public void setItemRegDTO(ItemRegDTO itemRegDTO) {
		this.itemRegDTO = itemRegDTO;
	}

	public void update(Parent item_Edit, File selectFile) {

		// TextField
		Label ItemModifyName = (Label) item_Edit.lookup("#ItemModifyName");
		TextField ItemModifyCount = (TextField) item_Edit.lookup("#ItemModifyCount");
		TextField ItemModifyPrice = (TextField) item_Edit.lookup("#ItemModifyPrice");
		Label ItemModifyTotalPrice = (Label) item_Edit.lookup("#ItemModifyTotalPrice");
		ImageView ItemModifyPicture = (ImageView) item_Edit.lookup("#ItemModifyPicture");

		ComboBox<String> ItemModifyCategory = (ComboBox<String>) item_Edit.lookup("#ItemModifyCategory");
		String category = ItemModifyCategory.getValue();

		String name = ItemModifyName.getText();
		String countString = ItemModifyCount.getText();
		int count = Integer.parseInt(countString);
		String priceString = ItemModifyPrice.getText();
		int price = Integer.parseInt(priceString);

		if (name.isEmpty()) {
			controller.getMainSvc().msg("필수 데이터입니다. 내용을 입력해주세요.");
			return;
		} else {
			ItemEditDTO itemEditDTO = itemEditDAO.SelectName(name);
			if (itemEditDTO != null) {
				itemEditDTO = new ItemEditDTO();
				int code_categoryNum = 0;
				
				if(category.equals("식사")) {
					code_categoryNum=1;
				}else if(category.equals("라면")) {
					code_categoryNum=2;
				}else if(category.equals("분식")) {
					code_categoryNum=3;
				}else if(category.equals("간식")) {
					code_categoryNum=4;
				}else if(category.equals("과자")) {
					code_categoryNum=5;
				}else if(category.equals("음료")) {
					code_categoryNum=6;
				}
				
				//아이템코드 코드정의작업(번호 차례대로 붙이기)
				
				ArrayList<String> Numlist = itemEditDAO.codeInput(code_categoryNum);
				int maxNum = 0;
				
				for(String num : Numlist) {
					int a = Integer.parseInt("" + num.charAt(2) + num.charAt(3));
					if(a > maxNum)
						maxNum = a;
				}
				
				String code_num = "i" + code_categoryNum + String.format("%02d", ++maxNum);
				
				//아이템코드 코드 조합작업
				itemEditDTO.setItem_code(code_num);
				itemEditDTO.setMenu_category(category);
				itemEditDTO.setMenu_name(name);
				itemEditDTO.setPrice(price);
				itemEditDTO.setPcs(count);

				itemEditDAO.update(itemEditDTO);
				
				controller.getMainSvc().msg("상품이 수정 완료되었습니다.");
				ItemModifyTotalPrice.setText(String.valueOf(count * price));
				controller.getMainSvc().windowClose(item_Edit);
			} else {
				controller.getMainSvc().msg("존재하지 않은 품목은 수정할 수 없습니다.");
			}
		}
	}

	public void nameCheck(Parent item_Edit) {
		TextField ItemEditName = (TextField) item_Edit.lookup("#ItemModifyName");
		String name = ItemEditName.getText();

		itemEditDTO = itemEditDAO.SelectName(name);

		if (name.equals("")) {
			controller.getMainSvc().msg("수정하고자 하는 품목을 입력해주세요.");
		} else {
			if (itemEditDTO != null && name.equals(itemEditDTO.getMenu_name())) {
				controller.getMainSvc().msg("수정가능한 품목입니다.");
			} else {
				controller.getMainSvc().msg("수정할품목이 존재하지 않습니다.");
			}
		}
	}

}
