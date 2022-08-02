package user.payment;

import java.sql.Date;

//[member_info]
//
//		 create table member_info (
//		 id varchar2(20),
//		 pw varchar2(20),
//		 name varchar2(20),
//		 email varchar2(50),
//		 tel varchar2(20),
//		 remain_time number default 0,
//		 register_time date
//		 );
//
//
//		ALTER TABLE member_info ADD PRIMARY KEY (id);


public class MemberinfoDTO {
	

	private String id;
	private String pw;
	private String name;
	private String email;
	private String tel;
	private int remain_time;
	private Date register_time;
	
	
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
	public int getRemain_time() {
		return remain_time;
	}
	public void setRemain_time(int remain_time) {
		this.remain_time = remain_time;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	
	
	

}
