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
import project.rpg_notes.domain.PlaceRepository;

@Controller
public class NpcController {

	private NpcRepository npcRepository;
	private PlaceRepository placeRepository;

	public NpcController(NpcRepository npcRepository, PlaceRepository placeRepository) {
		super();
		this.npcRepository = npcRepository;
		this.placeRepository = placeRepository;
	}



	// All about NPCs (NPC = non player character)

	// 1. List NPCs
	@GetMapping("/npc/npclist")
	public String showNpcList(Model model) {
		System.out.println("List of NPCs");
		model.addAttribute("npc", npcRepository.findAll());
		model.addAttribute("places", placeRepository.findAll());
		return "npc/npcList";
	}

	// 2. Add new NPC
	@GetMapping("/npc/addnpc")
	public String addNpc(Model model) {
		System.out.println("You can now add NPC.");
		model.addAttribute("npc", new Npc());
		model.addAttribute("places", placeRepository.findAll());
		return "npc/addNpc";
	}

	// 3. Save the new NPC + error handling
	@PostMapping("/npc/savenpc")
	public String saveNpc(@Valid @ModelAttribute("npc") Npc npc, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("addNpc", npc);
			model.addAttribute("places", placeRepository.findAll());
			System.out.println("Saving new NPC failed: " + npc);
			return "npc/addNpc";
		}
		npcRepository.save(npc);
		System.out.println("New NPC saved: " + npc);
		return "redirect:/npc/npclist";
	}

	// 4. Edit NPC
	@GetMapping(value = "/npc/edit/{id}")
	public String editNpc(@PathVariable("id") Long npcId, Model model) {
		model.addAttribute("editNpc", npcRepository.findById(npcId));
		model.addAttribute("places", placeRepository.findAll());
		System.out.println("Ready to edit npcId " + npcId);
		return "npc/editNpc";
	}

	// 5. Save edited NPC + error handling
	@PostMapping("/npc/saveeditednpc")
	public String saveEditedNpc(@Valid @ModelAttribute("editNpc") Npc npc, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("editNpc", npc);
			model.addAttribute("places", placeRepository.findAll());
			System.out.println("Failed to edit NPC: " + npc);
			return "npc/editNpc";
		}
		npcRepository.save(npc);
		System.out.println("Edited NPC saved: " + npc);
		return "redirect:/npc/"+npc.getNpcId();
	}

	// 6. Delete NPC
	@GetMapping(value = "/npc/delete/{id}")
	public String deleteNpc(@PathVariable("id") Long npcId) {
		npcRepository.deleteById(npcId);
		System.out.println("Deleted npcId " + npcId);
		return "redirect:/npc/npclist";
	}
	
	// 7. Get all NPC info
	@GetMapping(value = "npc/{id}")
	public String getNpcInfo(@PathVariable("id") Long npcId, Model model) {
		npcRepository.findById(npcId).ifPresent(npc -> model.addAttribute("npc", npc)); //ChatGPT neuvoi muuttamaan tähän muotoon, että toimii.
		System.out.println("Get information about npc with id " + npcId);
		return "npc/npcInfo";
	}

}
