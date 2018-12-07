package avanscoperta.inventory;


import org.junit.Before;
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

    @Autowired
    private ItemViewRepository repository;

    private static HttpEntity<String> asHttpEntity(String json) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void noItems() throws Exception {
        final ItemView[] response = template.getForObject(getItemsUrl(), ItemView[].class);
        assertThat(response.length).isEqualTo(0);
    }

    @Test
    public void postNewItem() throws Exception {
        ResponseEntity<Void> postResponse = template.
                postForEntity(postItemUrl(), asHttpEntity("{ \"name\":\"foo\", \"quantity\":42 }"), Void.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final ItemView[] getResponse = template.getForObject(getItemsUrl(), ItemView[].class);
        assertThat(getResponse.length).isEqualTo(1);
        assertThat(getResponse[0].getName()).isEqualTo("foo");
        assertThat(getResponse[0].getQuantity()).isEqualTo(42);
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

    private String getItemsUrl() throws MalformedURLException {
        return new URL("http://localhost:" + port + "/items/").toString();
    }
}