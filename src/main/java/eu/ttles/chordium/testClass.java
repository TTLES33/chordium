package eu.ttles.chordium;

import eu.ttles.chordium.utils.ChordNotationCreator;

public class testClass {


    public static void main(String[] args) {

        boolean test = false;

        System.out.println(test);

       ChordNotationCreator chordNotationCreator = new ChordNotationCreator();
//
//
        System.out.println("test");
        chordNotationCreator.generateTonesByChordName("C", "maj13");
        chordNotationCreator.printWorkingChordTones();
//        System.out.println("------");
//        chordNotationCreator.printActualChordTones();



    }

}
