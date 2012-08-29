package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import domain.Course;
import domain.Program;
import domain.Requirement;

public class ProgramRepository {
	private static ProgramRepository theRepository = new ProgramRepository();

	public static ProgramRepository getInstance() {
		return theRepository;
	}

	public Program findById(String id) throws SQLException {
		Program program = null;
		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from program where id='"
					+ id + "'");
			if (rs.next()) {
				program = new Program(id);
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return program;

	}

	public List<Requirement> findPrerequisitesForRequirement(Requirement req) {
		try {
			Connection con = JDBCUtil.getConnection();
			List<Requirement> prerequisites = new ArrayList<Requirement>();

			PreparedStatement st1 = con.prepareStatement("select pre_id from prerequisites where req_id=?;");
			st1.setInt(1, req.getId());
			st1.execute();
			ResultSet rs = st1.getResultSet();
			while (rs.next()) {
				prerequisites.add(findRequirement(rs.getInt("pre_id")));
			}
			JDBCUtil.closeConnection(con);
			return prerequisites;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Requirement findRequirement(int id) {
		Requirement req = null;
		try {

			Connection con = JDBCUtil.getConnection();
			PreparedStatement st1 = con
					.prepareStatement("select * from requirement where id=?");
			st1.setInt(1, id);
			st1.execute();
			ResultSet rs = st1.getResultSet();
			if (rs.next()) {
				Course c = CourseRepository.getInstance().findById(rs.getString("course_id"));
				req = new Requirement(id, null, c);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return req;

	}

	public List<Requirement> findRequirements(String programId) {

		List<Requirement> reqs = new ArrayList<Requirement>();
		try {
			Connection con = JDBCUtil.getConnection();
			PreparedStatement st1 = con
					.prepareStatement("select * from requirement where program_id=?");
			st1.setString(1, programId);
			st1.execute();
			ResultSet rs = st1.getResultSet();
			if (rs.next()) {
				int reqId = rs.getInt("id");
				Course c = CourseRepository.getInstance().findById(
						rs.getString("course_id"));
				reqs.add(new Requirement(reqId, null, c));
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqs;
	}

	public Requirement findRequirementForCourse(String programId, Course course) {
		Requirement req = null;
		try {
			Connection con = JDBCUtil.getConnection();
			PreparedStatement st1 = con.prepareStatement("select * from requirement where program_id=? and course_id=?");
			st1.setString(1, programId);
			st1.setString(2, course.getId());
			st1.execute();
			ResultSet rs = st1.getResultSet();
			if (rs.next()) {
				req = new Requirement(rs.getInt("id"), null, course);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return req;
	}
}
