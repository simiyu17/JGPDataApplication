package com.jgp.bmo.domain;


import com.jgp.client.domain.Client;
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

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "bmo_client_data")
public class BMOClientData extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

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

    public BMOClientData() {
    }

    public BMOClientData(Partner partner, Client client, LocalDate dateFormSubmitted, Boolean isApplicantEligible, Integer tasAttended, Integer taSessionsAttended, Boolean isRecommendedForFinance, LocalDate decisionDate, String fiBusinessReferred, LocalDate dateRecordedByPartner, LocalDate dateRecordedToJGPDB, Integer rowIndex) {
        this.partner = partner;
        this.client = client;
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

        BMOClientData bmoData = (BMOClientData) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), bmoData.getId())
                .append(getPartner(), bmoData.getPartner())
                .append(getClient(), bmoData.getClient())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getPartner()).append(getClient()).toHashCode();
    }
}
