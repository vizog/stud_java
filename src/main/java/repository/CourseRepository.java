package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;

import domain.Course;


public class CourseRepository {
	private static CourseRepository theRepository = new CourseRepository();
	
	public static CourseRepository getInstance() {
		return theRepository;
	}
	
	public Course findById(String id) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from course where id='" + id + "'");
		
		Course crs = null;
		if (rs.next())
			crs = new Course(rs.getString("id"), rs.getString("name"), rs.getInt("units"));
		
		JDBCUtil.closeConnection(con);
		return crs;

	}
	
	public void save(Course course) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			
			if ((st.executeQuery("select * from course where id='" + course.getId() + "'")).next())
				st.executeUpdate("update course set name='" + course.getName() + "', units=3" + 
						"where id='" + course.getId() + "'");
			else
				st.executeUpdate("insert into course values('" + course.getId() + "', '" + course.getName() + "', 3)");
			JDBCUtil.closeConnection(con);
					
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	


	public List<Course> findPrerequisitesForCourse(Course course) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from prerequisites where course_id='" + course.getId() + "'");

			
			List<Course> prerequisites = new ArrayList<Course>();
			while (rs.next()) {
				prerequisites.add(findById(rs.getString("pre_id")));
			}
			JDBCUtil.closeConnection(con);
			return prerequisites;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
