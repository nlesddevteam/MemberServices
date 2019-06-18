package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorDateHistoryBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.DateFieldConstant;
public class BussingContractorDateHistoryManager {
	public static void addBussingContractorDateHistoryBean(BussingContractorDateHistoryBean atbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_date_history(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2,atbean.getLinkId());
			stat.setInt(3, atbean.getDateType());
			if(atbean.getOldValue() == null){
				stat.setTimestamp(4, null);
			}else{
				stat.setTimestamp(4, new Timestamp(atbean.getOldValue().getTime()));
			}
			if(atbean.getNewValue() == null){
				stat.setTimestamp(5, null);
			}else{
				stat.setTimestamp(5, new Timestamp(atbean.getNewValue().getTime()));
			}
			stat.setString(6, atbean.getChangedBy());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void addBussingContractorDateHistoryBean(BussingContractorDateHistoryBean atbean): "
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
	public static void CheckChangedEmployeeDates(BussingContractorEmployeeBean obean,BussingContractorEmployeeBean nbean,String cby){
		//now we check the date fields and see if they changed, if yes then add entry to date history table
		//DL Expiry
		if(compareDates(obean.getDlExpiryDate(),nbean.getDlExpiryDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.DLEXP.getValue());
			addbean.setOldValue(obean.getDlExpiryDate());
			addbean.setNewValue(nbean.getDlExpiryDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//DA Run Date
		if(compareDates(obean.getDaRunDate(),nbean.getDaRunDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.DARUN.getValue());
			addbean.setOldValue(obean.getDaRunDate());
			addbean.setNewValue(nbean.getDaRunDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//FA Expiry
		if(compareDates(obean.getFaExpiryDate(),nbean.getFaExpiryDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.FAEXP.getValue());
			addbean.setOldValue(obean.getFaExpiryDate());
			addbean.setNewValue(nbean.getFaExpiryDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//PRC/VSQ
		if(compareDates(obean.getPrcvsqDate(),nbean.getPrcvsqDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.PRCVSQ.getValue());
			addbean.setOldValue(obean.getPrcvsqDate());
			addbean.setNewValue(nbean.getPrcvsqDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		// PCC
		if(compareDates(obean.getPccDate(),nbean.getPccDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.PCC.getValue());
			addbean.setOldValue(obean.getPccDate());
			addbean.setNewValue(nbean.getPccDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		// Confidentiality Agreement
		if(compareDates(obean.getScaDate(),nbean.getScaDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(nbean.getId());
			addbean.setDateType(DateFieldConstant.CA.getValue());
			addbean.setOldValue(obean.getScaDate());
			addbean.setNewValue(nbean.getScaDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
	}
	public static void CheckChangedVehicleDates(BussingContractorVehicleBean obean,BussingContractorVehicleBean nbean,String cby){
		//now we check the date fields and see if they changed, if yes then add entry to date history table
		//Reg Expiry
		if(compareDates(obean.getRegExpiryDate(),nbean.getRegExpiryDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.REGEXP.getValue());
			addbean.setOldValue(obean.getRegExpiryDate());
			addbean.setNewValue(nbean.getRegExpiryDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//Insurance Expiry
		if(compareDates(obean.getInsExpiryDate(),nbean.getInsExpiryDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.INSEXP.getValue());
			addbean.setOldValue(obean.getInsExpiryDate());
			addbean.setNewValue(nbean.getInsExpiryDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//Fall Ins Date
		if(compareDates(obean.getFallInsDate(),nbean.getFallInsDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.FALLINS.getValue());
			addbean.setOldValue(obean.getFallInsDate());
			addbean.setNewValue(nbean.getFallInsDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		//Winter Ins Date
		if(compareDates(obean.getWinterInsDate(),nbean.getWinterInsDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.WININS.getValue());
			addbean.setOldValue(obean.getWinterInsDate());
			addbean.setNewValue(nbean.getWinterInsDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		// Fall HE Ins
		if(compareDates(obean.getFallHeInsDate(),nbean.getFallHeInsDate()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.FALLHEINS.getValue());
			addbean.setOldValue(obean.getFallHeInsDate());
			addbean.setNewValue(nbean.getFallHeInsDate());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		// Misc HE Ins
		if(compareDates(obean.getMiscHeInsDate1(),nbean.getMiscHeInsDate1()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.MISCHEINS1.getValue());
			addbean.setOldValue(obean.getMiscHeInsDate1());
			addbean.setNewValue(nbean.getMiscHeInsDate1());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
		// Misc HE Ins
		if(compareDates(obean.getMiscHeInsDate2(),nbean.getMiscHeInsDate2()) != 0){
			BussingContractorDateHistoryBean addbean = new BussingContractorDateHistoryBean();
			addbean.setLinkId(obean.getId());
			addbean.setDateType(DateFieldConstant.MISCHEINS2.getValue());
			addbean.setOldValue(obean.getMiscHeInsDate2());
			addbean.setNewValue(nbean.getMiscHeInsDate2());
			addbean.setChangedBy(cby);
			addBussingContractorDateHistoryBean(addbean);
		}
	}	
	private static int compareDates(Date odate, Date ndate){
		int rvalue=0;
		if(odate == null && ndate == null){
			rvalue=0;
		}else if(odate == null || ndate == null){
			rvalue=1;
		}else{
			rvalue=odate.compareTo(ndate);
		}
		
		return rvalue;
	}
	public static ArrayList<BussingContractorDateHistoryBean> getDateAuditLogEntries(int typeid, int conid, int fieldid, int fieldidv, String fdate, String tdate){
		ArrayList<BussingContractorDateHistoryBean> list = new ArrayList<BussingContractorDateHistoryBean>();
		StringBuilder sb = new StringBuilder();
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		//employee dates
		if(typeid == 1){
			sb.append("select * from BCS_DATE_HISTORY dh, BCS_CONTRACTOR_EMPLOYEE arc, BCS_CONTRACTORS con ");
			sb.append("where dh.LINK_ID=arc.ID and arc.CONTRACTORID=con.ID and dh.date_type=" + fieldid + " ");
			sb.append("and dh.DATE_CHANGED BETWEEN ");
			sb.append("TO_DATE('" + fdate + "', 'yyyy-mm-dd')");
			sb.append(" and TO_DATE('" + tdate + "', 'yyyy-mm-dd')");
			if(conid > 0){
				sb.append(" and arc.CONTRACTORID=" + conid);
			}
			sb.append(" order by dh.LINK_ID, dh.DATE_CHANGED desc");
		}else{
			//vehicle dates
			sb.append("select * from BCS_DATE_HISTORY dh, BCS_CONTRACTOR_VEHICLE arc, BCS_CONTRACTORS con ");
			sb.append("where dh.LINK_ID=arc.ID and arc.CONTRACTORID=con.ID and dh.date_type=" + fieldidv + " ");
			sb.append("and dh.DATE_CHANGED BETWEEN ");
			sb.append("TO_DATE('" + fdate + "', 'yyyy-mm-dd')");
			sb.append(" and TO_DATE('" + tdate + "', 'yyyy-mm-dd')");
			if(conid > 0){
				sb.append(" and arc.CONTRACTORID=" + conid);
			}
			sb.append(" order by dh.LINK_ID,dh.DATE_CHANGED desc");
		}
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				BussingContractorDateHistoryBean ebean = createBussingContractorDateHistoryBean(rs,typeid);
				list.add(ebean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	public static BussingContractorDateHistoryBean createBussingContractorDateHistoryBean(ResultSet rs,int datetype) {
		BussingContractorDateHistoryBean abean = null;
		try {
				abean = new BussingContractorDateHistoryBean();
				abean.setId(rs.getInt("ID"));
				abean.setLinkId(rs.getInt("LINK_ID"));
				abean.setDateType(rs.getInt("DATE_TYPE"));
				Timestamp ts= rs.getTimestamp("OLD_VALUE");
				if(ts != null){
					abean.setOldValue(new java.util.Date(rs.getTimestamp("OLD_VALUE").getTime()));
				}
				ts= rs.getTimestamp("NEW_VALUE");
				if(ts != null){
					abean.setNewValue(new java.util.Date(rs.getTimestamp("NEW_VALUE").getTime()));
				}
				ts= rs.getTimestamp("DATE_CHANGED");
				if(ts != null){
					abean.setDateChanged(new java.util.Date(rs.getTimestamp("DATE_CHANGED").getTime()));
				}
				abean.setChangedBy(rs.getString("CHANGED_BY"));
				//now we check to see if it is employee or vechicle
				if(datetype == 1){
					//populate employee bean
					abean.setBceBean(BussingContractorEmployeeManager.createBussingContractorEmployeeBean(rs));
				}else{
					//populate vehicle bean
					abean.setBcvBean(BussingContractorVehicleManager.createBussingContractorVehicleBean(rs));
				}
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	
}