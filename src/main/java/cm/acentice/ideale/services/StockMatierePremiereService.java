package cm.acentice.ideale.services;

import cm.acentice.ideale.entities.ApprovisionnementMatieresPremieres;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class StockMatierePremiereService {

    private final RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/ideale/api/v1/approvisionnement/matierepremieres";

    public StockMatierePremiereService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ResponseEntity<String> getStockMatierePremiere() throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
        HttpEntity<String>entity  = new HttpEntity<>("params",httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET,entity,String.class);

        ApprovisionnementMatieresPremieres[] premieres = new ObjectMapper().readValue(result.getBody(),ApprovisionnementMatieresPremieres[].class);
        for (ApprovisionnementMatieresPremieres mat: premieres) {
            System.out.println(mat.getDevise());
        }
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        return null;
    }
}
