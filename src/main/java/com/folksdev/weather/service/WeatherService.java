package com.folksdev.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folksdev.weather.constants.Constants;
import com.folksdev.weather.dto.WeatherDto;
import com.folksdev.weather.dto.WeatherResponse;
import com.folksdev.weather.model.WeatherEntity;
import com.folksdev.weather.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {

  //private static final String API_URL = "http://api.weatherstack.com/current?access_key=e9caf462fab11b343b3758c443c778e4&query=";
  private final WeatherRepository weatherRepository;
  private final RestTemplate restTemplate;
  //to convert json to object
  private final ObjectMapper objectMapper = new ObjectMapper();

  public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
    this.weatherRepository = weatherRepository;
    this.restTemplate = restTemplate;
  }

  public WeatherDto getWeatherByCityName (String city){
    Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);

    return weatherEntityOptional.map(weather -> {
      if(weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))){
        return WeatherDto.convert(getWeatherFromWeatherStack(city));
      }
      return WeatherDto.convert(weather);
    }).orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStack(city)));
  }

  private WeatherEntity getWeatherFromWeatherStack(String city){
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(city), String.class);
    try {
      WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
      return saveWeatherEntity(city, weatherResponse);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

  private String getWeatherStackUrl (String city) {
    return Constants.API_URL + Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.ACCESS_QUERY_PARAM + city;
  }

  private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse){
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    WeatherEntity weatherEntity = new WeatherEntity(
            city,
            weatherResponse.location().name(),
            weatherResponse.location().country(),
            weatherResponse.current().temperature(),
            LocalDateTime.now(),
            LocalDateTime.parse(weatherResponse.location().localtime(), dateTimeFormatter)
    );
    return weatherRepository.save(weatherEntity);
  }
}
