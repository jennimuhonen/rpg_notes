package project.rpg_notes.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;

@RestController
public class RpgNotesRestController {
	
private NpcRepository npcRepository;
	
	public RpgNotesRestController(NpcRepository npcRepository) {
		this.npcRepository = npcRepository;
	}
	
	//NPC
	//Get all NPCs
	@GetMapping("/npcs")
	public Iterable<Npc> getNpcs() {
		System.out.println("Getting NPCs.");
		return npcRepository.findAll();
	}
	
	//Get one NPC with id
	@GetMapping("/npcs/{id}")
	Optional<Npc> getNpcWithId(@PathVariable Long id) {
		System.out.println("Get NpcId " + id);
		return npcRepository.findById(id);
	}

}
