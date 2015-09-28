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
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.UserRoleType;

@Entity(name = "userRole")
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userRoleId;
    private UserRoleType userRoleType;
	private int versionId;
	private CommonDomainProperty commanDomainProperty;
	

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.UserRoleIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "USER_ROLE_ID", length = 12)
    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE_TYPE")
    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
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
