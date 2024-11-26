package com.example.SportFieldBookingSystem.Mapper;
import com.example.SportFieldBookingSystem.DTO.FieldDTO.FieldCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldImageDTO.FieldImageCreateDTO;
import com.example.SportFieldBookingSystem.DTO.FieldTimeRuleDTO.FieldTimeRuleDTO;
import com.example.SportFieldBookingSystem.DTO.TimeSlotDTO.TimeSlotResponseDTO;
import com.example.SportFieldBookingSystem.Entity.Field;
import com.example.SportFieldBookingSystem.Entity.FieldImage;
import com.example.SportFieldBookingSystem.Entity.FieldTimeRule;
import com.example.SportFieldBookingSystem.Entity.TimeSlot;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

        // Tạo ánh xạ từ DTO sang Entity
//        modelMapper.addMappings(new PropertyMap<FieldCreateDTO, Field>() {
//            @Override
//            protected void configure() {
//                map(source.getFieldTypeId(), destination.getFieldType().getFieldTypeId()); // Chuyển ID FieldType sang FieldType
//                map(source.getUserId(), destination.getUser().getUserId()); // Chuyển ID User sang User
//
//                // Chuyển fieldImageUrls từ DTO thành đối tượng FieldImage
//                using(new Converter<List<FieldImageCreateDTO>, List<FieldImage>>() {
//                    public List<FieldImage> convert(MappingContext<List<FieldImageCreateDTO>, List<FieldImage>> context) {
//                        List<FieldImageCreateDTO> fieldImageCreateDTOs = context.getSource();
//                        List<FieldImage> fieldImages = new ArrayList<>();
//                        for (FieldImageCreateDTO dto : fieldImageCreateDTOs) {
//                            FieldImage fieldImage = new FieldImage();
//                            fieldImage.setImageUrl(dto.getImageUrl());  // Giả sử `FieldImageCreateDTO` có trường `imageUrl`
//                            fieldImages.add(fieldImage);
//                        }
//                        return fieldImages;
//                    }
//                }).map(source.getFieldImageList(), destination.getFieldImageList());

                // Chuyển fieldTimeRuleList từ DTO thành đối tượng FieldTimeRule
//                using(new Converter<List<FieldTimeRuleDTO>, List<FieldTimeRule>>() {
//                    public List<FieldTimeRule> convert(MappingContext<List<FieldTimeRuleDTO>, List<FieldTimeRule>> context) {
//                        List<FieldTimeRuleDTO> fieldTimeRuleDTOs = context.getSource();
//                        List<FieldTimeRule> fieldTimeRules = new ArrayList<>();
//                        for (FieldTimeRuleDTO dto : fieldTimeRuleDTOs) {
//                            FieldTimeRule fieldTimeRule = new FieldTimeRule();
//                            // Giả sử FieldTimeRuleDTO có các trường cần thiết
//                            fieldTimeRule.setStartTime(dto.getStartTime());
//                            fieldTimeRule.setEndTime(dto.getEndTime());
//                            fieldTimeRules.add(fieldTimeRule);
//                        }
//                        return fieldTimeRules;
//                    }
//                }).map(source.getFieldTimeRuleList(), destination.getFieldTimeRuleList());
//            }
//        });
//
        return modelMapper;
    }

}