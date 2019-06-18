package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
public class BussingContractorSystemReportTableFieldManager {
	public static ArrayList<BussingContractorSystemReportTableFieldBean> getReportTableFields(int tableid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemReportTableFieldBean ebean = new BussingContractorSystemReportTableFieldBean();
		ArrayList<BussingContractorSystemReportTableFieldBean> list = new ArrayList<BussingContractorSystemReportTableFieldBean>(); 
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_report_tables_fields(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,tableid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemReportTableFieldBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorSystemReportTableFieldBean> getReportTableFields(int tableid):"
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
	public static BussingContractorSystemReportTableFieldBean getReportTableFieldById(int fieldid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemReportTableFieldBean ebean = new BussingContractorSystemReportTableFieldBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_report_table_fld_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,fieldid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemReportTableFieldBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static BussingContractorSystemReportTableFieldBean getReportTableFieldById(int fieldid):"
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
		return ebean;
	}	
	public static BussingContractorSystemReportTableFieldBean createBussingContractorSystemReportTableFieldBean(ResultSet rs) {
		BussingContractorSystemReportTableFieldBean abean = null;
		try {
				abean = new BussingContractorSystemReportTableFieldBean();
				abean.setFieldName(rs.getString("FIELDNAME"));
				abean.setFieldTitle(rs.getString("FIELDTITLE"));
				abean.setFieldType(rs.getString("FIELDTYPE"));
				abean.setIsActive(rs.getString("ISACTIVE"));
				abean.setId(rs.getInt("ID"));
				abean.setRelatedField(rs.getInt("RELATEDFIELD"));
				abean.setTableId(rs.getInt("TABLEID"));
				abean.setConstantField(rs.getString("CONSTANTFIELD"));
				abean.setColAlias(rs.getString("COLALIAS"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
