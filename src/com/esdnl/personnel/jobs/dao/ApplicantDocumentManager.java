package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantCovid19LogBean;
import com.esdnl.personnel.jobs.bean.ApplicantDocumentBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.constants.DocumentTypeSS;

public class ApplicantDocumentManager {

	public static ApplicantDocumentBean addApplicantDocumentBean(ApplicantDocumentBean abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_applicant_doc(?,?,?,?); end;");

			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getApplicant().getUID());
			stat.setInt(3, abean.getType().getValue());
			stat.setString(4, abean.getDescription());
			stat.setString(5, abean.getFilename());

			stat.execute();

			int id = stat.getInt(1);

			abean.setDocumentId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ApplicantDocumentBean addApplicantDocumentBean(ApplicantDocumentBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantDocumentBean to DB.", e);
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

		return abean;
	}
	public static ApplicantDocumentBean addApplicantDocumentBeanSS(ApplicantDocumentBean abean,int sstype)
	throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;
		
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
		
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_applicant_doc(?,?,?,?); end;");
		
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getApplicant().getUID());
			stat.setInt(3, sstype);
			stat.setString(4, abean.getDescription());
			stat.setString(5, abean.getFilename());
		
			stat.execute();
		
			int id = stat.getInt(1);
		
			abean.setDocumentId(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
		
			System.err.println("ApplicantDocumentBean addApplicantDocumentBean(ApplicantDocumentBean abean): " + e);
			throw new JobOpportunityException("Can not add ApplicantDocumentBean to DB.", e);
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
		
		return abean;
}
	public static void deleteApplicantDocumentBean(int id) throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.del_applicant_doc(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void deleteApplicantDocumentBean(int id): " + e);
			throw new JobOpportunityException("Can not delete ApplicantDocumentBean to DB.", e);
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

	public static ApplicantDocumentBean getApplicantDocumentBean(int documentID) throws JobOpportunityException {

		ApplicantDocumentBean doc = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_doc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, documentID);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				doc = createApplicantDocumentBean(rs);
		}
		catch (SQLException e) {
			System.err.println("ApplicantDocumentBean getApplicantDocumentBean(int documentID): " + e);
			throw new JobOpportunityException("Can not extract ApplicantDocumentBean from DB.", e);
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

		return doc;
	}

	public static Collection<ApplicantDocumentBean> getApplicantDocumentBean(ApplicantProfileBean profile)
			throws JobOpportunityException {

		ArrayList<ApplicantDocumentBean> v_opps = null;
		ApplicantDocumentBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<ApplicantDocumentBean>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_docs(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantDocumentBean(rs);
				
				

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantDocumentBean> getApplicantDocumentBean(ApplicantProfileBean profile): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantDocumentBean from DB.", e);
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

		return v_opps;
	}

	public static Collection<ApplicantDocumentBean> getApplicantDocumentBean(ApplicantProfileBean profile,DocumentType type)
			throws JobOpportunityException {

		ArrayList<ApplicantDocumentBean> v_opps = null;
		ApplicantDocumentBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new ArrayList<ApplicantDocumentBean>(3);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_docs(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.setInt(3, type.getValue());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createApplicantDocumentBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantDocumentBean> getApplicantDocumentBean(ApplicantProfileBean profile, DocumentType type): "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantDocumentBean from DB.", e);
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

		return v_opps;
	}
	public static Collection<ApplicantDocumentBean> getApplicantDocumentSSBean(ApplicantProfileBean profile,DocumentTypeSS type) throws JobOpportunityException {

			ArrayList<ApplicantDocumentBean> v_opps = null;
			ApplicantDocumentBean eBean = null;
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			
			try {
				v_opps = new ArrayList<ApplicantDocumentBean>(3);
			
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_applicant_docs(?, ?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, profile.getUID());
				stat.setInt(3, type.getValue());
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
			
				while (rs.next()) {
					eBean = createApplicantDocumentBean(rs);
			
					v_opps.add(eBean);
				}
			}
			catch (SQLException e) {
				System.err.println("Collection<ApplicantDocumentBean> getApplicantDocumentBean(ApplicantProfileBean profile,DocumentTypeSS type): "
						+ e);
				throw new JobOpportunityException("Can not extract ApplicantDocumentBean from DB.", e);
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
			
			return v_opps;
	}
	public static Collection<ApplicantDocumentBean> getNewApplicantLetters(String sin,Integer numdays) throws JobOpportunityException {
		ArrayList<ApplicantDocumentBean> v_opps = null;
		ApplicantDocumentBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<ApplicantDocumentBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_new_app_letters(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.setInt(3, numdays);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean = createApplicantDocumentBean(rs);
		
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<ApplicantDocumentBean> getNewApplicantLetters(String sin,Integer numdays) throws JobOpportunityException: "
					+ e);
			throw new JobOpportunityException("Can not extract ApplicantDocumentBean from DB.", e);
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
		
		return v_opps;
	}	
	public static ApplicantDocumentBean createApplicantDocumentBean(ResultSet rs) {

		ApplicantDocumentBean abean = null;
		try {
			abean = new ApplicantDocumentBean();

			abean.setDocumentId(rs.getInt("DOCUMENT_ID"));
			abean.setApplicant(ApplicantProfileManager.createApplicantProfileBean(rs));
			abean.setCreatedDate(new Date(rs.getTimestamp("CREATED_DATE").getTime()));
			abean.setDescription(rs.getString("DESCRIPTION"));
			abean.setFilename(rs.getString("FILENAME"));
			if(abean.getApplicant().getProfileType().equals("T")){
				abean.setType(DocumentType.get(rs.getInt("DOCUMENT_TYPE")));
			}else{
				abean.setTypeSS(DocumentTypeSS.get(rs.getInt("DOCUMENT_TYPE")));
			}
			//populate covid log details
			try {
				ApplicantCovid19LogBean clbean= new ApplicantCovid19LogBean();
				clbean.setAclId(rs.getInt("ACL_ID"));
				clbean.setVerifiedBy(rs.getString("VERIFIED_BY"));
				if(rs.getTimestamp("DATE_VERIFIED") ==  null) {
					clbean.setDateVerified(null);
				}else {
					clbean.setDateVerified(new Date(rs.getTimestamp("DATE_VERIFIED").getTime()));
				}
				clbean.setDocumentId(abean.getDocumentId());
				abean.setClBean(clbean);
			}
			catch (java.sql.SQLException e) {
				abean.setClBean(null);
			}
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}