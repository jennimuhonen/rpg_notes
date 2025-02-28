package project.rpg_notes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table (name="Note")
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noteId;
	
	@NotEmpty(message="An empty box is not a note.")
	private String noteContent;
	
	//Many Notes, one Npc
	@ManyToOne
	@JoinColumn(name="npcId", nullable=true)
	private Npc npc;
	
	//Many Notes, one Place
	@ManyToOne
	@JoinColumn(name="placeId", nullable=true)
	private Place place;

	public Note() {
		
	}

	public Note(String noteContent, Npc npc) {
		this.noteContent = noteContent;
		this.npc = npc;
	}

	public Note(long noteId, String noteContent, Npc npc) {
		this.noteId = noteId;
		this.noteContent = noteContent;
		this.npc = npc;
	}

	public Note(String noteContent, Place place) {
		this.noteContent = noteContent;
		this.place = place;
	}

	public Note(long noteId, String noteContent, Place place) {
		this.noteId = noteId;
		this.noteContent = noteContent;
		this.place = place;
	}

	public Note(String noteContent, Npc npc, Place place) {
		this.noteContent = noteContent;
		this.npc = npc;
		this.place = place;
	}

	public Note(long noteId, String noteContent, Npc npc, Place place) {
		this.noteId = noteId;
		this.noteContent = noteContent;
		this.npc = npc;
		this.place = place;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public Npc getNpc() {
		return npc;
	}

	public void setNpc(Npc npc) {
		this.npc = npc;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteContent=" + noteContent + ", npc=" + npc + ", place=" + place + "]";
	}
	
	
	
	
	
	
	
	

}
