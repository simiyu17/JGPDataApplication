package com.jgp.participant.domain;

import com.jgp.participant.dto.ParticipantDto;
import com.jgp.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "participants")
public class Participant extends BaseEntity {

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "jgp_id")
    private String jgpId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "owner_gender")
    private String ownerGender;

    @Column(name = "owner_age")
    private Integer ownerAge;

    @Column(name = "business_location")
    private String businessLocation;

    @Column(name = "industry_sector")
    private String industrySector;

    @Column(name = "business_segment")
    private String businessSegment;

    @Column(name = "is_business_registered")
    private Boolean isBusinessRegistered;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "has_bmo_membership")
    private Boolean hasBMOMembership;

    @Column(name = "bmo_membership")
    private String bmoMembership;

    @Column(name = "best_monthly_revenue")
    private BigDecimal bestMonthlyRevenue;

    @Column(name = "worst_monthly_revenue")
    private BigDecimal worstMonthlyRevenue;

    @Column(name = "total_regular_employees")
    private Integer totalRegularEmployees;

    @Column(name = "youth_regular_employees")
    private Integer youthRegularEmployees;

    @Column(name = "total_casual_employees")
    private Integer totalCasualEmployees;

    @Column(name = "youth_casual_employees")
    private Integer youthCasualEmployees;

    @Column(name = "sample_records")
    private String sampleRecords;

    @Column(name = "ta_needs")
    private String taNeeds;

    @Column(name = "person_with_disability")
    private String personWithDisability;

    @Column(name = "refugee_status")
    private String refugeeStatus;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_eligible")
    private Boolean isEligible;

    public Participant() {
    }

    private Participant(String businessName, String jgpId, String phoneNumber, String ownerGender, Integer ownerAge, String businessLocation, String industrySector, String businessSegment, Boolean isBusinessRegistered, String registrationNumber, Boolean hasBMOMembership, String bmoMembership, BigDecimal bestMonthlyRevenue, BigDecimal worstMonthlyRevenue, Integer totalRegularEmployees, Integer youthRegularEmployees, Integer totalCasualEmployees, Integer youthCasualEmployees, String sampleRecords, String taNeeds, String personWithDisability, String refugeeStatus, Boolean isActive) {
        this.businessName = businessName;
        this.jgpId = jgpId;
        this.phoneNumber = phoneNumber;
        this.ownerGender = ownerGender;
        this.ownerAge = ownerAge;
        this.businessLocation = businessLocation;
        this.industrySector = industrySector;
        this.businessSegment = businessSegment;
        this.isBusinessRegistered = isBusinessRegistered;
        this.registrationNumber = registrationNumber;
        this.hasBMOMembership = hasBMOMembership;
        this.bmoMembership = bmoMembership;
        this.bestMonthlyRevenue = bestMonthlyRevenue;
        this.worstMonthlyRevenue = worstMonthlyRevenue;
        this.totalRegularEmployees = totalRegularEmployees;
        this.youthRegularEmployees = youthRegularEmployees;
        this.totalCasualEmployees = totalCasualEmployees;
        this.youthCasualEmployees = youthCasualEmployees;
        this.sampleRecords = sampleRecords;
        this.taNeeds = taNeeds;
        this.personWithDisability = personWithDisability;
        this.refugeeStatus = refugeeStatus;
        this.isActive = isActive;
    }

    public static Participant createClient(ParticipantDto dto){
        return new Participant(dto.businessName(), dto.jgpId(), dto.phoneNumber(), dto.ownerGender(),
                dto.ownerAge(), dto.businessLocation(), dto.industrySector(), dto.businessSegment(),
                dto.isBusinessRegistered(), dto.registrationNumber(), dto.hasBMOMembership(),
                dto.bmoMembership(), dto.bestMonthlyRevenue(), dto.worstMonthlyRevenue(),
                dto.totalRegularEmployees(), dto.youthRegularEmployees(), dto.totalCasualEmployees(),
                dto.youthCasualEmployees(), dto.sampleRecords(), dto.taNeeds(),
                dto.personWithDisability(), dto.refugeeStatus(), Boolean.TRUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Participant client = (Participant) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), client.getId())
                .append(getJgpId(), client.getJgpId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getJgpId()).toHashCode();
    }
}
