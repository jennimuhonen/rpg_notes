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
import project.rpg_notes.domain.Note;
import project.rpg_notes.domain.NoteRepository;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.Place;
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


	//All about Notes
	//Notes can be added to Npcs and Places
	//There are also NPC / Place spesific add Notes under Npc / Place Controller
	
	//1. Add new Note
	@GetMapping("/note/addnote")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addNote(Model model) {
		System.out.println("Add new note");
		model.addAttribute("note", new Note());
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("npcs", npcRepository.findAll());
		return "note/addNote";
	}
	
	//2. Save new Note
	@PostMapping("/note/savenote")
	@PreAuthorize("hasAuthority('ADMIN')")
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
		if(note.getNpc()==null) {
			Long id=note.getPlace().getPlaceId();
			return "redirect:/place/"+id;
		} else {
			Long id=note.getNpc().getNpcId();
			return "redirect:/npc/"+id;
		}
	}
	
	//3. Edit Note
	@GetMapping(value="note/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editNote(@PathVariable("id") Long noteId, Model model) {
		System.out.println("Edit note");
		model.addAttribute("note", noteRepository.findById(noteId).orElse(null)); //Sivulle lisätyn delete-nappulan kanssa oli ongelmia, ChatGPT kehotti lisäämään orElse(null), joka ratkaisi ongelman. 
		model.addAttribute("places", placeRepository.findAll());
		model.addAttribute("npcs", npcRepository.findAll());
		System.out.println("Open editpage of note: " + noteId);
		return "note/editNote";
	}
	
	//4. Save edited Note
	@PostMapping("/note/saveeditednote")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEditedNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Failed to edit Note: "+ note);
			model.addAttribute("note", note);
			model.addAttribute("places", placeRepository.findAll());
			model.addAttribute("npcs", npcRepository.findAll());
			return "note/editNote";
		}
		System.out.println("Edited Note saved");
		noteRepository.save(note);
		if(note.getNpc()==null) {
			Long id=note.getPlace().getPlaceId();
			return "redirect:/place/"+id;
		} else {
			Long id=note.getNpc().getNpcId();
			return "redirect:/npc/"+id;
		}
	}
	
	//5. Delete Note
	@GetMapping(value="/note/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteNote(@PathVariable("id") Long noteId, Model model) {
		Note note = noteRepository.findById(noteId).orElse(null);
		Npc npc = note.getNpc();
		Place place = note.getPlace();
		System.out.println("Delete note with id:" + noteId);
		noteRepository.deleteById(noteId);
		if(npc==null) {
			Long id=place.getPlaceId();
			return "redirect:/place/"+id;
		} else {
			Long id=npc.getNpcId();
			return "redirect:/npc/"+id;
		}
	}

}
