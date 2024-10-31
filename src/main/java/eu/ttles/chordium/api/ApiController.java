package eu.ttles.chordium.api;

import eu.ttles.chordium.utils.ChordFinder;
import eu.ttles.chordium.utils.chordPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api", produces="application/json")
public class ApiController {

    //generica values for api
    @GetMapping("/findChords")
    public ArrayList<Map<String, Object>> findChords(@Autowired ChordFinder chordFinder, @RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets) {


        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets);

        return chordFinder.getApiChords();

    }

    //transposed vales for api using SVGuitar
    @GetMapping("/findChordsTransposed")
    public ArrayList<Map<String, Object>> findChordsTransposed(@Autowired ChordFinder chordFinder, @RequestParam() String base, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "EADGBE") String tuning, @RequestParam(defaultValue = "6") Integer numberOfStrings, @RequestParam(defaultValue = "15") Integer numberOfFrets) {

        createTuningAndFindChords(chordFinder, base, type, tuning, numberOfStrings, numberOfFrets);

        return chordFinder.getTransposedChords();

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

}


