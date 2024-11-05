package eu.ttles.chordium.api;

import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.chordPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api", produces="application/json")
public class ApiController {

    //generica values for api
    @GetMapping("/findChords")
    public ChordsApiResponse findChords(@Autowired ChordFinder chordFinder, @RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets) {

        checkApiMaxValues(base, type, tuning, numberOfStrings, numberOfFrets);

        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets);

        return chordFinder.getApiResponse(ApiRequest.APICHORDS);

    }

    //transposed vales for api using SVGuitar
    @GetMapping("/findChordsTransposed")
    public ChordsApiResponse findChordsTransposed(@Autowired ChordFinder chordFinder, @RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets) {

        checkApiMaxValues(base, type, tuning, numberOfStrings, numberOfFrets);

        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets);

        return chordFinder.getApiResponse(ApiRequest.TRANSPOSED);

    }

    @GetMapping("/getAllChordsInfo")
    public HashSet<chordPattern> getAllChordsInfo(@Autowired ChordFinder chordFinder) {

        return chordFinder.getChordPatterns();

    }




    //Api Utils
    public static void createTuningAndFindChords(@Autowired ChordFinder chordFinder, @RequestParam String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets) {
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
        System.out.println("tuningList: " + tuningList);

        //try to find chords, else throw error
        try {
            chordFinder.findChord(base, type, numberOfStrings, numberOfFrets, tuningList);
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
        if(numberOfStrings > 30){
            throw new IllegalArgumentException("exceeds max number of strings (30)");
        }
        if(numberOfFrets > 50){
            throw new IllegalArgumentException("exceeds max number of frets (50)");
        }
        if(tuning.length() > 30){
            throw new IllegalArgumentException("exceeds max number of tones in tuning (30)");
        }
    }
    }


