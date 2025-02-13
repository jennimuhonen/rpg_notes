package project.rpg_notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;

@Controller
public class RpgNotesController {
	
	private NpcRepository npcRepository;
	
	public RpgNotesController(NpcRepository npcRepository) {
		this.npcRepository = npcRepository;
	}

	@GetMapping({"/", "/index"})
		public String home() {
			System.out.println("Open homepage.");
			return "index";
		}
	
	
	//Handle NPCs
	
	@GetMapping("/npclist")
	public String showNpcList(Model model) {
		System.out.println("List of NPCs");
		model.addAttribute("npcs", npcRepository.findAll());
		//model.addAttribute("xxx", xxxRepository.findAll());
		return "npcList";
	}
	
	@GetMapping("/addnpc")
	public String addNpc(Model model) {
		System.out.println("You can now add NPC.");
		model.addAttribute("npc", new Npc());
		//model.addAttribute("xxx", xxxRepository.findAll());
		return "addNpc";
	}

}
