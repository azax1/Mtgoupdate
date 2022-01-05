package referenceTweets;

public enum DaysEU implements Day {
	MONDAY("Monday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Vintage Prelim\n"
		+ "1am Limited Prelim\n"
		+ "9am Pioneer Prelim\n"
		+ "Noon Limited Prelim\n"
		+ "4pm Modern Prelim\n"
		+ "6pm Limited Prelim\n"
		+ "8pm Legacy Prelim\n"
		+ "Midnight Modern Prelim"
	),
	
	TUESDAY("Tuesday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Modern Prelim\n"
		+ "3am Limited Prelim\n"
		+ "4am Pioneer Prelim\n"
		+ "6am Vintage Prelim\n"
		+ "9am Pauper Prelim\n"
		+ "Noon Modern Prelim\n"
		+ "4pm Pioneer Prelim\n"
		+ "6pm Limited Prelim\n"
		+ "8pm Pauper Prelim\n"
		+ "Midnight Standard Prelim"
	),
	
	WEDNESDAY("Wednesday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Standard Prelim\n"
		+ "1am Limited Prelim\n"
		+ "4am Legacy Prelim\n"
		+ "9am Limited Prelim\n"
		+ "Noon Legacy Prelim\n"
		+ "4pm Standard Prelim\n"
		+ "8pm Pioneer Prelim\n"
		+ "Midnight Modern Prelim"
	),
	
	THURSDAY("Thursday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Modern Prelim\n"
		+ "1am Limited Prelim\n"
		+ "4am Vintage Prelim\n"
		+ "9am Modern Prelim\n"
		+ "Noon Pioneer Prelim\n"
		+ "4pm Pauper Prelim\n"
		+ "8pm Modern Prelim\n"
		+ "9pm Limited Prelim\n"
		+ "Midnight Legacy Prelim"
	),
	
	FRIDAY("Friday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Legacy Prelim\n"
		+ "1am Limited Prelim\n"
		+ "4am Standard Prelim\n"
		+ "6am Pioneer Prelim\n"
		+ "9am Legacy Prelim\n"
		+ "11am Limited Prelim\n"
		+ "1pm Modern Prelim\n"
		+ "4pm Legacy Prelim\n"
		+ "7pm Limited Prelim\n"
		+ "8pm Vintage Prelim\n"
		+ "Midnight Pioneer Prelim"
	),
	
	SATURDAY("Saturday Tournament Schedule (CET)\n"
		+ "\n"
		+ "12am Pioneer Prelim\n"
		+ "3am Limited Prelim\n"
		+ "4am Modern Prelim\n"
		+ "9am Pioneer Prelim\n"
		+ "10am Limited Prelim\n"
		+ "11am Pauper Challenge\n"
		+ "1pm Legacy Challenge\n"
		+ "3pm Standard Challenge\n"
		+ "5pm Modern Challenge\n"
		+ "7pm Vintage Challenge\n"
		+ "9pm Limited Prelim\n"
		+ "11pm Pioneer Challenge"
	),
	
	SUNDAY("Sunday Tournament Schedule (CET)\n"
		+ "\n"
		+ "1am Limited Prelim\n"
		+ "9am Vintage Challenge\n"
		+ "1pm Modern Challenge\n"
		+ "3pm Pioneer Challenge\n"
		+ "5pm Legacy Challenge\n"
		+ "7pm Pauper Challenge\n"
		+ "9pm Limited Prelim\n"
		+ "11pm Standard Challenge\n"
		+ "Midnight Vintage Prelim"
	);
	
	private String label;
	
	private DaysEU(String label) {
		this.label = label;
	}
	
	@Override
	public String text() {
		return label;
	}

	@Override
	public String postTime() {
		return "T21:00:00Z"; // TODO handle DST
	}
};