package com.abhi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {

	public UserDtlsEntity findByEmail(String email);

	public UserDtlsEntity findByEmailAndPwd(String Email, String pwd);
}
