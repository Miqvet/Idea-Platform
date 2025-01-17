package itmo.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class Ticket {
    private final String origin;
    private final String destination;
    private final LocalTime departureTime;
    private final LocalTime arrivalTime;
    private final String carrier;
    private final int price;

    public Duration getFlightDuration() {
        return Duration.between(departureTime, arrivalTime);
    }
}