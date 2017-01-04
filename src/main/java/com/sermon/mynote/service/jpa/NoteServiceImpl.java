package com.sermon.mynote.service.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sermon.mynote.domain.AddNote;
import com.sermon.mynote.domain.AddSection;
import com.sermon.mynote.domain.AddSubSection;
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

		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */

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
	@Transactional(readOnly = false)
	public int createNote(AddNote addNote) {

		noteId = addNote.getNoteId();

		Note note = new Note();

		note.setAuthorId(addNote.getAuthorId());
		note.setCategoryId(addNote.getCategoryId());
		/*
		 * Date date = new Date(); note.setEventDate(new
		 * Timestamp(date.getTime())); note.setEventTime(new
		 * Timestamp(date.getTime()));
		 */
		note.setEventDate(addNote.getEventDate());
		note.setEventTime(addNote.getEventTime());
		note.setGroupId(addNote.getGroupId());
		note.setIntroduction(addNote.getIntroduction());
		List<String> keywords = addNote.getKeywords();
		String sectionKeyword = StringUtils.join(keywords, ',');
		note.setKeywords(sectionKeyword);
		note.setOrganizationId(addNote.getOrganizationId());
		note.setPublished(addNote.getPublished());
		note.setSubTitle(addNote.getSubTitle());
		note.setTitle((addNote.getTitle()).trim().replaceAll("\\s+", " "));
		note.setPreacherName(addNote.getPreacherName());

		Note newNote = noteRepository.save(note);

		List<AddSection> sections = addNote.getSections();

		if (sections != null && !sections.isEmpty()) {
			for (AddSection section : sections) {
				if (noteId == section.getNoteId()) {

					sectionId = section.getSectionId();

					Section noteSection = new Section();

					noteSection.setNoteId(newNote.getNoteId());
					List<Integer> sectionKeywords = section.getSectionKeyWords();
					String newSectionKeyword = StringUtils.join(sectionKeywords, ',');
					noteSection.setSectionKeyWords(newSectionKeyword);
					System.out.println(section.getSectionText());
					noteSection.setSectionText((section.getSectionText()).trim().replaceAll("\\s+", " "));
					System.out.println(noteSection.getSectionText());
					Section newSection = sectionRepository.save(noteSection);

					List<AddSubSection> subSections = addNote.getSubSections();

					if (subSections != null && !subSections.isEmpty()) {
						for (AddSubSection subSection : subSections) {
							if (sectionId == subSection.getSectionId()) {

								SubSection noteSubSection = new SubSection();

								noteSubSection.setSectionId(newSection.getSectionId());
								List<Integer> subSectionKeywords = subSection.getSubsectionKeyWords();
								String newSubSectionKeyword = StringUtils.join(subSectionKeywords, ',');
								noteSubSection.setSubsectionKeyWords(newSubSectionKeyword);
								noteSubSection.setSubsectionText(
										(subSection.getSubsectionText()).trim().replaceAll("\\s+", " "));

								subsectionRepository.save(noteSubSection);
							}
						}
					}
				}
			}
		}

		return newNote.getNoteId();
	}

	@Override
	public AddNote getNote(int id) {

		Note note = noteRepository.findOne(id);
		List<Section> sections = new ArrayList<Section>();
		List<SubSection> subSections = new ArrayList<SubSection>();
		List<SubSection> tempSubSections = new ArrayList<SubSection>();
		sections = sectionRepository.findSectionByNoteId(note.getNoteId());
		for (Section section : sections) {
			tempSubSections = subsectionRepository.findSubsectionBySectionId(section.getSectionId());
			subSections.addAll(tempSubSections);
		}

		AddNote addNote = new AddNote();
		addNote.setAuthorId(note.getAuthorId());
		addNote.setCategoryId(note.getCategoryId());
		addNote.setEventDate(note.getEventDate());
		addNote.setEventTime(note.getEventTime());
		addNote.setGroupId(note.getGroupId());
		addNote.setIntroduction(note.getIntroduction());
		List<String> keywords = new ArrayList<String>(Arrays.asList(note.getKeywords().split(",")));
		addNote.setKeywords(keywords);
		addNote.setNoteId(note.getNoteId());
		addNote.setOrganizationId(note.getOrganizationId());
		addNote.setPublished(note.getPublished());
		addNote.setSubTitle(note.getSubTitle());
		addNote.setTitle(note.getTitle());
		addNote.setPreacherName(note.getPreacherName());

		List<AddSection> addSections = new ArrayList<AddSection>();

		for (Section section : sections) {

			AddSection addSection = new AddSection();
			addSection.setNoteId(section.getNoteId());
			addSection.setSectionId(section.getSectionId());
			List<String> sectionKeyWords = new ArrayList<String>(
					Arrays.asList(section.getSectionKeyWords().split(",")));
			List<Integer> list = new ArrayList<Integer>();
			for (String s : sectionKeyWords) {
				try {
					list.add(Integer.valueOf(s));
				} catch (NumberFormatException e) {
					continue;
				}
			}
			addSection.setSectionKeyWords(list);
			addSection.setSectionText(section.getSectionText());
			addSections.add(addSection);
		}

		addNote.setSections(addSections);

		List<AddSubSection> addSubSections = new ArrayList<AddSubSection>();

		for (SubSection subSection : subSections) {

			AddSubSection addSubSection = new AddSubSection();
			addSubSection.setSectionId(subSection.getSectionId());
			addSubSection.setSubsectionId(subSection.getSubsectionId());
			List<String> subSectionKeyWords = new ArrayList<String>(
					Arrays.asList(subSection.getSubsectionKeyWords().split(",")));
			List<Integer> list = new ArrayList<Integer>();
			for (String s : subSectionKeyWords) {
				try {
					list.add(Integer.valueOf(s));
				} catch (NumberFormatException ex) {
					continue;
				}
			}
			addSubSection.setSubsectionKeyWords(list);
			addSubSection.setSubsectionText(subSection.getSubsectionText());
			addSubSections.add(addSubSection);
		}

		addNote.setSubSections(addSubSections);

		return addNote;
	}

	@Override
	public int updatePublish(PublishSchedule publishSchedule) {

		StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("PublishSchedule.update_publish_schedule");
		proc.setParameter("noteId", publishSchedule.getNoteId())
				.setParameter("publishDate", publishSchedule.getPublishDate())
				.setParameter("publishTime", publishSchedule.getPublishTime());

		int result = proc.executeUpdate();
		return result;
	}

}
