package domain;

import static org.junit.Assert.*;

import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	    	     List<Course> pres = CourseRepository.getInstance().findPrerequisitesForCourse(ap);
	    	     for (Course pre: pres) 
					ap.addPre(pre);
	    	     

	    	    dsOffering = OfferingRepository.getInstance().findById("ds1");

	    	    dmOffering = OfferingRepository.getInstance().findById("dm1");
	    	    math11 = OfferingRepository.getInstance().findById("math11");

	    	    math1 = CourseRepository.getInstance().findById("math1");
	    	   List<Course> mathPres = CourseRepository.getInstance().findPrerequisitesForCourse(math1);
	    	   for (Course pre : mathPres) 
	    		   math1.addPre(pre);
	    	   

	    	    ds = CourseRepository.getInstance().findById("ds");
	    	    List<Course> dsPres = CourseRepository.getInstance().findPrerequisitesForCourse(ds);
	    	    for (Course pre : dsPres) 
	    	    	ds.addPre(pre);

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

}
