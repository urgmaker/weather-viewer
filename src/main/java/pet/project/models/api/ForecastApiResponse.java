package pet.project.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import pet.project.models.api.entity.Main;
import pet.project.models.api.entity.Weather;
import pet.project.models.api.util.UnixTimestampDeserializer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastApiResponse {

    @JsonProperty("list")
    private List<HourlyForecast> forecasts;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyForecast {
        @JsonProperty("dt")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime date;

        @JsonProperty("main")
        private Main main;

        @JsonProperty("weather")
        private List<Weather> weathers;
    }
}
