package administrator.inven.edit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.Controller;

public class ItemEditController implements Initializable{
	@FXML ImageView ItemModifyPicture;
	@FXML TextField ItemModifyPrice;
	@FXML TextField ItemModifyCount;
	@FXML Label ItemModifyTotalPrice;
	
	private ItemEditDTO itemEditDTO;
	
	private Parent item_Edit;
	private Controller controller;
	private ItemEditService itemEditService;
	private File selectFile;
	private String fileName;
	
	public void setController(Controller controller) {
		this.controller = controller;
		itemEditService.setController(controller);
	}
	public void setItem_Edit(Parent item_Edit) {
		this.item_Edit = item_Edit;
	}
	public void setSelectFile(File selectFile) {
		this.selectFile = selectFile;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemEditService = new ItemEditService();
		
		ItemModifyCount.textProperty().addListener((attribute,before,after)->{
			ItemModifyTotalPrice.setText(String.valueOf(Integer.parseInt(after)*Integer.parseInt(ItemModifyPrice.getText())));
		});
	
	}
	
	
	
	//등록버튼 (fx_id : ItemRegButton)
	public void item_edit() {
		itemEditService.update(item_Edit, selectFile);
	}
	
	//이미지선택버튼 (fx_id : ItemRegPicButton)
//	public void imageSelect() {
//		
//		//사진 선택 창
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("이미지선택");
//		fileChooser.setInitialDirectory(new File("C:")); //default 디렉토리 설정
//		
//		//선택한 파일 정보 추출
//		//확장자 제한
//		ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg","*.gif","*png");
//		fileChooser.getExtensionFilters().add(imgType);
//		
//		selectFile = fileChooser.showOpenDialog(null);	// showOpenDialog는 창을 띄우는데 어느 위치에 띄울건지 인자를 받고
//        												// 그리고 선택한 파일의 경로값을 반환한다.
//		fileName = selectFile.getName();
//		try {
//			FileInputStream fis = new FileInputStream(selectFile);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			Image img = new Image(bis);
//			ItemModifyPicture.setImage(img);
//		}catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	//상품이름 중복 확인버튼 (fx_id : ItemRegNameButton)
	public void nameCheck() {
		itemEditService.nameCheck(item_Edit);
	}
	
}