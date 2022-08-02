package administrator;

import user.UserInfo;

public interface AdminService {
	public void nowLogin();//로그인현황 불러오기
	public void nowLogout(String id);//로그아웃
	public void orderCheck(UserInfo userOrder);//주문현황
	public void orderDelete();//주문현황삭제
	public void invenOpen();//재고관리창open
	public void memberOpen();//회원관리창open
}
