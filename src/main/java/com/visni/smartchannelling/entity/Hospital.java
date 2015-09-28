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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.AccountStatus;

@Entity(name = "hospital")
@Table(name = "HOSPITAL", uniqueConstraints = @UniqueConstraint(columnNames = "REGISTRATION_NUMBER"))
public class Hospital implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String hospitalId;
    private String hospitalName;
    private String registrationNumber;
    private String parentCompanyName;
    private String description;
    private String facilities;
    private String logo;
    private String webUrl;
    private double charges;
    private AccountStatus accountStatus;
    private List<HospitalManager> hopspitalManagers;
    private List<HospitalSpecialization> hospitalSpecializations;
    private List<HospitalDoctor> hospitalDoctors;
    private List<EmailAddress> emailAddresses;
    private List<ContactNumber> contactNumbers;
    private Address address;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.HospitalIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "HOSPITAL_ID", length = 12)
    public String getHospitalId() {
	return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
	this.hospitalId = hospitalId;
    }

    @Column(name = "HOSPITAL_NAME")
    public String getHospitalName() {
	return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
	this.hospitalName = hospitalName;
    }

    @Column(name = "REGISTRATION_NUMBER")
    public String getRegistrationNumber() {
	return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
	this.registrationNumber = registrationNumber;
    }

    @Column(name = "PARENT_COMPANY_NAME")
    public String getParentCompanyName() {
	return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
	this.parentCompanyName = parentCompanyName;
    }

    @Column(name = "DESCRIPTION", length = 1000)
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Column(name = "FACILITIES", length = 1000)
    public String getFacilities() {
	return facilities;
    }

    public void setFacilities(String facilities) {
	this.facilities = facilities;
    }

    @Column(name = "LOGO")
    public String getLogo() {
	return logo;
    }

    public void setLogo(String logo) {
	this.logo = logo;
    }

    @Column(name = "WEB_URL")
    public String getWebUrl() {
	return webUrl;
    }

    public void setWebUrl(String webUrl) {
	this.webUrl = webUrl;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
    public AccountStatus getAccountStatus() {
	return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
	this.accountStatus = accountStatus;
    }

    @OneToMany(mappedBy = "hospital")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<HospitalManager> getHopspitalManagers() {
	return hopspitalManagers;
    }

    public void setHopspitalManagers(List<HospitalManager> hopspitalManagers) {
	this.hopspitalManagers = hopspitalManagers;
    }

    @OneToMany(mappedBy = "hospital")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<HospitalSpecialization> getHospitalSpecializations() {
	return hospitalSpecializations;
    }

    public void setHospitalSpecializations(List<HospitalSpecialization> hospitalSpecializations) {
	this.hospitalSpecializations = hospitalSpecializations;
    }

    @OneToMany(mappedBy = "hospital")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<HospitalDoctor> getHospitalDoctors() {
	return hospitalDoctors;
    }

    public void setHospitalDoctors(List<HospitalDoctor> hospitalDoctors) {
	this.hospitalDoctors = hospitalDoctors;
    }

    @OneToMany(mappedBy = "hospital")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<EmailAddress> getEmailAddresses() {
	return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
	this.emailAddresses = emailAddresses;
    }

    @OneToMany(mappedBy = "hospital")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<ContactNumber> getContactNumbers() {
	return contactNumbers;
    }

    public void setContactNumbers(List<ContactNumber> contactNumbers) {
	this.contactNumbers = contactNumbers;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
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

    @Column(name = "HOSPITAL_CHARGES")
    public double getCharges() {
        return charges;
    }
    public void setCharges(double charges) {
        this.charges = charges;
    }

}
