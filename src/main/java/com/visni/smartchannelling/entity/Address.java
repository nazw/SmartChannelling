package com.visni.smartchannelling.entity;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "address")
@Table(name = "ADDRESS")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    private String addressId;
    private String addressValue;
    private String streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String zipCode;
    private String googleMapPoint;
	private Hospital hospital;
    private SystemUser systemUser;
	private int versionId;
    private CommonDomainProperty commonProperties;

 
    @Id   
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.AddressIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "ADDRESS_ID",length=12)
    public String getAddressId() {
        return addressId;
    }
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
	
	@Column(name = "ADDRESS_VALUE")
    public String getAddressValue() {
        return addressValue;
    }
    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }    
    
    @Column(name = "STREET_NUMBER")
    public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	@Column(name = "STREET_NAME")
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "STATE")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	
	
	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Column(name = "POSTAL_CODE")
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Column(name = "GOOGLE_MAP_POINT")
	public String getGoogleMapPoint() {
		return googleMapPoint;
	}
	public void setGoogleMapPoint(String googleMapPoint) {
		this.googleMapPoint = googleMapPoint;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
	@Cascade(org.hibernate.annotations.CascadeType.MERGE)
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
	public SystemUser getSystemUser() {
		return systemUser;
	}
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
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
        return commonProperties;
    }
    public void setCommonProperties(CommonDomainProperty commonProperties) {
        this.commonProperties = commonProperties;
    }
}
