package itmo.course;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


public class TicketsAnalyzer {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Используйте: java TicketAnalyzer <path.json>");
            return;
        }

        String filePath = args[0];

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(filePath));
            List<Ticket> tickets = new ArrayList<>();

            for (JsonNode ticketNode : rootNode.get("tickets")) {
                LocalTime departureTime;
                if (ticketNode.get("departure_time").asText().length() == 4) departureTime =
                        LocalTime.parse('0' + ticketNode.get("departure_time").asText());
                else departureTime = LocalTime.parse(ticketNode.get("departure_time").asText());
                LocalTime arrivalTime;
                if (ticketNode.get("arrival_time").asText().length() == 4) arrivalTime =
                        LocalTime.parse('0' + ticketNode.get("arrival_time").asText());
                else arrivalTime = LocalTime.parse(ticketNode.get("arrival_time").asText());
                Ticket ticket = new Ticket(
                        ticketNode.get("origin").asText(),
                        ticketNode.get("destination").asText(),
                        departureTime,
                        arrivalTime,
                        ticketNode.get("carrier").asText(),
                        ticketNode.get("price").asInt()
                );
                tickets.add(ticket);
            }

            List<Ticket> filteredTickets = tickets.stream()
                    .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                    .toList();

            Map<String, Duration> minFlightTimes = new HashMap<>();
            for (Ticket ticket : filteredTickets) {
                Duration flightDuration = ticket.getFlightDuration();
                minFlightTimes.merge(ticket.getCarrier(), flightDuration, (existing, newValue) ->
                        existing.compareTo(newValue) < 0 ? existing : newValue);
            }

            System.out.println("Минимальное время:");
            for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
                System.out.printf("Борт: %s, Время в пути: %d min\n",
                        entry.getKey(), entry.getValue().toMinutes());
            }


            List<Integer> prices = filteredTickets.stream()
                    .map(Ticket::getPrice)
                    .sorted()
                    .toList();

            double averagePrice = prices.stream().mapToInt(Integer::intValue).average().orElse(0);
            double medianPrice = calculateMedian(prices);
            double priceDifference = averagePrice - medianPrice;

            System.out.printf("\nСреднее: %.3f\n", averagePrice);
            System.out.printf("Медиана: %.3f\n", medianPrice);
            System.out.printf("Разница: %.3f\n", priceDifference);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static double calculateMedian(List<Integer> prices) {
        int size = prices.size();
        if (size == 0){
            return 0;
        } else if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }

}