package morsestringtest;

//영어 변환
enum EMorseCode {
	A(".-"), B("-..."), C("-.-."), D("-.."), E("."), F("..-."), G("--."), H("...."), I(".."), J(".---"), K("-.-"), L(".-.."), M("--"), N("-."),
	O("---"), P(".--."), Q("--.-"),	R(".-."), S("..."), T("-"), U("..-"), V("...-"), W(".--"), X("-..-"), Y("-.--"), Z("--.."),
	ZERO('0', "-----"), ONE('1', ".----"), TWO('2', "..---"), THREE('3', "...--"), FOUR('4', "....-"), FIVE('5', "....."), SIX('6', "-...."), SEVEN('7', "--..."),
	EIGHT('8', "---.."), NINE('9', "----.");

	private char character;
	private String code;

	EMorseCode(char character, String code) {
		this.character = character;
		this.code = code;
	}

	EMorseCode(String code) {
		this.character = this.name().charAt(0);
		this.code = code;
	}

	public static char decode(String s) {
		for (EMorseCode morseCode : EMorseCode.values()) {
			if (morseCode.code.equals(s)) {
				return morseCode.character;
			}
		}
		throw new IllegalArgumentException(s + " is not a valid Morse Code");
	}

	public static String encode(char c) {
		for (EMorseCode morseCode : EMorseCode.values()) {
			if (morseCode.character == Character.toUpperCase(c)) {
				return morseCode.code;
			}
		}
		throw new IllegalArgumentException(c + " cannot be found");
	}
}

//한글 모스코드 변환
enum KMorseCode {
	ZERO('0', "-----"), ONE('1', ".----"), TWO('2', "..---"), THREE('3', "...--"), FOUR('4', "....-"), FIVE('5', "....."), SIX('6', "-...."), SEVEN('7', "--..."),
	EIGHT('8', "---.."), NINE('9', "----."), 
        ㄱ(".-.."), ㄴ("..-."), ㄷ("-..."), ㄹ("...-"), ㅁ("--"),ㅂ(".--"),ㅅ("--."), ㅇ("-.-"),ㅈ(".--."),ㅊ("-.-."),ㅋ("-..-"),ㅌ("--.."),ㅍ("---"),ㅎ(".---"),
        ㄲ(".-.. .-.."),ㄸ("-... -..."),ㅃ(".-- .--"),ㅆ("--. --."),ㅉ(".--. .--."),
        ㅏ("."), ㅑ(".."), ㅓ("-"), ㅕ("..."), ㅗ(".-"), ㅛ("-."), ㅜ("...."), ㅠ(".-."), ㅡ("-.."), ㅣ("..-"), ㅐ("--.-"), ㅔ("-.--"),
        ㅘ(".- ."), ㅝ(".... -"),ㅚ(".- ..-"),ㅟ(".... ..-"),ㅒ(".. ..-"),ㅙ(".- --.-"),ㅞ(".... -.--"),ㅖ("... ..-"),ㅢ("-.. ..-"),
        ㄳ(".-.. --."),ㄵ("..-. .--."),ㄶ("..-. .---"),ㄺ("...- .-.."),ㄻ("...- --"),ㄼ("...- .--"),ㅀ("...- .---"),ㅄ(".-- --."),ㅅㄱ("--. .-..");

	private char character;
	private String code;

	KMorseCode(char character, String code) {
		this.character = character;
		this.code = code;
	}

	KMorseCode(String code) {
		this.character = this.name().charAt(0);
		this.code = code;
	}
        //모스코드=>한글
	public static char decode(String s) {
		for (KMorseCode morseCode : KMorseCode.values()) {
			if (morseCode.code.equals(s)) {
				return morseCode.character;
			}
		}
		throw new IllegalArgumentException(s + " is not a valid Morse Code");
	}
        //한글=>모스코드
	public static String encode(char c) {
		for (KMorseCode morseCode : KMorseCode.values()) {
			if (morseCode.character == Character.toUpperCase(c)) {
				return morseCode.code;
			}
		}
		throw new IllegalArgumentException(c + " cannot be found");
	}
}