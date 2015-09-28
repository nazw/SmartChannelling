package com.visni.smartchannelling.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.visni.smartchannelling.dao.SystemUserDAO;
import com.visni.smartchannelling.entity.SystemUser;
import com.visni.smartchannelling.entity.UserRole;



@Service(value = "customUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SystemUserDAO systemUserDAO;
   

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException, DataAccessException {
        // Declare a null Spring User
        UserDetails user = null;

        try {
            SystemUser systemUser = systemUserDAO.getSystemUserByUserName(userName);
            System.out.println("systemUser :" + systemUser);
            user = new User(systemUser.getUserName(), systemUser.getPassword(), true, true, true, true, getAuthorities(systemUser));
            //System.out.println("user :" + user);
        } catch (Exception e) {
        	 e.printStackTrace();
            throw new UsernameNotFoundException(userName);
           
        }
        return user;
    }

    /**
     * Retrieves the correct ROLE type depending on the access level, where access level is an Integer.
     * Basically, this interprets the access value whether it's for a regular user or admin.
     * 
     * @param access an integer value representing the access of the user
     * @return collection of granted authorities
     */
    public Collection<GrantedAuthority> getAuthorities(SystemUser systemUser) {
        // Create a list of grants for this user
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();


        Set<UserRole> useRoles = systemUser.getUserRoles();
        for (UserRole userRole : useRoles) {
            authList.add(new GrantedAuthorityImpl(userRole.getUserRoleType().toString()));
        }



        // Return list of granted authorities
        return authList;
    }
}
