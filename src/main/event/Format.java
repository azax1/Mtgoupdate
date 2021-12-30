package event;

public enum Format {
	STANDARD, PIONEER, MODERN, LEGACY, VINTAGE, PAUPER, LIMITED;
	
	public String toString() {
		String name = name();
		return name.charAt(0) + name.substring(1).toLowerCase();
	}
}
