package com.sermon.mynote.service.jpa;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.sermon.mynote.components.EmailService;
import com.sermon.mynote.domain.OrganizationGroup;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.UserVerificationTokens;
import com.sermon.mynote.repository.GroupRepository;
import com.sermon.mynote.repository.UserRepository;
import com.sermon.mynote.repository.UserVerificationTokenRepository;
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

	@Autowired
	private UserVerificationTokenRepository tokenRepository;

	@Autowired
	private EmailService emailService;

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

	public int updateUserPassword(Integer userId, String newPassword, String oldPassword) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.update_userpassword");
		String password = AppUtil.sha256(newPassword);
		String oldPwd = AppUtil.sha256(oldPassword);
		proc.setParameter("userId", userId).setParameter("userPwd", password).setParameter("userOldPwd", oldPwd);

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
	public int forgotPassword(String verificationToken, String password) {

		int userId = 0;

		try {
			Query query = em
					.createNativeQuery(
							"select userid returnvalue from UserVerificationTokens where verificationtoken=:verificationtoken")
					.setParameter("verificationtoken", verificationToken);
			System.out.println(query);

			if (query.getSingleResult() != null) {
				userId = (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		if (userId > 0) {

			User newUser = findById(userId);

			StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.update_userpassword");
			String newPassword = AppUtil.sha256(password);
			proc.setParameter("userId", userId).setParameter("userPwd", newPassword).setParameter("userOldPwd",
					newUser.getUserPassword());

			int result = proc.executeUpdate();

			if (result == 0) {

				StoredProcedureQuery proce = em
						.createNamedStoredProcedureQuery("UserVerificationTokens.update_tokenstatus");
				proce.setParameter("userid", userId).setParameter("verificationtoken", verificationToken);

				proce.executeUpdate();

				StoredProcedureQuery proced = em.createNamedStoredProcedureQuery("UserVerificationTokens.delete_token");
				proced.setParameter("userid", userId).setParameter("verificationtoken", verificationToken);

				proced.executeUpdate();

			}
			return result;
		} else {
			return -1;
		}

	}

	@Override
	public int updateUser(Integer userId, String userEmail, String userName, String userMobile) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("User.update_user");
		proc.setParameter("userId", userId).setParameter("userName", userName).setParameter("userEmail", userEmail)
				.setParameter("userMobile", userMobile);

		int result = proc.executeUpdate();
		return result;

	}

	@Override
	public int forgetPasswordLink(String userEmail) {

		int userId = 0;
		String userName = null;
		try {
			Query query = em.createNativeQuery("select userid returnvalue from user where useremail=:useremail")
					.setParameter("useremail", userEmail);
			System.out.println(query);

			if (query.getSingleResult() != null) {
				userId = (Integer) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		UUID uuId = UUID.randomUUID();
		UserVerificationTokens tokens = new UserVerificationTokens();
		tokens.setVerificationToken(uuId.toString());
		tokens.setUserId(userId);

		tokenRepository.save(tokens);

		try {
			Query query = em.createNativeQuery("select username returnvalue from user where userid=:userid")
					.setParameter("userid", userId);
			System.out.println(query);

			if (query.getSingleResult() != null) {
				userName = (String) query.getSingleResult();
			}
		} catch (NoResultException e) {

		}

		String resetPasswordUrl = "file:///D:/SermonNote/resetpassword.html#/?passwordResetToken="
				+ tokens.getVerificationToken();

		emailService.forgotPassword(userEmail, resetPasswordUrl, userName);

		return 0;
	}

}
