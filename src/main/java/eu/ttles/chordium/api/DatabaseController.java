package eu.ttles.chordium.api;
import eu.ttles.chordium.utils.ChordNotationCreator;
import eu.ttles.chordium.utils.chordPattern;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseController {


    private final String DB_URL = "jdbc:sqlite:chordDatabase.db";
    private Connection conn;
    private final ChordNotationCreator notationCreator;


    public DatabaseController() {
        this.notationCreator = new ChordNotationCreator();

        //create connection to db
        try {
            this.conn = DriverManager.getConnection(DB_URL);
        }catch (SQLException e) {
            System.err.println("Error reading chords: " + e.getMessage());
        }
    }


    //check if chord is generated in db
    public boolean isChordPreGenerated(String base, String type, String tuning, int numberOfFrets, int maxChordWidth, int maxNumberOfFingers){
        try {
            //create ChordNotationCreator object to get info about chord
            notationCreator.generateTonesByChordName(base, type);
            chordPattern currentChordPattern = notationCreator.getCurrentChordPattern(); //get current chord pattern to get base type name from alternatives


            //create sql request
            String sql = "SELECT * FROM chords WHERE base = ? AND type = ? AND tuning = ? AND numberOfFrets = ? AND maxChordWidth = ? AND maxNumberOfFingers = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, base);
            stmt.setString(2, currentChordPattern.getFullName());
            stmt.setString(3, tuning);
            stmt.setInt(4, numberOfFrets);
            stmt.setInt(5, maxChordWidth);
            stmt.setInt(6, maxNumberOfFingers);

            //execute sql
            ResultSet rs = stmt.executeQuery();

            //if there are 1 or more records
            return rs.isBeforeFirst();


        }catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public ChordsApiResponse getApiResponseTransposed(String base, String type, String tuning, int numberOfFrets, int maxChordWidth, int maxNumberOfFingers){
        try {
            //create ChordNotationCreator object to get info about chord
            notationCreator.generateTonesByChordName(base, type);   //generate chord info

            chordPattern currentChordPattern = notationCreator.getCurrentChordPattern(); //get current chord pattern
            ArrayList<ArrayList<String>> actualChordTones = notationCreator.getActualChordTones(); //get tones in chord

            //create response object
            ChordsApiResponse response = new ChordsApiResponse();

            //fill the body with values
            response.setChordName(currentChordPattern.getFullName());  //chord name
            response.setAliases(currentChordPattern.getAliases());  //chord aliases
            response.setIntervals(currentChordPattern.getIntervals());  //chord intervals
            response.setTones(actualChordTones);    //chord tones
            response.setMaxChordWidth(maxChordWidth);   //max chord width
            response.setGenerated(false);   //is generated (false)


            //create sql request
            String sql = "SELECT * FROM chords WHERE base = ? AND type = ? AND tuning = ? AND numberOfFrets = ? AND maxChordWidth = ? AND maxNumberOfFingers = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, base);
            stmt.setString(2, currentChordPattern.getFullName());
            stmt.setString(3, tuning);
            stmt.setInt(4, numberOfFrets);
            stmt.setInt(5, maxChordWidth);
            stmt.setInt(6, maxNumberOfFingers);

            //execute sql
            ResultSet rs = stmt.executeQuery();

            //add chords map to response body
            response.setChords(generateChordMapResponse(rs));

            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private  ArrayList<Map<String, Object>> generateChordMapResponse (ResultSet rs) throws SQLException {
        //create chords response body
        ArrayList<Map<String, Object>> transposedChords = new ArrayList<>();

        //fill chord response
        while (rs.next()) {

            //parse strings to correct formats
            String chordPositionsString = rs.getString("response_chordPositions");
            String barres = rs.getString("response_barres");

            // Remove the brackets and spaces
            String cleanedChordPositions = chordPositionsString.replaceAll("[\\[\\]\\s]", "");
            String cleanedBarres = barres.replaceAll("^\\[\\{|\\}\\]$", "");


            // Split the string by comma
            String[] chordPositionsStringArray = cleanedChordPositions.split(",");
            String[] barresStringArray = cleanedBarres.split(", ");

            //Convert string array to an integer array
            int[] chordPositionsArray = new int[chordPositionsStringArray.length];
            for (int i = 0; i < chordPositionsArray.length; i++) {
                chordPositionsArray[i] = Integer.parseInt(chordPositionsStringArray[i]);
            }

            //Create a map and populate it.
            Map<String, Integer> barreMap = new HashMap<>();
            for (String pair : barresStringArray) {

                String[] keyValue = pair.split("=");  //string array [0]=key; [1]=value
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    int value = Integer.parseInt(keyValue[1]);
                    barreMap.put(key, value);
                }
            }

            //create response hashmap
            Map<String, Object> transposedValues = new HashMap<>();
            transposedValues.put("chordWidth", rs.getInt("response_chordWidth"));
            transposedValues.put("chordPosition", rs.getInt("response_position"));
            transposedValues.put("barres", barreMap);
            transposedValues.put("tonePositions", chordPositionsArray);
            transposedValues.put("score", rs.getInt("difficultyScore"));
            transposedChords.add(transposedValues);


        }
        return transposedChords;
    }
}
