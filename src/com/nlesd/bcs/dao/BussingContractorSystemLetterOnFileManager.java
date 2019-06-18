package com.nlesd.bcs.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.esdnl.dao.DAOUtils;
import com.nlesd.bcs.bean.BussingContractorSystemLetterOnFileBean;
public class BussingContractorSystemLetterOnFileManager {
	public static BussingContractorSystemLetterOnFileBean addLetterOnFile(BussingContractorSystemLetterOnFileBean vbean) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.add_new_letter(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, vbean.getlDocument());
			stat.setString(3, vbean.getlName());
			stat.setString(4, vbean.getNotes());
			stat.setInt(5, vbean.getFkType());
			stat.setString(6, vbean.getlType());
			stat.setString(7, vbean.getAddedBy());
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
			System.err.println("BussingContractorSystemLetterOnFileBean addLetterOnFile(BussingContractorSystemLetterOnFileBean vbean): "
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
	public static ArrayList<BussingContractorSystemLetterOnFileBean> getLettersOnFile(Integer cid,String lettertype) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemLetterOnFileBean ebean = new BussingContractorSystemLetterOnFileBean();
		ArrayList<BussingContractorSystemLetterOnFileBean> list = new ArrayList<BussingContractorSystemLetterOnFileBean>();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_emp_letters(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.setString(3,lettertype);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemLetterOnFileBean(rs);
				list.add(ebean);
				
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("ArrayList<BussingContractorSystemLetterOnFileBean> getLettersOnFile(Integer cid,String lettertype):"
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
	public static boolean deleteLetterOnFile(Integer vid)  {

		Connection con = null;
		CallableStatement stat = null;
		boolean check=false;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.bcs_pkg.delete_letter_on_file(?); end;");
			stat.setInt(1, vid);
			stat.execute();
			check=true;
		}
		catch (SQLException e) {
			System.err.println("boolean deleteLetterOnFile(Integer vid):" + e);
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
	public static BussingContractorSystemLetterOnFileBean getLetterOnFileById(Integer cid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		BussingContractorSystemLetterOnFileBean ebean = new BussingContractorSystemLetterOnFileBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.bcs_pkg.get_letter_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, cid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createBussingContractorSystemLetterOnFileBean(rs);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("BussingContractorSystemLetterOnFileBean getLetterOnFileById(Integer cid):"
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
	public static BussingContractorSystemLetterOnFileBean createBussingContractorSystemLetterOnFileBean(ResultSet rs) {
		BussingContractorSystemLetterOnFileBean abean = null;
		try {
				abean = new BussingContractorSystemLetterOnFileBean();
				abean.setId(rs.getInt("ID"));
				abean.setlDocument(rs.getString("LDOCUMENT"));
				abean.setlName(rs.getString("LNAME"));
				abean.setNotes(rs.getString("NOTES"));
				abean.setDateAdded(new java.util.Date(rs.getTimestamp("DATEADDED").getTime()));
				abean.setFkType(rs.getInt("FKTYPE"));
				abean.setIsDeleted(rs.getString("ISDELETED"));
				abean.setlType(rs.getString("LTYPE"));
				abean.setAddedBy(rs.getString("ADDEDBY"));
		}catch (SQLException e) {
				abean = null;
		}
		return abean;
	}		
	
}
