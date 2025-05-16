package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GameItemRepository extends CrudRepository<GameItem, Long> {
	
	List<GameItem> findByItemName(String itemName);

}
