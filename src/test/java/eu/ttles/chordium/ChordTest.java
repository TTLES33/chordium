package eu.ttles.chordium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChordTest {


    //------------
    //ADD TONE
    //------------

    @Test
    @DisplayName("addTone")
    void addTone() {
        Chord chord = new Chord(6);
        chord.addTone(1);
        chord.addTone(2);
        chord.addTone(3);
        chord.addTone(4);
        chord.addTone(5);
        chord.addTone(6);

        ArrayList<Integer> tonesList = new ArrayList<>(List.of(1,2,3,4,5,6));
        assertEquals(tonesList, chord.getTonePositions());
    }
    @Test
    @DisplayName("addToneExtraStringException")
    void addToneExtraStringException() {
        Chord chord = new Chord(6);
        chord.addTone(1);
        chord.addTone(2);
        chord.addTone(3);
        chord.addTone(4);
        chord.addTone(5);
        chord.addTone(6);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->  chord.addTone(7));
        assertEquals("Tones out of bounds, trying to set tone on string number 7, number of strings: 6", exception.getMessage());
    }



    //------------
    //IS PLAYABLE
    //------------


    @Test
    @DisplayName("isPlayableAllCorrect")
    void isPlayableAllCorrect() {
        Chord chord = new Chord(6);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(2);
        chord.addTone(2);
        chord.addTone(0);

        assertTrue(chord.isPlayable(3));
    }

    @Test
    @DisplayName("isPlayableAllCorrectWithSkippedStrings")
    void isPlayableAllCorrectWithSkippedStrings() {

        Chord chord = new Chord(6);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(0);
        chord.addTone(3);
        chord.addTone(2);
        chord.addTone(0);



        assertTrue(chord.isPlayable(3));

    }

    @Test
    @DisplayName("isPlayableAllCorrectWithBarre")
    void isPlayableAllCorrectWithBarre() {

        Chord chord = new Chord(6);
        chord.addTone(3);
        chord.addTone(3);
        chord.addTone(5);
        chord.addTone(5);
        chord.addTone(4);
        chord.addTone(3);

        System.out.println(chord.getChordWidth());

        assertTrue(chord.isPlayable(3));

    }

    @Test
    @DisplayName("isPlayableIsCompleteFalse")
    void isPlayableIsCompleteFalse() {

        Chord chord = new Chord(6);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(2);
        chord.addTone(2);

        assertFalse(chord.isPlayable(3));

    }

    @Test
    @DisplayName("isPlayableMaxWidthExceeded")
    void isPlayableMaxWidthExceeded() {

        Chord chord = new Chord(6);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(5);
        chord.addTone(2);
        chord.addTone(0);

        assertFalse(chord.isPlayable(3));

    }

    @Test
    @DisplayName("isPlayableMaxFingersExceeded")
    void isPlayableMaxFingersExceeded() {

        Chord chord = new Chord(6);
        chord.addTone(1);
        chord.addTone(2);
        chord.addTone(1);
        chord.addTone(2);
        chord.addTone(2);
        chord.addTone(0);

        assertFalse(chord.isPlayable(3));

    }



    //------------
    //FIND CHORD WIDTH
    //------------

    @Test
    @DisplayName("findChordWidth")
    void findChordWidth() {

        Chord chord = new Chord(6);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(2);
        chord.addTone(3);
        chord.addTone(2);
        chord.addTone(0);

        assertEquals(3, chord.getChordWidth());

    }

    @Test
    @DisplayName("findChordWidthWithNonSoundingStrings")
    void findChordWidthWithNonSoundingStrings() {

        Chord chord = new Chord(6);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(2);
        chord.addTone(4);
        chord.addTone(2);
        chord.addTone(2);

        assertEquals(3, chord.getChordWidth());

    }


    //------------
    //FIND BARRE
    //------------
    @Test
    @DisplayName("findBarre")
    void findBarre() {

        Chord chord = new Chord(6);
        chord.addTone(3);
        chord.addTone(3);
        chord.addTone(5);
        chord.addTone(5);
        chord.addTone(3);
        chord.addTone(3);

        assertEquals(0, chord.getBarreStartString());
        assertEquals(5, chord.getBarreEndString());
        assertEquals(3, chord.getBarrePosition());

    }

    @Test
    @DisplayName("findBarreNoBarre")
    void findBarreNoBarre() {

        Chord chord = new Chord(6);
        chord.addTone(3);
        chord.addTone(0);
        chord.addTone(2);
        chord.addTone(2);
        chord.addTone(0);
        chord.addTone(0);

        assertEquals(0, chord.getBarreStartString());
        assertEquals(0, chord.getBarreEndString());
        assertEquals(0, chord.getBarrePosition());

    }

    @Test
    @DisplayName("findBarreNoBarreWithNonSoundingStrings")
    void findBarreNoBarreWithNonSoundingStrings() {

        Chord chord = new Chord(6);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(2);
        chord.addTone(2);
        chord.addTone(3);
        chord.addTone(2);

        assertEquals(2, chord.getBarreStartString());
        assertEquals(5, chord.getBarreEndString());
        assertEquals(2, chord.getBarrePosition());

    }





    //------------
    //IS CORRECT
    //------------

    @Test
    @DisplayName("isCorrect All correct")
    void isCorrect() {
        Chord chord = new Chord(6);
        chord.addTone(3);
        chord.addTone(2);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(0);
        chord.addTone(3);


        ArrayList<String> chordTones = new ArrayList<>(List.of("B","G","D"));
        ArrayList<InstrumetString> instrumetStrings = new ArrayList<>();

        InstrumetString newString1 = new InstrumetString("E", 15);
        InstrumetString newString2 = new InstrumetString("A", 15);
        InstrumetString newString3 = new InstrumetString("D", 15);
        InstrumetString newString4 = new InstrumetString("G", 15);
        InstrumetString newString5 = new InstrumetString("B", 15);
        InstrumetString newString6 = new InstrumetString("E", 15);
        instrumetStrings.add(newString1);
        instrumetStrings.add(newString2);
        instrumetStrings.add(newString3);
        instrumetStrings.add(newString4);
        instrumetStrings.add(newString5);
        instrumetStrings.add(newString6);


        assertTrue(chord.isCorrect(chordTones, instrumetStrings));
    }

    @Test
    @DisplayName("isCorrect Missing note")
    void isCorrectMissingNote() {
        Chord chord = new Chord(6);
        chord.addTone(0);
        chord.addTone(2);
        chord.addTone(0);
        chord.addTone(1);
        chord.addTone(0);
        chord.addTone(3);


        ArrayList<String> chordTones = new ArrayList<>(List.of("B","G","D"));
        ArrayList<InstrumetString> instrumetStrings = new ArrayList<>();

        InstrumetString newString1 = new InstrumetString("E", 15);
        InstrumetString newString2 = new InstrumetString("A", 15);
        InstrumetString newString3 = new InstrumetString("D", 15);
        InstrumetString newString4 = new InstrumetString("G", 15);
        InstrumetString newString5 = new InstrumetString("B", 15);
        InstrumetString newString6 = new InstrumetString("E", 15);
        instrumetStrings.add(newString1);
        instrumetStrings.add(newString2);
        instrumetStrings.add(newString3);
        instrumetStrings.add(newString4);
        instrumetStrings.add(newString5);
        instrumetStrings.add(newString6);


        assertFalse(chord.isCorrect(chordTones, instrumetStrings));
    }
    @Test
    @DisplayName("isCorrect With Non-played strings")
    void isCorrectWithNonPlayedStrings() {
        Chord chord = new Chord(6);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(5);
        chord.addTone(7);
        chord.addTone(0);
        chord.addTone(7);


        ArrayList<String> chordTones = new ArrayList<>(List.of("B","G","D"));
        ArrayList<InstrumetString> instrumetStrings = new ArrayList<>();

        InstrumetString newString1 = new InstrumetString("E", 15);
        InstrumetString newString2 = new InstrumetString("A", 15);
        InstrumetString newString3 = new InstrumetString("D", 15);
        InstrumetString newString4 = new InstrumetString("G", 15);
        InstrumetString newString5 = new InstrumetString("B", 15);
        InstrumetString newString6 = new InstrumetString("E", 15);
        instrumetStrings.add(newString1);
        instrumetStrings.add(newString2);
        instrumetStrings.add(newString3);
        instrumetStrings.add(newString4);
        instrumetStrings.add(newString5);
        instrumetStrings.add(newString6);


        assertTrue(chord.isCorrect(chordTones, instrumetStrings));
    }

    @Test
    @DisplayName("isCorrect With ALl Non-played strings")
    void isCorrectWithAllNonPlayedStrings() {
        Chord chord = new Chord(6);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(-1);
        chord.addTone(-1);


        ArrayList<String> chordTones = new ArrayList<>(List.of("B","G","D"));
        ArrayList<InstrumetString> instrumetStrings = new ArrayList<>();

        InstrumetString newString1 = new InstrumetString("E", 15);
        InstrumetString newString2 = new InstrumetString("A", 15);
        InstrumetString newString3 = new InstrumetString("D", 15);
        InstrumetString newString4 = new InstrumetString("G", 15);
        InstrumetString newString5 = new InstrumetString("B", 15);
        InstrumetString newString6 = new InstrumetString("E", 15);
        instrumetStrings.add(newString1);
        instrumetStrings.add(newString2);
        instrumetStrings.add(newString3);
        instrumetStrings.add(newString4);
        instrumetStrings.add(newString5);
        instrumetStrings.add(newString6);


        assertFalse(chord.isCorrect(chordTones, instrumetStrings));
    }


}