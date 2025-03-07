package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.Note;
import project.rpg_notes.domain.NoteRepository;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.PlaceRepository;

@Controller
public class NpcController {

	private NpcRepository npcRepository;
	private PlaceRepository placeRepository;
	private KeywRepository keywordRepository;
	private NoteRepository noteRepository;

	public NpcController(NpcRepository npcRepository, PlaceRepository placeRepository,
			KeywRepository keywordRepository, NoteRepository noteRepository) {
		this.npcRepository = npcRepository;
		this.placeRepository = placeRepository;
		this.keywordRepository = keywordRepository;
		this.noteRepository = noteRepository;
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
			model.addAttribute("npc", npc);
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editNpc(@PathVariable("id") Long npcId, Model model) {
		Npc npc = npcRepository.findById(npcId).orElseThrow();
		model.addAttribute("npc", npc);
		model.addAttribute("places", placeRepository.findAll());
		System.out.println("Ready to edit npcId " + npcId);
		return "npc/editNpc";
	}

	// 5. Save edited NPC + error handling
	@PostMapping("/npc/saveeditednpc")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditedNpc(@Valid @ModelAttribute("npc") Npc npc, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("npc", npc);
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
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteNpc(@PathVariable("id") Long npcId) {
		npcRepository.deleteById(npcId);
		System.out.println("Deleted npcId " + npcId);
		return "redirect:/npc/npclist";
	}
	
	// 7. Get all NPC info
	@GetMapping(value = "npc/{id}")
	public String getNpcInfo(@PathVariable("id") Long npcId, Model model) {
		npcRepository.findById(npcId).ifPresent(npc -> model.addAttribute("npc", npc)); //ChatGPT neuvoi muuttamaan tähän muotoon, että toimii.
		//Npc npc = npcRepository.findById(npcId).orElse(null); //testilisäys
		//System.out.println(npc.getKeywords());
		System.out.println("Get information about npc with id " + npcId);
		return "npc/npcInfo";
	}
	
	// 8. Add Keyword to NPC
	@GetMapping (value="/npc/addkeyword/{id}")
	public String addKeywordToNpc(@PathVariable("id") Long npcId, Model model) {
		Npc npc = npcRepository.findById(npcId).orElseThrow(); //ChatGPT:n ratkaisu kaatumisongelmaan, jonka paikkatieto html:ssä aiheutti.
	    model.addAttribute("npc", npc);
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("keywords", keywordRepository.findAll());
		System.out.println("Ready to add Keyword");
		return "npc/editNpcKeywords";
	}
	
	// 9. Save Keyword
	
	/*@PostMapping("/npc/savekeyword")
	public String saveKeywordsToNpc(@ModelAttribute("npc") Npc npc, Model model) {
		System.out.println("Keyword tallennettu npc:lle");
		System.out.println("Testi:"+npcRepository.findByNpcName("Hiljainen Metsästäjä").get(0).getKeywords());
		npcRepository.save(npc);
		return "redirect:/npc/"+npc.getNpcId();
	}*/
	
	//Alla oleva ChatGPT:n versio toimii, yllä oleva oma viritelmä ei.
	//Jotta alla oleva toimi, piti Keywordien ja Npc:iden ManyToMany-suhde siirtää Npc:n puolelle hallinnoitavaksi.
	//Palaa miettimään tätä ja yllä olevaa. :)
	@PostMapping("/npc/savekeyword")
	public String saveKeywordsToNpc(@RequestParam("npcId") Long npcId, 
	                                @RequestParam("keywordId") List<Long> keywordIds) {
	    System.out.println("Keyword tallennettu NPC:lle");

	    // Haetaan olemassa oleva NPC
	    Npc npc = npcRepository.findById(npcId).orElse(null);
	    if (npc != null) {
	    	List<Keyw> selectedKeywords = new ArrayList<>();
	    	keywordRepository.findAllById(keywordIds).forEach(selectedKeywords::add);
	        npc.setKeywords(selectedKeywords); // Lisätään avainsanat NPC:lle
	        
	        npcRepository.save(npc); // Tallennetaan muutokset
	        System.out.println("Tallennetut avainsanat: " + npc.getKeywords());
	    } else {
	        System.out.println("Virhe: NPC:tä ei löytynyt!");
	    }

	    return "redirect:/npc/" + npcId;
	}
	
	//Add new keyword, edit and delete it are under KeywController
	
	// 10. Add new Note
	@GetMapping(value="npc/addnpcnote/{id}")
	public String addNpcNote(@PathVariable("id") Long npcId, Model model) {
		System.out.println("Add new note to NPC");
		Npc npc = npcRepository.findById(npcId).orElseThrow();
		
		Note note = new Note();
		note.setNpc(npc);
		//note.setPlace(null);
		
		model.addAttribute("note", note);
		model.addAttribute("npc", npc);
		return "npc/addNpcNote";
	}
	
	// 11. Save Note
	@PostMapping("/npc/savenpcnote")
	public String saveNpcNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Adding note failed");
			model.addAttribute("note", note);
			Npc npc = note.getNpc();
			model.addAttribute("npc", npc);
			return "npc/addNpcNote";
		}
		noteRepository.save(note);
		System.out.println("New note saved: " + note);
		Long id = note.getNpc().getNpcId();
		return "redirect:/npc/"+id;
	}
	
	// 12. Edit and Delete Note -> these are under NoteController
	
}
