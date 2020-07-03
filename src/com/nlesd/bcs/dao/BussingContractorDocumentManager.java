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
import com.nlesd.bcs.bean.BussingContractorDocumentBean;
public class BussingContractorDocumentManager {
	public static BussingContractorDocumentBean addBussingContractorDocument(BussingContractorDocumentBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_contractor_doc(?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, vbean.getContractorId());
			stat.setInt(3, vbean.getDocumentType());
			stat.setString(4, vbean.getDocumentTitle());
			stat.setString(5, vbean.getDocumentPath());
			if(vbean.getExpiryDate() == null){
				stat.setTimestamp(6, null);
			}else{
				stat.setTimestamp(6, new Timestamp(vbean.getExpiryDate().getTime()));
			}
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
			System.err.println("BussingContractorDocumentBean addBussingContractorDocument(BussingContractorDocumentBean vbean): "
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
	public static ArrayList<BussingContractorDocumentBean> getBussingContractorDocumentsById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorDocumentBean ebean = new BussingContractorDocumentBean();
		ArrayList<BussingContractorDocumentBean> list = new ArrayList<BussingContractorDocumentBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_contractor_documents(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorDocumentBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorDocumentBean> getBussingContractorDocumentsById(Integer cid):"
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
	public static boolean deleteContractorDocument(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_cont_doc(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteContractorDocument(Integer vid) " + e);
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
	public static BussingContractorDocumentBean createBussingContractorDocumentBean(ResultSet rs) {
		BussingContractorDocumentBean abean = null;
		try {
				abean = new BussingContractorDocumentBean();
				abean.setId(rs.getInt("ID"));
				abean.setContractorId(rs.getInt("CONTRACTORID"));
				abean.setDocumentType(rs.getInt("DOCUMENTTYPE"));
				abean.setDocumentTitle(rs.getString("DOCUMENTTITLE"));
				abean.setDocumentPath(rs.getString("DOCUMENTPATH"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				Timestamp ts= rs.getTimestamp("DATEUPLOADED");
				if(ts != null){
					abean.setDateUploaded(new java.util.Date(rs.getTimestamp("DATEUPLOADED").getTime()));
				}
				ts= rs.getTimestamp("EXPIRYDATE");
				if(ts != null){
					abean.setExpiryDate(new java.util.Date(rs.getTimestamp("EXPIRYDATE").getTime()));
				}
				abean.setTypeString(rs.getString("DD_TEXT"));
				//extra fields for automated warning messages
				try {
					//using one big query
					if(rs.getString("COMPANY") !=  null) {
						abean.setCompanyName(rs.getString("COMPANY"));
					}else {
						abean.setCompanyName(rs.getString("CFNAME") + " " + rs.getString("CFNAME"));
					}
					
					abean.setCompanyEmail(rs.getString("CEMAIL"));
					abean.setWarningNotes(rs.getString("WTYPE"));
				}catch(Exception enew) {
					//in case we missed a function that is not returning all data one query
					abean.setCompanyName("");
					abean.setCompanyEmail("");
					abean.setWarningNotes("No Warning Text");
				}
				
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
}
