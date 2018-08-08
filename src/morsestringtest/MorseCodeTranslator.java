package morsestringtest;
import javax.swing.*;

public class MorseCodeTranslator {
        
	public MorseString toMorseString(String text, int a, JLabel la) {
		return MorseString.parse(text,a,la);
	}

	public String toText(String morseCode, int a, JLabel la) {
		MorseString morseString = new MorseString(morseCode,a, la);
		return morseString.asText();
	}
}
