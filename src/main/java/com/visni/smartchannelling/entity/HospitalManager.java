package com.visni.smartchannelling.entity;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;

@Entity(name = "hospitalmanager")
@Table(name = "HOSPITAL_MANAGER")
public class HospitalManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hospitalManagerId;
	private SystemUser systemUser;
	private Hospital hospital;
	private ActiveStatus activeStatus;
	private int versionId;
    private CommonDomainProperty commanDomainProperty;
	

	@Id   
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.HospitalManagerIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "HOSPITAL_MANAGER_ID",length=12)
	public String getHospitalManagerId() {
		return hospitalManagerId;
	}

	public void setHospitalManagerId(String hospitalManagerId) {
		this.hospitalManagerId = hospitalManagerId;
	}
	
	@Enumerated(EnumType.STRING)
    @Column(name = "ACTIVE_STATUS")
	public ActiveStatus getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(ActiveStatus activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	@Cascade(CascadeType.ALL)
	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}
	
	@ManyToOne
	@JoinColumn(name = "HOSPITAL_ID")
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
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
    public CommonDomainProperty getCommonProperties() {
        return commanDomainProperty;
    }
    public void setCommonProperties(CommonDomainProperty commanDomainProperty) {
        this.commanDomainProperty = commanDomainProperty;
    }


	

}
