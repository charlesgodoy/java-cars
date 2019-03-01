package com.lambdaschool.javacars;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j

public class CarsController {

    private final CarsRepository carsrepos;
    private final RabbitTemplate rt;

    public CarsController(CarsRepository carsrepos, RabbitTemplate rt) {
        this.carsrepos = carsrepos;
        this.rt = rt;
    }

    @GetMapping("/cars")
    public List<Cars> all() {
        return carsrepos.findAll();
    }

    @GetMapping("/cars/{id}")
    public Cars findOne(@PathVariable Long id) {
        return carsrepos.findById(id).orElseThrow(() -> new CarsNotFoundException(id));
    }

    @GetMapping("/cars/year/{year}")
    public List<Cars> findByYear(@PathVariable Long year) {
        List<Cars> carsList = carsrepos.findAll();
        return carsList.stream().filter(cars -> cars.getYear() == year).collect(Collectors.toList());
    }

    @GetMapping("/cars/brand/{brand}")
    public List<Cars> findByBrand(@PathVariable String brand) {
        CarsLog message = new CarsLog("Looked up cars" + brand);
        rt.convertAndSend(JavaCarsApplication.QUEUE_NAME, message.toString());
        List<Cars> carsList = carsrepos.findAll();
        return carsList.stream().filter(cars -> cars.getBrand().toLowerCase().equals(brand)).collect(Collectors.toList());
    }

    @PostMapping("/cars")
    public List<Cars> loadCarData(@RequestBody List<Cars> carsData) {
        CarsLog message = new CarsLog("Cars data successfully loaded");
        rt.convertAndSend(JavaCarsApplication.QUEUE_NAME, message.toString());
        log.info("Data uploaded");
        return carsrepos.saveAll(carsData);

    }

    @DeleteMapping("/cars/delete/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        carsrepos.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
