package project.rpg_notes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.Place;
import project.rpg_notes.domain.PlaceRepository;

@DataJpaTest
public class RpgNotesRepositoryTest {
	
	@Autowired
	private NpcRepository npcRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private KeywRepository keywordRepository;
	
	//npc-taulussa on vähintään 1 rivi
	@Test
	public void thereIsAtLeastOneNpc() {
		assertThat(npcRepository.count()).isGreaterThan(0);
	}
	
	//tallennetaan npc
	@Test
	public void saveNpc() throws Exception {
		Place place = new Place("S", "Pieni saari");
		placeRepository.save(place);
		Keyw keyword = new Keyw("Saaristo");
		keywordRepository.save(keyword);
		List<Keyw> keywords = new ArrayList<>();
		keywords.add(keyword);
		Npc npc = new Npc("Vihreä keiju", "keijukainen", place, keywords);
		npcRepository.save(npc);
		assertThat(npc.getNpcId()).isNotNull();
	}

}
