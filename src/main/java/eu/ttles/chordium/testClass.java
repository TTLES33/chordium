package eu.ttles.chordium;


import eu.ttles.chordium.utils.ChordFinder;

import java.util.ArrayList;

public class testClass {


    public static void main(String[] args) {


        String base = "C";
        String type = "maj";
        int numberOfStrings = 10;
        int numberOfFrets = 30;

        String tuning = "EADGBEDGBE";
        ChordFinder chordFinder = new ChordFinder();


        ArrayList<String> tuningList = new ArrayList<>();
        for (int i = 0; i < tuning.length(); i++) {
            tuningList.add(String.valueOf(tuning.charAt(i)));
            //look for sharp values
            if(i != tuning.length() - 1) {
                if(tuning.charAt(i+1) == '#') {
                    int tuningListSize = tuningList.size();
                    tuningList.set(tuningListSize - 1, tuningList.get(tuningListSize - 1) + '#');
                    i++;
                }
            }

        }
        System.out.println("tuningList: " + tuningList);

        //try to find chords, else throw error
        try {
            chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, 4,4);
            System.out.println(chordFinder.getApiChords());
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }



}
