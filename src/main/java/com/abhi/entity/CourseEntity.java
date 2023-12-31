package com.abhi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="AIT_COURSES")
public class CourseEntity {
	
	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;

}
