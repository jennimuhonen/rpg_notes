package project.rpg_notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RpgNotesController {

	@GetMapping({"/", "/index"})
		public String home() {
			System.out.println("Open homepage.");
			return "index";
		}
	
	@RequestMapping(value="/login")
	public String login() {
		System.out.println("Open login page");
		return "login";
	}

}
