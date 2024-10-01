package eu.ttles.chordium;

import java.util.ArrayList;
import java.util.Arrays;


public class ChordFinder {

    private final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};

    //array of generated chords
    private ArrayList<Chord> chords = new ArrayList<>();

    //array of needed tones to form a chord
    private ArrayList<String> chordTones = new ArrayList<>();

    //array of stings (notes on each string) STRING ARE COUNTED FROM 0 (lowest to highest)
    private ArrayList<InstrumetString> instrumentStrings = new ArrayList<>();

    private final int numberOfStrings;
    private final int numberOfFrets;

    public ChordFinder(String base, String type, int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning) {
        this.numberOfStrings = numberOfStrings;
        this.numberOfFrets = numberOfFrets;

        //get tones to form a chord
        this.generateChordNotes(base, type);

        //generate tones of each string
        this.generateStrings(numberOfStrings, numberOfFrets, instrumentTuning);

        //find all possible chord, runs function with increasing number of non-played strings (only from the lowest string)
        for(int i = 0; i < instrumentStrings.size() - 2; i++){
            //add first frets (by i) as -1 (non-played string)
            ArrayList<Integer> frets = new ArrayList<>();
            for(int j = 0; j < i; j++){
                frets.add(-1);
            }
            this.findAllChords(chordTones, i, frets);
        }


    }
    //todo: možná rozdělit na podfunkce
    //find all possible combinations of chord by recursively calling itself (finds all possible tones on a string and call itself on the next string)
    private void findAllChords(ArrayList<String> tonesInChord, int actualInstrumentString, ArrayList<Integer> frets){

        //actualInstrumentString = string on which is function currently searching
        if(actualInstrumentString < this.numberOfStrings){
            InstrumetString actualString = instrumentStrings.get(actualInstrumentString);

            //loop through every tone in chord
            for(String chordTone : tonesInChord){
                //System.out.println(chordTone);

                //find every position on current string for current tone (chordTone)
                ArrayList<Integer> actualTonesPositions = actualString.findTones(chordTone);

                //loop through all positions
                for(int actualTonePosition : actualTonesPositions){

                    //copy array of previos positions to be passed as argument
                    ArrayList<Integer> newFrets = new ArrayList<>(frets);
                    int newString = actualInstrumentString + 1;

                    newFrets.add(actualTonePosition);
                    findAllChords(tonesInChord, newString, newFrets);
                }
            }
        }else{
            //create chord object and add it array
            Chord newChord = new Chord(numberOfStrings);
            for(int fretPosition : frets){
                newChord.addTone(fretPosition);
            }

            //chords.add(newChord);
            if(newChord.isPlayable(4) && newChord.isCorrect(chordTones, instrumentStrings)){
                chords.add(newChord);
            }

        }

    }


    //generate all notes, chord should have
    private void generateChordNotes(String base, String type){
        if(base.equals("G") && type.equals("major")){
            this.chordTones.add("G");
            this.chordTones.add("B");
            this.chordTones.add("D");
        }
    }

    //generate array of InstrumentStrings, based on the tuning
    private void generateStrings(int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning){
        for(String stringTuning : instrumentTuning){
            if(Arrays.asList(tones).contains(stringTuning)){
                InstrumetString newString = new InstrumetString(stringTuning, numberOfFrets);
                instrumentStrings.add(newString);
            }else{
                throw new IllegalArgumentException(stringTuning + " is not a valid tone");
            }

        }
    }


    public void printStrings(){
        for(InstrumetString instrumetStringFound : instrumentStrings){
            instrumetStringFound.printString();
            System.out.println();
        }
    }

    public void printChords(){
        for(Chord chordFound : chords){
            chordFound.printString();
            //System.out.println(chordFound.isCorrect(chordTones, instrumentStrings));
            //System.out.println();
        }
    }

}
