package com.yabanci.ayrotektaxcalculator.converter;

import com.yabanci.ayrotektaxcalculator.dto.TaxDto;
import com.yabanci.ayrotektaxcalculator.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotektaxcalculator.model.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    TaxMapper INSTANCE = Mappers.getMapper(TaxMapper.class);

    Tax convertToTax(TaxSaveRequestDto taxSaveRequestDto);
    TaxDto convertToTaxDto(Tax tax);

    List<TaxDto> convertToTaxDtoList(List<Tax> taxes);
}