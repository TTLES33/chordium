package eu.ttles.chordium.utils;

import java.util.HashSet;

//data structure to hold chord intervals and its names
public class chordPattern {

    private String fullName;
    private int[] intervals;
    private HashSet<String> aliases;

    public chordPattern(String fullName, int[] intervals, HashSet<String> aliases) {
        this.fullName = fullName;
        this.intervals = intervals;
        this.aliases = aliases;
    }

    public boolean hasAlias(String alias) {
        return aliases.contains(alias);
    }


    public String getFullName() {
        return fullName;
    }
    public int[] getIntervals() {
        return intervals;
    }
    public HashSet<String> getAliases() {
        return aliases;
    }





}
