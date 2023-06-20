package com.abhi.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.binding.LoginFoam;
import com.abhi.binding.SignUpForm;
import com.abhi.binding.UnlockForm;
import com.abhi.entity.UserDtlsEntity;
import com.abhi.repo.UserDtlsRepo;
import com.abhi.utils.EmailUtils;
import com.abhi.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private HttpSession session;
	
	@Override
	public boolean signup(SignUpForm form) {

		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		if (user != null) {
			return false;
		}

		// TODO: copy data from binding obj to entity obj

		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// TODO: generate random password and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		// TODO: set account status as locked
		entity.setAccStatus("LOCKED");

		// TODO: insert record
		userDtlsRepo.save(entity);

		// TODO:send email to user to unlock the account

		String to = form.getEmail();
		String subject = "Unlock your Account | Abhi Tech";
		StringBuffer body = new StringBuffer();
		body.append("<h1>Use below temporary to unlock your account</h1>");
		body.append("Temporary Password :" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">Click here to Unlock your account");
		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;

		} else {
			return false;
		}

	}

	@Override
	public String login(LoginFoam form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return "Invalid Credentials";
		}

		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		
		//create session and store user data in session 
		//jb dashboard mai kaam krenge to to create statefull applicaation

		//to know logged in user data as a key and value format
		session.setAttribute("userId", entity.getUserId());
		
		return "success";
	}

	@Override
	public boolean forgotPwd(String email) {

		// CHECK RECORD present IN DB WITH GIVEN EMAIL
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// if rrecord is not available send error msg

		if (entity == null) {
			// return "Invalid Email Id";
			return false;
		}

		// if record available send pwd to email and send succs msg
		String Subject = "Recover Password";
		String body = "Your Password :: " + entity.getPwd();

		emailUtils.sendEmail(email, Subject, body);

		// return "Password sent to your mail";
		return true;
	}

}
