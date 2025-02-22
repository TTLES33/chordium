package eu.ttles.chordium.api;

import eu.ttles.chordium.utils.ChordFinder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration

public class AppConfiguration {

    @Bean
    public ChordFinder chordFinder(){
        return new ChordFinder();
    }

    @Bean
    public DatabaseController databaseController(){
        return new DatabaseController();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(" _____  _                       _  _                   ");
        System.out.println("/  __ \\| |                     | |(_)                  ");
        System.out.println("| /  \\/| |__    ___   _ __   __| | _  _   _  _ __ ___  ");
        System.out.println("| |    | '_ \\  / _ \\ | '__| / _` || || | | || '_ ` _ \\ ");
        System.out.println("| \\__/\\| | | || (_) || |   | (_| || || |_| || | | | | |");
        System.out.println(" \\____/|_| |_| \\___/ |_|    \\__,_||_| \\__,_||_| |_| |_|");
        System.out.println();
        System.out.println("Version: 2024-1.2.0-BETA");
        System.out.println("App started - listening on port 8080");
    }
}

