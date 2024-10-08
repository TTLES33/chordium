package eu.ttles.chordium;

import java.util.ArrayList;

public class testClass {


    public static void main(String[] args) {

        ChordNotationCreator chordNotationCreator = new ChordNotationCreator();


        System.out.println("test");
        chordNotationCreator.generateTonesByChordName("C", "maj13");
        chordNotationCreator.printWorkingChordTones();
        System.out.println("------");
        chordNotationCreator.printActualChordTones();



    }

}
