package kr.ac.knu.controller;

import kr.ac.knu.domain.weather.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/jsp")
public class JspController {
    @RequestMapping("/index")
    public String index() {
        return "/WEB-INF/jsp/index.jsp";
    }

    @RequestMapping("/weather")
    public String weather(Model model) throws Exception {
        /*
        Service Logic
         */
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:knu_2017", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM WEATHER");
        ResultSet rs = pstmt.executeQuery();

        /*
        Make Model
         */
        List<Weather> weathers = new ArrayList();
        while(rs.next()) {
            Weather weather = new Weather();
            weather.setWeatherDate(rs.getString("weather_date"));
            weather.setTemperature(rs.getLong("temperature"));
            weather.setExplain(rs.getString("explain"));
            weathers.add(weather);
        }

        /*
        Set Model and Send to View
         */
        model.addAttribute("weathers", weathers);
        return "/WEB-INF/jsp/weather.jsp";
    }
}
