package com.genill.restApi;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.genill.userProfile.profile.ProfilePicture;
import com.genill.userProfile.profile.ProfileRepository;
import com.genill.userProfile.profile.ProfileService;
import com.genill.users.Users;

@CrossOrigin()
@RestController
@RequestMapping("genill/api")
public class UserProfileApi {
	
	@Autowired 
	private ProfileService profileService;
	
	@Autowired
	private ProfileRepository repo;
	
	@GetMapping("{username}/checkusername")
	public ResponseEntity<String> checkUserExist(@PathVariable String username) {
		try {
		Users user = profileService.checkUsernameExist(username);
		if(user != null) {
			
			return new ResponseEntity<>("Username already Exist", HttpStatus.FORBIDDEN);
		}
		} catch(NullPointerException e) {
		
	}
		return new ResponseEntity<>("Username is available to register", HttpStatus.OK);
	}
	
	@GetMapping("{email}/checkemail")
	public ResponseEntity<String> checkUserEmailExist(@PathVariable String email) {
		try {
			if (!email.contains("@")) {
				return new ResponseEntity<>("Please Enter Valid Email", HttpStatus.BAD_REQUEST);
			} else if (profileService.checkUserByEmail(email) != null) {
				return new ResponseEntity<>("Email already Exist, Please login Using your email Address", HttpStatus.FORBIDDEN);
			}
		} catch(NullPointerException e) {
			
		}
		return new ResponseEntity<>("Email is available to register", HttpStatus.OK);
		
	}
	
	@GetMapping("/username/{username}")
	public Object usersCheck(@PathVariable String username) {
	
		Users user = profileService.checkUsernameExist(username);
		
	return repo.findById(username).map(x -> {
		x.setProfilePicture("hello");
		return repo.save(x);
	});
//	return userMap.mapToUser(user);
	}
	
	@PostMapping(path = "/uploadprofileimage")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadProfilePicture(@RequestParam("image") MultipartFile Imagefile,
			@RequestParam("username") String username) throws IOException {
		
		String profileImageName = Imagefile.getOriginalFilename();

		ProfilePicture profileImage = new ProfilePicture();

		String fileFolderPath = "/Users/anishniroula/Documents/Development/DevGenill/genillFrontend/public/assets/Userpic/" + username + "/ProfilePictures/";
		File filePath = new File(fileFolderPath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}

		File file = new File(fileFolderPath, profileImageName);
		int i = 0;
		while(file.exists()) {
			file = new File(fileFolderPath, Imagefile.getOriginalFilename().replace(".", i+"."));
			profileImageName = Imagefile.getOriginalFilename().replace(".", i+".");
			i++;
		}
		file.createNewFile();
		try {
			profileImage.setProfileImageName(profileImageName);
			profileImage.setProfileImage(Imagefile);
			profileImage.getProfileImage().transferTo(file);

			profileService.saveProfileImage(profileImage, username);
			return profileImage.getProfileImageName();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return profileImage.getProfileImageName();
	}
	

}
