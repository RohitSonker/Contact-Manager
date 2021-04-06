package com.smart.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.FileUploadHelper;



import com.smart.config.UserDetailsServiceImpl;
import com.smart.dou.ContactRepository;
import com.smart.dou.UserRepository;
@Controller
@RequestMapping("/user")
public class User_controller {
	@Autowired
	private FileUploadHelper fileUploadHelper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
@ModelAttribute
	public void userdetails(Model model,Principal principal) {
	User user=	userRepository.getUserByUserName(principal.getName());
		model.addAttribute("user",user);

		
	}
	@RequestMapping("/Dash")
	public String dash(Model model,Principal principal) {
	
		return "normal/dashboard";
	}
	@GetMapping("/user-contact")
	public String openaddContactForm(Model model) {
		model.addAttribute("contact",new Contact());
		return "normal/add_contact";
	}
	@PostMapping("/process-contact")
	public String process(@ModelAttribute Contact contact,@RequestParam("imageprofile") MultipartFile file,Principal principal){
		if(file.isEmpty()) {
			System.out.println("file is empty");
			contact.setImage("contact.png");
		}
		
		User user=	userRepository.getUserByUserName(principal.getName());
		
		System.out.println(file.getOriginalFilename());
		boolean f=fileUploadHelper.uploadFile(file);
		if(f) {
			System.out.println("Successfully uploaded");
			contact.setImage(file.getOriginalFilename());
		}
		contact.setUser(user);
		user.getContacts().add(contact);
		this.userRepository.save(user);
		System.out.println(contact);;
		return "normal/add_contact";
	}
	@GetMapping("/viewcontact/{page}")
	public String view(@PathVariable("page")Integer page,  Model model,Principal principal) {
	String UserName=principal.getName();
	User user=this.userRepository.getUserByUserName(UserName);
	//currentPage-page
	//ContactPage-page
	Pageable pageable=PageRequest.of(page, 5);
	
	Page<Contact> contacts=this.contactRepository.findContactByUser(user.getId(),pageable);
	model.addAttribute("currentPage", page);
	model.addAttribute("contact",contacts);
	model.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/contactview";
	}
	@GetMapping("/del/{cId}")
	private String del(@PathVariable("cId") Integer cid){
		
		Optional<Contact>contactoptional=this.contactRepository.findById(cid);
		Contact contact=contactoptional.get();
		this.contactRepository.delete(contact);
		return "redirect:/user/viewcontact/0";
	}
	@GetMapping("/userprofile")
	public String userprofile(Principal principal,Model model) {
		User user=	userRepository.getUserByUserName(principal.getName());
		model.addAttribute("user",user);
		return "normal/userprofile";
	}
	@GetMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Integer cId,Model model) {
		Optional<Contact>contactoptional=this.contactRepository.findById(cId);
		Contact contact=contactoptional.get();
		model.addAttribute("contact", contact);
		return "normal/contact_details";
	}
	//update contact
	
	@PostMapping("update-contact/{cId}")
	public String updateForm(@PathVariable("cId") Integer cid,Model model) {
		Contact contact=this.contactRepository.findById(cid).get();
		model.addAttribute("contact",contact);
			
		return "normal/updateform";
	}
	@PostMapping("/process-update")
	public String processupdate(@ModelAttribute Contact contact,Principal principal) {
		User user=this.userRepository.getUserByUserName(principal.getName());
		contact.setUser(user);	
		contact.setImage("contact.png");
		System.out.println("contact id");
	System.out.println(contact.getcId());
		
	this.contactRepository.save(contact);	
	return "redirect:/user/viewcontact/0";
	}
	
	

}
