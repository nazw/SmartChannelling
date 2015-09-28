package com.visni.smartchannelling.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SpecializationIdGenerator implements IdentifierGenerator{

	private Log log = LogFactory.getLog(SpecializationIdGenerator.class);
	
	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		
		String prefix = "SP";
        Connection connection = session.connection();
        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT nextval ('specialization_specialization_id') as nextval");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("nextval");
                String code = prefix + StringUtils.leftPad("" + id,10, '0');
                log.debug("Generated SpecializationIdGenerator Id: " + code);
                return code;
            }
            ps.close();
            connection.close();

        } catch (SQLException e) {
            log.error(e);
            throw new HibernateException(
                    "Unable to generate SpecializationIdGenerator Id Sequence");
        }
        return null;
	}

}
