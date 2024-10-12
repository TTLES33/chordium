package eu.ttles.chordium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChordNotationCreatorTest {

    @Test
    @DisplayName("generateTonesByChordName C maj")
    void generateTonesByChordNameCmaj() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("C", "maj");

        ArrayList<String> tones = new ArrayList<>(List.of("C","E","G"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones);

        assertEquals(container, chordNotationCreator.getActualChordTones());
    }
    @Test
    @DisplayName("generateTonesByChordName C (maj not specified)")
    void generateTonesByChordNameCmaj_typeNotSpecified() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("C");

        ArrayList<String> tones = new ArrayList<>(List.of("C","E","G"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones);

        assertEquals(container, chordNotationCreator.getActualChordTones());
    }

    @Test
    @DisplayName("generateTonesByChordName C maj13 (skipped tones)")
    void generateTonesByChordNameCmaj13() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("C", "maj13");

        ArrayList<String> tones = new ArrayList<>(List.of("C","E","G","B","A"));
        ArrayList<String> tones2 = new ArrayList<>(List.of("C","E","G","B","D", "A"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones2);
        container.add(tones);

        assertEquals(container, chordNotationCreator.getActualChordTones());
    }

    @Test
    @DisplayName("generateTonesByChordName C min")
    void generateTonesByChordNameWithOneString() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("cmin");

        ArrayList<String> tones = new ArrayList<>(List.of("C","Eb","G"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones);

        assertEquals(container, chordNotationCreator.getActualChordTones());
    }

    @Test
    @DisplayName("generateTonesByChordName C min (working intervals)")
    void generateTonesByChordNameWithOneStringWorkingIntervals() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("cmin");

        ArrayList<String> tones = new ArrayList<>(List.of("C","D#","G"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones);

        assertEquals(container, chordNotationCreator.getWorkingChordTones());
    }


    @Test
    @DisplayName("generateTonesByChordName G maj7, with overflow")
    void generateTonesByChordNameGmaj7Overflow() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        chordNotationCreator.generateTonesByChordName("G", "maj7");


        ArrayList<String> tones = new ArrayList<>(List.of("G", "B", "D", "F#"));
        ArrayList<ArrayList<String>> container = new ArrayList<>();
        container.add(tones);

        assertEquals(container, chordNotationCreator.getWorkingChordTones());
    }
    @Test
    @DisplayName("generateTonesByChordName bad base")
    void generateTonesByChordNameBadName() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chordNotationCreator.generateTonesByChordName("X", "maj"));
        assertEquals("base not accepted", exception.getMessage());
    }

    @Test
    @DisplayName("generateTonesByChordName bad type")
    void generateTonesByChordNameBadType() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chordNotationCreator.generateTonesByChordName("C", "test"));
        assertEquals("type not accepted", exception.getMessage());
    }


    @Test
    void testgenerateTonesByChordName() {
    }
}