package com.sermon.mynote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sermon.mynote.domain.UserFavorateEvents;

public interface EventFavoriteRepository extends PagingAndSortingRepository<UserFavorateEvents, Integer> {
/*
	@Query("DELETE FROM UserFavorateEvents WHERE userId = :userId AND eventId = :eventId")
	public Integer delete(@Param("userId") int userId, @Param("eventId") int eventId);*/
	
	UserFavorateEvents findByUserIdAndEventId(int userId, int eventId);

}
