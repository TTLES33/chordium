package eu.ttles.chordium;

import eu.ttles.chordium.api.ApiController;
import eu.ttles.chordium.utils.Chord;
import eu.ttles.chordium.utils.ChordFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class testClass {


    public static void main(String[] args) {

        System.out.println("test");

        File f = new File("src/main/java/eu/ttles/chordium/utils/chordIntervals.txt");
        try {
            System.out.println(f.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
