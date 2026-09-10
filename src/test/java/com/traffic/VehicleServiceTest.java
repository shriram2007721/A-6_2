package com.traffic;

import com.traffic.exception.InvalidVehicleException;
import com.traffic.model.Vehicle;
import com.traffic.model.VehicleType;
import com.traffic.service.VehicleService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest {

    @Test
    void testRegisterVehicle() {

        VehicleService service =
                new VehicleService();

        Vehicle vehicle =
                new Vehicle(
                        "TN58AB1234",
                        "Nishanth",
                        "9876543210",
                        VehicleType.CAR);

        service.registerVehicle(vehicle);

        assertEquals(
                1,
                service.getVehicleCount());

        assertEquals(
                vehicle,
                service.getVehicle("TN58AB1234"));
    }

    @Test
    void testInvalidVehicleNumber() {

        VehicleService service =
                new VehicleService();

        Vehicle vehicle =
                new Vehicle(
                        "",
                        "Nishanth",
                        "9876543210",
                        VehicleType.CAR);

        assertThrows(
                InvalidVehicleException.class,
                () -> service.registerVehicle(vehicle));
    }

    @Test
    void testInvalidPhoneNumber() {

        VehicleService service =
                new VehicleService();

        Vehicle vehicle =
                new Vehicle(
                        "TN58AB1234",
                        "Nishanth",
                        "12345",
                        VehicleType.CAR);

        assertThrows(
                InvalidVehicleException.class,
                () -> service.registerVehicle(vehicle));
    }

    @Test
    void testDuplicateVehicle() {

        VehicleService service =
                new VehicleService();

        Vehicle vehicle1 =
                new Vehicle(
                        "TN58AB1234",
                        "Nishanth",
                        "9876543210",
                        VehicleType.CAR);

        Vehicle vehicle2 =
                new Vehicle(
                        "TN58AB1234",
                        "Another Owner",
                        "9876543211",
                        VehicleType.BIKE);

        service.registerVehicle(vehicle1);

        assertThrows(
                InvalidVehicleException.class,
                () -> service.registerVehicle(vehicle2));
    }
}
