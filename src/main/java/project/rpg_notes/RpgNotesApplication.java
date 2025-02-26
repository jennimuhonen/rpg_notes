package project.rpg_notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public CommandLineRunner rpgData(NpcRepository npcRepository, PlaceRepository pRepository, NoteRepository nRepository) {
		return (args) -> {
			
			System.out.println("Adding places to database");
			pRepository.save(new Place("Hurjaportti", "Kahlerannikon siirtokunnan pääkaupunki."));
			pRepository.save(new Place("Motaku", "Saariston suurin saari"));
			
			System.out.println("-- Print places --");
			for (Place place : pRepository.findAll()) {
				System.out.println(place.toString());
			}
						
			System.out.println("Adding npcs");
			npcRepository.save(new Npc("Ilkeä Piraatti", "Mustan laivan kapteeni", pRepository.findByPlaceName("Hurjaportti").get(0)));
			npcRepository.save(new Npc("Surkea Piraatti", "Mustan laivan perämies", pRepository.findByPlaceName("Hurjaportti").get(0)));
			npcRepository.save(new Npc("Hiljainen Metsästäjä", "Samoaa Chenoggin viidakossa", pRepository.findByPlaceName("Motaku").get(0)));
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
		};
		
	}

}
