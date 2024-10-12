package eu.ttles.chordium;

import java.util.ArrayList;
import java.util.Arrays;


public class ChordFinder {

    private final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};

    //array of generated chords
    private ArrayList<Chord> chords;

    //array of needed tones to form a chord
    private ArrayList<ArrayList<String>> chordTones ;

    //array of stings (notes on each string) STRING ARE COUNTED FROM 0 (lowest to highest)
    private ArrayList<InstrumetString> instrumentStrings;

    private int numberOfStrings;
    private int numberOfFrets;
    private ArrayList<String> aliases;

    private final ChordNotationCreator chordNotationCreator;


    public ChordFinder() {
        this.chordNotationCreator = new ChordNotationCreator();
    }


    public void findChord(String base, String type, int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning){
        this.numberOfStrings = numberOfStrings;
        this.numberOfFrets = numberOfFrets;
        this.chords = new ArrayList<>();
        this.chordTones = new ArrayList<>();
        this.instrumentStrings = new ArrayList<>();



        //get tones to form a chord
        try {
            if(type.isBlank()){
                chordNotationCreator.generateTonesByChordName(base);
            }else{
                chordNotationCreator.generateTonesByChordName(base, type);
            }

            chordTones = chordNotationCreator.getWorkingChordTones();
            this.aliases = chordNotationCreator.getAliases();



        }catch (Exception e) {
            if(e.getMessage().equals("base not accepted")){
                System.out.println("base not accepted");
            } else if (e.getMessage().equals("type not accepted")) {
                System.out.println("type not accepted");
            }else{
                System.out.println(e.getMessage());
            }
        }


        //generate tones of each string
        this.generateStrings(numberOfStrings, numberOfFrets, instrumentTuning);

        //find all possible chord, runs function with increasing number of non-played strings (only from the lowest string)
        for(int i = 0; i < instrumentStrings.size() - 2; i++){
            //add first frets (by i) as -1 (non-played string)
            ArrayList<Integer> frets = new ArrayList<>();
            for(int j = 0; j < i; j++){
                frets.add(-1);
            }
            for(ArrayList<String> chordTonesArray : chordTones){
                this.findAllChords(chordTonesArray, i, frets);
            }
        }


    }
    public void findChord(String basePlusType, int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning){
        this.findChord(basePlusType,"", numberOfStrings, numberOfFrets, instrumentTuning);
    }


    //todo: možná rozdělit na podfunkce
    //find all possible combinations of chord by recursively calling itself (finds all possible tones on a string and call itself on the next string)
    private void findAllChords(ArrayList<String> tonesInChord, int currentInstrumentString, ArrayList<Integer> frets){
        //currentInstrumentString = string on which is function currently searching
        if(currentInstrumentString < this.numberOfStrings){
            InstrumetString actualString = instrumentStrings.get(currentInstrumentString);

            //loop through every tone in chord
            for(String chordTone : tonesInChord){

                //find every position on current string for current tone (chordTone)
                ArrayList<Integer> actualTonesPositions = actualString.findTones(chordTone);

                //loop through all positions
                for(int actualTonePosition : actualTonesPositions){

                    //copy array of previous positions to be passed as argument
                    ArrayList<Integer> newFrets = new ArrayList<>(frets);
                    int newString = currentInstrumentString + 1;

                    newFrets.add(actualTonePosition);
                    findAllChords(tonesInChord, newString, newFrets);
                }
            }
        }else{
            this.createNewChord(numberOfStrings, frets);
        }

    }

    //create chord object and add it array
    private void createNewChord(int numberOfStrings, ArrayList<Integer> frets){


        Chord newChord = new Chord(numberOfStrings);

        for(int fretPosition : frets){
            newChord.addTone(fretPosition);
        }



        if(newChord.isPlayable(4) && newChord.isCorrect(chordTones, instrumentStrings)){
            chords.add(newChord);
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
    public ArrayList<String> getAliases(){
        return this.aliases;
    }

}
