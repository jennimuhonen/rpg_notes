package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.NpcRepository;
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

	//All about Keywords (Keyw)
	//It's Keyw because Keyword was problematic
	//Keywords can be added to NPCs and Places
	
	//1. List of Keywords
	@GetMapping("/keyword/keywordlist")
	public String showKeywordList(Model model) {
		System.out.println("List of Keywords");
		model.addAttribute("keyword", keywordRepository.findAll());
		return "keyword/keywordList";
	}
	
	//2. Add new Keyword
	@GetMapping("/keyword/addkeyword")
	public String addKeyword(Model model) {
		System.out.println("Add new keyword");
		model.addAttribute("keyword", new Keyw());
		return "keyword/addKeyword";
	}
	
	//3. Save new Keyword
	@PostMapping("keyword/savekeyword")
	public String saveKeyword(@Valid @ModelAttribute("keyword") Keyw keyword, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Adding new keyword failed");
			model.addAttribute("keyword", keyword);
			return "keyword/addKeyword";
		}
		String keywordName=keyword.getKeywordName();
		List<Keyw> keywordList = new ArrayList<>();
		keywordList.addAll(keywordRepository.findByKeywordName(keywordName));
		if(!keywordList.isEmpty()) {
			System.out.println("Adding new keyword failed");
			model.addAttribute("errorMessage", "Keyword already exists"); //Ratkaisu virheviestin lisäämiseksi saatu ChatGPT:ltä
			model.addAttribute("keyword", keyword);
			return "keyword/addKeyword";
		}
		System.out.println("New keyword saverd: " + keyword);
		keywordRepository.save(keyword);
		return"redirect:/keyword/keywordlist";
	}
	
	//4. Edit Keyword
	
	//5. Save edited Keyword
	
	//6. Delete Keyword
	
	//7. Get all Keyword info
	

}
