package project.rpg_notes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import project.rpg_notes.domain.AppUser;
import project.rpg_notes.domain.AppUserRepository;
import project.rpg_notes.domain.GameItem;
import project.rpg_notes.domain.GameItemRepository;
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
	public CommandLineRunner rpgData(NpcRepository npcRepository, PlaceRepository pRepository, GameItemRepository giRepository, NoteRepository nRepository, KeywRepository kRepository, AppUserRepository auRepository) {
		return (args) -> {
			
			if(kRepository.count()==0) {
				System.out.println("Adding some keywords");
				kRepository.save(new Keyw("Musta laiva"));
				kRepository.save(new Keyw("Piraatti"));
				kRepository.save(new Keyw("Epäilyttävä!"));
			}
			
			System.out.println("-- Print keywords --");
			for (Keyw keyword : kRepository.findAll()) {
				System.out.println(keyword.toString());
			}
			
			if(pRepository.count()==0) {
				System.out.println("Adding places to database");
				pRepository.save(new Place("Hurjaportti", "Kahlerannikon siirtokunnan pääkaupunki.", kRepository.findByKeywordName("Epäilyttävä!")));
				pRepository.save(new Place("Motaku", "Saariston suurin saari"));
			}
			
			System.out.println("-- Print places --");
			for (Place place : pRepository.findAll()) {
				System.out.println(place.toString());
			}
			
			List<Keyw> keywords = new ArrayList<>();
			keywords.addAll(kRepository.findByKeywordName("Musta laiva"));
			keywords.addAll(kRepository.findByKeywordName("Piraatti"));
			System.out.println("-- Print list of keywords: "+keywords+" --");
			
			if(npcRepository.count()==0) {
				System.out.println("Adding npcs");
				npcRepository.save(new Npc("Ilkeä Piraatti", "Mustan laivan kapteeni", pRepository.findByPlaceName("Hurjaportti").get(0), keywords));
				npcRepository.save(new Npc("Surkea Piraatti", "Mustan laivan perämies", pRepository.findByPlaceName("Hurjaportti").get(0), keywords));
				npcRepository.save(new Npc("Hiljainen Metsästäjä", "Samoaa Chenoggin viidakossa", pRepository.findByPlaceName("Motaku").get(0), kRepository.findByKeywordName("Epäilyttävä!")));
				npcRepository.save(new Npc("Onnellinen Kokki", "Sillisalaatti-tavernan kokki", pRepository.findByPlaceName("Motaku").get(0)));
			}
			
			System.out.println("-- Print NPCs --");
			for (Npc npc : npcRepository.findAll()) {
				System.out.println(npc.toString());
			}
			
			if(giRepository.count()==0) {
				System.out.println("Adding items to database");
				giRepository.save(new GameItem("Mystinen kaulakoru", "Hohtaa pinkkinä"));
				giRepository.save(new GameItem("Pikkiriikkinen kenkä", "Laulaa lauluja"));
			}
			
			if(nRepository.count()==0) {
				System.out.println("Adding few notes");
				nRepository.save(new Note("Höppänä ja hieman ilkeä.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
				nRepository.save(new Note("Ollut Mustan laivan kapteenina 27 vuotta.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
				nRepository.save(new Note("Omistaa kissan nimeltään Musta.", npcRepository.findByNpcName("Ilkeä Piraatti").get(0)));
			}
			
			System.out.println("-- Print Notes --");
			for (Note note : nRepository.findAll()) {
				System.out.println(note.toString());
			}
		
			System.out.println("Tsekkaus: "+nRepository.findById(1L));
			
			if(auRepository.count()==0) {
				System.out.println("Adding two users");
				auRepository.save(new AppUser("user", "$2a$10$ch5N2B/5o6SWa848b5Z0i.YlP/eJ14Yms6MXVGjb1H/tM0dnQ02fu", "USER"));
				auRepository.save(new AppUser("admin", "$2a$10$s1ahkW.MFc.1oCkcHt5Ku.3wdh7AxIE6C7reU2twpuTUbIm6F4C1q", "ADMIN"));
				auRepository.save(new AppUser("guest", "$2a$10$5CRnG4vY6dDZB6nCMDY7xedGqzlXW0TkAv71phg2eI0BRgPCWdyPK", "GUEST"));
			}
			
		};
		
	}

}
