package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient maps;
    private final PriceClient prices;

    public CarService(CarRepository repository, MapsClient maps, PriceClient prices) {
        this.repository = repository;
        this.maps = maps;
        this.prices = prices;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car = repository.findById(id)
                .orElseThrow(CarNotFoundException::new);
        car.setPrice(prices.getPrice(id));
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return update(car);
        }
        car.setCreatedAt(null);
        car.setModifiedAt(null);
        car.setLocation(maps.getAddress(car.getLocation()));
        repository.saveAndFlush(car);
        car.setPrice(prices.getPrice(car.getId()));
        return repository.saveAndFlush(car);
    }

    /**
     * Updates a vehicle, based on prior existence of car
     * @param car An existing car object
     * @return the updated car as stored in the repository
     * @Throws CarNotFoundException if the given id does not exist
     */
    public Car update(Car car) {
        Long id = car.getId();
        Car oldCar = repository.findById(id).orElseThrow(CarNotFoundException::new);
        car.setCreatedAt(oldCar.getCreatedAt());
        car.setModifiedAt(null);
        car.setPrice(prices.getPrice(id));
        car.setLocation(maps.getAddress(car.getLocation()));
        repository.saveAndFlush(car);
        return car;
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Car car = repository.findById(id)
                .orElseThrow(CarNotFoundException::new);
        repository.delete(car);
    }
}
