package eu.ttles.chordium;

import eu.ttles.chordium.api.ApiController;
import eu.ttles.chordium.utils.Chord;
import eu.ttles.chordium.utils.ChordFinder;

import java.util.ArrayList;

public class testClass {


    public static void main(String[] args) {

        ArrayList<Chord> chord = test("C#", "maj");

        for(Chord chord1 : chord) {
            System.out.println(chord1);
        }



    }


    public static ArrayList<Chord> test(String base,String type) {

        ChordFinder chordFinder = new ChordFinder();

        String tuning = "EADGBE";
        int numberOfStrings = 6;
        int numberOfFrets = 15;
        //Create tuning ArrayList from String
        ApiController.createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets);

        return chordFinder.getChords();

    }

}
