package project.rpg_notes.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import project.rpg_notes.domain.GameItem;
import project.rpg_notes.domain.GameItemRepository;
import project.rpg_notes.domain.Keyw;
import project.rpg_notes.domain.KeywRepository;
import project.rpg_notes.domain.Note;
import project.rpg_notes.domain.NoteRepository;

@Controller
public class GameItemController {
	
	private GameItemRepository itemRepository;
	private KeywRepository keywordRepository;
	private NoteRepository noteRepository;
	
	public GameItemController(GameItemRepository itemRepository, KeywRepository keywordRepository,
			NoteRepository noteRepository) {
		this.itemRepository = itemRepository;
		this.keywordRepository = keywordRepository;
		this.noteRepository = noteRepository;
	}
	
	//All about Items
	
	//List Items
	@GetMapping("/item/itemlist")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String showItemList(Model model) {
		System.out.println("List of Items");
		model.addAttribute("item", itemRepository.findAll());
		return "item/itemList";
	}
	
	// Add new Item
	@GetMapping("/item/additem")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String addItem(Model model) {
		System.out.println("Ready to add new Item.");
		model.addAttribute("item", new GameItem());
		return "item/addItem";
	}
	
	//Save new Item
	@PostMapping("/item/saveitem")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveItem(@Valid @ModelAttribute("item") GameItem item, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Saving new Item failed.");
			model.addAttribute("item", item);
			return "item/addItem";
		}
		itemRepository.save(item);
		System.out.println("New Item saved: " + item);
		return "redirect:/item/" + item.getItemId();
	}
	
	// Edit Item
	@GetMapping(value="/item/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editItem(@PathVariable("id") Long itemId, Model model) {
		System.out.println("Ready to edit itemId " + itemId);
		GameItem item = itemRepository.findById(itemId).orElseThrow();
		model.addAttribute("item", item);
		model.addAttribute("keywords", keywordRepository.findAll());
		return "item/editItem";
	}
	
	// Save edited Item
	@PostMapping("/item/saveediteditem")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveEditedItem(@Valid @ModelAttribute("item") GameItem item, @RequestParam(value="keywordId", required=false) List<Long> keywords, BindingResult bindingResult, Model model) {
		
		List<Keyw> selectedKeywords = new ArrayList<>();
		Keyw keyword;
		if (keywords!=null) {
			for (int i = 0; i < keywords.size(); i++) {
				keyword = keywordRepository.findById(keywords.get(i)).orElseThrow();
				selectedKeywords.add(keyword);
			}
		}
		item.setKeywords(selectedKeywords);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Failed to edit Item: " + item);
			model.addAttribute("item", item);
			return "item/editItem";
		}
		
		itemRepository.save(item);
		System.out.println("Edited Item saved: " + item);
		return "redirect:/item/" + item.getItemId();
	}

	// Delete Item
	@GetMapping(value="item/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String deleteItem(@PathVariable("id") Long itemId) {
		itemRepository.deleteById(itemId);
		System.out.println("Deleted itemId" + itemId);
		return "redirect:/item/itemlist";
	}
	
	// Get all Item info
	@GetMapping(value="item/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String getItemInfo(@PathVariable("id") Long itemId, Model model) {
		System.out.println("Get information about Item with id " + itemId);
		GameItem item = itemRepository.findById(itemId).orElseThrow();
		model.addAttribute("item", item);
		return "item/itemInfo";
	}
	
	// Order Items alphabetically
	@GetMapping(value ="item/orderbyitem")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String orderItemsByItem(Model model) {
		System.out.println("Ordered list of Items");
		
		//Getting the list
		Iterable<GameItem> itemsIterable = itemRepository.findAll();
		List<GameItem> items = new ArrayList<>();
		itemsIterable.forEach(items::add);
			
		//Ordering it alphabetically
		items.sort(Comparator.comparing(item -> item.getItemName().toLowerCase()));
		
		model.addAttribute("item", items);
		
		return "item/itemlist";
	}
	
	//--- ITEM + KEYWORDS ---
	
	// Edit Item's Keywords
	@GetMapping(value="/item/editkeywords/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String editKeywordsForItem(@PathVariable("id") Long itemId, Model model) {
		System.out.println("Ready to edit Keywords");
		GameItem item = itemRepository.findById(itemId).orElseThrow();
		model.addAttribute("item", item);
		model.addAttribute("keywords", keywordRepository.findAll());
		return "item/editItemKeywords";
	}
	
	// Save Keywords	
	@PostMapping("/item/savekeywords")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String SaveKeywordsToItem(@RequestParam("itemId") Long itemId, @RequestParam(value="keywordId", required=false) List<Long> keywords, Model model) {
		GameItem item = itemRepository.findById(itemId).orElseThrow();
		List<Keyw> selectedKeywords = new ArrayList<>();
		Keyw keyword;
		if (keywords!=null) {
			for (int i = 0; i < keywords.size(); i++) {
			keyword = keywordRepository.findById(keywords.get(i)).orElseThrow();
			selectedKeywords.add(keyword);
			}
		}
		item.setKeywords(selectedKeywords);
		itemRepository.save(item);
		System.out.println("Seuraavat avainsanat tallennettu: " + item.getKeywords());
		return "redirect:/item/" + itemId;
	}
	
	//+++ Add new keyword, edit and delete it are under KeywController +++
	
	
	//--- ITEM + NOTES ---
	
	// Add new Note
	@GetMapping(value="item/additemnote/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String addItemNote(@PathVariable("id") Long itemId, Model model) {
		System.out.println("Add new note to Item");
		GameItem item = itemRepository.findById(itemId).orElseThrow();
		Note note = new Note();
		note.setItem(item);
		model.addAttribute("item", item);
		model.addAttribute("note", note);
		return "item/addItemNote";
	}
	
	// Save Note
	@PostMapping("/item/saveitemnote")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String saveItemNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Adding note failed");
			model.addAttribute("note", note);
			GameItem item = note.getItem();
			model.addAttribute("item", item);
			return "item/addItemNote";
		}
		noteRepository.save(note);
		System.out.println("New note saved: " + note);
		Long itemId = note.getItem().getItemId();
		return "redirect:/item/" + itemId;
	}
	
	
	//+++ Edit and Delete Note are under NoteController +++

}
