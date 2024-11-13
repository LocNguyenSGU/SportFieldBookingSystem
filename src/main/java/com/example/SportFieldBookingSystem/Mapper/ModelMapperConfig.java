package com.example.SportFieldBookingSystem.Mapper;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.typeMap(TimeSlot.class, TimeSlotResponseDTO.class)
//                .addMappings(mapper -> mapper.map(TimeSlot::getField, TimeSlotResponseDTO::setField));
        Converter<Collection<?>, List<?>> collectionToListConverter = new Converter<Collection<?>, List<?>>() {
            @Override
            public List<?> convert(MappingContext<Collection<?>, List<?>> context) {
                return new ArrayList<>(context.getSource());
            }
        };

        modelMapper.addConverter(collectionToListConverter);
        return modelMapper;
    }
}