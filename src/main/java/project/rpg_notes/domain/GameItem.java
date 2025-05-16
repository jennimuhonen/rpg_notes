package project.rpg_notes.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="item")
public class GameItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemId;
	
	@Size(min=1, max=250, message="Field is between 1-250 characters.")
	@NotEmpty(message="Item needs a name.")
	private String itemName;
	
	private String itemDescription;
	
	//One Item and many Notes
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="item")
	private List<Note> notes;
	
	//Many Items and many Keywords
	@ManyToMany
	@JoinTable(
			name="item_key",
			joinColumns=@JoinColumn(name="itemId"),
			inverseJoinColumns=@JoinColumn(name="keywordId")
	)
	private List<Keyw> keywords;

	public GameItem() {
	}

	public GameItem(long itemId, String itemName,
			String itemDescription, List<Note> notes, List<Keyw> keywords) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.notes = notes;
		this.keywords = keywords;
	}
	
	public GameItem(String itemName, String itemDescription) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
	}

	public GameItem(String itemName, String itemDescription, List<Keyw> keywords) {
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.keywords = keywords;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public List<Keyw> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyw> keywords) {
		this.keywords = keywords;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "GameItem [itemId=" + itemId + ", itemName=" + itemName + ", itemDescription=" + itemDescription
				+ ", notes=" + notes + ", keywords=" + keywords + "]";
	}
	
	

}
