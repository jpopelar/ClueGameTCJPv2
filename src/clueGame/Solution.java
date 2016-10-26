package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public boolean equals(Solution suggestion) {
		return (this.person.equals(suggestion.person) && this.weapon.equals(suggestion.weapon) && this.room.equals(suggestion.room));
	}

	@Override
	public String toString() {
		return "Solution [person=" + person + ", weapon=" + weapon + ", room=" + room + "]";
	}
	
}
