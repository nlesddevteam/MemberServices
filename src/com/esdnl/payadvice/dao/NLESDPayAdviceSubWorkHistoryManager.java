package com.esdnl.payadvice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.esdnl.payadvice.bean.NLESDPayAdviceException;
import com.esdnl.payadvice.bean.NLESDPayAdviceSubWorkHistoryBean;
public class NLESDPayAdviceSubWorkHistoryManager {
	public static void addNLESDPayAdviceWorkHistoryBean(NLESDPayAdviceSubWorkHistoryBean bean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
				con = DAOUtils.getConnection();
				con.setAutoCommit(true);
				stat = con.prepareCall("begin awsd_user.payroll_advice_pkg.add_pay_advice_work_history(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
				stat.setString(1, bean.getEmplId());
				stat.setInt(2, bean.getEmplRcd());
				stat.setString(3, bean.getName());
				stat.setString(4, bean.getDur());
				stat.setString(5, bean.getTrc());
				stat.setDouble(6, bean.getTlQuantity());
				stat.setDouble(7, bean.getOverrideRate());
				stat.setString(8, bean.getLocation());
				stat.setString(9, bean.getDescr());
				stat.setString(10, bean.getUserField1());
				stat.setString(11, bean.getName1());
				stat.setDouble(12, bean.getOthHrs());
				stat.setDouble(13, bean.getOtherEarnings());
				stat.setDouble(14, bean.getCompRate());
				stat.setDouble(15, bean.getCompRateUsed());
				stat.setInt(16, bean.getPayGroup());
				stat.execute();
			}
		catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("void addNLESDPayAdviceWorkHistoryBean(NLESDPayAdviceSubWorkHistoryBean bean) " + e);
			
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
	
	public static TreeMap<Integer,NLESDPayAdviceSubWorkHistoryBean> getNLESDPayAdviceWorkHistory(int paygroupid, String empnumber) throws NLESDPayAdviceException {
		NLESDPayAdviceSubWorkHistoryBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<Integer,NLESDPayAdviceSubWorkHistoryBean> tmap = new TreeMap<Integer,NLESDPayAdviceSubWorkHistoryBean>();
		try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? := awsd_user.payroll_advice_pkg.get_pay_advice_view_his(?,?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, paygroupid);
				stat.setString(3, empnumber);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					eBean = createNLESDPayAdviceSubWorkHistoryBean(rs);
					tmap.put(eBean.getId(), eBean);
				}
		}
		catch (SQLException e) {
				System.err.println("TreeMap<Integer,NLESDPayAdviceSubWorkHistoryBean> getNLESDPayAdviceTaxesBean(int paygroupid, String empnumber) " + e);
				throw new NLESDPayAdviceException("Can not extract NLESDPayAdviceSubWorkHistoryBean from DB.", e);
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
	public static NLESDPayAdviceSubWorkHistoryBean createNLESDPayAdviceSubWorkHistoryBean(ResultSet rs) {
		NLESDPayAdviceSubWorkHistoryBean abean = null;
		try {
				abean = new NLESDPayAdviceSubWorkHistoryBean();
				abean.setId(rs.getInt("ID"));
				abean.setEmplId(rs.getString("EMPLID"));
				abean.setEmplRcd(rs.getInt("EMPL_RCD"));
				abean.setName(rs.getString("NAME"));
				abean.setDur(rs.getString("DURATION"));
				abean.setTrc(rs.getString("TRC"));
				abean.setTlQuantity(Double.parseDouble(rs.getString("TL_QUANTITY")));
				abean.setOverrideRate(Double.parseDouble(rs.getString("OVERRIDE_RATE")));
				abean.setLocation(rs.getString("LOCATION"));
				abean.setDescr(rs.getString("DESCRIPTION"));
				abean.setUserField1(rs.getString("USER_FIELD_1"));
				abean.setName1(rs.getString("NAME1"));
				abean.setOthHrs(Double.parseDouble(rs.getString("OTH_HRS")));
				abean.setOtherEarnings(Double.parseDouble(rs.getString("OTHER_EARNS")));
				abean.setCompRate(Double.parseDouble(rs.getString("COMPRATE")));
				abean.setCompRateUsed(Double.parseDouble(rs.getString("COMPRATE_USED")));
				abean.setPayGroup(Integer.parseInt(rs.getString("PAY_GROUP_ID")));
			}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
