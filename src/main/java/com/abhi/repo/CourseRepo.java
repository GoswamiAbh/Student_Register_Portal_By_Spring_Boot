package com.abhi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
