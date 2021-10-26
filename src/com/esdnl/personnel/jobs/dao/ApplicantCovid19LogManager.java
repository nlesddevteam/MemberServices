package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ApplicantCovid19LogManager {
	public static int addCovid19Log(int docid) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.add_job_appl_covid19_log(?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setInt(2, docid);
			stat.execute();
			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			System.err.println("addCovid19LogManagers(int docid) : " + e);
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
	public static int verifyCovid19Doc(int docid,String verifiedby) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.verify_covid19_doc(?,?); end;");
			stat.setInt(1, docid);
			stat.setString(2, verifiedby);
			stat.execute();
			
		}
		catch (SQLException e) {
			System.err.println("verifyCovid19Doc(int docid,String verifiedby) : " + e);
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
