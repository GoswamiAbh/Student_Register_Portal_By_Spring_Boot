package com.abhi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.binding.DashboardResponse;
import com.abhi.binding.EnquiryForm;
import com.abhi.binding.EnquirySearchCriteria;
import com.abhi.entity.CourseEntity;
import com.abhi.entity.EnqStatusEntity;
import com.abhi.entity.StudentEnqEntity;
import com.abhi.entity.UserDtlsEntity;
import com.abhi.repo.CourseRepo;
import com.abhi.repo.EnqStatusRepo;
import com.abhi.repo.StudentEnqRepo;
import com.abhi.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			 Integer totalCnt = enquiries.size();
			 
			 Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled")).collect(Collectors.toList()).size();
			 
			 Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
			 .collect(Collectors.toList()).size();
			 
			 response.setTotalEnquiriesCnt(totalCnt);
			 response.setEnrolledCnt(enrolledCnt);
			 response.setLostCnt(lostCnt);
			 
			}
		   
		return response;
	}
	
	@Override
	public List<String> getCourses() {
		List<CourseEntity> findAll = courseRepo.findAll();
		
		//courses ke name chaiye bs drop down mai iske liye ye method likhenge
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		
		//courses drop dropdown ke liye
		List<String> statusList= new ArrayList<>();
		
		for(EnqStatusEntity entity:findAll) {
			statusList.add(entity.getStatusName());
		}
		
		return statusList;
	}

	

	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		StudentEnqEntity enqEntity= new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		
		Integer userId = (Integer) session.getAttribute("userId");
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		
		enqRepo.save(enqEntity);
		
		return true;
	}

	@Override
	public List<EnquiryForm> getEnquiry(Integer userId, EnquirySearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
