package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.Covid19EmailListBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Covid19EmailListManager {
	public static ArrayList<Covid19EmailListBean> getCovid19EmailList() {
		ArrayList<Covid19EmailListBean> v_opps = null;
		Covid19EmailListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<Covid19EmailListBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.testing_pkg.get_covid19_all_employees; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean = createCovid19EmailListBean(rs,true);
		
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("static ArrayList<Covid19EmailListBean> getCovid19EmailList(): "
					+ e);
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
	public static int addCovid19WarningEmail(Date startdate, Date enddate,String ranby,long numofemails) {

		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_covid19_warning_email(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setTimestamp(2, new java.sql.Timestamp(startdate.getTime()));
			stat.setTimestamp(3, new java.sql.Timestamp(enddate.getTime()));
			stat.setString(4, ranby);
			stat.setLong(5, numofemails);
			stat.execute();
			id= (int) stat.getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static int addCovid19WarningEmail(Date startdate, Date enddate,String ranby,long numofemails): "
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
	public static int addCovid19WarningEmailLog(String emailaddress,int cid) {

		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? :=awsd_user.personnel_jobs_pkg.add_covid19_warning_email_log(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2, emailaddress);
			stat.setInt(3, cid);
			stat.execute();
			id= (int) stat.getInt(1);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static int addCovid19WarningEmailLog(String emailaddress,int cid): "
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
	
	public static long addCovid19WarningEmailLogTM(TreeMap<String,Covid19EmailListBean> testing) {

		Connection con = null;
		CallableStatement stmt = null;
		long xx=0;
		
		try {
			con = DAOUtils.getConnection();
			//PreparedStatement stmt = con.prepareStatement("call awsd_user.personnel_jobs_pkg.add_covid19_warning_email_log(?,?)");
			//for (Map.Entry<String,Covid19EmailListBean> entry : testing.entrySet()) {
				//stmt.setString(1, entry.getKey());
				//stmt.setInt(2, -999);
				//stmt.addBatch();
				//xx++;
				//if (xx % 1000 == 0 || xx == testing.size()) {
				//	stmt.executeBatch(); // Execute every 1000 items.
				//}
		    //}
			stmt = con.prepareCall("begin  awsd_user.personnel_jobs_pkg.add_covid19_warning_email_tm(?,?); end;");
			for (Map.Entry<String,Covid19EmailListBean> entry : testing.entrySet()) {
				stmt.setString(1, entry.getKey());
				stmt.setLong(2, -999);
				stmt.addBatch();
				xx++;
				if (xx % 1000 == 0 || xx == testing.size()) {
					stmt.executeBatch(); // Execute every 1000 items.
				}
			}
			
			con.commit();
			//con.setAutoCommit(true);

			//stat = con.prepareCall("begin ? :=awsd_user.personnel_jobs_pkg.add_covid19_warning_email_log(?,?); end;");
			//stat.registerOutParameter(1, OracleTypes.INTEGER);
			//stat.setString(2, emailaddress);
			//stat.setInt(3, cid);
			//stat.execute();
			//id= (int) stat.getInt(1);
			
			try {
				stmt.close();
			}
			catch (Exception e) {}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("static int addCovid19WarningEmailLog(String emailaddress,int cid): "
					+ e);
			
		}
		finally {
			
			try {
				con.close();
			}
			catch (Exception e) {}
		}
		return xx;
	}
	public static ArrayList<Covid19EmailListBean> getCovid19EmailListCSV(int id) {
		ArrayList<Covid19EmailListBean> v_opps = null;
		Covid19EmailListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			v_opps = new ArrayList<Covid19EmailListBean>(3);
		
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_covid_email_csv(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
		
			while (rs.next()) {
				eBean = createCovid19EmailListBean(rs,false);
		
				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<Covid19EmailListBean> getCovid19EmailListCSV(int id): "
					+ e);
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
	public static Covid19EmailListBean createCovid19EmailListBean(ResultSet rs,boolean extendedfields) {
		Covid19EmailListBean abean = null;
		try {
			abean = new Covid19EmailListBean();
			abean.setFirstName(rs.getString("FNAME"));
			abean.setLastName(rs.getString("LNAME"));
			abean.setEmailAddress(rs.getString("TEMAIL"));
			if(!(extendedfields)) {
				//only getting fields for email csv
				abean.setLocation(rs.getString("LOCATION"));
			}else {
				abean.setDocumentId(rs.getInt("DOCUMENT_ID"));
				abean.setDateVerified(rs.getString("DATE_VERIFIED"));
				abean.setDateRejected(rs.getString("DATE_REJECTED"));
				if(rs.getInt("EXCEMPTION_DOC") == 1) {
					abean.setExemptionDoc(true);
				}else {
					abean.setExemptionDoc(true);
				}
			}
			
			try {
				if(rs.getInt("CSS_ID") > 0) {
					abean.setSpecialStatus(true);
				}else {
					abean.setSpecialStatus(false);
				}
			}catch(Exception e) {
				abean.setSpecialStatus(false);
			}
			
		}
		catch (SQLException e) {
			abean = null;
		}

		return abean;
	}
}
