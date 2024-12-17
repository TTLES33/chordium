package eu.ttles.chordium.utils;
import eu.ttles.chordium.api.ApiRequest;
import eu.ttles.chordium.api.ChordsApiResponse;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class ChordFinder {

    private final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};

    //array of generated chords
    private ArrayList<Chord> chords;

    //array of needed tones to form a chord
    private ArrayList<ArrayList<String>> chordTones ;

    //array of actual tones to form a chord
    private ArrayList<ArrayList<String>> actualChordTones ;

    //array of stings (notes on each string) STRING ARE COUNTED FROM 0 (lowest to highest)
    private ArrayList<InstrumetString> instrumentStrings;

    private int numberOfStrings;
    private int numberOfFrets;
    private int maxChordWidth; // maximum width of chord in frets
    private int maxNumberOfFingers; //maximum numbers of fingers available to play the chord


    private int zkouseneKombinace = 0;

    private final ChordNotationCreator chordNotationCreator;



    public ChordFinder() {
        this.chordNotationCreator = new ChordNotationCreator();
    }


    public void findChord(String base, String type, int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning, int maxChordWidth, int maxNumberOfFingers) throws IllegalArgumentException {

        //check if chord finder can be created
        argumentChecker(numberOfStrings, numberOfFrets, instrumentTuning);


        //set class variables from arguments
        this.numberOfStrings = numberOfStrings;
        this.numberOfFrets = numberOfFrets;
        this.maxChordWidth = maxChordWidth;
        this.maxNumberOfFingers = maxNumberOfFingers;
        this.chords = new ArrayList<>();
        this.chordTones = new ArrayList<>();
        this.instrumentStrings = new ArrayList<>();



        //get tones to form a chord
        if(type.isBlank()){
            chordNotationCreator.generateTonesByChordName(base);
        }else{
            chordNotationCreator.generateTonesByChordName(base, type);
        }

        chordTones = chordNotationCreator.getWorkingChordTones();
        actualChordTones = chordNotationCreator.getActualChordTones();


        //generate tones of each string
        this.generateStrings(numberOfFrets, instrumentTuning);


        //run findAllChords function for every possible tone combination in chord
        for(ArrayList<String> chordTonesArray : chordTones){
            ArrayList<Integer> frets = new ArrayList<>();
            ArrayList<String> usedTones = new ArrayList<>();

            this.findAllChords(chordTonesArray, this.numberOfStrings - 1, frets, -1, usedTones);
        }



    }
    public void findChord(String basePlusType, int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning){
        this.findChord(basePlusType,"", numberOfStrings, numberOfFrets, instrumentTuning, 4, 4);
    }

    //check if chord finder can be created (arguments have right formats)
    private void argumentChecker(int numberOfStrings, int numberOfFrets, ArrayList<String> instrumentTuning) throws IllegalArgumentException{
        if(numberOfStrings != instrumentTuning.size()){
            throw new IllegalArgumentException("Number_of_strings_does_not_match_number_of_tuning_tones");
        }
        if(numberOfFrets <=0){
            throw new IllegalArgumentException("Fret number can't be negative");
        }
    }


    //find all possible combinations of chord by recursively calling itself (finds all possible tones on a string and call itself on the next string)
    private void findAllChords(ArrayList<String> tonesInChord, int currentInstrumentString, ArrayList<Integer> frets, int basePosition, ArrayList<String> usedTones){
        //currentInstrumentString = string on which is function currently searching
        if(currentInstrumentString >= 0){
            InstrumetString actualString = instrumentStrings.get(this.numberOfStrings - 1 - currentInstrumentString);

            //loop through every tone in chord
            for(String chordTone : tonesInChord){

                //find every position on current string for current tone (chordTone)
                ArrayList<Integer> actualTonesPositions = actualString.findTones(chordTone);
                ArrayList<String> usedTonesCopy = new ArrayList<>(usedTones);
                usedTonesCopy.add(chordTone);


                //loop through all positions
                for(int actualTonePosition : actualTonesPositions){

                    //if this iteration is first, set chord base fret position to first note
                    if(basePosition == -1){
                        basePosition = actualTonePosition;
                    }

                    //if new tone is in playable range (+- 4 frets) continue
                    boolean isToneInWidthRange = actualTonePosition  >= (basePosition - maxChordWidth) && actualTonePosition <= (basePosition + maxChordWidth);
                    if(isToneInWidthRange || actualTonePosition == 0){

                        //copy array of previous positions to be passed as argument
                        ArrayList<Integer> newFrets = new ArrayList<>(frets);
                        int newString = currentInstrumentString - 1;

                        newFrets.add(actualTonePosition);

                        //recursive call next string
                        findAllChords(tonesInChord, newString, newFrets, basePosition, usedTonesCopy);
                    }
                }
            }
        }else{
            this.createNewChords(tonesInChord, frets, usedTones);
        }
    }

    //create all possible combinations of found chord - create chords with less played strings
    private void createNewChords(ArrayList<String> tonesInChord, ArrayList<Integer> frets, ArrayList<String> usedTones){


        //create base chord object
        this.createNewChord(frets);

        //increasingly add non played strings from lowest to highest
        for(int i = 0; i < usedTones.size(); i++){

            //copy and remove first element from usedTones array
            ArrayList<String> usedTonesReduced = new ArrayList<>(usedTones);
            usedTonesReduced.removeFirst();

            //if chord still contains all tones needed
            if(usedTonesReduced.containsAll(tonesInChord)){
                //add corresponding non played string to array
                ArrayList<Integer> fretsReduced = new ArrayList<>(frets);
                for(int x = 0; x <= i; x++){
                    fretsReduced.set(x, -1);
                }

                this.createNewChord(fretsReduced);

            }else{
                break; //chord is not correct anymore
            }
        }
    }


    //create chord object and add it array
    private void createNewChord(ArrayList<Integer> frets){

        Chord newChord = new Chord(this.numberOfStrings);
        System.out.println(frets);
        boolean isPossible = newChord.addAllTonesIfPossible(maxChordWidth, maxNumberOfFingers, frets, chordTones, instrumentStrings);
        System.out.println(isPossible);
        if(isPossible){
            chords.add(newChord);
        }
    }


    //generate array of InstrumentStrings, based on the tuning
    private void generateStrings(int numberOfFrets, ArrayList<String> instrumentTuning){
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
        this.sortChords();
        for(Chord chordFound : chords){
            chordFound.printString();
        }
    }



    //form an api response and return it
    public ChordsApiResponse getApiResponse(ApiRequest apiRequest){



        //create new response body
        ChordsApiResponse chordsApiResponse = new ChordsApiResponse();
        chordPattern currentChordPattern = chordNotationCreator.getCurrentChordPattern();

        //fill the body with values
        chordsApiResponse.setChordName(currentChordPattern.getFullName());
        chordsApiResponse.setAliases(currentChordPattern.getAliases());
        chordsApiResponse.setIntervals(currentChordPattern.getIntervals());
        chordsApiResponse.setTones(this.actualChordTones);




        //return transposed / or api chord values
        if(apiRequest.equals(ApiRequest.TRANSPOSED)){
            chordsApiResponse.setChords(getTransposedChords());
        }else{
            chordsApiResponse.setChords(getApiChords());
        }

        return chordsApiResponse;
    }

    //return all chords to api (transposed to be best used with SVGuitar
    public ArrayList<Map<String, Object>> getTransposedChords(){
        this.sortChords();

        ArrayList<Map<String, Object>> transposedChords = new ArrayList<>();
        System.out.println("Length: " + chords.size());
        for(Chord chord : chords){
            System.out.println(chord.getApiValues());
            transposedChords.add(chord.getTransposedValues());
        }
        return transposedChords;
    }

    //return all chords to generic api
    public ArrayList<Map<String, Object>> getApiChords(){
        this.sortChords();
        ArrayList<Map<String, Object>> apiValues = new ArrayList<>();
        for(Chord chord : chords){
            apiValues.add(chord.getApiValues());
        }
        return apiValues;
    }

    public ArrayList<Chord> getChords(){
        this.sortChords();
        return this.chords;
    }



    //sort chord array by its complexityScore
    private void sortChords(){
        Collections.sort(chords);
    }

    //return info about all chord types (for api)
    public HashSet<chordPattern> getChordPatterns(){
        return chordNotationCreator.getChordPatterns();
    }
}
