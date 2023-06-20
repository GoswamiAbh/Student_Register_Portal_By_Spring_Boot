package com.abhi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "AIT_ENQURIRY_STATUS")
public class EnqStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;

}
