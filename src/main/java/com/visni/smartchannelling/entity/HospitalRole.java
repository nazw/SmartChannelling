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

@Entity(name = "hospitalRole")
@Table(name = "HOSPITAL_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }))
public class HospitalRole implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String hospitalRoleId;
    private Set<DoctorHospitalRole> doctorHospitalRoles;
    private String name;
    private String description;
    private ActiveStatus activeStatus;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.HospitalRoleIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "HOSPITAL_ROLE_ID", length = 12)
    public String getHospitalRoleId() {
	return hospitalRoleId;
    }

    public void setHospitalRoleId(String hospitalRoleId) {
	this.hospitalRoleId = hospitalRoleId;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
    public ActiveStatus getActiveStatus() {
	return activeStatus;
    }

    public void setActiveStatus(ActiveStatus activeStatus) {
	this.activeStatus = activeStatus;
    }

    @OneToMany(mappedBy = "hospitalRole")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<DoctorHospitalRole> getDoctorHospitalRoles() {
	return doctorHospitalRoles;
    }

    public void setDoctorHospitalRoles(Set<DoctorHospitalRole> doctorHospitalRoles) {
	this.doctorHospitalRoles = doctorHospitalRoles;
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
