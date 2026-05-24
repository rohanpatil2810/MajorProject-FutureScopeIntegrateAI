package in.rohanIT.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rohanIT.ecommerce.dto.UserDto;
import in.rohanIT.ecommerce.dto.VendorDto;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.services.ProfileService;
import jakarta.mail.MessagingException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/updateUserProfile")
	public UserDetails updateUserProfile(@RequestBody UserDto userDto) throws MessagingException {
		return profileService.updateUser(userDto);
	}
	
	@PostMapping("/updateVendorProfile")
	public Vendors updateVendorProfile(@RequestBody VendorDto vendorDto) throws MessagingException {
		return profileService.updateVendor(vendorDto);
	}
}
