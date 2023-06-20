package com.abhi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.abhi.binding.DashboardResponse;
import com.abhi.binding.EnquiryForm;
import com.abhi.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService enqService;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();

		return "index";

	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		// TODO: logic to fetch the data
		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";

	}
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		//TODO: save the data
		
		boolean status = enqService.saveEnquiry(formObj);
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added");
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
		
		return "add-enquiry";
		
	}


	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		List<String> courses = enqService.getCourses();

		List<String> enqStatuses = enqService.getEnqStatuses();

		EnquiryForm formObj = new EnquiryForm();


		model.addAttribute("courseNames", courses);
		model.addAttribute("StatusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";

	}
	
	
	@GetMapping("/enquires")
	public String viewEnquiries() {
		return "view-enquiries";

	}

}
