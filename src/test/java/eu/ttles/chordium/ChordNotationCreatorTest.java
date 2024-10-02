package eu.ttles.chordium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChordNotationCreatorTest {

    @Test
    @DisplayName("getTonesByChordName C maj")
    void getTonesByChordNameCmaj() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        ArrayList<String> tones = new ArrayList<>(List.of("C","E","G"));
        assertEquals(tones, chordNotationCreator.getTonesByChordName("C", "maj"));
    }
    @Test
    @DisplayName("getTonesByChordName G min, with lowe/upper case different")
    void getTonesByChordNameWithOneString() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        ArrayList<String> tones = new ArrayList<>(List.of("G","A#","D"));
        assertEquals(tones, chordNotationCreator.getTonesByChordName("gMIN"));
    }
    @Test
    @DisplayName("getTonesByChordName G maj7, with overflow")
    void getTonesByChordNameGmaj7Overflow() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        ArrayList<String> tones = new ArrayList<>(List.of("G", "B", "D", "F#"));
        assertEquals(tones, chordNotationCreator.getTonesByChordName("G", "maj7"));
    }
    @Test
    @DisplayName("getTonesByChordName bad base")
    void getTonesByChordNameBadName() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chordNotationCreator.getTonesByChordName("X", "maj"));
        assertEquals("type/base not accepted", exception.getMessage());
    }

    @Test
    @DisplayName("getTonesByChordName bad type")
    void getTonesByChordNameBadType() {
        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> chordNotationCreator.getTonesByChordName("C", "test"));
        assertEquals("type/base not accepted", exception.getMessage());
    }


    @Test
    void testGetTonesByChordName() {
    }
}