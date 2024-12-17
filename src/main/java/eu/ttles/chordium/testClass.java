package eu.ttles.chordium;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ttles.chordium.api.ApiRequest;
import eu.ttles.chordium.utils.ChordFinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class testClass {


    public static void main(String[] args) {


        String base = "C";
        String type = "maj";
        int numberOfStrings = 5;
        int numberOfFrets = 10;

        String tuning = "ADGBE";
        ChordFinder chordFinder = new ChordFinder();


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
        System.out.println("tuningList: " + tuningList);
        chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, 4,4);
        System.out.println(chordFinder.getTransposedChords());


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
