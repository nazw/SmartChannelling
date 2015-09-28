package com.visni.smartchannelling.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;
import org.hibernate.annotations.Cascade;

@Entity(name = "specialization")
@Table(name = "SPECIALIZATION", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }))
public class Specialization implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String specializationId;
    private String name;
    private String description;
    private String area;
    private ActiveStatus activeStatus;
    private Set<HospitalSpecialization> hospitalSpecializations;
    private Set<DoctorSpecialization> doctorSpecializations;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.SpecializationIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "SPECIALIZATION_ID", length = 12)
    public String getSpecializationId() {
	return specializationId;
    }

    public void setSpecializationId(String specializationId) {
	this.specializationId = specializationId;
    }

    @Column(name = "NAME")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Column(name = "AREA")
    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
    public ActiveStatus getActiveStatus() {
	return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
	this.activeStatus = activeStatus;
    }

    @OneToMany(mappedBy = "specialization")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<HospitalSpecialization> getHospitalSpecializations() {
	return hospitalSpecializations;
    }

    public void setHospitalSpecializations(Set<HospitalSpecialization> hospitalSpecializations) {
	this.hospitalSpecializations = hospitalSpecializations;
    }

    @OneToMany(mappedBy = "specialization")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<DoctorSpecialization> getDoctorSpecializations() {
	return doctorSpecializations;
    }

    public void setDoctorSpecializations(Set<DoctorSpecialization> doctorSpecializations) {
	this.doctorSpecializations = doctorSpecializations;
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

}
