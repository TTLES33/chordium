package eu.ttles.chordium.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;


//response body template for api responses
public class ChordsApiResponse {
    private String chordName;
    private HashSet<String> aliases;
    private ArrayList<Map<String, Object>> chords;
    private ArrayList<ArrayList<String>> tones;
    private int[] intervals;
    private int maxChordWidth;
    public ChordsApiResponse() {

    }
    public void setChordName(String chordName) {
        this.chordName = chordName;
    }
    public void setAliases(HashSet<String> aliases) {
        this.aliases = aliases;
    }
    public void setChords(ArrayList<Map<String, Object>> chords) {
        this.chords = chords;
    }
    public void setTones(ArrayList<ArrayList<String>> tones) {
        this.tones = tones;
    }
    public void setIntervals(int[] intervals) {
        this.intervals = intervals;
    }
    public void setMaxChordWidth(int maxChordWidth) {
        this.maxChordWidth = maxChordWidth;
    }



    public String getChordName() {
        return chordName;
    }

    public HashSet<String> getAliases() {
        return aliases;
    }
    public ArrayList<Map<String, Object>> getChords() {
        return chords;
    }
    public ArrayList<ArrayList<String>> getTones() {
        return tones;
    }
    public int[] getIntervals() {
        return intervals;
    }
    public int getMaxChordWidth() {
        return this.maxChordWidth;
    }


}
