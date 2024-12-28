package eu.ttles.chordium.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.chordPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api", produces="application/json")
public class ApiController {

    //depresed
    //generica values for api
    //todo: check non transposed resposes
    @GetMapping("/findChords")
    public ChordsApiResponse findChords(@RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets, @RequestParam(defaultValue = "4") Integer maxChordWidth, @RequestParam(defaultValue = "4") Integer maxNumberOfFingers) {
        ChordFinder chordFinder = new ChordFinder();
        checkApiMaxValues(base, type, tuning, numberOfStrings, numberOfFrets);

        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets, maxChordWidth, maxNumberOfFingers);

        return chordFinder.getApiResponse(ApiRequest.APICHORDS);

    }

    //transposed vales for api using SVGuitar
    @GetMapping("/findChordsTransposed")
    public ResponseEntity<?> findChordsTransposed(@RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets, @RequestParam(defaultValue = "4") Integer maxChordWidth, @RequestParam(defaultValue = "4") Integer maxNumberOfFingers) {

        System.out.println("New request - base:" + base + ", type:" + type + ", tuning:" + tuning + ", numberOfStrings:" + numberOfStrings + ", numberOfFrets:" + numberOfFrets + maxChordWidth + ", maxNumberOfFings:" + maxNumberOfFingers);
        long requestStart = System.currentTimeMillis();

        ChordFinder chordFinder = new ChordFinder();
        checkApiMaxValues(base, type, tuning, numberOfStrings, numberOfFrets);

        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets, maxChordWidth, maxNumberOfFingers);


        try {

            ObjectMapper objectMapper = new ObjectMapper();

            //save api response as string
            String jsonArray = objectMapper.writeValueAsString(chordFinder.getApiResponse(ApiRequest.TRANSPOSED));

            //create custom uuid file name
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + ".json";

            //create new file
            File jsonFile = new File("cacheFiles/" + fileName);
            jsonFile.createNewFile();

            //write response to file
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("cacheFiles/" + fileName, true));
            myWriter.write(jsonArray);
            myWriter.close();


            File file = ResourceUtils.getFile("cacheFiles/" + fileName);



            // read file size
            long fileSizeInBytes = file.length();
            // create a input stream for the file
            FileInputStream fileInputStream = new FileInputStream(file);
            // create a spring boot input stream wrapper
            InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);


            long finish = System.currentTimeMillis();
            long timeElapsed = finish - requestStart;
            System.out.println("    Response: " + fileName);
            System.out.println("    Time elapsed: " + timeElapsed + "ms");


            // return the input stream a response
            return ResponseEntity.ok()
                    .contentLength(fileSizeInBytes)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(inputStreamResource);


        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        //calculate time elapsed
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - requestStart;
        System.out.println("    Time elapsed: " + timeElapsed);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getAllChordsInfo")
    public HashSet<chordPattern> getAllChordsInfo(@Autowired ChordFinder chordFinder) {

        return chordFinder.getChordPatterns();

    }




    //Api Utils
    public static void createTuningAndFindChords(ChordFinder chordFinder, @RequestParam String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets, @RequestParam(defaultValue = "4") Integer maxChordWidth, @RequestParam(defaultValue = "4") Integer maxNumberOfFingers) {
        //Create tuning ArrayList from String
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

        //try to find chords, else throw error
        try {
            chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList, maxChordWidth, maxNumberOfFingers);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void checkApiMaxValues (String base, String type, String tuning, Integer numberOfStrings, Integer numberOfFrets) throws IllegalArgumentException{
        if(base.length() > 2){
            throw new IllegalArgumentException("base tone not legal value");
        }
        if(type.length() > 10){
            throw new IllegalArgumentException("type not legal value");
        }
        if(numberOfStrings > 16){
            throw new IllegalArgumentException("exceeds max number of strings (16)");
        }
        if(numberOfFrets > 50){
            throw new IllegalArgumentException("exceeds max number of frets (50)");
        }
        if(tuning.length() > 30){
            throw new IllegalArgumentException("exceeds max number of tones in tuning (30)");
        }



        //check for advanced max values
//        max nmb. of strings        max nmr. of frets
//          0 - 10              ---     50
//          11 - 12             ---     30
//          13                  ---     15
//          14                  ---     12
//          15                  ---     12
//          16                  ---     10

        if(numberOfStrings <=10){
            if(numberOfFrets > 50){
                throw new IllegalArgumentException("exceeds max number of frets and strings");
            }
        } else if (numberOfStrings <= 12) {
            if(numberOfFrets > 30){
                throw new IllegalArgumentException("exceeds max number of frets and strings");
            }
        }else if(numberOfStrings == 13) {
            if (numberOfFrets > 15) {
                throw new IllegalArgumentException("exceeds max number of frets and strings");
            }
        } else if (numberOfStrings == 14 || numberOfStrings == 15) {
            if (numberOfFrets > 12) {
                throw new IllegalArgumentException("exceeds max number of frets and strings");
            }
        }
        else if (numberOfStrings == 16) {
            if (numberOfFrets > 10) {
                throw new IllegalArgumentException("exceeds max number of frets and strings");
            }
        }
    }
    }


