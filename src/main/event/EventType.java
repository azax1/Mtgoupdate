package event;

public enum EventType {
	PRELIM, CHALLENGE, PTQ, SUPER_PTQ, LCQ, SHOWCASE_CHALLENGE;
	
	public String toString() {
		String name = name().replace("_", " ");
		return name.charAt(0) + name.substring(1).toLowerCase();
	}
}
