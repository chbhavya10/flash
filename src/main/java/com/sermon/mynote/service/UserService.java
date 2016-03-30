package com.sermon.mynote.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sermon.mynote.domain.OrganizationGroup;
import com.sermon.mynote.domain.User;

public interface UserService {

	public List<User> findAll();

	public User findById(Integer id);

	public User save(User user);

	public boolean createUser(String username, String useremail,
			String userpassword, String userStatus, Timestamp currentDate);

	public int updateUserPassword(Integer userId, String password);

	public Page<User> findAllByPage(Pageable pageable);

	public Integer validateUser(String username, String password);

	public Integer checkUserNameAvailability(String username);

	public int checkUserEmailAvailability(String useremail);

	public List<OrganizationGroup> getGroups();

	public void updateUserRole(int authorId, int roleId);

}
