package fi.xamk.tilavaraus.web;

import fi.xamk.tilavaraus.domain.MyUserDetails;
import fi.xamk.tilavaraus.domain.User;
import fi.xamk.tilavaraus.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "user/login";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "user/register";
	}

	@GetMapping("/profile")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String showProfile(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		model.addAttribute("user", userDetails.getUser());
		return "user/profile";
	}

	@PostMapping("/profile")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public String updateProfile(@AuthenticationPrincipal MyUserDetails userDetails,
	                            @ModelAttribute("user") @Valid User updatedUser,
	                            Model model) {
		User currentUser = userDetails.getUser();
		currentUser.setName(updatedUser.getName());
		currentUser.setAddress(updatedUser.getAddress());
		currentUser.setZip(updatedUser.getZip());
		currentUser.setCity(updatedUser.getCity());
		model.addAttribute("user", userRepository.save(currentUser));
		return "user/profile";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			bindingResult.rejectValue("email", "emailAlreadyUsed", "Email already in use!");
		}

		if (bindingResult.hasErrors()) {
			return "user/register";
		}

		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "redirect:/login";
	}


}
