package project.rpg_notes.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="Npc")
public class Npc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long npcId;
	
	@Size(min=1, max=250, message="Field is between 1-250 characters.")
	@NotEmpty(message="NPC needs a name.")
	private String npcName;
	
	private String npcNotes;
	
	//Many Npcs in one place
	@ManyToOne
	@JoinColumn(name="placeId")
	private Place place;
	
	@ManyToMany(mappedBy = "npcs")
	private List<Keyw> keywords;
	
	public Npc() {	
	}
	
	public Npc(String npcName, String npcNotes, Place place) {
		this.npcName = npcName;
		this.npcNotes = npcNotes;
		this.place = place;
	}

	public Npc(long npcId, String npcName, String npcNotes) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcNotes = npcNotes;
	}

	public Npc(long npcId, String npcName, String npcNotes, Place place) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcNotes = npcNotes;
		this.place = place;
	}

	public long getNpcId() {
		return npcId;
	}

	public void setNpcId(long npcId) {
		this.npcId = npcId;
	}

	public String getNpcName() {
		return npcName;
	}

	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}

	public String getNpcNotes() {
		return npcNotes;
	}

	public void setNpcNotes(String npcNotes) {
		this.npcNotes = npcNotes;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Npc [npcId=" + npcId + ", npcName=" + npcName + ", npcNotes=" + npcNotes + ", place=" + place + "]";
	}

}
