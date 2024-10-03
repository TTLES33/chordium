package eu.ttles.chordium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ChordNotationCreator {
    //todo: crate array with b notes
    private final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};

    HashMap<String, int[]> notationMap;

    public ChordNotationCreator() {
        //add all types to hashmap with intervals
        this.notationMap = new HashMap<>();
//        notationMap.put("maj", new int[]{0, 4, 7});
//        notationMap.put("min", new int[]{0,3,7});
//        notationMap.put("aug", new int[]{0,4,8});
//        notationMap.put("dim", new int[]{0,3,6});
//        notationMap.put("7", new int[]{0,4,7,10});
//        notationMap.put("maj7", new int[]{0,4,7,11,});
//        notationMap.put("dim7", new int[]{0,3,6,9});
//        notationMap.put("min7", new int[]{0,3,7,10});
//        notationMap.put("mmaj7", new int[]{0,3,7,11});
//        notationMap.put("aug7", new int[]{0,4,6,10});
//        notationMap.put("9", new int[]{0,4,7,10,2});
//        notationMap.put("11", new int[]{0,4,97,10,2,5});//9 - can be omitted
//        notationMap.put("13", new int[]{0,4,7,10,2,95,9});
//        notationMap.put("2", new int[]{0,4,7,2});
//        notationMap.put("4", new int[]{0,4,7,5});
//        notationMap.put("6", new int[]{0,4,7,9});
//        notationMap.put("6/9", new int[]{0,4,7,9,2});
//        notationMap.put("7/6", new int[]{0,4,7,9,10});
//        notationMap.put("sus2", new int[]{0,2,7});
//        notationMap.put("sus4", new int[]{0,5,7});
//        notationMap.put("9sus4", new int[]{0,5,7,10,2});
//        notationMap.put("7sus4", new int[]{0,5,7,10});
//        notationMap.put("7sus2", new int[]{0,2,7,10});
        loadChordIntervalsFromFile();
    }

    private void loadChordIntervalsFromFile(){


        try{
            Scanner scanner = new Scanner(new File("src/main/java/eu/ttles/chordium/chordIntervals.txt"));
            while(scanner.hasNextLine()){
                String type = scanner.nextLine();
                String[] data = scanner.nextLine().split("-");

                //replace all "b" with "-"
                data = Arrays.stream(data).map(o -> o.replace("b","-")).toArray(String[]::new);

                //pass to addChordIntervalsToHashMap interval array with removed "(" and ")" and parsed do Integers
                //todo: replace () with 9
                addChordIntervalsToHashMap(Arrays.stream(data).map(o -> o.replace("(", "") .replace(")", "")).mapToInt((o -> Integer.parseInt(o))).toArray());


            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //todo: maybe remove method
    private void addChordIntervalsToHashMap(int[] intervals){

        for(int data : intervals){
            System.out.println(data);
        }
    }



    //todo: handle 9x intervals and notes with b ( return realne noty s beckama, plus s křížkama pro program
    //get chord tones by its name (base + type)
    public ArrayList<String> getTonesByChordName(String base, String type) throws IllegalArgumentException{

        base = base.toUpperCase();
        type = type.toLowerCase();


        //if type and base exists
        if(this.notationMap.containsKey(type) && Arrays.asList(this.tones).contains(base)){

            //get intervals
            int[] tonesIntervals = this.notationMap.get(type);
            ArrayList<String> chordTones = new ArrayList<>();

            //find index of base tone in tones array
            int firstIndex = 0;
            for(int i = 0; i < this.tones.length; i++){
                if(this.tones[i].equalsIgnoreCase(base)) {
                    firstIndex = i;
                    break;
                }
            }

            for(int i = 0; i < tonesIntervals.length; i++){
                //find index of next tone
                int actualIndex = firstIndex + tonesIntervals[i];
                //if index out of bounds, revert to start
                if(actualIndex >= 12){
                    actualIndex = actualIndex - 12;
                }
                //add tone by interval
                chordTones.add(this.tones[actualIndex]);
            }

            return chordTones;

        }else{
            throw new IllegalArgumentException("type/base not accepted");
        }
    }

    //get chord name by full string
    public ArrayList<String> getTonesByChordName(String chordName) {

        //split string to base and type
        String base = chordName.substring(0, 1);
        String type = chordName.substring(1);

        //call parent function
        return this.getTonesByChordName(base, type);
    }

}
