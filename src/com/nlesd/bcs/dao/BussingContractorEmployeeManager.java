package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
public class BussingContractorEmployeeManager {
	public static BussingContractorEmployeeBean addBussingContractorEmployee(BussingContractorEmployeeBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_cont_employee(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getContractorId());
			stat.setInt(3, vbean.getEmployeePosition());
			stat.setString(4, vbean.getStartDate());
			stat.setString(5, vbean.getContinuousService());
			stat.setString(6, vbean.getFirstName());
			stat.setString(7, vbean.getLastName());
			stat.setString(8,vbean.getMiddleName());
			stat.setString(9,vbean.getAddress1());
			stat.setString(10,vbean.getAddress2());
			stat.setString(11,vbean.getCity());
			stat.setString(12,vbean.getProvince());
			stat.setString(13,vbean.getPostalCode());
			stat.setString(14,vbean.getHomePhone());
			stat.setString(15,vbean.getCellPhone());
			stat.setString(16,vbean.getEmail());
			stat.setString(17,vbean.getDlNumber());
			if(vbean.getDlExpiryDate() == null){
				stat.setTimestamp(18, null);
			}else{
				stat.setTimestamp(18, new Timestamp(vbean.getDlExpiryDate().getTime()));
			}
			stat.setInt(19, vbean.getDlClass());
			if(vbean.getDaRunDate()== null){
				stat.setTimestamp(20, null);
			}else{
				stat.setTimestamp(20, new Timestamp(vbean.getDaRunDate().getTime()));
			}
			stat.setString(21,vbean.getDaConvictions());
			if(vbean.getFaExpiryDate() == null){
				stat.setTimestamp(22, null);
			}else{
				stat.setTimestamp(22, new Timestamp(vbean.getFaExpiryDate().getTime()));
			}
			if(vbean.getPccDate() == null){
				stat.setTimestamp(23, null);
			}else{
				stat.setTimestamp(23, new Timestamp(vbean.getPccDate().getTime()));
			}
			if(vbean.getScaDate() == null){
				stat.setTimestamp(24, null);
			}else{
				stat.setTimestamp(24, new Timestamp(vbean.getScaDate().getTime()));
			}
			
			stat.setString(25,vbean.getFindingsOfGuilt());
			stat.setString(26,vbean.getDlFront());
			stat.setString(27,vbean.getDlBack());
			stat.setString(28,vbean.getDaDocument());
			stat.setString(29,vbean.getFaDocument());
			stat.setString(30,vbean.getPrcvsqDocument());
			stat.setString(31,vbean.getPccDocument());
			stat.setString(32,vbean.getScaDocument());
			stat.setInt(33, vbean.getStatus());
			if(vbean.getPrcvsqDate() == null){
				stat.setTimestamp(34, null);
			}else{
				stat.setTimestamp(34, new Timestamp(vbean.getPrcvsqDate().getTime()));
			}
			if(vbean.getBirthDate() == null){
				stat.setTimestamp(35, null);
			}else{
				stat.setTimestamp(35, new Timestamp(vbean.getBirthDate().getTime()));
			}
			stat.setString(36,vbean.getDaSuspensions());
			stat.setString(37,vbean.getDaAccidents());
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
			System.err.println("BussingContractorVehicleBean addBussingContractorVehicle(BussingContractorVehicleBean vbean): "
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
	public static ArrayList<BussingContractorEmployeeBean> getContractorsEmployees(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_employees_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean bean = createBussingContractorEmployeeBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getContractorsVehicles(int id): "
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
	public static boolean deleteContractorEmployee(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_cont_employee(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteContractorEmployee(Integer vid) " + e);
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
		return check;
	}
	public static BussingContractorEmployeeBean getBussingContractorEmployeeById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_employee_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorEmployeeBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorEmployeeBean getBussingContractorEmployeeById(Integer cid):"
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
	public static BussingContractorEmployeeBean updateBussingContractorEmployee(BussingContractorEmployeeBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_cont_employee(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, vbean.getContractorId());
			stat.setInt(2, vbean.getEmployeePosition());
			stat.setString(3, vbean.getStartDate());
			stat.setString(4, vbean.getContinuousService());
			stat.setString(5, vbean.getFirstName());
			stat.setString(6, vbean.getLastName());
			stat.setString(7,vbean.getMiddleName());
			stat.setString(8,vbean.getAddress1());
			stat.setString(9,vbean.getAddress2());
			stat.setString(10,vbean.getCity());
			stat.setString(11,vbean.getProvince());
			stat.setString(12,vbean.getPostalCode());
			stat.setString(13,vbean.getHomePhone());
			stat.setString(14,vbean.getCellPhone());
			stat.setString(15,vbean.getEmail());
			stat.setString(16,vbean.getDlNumber());
			if(vbean.getDlExpiryDate() == null){
				stat.setTimestamp(17, null);
			}else{
				stat.setTimestamp(17, new Timestamp(vbean.getDlExpiryDate().getTime()));
			}
			stat.setInt(18, vbean.getDlClass());
			if(vbean.getDaRunDate()== null){
				stat.setTimestamp(19, null);
			}else{
				stat.setTimestamp(19, new Timestamp(vbean.getDaRunDate().getTime()));
			}
			stat.setString(20,vbean.getDaConvictions());
			if(vbean.getFaExpiryDate() == null){
				stat.setTimestamp(21, null);
			}else{
				stat.setTimestamp(21, new Timestamp(vbean.getFaExpiryDate().getTime()));
			}
			if(vbean.getPccDate() == null){
				stat.setTimestamp(22, null);
			}else{
				stat.setTimestamp(22, new Timestamp(vbean.getPccDate().getTime()));
			}
			if(vbean.getScaDate() == null){
				stat.setTimestamp(23, null);
			}else{
				stat.setTimestamp(23, new Timestamp(vbean.getScaDate().getTime()));
			}
			
			stat.setString(24,vbean.getFindingsOfGuilt());
			stat.setString(25,vbean.getDlFront());
			stat.setString(26,vbean.getDlBack());
			stat.setString(27,vbean.getDaDocument());
			stat.setString(28,vbean.getFaDocument());
			stat.setString(29,vbean.getPrcvsqDocument());
			stat.setString(30,vbean.getPccDocument());
			stat.setString(31,vbean.getScaDocument());
			stat.setInt(32, vbean.getStatus());
			if(vbean.getPrcvsqDate() == null){
				stat.setTimestamp(33, null);
			}else{
				stat.setTimestamp(33, new Timestamp(vbean.getPrcvsqDate().getTime()));
			}
			stat.setInt(34,vbean.getId());
			if(vbean.getBirthDate() == null){
				stat.setTimestamp(35, null);
			}else{
				stat.setTimestamp(35, new Timestamp(vbean.getBirthDate().getTime()));
			}
			stat.setString(36,vbean.getDaSuspensions());
			stat.setString(37,vbean.getDaAccidents());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorVehicleBean addBussingContractorVehicle(BussingContractorVehicleBean vbean): "
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
	public static boolean approveContractorEmployee(Integer vid,String approvedby)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.approve_cont_emp_status(?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean approveContractorEmployee(Integer vid,String approvedby) " + e);
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
		return check;
	}
	public static boolean rejectContractorEmployee(Integer vid,String approvedby,String statusnotes)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.reject_cont_emp_status(?,?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.setString(3, statusnotes);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean rejectContractorEmployee(Integer vid,String approvedby,String statusnotes) " + e);
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
		return check;
	}
	public static boolean suspendContractorEmployee(Integer vid,String approvedby,String statusnotes)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.suspend_cont_emp_status(?,?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.setString(3, statusnotes);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean suspendContractorEmployee(Integer vid,String approvedby,String statusnotes) " + e);
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
		return check;
	}		
	public static BussingContractorEmployeeBean addBussingContractorEmployeeArc(BussingContractorEmployeeBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_cont_emp_arc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getContractorId());
			stat.setInt(3, vbean.getEmployeePosition());
			stat.setString(4, vbean.getStartDate());
			stat.setString(5, vbean.getContinuousService());
			stat.setString(6, vbean.getFirstName());
			stat.setString(7, vbean.getLastName());
			stat.setString(8,vbean.getMiddleName());
			stat.setString(9,vbean.getAddress1());
			stat.setString(10,vbean.getAddress2());
			stat.setString(11,vbean.getCity());
			stat.setString(12,vbean.getProvince());
			stat.setString(13,vbean.getPostalCode());
			stat.setString(14,vbean.getHomePhone());
			stat.setString(15,vbean.getCellPhone());
			stat.setString(16,vbean.getEmail());
			stat.setString(17,vbean.getDlNumber());
			if(vbean.getDlExpiryDate() == null){
				stat.setTimestamp(18, null);
			}else{
				stat.setTimestamp(18, new Timestamp(vbean.getDlExpiryDate().getTime()));
			}
			stat.setInt(19, vbean.getDlClass());
			if(vbean.getDaRunDate()== null){
				stat.setTimestamp(20, null);
			}else{
				stat.setTimestamp(20, new Timestamp(vbean.getDaRunDate().getTime()));
			}
			stat.setString(21,vbean.getDaConvictions());
			if(vbean.getFaExpiryDate() == null){
				stat.setTimestamp(22, null);
			}else{
				stat.setTimestamp(22, new Timestamp(vbean.getFaExpiryDate().getTime()));
			}
			if(vbean.getPccDate() == null){
				stat.setTimestamp(23, null);
			}else{
				stat.setTimestamp(23, new Timestamp(vbean.getPccDate().getTime()));
			}
			if(vbean.getScaDate() == null){
				stat.setTimestamp(24, null);
			}else{
				stat.setTimestamp(24, new Timestamp(vbean.getScaDate().getTime()));
			}
			
			stat.setString(25,vbean.getFindingsOfGuilt());
			stat.setString(26,vbean.getDlFront());
			stat.setString(27,vbean.getDlBack());
			stat.setString(28,vbean.getDaDocument());
			stat.setString(29,vbean.getFaDocument());
			stat.setString(30,vbean.getPrcvsqDocument());
			stat.setString(31,vbean.getPccDocument());
			stat.setString(32,vbean.getScaDocument());
			stat.setInt(33, vbean.getStatus());
			if(vbean.getPrcvsqDate() == null){
				stat.setTimestamp(34, null);
			}else{
				stat.setTimestamp(34, new Timestamp(vbean.getPrcvsqDate().getTime()));
			}
			stat.setInt(35,vbean.getId());
			stat.setString(36,vbean.getApprovedBy());
			if(vbean.getDateApproved() == null){
				stat.setTimestamp(37, null);
			}else{
				stat.setTimestamp(37, new Timestamp(vbean.getDateApproved().getTime()));
			}
			stat.setString(38,vbean.getStatusNotes());
			if(vbean.getBirthDate() == null){
				stat.setTimestamp(39, null);
			}else{
				stat.setTimestamp(39, new Timestamp(vbean.getBirthDate().getTime()));
			}
			stat.setString(40,vbean.getDaSuspensions());
			stat.setString(41,vbean.getDaAccidents());
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
			System.err.println("BussingContractorVehicleBean addBussingContractorVehicle(BussingContractorVehicleBean vbean): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesAwaitingApproval() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_employees_approvals; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesAwaitingApproval(): "
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
	public static ArrayList<BussingContractorEmployeeBean> searchEmployeesByString(String searchBy, String searchFor, String province){
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT BCS_CONTRACTOR_EMPLOYEE.*,BCS_CONTRACTORS.COMPANY FROM BCS_CONTRACTOR_EMPLOYEE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE upper(");
			if(searchBy.equals("Province")){
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + province.toUpperCase() + "%' and isdeleted='N'  order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			}else{
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%' and isdeleted='N'  order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			}
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorEmployeeBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchEmployeesByString(String searchBy, String searchFor) " + e);
			
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
	public static ArrayList<BussingContractorEmployeeBean> searchEmployeesByInteger(String searchBy, String searchFor, Integer searchInt){
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select BCS_CONTRACTOR_EMPLOYEE.*,BCS_CONTRACTORS.COMPANY from BCS_CONTRACTOR_EMPLOYEE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString() + " and isdeleted='N' order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorEmployeeBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchEmployeesByInteger(String searchBy, String searchFor, Integer searchInt: " + e);
			
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
			 searchby="BCS_CONTRACTOR_EMPLOYEE.firstname";
		 }else if(ddvalue.equals("Last Name")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.lastname";
		 }else if(ddvalue.equals("Email")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.email";
		 }else if(ddvalue.equals("Address")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.address1";
		 }else if(ddvalue.equals("City")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.city";
		 }else if(ddvalue.equals("Province")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.province";
		 }else if(ddvalue.equals("Postal Code")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.postalcode";
		 }else if(ddvalue.equals("Phone Number")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.homephone";
		 }else if(ddvalue.equals("Company")){
			 searchby="BCS_CONTRACTORS.company";
		 }else if(ddvalue.equals("Status")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.status";
		 }else if(ddvalue.equals("Company")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.company";
		 }else if(ddvalue.equals("Position")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.employeeposition";
		 }else if(ddvalue.equals("Driver Licence Class")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.dlclass";
		 }else if(ddvalue.equals("Driver Licence Number")){
			 searchby="BCS_CONTRACTOR_EMPLOYEE.dlnumber";
		 }
		
		return searchby;
	}
	public static ArrayList<BussingContractorEmployeeBean> getApprovedContractorsDrivers(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_drivers_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean bean = createBussingContractorEmployeeBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorEmployeeBean> getApprovedContractorsDrivers(int id): "
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
	public static BussingContractorEmployeeBean getCurrentRouteDriver(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorEmployeeBean ebean = new BussingContractorEmployeeBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_current_route_driver(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorEmployeeBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorEmployeeBean getBussingContractorEmployeeById(Integer cid):"
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
	public static ArrayList<BussingContractorEmployeeBean> getContractorsEmployeesByStatus(int id,int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_employees_by_id_st(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.setInt(3,status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean bean = createBussingContractorEmployeeBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getContractorsEmployeesByStatus(int id,int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> getAllContractorsEmployees() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_all_cont_employees; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean bean = createBussingContractorEmployeeBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorEmployeeBean> getAllContractorsEmployees(): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesByStatus(int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_employees_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatus(int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesByStatusFull(int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_employees_by_status_f(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBeanFull(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatusFull(int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesRemoved(int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_employees_removed(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatus(int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesByStatusReg(int status,int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_employees_by_status(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.setInt(3, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBean(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatus(int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> getEmployeesByStatusRegFull(int status,int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_employees_by_status_f(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.setInt(3, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorEmployeeBean abean = new BussingContractorEmployeeBean();
				abean = createBussingContractorEmployeeBeanFull(rs);
				list.add(abean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatusRegFull(int status,int cid): "
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
	public static TreeMap<String,Integer>getEmployeesByStatusRegTM(int status,int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap<String,Integer> list = new TreeMap<String,Integer>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_employees_by_status(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.setInt(3, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				list.put(rs.getString("FIRSTNAME") + ", " + rs.getString("FIRSTNAME"), rs.getInt("ID"));
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorEmployeeBean> getEmployeesByStatus(int status): "
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
	public static ArrayList<BussingContractorEmployeeBean> searchEmployeesByIntegerReg(String searchBy, String searchFor, Integer searchInt,Integer cid){
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select BCS_CONTRACTOR_EMPLOYEE.*,BCS_CONTRACTORS.COMPANY from BCS_CONTRACTOR_EMPLOYEE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString() + " and isdeleted='N' and BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=" + cid + " order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorEmployeeBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchEmployeesByInteger(String searchBy, String searchFor, Integer searchInt: " + e);
			
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
	public static ArrayList<BussingContractorEmployeeBean> searchEmployeesByStringReg(String searchBy, String searchFor, String province,Integer cid){
		ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
		BussingContractorEmployeeBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT BCS_CONTRACTOR_EMPLOYEE.*,BCS_CONTRACTORS.COMPANY FROM BCS_CONTRACTOR_EMPLOYEE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE upper(");
			if(searchBy.equals("Province")){
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + province.toUpperCase() + "%' and isdeleted='N' and BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=" + cid + " order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			}else{
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%' and isdeleted='N' and BCS_CONTRACTOR_EMPLOYEE.CONTRACTORID=" + cid + " order by BCS_CONTRACTOR_EMPLOYEE.lastname,BCS_CONTRACTOR_EMPLOYEE.firstname");
			}
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			System.out.println(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorEmployeeBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchEmployeesByString(String searchBy, String searchFor) " + e);
			
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
	public static TreeMap<Integer,BussingContractorEmployeeBean> getEmployeeArchiveRecordsById(int id,TreeMap<Integer,BussingContractorEmployeeBean> list) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_employee_arc_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			Integer x=1;
			while (rs.next()){
				BussingContractorEmployeeBean bean = createBussingContractorEmployeeBean(rs);
				list.put(x,bean);
				x++;
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,BussingContractorEmployeeBean> getEmployeeArchiveRecordsById(int id,TreeMap<Integer,BussingContractorEmployeeBean> list): "
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
	public static BussingContractorEmployeeBean createBussingContractorEmployeeBean(ResultSet rs) {
		BussingContractorEmployeeBean abean = null;
		try {
				abean = new BussingContractorEmployeeBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.setEmployeePosition(rs.getInt("EMPLOYEEPOSITION"));
				abean.setStartDate(rs.getString("STARTDATE"));
				abean.setContinuousService(rs.getString("CONTINUOUSSERVICE"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setMiddleName(rs.getString("MIDDLENAME"));
				abean.setAddress1(rs.getString("ADDRESS1"));
				abean.setAddress2(rs.getString("ADDRESS2"));
				abean.setCity(rs.getString("CITY"));
				abean.setProvince(rs.getString("PROVINCE"));
				abean.setPostalCode(rs.getString("POSTALCODE"));
				abean.setHomePhone(rs.getString("HOMEPHONE"));
				abean.setCellPhone(rs.getString("CELLPHONE"));
				abean.setEmail(rs.getString("EMAIL"));
				abean.setDlNumber(rs.getString("DLNUMBER"));
				Timestamp ts= rs.getTimestamp("DLEXPIRYDATE");
				if(ts != null){
					abean.setDlExpiryDate(new java.util.Date(rs.getTimestamp("DLEXPIRYDATE").getTime()));
				}
				abean.setDlClass(rs.getInt("DLCLASS"));
				ts= rs.getTimestamp("DARUNDATE");
				if(ts != null){
					abean.setDaRunDate(new java.util.Date(rs.getTimestamp("DARUNDATE").getTime()));
				}
				abean.setDaConvictions(rs.getString("DACONVICTIONS"));
				ts= rs.getTimestamp("FAEXPIRYDATE");
				if(ts != null){
					abean.setFaExpiryDate(new java.util.Date(rs.getTimestamp("FAEXPIRYDATE").getTime()));
				}
				ts= rs.getTimestamp("PCCDATE");
				if(ts != null){
					abean.setPccDate(new java.util.Date(rs.getTimestamp("PCCDATE").getTime()));
				}
				ts= rs.getTimestamp("SCADATE");
				if(ts != null){
					abean.setScaDate(new java.util.Date(rs.getTimestamp("SCADATE").getTime()));
				}
				
				abean.setFindingsOfGuilt(rs.getString("FINDINGSOFGUILT"));
				abean.setDlFront(rs.getString("DLFRONT"));
				abean.setDlBack(rs.getString("DLBACK"));
				abean.setDaDocument(rs.getString("DADOCUMENT"));
				abean.setFaDocument(rs.getString("FADOCUMENT"));
				abean.setPrcvsqDocument(rs.getString("PRCVSQDOCUMENT"));
				abean.setPccDocument(rs.getString("PCCDOCUMENT"));
				abean.setScaDocument(rs.getString("SCADOCUMENT"));
				abean.setStatus(rs.getInt("STATUS"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				try {
					//using one big query
					abean.setEmployeePositionText(rs.getString("DD_TEXT"));
				}catch(Exception enew) {
					//in case we missed a function that is not returning all data one query
					abean.setEmployeePositionText(DropdownManager.getDropdownItemText(rs.getInt("EMPLOYEEPOSITION")));
				}
				ts= rs.getTimestamp("PRCVSQDATE");
				if(ts != null){
					abean.setPrcvsqDate(new java.util.Date(rs.getTimestamp("PRCVSQDATE").getTime()));
				}
				abean.setApprovedBy(rs.getString("APPROVEDBY"));
				ts= rs.getTimestamp("DATEAPPROVED");
				if(ts != null){
					abean.setDateApproved(new java.util.Date(rs.getTimestamp("DATEAPPROVED").getTime()));
				}
				ts= rs.getTimestamp("BIRTHDATE");
				if(ts != null){
					abean.setBirthDate(new java.util.Date(rs.getTimestamp("BIRTHDATE").getTime()));
				}
				abean.setStatusNotes(rs.getString("STATUSNOTES"));
				abean.setBcBean(BussingContractorManager.getBussingContractorById(abean.getContractorId()));
				abean.setDaSuspensions(rs.getString("DASUSPENSIONS"));
				abean.setDaAccidents(rs.getString("DAACCIDENTS"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
	public static boolean checkEmployeeDLNumber(String dlnumber)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.check_dl_number(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,dlnumber);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				check=true;
			}
		}
		catch (SQLException e) {
			System.err.println("boolean checkEmployeeDLNumber(String dlnumber) " + e);
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
		return check;
	}	
	public static BussingContractorEmployeeBean createBussingContractorEmployeeBeanFull(ResultSet rs)  {
		//new function that will replace old one once all queries have been updated with full data being returned in one query
		BussingContractorEmployeeBean abean = null;
		try {
				abean = new BussingContractorEmployeeBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.setEmployeePosition(rs.getInt("EMPLOYEEPOSITION"));
				abean.setStartDate(rs.getString("STARTDATE"));
				abean.setContinuousService(rs.getString("CONTINUOUSSERVICE"));
				abean.setFirstName(rs.getString("FIRSTNAME"));
				abean.setLastName(rs.getString("LASTNAME"));
				abean.setMiddleName(rs.getString("MIDDLENAME"));
				abean.setAddress1(rs.getString("ADDRESS1"));
				abean.setAddress2(rs.getString("ADDRESS2"));
				abean.setCity(rs.getString("CITY"));
				abean.setProvince(rs.getString("PROVINCE"));
				abean.setPostalCode(rs.getString("POSTALCODE"));
				abean.setHomePhone(rs.getString("HOMEPHONE"));
				abean.setCellPhone(rs.getString("CELLPHONE"));
				abean.setEmail(rs.getString("EMAIL"));
				abean.setDlNumber(rs.getString("DLNUMBER"));
				Timestamp ts= rs.getTimestamp("DLEXPIRYDATE");
				if(ts != null){
					abean.setDlExpiryDate(new java.util.Date(rs.getTimestamp("DLEXPIRYDATE").getTime()));
				}
				abean.setDlClass(rs.getInt("DLCLASS"));
				ts= rs.getTimestamp("DARUNDATE");
				if(ts != null){
					abean.setDaRunDate(new java.util.Date(rs.getTimestamp("DARUNDATE").getTime()));
				}
				abean.setDaConvictions(rs.getString("DACONVICTIONS"));
				ts= rs.getTimestamp("FAEXPIRYDATE");
				if(ts != null){
					abean.setFaExpiryDate(new java.util.Date(rs.getTimestamp("FAEXPIRYDATE").getTime()));
				}
				ts= rs.getTimestamp("PCCDATE");
				if(ts != null){
					abean.setPccDate(new java.util.Date(rs.getTimestamp("PCCDATE").getTime()));
				}
				ts= rs.getTimestamp("SCADATE");
				if(ts != null){
					abean.setScaDate(new java.util.Date(rs.getTimestamp("SCADATE").getTime()));
				}
				
				abean.setFindingsOfGuilt(rs.getString("FINDINGSOFGUILT"));
				abean.setDlFront(rs.getString("DLFRONT"));
				abean.setDlBack(rs.getString("DLBACK"));
				abean.setDaDocument(rs.getString("DADOCUMENT"));
				abean.setFaDocument(rs.getString("FADOCUMENT"));
				abean.setPrcvsqDocument(rs.getString("PRCVSQDOCUMENT"));
				abean.setPccDocument(rs.getString("PCCDOCUMENT"));
				abean.setScaDocument(rs.getString("SCADOCUMENT"));
				abean.setStatus(rs.getInt("STATUS"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.setEmployeePositionText(rs.getString("DD_TEXT"));
				
				ts= rs.getTimestamp("PRCVSQDATE");
				if(ts != null){
					abean.setPrcvsqDate(new java.util.Date(rs.getTimestamp("PRCVSQDATE").getTime()));
				}
				abean.setApprovedBy(rs.getString("APPROVEDBY"));
				ts= rs.getTimestamp("DATEAPPROVED");
				if(ts != null){
					abean.setDateApproved(new java.util.Date(rs.getTimestamp("DATEAPPROVED").getTime()));
				}
				ts= rs.getTimestamp("BIRTHDATE");
				if(ts != null){
					abean.setBirthDate(new java.util.Date(rs.getTimestamp("BIRTHDATE").getTime()));
				}
				abean.setStatusNotes(rs.getString("STATUSNOTES"));
				//pass full recordset to create function
				//abean.setBcBean(BussingContractorManager.getBussingContractorById(abean.getContractorId()));
				abean.setBcBean(BussingContractorManager.createBussingContractorBeanFull(rs));
				
				abean.setDaSuspensions(rs.getString("DASUSPENSIONS"));
				abean.setDaAccidents(rs.getString("DAACCIDENTS"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}
}
