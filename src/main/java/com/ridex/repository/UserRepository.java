package com.ridex.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ridex.entity.UserEntity;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	@Transactional(readOnly = true)
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
	
	Optional<UserEntity> findById(int userId);
	


}