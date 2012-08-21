package domain;

import static org.junit.Assert.*;

import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.exceptions.EnrollmentRulesViolationException;
import domain.exceptions.StudentNotFoundException;
import domain.exceptions.UnknownOfferingsException;

import repository.CourseRepository;
import repository.OfferingRepository;
import repository.StudentRepository;
import repository.TermRepository;

public class TestDomain {

	private Course ap;
	private Offering dsOffering;
	private Offering dmOffering;
	private Offering math11;
	private Course math1;
	private Course ds;
	private Student bebe;
	private Term term88_89_2;
	private Term term88_89_1;

	@Before
	public void setUp() throws Exception {
		ap = CourseRepository.getInstance().findById("ap");
//		List<Course> pres = CourseRepository.getInstance()
//				.findPrerequisitesForCourse(ap);
//		for (Course pre : pres)
//			ap.addPre(pre);

		dsOffering = OfferingRepository.getInstance().findById("ds1");

		dmOffering = OfferingRepository.getInstance().findById("dm1");
		math11 = OfferingRepository.getInstance().findById("math11");

		math1 = CourseRepository.getInstance().findById("math1");
//		List<Course> mathPres = CourseRepository.getInstance()
//				.findPrerequisitesForCourse(math1);
//		for (Course pre : mathPres)
//			math1.addPre(pre);

		ds = CourseRepository.getInstance().findById("ds");
//		List<Course> dsPres = CourseRepository.getInstance()
//				.findPrerequisitesForCourse(ds);
//		for (Course pre : dsPres)
//			ds.addPre(pre);

		bebe = StudentRepository.getInstance().findByName("bebe");

		term88_89_1 = TermRepository.getInstance().findByName("88-89-1");
		term88_89_2 = TermRepository.getInstance().findByName("88-89-2");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStudentHasPassed1() {
		boolean result = bebe.hasPassed(ap);
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void testStudentHasPassed2() {
		boolean result = bebe.hasPassed(math1);
		Assert.assertEquals(result, false);
	}

	@Test
	public void testStudentHasPassedPres1() {
		boolean result = dsOffering.hasPassedPre(bebe);
		Assert.assertEquals(result, false);
	}
	
	@Test
	public void testStudentTakeCourse1() {
		EnrollCtrl enrollCtrl = new EnrollCtrl();
		try {
			enrollCtrl.enroll(bebe.getName(), dsOffering.getId());
			assertTrue(
					"bebe has not passed all prereqs but enrollment was successful",
					false);
		} catch (UnknownOfferingsException e) {
			assertTrue("UnknownOfferingsException", false);
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			assertTrue("StudentNotFoundException", false);
			e.printStackTrace();
		} catch (EnrollmentRulesViolationException e) {
			assertTrue(e.getMessage().startsWith("Has not passed pre"));
			e.printStackTrace();
		}

	}

	@Test
	public void testStudentTakeCourse2() {
		EnrollCtrl enrollCtrl = new EnrollCtrl();
		try {
			enrollCtrl.enroll(bebe.getName(), dmOffering.getId());
			assertTrue(
					"bebe has not passed all prereqs but enrollment was successful",
					false);
		} catch (UnknownOfferingsException e) {
			assertTrue("UnknownOfferingsException", false);
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			assertTrue("StudentNotFoundException", false);
			e.printStackTrace();
		} catch (EnrollmentRulesViolationException e) {
			assertTrue(e.getMessage().startsWith("Has not passed pre"));
			e.printStackTrace();
		}
	}

	@Test
	public void testStudentTakeCourse3() {
		EnrollCtrl enrollCtrl = new EnrollCtrl();
		try {
			enrollCtrl.enroll(bebe.getName(), math11.getId());
			assertTrue("bebe has not passed all prereqs but enrollment was successful",	false);
		} catch (UnknownOfferingsException e) {
			assertTrue("UnknownOfferingsException", false);
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			assertTrue("StudentNotFoundException", false);
			e.printStackTrace();
		} catch (EnrollmentRulesViolationException e) {
			assertTrue(e.getMessage().startsWith("Has already taken this course in this term"));
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGPA1() {
		double termGPA = bebe.getTermGPA(term88_89_1);
		assertEquals(12.8, termGPA, 0);
	}
	
	@Test
	public void testGPA2() {
		double termGPA = bebe.getTermGPA(term88_89_2);
		assertEquals(0, termGPA, 0);
	}

}
