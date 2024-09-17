package com.example.SportFieldBookingSystem.Entity;

import jakarta.persistence.*;
@Entity
@Table(name = "cancellation_policy")
public class CancellationPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private int policyId;

    @OneToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Column(name = "cancellation_fee", nullable = false)
    private double cancellationFee;  // Phí hủy

    @Column(name = "advance_notice", nullable = false)
    private int advanceNotice;  // Thời gian thông báo trước tính bằng giờ

    public CancellationPolicy() {

    }

    public CancellationPolicy(int policyId, Field field, double cancellationFee, int advanceNotice) {
        this.policyId = policyId;
        this.field = field;
        this.cancellationFee = cancellationFee;
        this.advanceNotice = advanceNotice;
    }
}
