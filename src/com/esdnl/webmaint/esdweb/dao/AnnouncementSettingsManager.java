package com.esdnl.webmaint.esdweb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.webmaint.esdweb.bean.AnnouncementSettingsBean;
import com.esdnl.webmaint.esdweb.bean.EsdWebException;
import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;

public class AnnouncementSettingsManager {

	public static AnnouncementSettingsBean getAnnouncementSettingsBean(AnnouncementTypeConstant msg_type)
			throws EsdWebException {

		AnnouncementSettingsBean settings = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.esd_web.get_settings(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, msg_type.getTypeID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				settings = createAnnouncementSettingsBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("AnnouncementSettingsBean getAnnouncementSettingsBean(AnnouncementTypeConstant msg_type): "
					+ e);
			throw new EsdWebException("Can not extract AnnouncementSettingsBean from DB.", e);
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

		return settings;
	}

	public static AnnouncementSettingsBean createAnnouncementSettingsBean(ResultSet rs) {

		AnnouncementSettingsBean abean = null;
		try {
			abean = new AnnouncementSettingsBean();

			abean.setSettingId(rs.getInt("setting_id"));
			abean.setType(AnnouncementTypeConstant.get(rs.getInt("type_id")));
			abean.setExportLimit(rs.getInt("export_limit"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}

}
