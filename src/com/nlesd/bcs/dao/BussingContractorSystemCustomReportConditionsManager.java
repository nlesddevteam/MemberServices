package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportConditionsBean;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
public class BussingContractorSystemCustomReportConditionsManager {
	public static BussingContractorSystemCustomReportConditionsBean addBussingContractorCustomConditionsReportBean(BussingContractorSystemCustomReportConditionsBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_custom_report_cond(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getFieldId());
			stat.setString(3, vbean.getOperatorId());
			stat.setString(4, vbean.getcText());
			stat.setInt(5, vbean.getReportId());
			stat.setInt(6, vbean.getSelectId());
			stat.setString(7, vbean.getStartDate());
			stat.setString(8, vbean.getEndDate());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			vbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemCustomReportConditionsBean addBussingContractorCustomConditionsReportBean(BussingContractorSystemCustomReportConditionsBean vbean): "
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
		return vbean;
	}
	public static ArrayList<BussingContractorSystemCustomReportConditionsBean> getCustomReportConditionsById(int reportid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemCustomReportConditionsBean> list = new ArrayList<BussingContractorSystemCustomReportConditionsBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_custom_reports_cond_by(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,reportid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemCustomReportConditionsBean bean = createBussingContractorSystemCustomReportConditionsBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorSystemCustomReportConditionsBean> getCustomReportConditionsById(int reportid): "
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
	public static void deleteBussingContractorCustomConditionsReportBean(int id) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_report_condition(?); end;");
			stat.setInt(1, id);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static void deleteBussingContractorCustomConditionsReportBean(int id): "
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
	}	
	public static BussingContractorSystemCustomReportConditionsBean createBussingContractorSystemCustomReportConditionsBean(ResultSet rs) {
		BussingContractorSystemCustomReportConditionsBean abean = null;
		try {
				abean = new BussingContractorSystemCustomReportConditionsBean();
				abean.setId(rs.getInt("ID"));
				abean.setFieldId(rs.getInt("FIELDID"));
				abean.setOperatorId(rs.getString("OPERATORID"));
				abean.setcText(rs.getString("CTEXT"));
				abean.setReportId(rs.getInt("REPORTID"));
				abean.setSelectId(rs.getInt("SELECTID"));
				abean.setStartDate(rs.getString("STARTDATE"));
				abean.setEndDate(rs.getString("ENDDATE"));
				//now we set the field name to use on screen
				BussingContractorSystemReportTableFieldBean fbean =BussingContractorSystemReportTableFieldManager.getReportTableFieldById(abean.getFieldId());
				abean.setFieldName(fbean.getFieldTitle());
				abean.setFieldType(fbean.getFieldType());
				if(abean.getSelectId() > 0){
					abean.setSelectText(DropdownManager.getDropdownItemText(abean.getSelectId()));
				}else{
					abean.setSelectText("");
				}
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
