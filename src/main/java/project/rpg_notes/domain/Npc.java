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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	private String npcDescription;
	
	//Many Npcs and one Place
	@ManyToOne
	@JoinColumn(name="placeId")
	private Place place;
	
	//One Npc and many Notes
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="npc")
	private List<Note> notes;
	
	//Many Npcs many Keywords
	@ManyToMany
	@JoinTable(
			name="npc_key",
			joinColumns=@JoinColumn(name="npcId"),
			inverseJoinColumns=@JoinColumn(name="keywordId")
	)
	private List<Keyw> keywords;
	
	public Npc() {	
	}
	
	public Npc(String npcName, String npcDescription, Place place) {
		this.npcName = npcName;
		this.npcDescription = npcDescription;
		this.place = place;
	}

	public Npc(long npcId, String npcName, String npcDescription) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcDescription = npcDescription;
	}

	public Npc(long npcId, String npcName, String npcDescription, Place place) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcDescription = npcDescription;
		this.place = place;
	}
	
	public Npc(long npcId, String npcName, String npcDescription, Place place, List<Keyw> keywords) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcDescription = npcDescription;
		this.place = place;
		this.keywords = keywords;
	}

	public Npc(String npcName, String npcDescription, Place place, List<Keyw> keywords) {
		this.npcName = npcName;
		this.npcDescription = npcDescription;
		this.place = place;
		this.keywords = keywords;
	}
	
	public Npc(long npcId, String npcName, String npcDescription, Place place, List<Note> notes, List<Keyw> keywords) {
		this.npcId = npcId;
		this.npcName = npcName;
		this.npcDescription = npcDescription;
		this.place = place;
		this.notes = notes;
		this.keywords = keywords;
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

	public String getNpcDescription() {
		return npcDescription;
	}

	public void setNpcDescription(String npcDescription) {
		this.npcDescription = npcDescription;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Keyw> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyw> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "Npc [npcId=" + npcId + ", npcName=" + npcName + ", npcDescription=" + npcDescription + ", place=" + place + "]";
	}

}
