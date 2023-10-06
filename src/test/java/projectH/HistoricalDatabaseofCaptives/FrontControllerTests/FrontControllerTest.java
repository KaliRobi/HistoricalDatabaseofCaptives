package projectH.HistoricalDatabaseofCaptives.FrontControllerTests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import projectH.HistoricalDatabaseofCaptives.DataCleaner.LocalAbbreviatedEntity;
import projectH.HistoricalDatabaseofCaptives.DataCleaner.LocalAbbreviatedEntityRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class FrontControllerTest {
// makes sure if the endpoint is up
    HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    public void abbreviation_post_request_endpoint_up_test() throws URISyntaxException, InterruptedException, ExecutionException {

        HttpRequest req = HttpRequest.newBuilder(new URI("http://localhost:8081/v1/AddAbbrevs")).GET().build();
        CompletableFuture< HttpResponse<String>> resp =
               httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), resp.get().statusCode() );

    }
    @Test
    public void abbreviation_post_request_upload_test() throws URISyntaxException, ExecutionException, InterruptedException, JsonProcessingException {
        String json =  "{\"abbr\":\"b\", \"value_hu\" : \"börtön\", \"value_en\" : \"prison\", \"table_column\" : \"degree_of_punishment\", \"related_table\":  \"captives_data\"}, {\"abbr\":\"e\",\"value_hu\" : \"elzárás\",\"value_en\" : \"enclosure\",\"table_column\" : \"degree_of_punishment\",\"related_table\":  \"captives_data\"}]";

        ObjectMapper objectMapper = new ObjectMapper();

        LocalAbbreviatedEntity JNode =  objectMapper.readValue(json, LocalAbbreviatedEntity.class);


        HttpRequest req = HttpRequest.newBuilder(new URI("http://localhost:8081/v1/AddAbbrevs"))
                        .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(JNode))).build();


             CompletableFuture<HttpResponse<String>>  response =  httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());


        assertEquals(HttpStatus.ACCEPTED.value(), response.get().statusCode() );




    }



}
