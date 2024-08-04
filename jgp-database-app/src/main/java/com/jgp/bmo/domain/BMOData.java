package com.jgp.bmo.domain;


import com.jgp.patner.domain.Partner;
import com.jgp.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "bmo_data")
public class BMOData extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

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

    @Column(name = "form_submitted_on")
    private LocalDate dateFormSubmitted;

    @Column(name = "is_applicant_eligible")
    private Boolean isApplicantEligible;

    @Column(name = "tas_attended")
    private Integer tasAttended;

    @Column(name = "ta_sessions_attended")
    private Integer taSessionsAttended;

    @Column(name = "recommended_for_finance")
    private Boolean isRecommendedForFinance;

    @Column(name = "decision_date")
    private LocalDate decisionDate;

    @Column(name = "fi_business_referred")
    private String fiBusinessReferred;

    @Column(name = "date_partner_recorded")
    private LocalDate dateRecordedByPartner;

    @Column(name = "date_recorded_to_jgp")
    private LocalDate dateRecordedToJGPDB;

    private transient Integer rowIndex;

    public BMOData() {
    }

    public BMOData(Partner partner, String businessName, String jgpId, String phoneNumber, String ownerGender, Integer ownerAge, String businessLocation, String industrySector, String businessSegment, Boolean isBusinessRegistered, String registrationNumber, Boolean hasBMOMembership, BigDecimal bestMonthlyRevenue, BigDecimal worstMonthlyRevenue, Integer totalRegularEmployees, Integer youthRegularEmployees, Integer totalCasualEmployees, Integer youthCasualEmployees, String sampleRecords, String taNeeds, String personWithDisability, String refugeeStatus, LocalDate dateFormSubmitted, Boolean isApplicantEligible, Integer tasAttended, Integer taSessionsAttended, Boolean isRecommendedForFinance, LocalDate decisionDate, String fiBusinessReferred, LocalDate dateRecordedByPartner, LocalDate dateRecordedToJGPDB, Integer rowIndex) {
        this.partner = partner;
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
        this.dateFormSubmitted = dateFormSubmitted;
        this.isApplicantEligible = isApplicantEligible;
        this.tasAttended = tasAttended;
        this.taSessionsAttended = taSessionsAttended;
        this.isRecommendedForFinance = isRecommendedForFinance;
        this.decisionDate = decisionDate;
        this.fiBusinessReferred = fiBusinessReferred;
        this.dateRecordedByPartner = dateRecordedByPartner;
        this.dateRecordedToJGPDB = dateRecordedToJGPDB;
        this.rowIndex = rowIndex;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BMOData bmoData = (BMOData) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), bmoData.getId())
                .append(getBusinessName(), bmoData.getBusinessName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getBusinessName()).toHashCode();
    }
}
