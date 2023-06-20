package com.abhi.service;

import java.util.List;

import com.abhi.binding.DashboardResponse;
import com.abhi.binding.EnquiryForm;
import com.abhi.binding.EnquirySearchCriteria;

public interface EnquiryService {

	public List<String> getCourses();

	public List<String> getEnqStatuses();

	public DashboardResponse getDashboardData(Integer userId);

	public boolean saveEnquiry(EnquiryForm form);

	public List<EnquiryForm> getEnquiry(Integer userId, EnquirySearchCriteria criteria);

}
