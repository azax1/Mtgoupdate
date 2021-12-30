package event;

public enum EventType {
	PRELIM, CHALLENGE; // eventually include LCQ, SHOWCASE_CHALLENGE, PTQ
	
	public String toString() {
		String name = name();
		return name.charAt(0) + name.substring(1).toLowerCase();
	}
}
