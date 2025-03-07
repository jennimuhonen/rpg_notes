package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.Place;
import project.rpg_notes.domain.PlaceRepository;

@Controller
public class KeywController {

	private KeywRepository keywordRepository;
	private NpcRepository npcRepository;
	private PlaceRepository placeRepository;

	public KeywController(KeywRepository keywordRepository, NpcRepository npcRepository,
			PlaceRepository placeRepository) {
		this.keywordRepository = keywordRepository;
		this.npcRepository = npcRepository;
		this.placeRepository = placeRepository;
	}

	// All about Keywords (Keyw)
	// It's Keyw because Keyword was problematic
	// Keywords can be added to NPCs and Places

	// 1. List of Keywords
	@GetMapping("/keyword/keywordlist")
	public String showKeywordList(Model model) {
		System.out.println("List of Keywords");
		model.addAttribute("keyword", keywordRepository.findAll());
		return "keyword/keywordList";
	}

	// 2. Add new Keyword
	@GetMapping("/keyword/addkeyword")
	public String addKeyword(Model model) {
		System.out.println("Add new keyword");
		model.addAttribute("keyword", new Keyw());
		return "keyword/addKeyword";
	}

	// 3. Save new Keyword
	@PostMapping("keyword/savekeyword")
	public String saveKeyword(@Valid @ModelAttribute("keyword") Keyw keyword, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Adding new keyword failed");
			model.addAttribute("keyword", keyword);
			return "keyword/addKeyword";
		}

		// Check if there is another keyword with same name
		String keywordName = keyword.getKeywordName();
		List<Keyw> keywordList = new ArrayList<>();
		keywordList.addAll(keywordRepository.findByKeywordNameIgnoreCase(keywordName));
		if (!keywordList.isEmpty()) {
			System.out.println("Adding new keyword failed");
			model.addAttribute("errorMessage", "Keyword already exists"); // Ratkaisu virheviestin lisäämiseksi saatu
																			// ChatGPT:ltä
			model.addAttribute("keyword", keyword);
			return "keyword/addKeyword";
		}

		System.out.println("New keyword saverd: " + keyword);
		keywordRepository.save(keyword);
		return "redirect:/keyword/keywordlist";
	}

	// 4. Edit Keyword
	@GetMapping(value = "keyword/edit/{id}")
	public String editKeyword(@PathVariable("id") Long keywordId, Model model) {
		System.out.println("Ready to edit keywordId " + keywordId);
		Keyw keyword = keywordRepository.findById(keywordId).orElseThrow();
		model.addAttribute("keyword", keyword);
		return "keyword/editKeyword";
	}

	// 5. Save edited Keyword
	@PostMapping("/keyword/saveeditedkeyword")
	public String saveEditedKeyword(@Valid @ModelAttribute("keyword") Keyw keyword, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Failed to edit keyword");
			model.addAttribute("keyword", keyword);
			return "keyword/editKeyword";
		}

		// Check if there is another keyword with same name
		String keywordName = keyword.getKeywordName();
		List<Keyw> keywordList = new ArrayList<>();
		keywordList.addAll(keywordRepository.findByKeywordNameIgnoreCase(keywordName));
		if (!keywordList.isEmpty()) {
			System.out.println("Adding new keyword failed");
			model.addAttribute("errorMessage", "Keyword already exists"); // Ratkaisu virheviestin lisäämiseksi saatu ChatGPT:ltä
			model.addAttribute("keyword", keyword);
			return "keyword/addKeyword";
		}
		
		System.out.println("Edited keyword saved: " + keyword);
		keywordRepository.save(keyword);
		return "redirect:/keyword/" + keyword.getKeywordId();
	}

	// 6. Delete Keyword
	@GetMapping(value = "/keyword/delete/{id}")
	public String deleteKeyword(@PathVariable("id") Long keywordId) {

		Keyw keyword = keywordRepository.findById(keywordId).orElseThrow();

		// Check if some Npc has the keyword and delete it first from there
		List<Npc> npcs = keyword.getNpcs();
		if (!npcs.isEmpty()) {
			System.out.println("---Removing from npcs---");
			for (int i = 0; i < npcs.size(); i++) {
				Long npcId = npcs.get(i).getNpcId();
				Npc npc = npcRepository.findById(npcId).orElseThrow();
				npc.getKeywords().remove(keyword);
				System.out.println(i + ". Remove " + keyword.getKeywordName() + " from npc " + npc.getNpcName());
			}
		}

		// Check if some Place has the keyword and delete it first from there
		List<Place> places = keyword.getPlaces();
		if (!places.isEmpty()) {
			System.out.println("---Removing from places---");
			for (int i = 0; i < places.size(); i++) {
				Long placeId = places.get(i).getPlaceId();
				Place place = placeRepository.findById(placeId).orElseThrow();
				place.getKeywords().remove(keyword);
				System.out.println("Remove " + keyword.getKeywordName() + " from place " + place.getPlaceName());
			}
		}

		System.out.println("Deleted keywordId " + keywordId);
		keywordRepository.deleteById(keywordId);
		return "redirect:/keyword/keywordlist";
	}

	// 7. Get all Keyword info
	@GetMapping(value = "keyword/{id}")
	public String getKeywordInfo(@PathVariable("id") long keywordId, Model model) {
		System.out.println("Get information about keyword with id " + keywordId);
		Keyw keyword = keywordRepository.findById(keywordId).orElseThrow();
		model.addAttribute("keyword", keyword);
		return "keyword/keywordInfo";
	}

}
