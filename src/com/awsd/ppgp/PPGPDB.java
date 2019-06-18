package com.awsd.ppgp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.dao.DAOUtils;
import com.esdnl.util.StringUtils;

public class PPGPDB {

	public static PPGP addPPGP(PPGP ppgp) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();

			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.add_ppgp(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, ppgp.getPersonnel().getPersonnelID());
			stat.setString(3, ppgp.getSchoolYear());
			stat.execute();

			ppgp.setPPGPID(((OracleCallableStatement) stat).getInt(1));
		}
		catch (PersonnelException e) {
			throw new PPGPException("Could not find personnel record");
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.addPPGP(): " + e);
			throw new PPGPException("Could not added PPGP to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (ppgp);
	}

	/*
	 * Only returns the pgp in the database, does not include the goals/tasks
	 * associated with the pgps
	 * 
	 * TODO: Convert to PL/SQL and Return full pgp tree structure
	 */
	public static Vector<PPGP> getPPGP(Personnel p) throws PPGPException {

		Vector<PPGP> ppgps = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			ppgps = new Vector<PPGP>();

			sql = "SELECT * FROM PPGP WHERE PERSONNEL_ID=" + p.getPersonnelID() + " ORDER BY SCHOOL_YEAR DESC";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				ppgps.add(new PPGP(rs.getInt("PPGP_ID"), p, rs.getString("SCHOOL_YEAR")));
			}
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.getPPGP(Personnel): " + e);
			throw new PPGPException("Can not extract PPGP from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return ppgps;
	}

	public static PPGP getPPGP(Personnel p, String school_year) throws PPGPException {

		PPGP ppgp = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_ppgp(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, school_year);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				ppgp = createPPGP(rs);

		}
		catch (SQLException e) {
			System.err.println("PPGP getPPGP(Personnel p, String school_year): " + e);
			throw new PPGPException("Can not extract PPGP from DB.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return ppgp;

	}

	public static PPGP getPPGP(Personnel p, int ppgp_id) throws PPGPException {

		PPGP ppgp = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_ppgp(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setInt(3, ppgp_id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				ppgp = createPPGP(rs);

		}
		catch (SQLException e) {
			System.err.println("PPGP getPPGP(Personnel p, int ppgp_id): " + e);
			throw new PPGPException("Can not extract PPGP from DB.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return ppgp;

	}

	public static PPGP getPPGP(int ppgp_id) throws PPGPException {

		PPGP ppgp = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_ppgp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, ppgp_id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				ppgp = createPPGP(rs);

		}
		catch (SQLException e) {
			System.err.println("PPGP getPPGP(int ppgp_id): " + e);
			throw new PPGPException("Can not extract PPGP from DB.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return ppgp;

	}

	public static HashMap<String, PPGP> getPPGPMap(Personnel p) throws PPGPException {

		PPGP ppgp = null;
		HashMap<String, PPGP> ppgps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			ppgps = new HashMap<String, PPGP>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_all_ppgp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					ppgp = createPPGP(rs);

					ppgps.put(ppgp.getSchoolYear(), ppgp);
				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("PPGP getPPGPMap(Personnel p): " + e);
			throw new PPGPException("Can not extract PPGP from DB.");
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return ppgps;

	}

	public static boolean deletePPGP(int id) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;
		int check;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.pgp_sys.del_ppgp(?); end;");
			stat.setInt(1, id);
			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.deletePPGP(): " + e);
			throw new PPGPException("Could not delete PPGP to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	/*
	 * TODO: covert to PL/SQL
	 */
	public static String[] getPGPYears() throws PPGPException {

		Vector<String> ppgps = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			ppgps = new Vector<String>();

			sql = "select distinct school_year from awsd_user.ppgp order by school_year desc";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				ppgps.add(rs.getString("school_year"));
			}
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.getPGPYears(): " + e);
			throw new PPGPException("Can not extract PPGP years from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (String[]) ppgps.toArray(new String[0]);
	}

	public static PPGPGoal addPPGPGoal(PPGP ppgp, PPGPGoal goal) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.add_goal(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, goal.getPPGPGoalDescription());
			stat.setInt(3, ppgp.getPPGPID());
			stat.execute();

			goal.setPPGPGoalID(((OracleCallableStatement) stat).getInt(1));
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.addPPGPGoal(): " + e);
			throw new PPGPException("Could not added PPGP to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (goal);
	}

	public static PPGPGoal getPPGPGoal(int gid) throws PPGPException {

		PPGPGoal g = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_goal(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, gid);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				g = createPPGPGoal(rs);
				do {
					if (rs.getInt("PPGPTASK_ID") > 0)
						g.put(rs.getInt("PPGPTASK_ID"), createPPGPTask(rs));
				} while (rs.next());
			}
			else {
				throw new PPGPException("NO GOAL FOUND");
			}
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.getPPGPGoal(): " + e);
			throw new PPGPException("Can not extract goal from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return g;
	}

	public static PPGPGoal getPPGPGoalByTask(int tid) throws PPGPException {

		PPGPGoal g = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_goal_by_task(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, tid);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				g = createPPGPGoal(rs);
				g.put(rs.getInt("PPGPTASK_ID"), createPPGPTask(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.getPPGPGoalByTask(int tid): " + e);
			throw new PPGPException("Can not extract goal from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return g;
	}

	public static boolean deletePPGPGoal(int gid) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;
		int check;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.pgp_sys.del_goal(?); end;");
			stat.setInt(1, gid);
			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.deletePPGPGoal(): " + e);
			throw new PPGPException("Could not delete goal to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static boolean modifyPPGPGoal(PPGPGoal g) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;
		int check;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.pgp_sys.mod_goal(?,?); end;");
			stat.setInt(1, g.getPPGPGoalID());
			stat.setString(2, g.getPPGPGoalDescription());
			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.modifyPPGPGoal(): " + e);
			throw new PPGPException("Can not extract goal from DB: " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static boolean addPPGPGoalTask(PPGPGoal goal, PPGPTask task) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;
		int check = 1;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.pgp_sys.addPPGPGoalTask(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, task.getSchoolSupport());
			stat.setString(2, task.getDistrictSupport());
			stat.setString(3, task.getCompletionDate());
			stat.setString(4, task.getSelfEvaluation());
			stat.setInt(5, goal.getPPGPGoalID());
			stat.setString(6, task.getDescription());

			if (task.getCategory() != null)
				stat.setInt(7, task.getCategory().getCategoryID());
			else
				stat.setNull(7, OracleTypes.NUMBER);

			if (task.getGrade() != null)
				stat.setInt(8, task.getGrade().getGradeID());
			else
				stat.setNull(8, OracleTypes.NUMBER);

			if (task.getSubject() != null)
				stat.setInt(9, task.getSubject().getSubjectID());
			else
				stat.setNull(9, OracleTypes.NUMBER);

			if (task.getTopic() != null)
				stat.setInt(10, task.getTopic().getTopicID());
			else
				stat.setNull(10, OracleTypes.NUMBER);

			if (task.getSpecificTopic() != null)
				stat.setInt(11, task.getSpecificTopic().getSpecificTopicID());
			else
				stat.setNull(11, OracleTypes.NUMBER);

			stat.setString(12, task.getTechnologySupport());
			stat.setString(13, task.getTechnologySchoolSupport());
			stat.setString(14, task.getTechnologyDistrictSupport());
			if (task.getDomain() != null)
				stat.setInt(15, task.getDomain().getDomainID());
			else
				stat.setNull(15, OracleTypes.NUMBER);
			
			if (task.getStrength() != null)
				stat.setInt(16, task.getStrength().getStrengthID());
			else
				stat.setNull(16, OracleTypes.NUMBER);

			stat.execute();
		}
		catch (SQLException e) {
			check = 0;
			System.err.println("PPGPDB.addPPGPGoalTask(): " + e);
			throw new PPGPException("Could not added PPGP to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return (check == 1);
	}

	public static boolean deletePPGPGoalTask(int tid) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;
		int check;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.pgp_sys.del_task(?); end;");
			stat.setInt(1, tid);
			check = stat.executeUpdate();
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.deletePPGPGoalTask(int tid): " + e);
			throw new PPGPException("Could not delete task to DB");
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return (check == 1);
	}

	public static PPGPTask getPPGPGoalTask(int tid) throws PPGPException {

		PPGPTask task = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.pgp_sys.get_task(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, tid);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				task = createPPGPTask(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("PPGPTask PPGPDB.getPPGPGoalTask(int tid)): " + e);
			throw new PPGPException("Can not extract tasks from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return task;
	}

	public static void modifyPPGPGoalTask(PPGPTask task) throws PPGPException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin awsd_user.pgp_sys.modPPGPGoalTask(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, task.getTaskID());
			stat.setString(2, task.getSchoolSupport());
			stat.setString(3, task.getDistrictSupport());
			stat.setString(4, task.getCompletionDate());
			stat.setString(5, task.getSelfEvaluation());
			stat.setString(6, task.getDescription());

			if (task.getCategory() != null)
				stat.setInt(7, task.getCategory().getCategoryID());
			else
				stat.setNull(7, OracleTypes.NUMBER);

			if (task.getGrade() != null)
				stat.setInt(8, task.getGrade().getGradeID());
			else
				stat.setNull(8, OracleTypes.NUMBER);

			if (task.getSubject() != null)
				stat.setInt(9, task.getSubject().getSubjectID());
			else
				stat.setNull(9, OracleTypes.NUMBER);

			if (task.getTopic() != null)
				stat.setInt(10, task.getTopic().getTopicID());
			else
				stat.setNull(10, OracleTypes.NUMBER);

			if (task.getSpecificTopic() != null)
				stat.setInt(11, task.getSpecificTopic().getSpecificTopicID());
			else
				stat.setNull(11, OracleTypes.NUMBER);

			stat.setString(12, task.getTechnologySupport());
			stat.setString(13, task.getTechnologySchoolSupport());
			stat.setString(14, task.getTechnologyDistrictSupport());
			if (task.getDomain() != null)
				stat.setInt(15, task.getDomain().getDomainID());
			else
				stat.setNull(15, OracleTypes.NUMBER);
			
			if (task.getStrength() != null)
				stat.setInt(16, task.getStrength().getStrengthID());
			else
				stat.setNull(16, OracleTypes.NUMBER);

			stat.execute();

		}
		catch (SQLException e) {
			System.err.println("PPGPDB.modifyPPGPGoalTask(): " + e);
			throw new PPGPException("Can not extract tasks from DB: " + e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

	}

	public static SearchResults search(String sy, String region, String[] keywords, int cat_id, int grade_id,
																			int subject_id, int topic_id, int stopic_id,boolean useNewCriteria
																			,int domain_id,int strength_id) throws PPGPException {

		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		ArrayList<Vector<SearchResult>> pages = null;
		Vector<SearchResult> page = null;
		int cnt = 0;

		final int num_docs_per_page = 10;

		try {
			pages = new ArrayList<Vector<SearchResult>>(10);

			if (StringUtils.isEmpty(sy))
				sy = Utils.getCurrentSchoolYear();

			sql = new StringBuffer();
			sql.append("SELECT DISTINCT(PPGP.PPGP_ID), PPGP.PERSONNEL_ID, SCHOOL_YEAR, ");
			sql.append("PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID, ");
			sql.append("SCHOOL_REGION.REGION_ID REGION_ID, REGION_NAME, SCHOOL.SCHOOL_ID, SCHOOL_NAME, ");
			
			if(useNewCriteria){
				sql.append("ppgpgoal.*, ppgptask.*, ppgpdomain.*, ppgpstrength.* ");
			}else{
				sql.append("ppgpgoal.*, ppgptask.*, ppgptask_category.*, ppgptask_grade.*, ppgptask_subject.*, ppgptask_topicarea.*, ppgptask_stopic.* ");
			}
			

			sql.append("FROM PPGP, ppgpgoal,ppgptask, ");
			if(useNewCriteria){
				sql.append("ppgpdomain, ppgpstrength, ");
			}else{
				sql.append("ppgptask_category, ppgptask_grade, ppgptask_subject, ppgptask_topicarea, ppgptask_stopic, ");
			}
			
			
			sql.append("PERSONNEL, SCHOOL, SCHOOL_REGION WHERE SCHOOL_YEAR='"
					+ sy + "'");
			//sql.append("PERSONNEL, SCHOOL, SCHOOL_REGION, SCHOOL_REGION_SCHOOL_SYSTEMS, SCHOOL_SYSTEM, SCHOOL_SYSTEM_SCHOOLS WHERE SCHOOL_YEAR='"
					//+ sy + "'");

			if (region != null) {
				sql.append(" AND SCHOOL_REGION.REGION_ID = " + region);
			}

			if (keywords.length > 0) {
				sql.append(" AND PPGP.PPGP_ID IN (SELECT DISTINCT PPGPGOAL.PPGP_ID FROM PPGPGOAL JOIN PPGPTASK ON PPGPGOAL.PPGPGOAL_ID = PPGPTASK.PPGPGOAL_ID WHERE (");
				for (int i = 0; i < keywords.length; i++) {
					sql.append("(UPPER(PPGPGOAL_DESC) LIKE '%" + keywords[i].trim().toUpperCase() + "%'");
					sql.append(" OR UPPER(PPGPTASK_DESC) LIKE '%" + keywords[i].trim().toUpperCase() + "%')");

					if (i < keywords.length - 1) {
						sql.append(" AND ");
					}
				}
				sql.append("))");
			}
			if(useNewCriteria){
				if ((domain_id > 0) || (strength_id > 0)) {
					boolean addand = false;

					sql.append(" AND PPGP.PPGP_ID IN (SELECT DISTINCT PPGPGOAL.PPGP_ID FROM PPGPGOAL JOIN PPGPTASK ON PPGPGOAL.PPGPGOAL_ID = PPGPTASK.PPGPGOAL_ID WHERE (");

					if (domain_id > 0) {
						sql.append("PPGPTASK.DOMAIN_ID = " + domain_id);
						addand = true;
					}
					if (strength_id > 0) {
						if (addand)
							sql.append(" AND ");
						else
							addand = true;

						sql.append("PPGPTASK.STRENGTH_ID = " + strength_id);
					}
					sql.append("))");
				}
			}else{
				if ((cat_id > 0) || (grade_id > 0) || (subject_id > 0) || (topic_id > 0) || (stopic_id > 0)) {
					boolean addand = false;

					sql.append(" AND PPGP.PPGP_ID IN (SELECT DISTINCT PPGPGOAL.PPGP_ID FROM PPGPGOAL JOIN PPGPTASK ON PPGPGOAL.PPGPGOAL_ID = PPGPTASK.PPGPGOAL_ID WHERE (");

					if (cat_id > 0) {
						sql.append("PPGPTASK.CAT_ID = " + cat_id);
						addand = true;
					}
					if (grade_id > 0) {
						if (addand)
							sql.append(" AND ");
						else
							addand = true;

						sql.append("PPGPTASK.GRADE_ID = " + grade_id);
					}
					if (subject_id > 0) {
						if (addand)
							sql.append(" AND ");
						else
							addand = true;

						sql.append("PPGPTASK.SUBJECT_ID = " + subject_id);
					}
					if (topic_id > 0) {
						if (addand)
							sql.append(" AND ");
						else
							addand = true;

						sql.append("PPGPTASK.TOPIC_ID = " + topic_id);
					}
					if (stopic_id > 0) {
						if (addand)
							sql.append(" AND ");
						else
							addand = true;

						sql.append("PPGPTASK.STOPIC_ID = " + stopic_id);
					}

					sql.append("))");
				}
			}


			sql.append(" AND PPGP.PERSONNEL_ID = PERSONNEL.PERSONNEL_ID");
			sql.append(" AND ppgp.ppgp_id = ppgpgoal.ppgp_id(+)");
			sql.append(" AND ppgpgoal.ppgpgoal_id = ppgptask.ppgpgoal_id(+)");
			if(useNewCriteria){
				sql.append(" AND ppgptask.domain_id = ppgpdomain.id(+)");
				sql.append(" AND ppgptask.strength_id = ppgpstrength.id(+)");
			}else{
				sql.append(" AND ppgptask.cat_id = ppgptask_category.cat_id(+)");
				sql.append(" AND ppgptask.grade_id = ppgptask_grade.grade_id(+)");
				sql.append(" AND ppgptask.subject_id = ppgptask_subject.subject_id(+)");
				sql.append(" AND ppgptask.topic_id = ppgptask_topicarea.topic_id(+)");
				sql.append(" AND ppgptask.stopic_id = ppgptask_stopic.stopic_id(+)");
			}
			sql.append(" AND PERSONNEL.SCHOOL_ID = SCHOOL.SCHOOL_ID");
			sql.append(" AND SCHOOL.REGION_ID=SCHOOL_REGION.REGION_ID"); 
			sql.append(" ORDER BY REGION_NAME, SCHOOL_NAME, PERSONNEL_LASTNAME, PERSONNEL_FIRSTNAME");
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql.toString());
			page = new Vector<SearchResult>(num_docs_per_page);
			if (rs.next()) {
				do {
					cnt++;
					if (page.size() == num_docs_per_page) {
						pages.add(page);
						page = new Vector<SearchResult>(num_docs_per_page);
					}
					page.add(new SearchResult(RegionManager.createRegionBean(rs), PPGPDB.createPPGP(rs)));
				} while (!rs.isAfterLast());
			}
			if (page.size() > 0) {
				pages.add(page);
			}

			// System.out.println("Total: " + cnt + "\nPages: " + pages.size());
		}
		catch (SQLException e) {
			System.err.println("PPGPDB.search(): " + e);
			throw new PPGPException("Can not extract pgps from DB: " + e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return new SearchResults(pages, cnt);
	}

	public static PPGP createPPGP(ResultSet rs) throws PPGPException {

		PPGP ppgp = null;

		try {
			ppgp = new PPGP();

			ppgp.setPPGPID(rs.getInt("PPGP_ID"));
			ppgp.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			ppgp.setSchoolYear(rs.getString("SCHOOL_YEAR"));

			PPGPGoal goal = null;

			do {
				if (rs.getInt("PPGPGOAL_ID") > 0) {
					if (!ppgp.containsKey(rs.getInt("PPGPGOAL_ID"))) {
						goal = createPPGPGoal(rs);
						ppgp.put(rs.getInt("PPGPGOAL_ID"), goal);
					}
					else
						goal = ppgp.get(rs.getInt("PPGPGOAL_ID"));

					if (rs.getInt("PPGPTASK_ID") > 0)
						goal.put(rs.getInt("PPGPTASK_ID"), createPPGPTask(rs));
				}

			} while (rs.next() && (ppgp.getPPGPID() == rs.getInt("PPGP_ID")));

		}
		catch (SQLException e) {
			ppgp = null;
		}

		return ppgp;
	}

	public static PPGPGoal createPPGPGoal(ResultSet rs) {

		PPGPGoal goal = null;

		try {
			goal = new PPGPGoal();

			goal.setPPGPGoalID(rs.getInt("PPGPGOAL_ID"));
			goal.setPPGPGoalDescription(rs.getString("PPGPGOAL_DESC"));
		}
		catch (SQLException e) {
			goal = null;
		}

		return goal;
	}

	public static PPGPTask createPPGPTask(ResultSet rs) {

		PPGPTask task = null;

		try {
			task = new PPGPTask();

			task.setCategory(TaskCategoryManager.createTaskCategoryBean(rs));
			task.setCompletionDate(rs.getString("PPGPTASK_DATE"));
			task.setDescription(rs.getString("PPGPTASK_DESC"));
			task.setDistrictSupport(rs.getString("PPGPTASK_DISTRICTSUPPORT"));
			task.setGoalID(rs.getInt("PPGPGOAL_ID"));
			task.setGrade(TaskGradeManager.createTaskGradeBean(rs));
			task.setSchoolSupport(rs.getString("PPGPTASK_SCHOOLSUPPORT"));
			task.setSelfEvaluation(rs.getString("PPGPTASK_SELFEVAL"));
			task.setSpecificTopic(TaskSpecificTopicManager.createTaskSpecificTopicBean(rs));
			task.setSubject(TaskSubjectManager.createTaskSubjectBean(rs));
			task.setTaskID(rs.getInt("PPGPTASK_ID"));
			task.setTopic(TaskTopicAreaManager.createTaskTopicAreaBean(rs));
			task.setTechnologySupport(rs.getString("TECH_SUPPORT"));
			task.setTechnologySchoolSupport(rs.getString("TECH_SCHOOL_SUPPORT"));
			task.setTechnologyDistrictSupport(rs.getString("TECH_DISTRICT_SUPPORT"));
			task.setDomain(TaskDomainManager.getTaskDomainBeanById(rs.getInt("DOMAIN_ID")));
			task.setStrength(TaskDomainStrengthManager.getTaskDomainStrenthBeanById(rs.getInt("STRENGTH_ID")));
		}
		catch (SQLException e) {
			task = null;
		} catch (PPGPException e) {
			// TODO Auto-generated catch block
			task = null;
		}

		return task;
	}

}