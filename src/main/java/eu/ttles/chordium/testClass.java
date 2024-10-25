package eu.ttles.chordium;

import eu.ttles.chordium.utils.Chord;
import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.ChordNotationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
        ArrayList<String> tuningList = new ArrayList<>();
        for (int i = 0; i < tuning.length(); i++) {
            tuningList.add(String.valueOf(tuning.charAt(i)));
        }


        try {
            chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return chordFinder.getChords();

    }

}
