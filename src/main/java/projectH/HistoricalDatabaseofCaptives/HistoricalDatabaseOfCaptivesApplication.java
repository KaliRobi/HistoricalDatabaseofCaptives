package projectH.HistoricalDatabaseofCaptives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//https://docs.spring.io/spring-data/jpa/docs/current/reference/html
@SpringBootApplication
public class HistoricalDatabaseOfCaptivesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricalDatabaseOfCaptivesApplication.class, args);
	}

}


//TODO
// this will be the change log
// backup created about the local db
// Visual parts are getting prioritised. (Appears to be more interesting to look at )
// This the GIS part is the most important at the moment . Things to learn => https://docs.qgis.org/3.28/en/docs/training_manual/index.html
// 2023.06.11 main goal is to figure out the main concept of the GIS part.
// 2023.07.04 Most of the Geolocation part is done. cache left to do so db call is necessary to see what see what sources do we have
// 2023.07.21 Geo Part is done. One Bug left, after 5-600 entry to the db some locations are duplicated. Tolerated for now, because There is only one
// bulk entry front of me atm and repeating the same import over and over again just takes too much time (6< h) Will write a clear up script.
// Continue to work on the Antopometrics package
// .
// another idea is to create data cleaning utility
// will go through the db and checks for data not meeting the requirements or possibly wrongly entered, extremes from statistical point of view.
// first this will only insert the id from captives_data table to another table with the reasoncode / explanation
// later this might get smarter
