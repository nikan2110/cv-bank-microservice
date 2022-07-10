package telran.cvbank.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import telran.cvbank.dto.exceptions.WrongCityException;

@Service(value = "openWeather")
public class OpenWeatherService implements WeatherService {


//    @Value("${API_KEY}")
    String API_KEY;
//    @Value("${BASE_URL}")
    String BASE_URL;

    @Override
    public Double[] getCoordinatesByCity(String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL).queryParam("q", city)
                .queryParam("appid", API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, builder.build().toUri());
        ResponseEntity<List> responseEntity = null;
        responseEntity = restTemplate.exchange(requestEntity, List.class);
        HashMap<String, Double> data;
        try {
            data = (HashMap<String, Double>) responseEntity.getBody().get(0);
        } catch (Exception e) {
            throw new WrongCityException();
        }
        Double[] coordinates = {data.get("lon"), data.get("lat")};
        return coordinates;
    }
}
