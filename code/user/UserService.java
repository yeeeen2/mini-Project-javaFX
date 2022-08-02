package user;

import javafx.scene.Parent;

public interface UserService {
	public void menuWindowOpen(String menu);//메뉴선택창 오픈
	public void userInfo(String id);//유저정보
	public void logout();//로그아웃
	public void tcOpen();//시간충전
	public void payOpen();//결제창
}
