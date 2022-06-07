package controller;

import common.AppContext;
import service.AuthService;

public class AuthController {
	private AuthService authSvc;
	
	public AuthController() {
		this.authSvc = new AuthService();
		
	}
	public boolean authinticate(String username, String password) {
		var user = this.authSvc.authinticate(username, password);
		if(user == null)
			return false;
		
		// need refactor
		AppContext.loginUser = user;
		
		return true;
		
	}

}
