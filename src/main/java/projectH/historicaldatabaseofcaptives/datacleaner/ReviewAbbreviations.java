package projectH.historicaldatabaseofcaptives.datacleaner;


import org.springframework.stereotype.Component;

import java.util.List;

/*
sex, build, dentition, religion, childhood_status, marital, status, literacy, degree_of_crime, degree_of_punishment
these columns are using abbreviations , each of these must be validated
 */
@Component
public class ReviewAbbreviations {

    private final LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository;

    public ReviewAbbreviations(LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository) {
        this.localAbbreviatedEntityRepository = localAbbreviatedEntityRepository;
    }


    public void addAbbreviations(List<LocalAbbreviatedEntity> localAbbreviatedEntityList){
        localAbbreviatedEntityList.forEach(localAbbreviatedEntityRepository::save);

    }



}
