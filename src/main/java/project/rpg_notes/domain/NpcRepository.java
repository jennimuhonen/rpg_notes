package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//muutettu CrudRepositorysta JpaRepositoryksi Queryjen takia
public interface NpcRepository extends JpaRepository<Npc, Long> {
	
	List<Npc> findByNpcName(String npcName);
	
	List<Npc> findByPlacePlaceName(String placeName);
	
	@Query("SELECT n.npcName FROM Npc n WHERE n.npcName LIKE :keyword%")
	List<String> findNpcsBySearchTermInBeginning(@Param("keyword") String keyword);
	
	@Query("SELECT n.npcName FROM Npc n WHERE n.npcName LIKE %:keyword%")
	List<String> findNpcsContainingSearchTerm(@Param("keyword") String keyword);
}
