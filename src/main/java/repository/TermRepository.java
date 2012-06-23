package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.JDBCUtil;

import domain.Term;


public class TermRepository {
	private static TermRepository theRepository = new TermRepository();

	public static TermRepository getInstance() {
		return theRepository;
	}

	public Term findCurrentTerm() {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from term order by start_date desc");

			Term currentTerm = null;
			if (rs.next())
				currentTerm = new Term(rs.getString("name"), rs.getDate("start_date"));
			JDBCUtil.closeConnection(con);
			return currentTerm;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Term findByName(String name) {
		try {
			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from term");

			Term currentTerm = null;
			if (rs.next())
				currentTerm = new Term(rs.getString("name"), rs.getDate("start_date"));
			JDBCUtil.closeConnection(con);
			return currentTerm;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
