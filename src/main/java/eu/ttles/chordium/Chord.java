package eu.ttles.chordium;

import java.util.ArrayList;
import java.util.Collections;

public class Chord implements Comparable<Chord>{

    //fret positions of each tone (all strings)
    //private int[] tonesPositions;
    ArrayList<Integer> tonesPositions;
    private int position = 1;

    private int barrePosition = 0;
    private int barreStartString = 0;
    private int barreEndString = 0;
    private int barreNumberOfPlayedStrings = 0; // number of string, which are played by the barre (e.g.: Fmaj - [1,3,3,2,1,1] -> barreNumberOfSoundingStrings = 3

    private int chordWidth = 3;

    private int numberOfStrings;




    public Chord(int numberOfStrings){
        this.numberOfStrings = numberOfStrings;
        this.tonesPositions = new ArrayList<>();
    }

    //adds tone to the chord
    public void addTone(int tonePosition){
        if(this.tonesPositions.size() < this.numberOfStrings){
            this.tonesPositions.add(tonePosition);

            if(isComplete()){
                this.findBarre();
                this.findChordWidth();
            }


        }else{
            throw new IllegalArgumentException("Tones out of bounds, trying to set tone on string number " + (this.tonesPositions.size() + 1) + ", number of strings: " + this.numberOfStrings);
        }
    }

    //checks if chord is created completely (all string have tones set)
    private boolean isComplete(){
        //if chord is made out of only non-played strings
        if(tonesPositions.stream().filter(o -> o == -1).count() == this.numberOfStrings){
            return false;
        }
        return (this.numberOfStrings == this.tonesPositions.size());

    }

    //check if chord is physically possible to play
    public boolean isPlayable(int maxWidth){

        if(!isComplete()){
            return false;
        }
        if(this.chordWidth > maxWidth){
            return false;
        }

        int emptyStrings = (int) tonesPositions.stream().filter(m -> m == 0).count();

        int skippedStrings = (int) tonesPositions.stream().filter(m -> m == -1).count();

        int barreNumberOfSoundingStringsFingers = barreNumberOfPlayedStrings;

        if(barreNumberOfPlayedStrings > 0){
            barreNumberOfSoundingStringsFingers = barreNumberOfPlayedStrings - 1;
        }


        if((this.numberOfStrings - skippedStrings - barreNumberOfSoundingStringsFingers - emptyStrings) > 4){
            return false;
        }
        return true;

    }

    //check if the cord is musically correct
    //arguments: tones - tones which the cord should have, instrumentStrings
    public boolean isCorrect(ArrayList<String> tones, ArrayList<InstrumetString> instrumetStrings){
        if(!isComplete()){
            return false;
        }
        boolean correct = true;

        //loop through all tones(strings) of created chord
        for(int i = 0; i < tonesPositions.size(); i++){
            if(tonesPositions.get(i) != -1){

                //get note by position on string
                String toneByPosition = instrumetStrings.get(i).findToneByPosition(tonesPositions.get(i));

                //check if chord should have the tone
                if(!tones.contains(toneByPosition)){
                    correct = false;
                }
            }
        }
        return correct;
    }

    //finds width of chord on fretboard
    private void findChordWidth(){
        if(!isComplete()){
            throw new RuntimeException("Chord not complete");
        }
        int minPostition = this.tonesPositions.getFirst();
        int maxPostition = this.tonesPositions.getFirst();

        for(int i = 1; i <this.tonesPositions.size(); i++){

            if(this.tonesPositions.get(i) < minPostition && this.tonesPositions.get(i) >= 0   ||  minPostition == -1) {
                minPostition = this.tonesPositions.get(i);
            }
            if(this.tonesPositions.get(i) > maxPostition){
                maxPostition = this.tonesPositions.get(i);
            }
        }
        if(minPostition == 0){
            this.chordWidth = maxPostition - minPostition;
        }else{
            this.chordWidth = maxPostition - minPostition  + 1;
        }

    }

    //find barre in chord
    public void findBarre(){
        if(!isComplete()){
            throw new RuntimeException("Chord not complete");
        }

        //sort array of positions from low to high
        ArrayList<Integer> tonesPositionsCopy =  new ArrayList<>(tonesPositions);
        Collections.sort(tonesPositionsCopy);

        //remove all non-played strings
        tonesPositionsCopy.removeIf(m -> m == -1);


        if(tonesPositionsCopy.get(0) != 0 && tonesPositionsCopy.get(1) == tonesPositionsCopy.get(0)){
            this.barrePosition = tonesPositionsCopy.get(0);
            this.barreNumberOfPlayedStrings = (int) tonesPositions.stream().filter(m -> m == tonesPositionsCopy.get(0)).count();
            for(int i = 0; i < this.tonesPositions.size(); i++ ){
                if(this.tonesPositions.get(i) == this.barrePosition){
                        this.barreStartString = i;
                        this.barreEndString = this.numberOfStrings - 1;
                        break;
                }
            }
        }
    }

    //prints all tones of chord on one line
    public void printString(){
        for(int i = 0; i < this.tonesPositions.size(); i++){
            System.out.print(this.tonesPositions.get(i) + " ");
        }
        System.out.println();
        //System.out.println("Barre: " + this.barrePosition + ": " + this.barreStartString + " " + this.barreEndString + " soundingStrings: " + this.barreNumberOfPlayedStrings);
        //System.out.println("Width:" + this.chordWidth);
       // System.out.println("isPlayable: " + this.isPlayable(4)  );
    }


    public void setPosition(final int position){
        this.position = position;
    }
    public int getPosition(){
        return this.position;
    }
    //get set barre
    public void setBarre(final int barrePosition, final int barreStartString, final int barreEndString){
        this.barrePosition = barrePosition;
        this.barreStartString = barreStartString;
        this.barreEndString = barreEndString;
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
    public int compareTo(Chord o) {
        if(this.position > o.position){
            return 1;
        }
        return 0;
    }
}
