package project.rpg_notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import project.rpg_notes.domain.Note;
import project.rpg_notes.domain.NoteRepository;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.PlaceRepository;

@Controller
public class NoteController {
	
	private NoteRepository noteRepository;
	private NpcRepository npcRepository;
	private PlaceRepository placeRepository;

	public NoteController(NoteRepository noteRepository, NpcRepository npcRepository, PlaceRepository placeRepository) {
		this.noteRepository = noteRepository;
		this.npcRepository = npcRepository;
		this.placeRepository = placeRepository;
	}


	//All about notes
	//Notes can be added to npcs and places
	
	//1. Add new Note
	@GetMapping("/note/addnote")
	public String addNote(Model model) {
		System.out.println("Add new note");
		model.addAttribute("note", new Note());
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("npcs", npcRepository.findAll());
		return "note/addNote";
	}
	
	//2. Save new Note
	@PostMapping("/note/savenote")
	public String saveNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Adding new note failed");
			model.addAttribute("note", note);
			model.addAttribute("places", placeRepository.findAll());
			model.addAttribute("npcs", npcRepository.findAll());
			return "note/addNote";
		}
		System.out.println("New note saved: " + note);
		noteRepository.save(note);
		return "redirect:/npc/npclist";
	}
	

}
