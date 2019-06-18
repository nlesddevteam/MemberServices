package com.esdnl.fund3.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.fund3.bean.ContactBean;
import com.esdnl.fund3.bean.Fund3Exception;
import com.nlesd.school.service.SchoolZoneService;
public class ContactManager {
	public static Integer addNewContact(ContactBean cb) {
		Connection con = null;
		CallableStatement stat = null;
		int id=0;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.add_fund3_contact(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.INTEGER);
			stat.setString(2,cb.getContactName());
			stat.setString(3, cb.getContactTitle());
			stat.setString(4, cb.getContactPhone());
			stat.setString(5, cb.getContactEmail());
			stat.setInt(6, cb.getContactZone().getZoneId());
			stat.setInt(7, cb.getIsActive());
			stat.execute();
			id=((CallableStatement) stat).getInt(1);
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("Integer addNewContact(ContactBean cb) " + e);
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
	public static Vector<ContactBean> getContacts() throws Fund3Exception {
		Vector<ContactBean> contacts = null;
		ContactBean contact = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			contacts= new Vector<ContactBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_contacts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				contact = createContactBean(rs);
				contacts.add(contact);
			}
		}
		catch (SQLException e) {
			System.err.println("ContactManager getContacts() " + e);
			throw new Fund3Exception("Can not extract contacts from DB: " + e);
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
		return contacts;
	}
	public static ContactBean getContactById(int id) throws Fund3Exception {
		ContactBean contact = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_fund3_contact_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				contact = createContactBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("ContactBean getContactById(int id) " + e);
			throw new Fund3Exception("Can not extract contact from DB: " + e);
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
		return contact;
	}
	public static void updateContact(ContactBean cb) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.update_fund3_contact(?,?,?,?,?,?,?); end;");
			stat.setString(1,cb.getContactName());
			stat.setString(2, cb.getContactTitle());
			stat.setString(3, cb.getContactPhone());
			stat.setString(4, cb.getContactEmail());
			stat.setInt(5, cb.getContactZone().getZoneId());
			stat.setInt(6, cb.getIsActive());
			stat.setInt(7, cb.getId());
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void updateNewContact(ContactBean cb)" + e);
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
	public static void deleteContact(Integer tid) {
		Connection con = null;
		CallableStatement stat = null;
		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);
			stat = con.prepareCall("begin awsd_user.fund3_pkg.delete_fund3_contact(?); end;");
			stat.setInt(1, tid);
			stat.execute();
			}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("void deleteContact(Integer tid) " + e);
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
	public static Vector<ContactBean> getActiveContacts() throws Fund3Exception {
		Vector<ContactBean> contacts = null;
		ContactBean contact = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		try {
			contacts= new Vector<ContactBean>(5);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.fund3_pkg.get_active_contacts; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()) {
				contact = createContactBean(rs);
				contacts.add(contact);
			}
		}
		catch (SQLException e) {
			System.err.println("Vector<ContactBean> getActiveContacts()  " + e);
			throw new Fund3Exception("Can not extract contacts from DB: " + e);
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
		return contacts;
	}	
	public static ContactBean createContactBean(ResultSet rs) {
		ContactBean abean = null;
		try {
			abean = new ContactBean();
			abean.setId(rs.getInt("ID"));
			abean.setContactName(rs.getString("CONTACT_NAME"));
			abean.setContactTitle(rs.getString("CONTACT_TITLE"));
			abean.setContactPhone(rs.getString("CONTACT_PHONE"));
			abean.setContactEmail(rs.getString("CONTACT_EMAIL"));
			abean.setContactZone(SchoolZoneService.getSchoolZoneBean(rs.getInt("CONTACT_ZONE")));
			abean.setIsActive(rs.getInt("IS_ACTIVE"));
		}
		catch (SQLException e) {
			abean = null;
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return abean;
	}
}