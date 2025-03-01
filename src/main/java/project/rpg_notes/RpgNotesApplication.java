package project.rpg_notes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.Note;
import project.rpg_notes.domain.NoteRepository;
import project.rpg_notes.domain.Npc;
import project.rpg_notes.domain.NpcRepository;
import project.rpg_notes.domain.Place;
import project.rpg_notes.domain.PlaceRepository;



@SpringBootApplication
public class RpgNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpgNotesApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner rpgData(NpcRepository npcRepository, PlaceRepository pRepository, NoteRepository nRepository, KeywRepository kRepository) {
		return (args) -> {
			
			System.out.println("Adding some keywords");
			kRepository.save(new Keyw("Musta laiva"));
			kRepository.save(new Keyw("Piraatti"));
			kRepository.save(new Keyw("Epäilyttävä!"));
			
			System.out.println("-- Print keywords --");
			for (Keyw keyword : kRepository.findAll()) {
				System.out.println(keyword.toString());
			}
			
			System.out.println("Adding places to database");
			pRepository.save(new Place("Hurjaportti", "Kahlerannikon siirtokunnan pääkaupunki.", kRepository.findByKeywordName("Epäilyttävä!")));
			pRepository.save(new Place("Motaku", "Saariston suurin saari"));
			
			System.out.println("-- Print places --");
			for (Place place : pRepository.findAll()) {
				System.out.println(place.toString());
			}
			
			List<Keyw> keywords = new ArrayList<>();
			keywords.addAll(kRepository.findByKeywordName("Musta laiva"));
			keywords.addAll(kRepository.findByKeywordName("Piraatti"));
			System.out.println("Printataan lista keywords: "+keywords);
						
			System.out.println("Adding npcs");
			npcRepository.save(new Npc("Ilkeä Piraatti", "Mustan laivan kapteeni", pRepository.findByPlaceName("Hurjaportti").get(0), keywords));
			npcRepository.save(new Npc("Surkea Piraatti", "Mustan laivan perämies", pRepository.findByPlaceName("Hurjaportti").get(0), keywords));
			npcRepository.save(new Npc("Hiljainen Metsästäjä", "Samoaa Chenoggin viidakossa", pRepository.findByPlaceName("Motaku").get(0), kRepository.findByKeywordName("Epäilyttävä!")));
			npcRepository.save(new Npc("Onnellinen Kokki", "Sillisalaatti-tavernan kokki", pRepository.findByPlaceName("Motaku").get(0)));
			
			System.out.println("-- Print NPCs --");
			for (Npc npc : npcRepository.findAll()) {
				System.out.println(npc.toString());
			}
			
			System.out.println("Adding few notes");
			nRepository.save(new Note("Höppänä ja hieman ilkeä.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
			nRepository.save(new Note("Ollut Mustan laivan kapteenina 27 vuotta.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
			nRepository.save(new Note("Omistaa kissan nimeltään Musta.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
			
			System.out.println("-- Print Notes --");
			for (Note note : nRepository.findAll()) {
				System.out.println(note.toString());
			}
		
			System.out.println("Tsekkaus: "+nRepository.findById(1L));
		};
		
	}

}
