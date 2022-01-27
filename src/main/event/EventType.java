package event;

public enum EventType {
	PRELIM, CHALLENGE, CHALLENGE_32, LCQ, MOCS_OPEN, PTQ, SHOWCASE_CHALLENGE, SUPER_PTQ;
	
	public String toString() {
		switch(this) {
			case PRELIM:
				return "Prelim";
			case CHALLENGE:
				return "Challenge";
			case CHALLENGE_32:
				return "Challenge (32 player)";
			case LCQ:
				return "LCQ";
			case MOCS_OPEN:
				return "MOCS Showcase Open";
			case PTQ:
				return "PTQ";
			case SHOWCASE_CHALLENGE:
				return "Showcase Challenge";
			case SUPER_PTQ:
				return "Super PTQ";
			default:
				throw new UnsupportedOperationException("Unrecognized event type " + this.name());		
		}
	}
}
