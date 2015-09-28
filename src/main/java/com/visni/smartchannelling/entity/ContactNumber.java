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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.NotifyToContactStatus;
import com.visni.smartchannelling.util.PriorityStatus;

/**
 * @author nadeeshani
 *
 */
@Entity(name="contactNumber")
@Table(name="CONTACT_NUMBER", uniqueConstraints=@UniqueConstraint(columnNames={"CONTACT_NUMBER_VALUE"}))
public class ContactNumber implements Serializable{

	private static final long serialVersionUID = 1L;

	private String contactNumberId;
	private String contactNumberValue;
	private SystemUserDetail systemUserDetail;
	private Hospital hospital;
	private PriorityStatus contactNumberPriorityStatus;
	private NotifyToContactStatus notifyToContactStatus;
	private int versionId;
	private CommonDomainProperty commanDomainProperty;
	
	@Id   
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.ContactNumberIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "CONTACT_NUMBER_ID",length=12)
	public String getContactNumberId() {
		return contactNumberId;
	}
	public void setContactNumberId(String contactNumberId) {
		this.contactNumberId = contactNumberId;
	}
	
	@Column(name = "CONTACT_NUMBER_VALUE")
	public String getContactNumberValue() {
		return contactNumberValue;
	}
	public void setContactNumberValue(String contactNumberValue) {
		this.contactNumberValue = contactNumberValue;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name = "CONTACT_NUMBER_PRIORITY_STATUS")
	public PriorityStatus getContactNumberPriorityStatus() {
		return contactNumberPriorityStatus;
	}
	public void setContactNumberPriorityStatus(
			PriorityStatus contactNumberPriorityStatus) {
		this.contactNumberPriorityStatus = contactNumberPriorityStatus;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITAL_ID")
        @Cascade(CascadeType.ALL)
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
