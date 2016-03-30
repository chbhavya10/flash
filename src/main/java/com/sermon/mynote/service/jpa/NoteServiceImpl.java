package com.sermon.mynote.service.jpa;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.Note;
import com.sermon.mynote.domain.PublishSchedule;
import com.sermon.mynote.domain.Section;
import com.sermon.mynote.domain.SubSection;
import com.sermon.mynote.repository.NoteRepository;
import com.sermon.mynote.repository.PublishRepository;
import com.sermon.mynote.repository.SectionRepository;
import com.sermon.mynote.repository.SubSectionRepository;
import com.sermon.mynote.service.NoteService;

@Service("noteService")
@Repository
@Transactional
public class NoteServiceImpl implements NoteService {

	final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private PublishRepository publishRepository;

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private SubSectionRepository subsectionRepository;

	int noteId;
	int sectionId;
	int subSectionId;

	public Note findById(Integer id) {
		return noteRepository.findOne(id.intValue());
	}

	public Note save(Note note) {

		Date date = new Date();
		note.setEventDate(new Timestamp(date.getTime()));
		note.setEventTime(new Timestamp(date.getTime()));
		return noteRepository.save(note);
	}

	/*
	 * 
	 * public void createNote(Integer authorId,String title,String
	 * subTitle,String introduction, Date eventDate,Time eventTime,Integer
	 * noteCategoryId,String keywords,Integer organizationId);
	 */
	@Override
	public int delete(Note note) {
		/*
		 * Note mergedNote = em.merge(note); em.remove(mergedNote); logger.info(
		 * "Note with id: " + note.getNoteId()+ " deleted successfully");
		 */

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Note.delete_note");

		proc.setParameter("NoteId", note.getNoteId());
		int result = proc.executeUpdate();

		return result;
	}

	@Transactional(readOnly = true)
	public List<Note> findNotesByAuthorId(int authorId) {

		return Lists.newArrayList(noteRepository.findNotesByAuthorId(authorId));
	}

	@Override
	public PublishSchedule publishLater(PublishSchedule publishSchedule) {
		return publishRepository.save(publishSchedule);
	}

	@Override
	public int updatePublishNow(Integer noteId, String published) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("Note.update_publish_now");
		proc.setParameter("noteId", noteId).setParameter("published", published);

		int result = proc.executeUpdate();
		return result;

	}

	@Override
	public int createNote(AddNote addNote) {

		noteId = addNote.getNoteId();

		Note note = new Note();

		note.setAuthorId(addNote.getAuthorId());
		note.setCategoryId(addNote.getCategoryId());
		Date date = new Date();
		note.setEventDate(new Timestamp(date.getTime()));
		note.setEventTime(new Timestamp(date.getTime()));
		note.setGroupId(addNote.getGroupId());
		note.setIntroduction(addNote.getIntroduction());
		note.setKeywords(addNote.getKeywords());
		note.setOrganizationId(addNote.getOrganizationId());
		note.setPublished(addNote.getPublished());
		note.setSubTitle(addNote.getSubTitle());
		note.setTitle(addNote.getTitle());

		Note newNote = noteRepository.save(note);

		List<Section> sections = addNote.getSections();

		if (!sections.isEmpty()) {
			for (Section section : sections) {
				if (noteId == section.getNoteId()) {

					sectionId = section.getSectionId();

					Section noteSection = new Section();

					noteSection.setNoteId(newNote.getNoteId());
					noteSection.setSectionKeyWords(section.getSectionKeyWords());
					noteSection.setSectionText(section.getSectionText());

					Section newSection = sectionRepository.save(noteSection);

					List<SubSection> subSections = addNote.getSubSections();

					if (!subSections.isEmpty()) {
						for (SubSection subSection : subSections) {
							if (sectionId == subSection.getSectionId()) {

								SubSection noteSubSection = new SubSection();

								noteSubSection.setSectionId(newSection.getSectionId());
								noteSubSection.setSubsectionKeyWords(subSection.getSubsectionKeyWords());
								noteSubSection.setSubsectionText(subSection.getSubsectionText());

								subsectionRepository.save(noteSubSection);
							}
						}
					}
				}
			}
		}

		return newNote.getNoteId();
	}

}
