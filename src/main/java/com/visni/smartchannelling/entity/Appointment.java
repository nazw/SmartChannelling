package com.visni.smartchannelling.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.BookingStatus;

@Entity(name = "appointment")
@Table(name = "APPOINTMENT")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    private String appointmentId;
    private SystemUser patient;
    private VisitingSolts visitingSolt;
    private BookingStatus bookingStatus;
    private ActiveStatus activeStatus;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.AppointmentIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "APPOINTMENT_ID", length = 12)
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public SystemUser getPatient() {
        return patient;
    }

    public void setPatient(SystemUser patient) {
        this.patient = patient;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VISITING_ID")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public VisitingSolts getVisitingSolt() {
        return visitingSolt;
    }

    public void setVisitingSolt(VisitingSolts visitingSolt) {
        this.visitingSolt = visitingSolt;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "BOOKING_STATUS")
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
    public ActiveStatus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Version
    @Column(name = "VERSION_ID")
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    //Supplimentary getters and setters used in db as surrogate keys
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "creationDate", column =
        @Column(name = "CREATION_DATE")),
        @AttributeOverride(name = "lastModifiedUser", column =
        @Column(name = "LAST_MODIFIED_USER")),
        @AttributeOverride(name = "lastModifiedDate", column =
        @Column(name = "LAST_MODIFIED_DATE")),
        @AttributeOverride(name = "createdUser", column =
        @Column(name = "CREATED_USER"))
    })
    public CommonDomainProperty getCommonProperties() {
        return commanDomainProperty;
    }

    public void setCommonProperties(CommonDomainProperty commanDomainProperty) {
        this.commanDomainProperty = commanDomainProperty;
    }
}
