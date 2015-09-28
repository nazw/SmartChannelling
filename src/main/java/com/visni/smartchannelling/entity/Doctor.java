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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.AccountStatus;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.Cascade;

@Entity(name = "doctor")
@Table(name = "DOCTOR", uniqueConstraints = @UniqueConstraint(columnNames = "DOC_REGISTRATION_NUMBER"))
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String doctorId;
    private String docRegistrationNumber;
    private String description;
    private Set<DoctorSpecialization> doctorSpecializations;
    private Set<HospitalDoctor> hospitalDoctors;
    private Set<DoctorHospitalRole> doctorHospitalRoles;
    private SystemUserDetail systemUserDetail;
    private AccountStatus accountStatus;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.DoctorIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "DOCTOR_ID", length = 12)
    public String getDoctorId() {
	return doctorId;
    }

    public void setDoctorId(String doctorId) {
	this.doctorId = doctorId;
    }

    @Column(name = "DOC_REGISTRATION_NUMBER")
    public String getDocRegistrationNumber() {
	return docRegistrationNumber;
    }

    public void setDocRegistrationNumber(String docRegistrationNumber) {
	this.docRegistrationNumber = docRegistrationNumber;
    }

    @Column(name = "DESCRIPTION", length = 1000)
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
    public AccountStatus getAccountStatus() {
	return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
	this.accountStatus = accountStatus;
    }

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<DoctorSpecialization> getDoctorSpecializations() {
	return doctorSpecializations;
    }

    public void setDoctorSpecializations(Set<DoctorSpecialization> doctorSpecializations) {
	this.doctorSpecializations = doctorSpecializations;
    }

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<HospitalDoctor> getHospitalDoctors() {
	return hospitalDoctors;
    }

    public void setHospitalDoctors(Set<HospitalDoctor> hospitalDoctors) {
	this.hospitalDoctors = hospitalDoctors;
    }

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<DoctorHospitalRole> getDoctorHospitalRoles() {
	return doctorHospitalRoles;
    }

    public void setDoctorHospitalRoles(Set<DoctorHospitalRole> doctorHospitalRoles) {
	this.doctorHospitalRoles = doctorHospitalRoles;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYSTEM_USER_DETAIL_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public SystemUserDetail getSystemUserDetail() {
	return systemUserDetail;
    }

    public void setSystemUserDetail(SystemUserDetail systemUserDetail) {
	this.systemUserDetail = systemUserDetail;
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
