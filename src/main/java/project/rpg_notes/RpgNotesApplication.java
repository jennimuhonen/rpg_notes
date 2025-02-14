package project.rpg_notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public CommandLineRunner rpgData(NpcRepository npcRepository, PlaceRepository pRepository) {
		return (args) -> {
			
			System.out.println("Adding places to database");
			pRepository.save(new Place("Hurjaportti", "Kahlerannikon siirtokunnan pääkaupunki."));
			pRepository.save(new Place("Motaku", "Saariston suurin saari"));
			
			System.out.println("-- Print places --");
			for (Place place : pRepository.findAll()) {
				System.out.println(place.toString());
			}
						
			System.out.println("Lisätään tietokantaan kirjoja");
			npcRepository.save(new Npc("Ilkeä Piraatti", "Mustan laivan kapteeni", pRepository.findByPlaceName("Hurjaportti").get(0)));
			npcRepository.save(new Npc("Surkea Piraatti", "Mustan laivan perämies", pRepository.findByPlaceName("Hurjaportti").get(0)));
			npcRepository.save(new Npc("Hiljainen Metsästäjä", "Samoaa Chenoggin viidakossa", pRepository.findByPlaceName("Motaku").get(0)));
			npcRepository.save(new Npc("Onnellinen Kokki", "Sillisalaatti-tavernan kokki", pRepository.findByPlaceName("Motaku").get(0)));
			
			System.out.println("-- Print NPCs --");
			for (Npc npc : npcRepository.findAll()) {
				System.out.println(npc.toString());
			}
			
		};
		
	}

}
