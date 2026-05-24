package in.rohanIT.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.rohanIT.ecommerce.dto.UserDto;
import in.rohanIT.ecommerce.entity.CustomerOrder;
import in.rohanIT.ecommerce.entity.UserDetails;
import in.rohanIT.ecommerce.services.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/getUser/{email}")
	public UserDetails getUser(@PathVariable String email) {
	    return userService.getUser(email);
	}
	
	@PostMapping("/registration")
	public String registerUser(@RequestBody UserDto userDto) {
		return userService.userRegistration(userDto);
	}
	
	@PostMapping("/login")
	public Map<String, Object> userLogin(@RequestBody UserDto userDto) {
		return userService.userLogin(userDto);
	}
	
	@PostMapping("/pwChange")
	public String changeUserPW(@RequestBody UserDto userDto) {
		return userService.userPwChange(userDto);
	}
	
	@PostMapping("/getMyOrders")
	public List<CustomerOrder> getMyOrders(@RequestBody UserDto userDto){
		return userService.getMyOrders(userDto);
	}
}

