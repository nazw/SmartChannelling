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
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.visni.smartchannelling.util.AccountStatus;

/**
 * @author nadeeshani
 *
 */
@Entity(name = "systemUserDetail")
@Table(name = "SYSTEM_USER_DETAIL")
public class SystemUserDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String systemUserDetailId;
    private String firstName;
    private String lastName;
    private String title;
    private String middleName;
    private String gender;
    private String profileImage;
    private List<EmailAddress> emailAddresses;
    private List<ContactNumber> contactNumbers;
    private SystemUser systemUser;
    private Doctor doctor;
    private AccountStatus accountStatus;
	private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.SystemUserDetailIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "SYSTEM_USER_DETAIL_ID", length = 12)
    public String getSystemUserDetailId() {
		return systemUserDetailId;
	}

	public void setSystemUserDetailId(String systemUserDetailId) {
		this.systemUserDetailId = systemUserDetailId;
	}


    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name = "TITLE")
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name = "PROFILE_IMAGE")
	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}


	@Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS")
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
    
  
    @OneToMany(mappedBy = "userDetail")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }
    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    @OneToMany(mappedBy = "userDetail")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    public List<ContactNumber> getContactNumbers() {
        return contactNumbers;
    }
    public void setContactNumbers(List<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYSTEM_USER_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public SystemUser getSystemUser() {
        return systemUser;
    }
    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
    

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "systemUserDetail")
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
    public CommonDomainProperty getCommanDomainProperty() {
        return commanDomainProperty;
    }

    public void setCommanDomainProperty(CommonDomainProperty commanDomainProperty) {
        this.commanDomainProperty = commanDomainProperty;
    }
}
