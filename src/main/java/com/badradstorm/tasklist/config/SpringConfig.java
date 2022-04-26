package com.badradstorm.tasklist.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

  //  Конфиг для того, чтобы в тестах корректно сравнивался текст на русском языке
  //  https://stackoverflow.com/questions/58525387/mockmvc-no-longer-handles-utf-8-characters-with-spring-boot-2-2-0-release
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.stream()
        .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
        .findFirst()
        .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(
            UTF_8));
  }
}