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

public class UserRoleIdGenerator implements IdentifierGenerator{
	
	private Log log = LogFactory.getLog(UserRoleIdGenerator.class);
	
	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		String prefix = "UR";
        Connection connection = session.connection();
        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT nextval ('user_role_user_role_id') as nextval");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("nextval");
                String code = prefix + StringUtils.leftPad("" + id,10, '0');
                log.debug("Generated UserRoleIdGenerator Id: " + code);
                return code;
            }
            
            ps.close();
            connection.close();

        } catch (SQLException e) {
            log.error(e);
            throw new HibernateException(
                    "Unable to generate UserRoleIdGenerator Id Sequence");
        }
        return null;
	}
}
