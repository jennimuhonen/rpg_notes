package project.rpg_notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface KeywRepository extends CrudRepository<Keyw, Long> {
	
	List<Keyw> findByKeywordName(String keyword);
	List<Keyw> findByKeywordNameIgnoreCase(String keyword); //source https://stackoverflow.com/questions/22573428/case-insensitive-query-with-spring-crudrepository

}
