package com.udacity.pricing;

import com.udacity.pricing.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<Price,Long> {
}
