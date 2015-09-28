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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;

@Entity(name = "hospitalSpecialization")
@Table(name = "HOSPITAL_SPECIALIZATION")
public class HospitalSpecialization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hospitalSpecializationId;
	private Hospital hospital;
	private ActiveStatus activeStatus;
	private Specialization specialization;
	private int versionId;
    private CommonDomainProperty commanDomainProperty;

	

	@Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.HospitalSpecializationIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "HOSPITAL_SPECIALIZATION_ID",length=12)
	public String getHospitalSpecializationId() {
		return hospitalSpecializationId;
	}

	public void setHospitalSpecializationId(String hospitalSpecializationId) {
		this.hospitalSpecializationId = hospitalSpecializationId;
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
	@JoinColumn(name = "HOSPITAL_ID")
	@Cascade(CascadeType.MERGE)
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@ManyToOne
	@JoinColumn(name = "SPECIALIZATION_ID")
	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
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
