package com.eryansky.service.sys;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eryansky.common.orm.hibernate.EntityManager;
import com.eryansky.common.orm.hibernate.HibernateDao;
import com.eryansky.entity.sys.Bug;
/**
 * bug管理Service层.
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date 2013-3-27 下午8:00:22 
 *
 */
@Service
public class BugManager extends EntityManager<Bug, Long> {

	private HibernateDao<Bug, Long> bugDao;
	
	
	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		bugDao = new HibernateDao<Bug, Long>(sessionFactory, Bug.class);
	}
	
	@Override
	protected HibernateDao<Bug, Long> getEntityDao() {
		return bugDao;
	}

}
