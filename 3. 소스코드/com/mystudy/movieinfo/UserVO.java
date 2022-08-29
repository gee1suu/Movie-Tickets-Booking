package com.mystudy.movieinfo;
public class UserVO {

	int customer_id;
	String customer_name;
	String membership_id;
	String user_id;
	String user_pw;
	String user_email;

	public UserVO() {

	}

	public UserVO(int customer_id, String customer_name, String membership_id, String user_id, String user_pw,
			String user_email) {

		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.membership_id = membership_id;
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_email = user_email;
	}

	public UserVO(String customer_name, String user_id, String user_pw, String user_email) {
		super();
		this.customer_name = customer_name;
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_email = user_email;
	}

	public UserVO(int customer_id, String customer_name, String user_id, String user_pw, String user_email) {
		super();
		this.customer_name = customer_name;
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_email = user_email;
		this.customer_id = customer_id;
	}

	public UserVO(String user_id, String user_pw) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getMembership_id() {
		return membership_id;
	}

	public void setMembership_id(String membership_id) {
		this.membership_id = membership_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	@Override
	public String toString() {
		return "고객번호 : " + getCustomer_id() + "\n" + "이름 : " + getCustomer_name() + "\n"
				+ "멤버십등급 : " + getMembership_id() + "\n" + "ID : " + getUser_id() + "\n" + "PW : "
				+ getUser_pw() + "\n" + "EMAIL : " + getUser_email();
	}

}
