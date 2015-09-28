package com.visni.smartchannelling.entity;

import java.io.Serializable;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;


/**
 * @author nadeeshani
 *
 */
@Entity(name="emailAddress")
@Table(name="EMAIL_ADDRESS", uniqueConstraints=@UniqueConstraint(columnNames={"EMAIL_ADDRESS_VALUE"}))
public class EmailAddress implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String emailAddressId;
	private String emailAddressValue;
	private SystemUserDetail systemUserDetail;
	private PriorityStatus emailPriorityStatus;
	private NotifyToContactStatus notifyToContactStatus;
	private Hospital hospital;
	private int versionId;
	private CommonDomainProperty commanDomainProperty;
	    
    
	@Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.EmailAddressIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "EMAIL_ADDRESS_ID",length=12)
	public String getEmailAddressId() {
		return emailAddressId;
	}
	public void setEmailAddressId(String emailAddressId) {
		this.emailAddressId = emailAddressId;
	}
	
	
	@Column(name = "EMAIL_ADDRESS_VALUE")
	@Pattern(regexp = ".+@.+\\.[a-z]+")
	public String getEmailAddressValue() {
		return emailAddressValue;
	}
	public void setEmailAddressValue(String emailAddressValue) {
		this.emailAddressValue = emailAddressValue;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSTEM_USER_DETAIL_ID")
	@Cascade(CascadeType.MERGE)
	public SystemUserDetail getUserDetail() {
		return systemUserDetail;
	}
	public void setUserDetail(SystemUserDetail systemUserDetail) {
		this.systemUserDetail = systemUserDetail;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name = "EMAIL_PRIORITY_STATUS")
	public PriorityStatus getEmailPriorityStatus() {
		return emailPriorityStatus;
	}
	public void setEmailPriorityStatus(PriorityStatus emailPriorityStatus) {
		this.emailPriorityStatus = emailPriorityStatus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITAL_ID")
        @Cascade(org.hibernate.annotations.CascadeType.MERGE)
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name = "NOTIFY_TO_CONTACT_STATUS")
	public NotifyToContactStatus getNotifyToContactStatus() {
		return notifyToContactStatus;
	}
	public void setNotifyToContactStatus(NotifyToContactStatus notifyToContactStatus) {
		this.notifyToContactStatus = notifyToContactStatus;
	}
	
	
	@Version
	@Column(name="VERSION_ID")
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
