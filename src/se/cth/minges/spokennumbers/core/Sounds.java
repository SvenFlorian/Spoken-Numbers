package se.cth.minges.spokennumbers.core;

/**
 * Contains references to the soundfiles.
 * @author Florian Minges
 */
public enum Sounds {
	ZERO (StringConstants.SOUNDS_ROOT_DIR + 0 + StringConstants.SOUND_SUFFIX) ,
	ONE (StringConstants.SOUNDS_ROOT_DIR + 1 + StringConstants.SOUND_SUFFIX) ,
	TWO (StringConstants.SOUNDS_ROOT_DIR + 2 + StringConstants.SOUND_SUFFIX) ,
	THREE (StringConstants.SOUNDS_ROOT_DIR + 3 + StringConstants.SOUND_SUFFIX) ,
	FOUR (StringConstants.SOUNDS_ROOT_DIR + 4 + StringConstants.SOUND_SUFFIX) ,
	FIVE (StringConstants.SOUNDS_ROOT_DIR + 5 + StringConstants.SOUND_SUFFIX) ,
	SIX (StringConstants.SOUNDS_ROOT_DIR + 6 + StringConstants.SOUND_SUFFIX) ,
	SEVEN (StringConstants.SOUNDS_ROOT_DIR + 7 + StringConstants.SOUND_SUFFIX) ,
	EIGHT (StringConstants.SOUNDS_ROOT_DIR + 8 + StringConstants.SOUND_SUFFIX) ,
	NINE (StringConstants.SOUNDS_ROOT_DIR + 9 + StringConstants.SOUND_SUFFIX),
	A (StringConstants.SOUNDS_ROOT_DIR + "a" + StringConstants.SOUND_SUFFIX),
	B (StringConstants.SOUNDS_ROOT_DIR + "b" + StringConstants.SOUND_SUFFIX),
	C (StringConstants.SOUNDS_ROOT_DIR + "c" + StringConstants.SOUND_SUFFIX);
	
	private String value;
	
	private Sounds(String clipPath) {
		this.value = clipPath;
	}
	
	/** Returns the enums stored string/value. */
	public String getValue() {
		return this.value;
	}
	
	/** Returns the Sound-enum that represents a given integer. */
	public static Sounds convert(int integer) {
		switch (integer) {
			case 0: return Sounds.ZERO; 
			case 1: return Sounds.ONE; 
			case 2: return Sounds.TWO; 
			case 3: return Sounds.THREE; 
			case 4: return Sounds.FOUR; 
			case 5: return Sounds.FIVE; 
			case 6: return Sounds.SIX; 
			case 7: return Sounds.SEVEN; 
			case 8: return Sounds.EIGHT; 
			case 9: return Sounds.NINE; 
			case 10: return Sounds.A;
			case 11: return Sounds.B;
			case 12: return Sounds.C;
			default: return null;
		}
	}

}
