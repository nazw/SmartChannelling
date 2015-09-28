package com.visni.smartchannelling.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;

@Entity(name = "visiting")
@Table(name = "VISITING")
public class Visiting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String visitingId;
	private HospitalDoctor hospitalDoctor;
	private int dayOfWeek;
	private Date startDate;
	private Date startTime;
	private Date endDate;
	private Date endTime;
	private int avgTimePerAppointment;
	private double avgDelayPerAppointment;
	private int noOfPatients;
	private double charges;
	private ActiveStatus visitingStatus;
	private List<VisitingSolts> visitingSolts;
	private int versionId;
    private CommonDomainProperty commonDomainProperty;

	@Id   
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.VisitingIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "VISITING_ID",length=12)
	public String getVisitingId() {
		return visitingId;
	}

	public void setVisitingId(String visitingId) {
		this.visitingId = visitingId;
	}
	
	@ManyToOne
	@JoinColumn(name = "HOSPITAL_DOCTOR_ID")
	public HospitalDoctor getHospitalDoctor() {
		return hospitalDoctor;
	}

	public void setHospitalDoctor(HospitalDoctor hospitalDoctor) {
		this.hospitalDoctor = hospitalDoctor;
	}

	@OneToMany(mappedBy = "visiting")
	@Cascade(CascadeType.ALL)
	public List<VisitingSolts> getVisitingSolts() {
		return visitingSolts;
	}
	public void setVisitingSolts(List<VisitingSolts> visitingSolts) {
		this.visitingSolts = visitingSolts;
	}
	

	@Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "END_TIME")
    @Temporal(TemporalType.TIME)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "AVG_TIME_PER_APPOINTMENT")
	public int getAvgTimePerAppointment() {
		return avgTimePerAppointment;
	}

	public void setAvgTimePerAppointment(int avgTimePerAppointment) {
		this.avgTimePerAppointment = avgTimePerAppointment;
	}

	@Column(name = "AVG_DELAY_PER_APPOINTMENT")
	public double getAvgDelayPerAppointment() {
		return avgDelayPerAppointment;
	}

	public void setAvgDelayPerAppointment(double avgDelayPerAppointment) {
		this.avgDelayPerAppointment = avgDelayPerAppointment;
	}

	@Column(name = "NO_OF_PATIENTS")
	public int getNoOfPatients() {
		return noOfPatients;
	}

	public void setNoOfPatients(int noOfPatients) {
		this.noOfPatients = noOfPatients;
	}

	@Column(name = "CHARGES")
	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	
	
    @Column(name = "DAY_OF_WEEK")
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}


	@Enumerated(EnumType.STRING)
    @Column(name = "VISITING_STATUS")
	public ActiveStatus getVisitingStatus() {
		return visitingStatus;
	}	
	public void setVisitingStatus(ActiveStatus visitingStatus) {
		this.visitingStatus = visitingStatus;
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
    public CommonDomainProperty getCommonDomainProperty() {
		return commonDomainProperty;
	}

	public void setCommonDomainProperty(CommonDomainProperty commonDomainProperty) {
		this.commonDomainProperty = commonDomainProperty;
	}

	
	

}
