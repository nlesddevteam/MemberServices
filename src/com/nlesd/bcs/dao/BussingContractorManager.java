package com.nlesd.bcs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorBean;
public class BussingContractorManager {
	public static BussingContractorBean addBussingContractor(BussingContractorBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contractor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, bpbbean.getFirstName());
			stat.setString(3, bpbbean.getLastName());
			stat.setString(4, bpbbean.getMiddleName());
			stat.setString(5, bpbbean.getEmail());
			stat.setString(6, bpbbean.getAddress1());
			stat.setString(7, bpbbean.getAddress2());
			stat.setString(8, bpbbean.getCity());
			stat.setString(9, bpbbean.getProvince());
			stat.setString(10, bpbbean.getPostalCode());
			stat.setString(11, bpbbean.getHomePhone());
			stat.setString(12, bpbbean.getCellPhone());
			stat.setString(13, bpbbean.getWorkPhone());
			stat.setString(14, bpbbean.getCompany());
			stat.setInt(15, bpbbean.getStatus());
			stat.setString(16, bpbbean.getBusinessNumber());
			stat.setString(17, bpbbean.getHstNumber());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean addBullyingPledgeBean(BussingContractorBean bpbbean): "
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
		return bpbbean;
	}
	public static boolean checkContractor(String email,String businessnumber, String hstnumber) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean isvalid=true;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contractor_check(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email.toUpperCase());
			stat.setString(3, businessnumber.toUpperCase());
			stat.setString(4, hstnumber.toUpperCase());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			if (rs.next())
				isvalid=false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static boolean checkContractor(String email,String businessnumber, String hstnumber): "
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
		return isvalid;
	}
	public static ArrayList<BussingContractorBean> getAwaitingApproval() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractors_approvals; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("boolean checkPledgeEmail(String email,String businessnumber, String hstnumber): "
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
	public static BussingContractorBean getBussingContractorById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorBean ebean = new BussingContractorBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean getBussingContractorById(Integer cid): "
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
	public static void approveContractorsStatus(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.approve_contractor_status(?); end;");
			stat.setInt(1, cid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void approveContractorsStatus(Integer cid): "
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
	public static String createTempoararyPassword(){
		//create a six character password
		StringBuilder randomString = new StringBuilder();
		String chars = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";
		int length = chars.length();
		for (int i = 0; i < 6; i++) {
			randomString.append(chars.split("")[(int) (Math.random() * (length - 1))]);
		}
		return randomString.toString();
	}
	public static Integer addBussingContractorSecurity(Integer cid,String email, String password) {
		Connection con = null;
		CallableStatement stat = null;
		Integer id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contractor_sec(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, password);
			stat.setInt(3, cid);
			stat.setString(4, email);
			stat.execute();
			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addBussingContractorSecurity(Integer cid,String email): "
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
	public static void rejectContractorsStatus(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.reject_contractor_status(?); end;");
			stat.setInt(1, cid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void rejectContractorsStatus(Integer cid,String notes): "
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
	public static void suspendContractorsStatus(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.suspend_contractor_status(?); end;");
			stat.setInt(1, cid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void suspendContractorsStatus(Integer cid): "
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
	public static void unsuspendContractorsStatus(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.unsuspend_contractor_status(?); end;");
			stat.setInt(1, cid);
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void unsuspendContractorsStatus(Integer cid): "
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
	public static BussingContractorBean getBussingContractorByEmail(String email) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorBean ebean = new BussingContractorBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_by_em(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, email);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean getBussingContractorByEmail(String email): "
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
	public static BussingContractorBean updateBussingContractor(BussingContractorBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_contractor_info(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, bpbbean.getFirstName());
			stat.setString(2, bpbbean.getLastName());
			stat.setString(3, bpbbean.getMiddleName());
			stat.setString(4, bpbbean.getEmail());
			stat.setString(5, bpbbean.getAddress1());
			stat.setString(6, bpbbean.getAddress2());
			stat.setString(7, bpbbean.getCity());
			stat.setString(8, bpbbean.getProvince());
			stat.setString(9, bpbbean.getPostalCode());
			stat.setString(10, bpbbean.getHomePhone());
			stat.setString(11, bpbbean.getCellPhone());
			stat.setString(12, bpbbean.getWorkPhone());
			stat.setString(13, bpbbean.getCompany());
			stat.setInt(14, bpbbean.getStatus());
			stat.setString(15, bpbbean.getBusinessNumber());
			stat.setString(16, bpbbean.getHstNumber());
			stat.setString(17, bpbbean.getMaddress1());
			stat.setString(18, bpbbean.getMaddress2());
			stat.setString(19, bpbbean.getMcity());
			stat.setString(20, bpbbean.getMprovince());
			stat.setString(21, bpbbean.getMpostalCode());
			stat.setString(22, bpbbean.getMsameAs());
			stat.setInt(23, bpbbean.getId());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean updateBussingContractor(BussingContractorBean bpbbean): "
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
		return bpbbean;
	}
	public static BussingContractorBean updateBussingContractorArc(BussingContractorBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.update_contractor_arc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, bpbbean.getFirstName());
			stat.setString(3, bpbbean.getLastName());
			stat.setString(4, bpbbean.getMiddleName());
			stat.setString(5, bpbbean.getEmail());
			stat.setString(6, bpbbean.getAddress1());
			stat.setString(7, bpbbean.getAddress2());
			stat.setString(8, bpbbean.getCity());
			stat.setString(9, bpbbean.getProvince());
			stat.setString(10, bpbbean.getPostalCode());
			stat.setString(11, bpbbean.getHomePhone());
			stat.setString(12, bpbbean.getCellPhone());
			stat.setString(13, bpbbean.getWorkPhone());
			stat.setString(14, bpbbean.getCompany());
			stat.setInt(15, bpbbean.getStatus());
			stat.setString(16, bpbbean.getBusinessNumber());
			stat.setString(17, bpbbean.getHstNumber());
			stat.setString(18, bpbbean.getMaddress1());
			stat.setString(19, bpbbean.getMaddress2());
			stat.setString(20, bpbbean.getMcity());
			stat.setString(21, bpbbean.getMprovince());
			stat.setString(22, bpbbean.getMpostalCode());
			stat.setString(23, bpbbean.getMsameAs());
			stat.setInt(24, bpbbean.getId());
			stat.setString(25, bpbbean.getBoardOwned());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("updateBussingContractorArc(BussingContractorBean bpbbean): "
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
		return bpbbean;
	}
	public static ArrayList<BussingContractorBean> searchContractorsByString(String searchBy, String searchFor, String province){
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		BussingContractorBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * from BCS_CONTRACTORS WHERE upper(");
			if(searchBy.equals("Province")){
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + province.toUpperCase() + "%' order by lastname,firstname");
			}else{
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%' order by lastname,firstname");
			}
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchContractorsByString(String searchBy, String searchFor) " + e);
			
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
		return list;
	}
	public static ArrayList<BussingContractorBean> searchContractorsByInteger(String searchBy, String searchFor, Integer searchInt){
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		BussingContractorBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * from BCS_CONTRACTORS WHERE ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString() + " order by lastname,firstname");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchContractorsByInteger(String searchBy, String searchFor, Integer searchInt: " + e);
			
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
		return list;
	}
	private static String getSearchByFieldName(String ddvalue){
		String searchby="";
		 if(ddvalue.equals("First Name")){
			 searchby="firstname";
		 }else if(ddvalue.equals("Last Name")){
			 searchby="lastname";
		 }else if(ddvalue.equals("Email")){
			 searchby="email";
		 }else if(ddvalue.equals("Address")){
			 searchby="address1";
		 }else if(ddvalue.equals("City")){
			 searchby="city";
		 }else if(ddvalue.equals("Province")){
			 searchby="province";
		 }else if(ddvalue.equals("Postal Code")){
			 searchby="postalcode";
		 }else if(ddvalue.equals("Phone Number")){
			 searchby="homephone";
		 }else if(ddvalue.equals("Company")){
			 searchby="company";
		 }else if(ddvalue.equals("Status")){
			 searchby="status";
		 }else if(ddvalue.equals("Business Number")){
			 searchby="businessnumber";
		 }else if(ddvalue.equals("HST Number")){
			 searchby="hstnumber";
		 }
		
		return searchby;
	}
	public static ArrayList<BussingContractorBean> getApprovedContactors() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_approved_contractors; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorBean> getApprovedContactors():" + e);
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
	public static ArrayList<BussingContractorBean> getContractorsByStatus(int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractors_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("boolean checkPledgeEmail(String email,String businessnumber, String hstnumber): "
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
	public static ArrayList<BussingContractorBean> getAllContractors() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_all_contractors; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorBean> getAllContractors(): "
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
		Collections.sort(list);
		return list;
	}
	public static TreeMap<String,BussingContractorBean> getAllContractorsTM() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,BussingContractorBean> list = new TreeMap<String,BussingContractorBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_all_contractors; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.put(abean.getContractorName(), abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<String,BussingContractorBean> getAllContractorsTM()): "
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
	public static BussingContractorBean addBussingContractorAdmin(BussingContractorBean bpbbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contractor_ad(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, bpbbean.getFirstName());
			stat.setString(3, bpbbean.getLastName());
			stat.setString(4, bpbbean.getMiddleName());
			stat.setString(5, bpbbean.getEmail());
			stat.setString(6, bpbbean.getAddress1());
			stat.setString(7, bpbbean.getAddress2());
			stat.setString(8, bpbbean.getCity());
			stat.setString(9, bpbbean.getProvince());
			stat.setString(10, bpbbean.getPostalCode());
			stat.setString(11, bpbbean.getHomePhone());
			stat.setString(12, bpbbean.getCellPhone());
			stat.setString(13, bpbbean.getWorkPhone());
			stat.setString(14, bpbbean.getCompany());
			stat.setInt(15, bpbbean.getStatus());
			stat.setString(16, bpbbean.getBusinessNumber());
			stat.setString(17, bpbbean.getHstNumber());
			stat.setString(18, bpbbean.getMaddress1());
			stat.setString(19, bpbbean.getMaddress2());
			stat.setString(20, bpbbean.getMcity());
			stat.setString(21, bpbbean.getMprovince());
			stat.setString(22, bpbbean.getMpostalCode());
			stat.setString(23, bpbbean.getMsameAs());
			stat.execute();
			Integer sid= ((OracleCallableStatement) stat).getInt(1);
			bpbbean.setId(sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorBean addBullyingPledgeBean(BussingContractorBean bpbbean): "
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
		return bpbbean;
	}
	public static TreeMap<Integer,BussingContractorBean> getContractorArchiveRecordsById(int cid, TreeMap<Integer,BussingContractorBean> list) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_arc_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			int x=1;
			while (rs.next()){
				BussingContractorBean abean = new BussingContractorBean();
				abean = createBussingContractorBean(rs);
				list.put(x,abean);
				x++;
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,BussingContractorBean> getContractorArchiveRecordsById(int cid, TreeMap<Integer,BussingContractorBean> list):" + e);
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
	public static BussingContractorBean createBussingContractorBean(ResultSet rs) {
		BussingContractorBean abean = null;
		try {
				abean = new BussingContractorBean();
				abean.setId(rs.getInt("ID"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setMiddleName(rs.getString("MIDDLENAME"));
				abean.setEmail(rs.getString("EMAIL"));
				abean.setAddress1(rs.getString("ADDRESS1"));
				abean.setAddress2(rs.getString("ADDRESS2"));
				abean.setCity(rs.getString("CITY"));
				abean.setProvince(rs.getString("PROVINCE"));
				abean.setPostalCode(rs.getString("POSTALCODE"));
				abean.setHomePhone(rs.getString("HOMEPHONE"));
				abean.setCellPhone(rs.getString("CELLPHONE"));
				abean.setWorkPhone(rs.getString("WORKPHONE"));
				abean.setCompany(rs.getString("COMPANY"));
				abean.setBusinessNumber(rs.getString("BUSINESSNUMBER"));
				abean.setHstNumber(rs.getString("HSTNUMBER"));
				abean.setStatus(rs.getInt("STATUS"));
				abean.setDateSubmitted(new java.util.Date(rs.getTimestamp("DATESUBMITTED").getTime()));
				//retrieve the security bean if it exists
				abean.setSecBean(BussingContractorSecurityManager.getBussingContractorSecurityById(abean.getId()));
				abean.setMaddress1(rs.getString("MADDRESS1"));
				abean.setMaddress2(rs.getString("MADDRESS2"));
				abean.setMcity(rs.getString("MCITY"));
				abean.setMprovince(rs.getString("MPROVINCE"));
				abean.setMpostalCode(rs.getString("MPOSTALCODE"));
				abean.setMsameAs(rs.getString("MSAMEAS"));
				abean.setBoardOwned(rs.getString("BOARDOWNED"));
				//retrieve the security bean if it exists
				abean.setComBean(BussingContractorCompanyManager.getBussingContractorCompanyById(abean.getId()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	
}
