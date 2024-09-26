package com.jgp.bmo.domain;


import com.jgp.participant.domain.Participant;
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
@Table(name = "bmo_participants_data")
public class BMOParticipantData extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

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

    @Column(name = "data_is_approved")
    private Boolean isDataApprovedByPartner;

    private transient Integer rowIndex;

    public BMOParticipantData() {
    }

    public BMOParticipantData(Partner partner, Participant participant, LocalDate dateFormSubmitted, Boolean isApplicantEligible, Integer tasAttended, Integer taSessionsAttended, Boolean isRecommendedForFinance, LocalDate decisionDate, String fiBusinessReferred, LocalDate dateRecordedByPartner, LocalDate dateRecordedToJGPDB, Integer rowIndex) {
        this.partner = partner;
        this.participant = participant;
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
        this.isDataApprovedByPartner = false;
    }

    public void approveData(Boolean approval){
        this.isDataApprovedByPartner = approval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BMOParticipantData bmoData = (BMOParticipantData) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), bmoData.getId())
                .append(getPartner(), bmoData.getPartner())
                .append(getParticipant(), bmoData.getParticipant())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getPartner()).append(getParticipant()).toHashCode();
    }
}
