package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantShortlistAuditBean;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantShortlistAuditManager {
	public static TreeMap<String,ApplicantShortlistAuditBean> getAuditReport(String compnumber){
		TreeMap<String,ApplicantShortlistAuditBean> auditlist = new TreeMap<String,ApplicantShortlistAuditBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.PERSONNEL_JOBS_PKG.get_shortlist_audit(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, compnumber);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				ApplicantShortlistAuditBean eBean = createApplicantShortlistAuditBean(rs);
				auditlist.put(eBean.getApplicantName(), eBean);
				
			}
		}
		catch (Exception e) {
			System.err.println("TreeMap<String,ApplicantShortlistAuditBean> getAuditReport(String compnumber) " + e);
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
		return auditlist;
	}
	public static ApplicantShortlistAuditBean createApplicantShortlistAuditBean(ResultSet rs) {

		ApplicantShortlistAuditBean abean = null;
		try {
			abean = new ApplicantShortlistAuditBean();
			abean.setApplicantId(rs.getString("SIN"));
			abean.setApplicantName(rs.getString("SURNAME") + "," + rs.getString("FIRSTNAME"));
			abean.setApplicantEmail(rs.getString("EMAIL"));
			//check to see if there is shortlist applicant audit record
			if(rs.getInt("JAS_ID") > 0) {
				abean.setJasId(rs.getInt("JAS_ID"));
				abean.setShortlistedByName(rs.getString("SBYFULLNAME"));
				if (rs.getDate("DATE_SHORTLISTED") != null) {
					abean.setShortlistedByDate(new java.util.Date(rs.getDate("DATE_SHORTLISTED").getTime()));
				}
				//check for a reason, if null then must be filter object
				if(rs.getString("SHORTLIST_REASON") != null) {
					abean.setShortlistedReason(rs.getString("SHORTLIST_REASON"));
				}else {
					//filter object used, populate fields
					abean.setPermanentContract(rs.getString("PERM_CONTRACT"));
					abean.setPermanentExp(rs.getInt("PERM_EXP"));
					abean.setReplacementExp(rs.getInt("REPL_EXP"));
					abean.setTotalExp(rs.getInt("PERM_REPL_EXP"));
					abean.setSubDays(rs.getInt("SUB_DAYS"));
					abean.setTLARequirements(rs.getBoolean("TLA_CEC"));
					abean.setSpecialEducationCourses(rs.getInt("SE_COURSES"));
					abean.setFrenchCourses(rs.getInt("FL_COURSES"));
					abean.setMathCourses(rs.getInt("M_COURSES"));
					abean.setEnglishCourses(rs.getInt("E_COURSES"));
					abean.setMusicCourses(rs.getInt("MU_COURSES"));
					abean.setTechnologyCourses(rs.getInt("T_COURSES"));
					abean.setScienceCourses(rs.getInt("S_COURSES"));
					abean.setSocialStudiesCourses(rs.getInt("SS_COURSES"));
					abean.setArtCourses(rs.getInt("A_COURSES"));
					if(rs.getString("PT_LEVEL") != null) {
						StringBuilder sb = new StringBuilder();
						String[] test = rs.getString("PT_LEVEL").split(",");
						for(String s : test) {
							if(sb == null) {
								sb = new StringBuilder();
								sb.append(TrainingMethodConstant.get(Integer.parseInt(s)).getDescription());
							}else {
								sb.append(",");
								sb.append(TrainingMethodConstant.get(Integer.parseInt(s)).getDescription());
							}
						}
						abean.setProfessionalTrainingLevel(sb.toString());
					}
					if(rs.getString("DEGREESSTRING") != null) {
						abean.setDegreeList(rs.getString("DEGREESSTRING"));
					}
					if(rs.getString("MAJORSSTRING") != null) {
						abean.setMajorList(rs.getString("MAJORSSTRING"));
					}
					if(rs.getString("MINORSSTRING") != null) {
						abean.setMinorList(rs.getString("MINORSSTRING"));
					}
					if(rs.getString("MAJORGROUPSTRING") != null) {
						abean.setMajorSubjectGroupList(rs.getString("MAJORGROUPSTRING"));
					}
					if(rs.getString("MINORGROUPSTRING") != null) {
						abean.setMinorSubjectGroupList(rs.getString("MINORGROUPSTRING"));
					}
					if(rs.getString("REGIONALPREFSTRING") != null) {
						abean.setRegionalPreferenceList(rs.getString("REGIONALPREFSTRING"));
					}
					
				}
			}
		}catch (SQLException e) {
			abean = null;
		}
		return abean;
	}	
}
