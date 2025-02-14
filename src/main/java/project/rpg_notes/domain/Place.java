package project.rpg_notes.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="Place")
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long placeId;
	
	@Size(min=1, max=250, message="Field is between 1-250 characters.")
	@NotEmpty(message="Place needs a name.")
	private String placeName;
	
	private String placeNotes;
	
	//One place and many NPCs
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="place")
	private List<Npc> Npcs;

	public Place() {	
	}
	
	public Place(String placeName, String placeNotes) {
		this.placeName = placeName;
		this.placeNotes = placeNotes;
	}

	public Place(long placeId, String placeName, String placeNotes, List<Npc> npcs) {
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeNotes = placeNotes;
		Npcs = npcs;
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

	public String getPlaceNotes() {
		return placeNotes;
	}

	public void setPlaceNotes(String placeNotes) {
		this.placeNotes = placeNotes;
	}

	public List<Npc> getNpcs() {
		return Npcs;
	}

	public void setNpcs(List<Npc> npcs) {
		Npcs = npcs;
	}

	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", placeName=" + placeName + ", placeNotes=" + placeNotes + "]";
	}	

}
