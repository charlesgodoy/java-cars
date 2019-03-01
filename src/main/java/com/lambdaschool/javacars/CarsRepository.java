package com.lambdaschool.javacars;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository  extends JpaRepository<Cars, Long> {
}
