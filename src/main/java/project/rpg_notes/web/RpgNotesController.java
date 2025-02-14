package project.rpg_notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
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
	
	
	//All about NPCs (NPC = non player character)
	
	//1. List NPCs
	@GetMapping("/npclist")
	public String showNpcList(Model model) {
		System.out.println("List of NPCs");
		model.addAttribute("npc", npcRepository.findAll());
		//model.addAttribute("xxx", xxxRepository.findAll());
		return "npcList";
	}
	
	//2. Add new NPC
	@GetMapping("/addnpc")
	public String addNpc(Model model) {
		System.out.println("You can now add NPC.");
		model.addAttribute("npc", new Npc());
		//model.addAttribute("xxx", xxxRepository.findAll());
		return "addNpc";
	}
	
	//3. Save the new NPC + error handling
	@PostMapping("/savenpc")
	public String saveNpc(@Valid @ModelAttribute("npc") Npc npc, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("addNpc", npc);
			//model.addAttribute("xxx", xxxRepository.findAll());
			System.out.println("Saving new NPC failed: " + npc);
			return "addNpc";
		}
		npcRepository.save(npc);
		System.out.println("New NPC saved: " + npc);
		return "redirect:/npclist";
	}
	
	//4. Edit NPC
	@GetMapping(value = "/edit/{id}")
	public String editNpc(@PathVariable("id") Long npcId, Model model) {
		model.addAttribute("editNpc", npcRepository.findById(npcId));
		//model.addAttribute("xxx", xxxRepository.findAll());
		System.out.println("Ready to edit npcId " + npcId);
		return "editNpc";
	}
	
	//5. Save edited NPC + error handling
	@PostMapping("/saveeditednpc")
	public String saveEditedNpc(@Valid @ModelAttribute("editNpc") Npc npc, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("editNpc", npc);
			//model.addAttribute("xxx", xxxRepository.findAll());
			System.out.println("Failed to edit NPC: " + npc);
			return "editNpc";
		}
		npcRepository.save(npc);
		System.out.println("Edited NPC saved: " + npc);
		return "redirect:/npclist";
	}
	
	//6. Delete NPC + are you sure
	@GetMapping(value = "/delete/{id}")
	public String deleteNpc(@PathVariable("id") Long npcId) {
		npcRepository.deleteById(npcId);
		System.out.println("Deleted npcId " + npcId);
		return "redirect:/npclist";
	}

}
