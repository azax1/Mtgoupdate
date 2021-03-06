package event;

public enum Format {
	STANDARD, PIONEER, MODERN, LEGACY, VINTAGE, PAUPER, LIMITED;
	
	public String toString() {
		String name = name();
		return name.charAt(0) + name.substring(1).toLowerCase();
	}
	
	public boolean isShowcaseFormat() {
		if (this == PIONEER || this == MODERN || this == LEGACY || this == PAUPER) {
			return true;
		}
		return false;
	}
}
