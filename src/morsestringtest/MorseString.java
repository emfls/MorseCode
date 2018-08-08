package morsestringtest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.regex.Pattern;
import javax.swing.JLabel;

public class MorseString{
    
	private static final String CHAR_SEPARATOR = " ";
	private static final String WORD_SEPARATOR = "/";
	private static final String DOT = ".";
	private static final String DASH = "-";

	private static final float SAMPLE_RATE = 8000;
	private final int ONE_UNIT_OF_MORSE_CODE = 240;

	private static final Pattern VALID_MORSE_PATTERN = Pattern.compile(
			"(" + Pattern.quote(DASH)
					+ "|" + Pattern.quote(DOT)
					+ "|" + Pattern.quote(WORD_SEPARATOR)
					+ "|\\s)*");

	private String text;
	private String codes;

	private static final int DASH_LENGTH = 5;
	private static final int DOT_LENGTH = 2;
	private static final int SPACE_BETWEEN_ONE_LETTER = 3;

        final static String[] chosung = { "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
        final static String[] moeum = { "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ" };
        final static String[] badchim = { "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };

	public MorseString(String codes, int a, JLabel la) {
		if (!isValidMorse(codes)) {
			la.setText(" is not a valid Morse Code");
		}

		this.text = !codes.isEmpty() ? translate(codes,a) : codes;
		this.codes = codes;
	}
        
        
        public static String toDaro(String s) {
            if (s == null)
                return null;
            String t = "";
            String tmp = "";
            int n, n1, n2, n3;
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                n = (int)(c & 0xFFFF);
                if (n >= 0xAC00 && n <= 0xD7A3) {
                    n = n - 0xAC00;
                    n1 = n / (21 * 28);
                    n = n % (21 * 28);
                    n2 = n / 28;
                    n3 = n % 28;
                    tmp = chosung[n1] + moeum[n2] + badchim[n3];
                    t += tmp;
                }
                else {
                    t += c;
                }
            }
            return t;
        }

	public static boolean isValidMorse(CharSequence ch) {

		return VALID_MORSE_PATTERN.matcher(ch).matches();
	}

	private String translate(String code,int a) {
		String[] words = code.split(WORD_SEPARATOR);
		StringBuilder result = new StringBuilder();
                
                if(a==2){
                    for (String word : words) {
                            String[] letters = word.trim().split(CHAR_SEPARATOR);
                            for (String letter : letters) {
                                    result.append(EMorseCode.decode(letter));
                            }
                            result.append(CHAR_SEPARATOR);
                    }
                }
                else if(a==1){
                    for (String word : words) {
                            String[] letters = word.trim().split(CHAR_SEPARATOR);
                            for (String letter : letters) {
                                    result.append(KMorseCode.decode(letter));
                            }
                            result.append(CHAR_SEPARATOR);
                    }
                }

		String text = result.toString()
				.substring(0, result.length() - 1);

		return text;
	}

	public static MorseString parse(String text, int a, JLabel la) {
		if (text == null || text.isEmpty()) {
			return new MorseString("",a,la);
		} else if (!text.matches("[\\s\\dA-Za-z]*")) {
			//throw new IllegalArgumentException("String too complicated");
		}
                text=toDaro(text);
		int length = text.length();
		StringBuilder result 
				= new StringBuilder();
                if(a==2){
                    for (int i = 0; i < length; i++) {
                            if (text.charAt(i) == ' ') {
                                    result.append(WORD_SEPARATOR).append(CHAR_SEPARATOR);
                                    continue;
                            }
                            result.append(EMorseCode.encode(text.charAt(i))).append(CHAR_SEPARATOR);
                    }
                }
                else if(a==1){
                    for (int i = 0; i < length; i++) {
                            if (text.charAt(i) == ' ') {
                                    result.append(WORD_SEPARATOR).append(CHAR_SEPARATOR);
                                    continue;
                            }
                            result.append(KMorseCode.encode(text.charAt(i))).append(CHAR_SEPARATOR);
                    }
                }

		return new MorseString(result.toString().trim(),a,la);
	}

	@Override
	public String toString() {
		return codes;
	}

	

	public String asText() {
		return text;
	}

	public void printText(){
		String[] words = codes.split(WORD_SEPARATOR);

		for (String word : words) {
			String[] letters = word.split(CHAR_SEPARATOR);

			for (String letter : letters) {
				char[] chars = letter.trim().toCharArray();

				for (int i = 0; i < chars.length; i++) {
					if (i < chars.length-1)
					System.out.print("-");
				}
				System.out.print("  ");
			}
			System.out.print("\t");
		}
	}

        
            private void createTone(int hz, int msecs, double vol)
                            throws LineUnavailableException {
                    byte[] bufffer = new byte[1];

                    AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
                    SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(af);

                    sourceDataLine.open(af);
                    sourceDataLine.start();
                    for (int i = 0; i < msecs * 8; i++) {
                            double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
                            bufffer[0] = (byte) (Math.sin(angle) * 120.0 * vol);
                            sourceDataLine.write(bufffer, 0, 1);
                    }
                    sourceDataLine.drain();
                    sourceDataLine.stop();
                    sourceDataLine.close();
            }

            public void play() throws LineUnavailableException, InterruptedException {
                    char[] chars = codes.trim().toCharArray();

                    for (char c : chars) {
                            playOneBlip(c);
                    }
                    
            }

            //각 모스부호 음 내는 코드
            private void playOneBlip(char c) throws LineUnavailableException, InterruptedException {
                    if (c == '-') {
                            playDah();
                    } else if (c == '.') {
                            playDit();
                    }

                    Thread.sleep(ONE_UNIT_OF_MORSE_CODE * SPACE_BETWEEN_ONE_LETTER);
            }

            //짧은 음 내기
            private void playDit() throws LineUnavailableException {
                    createTone(400, ONE_UNIT_OF_MORSE_CODE * DOT_LENGTH, 0.5);
            }

            //긴 음 내기
            private void playDah() throws LineUnavailableException {
                    createTone(600, ONE_UNIT_OF_MORSE_CODE * DASH_LENGTH, 0.5);
            }
        
}


