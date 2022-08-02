package administrator.inven.register;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import main.Controller;

public class ItemRegController implements Initializable{
	@FXML ImageView ItemRegPicture;
	@FXML TextField ItemRegName;
	@FXML TextField ItemRegPrice;
	@FXML TextField ItemRegCount;
	@FXML Label ItemRegTotalPrice;
	@FXML Button ItemRegNameButton;
	@FXML Button ItemRegButton;
	
	private Controller controller;
	private Parent item_Register;
	private ItemRegService itemRegService;
	private File selectFile;
	private String fileName;
	private ItemRegDTO itemRegDTO;
	
	
	public void setController(Controller controller) {
		this.controller = controller;
		itemRegService.setController(controller);
	}
	public void setItem_Register(Parent item_Register) {
		this.item_Register = item_Register;
	}
	public void setSelectFile(File selectFile) {
		this.selectFile = selectFile;
	}
	public void setDto(ItemRegDTO itemRegDTO) {
		this.itemRegDTO = itemRegDTO;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemRegService = new ItemRegService();
		
		ItemRegButton.setDisable(true);
		ItemRegNameButton.setOnAction(event->{
			if(ItemRegName.getText().equals("")) { 		//빈칸일때
				controller.getMainSvc().msg("등록하고자 하는 상품의 이름은 한글자 이상이어야합니다.");
				ItemRegButton.setDisable(true);
			}else { 									//빈칸아니고 어떤것을 넣었을때
				nameCheck();
				if(nameCheck()==1) {
					ItemRegButton.setDisable(false);
				}else {
					ItemRegButton.setDisable(true);
				}
									
			}
				
			
		});
		
		ItemRegCount.textProperty().addListener((attribute,before,after)->{
			ItemRegTotalPrice.setText(String.valueOf(Integer.parseInt(after) * Integer.parseInt(ItemRegPrice.getText())));
		});
		
	}
	
	//등록버튼 (fx_id : ItemRegButton)
	public void item_reg() {
		itemRegService.insert(item_Register, selectFile);
	}
	
	
	
	//이미지선택버튼 (fx_id : ItemRegPicButton)
	public void imageSelect() {
		ItemRegPicture.setImage(null);
		
		//사진 선택 창
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("이미지선택");
		fileChooser.setInitialDirectory(new File("C:")); //default 디렉토리 설정
		
		//선택한 파일 정보 추출
		//확장자 제한
		ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg","*.gif","*png");
		fileChooser.getExtensionFilters().add(imgType);
		
		selectFile = fileChooser.showOpenDialog(null);	// showOpenDialog는 창을 띄우는데 어느 위치에 띄울건지 인자를 받고
        												// 그리고 선택한 파일의 경로값을 반환한다.
		fileName = selectFile.getName();
		try {
			FileInputStream fis = new FileInputStream(selectFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Image img = new Image(bis);
			ItemRegPicture.setImage(img);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//상품이름 중복 확인버튼 (fx_id : ItemRegNameButton)
	public int nameCheck() {
		return itemRegService.nameCheck(item_Register);
	
	}
	
	
	

}
