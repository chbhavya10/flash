package com.sermon.mynote.service;

import com.sermon.mynote.domain.NoteDownload;

public interface NoteDownloadService {

	NoteDownload save(NoteDownload noteDownload);

	int remove(NoteDownload noteDownload);

}