package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;

import domain.Offering;
import domain.Student;
import domain.StudyRecord;
import domain.exceptions.StudentNotFoundException;


public class StudentRepository {
	private static StudentRepository theRepository = new StudentRepository();
	
	public static StudentRepository getInstance() {
		return theRepository;
	}
	
	public Student findByName(String name) throws StudentNotFoundException {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from student where name='" + name + "'");

			Student student = null;
			if (rs.next()) {
				student = new Student(rs.getString("id"), rs.getString("name"));
				student.setProgram(ProgramRepository.getInstance().findById(rs.getString("program_id")));
			}
			else
				throw new StudentNotFoundException();
			JDBCUtil.closeConnection(con);
			return student;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<StudyRecord> findStudyRecordsForStudent(Student student) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from study_record where student_id='" + student.getId() + "'");

			
			List<StudyRecord> studyRecords = new ArrayList<StudyRecord>();
			while (rs.next()) {
				Offering offering = OfferingRepository.getInstance().findById(rs.getString("offering_id"));
				studyRecords.add(new StudyRecord(offering, rs.getDouble("grade")));
			}
			JDBCUtil.closeConnection(con);
			return studyRecords;
		} catch (SQLException ex) {
			return null;
		}
	}

	public void save(Student student) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			if ((st.executeQuery("select * from student where id='" + student.getId() + "'")).next())
				st.executeUpdate("update student set name='" + student.getName() + 
						"' where id='" + student.getId() + "'");
			else
				st.executeUpdate("insert into student values('" + student.getId() + "', '" + student.getName() + "')"); 
					
			
			for (StudyRecord sr : student.getStudyRecords()) {
				if ((st.executeQuery("select * from study_record where student_id='" + student.getId() + 
						"' and offering_id='" + sr.getOffering().getId() + "'")).next())
					st.executeUpdate("update study_record set grade=" + sr.getGrade() + 
							"where student_id='" + student.getId() + 
							"' and offering_id='" + sr.getOffering().getId() + "'");
				else {
					st.executeUpdate("insert into study_record values('"+ 
							student.getId() + "','" +
							sr.getOffering().getId() + "'," +
							sr.getGrade() + ")");
				}
					
			}
			JDBCUtil.closeConnection(con);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteAll() {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			st.executeUpdate("delete from study_record");
			st.executeUpdate("delete from student");
			JDBCUtil.closeConnection(con);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
