package administrator.inven.register;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.Controller;

public class ItemRegService {
	@FXML Button ItemRegButton;

	private Controller controller;
	private ItemRegDAO itemRegDAO = new ItemRegDAO();
	private ItemRegDTO itemRegDTO;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public void setDto(ItemRegDTO itemRegDTO) {
		this.itemRegDTO = itemRegDTO;
	}

	public void insert(Parent item_Register, File selectFile) {
		
		//TextField
		TextField ItemRegName = (TextField)item_Register.lookup("#ItemRegName");
		TextField ItemRegCount = (TextField)item_Register.lookup("#ItemRegCount");
		TextField ItemRegPrice = (TextField)item_Register.lookup("#ItemRegPrice");
		Label ItemRegTotalPrice = (Label)item_Register.lookup("#ItemRegTotalPrice");
		ImageView ItemRegPicture = (ImageView)item_Register.lookup("#ItemRegPicture");
		
		ComboBox<String> ItemRegCategory = (ComboBox<String>)item_Register.lookup("#ItemRegCategory");
		String category = ItemRegCategory.getValue();
		
		String name = ItemRegName.getText();
		String countString = ItemRegCount.getText();
		int count = Integer.parseInt(countString);
		String priceString = ItemRegPrice.getText();
		int price= Integer.parseInt(priceString);
		
		if(name.isEmpty()) {
			controller.getMainSvc().msg("필수 데이터입니다. 내용을 입력해주세요.");
			return;
		}else {
			ItemRegDTO itemRegDTO = itemRegDAO.SelectName(name);
			if(itemRegDTO==null) {
				itemRegDTO = new ItemRegDTO();
				
				//아이템코드 코드정의작업(카테고리분류)
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
				
				ArrayList<String> Numlist = itemRegDAO.codeInput(code_categoryNum);
				int maxNum = 0;
				
				for(String num : Numlist) {
					int a = Integer.parseInt("" + num.charAt(2) + num.charAt(3));
					if(a > maxNum)
						maxNum = a;
				}
				
				String code_num = "i" + code_categoryNum + String.format("%02d", ++maxNum);
				
				//아이템코드 코드 조합작업
				itemRegDTO.setItem_code(code_num);
				itemRegDTO.setMenu_category(category);
				itemRegDTO.setMenu_name(name);
				itemRegDTO.setPrice(price);
				itemRegDTO.setPcs(count);
				
				//파일경로 불러와서 추출
				String filePath =String.valueOf(selectFile);
				filePath = filePath.replace("\\", "/");
				itemRegDTO.setItem_pic(filePath);
				itemRegDAO.insert(itemRegDTO);
				controller.getMainSvc().msg("상품이 등록 완료되었습니다.");
				ItemRegTotalPrice.setText(String.valueOf(count*price));
				controller.getInvenController().listReg();
				controller.getMainSvc().windowClose(item_Register);
			}else {
				controller.getMainSvc().msg("이미 존재하는 품목명입니다.");
			}
		}
	}
	

	public int nameCheck(Parent item_Register) {
		TextField ItemRegName = (TextField)item_Register.lookup("#ItemRegName");
		String name = ItemRegName.getText();
		int result=0;
		
		itemRegDTO = itemRegDAO.SelectName(name);
		
		if(name.equals("")) {
			controller.getMainSvc().msg("등록하고자 하는 품목을 입력해주세요.");
		}else {
			if(itemRegDTO!=null && name.equals(itemRegDTO.getMenu_name())){
				controller.getMainSvc().msg("중복되는 이름이 존재합니다.");
				return result;
			}else {
				controller.getMainSvc().msg("사용가능한 이름입니다.");
				return result=1;
			}
		}
		return result;
	}
	
}
