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
import com.nlesd.bcs.bean.BussingContractorVehicleDocumentBean;
public class BussingContractorVehicleDocumentManager {
	public static BussingContractorVehicleDocumentBean addBussingContractorVehicleDocument(BussingContractorVehicleDocumentBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_vehicle_doc(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getVehicleId() );
			stat.setInt(3, vbean.getContractorId());
			stat.setInt(4, vbean.getDocumentType());
			stat.setString(5, vbean.getDocumentTitle());
			stat.setString(6, vbean.getDocumentPath());
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
			System.err.println("BussingContractorVehicleDocumentBean addBussingContractorVehicleDocument(BussingContractorVehicleDocumentBean vbean): "
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
	public static ArrayList<BussingContractorVehicleDocumentBean> getBussingContractorVehicleDocumentsById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorVehicleDocumentBean ebean = new BussingContractorVehicleDocumentBean();
		ArrayList<BussingContractorVehicleDocumentBean> list = new ArrayList<BussingContractorVehicleDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_vehicle_documents(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorVehicleDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorVehicleDocumentBean> getBussingContractorVehicleDocumentsById(Integer cid):"
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
	public static boolean deleteContractorVehicleDocument(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_cont_veh_doc(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteContractorVehicleDocument(Integer vid) " + e);
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
	public static BussingContractorVehicleDocumentBean createBussingContractorVehicleDocumentBean(ResultSet rs) {
		BussingContractorVehicleDocumentBean abean = null;
		try {
				abean = new BussingContractorVehicleDocumentBean();
				abean.setId(rs.getInt("ID"));
				abean.setVehicleId(rs.getInt("VEHICLEID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.setDocumentType(rs.getInt("DOCUMENTTYPE"));
				abean.setDocumentTitle(rs.getString("DOCUMENTTITLE"));
				abean.setDocumentPath(rs.getString("DOCUMENTPATH"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				Timestamp ts= rs.getTimestamp("DATEUPLOADED");
				if(ts != null){
					abean.setDateUploaded(new java.util.Date(rs.getTimestamp("DATEUPLOADED").getTime()));
				}
				abean.setTypeString(rs.getString("DD_TEXT"));
				
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
