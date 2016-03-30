package com.sermon.mynote.service;

import java.util.List;


import com.sermon.mynote.domain.VwUserprofile;

public interface VwUserprofileService {

	public List<VwUserprofile> findUserProfileByUserId(int userid);
}
