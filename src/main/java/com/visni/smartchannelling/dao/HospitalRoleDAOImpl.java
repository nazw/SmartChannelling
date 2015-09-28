/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visni.smartchannelling.dao;

import com.visni.smartchannelling.entity.HospitalRole;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.transform.Transformers;
import org.hibernate.Query;

/**
 *
 * @author visni
 */
@Repository("HospitalRoleDAO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HospitalRoleDAOImpl implements HospitalRoleDAO {
    
        @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public HospitalRole getHospitalRoleInfoById(String hospitalRoleId)throws Exception {

        String getHospitalRoleByIdQuery = " SELECT hsp.hospital_role_id,hsp.name"
                + " FROM hospital_role hsp"
                + " WHERE hsp.hospital_role_id='" + hospitalRoleId + "'";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(getHospitalRoleByIdQuery);

        Map mapOfGetHospitalById = (Map) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();

        HospitalRole hospitalRole = new HospitalRole();
        hospitalRole.setHospitalRoleId((String) mapOfGetHospitalById.get("hospital_role_id"));
        hospitalRole.setName((String)mapOfGetHospitalById.get("name"));
   
        return hospitalRole;

    }
    
}
