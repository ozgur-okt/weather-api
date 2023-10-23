package com.folksdev.weather.service;

import com.folksdev.weather.dto.WeatherDto;
import com.folksdev.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
  private final WeatherRepository weatherRepository;

  public WeatherService(WeatherRepository weatherRepository) {
    this.weatherRepository = weatherRepository;
  }

  public WeatherDto getWeatherByCityName (String city){

  }
}
