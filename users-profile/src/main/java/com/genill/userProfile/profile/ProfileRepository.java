package com.genill.userProfile.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genill.users.Users;

public interface ProfileRepository extends JpaRepository<Users, String> {

	public Users findByUsername(String username);
	
	public Users findByEmail(String email);
	
}
