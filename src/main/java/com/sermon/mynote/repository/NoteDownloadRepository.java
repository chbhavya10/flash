package com.sermon.mynote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sermon.mynote.domain.NoteDownload;

public interface NoteDownloadRepository extends PagingAndSortingRepository<NoteDownload, Integer> {

}