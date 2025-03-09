package project.rpg_notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RpgNotesRestTest {

	@Autowired
	private WebApplicationContext webAppContext;
	
	private MockMvc mockMvc;
	
	//rakentaa testausympäristön
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	//toimiiko /npcs
	@Test
	public void npcsSatusOk() throws Exception {
		mockMvc.perform(get("/npcs")).andExpect(status().isOk());
	}
	
	//toimiiko api (automaattinen rest)
	@Test
	public void apiStatusOk() throws Exception {
		mockMvc.perform(get("/api/places")).andExpect(status().isOk());
	}
	
	//sisältö JSON-formaatissa
	@Test
	public void isJson() throws Exception {
		mockMvc.perform(get("/npcs"))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
