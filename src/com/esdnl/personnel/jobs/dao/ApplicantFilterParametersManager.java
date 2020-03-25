package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantFilterParameters;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantFilterParametersManager {
	public static String getDegreesString(String degrees) {
		Connection con = null;
		CallableStatement stat = null;
		StringBuilder sb = new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_filter_degrees(?); end;");
			stat.registerOutParameter(1, OracleTypes.VARCHAR);
			stat.setString(2, degrees);
			stat.execute();
			sb.append(((OracleCallableStatement) stat).getString(1));
			
		}
		catch (SQLException e) {
			System.err.println("static String getDegreesString(String degrees): "
					+ e);
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

		return sb.toString();
	}
	public static String getSubjectGroupString(String groups) {
		Connection con = null;
		CallableStatement stat = null;
		StringBuilder sb = new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_filter_subject_groups(?); end;");
			stat.registerOutParameter(1, OracleTypes.VARCHAR);
			stat.setString(2, groups);
			stat.execute();
			sb.append(((OracleCallableStatement) stat).getString(1));
			
		}
		catch (SQLException e) {
			System.err.println("String getSubjectGroupString(String groups): "
					+ e);
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

		return sb.toString();
	}
	public static String getSubjectString(String groups) {
		Connection con = null;
		CallableStatement stat = null;
		StringBuilder sb = new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_filter_subjects(?); end;");
			stat.registerOutParameter(1, OracleTypes.VARCHAR);
			stat.setString(2, groups);
			stat.execute();
			sb.append(((OracleCallableStatement) stat).getString(1));
			
		}
		catch (SQLException e) {
			System.err.println("String getSubjectString(String groups): "
					+ e);
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

		return sb.toString();
	}
	public static String getRegionsString(String groups) {
		Connection con = null;
		CallableStatement stat = null;
		StringBuilder sb = new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_filter_regions(?); end;");
			stat.registerOutParameter(1, OracleTypes.VARCHAR);
			stat.setString(2, groups);
			stat.execute();
			sb.append(((OracleCallableStatement) stat).getString(1));
			
		}
		catch (SQLException e) {
			System.err.println("String getRegionsString(String groups): "
					+ e);
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

		return sb.toString();
	}
	public static int addApplicantFilterParameters(ApplicantFilterParameters abean) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_job_appl_sl(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,abean.getJob().getCompetitionNumber());
			stat.setString(3,abean.getApplicantId());
			stat.setInt(4,abean.getShortlistedBy());
			stat.setDate(5, null);//set in procedure
			stat.setString(6,abean.getShortlistReason());
			stat.setString(7,abean.getPermanentContract());
			stat.setInt(8, abean.getPermanentExp());
			stat.setInt(9, abean.getReplacementExp());
			stat.setInt(10, abean.getTotalExp());
			stat.setInt(11, abean.getSubDays());
			stat.setInt(12, abean.isTLARequirements() ? 1:0);
			stat.setInt(13, abean.getSpecialEducationCourses());
			stat.setInt(14, abean.getFrenchCourses());
			stat.setInt(15, abean.getMathCourses());
			stat.setInt(16, abean.getEnglishCourses());
			stat.setInt(17, abean.getMusicCourses());
			stat.setInt(18, abean.getTechnologyCourses());
			stat.setInt(19, abean.getScienceCourses());
			stat.setInt(20, abean.getSocialStudiesCourses());
			stat.setInt(21, abean.getArtCourses());
			stat.setString(22, abean.getTrainingMethods() ==  null? "" : String.join(",", abean.getTrainingMethods()));
			stat.setString(23, abean.getDegrees() ==  null? "" : String.join(",", abean.getDegrees()));
			stat.setString(24, abean.getMajorsSubjectGroup() ==  null? "" : String.join(",", abean.getMajorsSubjectGroup()));
			stat.setString(25, abean.getMajors() ==  null? "" : String.join(",", abean.getMajors()));
			stat.setString(26, abean.getMinorsSubjectGroup() ==  null? "" : String.join(",", abean.getMinorsSubjectGroup()));
			stat.setString(27, abean.getMinors() ==  null? "" : String.join(",", abean.getMinors()));
			if(abean.getRegionalPreferences() == null) {
				stat.setString(28, "");
			}else {
				String strArray[] = Arrays.stream(abean.getRegionalPreferences())
	                    .mapToObj(String::valueOf)
	                    .toArray(String[]::new);
				String indegrees = String.join(",", strArray );
				stat.setString(28, indegrees);
			}
			
			stat.execute();
			
			id = ((OracleCallableStatement) stat).getInt(1);
			
		}
		catch (SQLException e) {
			System.err.println("static addApplicantFilterParameters(ApplicantFilterParameters abean): "
					+ e);
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

		return id;
	}	
}
