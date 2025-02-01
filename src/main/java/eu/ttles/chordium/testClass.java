package eu.ttles.chordium;



import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.chordPattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class testClass {

    public static void getAllChordsInfo( ChordFinder chordFinder) {


        String tuning = "EADGBE";
        int numberOfStrings = 6;
        ArrayList<String> tuningList = new ArrayList<>();
        for (int i = 0; i < tuning.length(); i++) {
            tuningList.add(String.valueOf(tuning.charAt(i)));
            //look for sharp values
            if(i != tuning.length() - 1) {
                if(tuning.charAt(i+1) == '#') {
                    int tuningListSize = tuningList.size();
                    tuningList.set(tuningListSize - 1, tuningList.get(tuningListSize - 1) + '#');
                    i++;
                }
            }

        }

            Connection c = null;
            Statement st = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chordDatabase.db");
            c.setAutoCommit(false);

            st = c.createStatement();


            HashSet<chordPattern> chordPatterns = chordFinder.getChordPatterns();
            final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};


            for (String tone : tones) {
                Iterator<chordPattern> iterator = chordPatterns.iterator();
                while (iterator.hasNext()) {

                    chordPattern p = iterator.next();
                  //   String insertSql = "INSERT INTO chords (base, type, json_data, difficultyScore) VALUES ('" + tone + "', '" + p.getFullName() + "', '" + tone + "', '" + tone + "',

                    chordFinder.findChord(tone, p.getFullName(),6,15, tuningList, 4,4);
                    System.out.println(tone + " - " + p.getFullName() + " - " + chordFinder.getTransposedChords());
                }
            }
        }catch ( Exception e ) {
            System.out.println("error");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }












    }


    public static void insertChordTypesIntoDB(ChordFinder chordFinder) {
        HashSet<chordPattern> chordPatterns = chordFinder.getChordPatterns();
        Connection c = null;
        Statement st = null;

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chordDatabase.db");
            c.setAutoCommit(false);

            System.out.println("Connected to database successfully");
            st = c.createStatement();



            Iterator<chordPattern> iterator = chordPatterns.iterator();

            int index = 0;
            while (iterator.hasNext()) {
                chordPattern p = iterator.next();
                String mainName = p.getFullName();
                HashSet<String> alternatives = p.getAliases();

                String sql = "INSERT INTO chordTypes (typeName, TypeGroupID) VALUES ('" + mainName + "','" + index + "');";
                st.executeUpdate(sql);

                for (String alternative : alternatives) {
                    String sqlA = "INSERT INTO chordTypes (typeName, TypeGroupID) VALUES ('" + alternative + "','" + index + "');";
                    st.executeUpdate(sqlA);
                }
                st.close();
                c.commit();
                c.close();


                index++;
            }
        }catch (Exception e){
            System.out.println("error");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public static void main(String[] args) {

        ChordFinder chordFinder = new ChordFinder();
      //  getAllChordsInfo(chordFinder);
        insertChordTypesIntoDB(chordFinder);





//
//        String base = "C";
//        String type = "maj";
//
//
//        int numberOfFrets = 12;
//
//        String tuning = "EADGBEEADGEECG";
//
//        int numberOfStrings = tuning.length();
//        ChordFinder chordFinder = new ChordFinder();
//
//
//        ArrayList<String> tuningList = new ArrayList<>();
//        for (int i = 0; i < tuning.length(); i++) {
//            tuningList.add(String.valueOf(tuning.charAt(i)));
//            //look for sharp values
//            if(i != tuning.length() - 1) {
//                if(tuning.charAt(i+1) == '#') {
//                    int tuningListSize = tuningList.size();
//                    tuningList.set(tuningListSize - 1, tuningList.get(tuningListSize - 1) + '#');
//                    i++;
//                }
//            }
//
//        }
//        System.out.println("tuningList: " + tuningList);
//        chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, 4,4);
//        System.out.println("---------");
//        System.out.println(chordFinder.getTransposedChords());
//
//        System.out.println("---------");


//        ArrayList<Integer> frets = new ArrayList<>();
//        frets.add(0);
//        frets.add(1);
//        frets.add(1);
//        frets.add(1);
//        frets.add(-1);
//        frets.add(-1);
//        ArrayList<ArrayList<String>> chordTones = new ArrayList<>();
//        ArrayList<String> tones = new ArrayList<>();
//        tones.add("C");
//        tones.add("E");
//        tones.add("G");
//        chordTones.add(tones);
//
//
//        ArrayList<InstrumetString> strings = new ArrayList<>();
//
//
//        InstrumetString string0 = new InstrumetString("E",15);
//        InstrumetString string1 = new InstrumetString("A",15);
//        InstrumetString string2 = new InstrumetString("D",15);
//        InstrumetString string3 = new InstrumetString("G",15);
//        InstrumetString string4 = new InstrumetString("B",15);
//        InstrumetString string5 = new InstrumetString("E",15);
//
//        strings.add(string0);
//        strings.add(string1);
//        strings.add(string2);
//        strings.add(string3);
//        strings.add(string4);
//        strings.add(string5);
//
//
//
//
//        Chord testChord = new Chord(6);
//        testChord.addAllTonesIfPossible(4,4, frets,chordTones, strings);
//        System.out.println(testChord.hashCode());
//
//
//        Chord testChord2 = new Chord(6);
//        testChord2.addAllTonesIfPossible(4,4, frets, chordTones, strings);
//        System.out.println(testChord2.hashCode());





//        //try to find chords, else throw error
//        try {
//            chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, 4,4);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            String jsonArray = objectMapper.writeValueAsString(chordFinder.getApiResponse(ApiRequest.TRANSPOSED));
//            //System.out.println(jsonArray);
//
//            UUID uuid = UUID.randomUUID();
//            String fileName = uuid.toString() + ".json";
//
//            File jsonFile = new File(fileName);
//            jsonFile.createNewFile();
//
//            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true));
//            myWriter.write(jsonArray);
//            myWriter.close();
//
//
//            //
//        } catch (IOException e){
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }


    }



}
