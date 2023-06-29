package projectH.HistoricalDatabaseofCaptives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html
@SpringBootApplication
//@EntityScan({ "projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive"})
public class HistoricalDatabaseOfCaptivesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricalDatabaseOfCaptivesApplication.class, args);
	}

}


//TODO
// this will be the change log
// backup created about the local db
// Visually interesting parts ae getting prioritised. (Appears to be more interesting )
// This the GIS part is the most important at the moment . Things to learn => https://docs.qgis.org/3.28/en/docs/training_manual/index.html
// 2023.06.11 main goal is to figure out the main concept of the GIS part.
// .
// another idea is to create data cleaning utility
//    will go through the db and checks for data not meeting the requirements or possibly wrongly entered, extremes from statistical point of view.
//    first this will only insert the id from captives_data table to another table with the reasoncode / explanation
//    later this might get smarter
