package com.folksdev.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
  //private static final String API_URL = "http://api.weatherstack.com/current?access_key=e9caf462fab11b343b3758c443c778e4&query=";
  public static String API_URL;
  public static String ACCESS_KEY_PARAM = "?access_key=";
  public static String ACCESS_QUERY_PARAM = "&query=";
  public static String API_KEY;

  @Value("${weather-stack.api-url}")
  public void setApiUrl(String apiUrl){
    Constants.API_URL = apiUrl;
  }
  @Value("${weather-stack.api-key}")
  public void setApiKey(String apiKey) {
    Constants.API_KEY = apiKey;
  }
}
