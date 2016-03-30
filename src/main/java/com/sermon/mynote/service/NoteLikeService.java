package com.sermon.mynote.service;

import com.sermon.mynote.domain.NoteLike;

public interface NoteLikeService {

	NoteLike save(NoteLike noteLike);
	
	public int findById(int noteId,int userId);

	int updateLike(int noteId, int userId, int likeCount);

}
