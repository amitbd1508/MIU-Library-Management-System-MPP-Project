package service;

import datapersistant.DataAccessFacade;
import model.User;

public class AuthService {
	private DataAccessFacade dataaccess;
	public AuthService() {
		dataaccess =  new DataAccessFacade();
	}
	
	public User authinticate(String username, String password) {
		var user = this.dataaccess.getUser(username, password);
		return user;
	}
}
