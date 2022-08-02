package administrator.manage;

import javafx.scene.Parent;

public interface MemberManageService {
	
	public MemberManageDTO memberSelectAllProc(Parent root);
	
	public MemberManageDTO memberSelectProc(Parent root);
	
	public int memberUpdateProc(Parent root);
	
	public int memberDeleteProc(Parent root);
	
}
