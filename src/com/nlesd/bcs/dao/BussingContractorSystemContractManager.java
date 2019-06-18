package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
public class BussingContractorSystemContractManager {
	public static BussingContractorSystemContractBean addBussingContractorSystemContract(BussingContractorSystemContractBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contract(?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getContractName());
			stat.setInt(3, vbean.getContractType());
			stat.setString(4, vbean.getContractNotes());
			stat.setInt(5, vbean.getContractRegion());
			if(vbean.getContractExpiryDate() == null){
				stat.setTimestamp(6, null);
			}else{
				stat.setTimestamp(6, new Timestamp(vbean.getContractExpiryDate().getTime()));
			}
			stat.setString(7, vbean.getAddedBy());
			stat.setInt(8,vbean.getVehicleType());
			stat.setInt(9,vbean.getVehicleSize());
			if(vbean.getContractStartDate() == null){
				stat.setTimestamp(10, null);
			}else{
				stat.setTimestamp(10, new Timestamp(vbean.getContractStartDate().getTime()));
			}
			stat.setInt(11,vbean.getBoardOwned());
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
			System.err.println("static BussingContractorSystemContractBean addBussingContractorSystemContract(BussingContractorSystemContractBean vbean): "
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
	public static ArrayList<BussingContractorSystemContractBean> getContracts() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contracts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				BussingContractorSystemContractBean bean = createBussingContractorSystemContractBean(rs);
				list.add(bean);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("static ArrayList<BussingContractorSystemContractBean> getContractorsEmployees(): "
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
	public static BussingContractorSystemContractBean getBussingContractorSystemContractById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemContractBean ebean = new BussingContractorSystemContractBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contract_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemContractBean(rs);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemContractBean getBussingContractorSystemContractById(Integer cid):"
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
	public static BussingContractorSystemContractBean updateBussingContractorSystemContract(BussingContractorSystemContractBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_contract(?,?,?,?,?,?,?,?,?,?); end;");
			stat.setString(1, vbean.getContractName());
			stat.setInt(2, vbean.getContractType());
			stat.setString(3, vbean.getContractNotes());
			stat.setInt(4, vbean.getContractRegion());
			if(vbean.getContractExpiryDate() == null){
				stat.setTimestamp(5, null);
			}else{
				stat.setTimestamp(5, new Timestamp(vbean.getContractExpiryDate().getTime()));
			}
			stat.setString(6, vbean.getAddedBy());
			stat.setInt(7,vbean.getId());
			stat.setInt(8,vbean.getVehicleType());
			stat.setInt(9,vbean.getVehicleSize());
			if(vbean.getContractStartDate() == null){
				stat.setTimestamp(10, null);
			}else{
				stat.setTimestamp(10, new Timestamp(vbean.getContractStartDate().getTime()));
			}
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemContractBean updateBussingContractorSystemContract(BussingContractorSystemContractBean vbean): "
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
	public static boolean deleteContract(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_contract(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("static boolean deleteContract(Integer vid) " + e);
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
	public static ArrayList<BussingContractorSystemContractBean> searchContractsByString(String searchBy, String searchFor, String searchDate){
		ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
		BussingContractorSystemContractBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			if(searchBy.equals("Expiry Date")){
				sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where to_char(");
				sb.append(getSearchByFieldName(searchBy) + ",'mm/dd/yyyy') ='" + searchDate + "'");
				sb.append(" and bc.ISDELETED='N' and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
			}else{
				sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where upper(");
				sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'");
				sb.append(" and bc.ISDELETED='N' and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
			}
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemContractBean(rs);
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
	public static ArrayList<BussingContractorSystemContractBean> searchContractsByInteger(String searchBy, String searchFor, Integer searchInt){
		ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
		BussingContractorSystemContractBean ebean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement stat = null;
		try {
			con = DAOUtils.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where ");
			sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString());
			sb.append(" and bc.ISDELETED='N' and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
			stat = con.createStatement();
			rs = stat.executeQuery(sb.toString());
			while (rs.next()) {
				ebean = createBussingContractorSystemContractBean(rs);
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
			 if(ddvalue.equals("Name")){
				 searchby="bc.contractname";
			 }else if(ddvalue.equals("Type")){
				 searchby="bc.contracttype";
			 }else if(ddvalue.equals("Notes")){
				 searchby="bc.contractnotes";
			 }else if(ddvalue.equals("Region")){
				 searchby="bc.contractregion";
			 }else if(ddvalue.equals("Expiry Date")){
				 searchby="bc.contractexpirydate";
			 }
			
			return searchby;
		}
		public static ArrayList<BussingContractorSystemContractBean> getContractsForContractor(int contractorid) {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_contracts(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2, contractorid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					BussingContractorSystemContractBean bean = createBussingContractorSystemContractBean(rs);
					list.add(bean);
				}
					
			}
			catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("static ArrayList<BussingContractorSystemContractBean> getContractsForContractor(int contractorid): "
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
		public static ArrayList<BussingContractorSystemContractBean> searchContractsByStringReg(String searchBy, String searchFor, String searchDate,Integer cid){
			ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
			BussingContractorSystemContractBean ebean = null;
			Connection con = null;
			ResultSet rs = null;
			Statement stat = null;
			try {
				con = DAOUtils.getConnection();
				StringBuilder sb = new StringBuilder();
				if(searchBy.equals("Expiry Date")){
					sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where to_char(");
					sb.append(getSearchByFieldName(searchBy) + ",'mm/dd/yyyy') ='" + searchDate + "'");
					sb.append(" and bc.ISDELETED='N' and bc.BOARDOWNED=" + cid + " and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
				}else{
					sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where upper(");
					sb.append(getSearchByFieldName(searchBy) + ") like '%" + searchFor.toUpperCase() + "%'");
					sb.append(" and bc.ISDELETED='N' and bc.BOARDOWNED=" + cid + " and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
				}
				stat = con.createStatement();
				rs = stat.executeQuery(sb.toString());
				while (rs.next()) {
					ebean = createBussingContractorSystemContractBean(rs);
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
		public static ArrayList<BussingContractorSystemContractBean> searchContractsByIntegerReg(String searchBy, String searchFor, Integer searchInt,Integer cid){
			ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
			BussingContractorSystemContractBean ebean = null;
			Connection con = null;
			ResultSet rs = null;
			Statement stat = null;
			try {
				con = DAOUtils.getConnection();
				StringBuilder sb = new StringBuilder();
				sb.append("select bc.*,dd.DD_TEXT ddtext1,ddd.DD_TEXT ddtext2 from BCS_CONTRACT bc,BCS_DD_ITEMS dd,BCS_DD_ITEMS ddd where ");
				sb.append(getSearchByFieldName(searchBy) + " = " + searchInt.toString());
				sb.append(" and bc.ISDELETED='N' and bc.BOARDOWNED=" + cid + " and dd.ID=bc.CONTRACTTYPE and ddd.ID=bc.CONTRACTREGION order by bc.CONTRACTNAME");
				stat = con.createStatement();
				rs = stat.executeQuery(sb.toString());
				while (rs.next()) {
					ebean = createBussingContractorSystemContractBean(rs);
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
		public static ArrayList<BussingContractorSystemContractBean> getContractsReg(int cid) {
			Connection con = null;
			CallableStatement stat = null;
			ResultSet rs = null;
			ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
			try {
				con = DAOUtils.getConnection();
				stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contracts_reg(?); end;");
				stat.registerOutParameter(1, OracleTypes.CURSOR);
				stat.setInt(2,cid);
				stat.execute();
				rs = ((OracleCallableStatement) stat).getCursor(1);
				while (rs.next()){
					BussingContractorSystemContractBean bean = createBussingContractorSystemContractBean(rs);
					list.add(bean);
				}
					
			}
			catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();
				}
				catch (Exception ex) {}
				System.err.println("static ArrayList<BussingContractorSystemContractBean> getContractorsEmployees(): "
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
	public static BussingContractorSystemContractBean createBussingContractorSystemContractBean(ResultSet rs) {
		BussingContractorSystemContractBean abean = null;
		try {
				abean = new BussingContractorSystemContractBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractName(rs.getString("CONTRACTNAME"));
				abean.setContractType(rs.getInt("CONTRACTTYPE"));
				abean.setContractNotes(rs.getString("CONTRACTNOTES"));
				abean.setContractRegion(rs.getInt("CONTRACTREGION"));
				Timestamp ts= rs.getTimestamp("CONTRACTEXPIRYDATE");
				if(ts != null){
					abean.setContractExpiryDate(new java.util.Date(rs.getTimestamp("CONTRACTEXPIRYDATE").getTime()));
				}
				abean.setAddedBy(rs.getString("ADDEDBY"));
				ts= rs.getTimestamp("DATEADDED");
				if(ts != null){
					abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATEADDED").getTime()));
				}
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.setContractRegionString(rs.getString("DDTEXT2"));
				abean.setContractTypeString(rs.getString("DDTEXT1"));
				abean.setVehicleType(rs.getInt("VEHICLETYPE"));
				abean.setVehicleSize(rs.getInt("VEHICLESIZE"));
				ts= rs.getTimestamp("CONTRACTSTARTDATE");
				if(ts != null){
					abean.setContractStartDate(new java.util.Date(rs.getTimestamp("CONTRACTSTARTDATE").getTime()));
				}
				abean.setContractHistory(BussingContractorSystemContractHistoryManager.getBussingContractorSystemContractStatus(abean.getId()));
		}
		catch (SQLException e) {
				abean = null;
		}
		return abean;
	}	
}
