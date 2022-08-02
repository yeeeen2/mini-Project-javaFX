package administrator.inven;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Controller;

public class InventoryService {
	private InventoryDAO inventoryDao;
	private InventoryDTO inventoryDto;
	private Controller controller;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public InventoryService() {
		inventoryDao = new InventoryDAO();
	}

	// 상품정보창
	public void confirmOpen(Parent root) {
		ListView<String> lv = (ListView<String>) root.lookup("#lv");
		String item = lv.getSelectionModel().getSelectedItem();
		inventoryDto = inventoryDao.selectItem(item);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/administrator/inven/InfoConfirm.fxml"));
		Parent MIConfirm;
		try {
			MIConfirm = loader.load();
			InvenController invenController = controller.getInvenController();
			loader.setController(invenController);
			invenController.setConfirmForm(MIConfirm);
			invenController.confirmInit();
			Label infoConfirmName = (Label) MIConfirm.lookup("#infoConfirmName");
			Label infoConfirmPrice = (Label) MIConfirm.lookup("#infoConfirmPrice");
			Label infoConfirmpcs = (Label) MIConfirm.lookup("#infoConfirmpcs");
			ImageView infoConfirmImage = (ImageView) MIConfirm.lookup("#infoConfirmImage");
			infoConfirmName.setText(inventoryDto.getMenu_name());
			infoConfirmPrice.setText("" + inventoryDto.getPrice());
			infoConfirmpcs.setText("" + inventoryDto.getPcs());
			String image2 = inventoryDto.getItem_pic().split("/")[1];
			try {
				if(image2.equals("pic")) {
					infoConfirmImage.setImage(new Image(inventoryDto.getItem_pic()));
				}else {
					FileInputStream fis = new FileInputStream(inventoryDto.getItem_pic());
					BufferedInputStream bis = new BufferedInputStream(fis);
					infoConfirmImage.setImage(new Image(bis));
					try {
						bis.close();
						fis.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				infoConfirmImage.setImage(new Image("/pic/notFound.jpg"));
			}
			Scene scene = new Scene(MIConfirm);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("상품확인창");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register(Parent root) {
		// Listview 화면에 데이터 출력하기.
		ListView<String> lv = (ListView<String>) root.lookup("#lv");
		lv.getItems().clear();
		ArrayList<String> list = inventoryDao.selectAll();
		// Listview에 데이터 불러오기
		if (lv != null) {
			for (String manu : list)
				lv.getItems().add(manu);
		}
	}

	// 상품등록창 오픈
	public void regOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/administrator/inven/register/Item_Register.fxml"));
		Parent MIRegister;
		try {
			MIRegister = loader.load();
			controller.setItemRegController(loader.getController());
			controller.getItemRegController().setItem_Register(MIRegister);
			ComboBox<String> combo = (ComboBox<String>)MIRegister.lookup("#ItemRegCategory");
			if(combo!=null) {
				combo.getItems().addAll("식사","라면","분식","간식","과자","음료");
			}
			Scene scene = new Scene(MIRegister);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("상품등록");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 수정창연결
	public void editOpen(Parent root) {
		Label infoConfirmName = (Label)root.lookup("#infoConfirmName");
		inventoryDto = inventoryDao.selectItem(infoConfirmName.getText());
		controller.getMainSvc().windowClose(root);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/administrator/inven/edit/Item_Edit.fxml"));
		Parent editForm;
		try {
			editForm = loader.load();
			controller.setItemEditController(loader.getController());
			controller.getItemEditController().setItem_Edit(editForm);
			Label ItemModifyName = (Label) editForm.lookup("#ItemModifyName");
			TextField ItemModifyPrice = (TextField) editForm.lookup("#ItemModifyPrice");
			TextField ItemModifyCount = (TextField) editForm.lookup("#ItemModifyCount");
			ImageView ItemModifyPicture = (ImageView) editForm.lookup("#ItemModifyPicture");
			Label ItemModifyTotalPrice = (Label) editForm.lookup("#ItemModifyTotalPrice");
			ItemModifyName.setText(inventoryDto.getMenu_name());
			ItemModifyPrice.setText("" + inventoryDto.getPrice());
			ItemModifyCount.setText("" + inventoryDto.getPcs());
			ItemModifyTotalPrice.setText("" + (inventoryDto.getPrice() * inventoryDto.getPcs()));
			String image2 = inventoryDto.getItem_pic().split("/")[1];
			try {
				if(image2.equals("pic")) {
					ItemModifyPicture.setImage(new Image(inventoryDto.getItem_pic()));
				}else {
					FileInputStream fis = new FileInputStream(inventoryDto.getItem_pic());
					BufferedInputStream bis = new BufferedInputStream(fis);
					ItemModifyPicture.setImage(new Image(bis));
					try {
						bis.close();
						fis.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				ItemModifyPicture.setImage(new Image("/pic/notFound.jpg"));
			}
			ComboBox<String> combo = (ComboBox<String>)editForm.lookup("#ItemModifyCategory");
			if(combo!=null) {
				combo.getItems().addAll("식사","라면","분식","간식","과자","음료");
			}
			combo.setValue(inventoryDto.getMenu_category());
			Scene scene = new Scene(editForm);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("상품수정");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 삭제
	public void delete(Parent root) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("상품삭제");
		alert.setContentText("등록된 상품을 삭제하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Label infoConfirmName = (Label) root.lookup("#infoConfirmName");
			if (inventoryDao.delete(infoConfirmName.getText()) != 0) {
				controller.getMainSvc().msg("삭제되었습니다.");
				controller.getMainSvc().windowClose(root);
				controller.getInvenController().listReg();
			} else
				controller.getMainSvc().msg("삭제오류");
		} else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}

}
