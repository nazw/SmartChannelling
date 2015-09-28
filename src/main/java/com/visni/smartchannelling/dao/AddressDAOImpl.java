package com.visni.smartchannelling.dao;


import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visni.smartchannelling.entity.Address;

@Repository("addressDAO")
@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
public class AddressDAOImpl implements AddressDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly = true)
	public Address getAddressById(String id) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Address.class, "ad");
		criteria.setProjection(Projections.projectionList()
				.add(Property.forName("ad.addressId").as("addressId"))
				.add(Property.forName("ad.addressValue").as("addressValue"))
				.add(Property.forName("ad.streetNumber").as("streetNumber"))
				.add(Property.forName("ad.streetName").as("streetName"))
				.add(Property.forName("ad.city").as("city"))
				.add(Property.forName("ad.state").as("state"))
				.add(Property.forName("ad.country").as("country"))
				.add(Property.forName("ad.postalCode").as("postalCode"))
				.add(Property.forName("ad.zipCode").as("zipCode"))
				.add(Property.forName("ad.googleMapPoint").as("googleMapPoint"))
				.add(Property.forName("ad.versionId").as("versionId"))
				);
		criteria.add(Restrictions.eq("ad.addressId", id));
		Address address = (Address) criteria.setResultTransformer(new AliasToBeanResultTransformer(Address.class)).uniqueResult();
		return address;
	}

}
