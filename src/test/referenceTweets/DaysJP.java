package referenceTweets;

public enum DaysJP implements Day {
		MONDAY("Monday Tournament Schedule (JST)\n"
			+ "\n"
			+ "1am Legacy Challenge\n"
			+ "3am Pauper Challenge\n"
			+ "5am Limited Prelim\n"
			+ "7am Standard Challenge\n"
			+ "8am Vintage Prelim\n"
			+ "9am Limited Prelim\n"
			+ "5pm Pioneer Prelim\n"
			+ "8pm Limited Prelim\n"
			+ "Midnight Modern Prelim"
		),
	
		TUESDAY("Tuesday Tournament Schedule (JST)\n"
			+ "\n"
			+ "12am Modern Prelim\n"
			+ "2am Limited Prelim\n"
			+ "4am Legacy Prelim\n"
			+ "8am Modern Prelim\n"
			+ "11am Limited Prelim\n"
			+ "Noon Pioneer Prelim\n"
			+ "2pm Vintage Prelim\n"
			+ "5pm Pauper Prelim\n"
			+ "8pm Modern Prelim\n"
			+ "Midnight Pioneer Prelim"
		),
		
		WEDNESDAY("Wednesday Tournament Schedule (JST)\n"
			+ "\n"
			+ "12am Pioneer Prelim\n"
			+ "2am Limited Prelim\n"
			+ "4am Pauper Prelim\n"
			+ "8am Standard Prelim\n"
			+ "9am Limited Prelim\n"
			+ "Noon Legacy Prelim\n"
			+ "5pm Limited Prelim\n"
			+ "8pm Legacy Prelim\n"
			+ "Midnight Standard Prelim"
		),
		
		THURSDAY("Thursday Tournament Schedule (JST)\n"
			+ "\n"
			+ "12am Standard Prelim\n"
			+ "4am Pioneer Prelim\n"
			+ "8am Modern Prelim\n"
			+ "9am Limited Prelim\n"
			+ "Noon Vintage Prelim\n"
			+ "5pm Modern Prelim\n"
			+ "8pm Pioneer Prelim\n"
			+ "Midnight Pauper Prelim"
		),
		
		FRIDAY("Friday Tournament Schedule (JST)\n"
			+ "\n"
			+ "12am Pauper Prelim\n"
			+ "4am Modern Prelim\n"
			+ "5am Limited Prelim\n"
			+ "8am Legacy Prelim\n"
			+ "9am Limited Prelim\n"
			+ "Noon Standard Prelim\n"
			+ "2pm Pioneer Prelim\n"
			+ "5pm Legacy Prelim\n"
			+ "7pm Limited Prelim\n"
			+ "9pm Modern Prelim\n"
			+ "Midnight Legacy Prelim"
		),
		
		SATURDAY("Saturday Tournament Schedule (JST)\n"
			+ "\n"
			+ "12am Legacy Prelim\n"
			+ "3am Limited Prelim\n"
			+ "4am Vintage Prelim\n"
			+ "8am Pioneer Prelim\n"
			+ "11am Limited Prelim\n"
			+ "Noon Modern Prelim\n"
			+ "5pm Pioneer Prelim\n"
			+ "6pm Limited Prelim\n"
			+ "7pm Pauper Challenge\n"
			+ "9pm Legacy Challenge (32 player)\n"
			+ "11pm Standard Challenge"
		),
		
		SUNDAY("Sunday Tournament Schedule (JST)\n"
			+ "\n"
			+ "1am Modern Challenge\n"
			+ "3am Vintage Challenge\n"
			+ "5am Limited Prelim\n"
			+ "7am Pioneer Challenge\n"
			+ "9am Limited Prelim\n"
			+ "5pm Vintage Challenge\n"
			+ "9pm Modern Challenge\n"
			+ "11pm Pioneer Challenge"
		);

	private String label;
	
	private DaysJP(String label) {
		this.label = label;
	}
	
	@Override
	public String text() {
		return label;
	}

	@Override
	public String postTime() {
		return "T13:00:00Z"; // TODO handle DST
	}
}
