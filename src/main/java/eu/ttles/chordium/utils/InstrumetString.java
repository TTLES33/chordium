package eu.ttles.chordium.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class InstrumetString {

    //all possible tones
    private final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};
    ArrayList<String> thisString = new ArrayList<>();



    public InstrumetString(String baseTone, int maxPosition) {
        int position = 0;

        //find tone in array tones from string from argument
        for(int i = 0; i < tones.length; i++) {
            if(tones[i].equals(baseTone)) {
                position = i;
            }
        }


        //fill string arraylist with all tones on string
        for(int i = 0; i < maxPosition; i++){

            //System.out.println(tones[position]);

            thisString.add(tones[position]);

            position++;
            //if all tones are used, start over
            if(position >= 12){
                position = 0;
            }
        }

    }


    //finds positions of tones on this string
    public ArrayList<Integer> findTones(String tone){
        //if string doesnť contain tone at all
        if(!Arrays.asList(tones).contains(tone)){
            return new ArrayList<>(0);
        }

        ArrayList<Integer> returnArray = new ArrayList<>();
        for(int i = 0; i < thisString.size(); i++){
            if(thisString.get(i).equals(tone)){
                returnArray.add(i);
            }
        }
        return returnArray;


    }

    public String findToneByPosition(int position) throws IndexOutOfBoundsException{
        if(position >= thisString.size()){
            throw new IndexOutOfBoundsException("position index out of bounds");
        }else if(position == -1){ //string not played
            return "none";

        }else{
            return thisString.get(position);
        }
    }

    public ArrayList<String> getThisString(){
        return thisString;
    }
    public void printString(){
        thisString.stream().forEach(o -> System.out.print(o + "   "));
        System.out.println();
    }



}
