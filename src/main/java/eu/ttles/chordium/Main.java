package eu.ttles.chordium;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        ArrayList<String> tuning = new ArrayList<>();
        tuning.add("E");
        tuning.add("A");
        tuning.add("D");
        tuning.add("G");
        tuning.add("B");
        tuning.add("E");


        ChordFinder chordFinder = new ChordFinder();

        chordFinder.findChord("C", 6,15, tuning);
        chordFinder.printChords();

        System.out.println("--------G7--------------");
        chordFinder.findChord("G","7", 6,15, tuning);
        chordFinder.printChords();

    }
}