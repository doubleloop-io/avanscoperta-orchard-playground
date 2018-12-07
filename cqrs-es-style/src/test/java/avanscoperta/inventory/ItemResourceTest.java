package avanscoperta.inventory;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;
//
//    @Test
//    public void itWorksGet() throws Exception {
//        final URL url = new URL("http://localhost:" + port + "/items/");
//        ResponseEntity<String> response = template.getForEntity(url.toString(), String.class);
//        assertThat(response.getBody()).isEqualTo("ciao!");
//    }

    private static HttpEntity<String> asHttpEntity(String json) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }

    @Test
    public void postNewItem() throws Exception {

        ResponseEntity<Void> response = template.
                postForEntity(postItemUrl(), asHttpEntity("{ \"name\":\"foo\", \"quantity\":42 }"), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void postNewItem_emptyName() throws Exception {
        ResponseEntity<Void> response = template.
                postForEntity(postItemUrl(), asHttpEntity("{ \"name\":\"\", \"quantity\":42 }"), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postNewItem_missingName() throws Exception {
        ResponseEntity<Void> response = template.
                postForEntity(postItemUrl(), asHttpEntity("{ \"quantity\":42 }"), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private String postItemUrl() throws MalformedURLException {
        return new URL("http://localhost:" + port + "/items/").toString();
    }
}