package com.visni.smartchannelling.entity;

import java.io.Serializable;
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
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;

@Entity(name = "hospitalDoctor")
@Table(name = "HOSPITAL_DOCTOR")
public class HospitalDoctor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hospitalDoctorId;
    private Doctor doctor;
    private Hospital hospital;
    private ActiveStatus activeStatus;
    private List<Visiting> visitingList;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.HospitalDoctorIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "HOSPITAL_DOCTOR_ID", length = 12)
    public String getHospitalDoctorId() {
	return hospitalDoctorId;
    }

    public void setHospitalDoctorId(String hospitalDoctorId) {
	this.hospitalDoctorId = hospitalDoctorId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
    public ActiveStatus getActiveStatus() {
	return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
	this.activeStatus = activeStatus;
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

    @ManyToOne
    @JoinColumn(name = "HOSPITAL_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Hospital getHospital() {
	return hospital;
    }

    public void setHospital(Hospital hospital) {
	this.hospital = hospital;
    }

    @OneToMany(mappedBy = "hospitalDoctor")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<Visiting> getVisitingList() {
	return visitingList;
    }

    public void setVisitingList(List<Visiting> visitingList) {
	this.visitingList = visitingList;
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
    @AttributeOverrides({ @AttributeOverride(name = "creationDate", column = @Column(name = "CREATION_DATE")),
	    @AttributeOverride(name = "lastModifiedUser", column = @Column(name = "LAST_MODIFIED_USER")),
	    @AttributeOverride(name = "lastModifiedDate", column = @Column(name = "LAST_MODIFIED_DATE")),
	    @AttributeOverride(name = "createdUser", column = @Column(name = "CREATED_USER")) })
    public CommonDomainProperty getCommonProperties() {
	return commanDomainProperty;
    }

    public void setCommonProperties(CommonDomainProperty commanDomainProperty) {
	this.commanDomainProperty = commanDomainProperty;
    }
    
    @Override
    public String toString() {
     
        return "~~~ Hospital : " +hospital.getHospitalId()+"#"+hospital.getHospitalName()
        	+"\n~~~ Doctor : " + doctor.getDoctorId()+"#"+doctor.getSystemUserDetail().getFirstName() + " " + doctor.getSystemUserDetail().getLastName();
    }
    
}
