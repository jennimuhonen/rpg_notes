package project.rpg_notes.web;

import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String search() {
		System.out.println("Open search");
		return "search";
	}
	
	@GetMapping("/makesearch")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String makeSearch() {
		System.out.println("Searching...");
		
		
		return "search";
	}
	
	@GetMapping("/searchnpc")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String searchNpc() {
		System.out.println("Searching Npc...");
		
		
		return "search";
	}

}
