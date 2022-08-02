package user.menu;

import java.util.ArrayList;

import javafx.scene.Parent;

public interface MenuService {
	public void backHome();//창닫기&메인불러오기
	public void productOpen(ItemDTO item);//상품정보오픈
	public void makeGoodsLine(ArrayList<String> itemList);//DB목록을 받아와서 상품목록제작
	public void userInfo(String id);//유저정보
	public void tcOpen();//시간충전
	public void payOpen();//결제창
	public void menuCancel();//메뉴취소
	public void menuClear();
}
