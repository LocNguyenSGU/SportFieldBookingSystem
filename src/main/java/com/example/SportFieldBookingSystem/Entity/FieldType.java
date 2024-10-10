package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="field_type")
public class FieldType { // loai san
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_type_id")
    private int fieldTypeId;
    @Column(name="field_type_name")
    private String fieldTypeName;
    @Column(name="field_type_desc")
    private String fieldTypeDesc;
    @OneToMany(mappedBy = "fieldType")
    private List<Field> fields;
}
