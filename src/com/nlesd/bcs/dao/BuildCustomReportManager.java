package com.nlesd.bcs.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.security.User;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableBean;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
public class BuildCustomReportManager {
	public static String buildSelectClause(int[] fields,StringBuilder reportnames,int[] tables) {
		StringBuilder sb = new StringBuilder();
		boolean isused=false;
		sb.append("SELECT ");
		reportnames.append("<TR>");
		for(int x : fields){
			BussingContractorSystemReportTableFieldBean fbean = BussingContractorSystemReportTableFieldManager.getReportTableFieldById(x);
			BussingContractorSystemReportTableBean tbean = BussingContractorSystemReportTableManager.getReportTableById(fbean.getTableId());
			if(!(isused)){
				isused=true;
			}else{
				sb.append(",");
			}
			if(fbean.getColAlias() == null){
				sb.append(tbean.getShortName() + "." + fbean.getFieldName());
			}else{
				sb.append(tbean.getShortName() + "." + fbean.getColAlias());
			}
			
			reportnames.append("<TH>");
			reportnames.append(fbean.getFieldTitle());
			reportnames.append("</TH>");
	
		}
		//now we need to append the parent table fields
		BussingContractorSystemReportTableBean tbean = BussingContractorSystemReportTableManager.getReportTableById(tables[0]);
		if(!(tbean.getJoinSelect() == null)){
			sb.append("," + tbean.getJoinSelect());
			String[] headers = tbean.getJoinHeader().split(",");
			for(String s : headers){
				reportnames.append("<TH>");
				reportnames.append(s);
				reportnames.append("</TH>");
			}
		}
		reportnames.append("</TR>");
		return sb.toString();
	}
	public static String buildFromClause(int[] tables) {
		StringBuilder sb = new StringBuilder();
		boolean isused=false;
		sb.append(" FROM ");
		for(int x : tables){
			BussingContractorSystemReportTableBean fbean = BussingContractorSystemReportTableManager.getReportTableById(x);
			if(!(isused)){
				sb.append(fbean.getTableName() + " " + fbean.getShortName());
				isused=true;
			}else{
				sb.append(",");
				sb.append(fbean.getTableName() + " " + fbean.getShortName());
			}
			if(!(fbean.getJoinTables() == null)){
				if(!(isused)){
					sb.append(fbean.getJoinTables());
					isused=true;
				}else{
					sb.append(",");
					sb.append(fbean.getJoinTables());
				}
				
			}
		}
		return sb.toString();
	}
	public static String buildWhereClause(int[] fields,String[] operators, String[] ctexts,int[] selectids,String[] startdates,String[] enddates,User usr,int[] tables) {
		StringBuilder sb = new StringBuilder();
		boolean isused=false;
		int count=0;
		if(fields == null){
			//we still need to check and see if there are join tables and add the join
			for(int xx:tables){
				BussingContractorSystemReportTableBean fbean = BussingContractorSystemReportTableManager.getReportTableById(xx);
				if(!(fbean.getJoinTables() == null)){
					if(!(isused)){
						if(!(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()) == "")){
							sb.append(" WHERE ");
							//now we filter for regional admin
							sb.append(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()));
							isused=true;
						}
						
					}else{
						if(!(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()) == "")){
							sb.append(" AND ");
							//now we filter for regional admin
							sb.append(checkForRegionalAdmin(fbean.getTableName(), usr,fbean.getShortName()));
						}
					}
					if(isused==true){
						sb.append(" AND ");
						sb.append(fbean.getJoinWhere());
					}else{
						sb.append(" WHERE ");
						sb.append(fbean.getJoinWhere());
					}
					
					
				}
			}
			return sb.toString();
		}
		boolean addedjoins=false;
		BussingContractorSystemReportTableBean tbean = new BussingContractorSystemReportTableBean();
		for(int x : fields){
			BussingContractorSystemReportTableFieldBean fbean = BussingContractorSystemReportTableFieldManager.getReportTableFieldById(x);
			tbean = BussingContractorSystemReportTableManager.getReportTableById(fbean.getTableId());
			if(!(isused)){
				sb.append(" WHERE ");
				isused=true;
			}else{
				sb.append(" AND ");
			}
			if(fbean.getFieldType().equals("VARCHAR")){
				sb.append(getOperatorStringVarchar(operators[count],ctexts[count],tbean.getShortName() + "." + fbean.getFieldName()));
			}else if(fbean.getFieldType().equals("DDLIST")){
				sb.append(getOperatorStringInt(operators[count],selectids[count],tbean.getShortName() + "." + fbean.getFieldName()));
			}else if(fbean.getFieldType().equals("DATE")){
				sb.append(getOperatorStringDate(operators[count],tbean.getShortName() + "." + fbean.getFieldName(),startdates[count],enddates[count]));
			}else if(fbean.getFieldType().equals("YESNO")){
				sb.append(getOperatorStringYesNo(operators[count],ctexts[count],tbean.getShortName() + "." + fbean.getFieldName()));
			}else if(fbean.getFieldType().equals("FILE")){
				sb.append(getOperatorStringFile(operators[count],selectids[count],tbean.getShortName() + "." + fbean.getFieldName()));
			}
			if(addedjoins == false){
				if(!(tbean.getJoinTables() == null)){
					
					if(!(isused)){
						sb.append(" WHERE ");
						sb.append(tbean.getJoinWhere());
						isused=true;
					}else{
						sb.append(" AND ");
						sb.append(tbean.getJoinWhere());
					}
					
				}else{
					if(tbean.getParentTable() > 0){
						//make sure we do not add table twice
						tbean = BussingContractorSystemReportTableManager.getReportTableById(tbean.getParentTable());
						if(!(isused)){
							sb.append(" WHERE ");
							sb.append(tbean.getJoinWhere());
							isused=true;
							//now check for regional admin filter
						}else{
							sb.append(" AND ");
							sb.append(tbean.getJoinWhere());
							
						}
					}
					
				}
				addedjoins=true;
			}
			count++;
		}
		
		//now we check for regional filter
		for(int xx:tables){
			BussingContractorSystemReportTableBean fbean = BussingContractorSystemReportTableManager.getReportTableById(xx);
			if(!(fbean.getJoinTables() == null)){
				if(!(isused)){
					if(!(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()) == "")){
						sb.append(" WHERE ");
						//now we filter for regional admin
						sb.append(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()));
						isused=true;
					}
				}else{
					if(!(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()) == "")){
						sb.append(" AND ");
						//now we filter for regional admin
						sb.append(checkForRegionalAdmin(fbean.getTableName(), usr, fbean.getShortName()));
						isused=true;
					}
				}
				
				
			}
		}
		
		return sb.toString();
	}
	public static String checkForRegionalAdmin(String reporttable,User usr, String shortname){
		String sWhere="";
		if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
	    	int cid=0;
	    	if(usr.checkPermission("BCS-VIEW-WESTERN")){
				cid = BoardOwnedContractorsConstant.WESTERN.getValue();
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
			}
			if(reporttable.equals(EntryTableConstant.CONTRACTOREMPLOYEE.getDescription().toUpperCase())){
				sWhere=shortname + ".CONTRACTORID=" + cid;
			}else if (reporttable.equals(EntryTableConstant.CONTRACTORVEHICLE.getDescription().toUpperCase())){
				sWhere=shortname + ".CONTRACTORID=" + cid;
			}
		}
		return sWhere;
		
	}
	public static String getOperatorStringVarchar(String operator,String ctext, String fieldname){
		String rstring ="";
		if(operator.equals("EQ")){
			rstring="upper(" + fieldname + ") ='" + ctext.toUpperCase() + "'";
		}else if(operator.equals("CO")){
			rstring="upper(" +fieldname + ") like '%" + ctext.toUpperCase() + "%'";
		}
		return rstring;
	}
	public static String getOperatorStringInt(String operator,int fieldvalue, String fieldname){
		String rstring ="";
		rstring = fieldname + " = " + fieldvalue;
		return rstring;
	}
	public static String getOperatorStringFile(String operator,int fieldvalue, String fieldname){
		String rstring ="";
		//95 no file 96 has file
		if(fieldvalue==95){
			rstring = fieldname + " is null";
		}else{
			rstring = fieldname + " is not null";
		}
		
		return rstring;
	}
	public static String getOperatorStringDate(String operator,String fieldname,String startdate,String enddate){
		String rstring ="";
		if(operator.equals("BT")){
			rstring =  fieldname + " BETWEEN to_date('" + startdate + "','mm/dd/yyyy') and to_date('" + enddate +"','mm/dd/yyyy')"; 
		}else if(operator.equals("EQ")){
			rstring =  fieldname + " = to_date('" + startdate + "','mm/dd/yyyy')"; 
		}else if(operator.equals("GT")){
			rstring =  fieldname + " >= to_date('" + startdate + "','mm/dd/yyyy')";  
		}else if(operator.equals("LT")){
			rstring =  fieldname + " <= to_date('" + startdate + "','mm/dd/yyyy')"; 
		}
		return rstring;
	}
	public static String getOperatorStringYesNo(String operator,String ctext, String fieldname){
		String rstring ="";
		if(operator.equals("EQ")){
			if(ctext.equals("Y")){
				rstring="upper(" + fieldname + ") ='" + ctext.toUpperCase() + "'";
			}else{
				rstring="(upper(" + fieldname + ") ='" + ctext.toUpperCase() + "' or " + fieldname +" is null)";
			}
		}
		return rstring;
	}
	public static String buildSelectClauseSave(String fieldnames,String tableid) {
		StringBuilder reportnames = new StringBuilder();
		String fields[] = fieldnames.substring(1, fieldnames.length()-1).split(",");
		reportnames.append("<TR>");
		for(String x : fields){
			BussingContractorSystemReportTableFieldBean fbean = BussingContractorSystemReportTableFieldManager.getReportTableFieldById(Integer.parseInt(x.trim()));
			reportnames.append("<TH>");
			reportnames.append(fbean.getFieldTitle());
			reportnames.append("</TH>");
	
		}
		//now we need to append the parent table fields
		String[] tables = tableid.replace("[", "").replace("]", "").split(",");
		BussingContractorSystemReportTableBean tbean = BussingContractorSystemReportTableManager.getReportTableById(Integer.parseInt(tables[0]));
		if(!(tbean.getJoinSelect() == null)){
			String[] headers = tbean.getJoinHeader().split(",");
			for(String s : headers){
				reportnames.append("<TH>");
				reportnames.append(s);
				reportnames.append("</TH>");
			}
		}
		
		
		reportnames.append("</TR>");
		return reportnames.toString();
	}	
	public static String runReportSql(String sql){
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		StringBuilder sbtabledata= new StringBuilder();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				sbtabledata.append("<TR>");
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					int type = rsmd.getColumnType(i);
					if (type == Types.VARCHAR || type == Types.CHAR) {
						sbtabledata.append("<TD>");
						if(rs.getString(i) == null){
							sbtabledata.append("");
						}else{
							sbtabledata.append(rs.getString(i));
						}
						
						sbtabledata.append("</TD>");
		            }else if(type == Types.TIMESTAMP){
		            	sbtabledata.append("<TD>");
		            	Timestamp ts= rs.getTimestamp(i);
						if(ts != null){
							Date test = new java.util.Date(rs.getTimestamp(i).getTime());
							sbtabledata.append(new SimpleDateFormat("MM/dd/yyyy").format(test));
							
						}else{
							sbtabledata.append("");
						}
						
						sbtabledata.append("</TD>");
		            }
					else {
		                
						sbtabledata.append("<TD>");
						sbtabledata.append(getIntFieldStringValue(rsmd.getColumnName(i),rs.getLong(i)));
						sbtabledata.append("</TD>");
		            }
				}
				sbtabledata.append("</TR>");
			}
		}
		catch (SQLException e) {
			System.err.println("static String runReportSql(String sql) " + e);
			
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
		return sbtabledata.toString();
	}
	private static String getIntFieldStringValue(String colname,long colval){
		String returnvalue="";
		if(colname.equals("CONSTATUS")){
			returnvalue=StatusConstant.get((int)colval).getDescription();
		}else if(colname.equals("EMPSTATUS")){
			returnvalue=EmployeeStatusConstant.get((int)colval).getDescription();
		}else if(colname.equals("EMPLOYEEPOSITION")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("DLCLASS")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("VMAKE")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("VTYPE") || colname.equals("VEHICLETYPE")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("VMODEL")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("VSIZE") || colname.equals("VEHICLESIZE")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("VEHSTATUS")){
			returnvalue=VehicleStatusConstant.get((int)colval).getDescription();
		}else if(colname.equals("CONTRACTTYPE")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}else if(colname.equals("CONTRACTREGION")){
			returnvalue=DropdownManager.getDropdownItemText((int)colval);
		}
		else{
			return returnvalue=String.valueOf(colval);
		}
		return returnvalue;
	}
}
