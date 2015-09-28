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

import org.hibernate.annotations.GenericGenerator;

import com.visni.smartchannelling.util.ActiveStatus;

@Entity(name = "doctorHospitalRole")
@Table(name = "DOCTOR_HOSPITAL_ROLE")
public class DoctorHospitalRole implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String doctorHospitalRoleId;
    private HospitalRole hospitalRole;
    private Doctor doctor;
    private ActiveStatus activeStatus;
    private int versionId;
    private CommonDomainProperty commanDomainProperty;

    @Id
    @GenericGenerator(name = "seq_id", strategy = "com.visni.smartchannelling.idgenerator.DoctorHospitalRoleIdGenerator")
    @GeneratedValue(generator = "seq_id")
    @Column(name = "DOCTOR_HOSPITAL_ROLE_ID", length = 12)
    public String getDoctorHospitalRoleId() {
        return doctorHospitalRoleId;
    }

    public void setDoctorHospitalRoleId(String doctorHospitalRoleId) {
        this.doctorHospitalRoleId = doctorHospitalRoleId;
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
    @JoinColumn(name = "HOSPITAL_ROLE_ID")
    public HospitalRole getHospitalRole() {
        return hospitalRole;
    }

    public void setHospitalRole(HospitalRole hospitalRole) {
        this.hospitalRole = hospitalRole;
    }

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
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
    public CommonDomainProperty getCommonProperties() {
        return commanDomainProperty;
    }

    public void setCommonProperties(CommonDomainProperty commanDomainProperty) {
        this.commanDomainProperty = commanDomainProperty;
    }
}
