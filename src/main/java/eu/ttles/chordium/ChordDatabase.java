package eu.ttles.chordium;

import eu.ttles.chordium.api.ApiRequest;
import eu.ttles.chordium.api.ChordsApiResponse;
import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.chordPattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

//tool for pre-generating all chords to chordDatabase.db
//generates all chord types from chordintervals.txt
//by default is adding new chords to db, to delete db - uncomment deleteALlChords() method in main
public class ChordDatabase {


    //instrument settings
    //number of strings is based on length of tuning string
    static String tuning = "GCEA";
    static int numberOfFrets = 12;
    static int maxChordWidth = 4;
    static int maxNumberOfFingers = 4;



    //db settings
    private static final String DB_URL = "jdbc:sqlite:chordDatabase.db";
    private static final String INSERT_SQL =
            "INSERT INTO chords(base, type, tuning, numberOfFrets, maxChordWidth, maxNumberOfFingers, difficultyScore, response_chordWidth, response_position, response_barres, response_chordPositions) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static Connection connection;
    private static PreparedStatement batchStatement;
    private static int batchCount = 0;
    private static final int BATCH_SIZE = 1000;


    //add single record to db (add to batch)
    public static void addChord(String base, String type, String tuning, int numberOfFrets, int maxChordWidth, int maxNumberOfFingers, int difficultyScore, int chordWitdh, int position, String barres, String chordPositions) throws SQLException {

        //add data to sql statement
        batchStatement.setString(1, base);
        batchStatement.setString(2, type);
        batchStatement.setString(3, tuning);
        batchStatement.setInt(4, numberOfFrets);
        batchStatement.setInt(5, maxChordWidth);
        batchStatement.setInt(6, maxNumberOfFingers);
        batchStatement.setInt(7, difficultyScore);
        batchStatement.setInt(8, chordWitdh);
        batchStatement.setInt(9, position);
        batchStatement.setString(10, barres);
        batchStatement.setString(11, chordPositions);
        batchStatement.addBatch();

        batchCount++;
        //add to db when reached batch size
        if (batchCount >= BATCH_SIZE) {
            executeBatch();
        }
    }

    //add bacth to db
    public static void executeBatch() throws SQLException {
        if (batchCount > 0) {
            batchStatement.executeBatch();
            connection.commit();
            batchCount = 0;

            System.out.println("Batch added to sqlite db");
        }
    }

    //close connection (Execute any remaining batch)
    public static void close() throws SQLException {
        try {
            executeBatch(); // Execute any remaining batch operations
        } finally {
            if (batchStatement != null) {
                batchStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    //delete all records from db
    public static void deleteALlChords() {
        String sql = "DELETE FROM chords";
        try(
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ){
            int deletedItems = stmt.executeUpdate(sql);
            System.out.println(deletedItems);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {


        connection = DriverManager.getConnection(DB_URL);
        connection.setAutoCommit(false);
        batchStatement = connection.prepareStatement(INSERT_SQL);


        //DELETE ALL RECORDS
        //deleteALlChords();


        ChordFinder chordFinder = new ChordFinder();

        final String[] tones = {"G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};

        //get all patterns from chordIntervals.txt
        HashSet<chordPattern> chordPatterns = chordFinder.getChordPatterns();

        //generate tuning array from string
        int numberOfStrings = tuning.length();
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


        for (String base : tones) {
            for (chordPattern pattern : chordPatterns) {

                String type = pattern.getFullName();
                System.out.println(base + "-" + type);

                //find chords
                chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, maxChordWidth,maxNumberOfFingers);

                //generate response
                ChordsApiResponse transposedChords = chordFinder.getApiResponse(ApiRequest.TRANSPOSED);
                //get json response
                ArrayList<Map<String, Object>> chords = transposedChords.getChords();

                System.out.println("found chords: " + chords.size());
                for (Map<String, Object> chord : chords) {
                    int diffScore = Integer.parseInt(chord.get("score").toString());
                    int chordWidth = Integer.parseInt(chord.get("chordWidth").toString());
                    int position = Integer.parseInt(chord.get("chordPosition").toString());
                    String barres = chord.get("barres").toString();
                    String chordPositions = chord.get("tonePositions").toString();
                    addChord(base, type, tuning, numberOfFrets, maxChordWidth, maxNumberOfFingers, diffScore, chordWidth, position, barres, chordPositions);
                }
            }
        }
        close();

        //listChords();
        System.out.println("Operations complete. Check the 'chordDatabase.db' file.");

    }
}