package telran.cvbank.service;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import telran.cvbank.exceptions.WrongCityException;
import telran.cvbank.service.interfaces.WeatherService;

@Service(value = "openWeather")
public class OpenWeatherService implements WeatherService {

	@Value("${API_KEY}")
	String API_KEY;
	@Value("${BASE_URL}")
	String BASE_URL;

	ModelMapper mapper;

	@Autowired
	public OpenWeatherService(ModelMapper mapper) {
		this.mapper = mapper;
	}

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public Double[] getCoordinatesByCity(String city) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(BASE_URL)
				.queryParam("q", city)
				.queryParam("appid", API_KEY);
		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, builder.build().toUri());
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = restTemplate.exchange(requestEntity, String.class);
		} catch (RestClientException e) {
			throw new WrongCityException("City " + city + " doesn't exist");
		}
		Map<String, Object> data = new Gson().fromJson(responseEntity.getBody(), new TypeToken<HashMap<String, Object>>() {}.getType());
		Map<String, Double> coordinatesMap = (Map<String, Double>) data.get("coord");
		Double[] coordinates = { coordinatesMap.get("lon"), coordinatesMap.get("lat") };
		return coordinates;
	}
}
