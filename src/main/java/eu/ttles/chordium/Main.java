package eu.ttles.chordium;


import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        System.out.println("test");
        ArrayList<String> tuning = new ArrayList<>();
        tuning.add("E");
        tuning.add("A");
        tuning.add("D");
        tuning.add("G");
        tuning.add("B");
        tuning.add("E");

        //ChordFinder gdur = new ChordFinder("G", "major", 6,15, tuning);



        //gdur.printChords();

        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
        System.out.println(chordNotationCreator.getTonesByChordName("C","7"));


    }
}