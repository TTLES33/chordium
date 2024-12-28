package eu.ttles.chordium.utils;

import java.util.*;

public class Chord implements Comparable<Chord>{

    //fret positions of each tone (all strings) - from lowest to highests
    ArrayList<Integer> tonesPositions;
    private int position = 1; //position of chord on fretboard

    private int barrePosition = 0;
    private int barreStartString = 0;
    private int barreEndString = 0;
    private int barreNumberOfPlayedStrings = 0; // number of string, which are played by the barre (e.g.: Fmaj - [1,3,3,2,1,1] -> barreNumberOfSoundingStrings = 3

    private int chordWidth = 3;

    private final int numberOfStrings;

    public int compareScore = 0;






    public Chord(int numberOfStrings){
        this.numberOfStrings = numberOfStrings;
        this.tonesPositions = new ArrayList<>();
    }

    //adds tone to the chord - depressed
    public void addTone(int tonePosition){
        //check if number of already added tones is less than number of strings
        if(this.tonesPositions.size() < this.numberOfStrings){

            this.tonesPositions.add(tonePosition);
            if(isComplete()){
                this.findBarre();
                this.findChordWidth();
                this.computeScore();

            }
        }else{
            throw new IllegalArgumentException("Tones out of bounds, trying to set tone on string number " + (this.tonesPositions.size() + 1) + ", number of strings: " + this.numberOfStrings);
        }
    }

    //adds all tones to the chord
    public boolean addAllTonesIfPossible(int maxChordWidth, int maxNumberOfFingers, ArrayList<Integer> frets, ArrayList<ArrayList<String>> chordTones, ArrayList<InstrumetString> instrumentStrings){

        //check for exceeded number of tones
        if(frets.size() != this.numberOfStrings){
            return false;
            //throw new IllegalArgumentException("exceeds number of tones");
        }

        //add all tones to chord
        this.tonesPositions.addAll(frets);


        //check if chord is musically correct
        if(!isCorrect(chordTones, instrumentStrings)){
            return false;
        }


        this.findChordWidth();
        if(this.chordWidth > maxChordWidth){
            return false;
        }

        //find barre, and if chord is playable
        this.findBarre();
        if(!this.isPlayable(maxChordWidth, maxNumberOfFingers)){
            return false;
        }


        //create all attributes of chord
        this.computeScore();

        return true;
    }

    //checks if chord is created completely (all string have tones set)
    private boolean isComplete(){
        //if chord is made out of only non-played strings
//        if(tonesPositions.stream().allMatch(o -> o == -1)){
//            return false;
//        }
        return (this.numberOfStrings == this.tonesPositions.size());

    }

    //check if chord is physically possible to play
    public boolean isPlayable(int maxWidth, int maxNumberOfFingers){
        if(!isComplete()){
            return false;
        }

        if(this.chordWidth > maxWidth){
            return false;
        }

        HashMap<Integer, Integer> tonesPositionsMap = new HashMap();

        //create hashmap of tones positions counts
        for(Integer tonePosition : this.tonesPositions){
            if(!tonesPositionsMap.containsKey(tonePosition)){
                tonesPositionsMap.put(tonePosition, 1);
            }else{
                tonesPositionsMap.put(tonePosition, tonesPositionsMap.get(tonePosition) + 1);
            }
        }

        //number of empty strings
        //int emptyStrings = (int) tonesPositions.stream().filter(m -> m == 0).count();
        int emptyStrings;
        emptyStrings = tonesPositionsMap.getOrDefault(0, 0);

        //number of non-played strings
        //int skippedStrings = (int) tonesPositions.stream().filter(m -> m == -1).count();
        int skippedStrings;
        skippedStrings = tonesPositionsMap.getOrDefault(-1, 0);


        int barreNumberOfSoundingStringsFingers = barreNumberOfPlayedStrings;
        if(barreNumberOfPlayedStrings > 0){
            barreNumberOfSoundingStringsFingers = barreNumberOfPlayedStrings - 1;
        }


        if((this.numberOfStrings - skippedStrings - barreNumberOfSoundingStringsFingers - emptyStrings) > maxNumberOfFingers){ //4 fingers to play chord
            return false;
        }

        return true;

    }

    //check if the cord is musically correct
    //arguments: tones - tones which the cord should have, instrumentStrings
    public boolean isCorrect(ArrayList<ArrayList<String>> tonesChordShouldHave, ArrayList<InstrumetString> instrumetStrings){

        if(!isComplete()){
            return false;
        }

        //generate array of tones(strings) from int positions
        ArrayList<String> generatedTones = new ArrayList<>();
        for(int i = 0; i < tonesPositions.size(); i++){
            int currentPosition = tonesPositions.get(i);

            //skip non played strings
            if(currentPosition != -1){
                InstrumetString currentString = instrumetStrings.get(i);
                generatedTones.add(currentString.findToneByPosition(currentPosition));
            }
        }

        //hashset of unique generated tones
        HashSet<String> uniqueElements = new HashSet<>(generatedTones);


//        System.out.println("tonesChordShouldHave: " + tonesChordShouldHave);
//        System.out.println("generatedTones: " + generatedTones);
//        System.out.println("uniqueElements: " + uniqueElements);
//        System.out.println("tonesPositions: " + tonesPositions);

        // Iterate over each sublist in tonesChordShouldHave
        for (ArrayList<String> sublist : tonesChordShouldHave) {
            // Check if the current sublist contains all elements from uniqueElements and vise versa
            if (sublist.containsAll(uniqueElements) && uniqueElements.containsAll(sublist)) {
                //System.out.println(true);
                return true; // If all elements are found in a sublist, return true

            }
        }

       //System.out.println(false);
        return false;

    }

    //finds width of chord on fretboard (+ find chord position)
    private void findChordWidth(){
        if(!isComplete()){
            throw new RuntimeException("Chord not complete");
        }

        int minPostition = this.tonesPositions.getFirst();
        int maxPostition = this.tonesPositions.getFirst();

        for(int i = 1; i <this.tonesPositions.size(); i++){

            int currentPosition = this.tonesPositions.get(i);
            //if current position < previous minPosition, and it is > 0 or minPosition is non-played string
            if((currentPosition < minPostition && currentPosition > 0)   ||  minPostition == -1) {
                minPostition = currentPosition;
            }
            if(currentPosition > maxPostition){
                maxPostition = currentPosition;
            }
        }

        this.position = minPostition;

        if(minPostition == 0){
            this.chordWidth = maxPostition - minPostition;
        }else{
            this.chordWidth = maxPostition - minPostition  + 1;
        }

    }



    //find barre in chord
    public void findBarre(){

        HashMap<Integer, Integer> tonesPositionsMap= new HashMap<>();
        int lowestTone = Integer.MAX_VALUE;

        //fill tonesPositionsMap with tones counts and find lowest tone
        for (int currentPosition : tonesPositions) {
            //if string is played
            if (currentPosition != -1) {
                //check for lowest tone
                if (currentPosition < lowestTone) {
                    lowestTone = currentPosition;
                }
                //add tone count to hashmap
                if (!tonesPositionsMap.containsKey(currentPosition)) {
                    tonesPositionsMap.put(currentPosition, 1);
                } else {
                    tonesPositionsMap.put(currentPosition, tonesPositionsMap.get(currentPosition) + 1);
                }
            }
        }



        //if chord doesn't start at fret 0, if two lowest tones are same (min 2 tones to form a barre)
        if(lowestTone != 0 && tonesPositionsMap.get(lowestTone) > 1){
            this.barrePosition = lowestTone;

            this.barreNumberOfPlayedStrings = tonesPositionsMap.get(lowestTone);

            for(int i = 0; i < this.tonesPositions.size(); i++ ){
                if(this.tonesPositions.get(i) == this.barrePosition){
                        this.barreStartString = i;
                        this.barreEndString = this.numberOfStrings - 1;
                        break;
                }
            }
        }
    }

    //calculate chord complexity score to comparing
    private void computeScore(){
        HashMap<Integer, Integer> tonesCounts = new HashMap<>();

        //fill tonesCounts hashmap with tones counts
        for (int currentPosition : this.tonesPositions) {
            if (!tonesCounts.containsKey(currentPosition)) {
                tonesCounts.put(currentPosition, 1);
            } else {
                tonesCounts.put(currentPosition, tonesCounts.get(currentPosition) + 1);
            }
        }

        //number of non-played strings
        int skippedStrings = 0;
        if(tonesCounts.containsKey(-1)){
            skippedStrings = tonesCounts.get(-1);
        }

        //number of empty strings
        int emptyStrings = 0;
        if(tonesCounts.containsKey(0)){
            emptyStrings = tonesCounts.get(0);
        }


        int numberOfPlayedString = this.numberOfStrings - skippedStrings - emptyStrings;

        this.compareScore = 100 - 2*this.position - 2*this.chordWidth - numberOfPlayedString;

        if(this.barreEndString != 0 || this.barreStartString != 0){
            this.compareScore = this.compareScore - 1;
        }


        if(skippedStrings >= numberOfStrings/2){
            this.compareScore = this.compareScore - 15;
        }
        if(skippedStrings  +  emptyStrings == this.numberOfStrings){
            this.compareScore = this.compareScore - 50;
        }
    }

    //prints all tones of chord on one line
    public void printString(){
        for (Integer tonesPosition : this.tonesPositions) {
            System.out.print(tonesPosition + " ");
        }
        System.out.println();
        //System.out.println("Barre: " + this.barrePosition + ": " + this.barreStartString + " " + this.barreEndString + " soundingStrings: " + this.barreNumberOfPlayedStrings);
        //System.out.println("Width:" + this.chordWidth);
       // System.out.println("isPlayable: " + this.isPlayable(4)  );
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Integer tonesPosition : this.tonesPositions) {
            sb.append(tonesPosition).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }


    //generate output for generic API
    public Map<String, Object> getApiValues(){
        //returning HashMap
        Map<String, Object> apiValues = new HashMap<>();

        //create returning hashmap
        apiValues.put("chordWidth", this.chordWidth);
        apiValues.put("chordPosition", this.position);
        apiValues.put("barreStartString", barreStartString);
        apiValues.put("barreEndString", barreEndString);
        apiValues.put("barrePosition", barrePosition);
        apiValues.put("tonePositions", tonesPositions);

        return apiValues;
    }

    //generate output for API, that can be best used with SVguitar
    public Map<String, Object> getTransposedValues(){
        //returning HashMap
        Map<String, Object> transposedValues = new HashMap<>();

        //reverse string order
        ArrayList<Integer> reversedTonesPositions = new ArrayList<>(tonesPositions);
        Collections.reverse(reversedTonesPositions);

        //transposed values for api
        int apiPosition = this.position;
        int apiChordWidth = this.chordWidth;
        ArrayList<Integer> transposedTonesPositions = new ArrayList<>();
        ArrayList<Map<String, Integer>> barres = new ArrayList<>();

        //generate frets array
        for (int currentFret : reversedTonesPositions) {
            if (currentFret == -1 || currentFret == 0) {
                transposedTonesPositions.add(currentFret);
            } else {
                if (apiPosition == 0) {
                    transposedTonesPositions.add(currentFret - position);
                } else {
                    transposedTonesPositions.add(currentFret - position + 1);
                }
            }
        }

        //if chord position == 2, expend chord by 1 fret, so it starts at 1st fret
        if(this.position == 2){
            apiPosition = 1;
            apiChordWidth++;
            for(int x = 0; x < transposedTonesPositions.size(); x++){
                int currentFret = transposedTonesPositions.get(x);
                if(currentFret != 0 && currentFret != -1){
                    transposedTonesPositions.set(x, currentFret + 1);
                }
            }
        }
        //min chord width = 4
        if(apiChordWidth < 4){
            apiChordWidth = 4;
        }

        //generate barre object
        if(barreEndString != 0 || barreStartString != 0){
            Map<String, Integer> barre = new HashMap<>();
            barre.put("toString", reversedTonesPositions.size() - barreEndString);
            barre.put("fromString", reversedTonesPositions.size() - barreStartString);
            barre.put("fret", this.barrePosition - this.position + 1);
            barres.add(barre);
        }

        //create returning hashmap
        transposedValues.put("chordWidth", apiChordWidth);
        transposedValues.put("chordPosition", apiPosition);
        transposedValues.put("barres", barres);
        transposedValues.put("tonePositions", transposedTonesPositions);
        transposedValues.put("score", this.compareScore);

        return transposedValues;
    }

    public int getBarrePosition(){
        return this.barrePosition;
    }
    public int getBarreStartString(){
        return this.barreStartString;
    }
    public int getBarreEndString(){
        return this.barreEndString;
    }
    public int getNumberOfStrings(){
        return this.numberOfStrings;
    }

    public int getChordWidth(){
        findChordWidth();
        return this.chordWidth;
    }
    public ArrayList<Integer> getTonePositions(){
        return this.tonesPositions;
    }

    @Override
    public int hashCode(){
        System.out.println(this.tonesPositions);
        return Objects.hashCode(this.tonesPositions);
    }


    @Override
    public int compareTo(Chord o) {
        if(this.compareScore < o.compareScore){
            return 1;
        }else if(this.compareScore > o.compareScore){
            return -1;
        }
        return 0;
    }
}
