package kr.ac.knu.controller;

import kr.ac.knu.domain.weather.Weather;
import kr.ac.knu.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2017. 12. 19..
 */
@Controller
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherRepository weatherRepository;

    @RequestMapping("/index")
    public String index(Model model) throws Exception  {
        List<Weather> weathers = weatherRepository.findAll();
        model.addAttribute("weathers", weathers);

        return "/weather/index";
    }

    @RequestMapping("/{weatherDate}")
    public String weather(Model model, @PathVariable String weatherDate) {
        Weather weather = weatherRepository.findByWeatherDate(weatherDate);
        model.addAttribute("weather", weather);

        return "/weather/detail";
    }
}
