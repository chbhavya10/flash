package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.OrganizationGroup;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.repository.GroupRepository;
import com.sermon.mynote.repository.UserRepository;
import com.sermon.mynote.service.UserService;
import com.sermon.util.AppUtil;

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	/*
	 * @Autowired private EmailService emailService;
	 */

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return Lists.newArrayList(userRepository.findAll());
	}

	@Transactional(readOnly = true)
	public User findById(Integer id) {
		return userRepository.findOne(id.intValue());
	}

	public User save(User user) {

		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());

		String myDate = date + " " + time;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date utilDate = new java.util.Date();
		try {
			utilDate = sdf.parse(myDate);
		} catch (ParseException e) {
		}

		DateTime dateTime = new DateTime(utilDate);
		user.setCreateDt(dateTime);

		String encrptedPassword = AppUtil.sha256(user.getUserPassword());
		user.setUserPassword(encrptedPassword);
		user.setUserStatus("ACTIVE");

		return userRepository.save(user);
	}

	public boolean createUser(String username, String useremail, String userpassword, Timestamp currentDate) {
		/*
		 * List result = em .createNamedQuery("add_user")
		 * .setParameter("username", username) .setParameter("useremail",
		 * useremail) .setParameter("userpassword", userpassword)
		 * .getResultList();
		 */

		String password = AppUtil.sha256(userpassword);
		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.add_user");

		proc.setParameter("username", username).setParameter("useremail", useremail)
				.setParameter("userpassword", password).setParameter("createDt", currentDate);

		boolean result = proc.execute();
		return result;

	}

	public int updateUserPassword(Integer userId, String newPassword) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.update_userpassword");
		String password = AppUtil.sha256(newPassword);
		proc.setParameter("userId", userId).setParameter("userPwd", password);

		int result = proc.executeUpdate();
		return result;

	}

	@Transactional(readOnly = true)
	public Page<User> findAllByPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Integer validateUser(String username, String password) {
		String encryptPassword = AppUtil.sha256(password);
		try {
			Query query = em
					.createNativeQuery(
							"select userid returnvalue from user where username=:username and userpassword=:password")
					.setParameter("username", username).setParameter("password", encryptPassword);
			System.out.println(query);
			query.setParameter("username", username);

			if (query.getSingleResult() != null) {
				return (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return 0;

		/*
		 * Integer val = (Integer) query.getSingleResult(); if(val==null){
		 * return 0; } return val;
		 */
	}

	@Transactional(readOnly = true)
	public Integer checkUserNameAvailability(String username) {

		try {
			Query query = em.createNativeQuery("select userid returnvalue from user where username=:username")
					.setParameter("username", username);
			System.out.println(query);
			query.setParameter("username", username);

			if (query.getSingleResult() != null) {
				return (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return 0;

	}

	@Transactional(readOnly = true)
	public int checkUserEmailAvailability(String useremail) {

		try {
			Query query = em.createNativeQuery("select userid returnvalue from user where useremail=:useremail")
					.setParameter("useremail", useremail);
			System.out.println(query);
			query.setParameter("useremail", useremail);

			if (query.getSingleResult() != null) {
				return (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}
		return 0;

	}

	@Transactional(readOnly = true)
	public List<OrganizationGroup> getGroups() {
		return Lists.newArrayList(groupRepository.findAll());
	}

	@Override
	public void updateUserRole(int authorId, int roleId) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.update_user_role");
		proc.setParameter("roleId", roleId).setParameter("userId", authorId);

		proc.executeUpdate();

	}

	@Override
	public int forgotPassword(String userEmail) {

		// TODO Auto-generated method stub
		return 0;
	}

}
