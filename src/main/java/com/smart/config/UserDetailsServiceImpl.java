package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dou.UserRepository;
import com.smart.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService{
@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.getUserByUserName(username);
		if(user==null) {
			System.out.println("not found");
			throw new UsernameNotFoundException("could Not Found!!");
		}
		System.out.print(user);
		CustomeUserDetails customeUserDetails=new CustomeUserDetails(user);
		return customeUserDetails;
	}

}
