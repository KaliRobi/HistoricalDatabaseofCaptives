package projectH.HistoricalDatabaseofCaptives.FrontControllerTests;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public void abbreviation_post_request_upload_test() throws URISyntaxException, ExecutionException, InterruptedException {
        String json =  "[{\"abbr\":\"b\", \"value_hu\" : \"börtön\", \"value_en\" : \"prison\", \"table_column\" : \"degree_of_punishment\", \"related_table\":  \"captives_data\"}, {\"abbr\":\"e\",\"value_hu\" : \"elzárás\",\"value_en\" : \"enclosure\",\"table_column\" : \"degree_of_punishment\",\"related_table\":  \"captives_data\"}]";
        String json2 = "[{ \"abbr\": \"a\",  \"value_hu\" :  \"alacsony\",  \"value_en\" :  \"titch\",  \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"e\",  \"value_hu\" :  \"erős\",  \"value_en\" :  \"strong\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"gy\",  \"value_hu\" :  \"gyenge\",  \"value_en\" :  \"weak\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"k\",  \"value_hu\" :  \"közepes\",  \"value_en\" :  \"medium\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"kc\",  \"value_hu\" :  \"köpcös\",  \"value_en\" :  \"knurl\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"kö\",  \"value_hu\" :  \"kövér\",  \"value_en\" :  \"plump\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"km\",  \"value_hu\" :  \"közép magas\",  \"value_en\" :  \"middling height\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"kny\",  \"value_hu\" :  \"közép nyulánk\",  \"value_en\" :  \"middling willowy\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"kk\",  \"value_hu\" :  \"közép kövér\", \"value_en\" : \"middling plump\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"ks\",  \"value_hu\" :  \"közép szikár\",  \"value_en\" :  \"middling lean\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"m\",  \"value_hu\" :  \"magas\",  \"value_en\" :  \"tall\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"n\",  \"value_hu\" :  \"növésben\",  \"value_en\" :  \"upgrowing\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"ny\",  \"value_hu\" :  \"nyulánk\",  \"value_en\" :  \"willowy\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"r\",  \"value_hu\" :  \"rendes\",  \"value_en\" :  \"regular \", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"s\",  \"value_hu\" :  \"szikár\",  \"value_en\" :  \"lean \", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"v\",  \"value_hu\" :  \"vézna\",  \"value_en\" :  \"thin \", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"vn\",  \"value_hu\" :  \"vékony növésben\",  \"value_en\" :  \"thin upgrowing\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"z\",  \"value_hu\" :  \"zömök\",  \"value_en\" :  \"thickset\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"h\",  \"value_hu\" :  \"hosszas\",  \"value_en\" :  \"tall\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"tö\",  \"value_hu\" :  \"tomzsi\"\t,  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"ro\",  \"value_hu\" :  \"rokkant\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"tör\",  \"value_hu\" :  \"törpe\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"cse\",  \"value_hu\" :  \"csenevész\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"töp\",  \"value_hu\" :  \"töpörödött\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"fej\",  \"value_hu\" :  \"fejlettlen\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"cso\",  \"value_hu\" :  \"csontos\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"kg\",  \"value_hu\" :  \"közép görnyedt\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"sug\",  \"value_hu\" :  \"sugar\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"},\n" +
                "{ \"abbr\": \"ker\",  \"value_hu\" :  \"kerek\",  \"value_en\" :  \"NA\", \"table_column\" :  \"Build\",  \"related_table\":   \"captives_data\"}]";

        String jsonReligion = "[{\"abbr\":\"b\"  , \"value_hu\" : \"baptista\",\"value_en\" :  \"Baptist\",  \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"e\"  , \"value_hu\" : \"evangélikus\"      ,\"value_en\" :  \"Evangelic\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"fn\" , \"value_hu\" : \"felekezet nélküli\" ,\"value_en\" : \"Without denomination\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"gk\" , \"value_hu\" : \"görög keleti\"      ,\"value_en\" : \"Greek Orthodox\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"i\"  , \"value_hu\" : \"izraelita\"         ,\"value_en\" : \"Israelite\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"lu\" , \"value_hu\" : \"luteránus\"         ,\"value_en\" : \"Lutheran\",  \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"r\"  , \"value_hu\" : \"református\"        ,\"value_en\" : \"Reformed\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"rk\" , \"value_hu\" : \"római katólikus\"   ,\"value_en\" : \"Roman Catholic\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"u\"  , \"value_hu\" : \"unitárius\"         ,\"value_en\" : \"Unitarian\",   \"table_column\" : \"religion\", \"related_table\":  \"captives_data\"}]";


        String jsonMaritalStatus  = "[{\"abbr\":\"e\" , \"value_hu\" : \"elvált\" ,\"value_en\" :\"divorced\" ,\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"eá\", \"value_hu\" : \"egyedülálló\"  ,\"value_en\" :\"single\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"evh\", \"value_hu\" :\"elvált, vadháhzas\" ,\"value_en\" :\"divorced lives in concubinage\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"h\" , \"value_hu\" :\"házas\"  ,\"value_en\" :\"married\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"hké\", \"value_hu\" :\"házas de külön élnek\",\"value_en\" : \"separated\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"hvh\", \"value_hu\" :\"házas de vadházastársi kapcsolata van\",\"value_en\" : \"married lives in concubinage\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"ö\"\", \"value_hu\" :\"özvegy\" ,\"value_en\" :\"widow\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"vh\", \"value_hu\" :\"vadházas\",\"value_en\" :\"concubinage\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"vhö\", \"value_hu\" :\"vadházas, özvegy\" ,\"value_en\" :\"widow lives in concubinage\",\"table_column\" : \"marital_status\", \"related_table\":  \"captives_data\"}]";
        
        
        String jsonDegreeOfPunishment = "[{\"abbr\":\"f\" , \"value_hu\" : \"fogház\" ,\"value_en\" :\"detention\" ,\"table_column\" : \"degree_of_punishment\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"fe\", \"value_hu\" :\"fegyház\",\"value_en\" : \"penitentiary\",\"table_column\" : \"degree_of_punishment\", \"related_table\":  \"captives_data\"},\n" +
                "{\"abbr\":\"szd\", \"value_hu\" :\"szigorított dologház\" ,\"value_en\" :\"workhouse\",\"table_column\" : \"degree_of_punishment\", \"related_table\":  \"captives_data\"}]";
        HttpRequest req = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI("http://localhost:8081/v1/AddAbbrevs")).POST(HttpRequest.BodyPublishers.ofString(jsonMaritalStatus)).build();

         CompletableFuture<HttpResponse<String>> response =  httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpStatus.ACCEPTED.value(), response.get().statusCode() );
    }

    public void create_a_captive(){

//        this needs to wait until I get access to the record storage so I can test it by real data;

    }
    @Test
    public void update_a_captive() throws URISyntaxException, ExecutionException, InterruptedException {

        Long id = 11L;
        String newPeculiarity = "{\"special_peculiarities\" : null, \"height\" : \"153\"}";

       HttpRequest req =  HttpRequest.newBuilder().header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(newPeculiarity))
                .uri(new URI("http://localhost:8081/v1/updateCaptiveV2/" + id )).build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.OK.value(), response.get().statusCode() );


    }

}
