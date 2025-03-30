package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import project.rpg_notes.domain.AppUser;
import project.rpg_notes.domain.AppUserRepository;
import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.SignUpForm;

@Controller
public class UserController {
	
	private AppUserRepository appUserRepository;

	public UserController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
	@RequestMapping(value="signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignUpForm());
		return "signup";
	}
	
	@PostMapping("saveuser")
	public String save(@Valid @ModelAttribute("signupform") SignUpForm signUpForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(!bindingResult.hasErrors()) {
			if(signUpForm.getPassword().equals(signUpForm.getPasswordCheck())) {
				String pwd = signUpForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd=bc.encode(pwd);
				
				AppUser newUser = new AppUser();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signUpForm.getUsername());
				newUser.setRole("USER");
				if(appUserRepository.findByUsername(signUpForm.getUsername())==null) {
					appUserRepository.save(newUser);
				}
				else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			}
			else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Password does not match");
				return "signup";
			}
		}
		else {
			return "signup";
		}
		redirectAttributes.addFlashAttribute("successMessage", "Account was created successfully! You can now log in."); //Ratkaisu saatu ChatGPT:ltä
		return "redirect:/login";
	}
	
	@GetMapping("/admin/userlist")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String showUserList(Model model) {
		System.out.println("List of Users");
		model.addAttribute("user", appUserRepository.findAll());
		return "admin/userList";
	}
	
	@GetMapping(value = "/admin/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editUserRole(@PathVariable("id") Long id, Model model) {
		System.out.println("Ready to edit User " + id);
		AppUser user = appUserRepository.findById(id).orElseThrow();
		model.addAttribute("user", user);
		System.out.println("User: " + user);
		return "admin/editUserRole";
	}
	
	@PostMapping("/admin/saveediteduser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditedUser(@Valid @ModelAttribute("user") AppUser user, Model model) {
		appUserRepository.save(user);
		System.out.println("Edited User saved: " + user);
		return "redirect:/admin/userlist";
	}
	
	@GetMapping(value = "/admin/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("id") Long id) {
		appUserRepository.deleteById(id);
		System.out.println("Deleted appUserId " + id);
		return "redirect:/admin/userlist";
	}

}
