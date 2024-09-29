package eu.ttles.chordium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstrumetStringTest {

    @Test
    @DisplayName("getStringGofLength20")
    void getStringGofLength20(){
        InstrumetString string = new InstrumetString("E", 20);
        ArrayList<String> list = new ArrayList<>(List.of("E", "F", "F#","G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
        assertEquals(list, string.getThisString());

    }
    @Test
    @DisplayName("getStringGofLength12")
    void getStringGofLength12(){
        InstrumetString string = new InstrumetString("E", 12);
        ArrayList<String> list = new ArrayList<>(List.of("E", "F", "F#","G", "G#", "A", "A#", "B", "C", "C#", "D", "D#"));
        assertEquals(list, string.getThisString());

    }
    @Test
    @DisplayName("getStringEofLength20")
    void getStringEofLength20(){
        InstrumetString string = new InstrumetString("G", 20);
        ArrayList<String> list = new ArrayList<>(List.of("G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D"));
        assertEquals(list, string.getThisString());

    }

    @Test
    @DisplayName("findTonesEFromE")
    void findTonesEFromE(){
        InstrumetString string = new InstrumetString("E", 15);
        ArrayList<Integer> list = new ArrayList<>(List.of(0,12));
        assertEquals(list, string.findTones("E"));
    }
    @Test
    @DisplayName("findTonesGFromE")
    void findTonesGFromE(){
        InstrumetString string = new InstrumetString("E", 15);
        ArrayList<Integer> list = new ArrayList<>(List.of(3));
        assertEquals(list, string.findTones("G"));
    }
    @Test
    @DisplayName("findTonesXFromE")
    void findTonesXFromE(){
        InstrumetString string = new InstrumetString("E", 15);
        ArrayList<Integer> list = new ArrayList<>();
        assertEquals(list, string.findTones("X"));
    }

    @Test
    @DisplayName("findToneByPosition1")
    void findToneByPosition1(){
        InstrumetString string = new InstrumetString("E", 15);
        assertEquals("E", string.findToneByPosition(0));
    }
    @Test
    @DisplayName("findToneByPosition3")
    void findToneByPosition3(){
        InstrumetString string = new InstrumetString("E", 15);
        assertEquals("G", string.findToneByPosition(3));
    }
    @Test
    @DisplayName("findToneByPositionException")
    void findToneByPositionException(){
        InstrumetString string = new InstrumetString("E", 15);
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> string.findToneByPosition(20));
        assertEquals("position index out of bounds", exception.getMessage());
    }
    @Test
    @DisplayName("findToneByPositionNegativeIndex")
    void findToneByPositionNegativeIndex(){
        InstrumetString string = new InstrumetString("E", 15);
        assertEquals("none", string.findToneByPosition(-1));
    }
}