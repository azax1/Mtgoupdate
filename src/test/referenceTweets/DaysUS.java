package referenceTweets;

public enum DaysUS implements Day {
	MONDAY("Monday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Pioneer Prelim\n"
		+ "3am Limited Prelim\n"
		+ "7am Modern Prelim\n"
		+ "9am Limited Prelim\n"
		+ "11am Legacy Prelim\n"
		+ "3pm Modern Prelim\n"
		+ "6pm Limited Prelim\n"
		+ "7pm Pioneer Prelim\n"
		+ "Midnight Limited Prelim"
	),
	
	TUESDAY("Tuesday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Limited Prelim\n"
		+ "3am Modern Prelim\n"
		+ "7am Pioneer Prelim\n"
		+ "9am Limited Prelim\n"
		+ "11am Modern Prelim\n"
		+ "3pm Pioneer Prelim\n"
		+ "4pm Limited Prelim\n"
		+ "7pm Legacy Prelim\n"
		+ "Midnight Limited Prelim"
	),
	
	WEDNESDAY("Wednesday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Limited Prelim\n"
		+ "3am Legacy Prelim\n"
		+ "7am Limited Prelim\n"
		+ "11am Pioneer Prelim\n"
		+ "3pm Modern Prelim\n"
		+ "4pm Limited Prelim\n"
		+ "9pm Limited Prelim\n"
		+ "Midnight Modern Prelim"
	),
	
	THURSDAY("Thursday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Modern Prelim\n"
		+ "3am Pioneer Prelim\n"
		+ "7am Limited Prelim\n"
		+ "11am Modern Prelim\n"
		+ "Noon Limited Prelim\n"
		+ "3pm Legacy Prelim\n"
		+ "4pm Limited Prelim\n"
		+ "7pm Modern Prelim\n"
		+ "9pm Pioneer Prelim\n"
		+ "Midnight Legacy Prelim"
	),
	
	FRIDAY("Friday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Legacy Prelim\n"
		+ "2am Limited Prelim\n"
		+ "4am Modern Prelim\n"
		+ "7am Legacy Prelim\n"
		+ "10am Limited Prelim\n"
		+ "11am Vintage Prelim\n"
		+ "3pm Pioneer Prelim\n"
		+ "6pm Limited Prelim\n"
		+ "7pm Modern Prelim\n"
		+ "Midnight Pioneer Prelim"
	),
	
	SATURDAY("Saturday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Pioneer Prelim\n"
		+ "1am Limited Challenge (32-player)\n"
		+ "2am Pauper Challenge\n"
		+ "4am Legacy Challenge (32-player)\n"
		+ "6am Standard Challenge\n"
		+ "8am Modern Challenge\n"
		+ "10am Vintage Challenge\n"
		+ "Noon Limited Challenge\n"
		+ "2pm Pioneer Challenge\n"
		+ "4pm Limited Prelim\n"
		+ "Midnight Vintage Challenge"
	),
	
	SUNDAY("Sunday Tournament Schedule (PT)\n"
		+ "\n"
		+ "12am Vintage Challenge\n"
		+ "1am Limited Challenge (32-player)\n"
		+ "4am Modern Challenge\n"
		+ "6am Pioneer Challenge\n"
		+ "8am Legacy Challenge\n"
		+ "10am Pauper Challenge\n"
		+ "Noon Limited Challenge\n"
		+ "2pm Standard Challenge\n"
		+ "3pm Vintage Prelim\n"
		+ "4pm Limited Prelim\n"
		+ "Midnight Pioneer Prelim"
	);
	
	private String label;
	
	private DaysUS(String label) {
		this.label = label;
	}
	
	@Override
	public String text() {
		return label;
	}

	@Override
	public String postTime() {
		return "T06:00:00Z"; // TODO handle DST
	}
}