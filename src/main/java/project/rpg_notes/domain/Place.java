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
@Table (name="place")
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long placeId;
	
	@Size(min=1, max=250, message="Field is between 1-250 characters.")
	@NotEmpty(message="Place needs a name.")
	private String placeName;
	
	private String placeDescription;
	
	//One Place and many NPCs
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="place")
	private List<Npc> npcs;
	
	//One Place and many Notes
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="place")
	private List<Note> notes;
	
	//Many Places and many Keywords
	@ManyToMany
	@JoinTable(
			name="place_key",
			joinColumns=@JoinColumn(name="placeId"),
			inverseJoinColumns=@JoinColumn(name="keywordId")
	)
	private List<Keyw> keywords;

	public Place() {	
	}
	
	public Place(String placeName, String placeDescription) {
		this.placeName = placeName;
		this.placeDescription = placeDescription;
	}

	public Place(long placeId, String placeName, String placeDescription, List<Npc> npcs) {
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeDescription = placeDescription;
		this.npcs = npcs;
	}
	
	public Place(String placeName, String placeDescription, List<Keyw> keywords) {
		this.placeName = placeName;
		this.placeDescription = placeDescription;
		this.keywords = keywords;
	}

	public Place(long placeId, String placeName, String placeDescription, List<Npc> npcs, List<Keyw> keywords) {
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeDescription = placeDescription;
		this.npcs = npcs;
		this.keywords = keywords;
	}

	public long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceDescription() {
		return placeDescription;
	}

	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<Npc> npcs) {
		this.npcs = npcs;
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
		return "Place [placeId=" + placeId + ", placeName=" + placeName + ", placeDescription=" + placeDescription + "]";
	}	

}
