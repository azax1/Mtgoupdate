package event;

public enum EventType {
	PRELIM, CHALLENGE, CHALLENGE_32, CHALLENGE_96, LCQ, MOCS_OPEN, RCQ, SHOWCASE_CHALLENGE, SUPER_RCQ;
	
	public String toString() {
		switch(this) {
			case PRELIM:
				return "Prelim";
			case CHALLENGE:
				return "Challenge";
			case CHALLENGE_32:
				return "Challenge (32-player)";
			case CHALLENGE_96:
				return "Challenge (96-player)";
			case LCQ:
				return "LCQ";
			case MOCS_OPEN:
				return "MOCS Showcase Open";
			case RCQ:
				return "RCQ";
			case SHOWCASE_CHALLENGE:
				return "Showcase Challenge";
			case SUPER_RCQ:
				return "Super RCQ";
			default:
				throw new UnsupportedOperationException("Unrecognized event type " + this.name());		
		}
	}
}
