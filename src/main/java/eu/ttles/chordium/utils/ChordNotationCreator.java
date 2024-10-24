package eu.ttles.chordium.utils;

import java.io.File;
import java.util.*;

public class ChordNotationCreator {
    private final String[] tones = {"C", "D", "E", "F", "G", "A", "B"};
    private final String[] tonesFlats = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    //hashmap of english short interval names with corresponding number of semitones
    private final HashMap<String, Integer> intervalsNames;
    //Set of chord patterns (names, intervals, aliases)
    private final HashSet<chordPattern> chordPatternsSet;

    private final ArrayList<ArrayList<String>> chordActualTones = new ArrayList<>();
    private final ArrayList<ArrayList<String>> chordWorkingTones = new ArrayList<>();
    private final ArrayList<String> aliases = new ArrayList<>();


    public ChordNotationCreator() {

        this.chordPatternsSet = new HashSet<>();

        //add all interval short names with number of semitones
        //Minor, major,or perfect intervals
        this.intervalsNames = new HashMap<>();
        this.intervalsNames.put("1P", 0);
        this.intervalsNames.put("2m", 1);
        this.intervalsNames.put("2M", 2);
        this.intervalsNames.put("3m", 3);
        this.intervalsNames.put("3M", 4);
        this.intervalsNames.put("4P", 5);
        this.intervalsNames.put("5P", 7);
        this.intervalsNames.put("6m", 8);
        this.intervalsNames.put("6M", 9);
        this.intervalsNames.put("7m", 10);
        this.intervalsNames.put("7M", 11);
        this.intervalsNames.put("8P", 12);
        this.intervalsNames.put("9m", 13);
        this.intervalsNames.put("9M", 14);
        this.intervalsNames.put("10m", 15);
        this.intervalsNames.put("10M", 16);
        this.intervalsNames.put("11P", 17);
        this.intervalsNames.put("12P", 19);
        this.intervalsNames.put("13m", 20);
        this.intervalsNames.put("13M", 21);
        this.intervalsNames.put("14m", 22);
        this.intervalsNames.put("14M", 23);
        this.intervalsNames.put("15P", 24);

        //Augmented or diminished intervals
        this.intervalsNames.put("2d", 0);
        this.intervalsNames.put("1A", 1);
        this.intervalsNames.put("3d", 2);
        this.intervalsNames.put("2A", 3);
        this.intervalsNames.put("4d", 4);
        this.intervalsNames.put("3A", 5);
        this.intervalsNames.put("4A", 6);
        this.intervalsNames.put("5d", 6);
        this.intervalsNames.put("6d", 7);
        this.intervalsNames.put("5A", 8);
        this.intervalsNames.put("7d", 9);
        this.intervalsNames.put("6A", 10);
        this.intervalsNames.put("8d", 11);
        this.intervalsNames.put("7A", 12);
        this.intervalsNames.put("9d", 12);
        this.intervalsNames.put("8A", 13);
        this.intervalsNames.put("10d", 14);
        this.intervalsNames.put("9A", 15);
        this.intervalsNames.put("11d", 16);
        this.intervalsNames.put("10A", 17);
        this.intervalsNames.put("11A", 18);
        this.intervalsNames.put("12d", 18);
        this.intervalsNames.put("13d", 19);
        this.intervalsNames.put("12A", 20);
        this.intervalsNames.put("14d", 21);
        this.intervalsNames.put("13A", 22);
        this.intervalsNames.put("15d", 23);
        this.intervalsNames.put("14A", 24);
        this.intervalsNames.put("15A", 25);

        loadChordIntervalsFromFile();
    }

    private void loadChordIntervalsFromFile(){

        try{
            //load file with all chords
            Scanner scanner = new Scanner(new File("src/main/java/eu/ttles/chordium/utils/chordIntervals.txt"));
            while(scanner.hasNextLine()){

                //seperate names to array
                String[] typeAliases = scanner.nextLine().split(",");

                //full chord name
                String mainName = typeAliases[0];

                //create hashset of chord aliases
                HashSet<String> aliases = new HashSet<>(Arrays.stream(typeAliases).toList());

                //split intervals to array
                String[] intervalsData = scanner.nextLine().split(" ");
                int[] intervals = new int[intervalsData.length];

                //arr intervals to integer array
                for(int i = 0; i < intervalsData.length; i++) {
                    int intervalIntValue;

                    String intervalData = intervalsData[i];

                    //if interval starts with "-" = can be omitted
                    //than add 100 to the interval number (can be omitted)
                    if(intervalData.startsWith("-")){
                        intervalData = intervalData.substring(1);
                        //System.out.println("1:" + intervalData);
                        intervalIntValue = intervalsNames.get(intervalData) + 100;
                    }else{
                        //System.out.println("2:" + intervalData);
                        intervalIntValue = intervalsNames.get(intervalData);
                    }

                    intervals[i] = intervalIntValue;
                }

                //create chordPattern
                chordPattern pattern = new chordPattern(mainName, intervals, aliases);

                //add chordPattern to hashset
                chordPatternsSet.add(pattern);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }




    //generate chord tones by its name (base + type)
    public void generateTonesByChordName(String base, String type) throws IllegalArgumentException{

        base = base.toUpperCase();

        if(type.isEmpty()){
            type = "maj";
        }
       // type = type.toLowerCase(); //todo: handle all cases

        //convert harp to b
        if(base.endsWith("#")){
            base = base.substring(0,1);
            if(Arrays.asList(this.tonesFlats).contains(base)){
                int nextItem = Arrays.asList(this.tonesFlats).indexOf(base) + 1;
                if(nextItem > 11){
                    nextItem = nextItem - 12;
                }
                base = tonesFlats[nextItem];
            }else{
                throw new IllegalArgumentException("base not accepted");
            }
        }

        //if base is correct
        if(Arrays.asList(this.tonesFlats).contains(base)){

            boolean chordTypeExists = false;
            //if type exists
            for(chordPattern pattern : chordPatternsSet){

                if(pattern.hasAlias(type)){
                    //get intervals
                    int[] tonesIntervals = pattern.getIntervals();
                    this.generateTonesFromIntervals(base, tonesIntervals);
                    chordTypeExists = true;
                    break;
                }
            }
            //type not found
            if(!chordTypeExists){
                //System.out.println(type);
                throw new IllegalArgumentException("type not accepted");
            }
        //base not found
        }else{
            throw new IllegalArgumentException("base not accepted");
        }
    }

    //get chord name by full string
    public void generateTonesByChordName(String chordName) {
        //split string to base and type
        String base = chordName.substring(0, 1);
        String type = chordName.substring(1);

        //call parent function
        this.generateTonesByChordName(base, type);
    }


    //generate tones of chord, by array of intervals and base(root) note
    //recursive
    private void generateTonesFromIntervals(String base, int[] tonesIntervals){

        //tones of chord correctly named (only flats)
        ArrayList<String> localChordActualTones = new ArrayList<>();

        //find index of base tone in tones array
        int firstIndex = 0;
        for(int i = 0; i < this.tonesFlats.length; i++){
            if(this.tonesFlats[i].equalsIgnoreCase(base)) {
                firstIndex = i;
                break;
            }
        }


        //loop through all intervals
        for(int i = 0; i < tonesIntervals.length; i++){

            int currentInterval = tonesIntervals[i];

            //if interval can be omitted - skip this loop iteration, and call recursively itself, with interval without this skipped interval
            if(currentInterval > 100){
                //create copy of interval array
                int[] newTonesIntervals = tonesIntervals.clone();

                //delete skipping of interval in new array
                newTonesIntervals[i] = newTonesIntervals[i] - 100;

                //call itself
                generateTonesFromIntervals(base, newTonesIntervals);
                continue;
            }

            int currentIndex = firstIndex + currentInterval;

            if(currentIndex >= 12){
                currentIndex = currentIndex - 12;
            }

            localChordActualTones.add(tonesFlats[currentIndex]);
        }

        this.chordActualTones.add(new ArrayList<>(localChordActualTones));
        this.chordWorkingTones.add(new ArrayList<>(
                replaceFlatNotesWithSharps(localChordActualTones)
        ));

    }






    //replace notes with flats with sharps
    private List<String> replaceFlatNotesWithSharps(ArrayList<String> inputArray){
        return  inputArray.stream().map(o->
                o.replace("Db","C#")
                        .replace("Eb","D#")
                        .replace("Gb","F#")
                        .replace("Ab","G#")
                        .replace("Bb","A#")
        ).toList();
    }



    public void printActualChordTones(){
        for(ArrayList<String> arrayList : this.chordActualTones){
            for(String tone : arrayList){
                System.out.println(tone);
            }
            System.out.println("---");
        }

    }
    public void printWorkingChordTones(){
        for(ArrayList<String> arrayList : this.chordWorkingTones){
            for(String tone : arrayList){
                System.out.println(tone);
            }
            System.out.println("---");

        }
    }


    public ArrayList<ArrayList<String>> getActualChordTones(){
        return this.chordActualTones;
    }
    public ArrayList<ArrayList<String>> getWorkingChordTones(){
        return this.chordWorkingTones;
    }
    public ArrayList<String> getAliases(){
        return this.aliases;
    }
}
