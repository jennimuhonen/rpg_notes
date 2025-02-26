package project.rpg_notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RpgNotesController {

	@GetMapping({"/", "/index"})
		public String home() {
			System.out.println("Open homepage.");
			return "index";
		}

}
