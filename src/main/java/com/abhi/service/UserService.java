package com.abhi.service;

import com.abhi.binding.LoginFoam;
import com.abhi.binding.SignUpForm;
import com.abhi.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginFoam form);
	
	public boolean signup(SignUpForm form);
	
	public boolean forgotPwd(String email);
	
	public boolean unlockAccount(UnlockForm form);

}
