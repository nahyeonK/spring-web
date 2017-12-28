package kr.ac.knu.repository;

import kr.ac.knu.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rokim on 2017. 12. 19..
 */
public interface WeatherRepository extends JpaRepository<Weather, String>{
    Weather findByWeatherDate(String weatherDate);
}
