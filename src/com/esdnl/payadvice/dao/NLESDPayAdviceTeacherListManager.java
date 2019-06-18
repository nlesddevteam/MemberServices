package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceTeacherListBean;
public class NLESDPayAdviceTeacherListManager {
	public static TreeMap<Integer,NLESDPayAdviceTeacherListBean> getNLESDPayAdviceTeacherListBean(String empnumber) throws NLESDPayAdviceException {
		NLESDPayAdviceTeacherListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,NLESDPayAdviceTeacherListBean> tmap = new TreeMap<Integer,NLESDPayAdviceTeacherListBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_tea_list_by_id(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, empnumber);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceTeacherListBean(rs);
					tmap.put(eBean.getId(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceTeacherListBean> getNLESDPayAdviceTeacherListBean(String empnumber)" + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceEarningsBean from DB.", e);
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
		return tmap;
	}
	public static ArrayList<NLESDPayAdviceTeacherListBean> getNLESDPayAdviceTeacherListAdminBean(String empnumber) throws NLESDPayAdviceException {
		NLESDPayAdviceTeacherListBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<NLESDPayAdviceTeacherListBean>tmap=new ArrayList<NLESDPayAdviceTeacherListBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_tea_list_adm(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setString(2, empnumber);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceTeacherListBean(rs);
					tmap.add(eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("ArrayList<NLESDPayAdviceTeacherListBean> getNLESDPayAdviceTeacherListAdminBean(String empnumber)" + e);
				throw new NLESDPayAdviceException("ArrayList<NLESDPayAdviceTeacherListBean> getNLESDPayAdviceTeacherListAdminBean(String empnumber)", e);
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
		return tmap;
	}
	public static NLESDPayAdviceTeacherListBean createNLESDPayAdviceTeacherListBean(ResultSet rs) {
		NLESDPayAdviceTeacherListBean abean = null;
		try {
				abean = new NLESDPayAdviceTeacherListBean();
				abean.setId(rs.getInt("ID"));
				abean.setPayGp(rs.getString("PAY_GP"));
				abean.setPayBgDt(rs.getString("PAY_BG_DT"));
				abean.setPayEndDt(rs.getString("PAY_END_DT"));
				abean.setBusUnit(rs.getString("BUS_UNIT"));
				//abean.setCheckNum(rs.getString("CHECK_NUM"));not used with new logic
				abean.setCheckNum("");
				abean.setCheckDt(rs.getString("CHECK_DT"));
				abean.setEmpNumber(rs.getString("EMPNUMBER"));
				abean.setPayrollId(rs.getString("EMPLOYEE_ID"));
				abean.setHisCount(rs.getInt("hiscount"));
				abean.setEmpName(rs.getString("EMP_NAME"));
				//abean.setEmpInfoId(rs.getInt("EMPID"));
				abean.setEmpInfoId(0);
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
