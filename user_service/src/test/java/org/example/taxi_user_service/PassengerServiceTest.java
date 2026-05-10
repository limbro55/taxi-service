package org.example.taxi_user_service;

import org.example.taxi_user_service.dto.PassengerRequest;
import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.repository.PassengerRepository;
import org.example.taxi_user_service.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    private PassengerRepository repository;

    @InjectMocks
    private PassengerService service;

    @Test
    void testCreatePassengerLogic() {
        PassengerRequest request = new PassengerRequest();
        request.setName("Анна");
        request.setEmail("anna@mail.ru");
        request.setPhone("12345");

        Passenger savedPassenger = new Passenger();
        savedPassenger.setId(1L);
        savedPassenger.setName("Анна");
        savedPassenger.setEmail("anna@mail.ru");
        savedPassenger.setPhone("12345");

        when(repository.save(any(Passenger.class))).thenReturn(savedPassenger);

        Passenger result = service.create(request);

        assertEquals("Анна", result.getName());
        verify(repository, times(1)).save(any(Passenger.class));
    }
}