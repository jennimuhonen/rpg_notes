package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.Comparator;
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
	
	// Comments in Finnish are for myself to help remember what I have done and why or source information.
	// Also I have saved in comments some versions I got from ChatGPT for learning purposes.

	
	// All about NPCs (NPC = non player character)
	
	// List NPCs
	@GetMapping("/npc/npclist")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String showNpcList(Model model) {
		System.out.println("List of NPCs");
		model.addAttribute("npc", npcRepository.findAll());
		model.addAttribute("places", placeRepository.findAll());
		return "npc/npcList";
	}

	// Add new NPC
	@GetMapping("/npc/addnpc")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String addNpc(Model model) {
		System.out.println("You can now add NPC.");
		model.addAttribute("npc", new Npc());
		model.addAttribute("places", placeRepository.findAll());
		return "npc/addNpc";
	}

	// Save the new NPC + error handling
	@PostMapping("/npc/savenpc")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveNpc(@Valid @ModelAttribute("npc") Npc npc, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("npc", npc);
			model.addAttribute("places", placeRepository.findAll());
			System.out.println("Saving new NPC failed: " + npc);
			return "npc/addNpc";
		}
		npcRepository.save(npc);
		System.out.println("New NPC saved: " + npc);
		return "redirect:/npc/"+npc.getNpcId();
	}

	// Edit NPC
	@GetMapping(value = "/npc/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editNpc(@PathVariable("id") Long npcId, Model model) {
		Npc npc = npcRepository.findById(npcId).orElseThrow();
		model.addAttribute("npc", npc);
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("keywords", keywordRepository.findAll());
		System.out.println("Ready to edit npcId " + npcId);
		return "npc/editNpc";
	}

	// Save edited NPC + error handling
	@PostMapping("/npc/saveeditednpc")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveEditedNpc(@Valid @ModelAttribute("npc") Npc npc, @RequestParam(value="keywordId", required=false) List<Long> keywordIds, BindingResult bindingResult, Model model) {
		
		List<Keyw> selectedKeywords = new ArrayList<>();
		Keyw keyword;
		if (keywordIds!=null) {
			for (int i = 0; i < keywordIds.size(); i++) {
			keyword = keywordRepository.findById(keywordIds.get(i)).orElseThrow();
			selectedKeywords.add(keyword);
			}
		}
		npc.setKeywords(selectedKeywords);
		
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

	// Delete NPC
	@GetMapping(value = "/npc/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String deleteNpc(@PathVariable("id") Long npcId) {
		npcRepository.deleteById(npcId);
		System.out.println("Deleted npcId " + npcId);
		return "redirect:/npc/npclist";
	}
	
	// Get all NPC info
	@GetMapping(value = "npc/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String getNpcInfo(@PathVariable("id") Long npcId, Model model) {
		//npcRepository.findById(npcId).ifPresent(npc -> model.addAttribute("npc", npc)); //ChatGPT:n antama ratkaisu, mutta yhdenmukaistettu muihin ratkaisuihin.
		Npc npc = npcRepository.findById(npcId).orElseThrow();
		model.addAttribute("npc", npc);
		System.out.println("Get information about npc with id " + npcId);
		return "npc/npcInfo";
	}
	
	// Order NPC's alphabetically by NPC
	@GetMapping(value ="npc/orderbynpc")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String orderNpcsByNpc(Model model) {
		System.out.println("Ordered list of NPCs");
		
		//Getting the list
		Iterable<Npc> npcsIterable = npcRepository.findAll(); //Muokattu ChatGPT:n avustuksella alla olevaa
		List<Npc> npcs = new ArrayList<>();
		npcsIterable.forEach(npcs::add);
			
		//Ordering it alphabetically
		npcs.sort(Comparator.comparing(npc -> npc.getNpcName().toLowerCase()));
		
		model.addAttribute("npc", npcs);
		model.addAttribute("places", placeRepository.findAll());
		
		return "npc/npclist";
	}
	
	@GetMapping(value ="npc/orderbyplace")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String orderNpcsByPlace(Model model) {
		System.out.println("Ordered list of NPCs");
		
		Iterable<Npc> npcsIterable = npcRepository.findAll();
		List<Npc> npcs = new ArrayList<>();
		npcsIterable.forEach(npcs::add);
		
		//Muotoilu ChatGPT:n työtä. Huomioidaan myös se, jos arvo on null.
		//(ehto) ? (jos tosi, tee tämä) : (jos epätosi, tee tämä)
		npcs.sort(Comparator.comparing(npc -> npc.getPlace() != null ? npc.getPlace().getPlaceName().toLowerCase() : ""));
		
		model.addAttribute("npc", npcs);
		model.addAttribute("places", placeRepository.findAll());
		
		return "npc/npclist";
	}
	
	//--- NPC + KEYWORDS ---
	
	// Edit Keywords that NPC has
	@GetMapping (value="/npc/editkeywords/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editKeywordsForNpc(@PathVariable("id") Long npcId, Model model) {
		Npc npc = npcRepository.findById(npcId).orElseThrow(); //ChatGPT:n ratkaisu kaatumisongelmaan, jonka paikkatieto html:ssä aiheutti.
	    model.addAttribute("npc", npc);
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("keywords", keywordRepository.findAll());
		System.out.println("Ready to add Keyword");
		return "npc/editNpcKeywords";
	}
	
	// Save Keywords
	
	//Tehty ChatGPT:n ratkaisun (alla) pohjalta.
	//Tässä käytetään @RequestParam, koska sen avulla voidaan lukea tietoa lomakkeelta. (Haaste liittyy ilmeisesti ManyToMany-suhteeseen ja siihen, kuinka se lomakkeelta luetaan.)
	//Edelliseen jatkona: Lomake lähettää keywordId:n ei keywords-listaa. Jälkimmäinen tarvittaisiin, jotta @ModelAttribute:lla voitaisiin lukea suoraviivaisesti vain npc.
	//KeywordId:lle on määritelty, ettei se ole pakollinen (required=false), koska muuten ohjelma kaatuu, jos käyttäjä ei valitse yhtään keywordia.
	@PostMapping("npc/savekeywords")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String SaveKeywordsToNpc(@RequestParam("npcId") Long npcId, @RequestParam(value="keywordId", required=false) List<Long> keywordIds) {
		Npc npc = npcRepository.findById(npcId).orElseThrow();
		List<Keyw> selectedKeywords = new ArrayList<>();
		Keyw keyword;
		if (keywordIds!=null) {
			for (int i = 0; i < keywordIds.size(); i++) {
				keyword = keywordRepository.findById(keywordIds.get(i)).orElseThrow();
				selectedKeywords.add(keyword);
			}
		}
		npc.setKeywords(selectedKeywords);
		npcRepository.save(npc);
		System.out.println("Seuraavat avainsanat tallennettu: " + npc.getKeywords());
		return "redirect:/npc/" + npcId;
	}
	
	//ChatGPT:n versio
	//Jotta alla oleva toimi, piti Keywordien ja Npc:iden ManyToMany-suhde siirtää Npc:n puolelle hallinnoitavaksi.
	/*@PostMapping("/npc/savekeywords")
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
	}*/
	
	
	//+++ Add new keyword, edit and delete it are under KeywController +++
	
	
	//--- NPC + NOTES ---
	
	// Add new Note
	@GetMapping(value="npc/addnpcnote/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
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
	
	// Save Note
	@PostMapping("/npc/savenpcnote")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
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
	
	//+++ Edit and Delete Note are under NoteController +++
	
}
