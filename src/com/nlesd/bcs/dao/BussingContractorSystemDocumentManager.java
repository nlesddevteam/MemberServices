package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemDocumentBean;
public class BussingContractorSystemDocumentManager {
	public static BussingContractorSystemDocumentBean addBussingContractorSystemDocument(BussingContractorSystemDocumentBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_system_doc(?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getDocumentType());
			stat.setString(3, vbean.getDocumentTitle());
			stat.setString(4, vbean.getDocumentPath());
			stat.setString(5, vbean.getUploadedBy());
			stat.setString(6, vbean.getvInternal());
			stat.setString(7, vbean.getvExternal());
			stat.setString(8, vbean.getShowMessage());
			stat.setInt(9, vbean.getMessageDays());
			stat.setString(10, vbean.getIsActive());
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
			System.err.println("BussingContractorSystemDocumentBean addBussingContractorSystemDocument(BussingContractorSystemDocumentBean vbean): "
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
	public static ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsByType(int doctype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		ArrayList<BussingContractorSystemDocumentBean> list = new ArrayList<BussingContractorSystemDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_system_documents(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsByType(int doctype):"
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
	public static BussingContractorSystemDocumentBean getBussingContractorSystemDocumentById(int doctype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_system_document_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemDocumentBean getBussingContractorSystemDocumentById(int doctype)):"
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
	public static boolean deleteContractorSystemDocument(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_system_doc(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteContractorSystemDocument(Integer vid): " + e);
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
	public static BussingContractorSystemDocumentBean updateBussingContractorSystemDocument(BussingContractorSystemDocumentBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.bcs_pkg.update_system_doc(?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, vbean.getDocumentType());
			stat.setString(2, vbean.getDocumentTitle());
			stat.setString(3, vbean.getDocumentPath());
			stat.setString(4, vbean.getUploadedBy());
			stat.setString(5, vbean.getvInternal());
			stat.setString(6, vbean.getvExternal());
			stat.setString(7, vbean.getShowMessage());
			stat.setInt(8, vbean.getMessageDays());
			stat.setString(9, vbean.getIsActive());
			stat.setInt(10, vbean.getId());
			stat.setTimestamp(11, new Timestamp(vbean.getDateUploaded().getTime()));
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemDocumentBean updateBussingContractorSystemDocument(BussingContractorSystemDocumentBean vbean): "
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
	public static ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsByTypeCont(int doctype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		ArrayList<BussingContractorSystemDocumentBean> list = new ArrayList<BussingContractorSystemDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_system_documents_cont(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsByTypeCont(int doctype):"
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
	public static ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsMessages() {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		ArrayList<BussingContractorSystemDocumentBean> list = new ArrayList<BussingContractorSystemDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_system_documents_mess; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemDocumentsMessages():"
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
	public static ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemContractDocuments(int doctype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		ArrayList<BussingContractorSystemDocumentBean> list = new ArrayList<BussingContractorSystemDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contract_documents(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemContractDocuments(int doctype):"
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
	public static ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemRouteDocuments(int doctype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemDocumentBean ebean = new BussingContractorSystemDocumentBean();
		ArrayList<BussingContractorSystemDocumentBean> list = new ArrayList<BussingContractorSystemDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_route_documents(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doctype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemDocumentBean> getBussingContractorSystemRouteDocuments(int doctype):"
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
	public static BussingContractorSystemDocumentBean createBussingContractorSystemDocumentBean(ResultSet rs) {
		BussingContractorSystemDocumentBean abean = null;
		try {
				abean = new BussingContractorSystemDocumentBean();
				abean.setId(rs.getInt("ID"));
				abean.setDocumentType(rs.getInt("DOCUMENTTYPE"));
				abean.setDocumentTitle(rs.getString("DOCUMENTTITLE"));
				abean.setDocumentPath(rs.getString("DOCUMENTPATH"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				Timestamp ts= rs.getTimestamp("DATEUPLOADED");
				if(ts != null){
					abean.setDateUploaded(new java.util.Date(rs.getTimestamp("DATEUPLOADED").getTime()));
				}
				abean.setUploadedBy(rs.getString("UPLOADEDBY"));
				abean.setvInternal(rs.getString("VINTERNAL"));
				abean.setvExternal(rs.getString("VEXTERNAL"));
				abean.setShowMessage(rs.getString("SHOWMESSAGE"));
				abean.setMessageDays(rs.getInt("MESSAGEDAYS"));
				abean.setIsActive(rs.getString("ISACTIVE"));
				abean.setTypeString(rs.getString("DD_TEXT"));
				
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
