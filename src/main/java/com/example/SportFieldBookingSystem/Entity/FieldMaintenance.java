package com.example.SportFieldBookingSystem.Entity;

import com.example.SportFieldBookingSystem.Enum.FieldMaintenanceEnum;
import com.example.SportFieldBookingSystem.Enum.ReviewEnum;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "field_maintenance")
public class FieldMaintenance { // thong tin ve cac lan bao tri san

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private int maintenanceId;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Column(name = "maintenance_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date maintenanceDate;  // Ngày bảo trì

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // Mô tả bảo trì
    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private FieldMaintenanceEnum status;
}
