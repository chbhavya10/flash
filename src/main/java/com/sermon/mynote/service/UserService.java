package com.sermon.mynote.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sermon.mynote.domain.OrgValidation;
import com.sermon.mynote.domain.OrganizationGroup;
import com.sermon.mynote.domain.OrganizationId;
import com.sermon.mynote.domain.User;
import com.sermon.mynote.domain.ValidateOrgKeyResponse;

public interface UserService {

	public List<User> findAll();

	public User findById(Integer id);

	public User save(User user);

	public boolean createUser(String username, String useremail, String userpassword, Timestamp currentDate);

	public int updateUserPassword(Integer userId, String newPassword, String oldPassword);

	public Page<User> findAllByPage(Pageable pageable);

	public Integer validateUser(String username, String password);

	public Integer checkUserNameAvailability(String username);

	public int checkUserEmailAvailability(String useremail);

	public List<OrganizationGroup> getGroups();

	public void updateUserRole(int authorId, int roleId);

	public int forgotPassword(String verificationToken, String password);

	public int updateUser(Integer userId, String userEmail, String userName, String userMobile);

	public int forgetPasswordLink(String userEmail);

	public ValidateOrgKeyResponse validateOrgKey(OrgValidation orgValidation);

	public OrganizationId getOrganizationId(int userId);

}
