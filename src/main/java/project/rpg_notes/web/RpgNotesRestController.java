package project.rpg_notes.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//Add new NPC
	@PostMapping("npcs")
	Npc newNpc(@RequestBody Npc newNpc ) {
		return npcRepository.save(newNpc);
	}
	
	//Edit NPC
	@PutMapping("/npcs/{id}")
	Npc editNpc(@RequestBody Npc editedNpc, @PathVariable Long id) {
		editedNpc.setNpcId(id);
		return npcRepository.save(editedNpc);
	}
	
	//Delete NPC
	@DeleteMapping("/npcs/{id}")
	public Iterable<Npc> deleteNpc(@PathVariable Long id){
		npcRepository.deleteById(id);
		return npcRepository.findAll();
	}
	
	//Find NPC from certain Place
	@GetMapping("/npcs/place/{placeName}")
	List<Npc> getNpcByPlace(@PathVariable String placeName) {
		return npcRepository.findByPlacePlaceName(placeName);
	}

}
