package projectH.HistoricalDatabaseofCaptives.GISData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class TargetLink {

    private URI link;

    private   String target;



    public TargetLink(URI link, String target) {
        this.link = link;

        this.target = target;
    }

    public TargetLink() {

    }

    public URI getLink() {
        return link;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "TargetLink{" +
                "link=" + link +
                ", target='" + target + '\'' +
                '}';
    }
}
