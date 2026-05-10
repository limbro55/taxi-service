package org.example.trip_service.client;

import org.example.trip_service.dto.DriverDTO; // Нужно будет создать и этот DTO
import java.util.List;

public interface UserClient {
    // Метод для получения списка всех доступных водителей из другого сервиса
    List<DriverDTO> getAvailableDrivers();
}