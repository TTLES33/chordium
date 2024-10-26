package eu.ttles.chordium.api;

import eu.ttles.chordium.utils.ChordFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AppConfiguration {

    @Bean
    public ChordFinder chordFinder(){
        return new ChordFinder();
    }


}

