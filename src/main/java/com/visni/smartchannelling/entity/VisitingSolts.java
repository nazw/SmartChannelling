package com.visni.smartchannelling.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;
import com.visni.smartchannelling.util.BookingStatus;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity(name = "visitingSolts")
@Table(name = "VISITING_SOLTS")
public class VisitingSolts implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String visitingSlotsId;
    private Visiting visiting;
    private Set<Appointment> appointments;
    private int slotNumber;
    private BookingStatus bookingStatus;
    private ActiveStatus activeStatus;
    private Date startTime;
    private Date endTime;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;



    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.VisitingSoltsIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "VISITING_SLOTS_ID", length = 12)
    public String getVisitingSlotsId() {
        return visitingSlotsId;
    }

    public void setVisitingSlotsId(String visitingSlotsId) {
        this.visitingSlotsId = visitingSlotsId;
    }

    @ManyToOne
    @JoinColumn(name = "VISITING_ID")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    public Visiting getVisiting() {
        return visiting;
    }

    public void setVisiting(Visiting visiting) {
        this.visiting = visiting;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "visitingSolt")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
    @Column(name = "SLOT_NUMBER")
    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
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

    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Version
    @Column(name = "VERSION_ID")
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

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
