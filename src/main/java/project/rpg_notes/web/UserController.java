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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import project.rpg_notes.domain.AppUser;
import project.rpg_notes.domain.AppUserRepository;
import project.rpg_notes.domain.SignUpForm;

@Controller
public class UserController {
	
	private AppUserRepository appUserRepository;

	public UserController(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
	//Sign up
	@RequestMapping(value="signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignUpForm());
		return "signup";
	}
	
	//Save user after sign up
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
	
	//Get userlist
	@GetMapping("/admin/userlist")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String showUserList(Model model) {
		System.out.println("List of Users");
		model.addAttribute("user", appUserRepository.findAll());
		return "admin/userList";
	}
	
	//Edit user's role
	@GetMapping(value = "/admin/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editUserRole(@PathVariable("id") Long id, Model model) {
		System.out.println("Ready to edit User " + id);
		AppUser user = appUserRepository.findById(id).orElseThrow();
		model.addAttribute("user", user);
		System.out.println("User: " + user);
		return "admin/editUserRole";
	}
	
	//Save edited user
	@PostMapping("/admin/saveediteduser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditedUser(@Valid @ModelAttribute("user") AppUser user, Model model, RedirectAttributes redirectAttributes) {
		String role = user.getRole();
		System.out.println("Save role "+role);
		List<AppUser> adminList = new ArrayList<>();
		adminList.addAll(appUserRepository.findByRole("ADMIN"));
		
		//For rahti just for safety
		if (user.getUsername().equals("admin")) {
			redirectAttributes.addFlashAttribute("failureMessage", "Sorry, you are not allowed to edit admin. :)");
			return "redirect:/admin/userlist";
		}
		
		//Save if user is ADMIN || Save if user is USER and there are ADMINs left
		if (role.equals("ADMIN") || role.equals("USER") && adminList.size()>1) {
			appUserRepository.save(user);
			System.out.println("Edited User saved: " + user);
			return "redirect:/admin/userlist";
		}
		
		//If were are here user was last ADMIN and can't become USER
		if (role.equals("USER")) {
			redirectAttributes.addFlashAttribute("failureMessage", "You can't change last admin to user.");
			return "redirect:/admin/userlist";
		}	
		
		//if the role is not correct
		else {
			redirectAttributes.addFlashAttribute("failureMessage", "The role needs to be ADMIN or USER.");
			Long id=user.getAppUserId();
			return "redirect:/admin/edit/"+id;
		}
	}
	
	//Delete user
	@GetMapping(value = "/admin/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		AppUser user = appUserRepository.findById(id).orElseThrow();
		String role = user.getRole();
		
		//For rahti just for safety
		if (user.getUsername().equals("admin")) {
			redirectAttributes.addFlashAttribute("failureMessage", "Sorry, you are not allowed to delete admin. :)");
			return "redirect:/admin/userlist";
		}
		
		//delete user if the role is USER
		if (role.equals("USER")) {
			appUserRepository.deleteById(id);
			System.out.println("Deleted appUserId " + id);
			return "redirect:/admin/userlist";
		}
		
		List<AppUser> userList = new ArrayList<>();
		userList.addAll(appUserRepository.findByRole(role));
		
		//check that user is not the only ADMIN
		if (userList.size()>1) {
			appUserRepository.deleteById(id);
			System.out.println("Deleted appUserId " + id);
			return "redirect:/admin/userlist";
		}
		else {
			redirectAttributes.addFlashAttribute("failureMessage", "You can't delete last admin.");
			return "redirect:/admin/userlist";
		}
	}

}
