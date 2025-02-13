package project.rpg_notes.domain;

public class Character {
	
	private long characterId;
	private String characterName;
	private String characterClass;
	private String characterPicture;
	private boolean active;
	
	public Character() {
		
	}
	
	public Character(long characterId, String characterName, String characterClass, String characterPicture, boolean active) {
		this.characterId = characterId;
		this.characterName = characterName;
		this.characterClass = characterClass;
		this.characterPicture = characterPicture;
		this.active = active;
	}
	
	public long getId() {
		return characterId;
	}
	
	public void setId(long characterId) {
		this.characterId = characterId;
	}
	
	public String getCharacterName() {
		return characterName;
	}
	
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	public String getCharacterClass() {
		return characterClass;
	}
	
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}
	
	public String getCharacterPicture() {
		return characterPicture;
	}
	
	public void setCharacterPicture(String characterPicture) {
		this.characterPicture = characterPicture;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "Character [characterId=" + characterId + ", characterName=" + characterName + ", characterClass=" + characterClass
				+ ", characterPicture=" + characterPicture + ", active=" + active + "]";
	}
	
}
