package com.abhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abhi.binding.LoginFoam;
import com.abhi.binding.SignUpForm;
import com.abhi.binding.UnlockForm;
import com.abhi.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String singUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";

	}

	@PostMapping("/signup")
	public String handleSingUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signup(form);
		if (status) {
			model.addAttribute("succMsg", "Check your Email");
		} else {
			model.addAttribute("errMsg", "Problem occured");
		}
		model.addAttribute("user", new SignUpForm());
		return "signup";

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		// setting the data to bind ing parameter
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);

		model.addAttribute("unlock", unlockFormObj);

		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = userService.unlockAccount(unlock);
			if(status) {
				model.addAttribute("succMsg", "your account unlock successfully");
			}else {
				model.addAttribute("errMsg", "Given temporary pwd is incorrect,check your email");
			}
		} else {
			model.addAttribute("errMsg", "New pwd and Confirm pwd should be same");

		}

		return "unlock";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginFoam());
		return "login";

	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginFoam loginForm, Model model) {
		
		String status = userService.login(loginForm);
		if(status.contains("success")) {
			//redirect req to dashboard method
			//return "dashboard";(not a right way to right)
			return "redirect:/dashboard";
			
		}
		
		model.addAttribute("errMsg", status);
		
		return "login";

	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";

	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email, Model model) {
		System.out.println(email);
		
		//TODO:logic
		
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send sucess msg
			model.addAttribute("succMsg", "Pwd sent to your email");
			
		}else {
			//send error msg
			model.addAttribute("errMsg", "Invalid Email");
			
		}
		
		
		
		return "forgotPwd";

	}

}

























