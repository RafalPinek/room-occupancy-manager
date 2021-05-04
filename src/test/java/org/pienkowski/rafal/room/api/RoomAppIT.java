package org.pienkowski.rafal.room.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoomAppIT {

    private static final String LOCAL_URL_PATTERN = "http://localhost:%s%s/api/v1/allocate?premium=%d&economy=%d&potential-guests=23,45,155,374,22,99.99,100,101,115,209";

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String context;

    @Test
    public void shouldHitAppEndpoint() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        int freePremiumCount = 7;
        int freeEconomyCount = 1;
        int expectedPremiumRoomsCount = 7;
        int expectedEconomyRoomsCount = 1;
        BigDecimal expectedPremiumTotal = BigDecimal.valueOf(1153.99);
        BigDecimal expectedEconomyTotal = BigDecimal.valueOf(45);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format(LOCAL_URL_PATTERN, port, context, freePremiumCount, freeEconomyCount)))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        // when
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        HotelUsage hotelUsage = objectMapper.readValue(response.body(), HotelUsage.class);
        assertEquals(expectedPremiumRoomsCount, hotelUsage.premium().count());
        assertEquals(expectedEconomyRoomsCount, hotelUsage.economy().count());
        assertEquals(expectedPremiumTotal, hotelUsage.premium().total().amount());
        assertEquals(expectedEconomyTotal, hotelUsage.economy().total().amount());
    }

    @Test
    public void shouldReceiveBadRequest() throws Exception {
        // given
        int invalidPremiumCount = -2;
        int invalidEconomyCount = -3;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format(LOCAL_URL_PATTERN, port, context, invalidPremiumCount, invalidEconomyCount)))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        // when
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
