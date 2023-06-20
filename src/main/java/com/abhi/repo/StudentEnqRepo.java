package com.abhi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>{

}
