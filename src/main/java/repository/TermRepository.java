package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.JDBCUtil;

import domain.Term;
import domain.TermRegulation;


public class TermRepository {
	private static TermRepository theRepository = new TermRepository();

	public static TermRepository getInstance() {
		return theRepository;
	}

	public Term findCurrentTerm() {
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from term order by start_date desc");

			Term currentTerm = null;
			if (rs.next()) {
				String regId = rs.getString("regulation_id");
				currentTerm = new Term(rs.getString("name"), rs.getDate("start_date"));
			
				//fetch TermRegulation
				PreparedStatement st1 = con.prepareStatement("select * from term_regulation where id = ?");
				st1.setString(1, regId);
				st1.execute();
				rs = st1.getResultSet();
				TermRegulation termRegulation;
				if (rs.next()) {
					termRegulation = new TermRegulation(rs.getInt("max_allowed_units"),rs.getBoolean("retake_allowed"),rs.getBoolean("take_without_pres_allowed"));
					currentTerm.setTermRegulation(termRegulation);
				}
			
			
			return currentTerm;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				JDBCUtil.closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public Term findByName(String name) {
		try {
			Connection con = JDBCUtil.getConnection();
			PreparedStatement st = con.prepareStatement("select * from term where name = ?");
			st.setString(1, name);
			st.execute();
			ResultSet rs = st.getResultSet();
			Term term = null;
			if (rs.next()) {
				term = new Term(rs.getString("name"), rs.getDate("start_date"));
				String regId = rs.getString("regulation_id");
				
				PreparedStatement st1 = con.prepareStatement("select * from term_regulation where id = ?");
				st1.setString(1, regId);
				st1.execute();
				rs = st1.getResultSet();
				TermRegulation termRegulation;
				if (rs.next()) {
					termRegulation = new TermRegulation(rs.getInt("max_allowed_units"),rs.getBoolean("retake_allowed"),rs.getBoolean("take_without_pres_allowed"));
					term.setTermRegulation(termRegulation);
				}
	
				
			}
			JDBCUtil.closeConnection(con);
			return term;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
