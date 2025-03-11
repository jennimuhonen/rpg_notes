package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NpcRepository extends CrudRepository<Npc, Long> {
	
	List<Npc> findByNpcName(String npcName);
	List<Npc> findByPlacePlaceName(String placeName);

}
