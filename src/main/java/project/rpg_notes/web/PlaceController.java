package project.rpg_notes.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.NoteRepository;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.Place;
import project.rpg_notes.domain.PlaceRepository;

@Controller
public class PlaceController {
	
	private PlaceRepository placeRepository;
	private NpcRepository npcRepository;
	private KeywRepository keywordRepository;
	private NoteRepository noteRepository;
	
	public PlaceController(PlaceRepository placeRepository, NpcRepository npcRepository,
			KeywRepository keywordRepository, NoteRepository noteRepository) {
		this.placeRepository = placeRepository;
		this.npcRepository = npcRepository;
		this.keywordRepository = keywordRepository;
		this.noteRepository = noteRepository;
	}


	// All about Places
	
	// 1. List Places
	@GetMapping("/place/placelist")
	public String showPlaceList(Model model) {
		System.out.println("List of Places");
		model.addAttribute("place", placeRepository.findAll());
		model.addAttribute("npc", npcRepository.findAll());
		return "place/placeList";
	}
	
	// 2. Add new Place
	@GetMapping("/place/addplace")
	public String addPlace(Model model) {
		System.out.println("Ready to add new Place.");
		model.addAttribute("place", new Place());
		return "place/addPlace";
	}
	
	// 3. Save new Place
	@PostMapping("/place/saveplace")
	public String savePlace(@Valid @ModelAttribute("place") Place place, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Saving new Place failed.");
			model.addAttribute("place", place);
			return "place/addPlace";
		}
		placeRepository.save(place);
		System.out.println("New NPC saved: " + place);
		return "redirect:/place/placelist";
	}
	
	// 4. Edit Place
	@GetMapping(value="/place/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editPlace(@PathVariable("id") Long placeId, Model model) {
		System.out.println("Ready to edit placeId " + placeId);
		Place place = placeRepository.findById(placeId).orElseThrow();
		model.addAttribute("place", place);
		return "place/editPlace";
	}
	
	// 5. Save edited Place
	@PostMapping("/place/saveeditedplace")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditedPlace(@Valid @ModelAttribute("place") Place place, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Failed to edit Place: " + place);
			model.addAttribute("place", place);
			return "place/editPlace";
		}
		placeRepository.save(place);
		System.out.println("Edited Place saved: " + place);
		return "redirect:/place/" + place.getPlaceId();
	}
	
	// 6. Delete Place
	@GetMapping(value="place/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deletePlace(@PathVariable("id") Long placeId) {
		placeRepository.deleteById(placeId);
		System.out.println("Deleted placeId" + placeId);
		return "redirect:/place/placelist";
	}
	
	// 7. Get all Place info
	@GetMapping(value="place/{id}")
	public String getPlaceInfo(@PathVariable("id") Long placeId, Model model) {
		System.out.println("Get information about Place whit id " + placeId);
		Place place = placeRepository.findById(placeId).orElseThrow();
		model.addAttribute("place", place);
		return "place/placeInfo";
	}
	
	//--- PLACE + KEYWORDS ---
	
	// 8. Edit Keywords that Place has
	@GetMapping(value="/place/editkeywords/{id}")
	public String editKeywordsForPlace(@PathVariable("id") Long placeId, Model model) {
		System.out.println("Ready to edit Keywords");
		Place place = placeRepository.findById(placeId).orElseThrow();
		model.addAttribute("place", place);
		model.addAttribute("keywords", keywordRepository.findAll());
		return "place/editPlaceKeywords";
	}
	
	// 9. Save Keywords
	
	
	
	// Add new keyword, edit and delete it are under KeywController
	
	
	//--- PLACE + NOTES ---
	
	// 10. Add new Note
	
	// 11. Save Note
	
	
	// Edit and Delete Note are under NoteController

}
