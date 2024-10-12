package eu.ttles.chordium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ChordPatternTest {

    @Test
    @DisplayName("All Normal - hasAlias")
    void hasAlias() {
        String name = "major";
        int[] intervals = {1,3,5};
        HashSet<String> set = new HashSet<>();
        set.add("maj");
        set.add("M");
        set.add("(none)");


        chordPattern test = new chordPattern(name, intervals, set);

        assertTrue(test.hasAlias("maj"));
    }

    @Test
    @DisplayName("Error - hasAlias - not containing")
    void hasAliasError() {
        String name = "major";
        int[] intervals = {1,3,5};
        HashSet<String> set = new HashSet<>();
        set.add("maj");
        set.add("M");
        set.add("(none)");


        chordPattern test = new chordPattern(name, intervals, set);

        assertFalse(test.hasAlias("min"));
    }



    @Test
    @DisplayName("Get Name - normal")
    void getName() {
        String name = "major";
        int[] intervals = {1,3,5};
        HashSet<String> set = new HashSet<>();
        set.add("maj");
        set.add("M");
        set.add("(none)");


        chordPattern test = new chordPattern(name, intervals, set);

        assertEquals(name, test.getFullName());
    }

    @Test
    @DisplayName("Get Intervals - normal")
    void getIntervals() {
        String name = "major";
        int[] intervals = {1,3,5};
        HashSet<String> set = new HashSet<>();
        set.add("maj");
        set.add("M");
        set.add("(none)");


        chordPattern test = new chordPattern(name, intervals, set);

        assertEquals(intervals, test.getIntervals());
    }



    @Test
    @DisplayName("getAliases - normal")
    void getAliases() {
        String name = "major";
        int[] intervals = {1,3,5};
        HashSet<String> set = new HashSet<>();
        set.add("maj");
        set.add("M");
        set.add("(none)");


        chordPattern test = new chordPattern(name, intervals, set);

        assertEquals(set, test.getAliases());
    }
}