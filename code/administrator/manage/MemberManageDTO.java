package administrator.manage;

public class MemberManageDTO {
	
	private String id;
	private String pw;
	private String name;
	private String email;
	private String tel;
	private String remain_time;
	private String register_time;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getRemain_time() {
		return remain_time;
	}
	
	public void setRemain_time(String remain_time) {
		this.remain_time = remain_time;
	}
	
	public String getRegister_time() {
		return register_time;
	}
	
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

}
