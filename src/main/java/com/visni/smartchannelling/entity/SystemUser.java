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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.UserAccountStatus;

@Entity(name = "systemUser")
@Table(name = "SYSTEM_USER", uniqueConstraints =@UniqueConstraint(columnNames = "USER_NAME"))
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String userName;
    private String password;
    private Set<UserRole> userRoles;
    private Set<Appointment> patientsList;
    private int versionId;
    private UserAccountStatus accountStatus;
    private CommonDomainProperty commonDomainProperty;
    private SystemUserDetail systemUserDetail;
    private Address address;
    private Set<HospitalManager> hopspitalManagers;

    
    
	@Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.SystemUserIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "USER_ID", length = 12)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYSTEM_USER_ROLES", joinColumns =
    @JoinColumn(name = "USER_ID"), inverseJoinColumns =
    @JoinColumn(name = "USER_ROLE_ID"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    
    @OneToMany(mappedBy = "patient", fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    
	public Set<Appointment> getPatientsList() {
		return patientsList;
	}

	public void setPatientsList(Set<Appointment> patientsList) {
		this.patientsList = patientsList;
	}
    
    @OneToMany(mappedBy="systemUser" , fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<HospitalManager> getHopspitalManagers() {
		return hopspitalManagers;
	}

	public void setHopspitalManagers(Set<HospitalManager> hopspitalManagers) {
		this.hopspitalManagers = hopspitalManagers;
	}


    @Version
    @Column(name = "VERSION_ID")
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
    public UserAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(UserAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
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

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "SYSTEM_USER_DETAIL_ID")
    public SystemUserDetail getSystemUserDetail() {
        return systemUserDetail;
    }
    public void setSystemUserDetail(SystemUserDetail systemUserDetail) {
        this.systemUserDetail = systemUserDetail;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
   
    
}
