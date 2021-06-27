package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemETReportBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class BussingContractorSystemETReportManager {
	public static ArrayList<BussingContractorSystemETReportBean> getEmployeeTrainingByStatus(int tid, int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemETReportBean> list = new ArrayList<BussingContractorSystemETReportBean>();
		try {
			con = DAOUtils.getConnection();
			if(status == 1) {
				stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_training_by_tid(?); end;");
			}else {
				stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_training_by_tid_inc(?); end;");
			}
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemETReportBean bean = createBussingContractorSystemETReportBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeeTrainingCompleted(int tid): "
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
		return list;
	}
	public static ArrayList<BussingContractorSystemETReportBean> getEmployeeTrainingByCompany(int tid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemETReportBean> list = new ArrayList<BussingContractorSystemETReportBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_training_by_com(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemETReportBean bean = createBussingContractorSystemETReportBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemETReportBean> getEmployeeTrainingByCompany(int tid): "
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
		return list;
	}		
	public static BussingContractorSystemETReportBean createBussingContractorSystemETReportBean(ResultSet rs) {
		BussingContractorSystemETReportBean abean = null;
		try {
				abean = new BussingContractorSystemETReportBean();
				abean.setId(rs.getInt("ID"));
				abean.setContinuousService(rs.getDouble("CONTINUOUSSERVICE"));
				abean.setEmployeeStatus(EmployeeStatusConstant.get(rs.getInt("STATUS")));
				abean.setEmployeeFirstName(rs.getString("FIRSTNAME"));
				abean.setEmployeeLastName(rs.getString("LASTNAME"));
				abean.setEmployeePosition(rs.getString("EMPPOS"));
				abean.setContractorFirstName(rs.getString("BCFIRSTNAME"));
				abean.setContractorLastName(rs.getString("BCLASTNAME"));
				abean.setCompanyName(rs.getString("BCCOMPANY"));
				Timestamp ts= rs.getTimestamp("TRAININGDATE");
				if(ts != null){
					abean.setTrainingDate(new java.util.Date(rs.getTimestamp("TRAININGDATE").getTime()));
				}
				ts= rs.getTimestamp("EXPIRYDATE");
				if(ts != null){
					abean.setExpiryDate(new java.util.Date(rs.getTimestamp("EXPIRYDATE").getTime()));
				}
				abean.setProvidedBy(rs.getString("PROVIDEDBY"));
				abean.setLocation(rs.getString("LOCATION"));
				abean.setTrainingType(rs.getString("TTYPESTRING"));
				abean.setTrainingLength(rs.getString("TLENSTRING"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}