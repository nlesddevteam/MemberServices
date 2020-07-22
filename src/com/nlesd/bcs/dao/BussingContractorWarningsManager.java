package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.BussingContractorDocumentBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.bean.BussingContractorWarningsBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
public class BussingContractorWarningsManager {
	public static ArrayList<BussingContractorWarningsBean> getWarningsByTable(String tablename) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> list = new ArrayList<BussingContractorWarningsBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_warnings_by_table(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, tablename);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorWarningsBean abean = new BussingContractorWarningsBean();
				abean = createBussingContractorWarningsBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorWarningsBean> getWarningsByTable(String tablename): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeeWarnings(int contractorid) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> wlist = getWarningsByTable("BCS_CONTRACTOR_EMPLOYEE");
		ArrayList<BussingContractorEmployeeBean> employeelist = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		try {
			con = DAOUtils.getConnection();
			
			for(BussingContractorWarningsBean wb : wlist){
				String sql="";
				sql=wb.getMainPageSql().replaceFirst("vcontractorid", Integer.toString(contractorid));
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					ebean = BussingContractorEmployeeManager.createBussingContractorEmployeeBean(rs);
					//set the warning type
					ebean.setWarningNotes(wb.getWarningName());
					employeelist.add(ebean);
				}
				
			}
			
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeeWarnings(ArrayList<BussingContractorWarningsBean> wlist, int contractorid): "
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
		return employeelist;
	}
	public static ArrayList<BussingContractorVehicleBean> getVehicleWarnings(int contractorid) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> wlist = getWarningsByTable("BCS_CONTRACTOR_VEHICLE");
		ArrayList<BussingContractorVehicleBean> employeelist = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		try {
			con = DAOUtils.getConnection();
			
			for(BussingContractorWarningsBean wb : wlist){
				String sql="";
				sql=wb.getMainPageSql().replaceFirst("vcontractorid", Integer.toString(contractorid));
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					ebean = BussingContractorVehicleManager.createBussingContractorVehicleBean(rs);
					//set the warning type
					ebean.setWarningNotes(wb.getWarningName());
					employeelist.add(ebean);
				}
				
			}
			
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getVehicleWarnings(int contractorid): "
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
		return employeelist;
	}
	public static ArrayList<BussingContractorDocumentBean> getDocumentWarnings(int contractorid) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorWarningsBean> wlist = getWarningsByTable("BCS_CONTRACTOR_DOCUMENTS");
		ArrayList<BussingContractorDocumentBean> doclist = new ArrayList<BussingContractorDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			
			for(BussingContractorWarningsBean wb : wlist){
				String sql="";
				sql=wb.getMainPageSql().replaceFirst("vcontractorid", Integer.toString(contractorid));
				stat = con.createStatement();
				rs = stat.executeQuery(sql);
				while (rs.next()) {
					BussingContractorDocumentBean ebean = BussingContractorDocumentManager.createBussingContractorDocumentBean(rs);
					//set the warning type
					ebean.setWarningNotes(wb.getWarningName());
					doclist.add(ebean);
				}
				
			}
			
				
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorDocumentBean> getDocumentWarnings(int contractorid): "
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
		return doclist;
	}	
	public static String getWarningsSqlByTable(String tablename) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		boolean isfirst=true;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_warnings_by_table(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, tablename);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			sb.append("select * from( ");
			while (rs.next()){
				BussingContractorWarningsBean abean = new BussingContractorWarningsBean();
				abean = createBussingContractorWarningsBean(rs);
				if(isfirst) {
					isfirst=false;
					sb.append(abean.getAutomatedSql());
					if(!tablename.equals(EntryTableConstant.CONTRACTORDOC.getDescription())) {
						sb.append(abean.getWarningDays());
					}
					
				}else {
					sb.append(" union ");
					sb.append(abean.getAutomatedSql());
					if(!tablename.equals(EntryTableConstant.CONTRACTORDOC.getDescription())) {
						sb.append(abean.getWarningDays());
					}
					
				}
				
			}
			if(tablename.equals(EntryTableConstant.CONTRACTOREMPLOYEE.getDescription())) {
				sb.append(") order by COMPANY,clname,cfname,wtype,LASTNAME,FIRSTNAME");	
			}else if(tablename.equals(EntryTableConstant.CONTRACTORVEHICLE.getDescription())) {
				sb.append(") order by COMPANY,clname,cfname,wtype,VPLATENUMBER,VSERIALNUMBER");	
			}else {
				sb.append(") order by COMPANY,clname,cfname,wtype");
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("String getWarningsSqlByTable(String tablename): "
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
		if(isfirst) {
			return "";
		}else {
			return sb.toString();
		}
	}
	public static ArrayList<BussingContractorEmployeeBean> getEmployeeWarningsAutomated(String sql) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> employeelist = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ebean = BussingContractorEmployeeManager.createBussingContractorEmployeeBean(rs);
				employeelist.add(ebean);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeeWarningsAutomated(String sql): "
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
		return employeelist;
	}
	public static TreeMap<String,ArrayList<BussingContractorEmployeeBean>> getEmployeeWarningsAutomatedTM(String sql) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		TreeMap<String,ArrayList<BussingContractorEmployeeBean>> employeelist = new TreeMap<String,ArrayList<BussingContractorEmployeeBean>>();
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		ArrayList<String> suspendedliste = new ArrayList<String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ebean = BussingContractorEmployeeManager.createBussingContractorEmployeeBeanFull(rs);
				//check to see if already added one for this company
				if(employeelist.containsKey(ebean.getCompanyName())) {
						employeelist.get(ebean.getCompanyName()).add(ebean);
				}else {
					ArrayList<BussingContractorEmployeeBean> alist = new ArrayList<BussingContractorEmployeeBean>();
					alist.add(ebean);
					employeelist.put(ebean.getCompanyName(), alist);
				}
				//finally we check to see if it is expired and make sure status set to suspended
				if(ebean.getWarningNotes().equals("Driver Licence Expired/Expiring")) {
					//now we check the correct date is less than today
					if(ebean.getDlExpiryDate() != null) {
						if(ebean.getStatus() == EmployeeStatusConstant.APPROVED.getValue()) {
							if(ebean.getDlExpiryDate().before(new Date())) {
								//update status to suspended
								//BussingContractorEmployeeManager.updateContractorEmployeeStatus(ebean.getId(), EmployeeStatusConstant.SUSPENDED.getValue());
								suspendedliste.add("<p>" + ebean.getFirstName() + " " + ebean.getLastName() + "(" + ebean.getCompanyName()
								+ "): Status set to suspended for Driver Licence Expired </p>");
							}
						}
					}
				}
				if(ebean.getWarningNotes().equals("First Aid/Epipen Expired (Expiring)")) {
					//now we check the correct date is less than today
					if(ebean.getFaExpiryDate() != null) {
						if(ebean.getStatus() == EmployeeStatusConstant.APPROVED.getValue()) {
							if(ebean.getFaExpiryDate().before(new Date())) {
								//update status to suspended
								//BussingContractorEmployeeManager.updateContractorEmployeeStatus(ebean.getId(), EmployeeStatusConstant.SUSPENDED.getValue());
								suspendedliste.add("<p>" + ebean.getFirstName() + " " + ebean.getLastName() + "(" + ebean.getCompanyName()
								+ "): Status set to suspended for First Aid/Epipen Expired </p>");
							}
						}
					}
				}
			}
			//now we send the message
			if(!suspendedliste.isEmpty()) {
				EmailBean email = new EmailBean();
				email.setTo("transportation@nlesd.ca");
				email.setBCC("rodneybatten@nlesd.ca");
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Suspended Employees");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				StringBuilder sb = new StringBuilder();
				for(String s: suspendedliste) {
					sb.append(s);
				}
				model.put("elist", sb.toString());
				model.put("etype","Employee(s)");
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/suspended_list.vm", model));
				email.send();
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeeWarningsAutomated(String sql): "
					+ e);
		//} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return employeelist;
	}
	public static TreeMap<String,ArrayList<BussingContractorVehicleBean>> getVehicleWarningsAutomatedTM(String sql) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		TreeMap<String,ArrayList<BussingContractorVehicleBean>> vehiclelist = new TreeMap<String,ArrayList<BussingContractorVehicleBean>>();
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		ArrayList<String> suspendedliste = new ArrayList<String>();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ebean = BussingContractorVehicleManager.createBussingContractorVehicleBeanFull(rs);
				//check to see if already added one for this company
				if(vehiclelist.containsKey(ebean.getCompanyName())) {
					vehiclelist.get(ebean.getCompanyName()).add(ebean);
				}else {
					ArrayList<BussingContractorVehicleBean> alist = new ArrayList<BussingContractorVehicleBean>();
					alist.add(ebean);
					vehiclelist.put(ebean.getCompanyName(), alist);
				}
				//finally we check to see if it is expired and make sure status set to suspended
				if(ebean.getWarningNotes().equals("Registration Expiry Date  has Past")) {
					//now we check the correct date is less than today
					if(ebean.getRegExpiryDate() != null) {
						if(ebean.getvStatus() == VehicleStatusConstant.APPROVED.getValue()) {
							if(ebean.getRegExpiryDate().before(new Date())) {
								//update status to suspended
									//BussingContractorVehicleManager.updateContractorVehicleStatus(ebean.getId(), VehicleStatusConstant.SUSPENDED.getValue());
									suspendedliste.add("<p>SN:" + ebean.getvSerialNumber() + " PN:" + ebean.getvPlateNumber() + "(" + ebean.getCompanyName()
								+ "): Status set to suspended for Registration Expired </p>");
							}
						}
					}
				}
				if(ebean.getWarningNotes().equals("Insurance Expiry Date  has Past")) {
					//now we check the correct date is less than today
					if(ebean.getInsExpiryDate() != null) {
						if(ebean.getvStatus() == VehicleStatusConstant.APPROVED.getValue()) {
							if(ebean.getInsExpiryDate().before(new Date())) {
								//update status to suspended
								//BussingContractorVehicleManager.updateContractorVehicleStatus(ebean.getId(), VehicleStatusConstant.SUSPENDED.getValue());
								suspendedliste.add("<p>SN:" + ebean.getvSerialNumber() + " PN:" + ebean.getvPlateNumber() + "(" + ebean.getCompanyName()
								+ "): Status set to suspended for Insurance Expired </p>");
							}
						}
					}
				}
			}
			//now we send the message
			if(!suspendedliste.isEmpty()) {
				EmailBean email = new EmailBean();
				email.setTo("transportation@nlesd.ca");
				email.setBCC("rodneybatten@nlesd.ca");
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Suspended Vehicles");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				StringBuilder sb = new StringBuilder();
				for(String s: suspendedliste) {
					sb.append(s);
				}
				model.put("elist", sb.toString());
				model.put("etype","Vehicles(s)");
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/suspended_list.vm", model));
				email.send();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<String,ArrayList<BussingContractorVehicleBean>> getVehicleWarningsAutomatedTM(String sql): "
					+ e);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return vehiclelist;
	}
	public static TreeMap<String,ArrayList<BussingContractorDocumentBean>> getDocumentWarningsAutomatedTM(String sql) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		TreeMap<String,ArrayList<BussingContractorDocumentBean>> doclist = new TreeMap<String,ArrayList<BussingContractorDocumentBean>>();
		BussingContractorDocumentBean ebean = new BussingContractorDocumentBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ebean = BussingContractorDocumentManager.createBussingContractorDocumentBean(rs);
				//check to see if already added one for this company
				if(doclist.containsKey(ebean.getCompanyName())) {
					doclist.get(ebean.getCompanyName()).add(ebean);
				}else {
					ArrayList<BussingContractorDocumentBean> alist = new ArrayList<BussingContractorDocumentBean>();
					alist.add(ebean);
					doclist.put(ebean.getCompanyName(), alist);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<String,ArrayList<BussingContractorDocumentBean>> getDocumentWarningsAutomatedTM(String sql): "
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
		return doclist;
	}		
	public static BussingContractorWarningsBean createBussingContractorWarningsBean(ResultSet rs) {
		BussingContractorWarningsBean abean = null;
		try {
				abean = new BussingContractorWarningsBean();
				abean.setId(rs.getInt("ID"));
				abean.setTableName(rs.getString("TABLENAME"));
				abean.setFieldName(rs.getString("FIELDNAME"));
				abean.setWarningDays(rs.getInt("WARNINGDAYS"));
				abean.setWarningNotes(rs.getString("WARNINGNOTES"));
				abean.setIsActive(rs.getString("ISACTIVE"));
				abean.setWarningName(rs.getString("WARNINGNAME"));
				abean.setWarningException(rs.getString("WARNINGEXCEPTION"));
				abean.setMainPageSql(rs.getString("MAINPAGESQL"));
				abean.setAutomatedSql(rs.getString("AUTOMATEDSQL"));		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
