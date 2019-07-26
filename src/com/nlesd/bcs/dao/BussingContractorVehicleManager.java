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
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
public class BussingContractorVehicleManager {
	public static BussingContractorVehicleBean addBussingContractorVehicle(BussingContractorVehicleBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_cont_vehicle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getvMake() );
			stat.setInt(3, vbean.getvModel());
			stat.setString(4, vbean.getvYear());
			stat.setString(5, vbean.getvSerialNumber());
			stat.setString(6, vbean.getvPlateNumber());
			stat.setInt(7,vbean.getvType());
			stat.setInt(8, vbean.getvSize());
			stat.setString(9, vbean.getvOwner());
			if(vbean.getRegExpiryDate() == null){
				stat.setTimestamp(10, null);
			}else{
				stat.setTimestamp(10, new Timestamp(vbean.getRegExpiryDate().getTime()));
			}
			if(vbean.getInsExpiryDate() == null){
				stat.setTimestamp(11, null);
			}else{
				stat.setTimestamp(11, new Timestamp(vbean.getInsExpiryDate().getTime()));
			}
			stat.setString(12, vbean.getInsuranceProvider());
			if(vbean.getFallInsDate() == null){
				stat.setTimestamp(13, null);
			}else{
				stat.setTimestamp(13, new Timestamp(vbean.getFallInsDate().getTime()));
			}
			if(vbean.getWinterInsDate() == null){
				stat.setTimestamp(14, null);
			}else{
				stat.setTimestamp(14, new Timestamp(vbean.getWinterInsDate().getTime()));
			}
			if(vbean.getFallHeInsDate() == null){
				stat.setTimestamp(15, null);
			}else{
				stat.setTimestamp(15, new Timestamp(vbean.getFallHeInsDate().getTime()));
			}
			if(vbean.getMiscHeInsDate1() == null){
				stat.setTimestamp(16, null);
			}else{
				stat.setTimestamp(16, new Timestamp(vbean.getMiscHeInsDate1().getTime()));
			}
			if(vbean.getMiscHeInsDate2() == null){
				stat.setTimestamp(17, null);
			}else{
				stat.setTimestamp(17, new Timestamp(vbean.getMiscHeInsDate2().getTime()));
			}
			stat.setInt(18, vbean.getContractorId());
			stat.setInt(19, vbean.getvStatus());
			stat.setString(20, vbean.getvModel2());
			stat.setString(21,vbean.getvMakeOther());
			stat.setString(22,vbean.getFallCMVI());
			stat.setString(23,vbean.getFallInsStation());
			stat.setString(24,vbean.getWinterCMVI());
			stat.setString(25,vbean.getWinterInsStation());
			stat.setString(26,vbean.getUnitNumber());
			stat.setString(27,vbean.getInsurancePolicyNumber());
			stat.setString(28,vbean.getFallInsFile());
			stat.setString(29,vbean.getWinterInsFile());
			stat.setString(30,vbean.getFallHEInsFile());
			stat.setString(31,vbean.getMiscHEInsFile1());
			stat.setString(32,vbean.getMiscHEInsFile2());
			stat.setString(33,vbean.getRegFile());
			stat.setString(34,vbean.getInsFile());
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
	public static BussingContractorVehicleBean getBussingContractorVehicleById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_vehicle_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorVehicleBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorVehicleBean getBussingContractorVehicleById(Integer cid):"
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
	public static ArrayList<BussingContractorVehicleBean> getContractorsVehicles(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_vehicles_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getContractorsVehicles(int id)): "
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
	public static BussingContractorVehicleBean updateBussingContractorVehicle(BussingContractorVehicleBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_cont_vehicle_by_id(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, vbean.getvMake() );
			stat.setInt(2, vbean.getvModel());
			stat.setString(3, vbean.getvYear());
			stat.setString(4, vbean.getvSerialNumber());
			stat.setString(5, vbean.getvPlateNumber());
			stat.setInt(6,vbean.getvType());
			stat.setInt(7, vbean.getvSize());
			stat.setString(8, vbean.getvOwner());
			if(vbean.getRegExpiryDate() == null){
				stat.setTimestamp(9, null);
			}else{
				stat.setTimestamp(9, new Timestamp(vbean.getRegExpiryDate().getTime()));
			}
			if(vbean.getInsExpiryDate() == null){
				stat.setTimestamp(10, null);
			}else{
				stat.setTimestamp(10, new Timestamp(vbean.getInsExpiryDate().getTime()));
			}
			stat.setString(11, vbean.getInsuranceProvider());
			if(vbean.getFallInsDate() == null){
				stat.setTimestamp(12, null);
			}else{
				stat.setTimestamp(12, new Timestamp(vbean.getFallInsDate().getTime()));
			}
			if(vbean.getWinterInsDate() == null){
				stat.setTimestamp(13, null);
			}else{
				stat.setTimestamp(13, new Timestamp(vbean.getWinterInsDate().getTime()));
			}
			if(vbean.getFallHeInsDate() == null){
				stat.setTimestamp(14, null);
			}else{
				stat.setTimestamp(14, new Timestamp(vbean.getFallHeInsDate().getTime()));
			}
			if(vbean.getMiscHeInsDate1() == null){
				stat.setTimestamp(15, null);
			}else{
				stat.setTimestamp(15, new Timestamp(vbean.getMiscHeInsDate1().getTime()));
			}
			if(vbean.getMiscHeInsDate2() == null){
				stat.setTimestamp(16, null);
			}else{
				stat.setTimestamp(16, new Timestamp(vbean.getMiscHeInsDate2().getTime()));
			}
			stat.setInt(17, vbean.getContractorId());
			stat.setInt(18, vbean.getId());
			stat.setString(19, vbean.getvModel2());
			stat.setString(20,vbean.getvMakeOther());
			stat.setString(21,vbean.getFallCMVI());
			stat.setString(22,vbean.getFallInsStation());
			stat.setString(23,vbean.getWinterCMVI());
			stat.setString(24,vbean.getWinterInsStation());
			stat.setString(25,vbean.getUnitNumber());
			stat.setString(26,vbean.getInsurancePolicyNumber());
			stat.setString(27, vbean.getFallInsFile());
			stat.setString(28, vbean.getWinterInsFile());
			stat.setString(29, vbean.getFallHEInsFile());
			stat.setString(30, vbean.getMiscHEInsFile1());
			stat.setString(31, vbean.getMiscHEInsFile2());
			stat.setString(32, vbean.getRegFile());
			stat.setString(33, vbean.getInsFile());
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
	public static BussingContractorVehicleBean addBussingContractorVehicleArc(BussingContractorVehicleBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.add_new_cont_vehicle_arc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, vbean.getvMake() );
			stat.setInt(2, vbean.getvModel());
			stat.setString(3, vbean.getvYear());
			stat.setString(4, vbean.getvSerialNumber());
			stat.setString(5, vbean.getvPlateNumber());
			stat.setInt(6,vbean.getvType());
			stat.setInt(7, vbean.getvSize());
			stat.setString(8, vbean.getvOwner());
			if(vbean.getRegExpiryDate() == null){
				stat.setTimestamp(9, null);
			}else{
				stat.setTimestamp(9, new Timestamp(vbean.getRegExpiryDate().getTime()));
			}
			if(vbean.getInsExpiryDate() == null){
				stat.setTimestamp(10, null);
			}else{
				stat.setTimestamp(10, new Timestamp(vbean.getInsExpiryDate().getTime()));
			}
			stat.setString(11, vbean.getInsuranceProvider());
			if(vbean.getFallInsDate() == null){
				stat.setTimestamp(12, null);
			}else{
				stat.setTimestamp(12, new Timestamp(vbean.getFallInsDate().getTime()));
			}
			if(vbean.getWinterInsDate() == null){
				stat.setTimestamp(13, null);
			}else{
				stat.setTimestamp(13, new Timestamp(vbean.getWinterInsDate().getTime()));
			}
			if(vbean.getFallHeInsDate() == null){
				stat.setTimestamp(14, null);
			}else{
				stat.setTimestamp(14, new Timestamp(vbean.getFallHeInsDate().getTime()));
			}
			if(vbean.getMiscHeInsDate1() == null){
				stat.setTimestamp(15, null);
			}else{
				stat.setTimestamp(15, new Timestamp(vbean.getMiscHeInsDate1().getTime()));
			}
			if(vbean.getMiscHeInsDate2() == null){
				stat.setTimestamp(16, null);
			}else{
				stat.setTimestamp(16, new Timestamp(vbean.getMiscHeInsDate2().getTime()));
			}
			stat.setInt(17, vbean.getContractorId());
			stat.setInt(18, vbean.getvStatus());
			stat.setString(19, vbean.getApprovedBy());
			if(vbean.getDateApproved() == null){
				stat.setTimestamp(20, null);
			}else{
				stat.setTimestamp(20, new Timestamp(vbean.getDateApproved().getTime()));
			}
			stat.setInt(21, vbean.getId());
			stat.setString(22, vbean.getvModel2());
			stat.setString(23,vbean.getvMakeOther());
			stat.setString(24,vbean.getFallCMVI());
			stat.setString(25,vbean.getFallInsStation());
			stat.setString(26,vbean.getWinterCMVI());
			stat.setString(27,vbean.getWinterInsStation());
			stat.setString(28,vbean.getUnitNumber());
			stat.setString(29,vbean.getInsurancePolicyNumber());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorVehicleBean addBussingContractorVehicleArc(BussingContractorVehicleBean vbean): "
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
	public static boolean deleteContractorVehicle(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_contractor_vehicle(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteContractorVehicle(Integer vid) " + e);
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
	public static ArrayList<BussingContractorVehicleBean> getVehiclesAwaitingApproval() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_vehicles_approvals; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getVehiclesAwaitingApproval(): "
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
	public static boolean approveContractorVehicle(Integer vid,String approvedby)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.approve_cont_veh_status(?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean approveContractorVehicle(Integer vid,String approvedby) " + e);
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
	public static boolean rejectContractorVehicle(Integer vid,String approvedby,String statusnotes)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.reject_cont_veh_status(?,?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.setString(3, statusnotes);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean rejectContractorVehicle(Integer vid,String approvedby,String statusnotes) " + e);
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
	public static boolean suspendContractorVehicle(Integer vid,String approvedby,String statusnotes)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.suspend_cont_veh_status(?,?,?); end;");
			stat.setInt(1, vid);
			stat.setString(2, approvedby);
			stat.setString(3, statusnotes);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean suspendContractorVehicle(Integer vid,String approvedby,String statusnotes) " + e);
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
	public static ArrayList<BussingContractorVehicleBean> searchVehiclesByString(String searchBy, String searchFor, String province){
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT BCS_CONTRACTOR_VEHICLE.*,BCS_CONTRACTORS.COMPANY FROM BCS_CONTRACTOR_VEHICLE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_VEHICLE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE upper(");
			sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'  and isdeleted='N' order by BCS_CONTRACTORS.COMPANY,BCS_CONTRACTOR_VEHICLE.vplatenumber");
			
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorVehicleBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchVehiclesByString(String searchBy, String searchFor) " + e);
			
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
	public static ArrayList<BussingContractorVehicleBean> searchVehiclesByInteger(String searchBy, String searchFor, Integer searchInt){
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select BCS_CONTRACTOR_VEHICLE.*,BCS_CONTRACTORS.COMPANY from BCS_CONTRACTOR_VEHICLE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_VEHICLE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString() + "  and isdeleted='N order by BCS_CONTRACTORS.COMPANY,BCS_CONTRACTOR_VEHICLE.vplatenumber");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorVehicleBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchVehiclesByInteger(String searchBy, String searchFor, Integer searchInt: " + e);
			
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
	public static ArrayList<BussingContractorVehicleBean> searchVehiclesByStringReg(String searchBy, String searchFor, String province,Integer cid){
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT BCS_CONTRACTOR_VEHICLE.*,BCS_CONTRACTORS.COMPANY FROM BCS_CONTRACTOR_VEHICLE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_VEHICLE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE upper(");
			sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'  and isdeleted='N' and BCS_CONTRACTOR_VEHICLE.CONTRACTORID=" + cid 
					+ "  order by BCS_CONTRACTORS.COMPANY,BCS_CONTRACTOR_VEHICLE.vplatenumber");
			
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorVehicleBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchVehiclesByString(String searchBy, String searchFor) " + e);
			
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
	public static ArrayList<BussingContractorVehicleBean> searchVehiclesByIntegerReg(String searchBy, String searchFor, Integer searchInt,Integer cid){
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select BCS_CONTRACTOR_VEHICLE.*,BCS_CONTRACTORS.COMPANY from BCS_CONTRACTOR_VEHICLE left outer join BCS_CONTRACTORS on BCS_CONTRACTOR_VEHICLE.CONTRACTORID=BCS_CONTRACTORS.ID WHERE ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString() + "  and isdeleted='N' and BCS_CONTRACTOR_VEHICLE.CONTRACTORID=" + cid 
					+ " order by BCS_CONTRACTORS.COMPANY,BCS_CONTRACTOR_VEHICLE.vplatenumber");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorVehicleBean(rs);
				list.add(ebean);
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<BussingContractorBean> searchVehiclesByInteger(String searchBy, String searchFor, Integer searchInt: " + e);
			
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
		 if(ddvalue.equals("Make")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vmake";
		 }else if(ddvalue.equals("Model")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vmodel2";
		 }else if(ddvalue.equals("Year")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vyear";
		 }else if(ddvalue.equals("Serial Number")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vserialnumber";
		 }else if(ddvalue.equals("Plate Number")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vplatenumber";
		 }else if(ddvalue.equals("Type")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vtype";
		 }else if(ddvalue.equals("Size")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vsize";
		 }else if(ddvalue.equals("Registered Owner")){
			 searchby="BCS_CONTRACTOR_VEHICLE.VROWNER";
		 }else if(ddvalue.equals("Company")){
			 searchby="BCS_CONTRACTORS.company";
		 }else if(ddvalue.equals("Status")){
			 searchby="BCS_CONTRACTOR_VEHICLE.vstatus";
		 }else if(ddvalue.equals("Insurance Provider")){
			 searchby="BCS_CONTRACTOR_VEHICLE.insuranceprovider";
		 }else if(ddvalue.equals("Unit Number")){
			 searchby="BCS_CONTRACTOR_VEHICLE.unitnumber";
		 }
		return searchby;
	}
	public static ArrayList<BussingContractorVehicleBean> getApprovedContractorsVehicles(int id) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_a_cont_vehicles_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getApprovedContractorsVehicles(int id)): "
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
	public static BussingContractorVehicleBean getCurrentRouteVehicle(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_current_route_vehicle(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorVehicleBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorVehicleBean getCurrentRouteVehicle(Integer cid):"
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
	public static ArrayList<BussingContractorVehicleBean> getContractorsVehiclesByStatus(int id,int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_cont_vehicles_by_id_st(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.setInt(3,status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getContractorsVehiclesByStatus(int id,int status): "
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
	public static ArrayList<BussingContractorVehicleBean> getAllContractorsVehicles() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorVehicleBean ebean = new BussingContractorVehicleBean();
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_all_cont_vehicles; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorVehicleBean(rs);
				list.add(ebean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorVehicleBean> getAllContractorsVehicles():"
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
	public static ArrayList<BussingContractorVehicleBean> getVehiclesByStatus(int status) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_vehicles_by_status(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getVehiclesByStatus(int status): "
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
	public static ArrayList<BussingContractorVehicleBean> getVehiclesByStatusReg(int status,int cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_reg_vehicles_by_status(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, status);
			stat.setInt(3, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleBean> getVehiclesByStatus(int status): "
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
	public static TreeMap<Integer,BussingContractorVehicleBean> getVehicleArchiveRecordsById(int id,TreeMap<Integer,BussingContractorVehicleBean> list) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_vehicle_arc_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2,id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			Integer x=1;
			while (rs.next()){
				BussingContractorVehicleBean bean = createBussingContractorVehicleBean(rs);
				list.put(x, bean);
				x++;
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("TreeMap<Integer,BussingContractorVehicleBean> getVehicleArchiveRecordsById(int id): "
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
	public static BussingContractorVehicleBean createBussingContractorVehicleBean(ResultSet rs) {
		BussingContractorVehicleBean abean = null;
		try {
				abean = new BussingContractorVehicleBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.setvMake(rs.getInt("VMAKE"));
				abean.setvModel(rs.getInt("VMODEL"));
				abean.setvYear(rs.getString("VYEAR"));
				abean.setvSerialNumber(rs.getString("VSERIALNUMBER"));
				abean.setvPlateNumber(rs.getString("VPLATENUMBER"));
				abean.setvType(rs.getInt("VTYPE"));
				abean.setvSize(rs.getInt("VSIZE"));
				abean.setvOwner(rs.getString("VROWNER"));
				Timestamp ts= rs.getTimestamp("REGEXPIRYDATE");
				if(ts != null){
					abean.setRegExpiryDate(new java.util.Date(rs.getTimestamp("REGEXPIRYDATE").getTime()));
				}
				ts= rs.getTimestamp("INSEXPIRYDATE");
				if(ts != null){
					abean.setInsExpiryDate(new java.util.Date(rs.getTimestamp("INSEXPIRYDATE").getTime()));
				}
				abean.setInsuranceProvider(rs.getString("INSURANCEPROVIDER"));
				ts= rs.getTimestamp("FALLINSDATE");
				if(ts != null){
					abean.setFallInsDate(new java.util.Date(rs.getTimestamp("FALLINSDATE").getTime()));
				}
				ts= rs.getTimestamp("WINTERINSDATE");
				if(ts != null){
					abean.setWinterInsDate(new java.util.Date(rs.getTimestamp("WINTERINSDATE").getTime()));
				}
				ts= rs.getTimestamp("FALLHEINSDATE");
				if(ts != null){
					abean.setFallHeInsDate(new java.util.Date(rs.getTimestamp("FALLHEINSDATE").getTime()));
				}
				ts= rs.getTimestamp("MISCHEINSDATE1");
				if(ts != null){
					abean.setMiscHeInsDate1(new java.util.Date(rs.getTimestamp("MISCHEINSDATE1").getTime()));
				}
				ts= rs.getTimestamp("MISCHEINSDATE2");
				if(ts != null){
					abean.setMiscHeInsDate2(new java.util.Date(rs.getTimestamp("MISCHEINSDATE2").getTime()));
				}
				abean.setvStatus(rs.getInt("VSTATUS"));
				abean.setApprovedBy(rs.getString("APPROVEDBY"));
				ts= rs.getTimestamp("DATEAPPROVED");
				if(ts != null){
					abean.setDateApproved(new java.util.Date(rs.getTimestamp("DATEAPPROVED").getTime()));
				}
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.setBcBean(BussingContractorManager.getBussingContractorById(abean.getContractorId()));
				abean.setStatusNotes(rs.getString("STATUSNOTES"));
				abean.setvModel2(rs.getString("VMODEL2"));
				abean.setvMakeOther(rs.getString("VMAKEOTHER"));
				abean.setFallCMVI(rs.getString("FALLCMVI"));
				abean.setWinterCMVI(rs.getString("WINTERCMVI"));
				abean.setFallInsStation(rs.getString("FALLINSSTATION"));
				abean.setWinterInsStation(rs.getString("WINTERINSSTATION"));
				abean.setUnitNumber(rs.getString("UNITNUMBER"));
				abean.setInsurancePolicyNumber(rs.getString("IPOLICYNUMBER"));
				abean.setFallInsFile(rs.getString("FALLINSFILE"));
				abean.setWinterInsFile(rs.getString("WINTERINSFILE"));
				abean.setFallHEInsFile(rs.getString("FALLHEINSFILE"));
				abean.setMiscHEInsFile1(rs.getString("MISCHEINSFILE1"));
				abean.setMiscHEInsFile2(rs.getString("MISCHEINSFILE2"));
				abean.setRegFile(rs.getString("REGFILE"));
				abean.setInsFile(rs.getString("INSFILE"));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
