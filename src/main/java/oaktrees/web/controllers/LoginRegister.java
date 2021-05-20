package oaktrees.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import oaktrees.data.User;
import oaktrees.db.DbWorker;
import oaktrees.db.services.UserService;
import oaktrees.security.Role;

@Controller
public class LoginRegister {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private DbWorker dbWorker;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String register() {
		return "logreg/register";
	}
	@GetMapping("/success_registration")
	public String successRegistration() {
		return "logreg/success_registration";
	}
	
	@PostMapping("/register_processing")
	public ModelAndView registerProcessing(
			@RequestParam("name") String name,
			@RequestParam("login") String login,
			@RequestParam("password") String password
			) {
		
		
		String redirectWay = "/register";
		
		User user = new User(name, login, passwordEncoder.encode(password));
		
		if(!dbWorker.isUserExists(login)) {
			user.setRole(Role.USER);
			//dbWorker.insert(user);
			userService.save(user);
			redirectWay = "/success_registration";
		};
		
		return new ModelAndView("redirect:" + redirectWay);
	}
	
	@GetMapping("/login")
	public String login() {
		return "logreg/login";
	}
	@PostMapping("/login_processing")
	public ModelAndView loginProcessing() {
		
		// checking for existing
		System.out.println("login processing!!");
		
		return new ModelAndView("redirect:" + "/onlyForLogined");
	}
	
	@GetMapping("/onlyForLogined")
	public String onlyForLogined() {
		return "logreg/onlyForLogined";
	}
	
	@GetMapping("/statistics")
	@PreAuthorize("hasAuthority('statistics_p')")
	public String statistics() {
		return "logreg/statistics";
	}

}
