package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place, Long> {
	
	List<Place> findByPlaceName(String placeName);

}
