package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dou.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
@GetMapping("/")
public String home() {
	return "home";
}
@GetMapping("/about")
public String about() {
	return "about";
}
@GetMapping("/signup")
public String Signup(Model model) {
	model.addAttribute("user", new User()); 
	return "Signup";
}

@GetMapping("/reg")
public String customeLogin(Model model) {
return "Login";	
}

//controller for user
@PostMapping("/do-register")
public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue="flase")boolean agreement,Model model,HttpSession session) throws Exception 
{
	if(!agreement) {
		throw new Exception("You have Not Agreed term And Condition");
	}
	
	try {
		if(result1.hasErrors()) {
			model.addAttribute("user", user);
			System.out.println(result1+"hre");
			return "Signup";
		}
		user.setRole("ROLE_ADMIN");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result=this.userRepository.save(user);
		System.out.println(result);
		System.out.println(agreement);
		model.addAttribute("user", new User());
		session.setAttribute("message",new Message("Succesfully Register", "alert-success"));
		return "Signup";
	}
	catch(Exception e) {
		e.printStackTrace();
		
		model.addAttribute("user", new User());
		session.setAttribute("message", new Message("Something Went wrong"+e.getMessage(),"alert-danger"));
		return "Signup";
	}
	
	
	
}


}

//controller for login



