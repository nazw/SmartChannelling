package com.visni.smartchannelling.entity;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;
import org.hibernate.annotations.Cascade;

@Entity(name = "doctorSpecialization")
@Table(name = "DOCTOR_SPECIALIZATION")
public class DoctorSpecialization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String doctorSpecializationId;
	private Doctor doctor;
	private ActiveStatus activeStatus;
	private Specialization specialization;
	private int versionId;
    private CommonDomainProperty commanDomainProperty;
	
	@Id   
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.DoctorSpecializationIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "DOCTOR_SPECIALIZATION_ID",length=12)
	public String getDoctorSpecializationId() {
		return doctorSpecializationId;
	}

	public void setDoctorSpecializationId(String doctorSpecializationId) {
		this.doctorSpecializationId = doctorSpecializationId;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
	public ActiveStatus getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(ActiveStatus activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	@ManyToOne
	@JoinColumn(name = "SPECIALIZATION_ID")
        @Cascade(org.hibernate.annotations.CascadeType.ALL)
	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	
	@ManyToOne()
	@JoinColumn(name = "DOCTOR_ID")
        @Cascade(org.hibernate.annotations.CascadeType.ALL)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
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
