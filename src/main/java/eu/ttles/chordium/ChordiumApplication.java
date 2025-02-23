package eu.ttles.chordium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
    public class ChordiumApplication {

    public static void main(String[] args) {
        System.out.println("starting app...");
        SpringApplication.run(ChordiumApplication.class, args);
    }
}
