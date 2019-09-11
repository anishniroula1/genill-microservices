package com.genill.user.profile;

import com.genill.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Users, String> {

	public Users findByUsername(String username);
	
	public Users findByEmail(String email);
	
}
