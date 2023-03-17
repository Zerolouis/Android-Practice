package com.zerolouis.practice03.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private Integer id;
	private String username;
	private String password;
	private Integer age;
	private String phone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id:" + id +
				"\n用户名:" + username +
				"\n密码:" + password  +
				"\n年龄:" + age +
				"\n手机号码:" + phone;
	}

	public UserInfo(Integer id, String username, String password, Integer age, String phone) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
		this.phone = phone;
	}

	public UserInfo() {
	}
}
