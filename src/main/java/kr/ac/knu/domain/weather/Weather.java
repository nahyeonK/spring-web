package kr.ac.knu.domain.weather;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by rokim on 2017. 12. 19..
 */
@Entity
@Data
public class Weather {
    @Id
    private String weatherDate;
    private long temperature;
    private String explain;
}
