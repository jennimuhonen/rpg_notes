package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface KeywRepository extends CrudRepository<Keyw, Long> {
	
	List<Keyw> findByKeywordName(String keyword);

}
