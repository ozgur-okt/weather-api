package com.folksdev.weather.controller;

import com.folksdev.weather.dto.WeatherDto;
import com.folksdev.weather.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
public class WeatherAPI {

  private final WeatherService weatherService;

  public WeatherAPI(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @RateLimiter(name = "basic")
  @GetMapping("/{city}")
  public ResponseEntity<WeatherDto> getWeather (@PathVariable("city") String city){
    return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
  }
}
