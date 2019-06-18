package com.esdnl.personnel.v2.database.recognition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.v2.model.recognition.bean.RecognitionException;
import com.esdnl.personnel.v2.model.recognition.bean.TemplateBean;

public class TemplateManager {

	public static void addTemplateBean(TemplateBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.add_template(?, ?); end;");
			stat.setString(1, abean.getName());
			stat.setString(2, abean.getFilename());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAwardCategoryBean(AwardCategoryBean abean): " + e);
			throw new RecognitionException("Can not add AwardCategoryBean to DB.", e);
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

	public static void updateTemplateBean(TemplateBean abean) throws RecognitionException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_recognition.update_template(?, ?, ?); end;");
			stat.setInt(1, abean.getId());
			stat.setString(2, abean.getName());
			stat.setString(3, abean.getFilename());
			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAwardCategoryBean(AwardCategoryBean abean): " + e);
			throw new RecognitionException("Can not add AwardCategoryBean to DB.", e);
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

	public static TemplateBean[] getTemplateBeans() throws RecognitionException {

		Vector beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_templates; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createTemplateBean(rs));

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new RecognitionException("Can not extract RecognitionRequestBean from DB.", e);
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

		return ((TemplateBean[]) beans.toArray(new TemplateBean[0]));
	}

	public static TemplateBean getTemplateBean(int id) throws RecognitionException {

		TemplateBean bean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_recognition.get_template(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createTemplateBean(rs);

		}
		catch (SQLException e) {
			System.err.println("RecognitionRequestBean[] getRecognitionRequestBeans(String sin): " + e);
			throw new RecognitionException("Can not extract RecognitionRequestBean from DB.", e);
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

		return bean;
	}

	public static TemplateBean createTemplateBean(ResultSet rs) {

		TemplateBean abean = null;

		try {
			abean = new TemplateBean();

			abean.setFilename(rs.getString("TEMPLATE_FILENAME"));
			abean.setId(rs.getInt("TEMPLATE_ID"));
			abean.setName(rs.getString("TEMPLATE_NAME"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}