package com.patel.pradeep.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	private final static String USERNAME_PROP_NAME = "userName";
	private final StringProperty userName;
	private final static String PASSWORD_PROP_NAME = "password";
	private StringProperty password;
	
	public User() {
		userName = new SimpleStringProperty(this,USERNAME_PROP_NAME,"");
		password = new SimpleStringProperty(this,PASSWORD_PROP_NAME,"");
	}
	
	public final String getUserName(){
		return userName.get();
	}
	
	public final void setUserName(String userName){
		this.userName.set(userName);
	}
	
	public StringProperty userNameProperty() {
		return userName;
	}

	public final String getPassword(){
		return password.get();
	}
	public final void setPassword(String password){
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return password;
	}

}
