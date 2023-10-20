package pet.project.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import pet.project.models.api.entity.Clouds;
import pet.project.models.api.entity.Main;
import pet.project.models.api.entity.Weather;
import pet.project.models.api.entity.Wind;
import pet.project.models.api.util.UnixTimestampDeserializer;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime date;

    @JsonProperty("sys")
    private Sys sys;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("sunrise")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunriseTime;

        @JsonProperty("sunset")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunsetTime;
    }
}
