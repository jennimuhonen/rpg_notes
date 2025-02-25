package project.rpg_notes.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="Keyword")
public class Keyw {
	//Name of this class is Keyw because Keyword seemed to be problematic.
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long keywordId;
	
	@Size(min=1, max=250, message="Field is between 1-250 characters.")
	@NotEmpty(message="What your keyword is?")
	private String keyword;
	
	@ManyToMany //source material: https://www.baeldung.com/jpa-many-to-many
	@JoinTable(
			name="npcKey",
			joinColumns=@JoinColumn(name="keywordId"),
			inverseJoinColumns=@JoinColumn(name="npcId")
	)
	private List<Npc> npcs;
	
	@ManyToMany
	@JoinTable(
			name="placeKey",
			joinColumns=@JoinColumn(name="keywordId"),
			inverseJoinColumns=@JoinColumn(name="placeId")
	)
	private List<Place> places;
	
	public Keyw() {
		
	}

	public Keyw(long keywordId, String keyword) {
		this.keywordId = keywordId;
		this.keyword = keyword;
	}

	public Keyw(long keywordId, String keyword, List<Npc> npcs, List<Place> places) {
		this.keywordId = keywordId;
		this.keyword = keyword;
		this.npcs = npcs;
		this.places = places;
	}

	public long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeywordName() {
		return keyword;
	}

	public void setKeywordName(String keyword) {
		this.keyword = keyword;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<Npc> npcs) {
		this.npcs = npcs;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	@Override
	public String toString() {
		return "Keyword [keywordId=" + keywordId + ", keyword=" + keyword + "]";
	}

}
