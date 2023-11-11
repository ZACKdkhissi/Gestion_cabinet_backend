package com.example.Gestion_cabinet_backend;

import com.example.Gestion_cabinet_backend.models.Authority;
import com.example.Gestion_cabinet_backend.models.User;
import com.example.Gestion_cabinet_backend.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GestionCabinetBackendApplication {

	//
	/*@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userDetailsRepository;*/

	public static void main(String[] args) {
		SpringApplication.run(GestionCabinetBackendApplication.class, args);
	}

	/*@PostConstruct
	protected void init() {

		List<Authority> authorityList=new ArrayList<>();

		//authorityList.add(createAuthority("USER"));
		authorityList.add(createAuthority("ADMIN"));

		User user=new User();

		user.setUserName("ADMIN");


		user.setPassword(passwordEncoder.encode("ADMIN"));
		user.setEnabled(true);
		user.setAuthorities(authorityList);

		userDetailsRepository.save(user);



	}

	private Authority createAuthority(String roleCode) {
		Authority authority=new Authority();
		authority.setRoleCode(roleCode);
		return authority;
	}*/

}
