package com.yabanci.ayrotektaxcalculator.repository;

import com.yabanci.ayrotektaxcalculator.model.ProductType;
import com.yabanci.ayrotektaxcalculator.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax,Long> {

    Tax findByProductType(ProductType productType);
}
