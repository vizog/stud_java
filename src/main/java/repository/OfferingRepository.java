package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.JDBCUtil;

import domain.Course;
import domain.Offering;
import domain.StudyRecord;
import domain.Term;


public class OfferingRepository {
	private static OfferingRepository theRepository = new OfferingRepository();

	public static OfferingRepository getInstance() {
		return theRepository;
	}

	public List<Offering> findOfferingsForTerm(Term term) {
		ArrayList<Offering> offerings = new ArrayList<Offering>();
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select offering.* from offering join term on term_name=name where term_name='" + term.getName() + "'");

			while (rs.next()) {
				Course crs = CourseRepository.getInstance().findById(rs.getString("course_id"));
				offerings.add(new Offering(rs.getString("id"), crs, term, rs.getDate("exam_date"), rs.getInt("section"), rs.getBoolean("locked")));
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offerings;

	}

	public Offering findById(String offeringId) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from offering where id='" + offeringId + "'");

			Offering offering = null;
			if (rs.next()) {
				Course crs = CourseRepository.getInstance().findById(rs.getString("course_id"));
				Term term = TermRepository.getInstance().findByName(rs.getString("term_name"));
				offering = new Offering(offeringId, crs, term, rs.getDate("exam_date"), rs.getInt("section"));
			}
			JDBCUtil.closeConnection(con);
			return offering;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Offering findByStudyRecord(StudyRecord sr) {
		try {
//			long start = System.currentTimeMillis();
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from offering o inner join study_record sr where o.id = sr.offering_id and sr.id = '" + sr.getId() + "'");
			Offering offering = null;
			if (rs.next()) {
				Course crs = CourseRepository.getInstance().findById(rs.getString("course_id"));
				Term term = TermRepository.getInstance().findByName(rs.getString("term_name"));
				offering = new Offering(rs.getString("offering_id"), crs, term, rs.getDate("exam_date"), rs.getInt("section"));
			}
			JDBCUtil.closeConnection(con);
//			Logger.getLogger(OfferingRepository.class).fatal("called! : " + (System.currentTimeMillis() - start));
			return offering;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public void save(Offering offering) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			if ((st.executeQuery("select * from offering where id='" + offering.getId() + "'")).next())
				st.executeUpdate("update offering set course_id='" + offering.getCourse().getId() + 
						"' ,term_name='" + offering.getTerm().getName() + "' ,locked= " + (offering.isLocked()?1:0) + " where id='" + offering.getId() + "'");
			else
				st.executeUpdate("insert into offering (id, course_id, term_name) values('" + 
						offering.getId() + "', '" + offering.getCourse().getId() + "', '" + offering.getTerm().getName() + "')");
			JDBCUtil.closeConnection(con);
					
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	


}
