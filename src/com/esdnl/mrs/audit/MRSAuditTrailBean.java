package com.esdnl.mrs.audit;

import com.awsd.security.User;
import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.mrs.CapitalType;
import com.esdnl.mrs.RequestComment;

public class MRSAuditTrailBean extends ApplicationObjectAuditBean {

	public static final ApplicationConstant APPLICATION = ApplicationConstant.MRS;

	public MRSAuditTrailBean(User usr, ActionTypeConstant action_type, CapitalType ct) {

		super();

		super.setActionType(action_type);

		if (action_type.equals(ActionTypeConstant.CREATE))
			super.setAction("CREATED - " + ct.getCapitalTypeID());
		else if (action_type.equals(ActionTypeConstant.DELETE))
			super.setAction("DELETED - " + ct.getCapitalTypeID());

		super.setApplication(MRSAuditTrailBean.APPLICATION);
		super.setObjectId(ct.getCapitalTypeID());
		super.setObjectType(ct.getClass().toString());
		super.setWho(usr.getLotusUserFullName());

		super.saveBean();

	}

	public MRSAuditTrailBean(User usr, ActionTypeConstant action_type, RequestComment comment) {

		super();

		super.setActionType(action_type);

		super.setAction(comment.getComment());
		super.setApplication(MRSAuditTrailBean.APPLICATION);
		super.setObjectId(Integer.toString(comment.getCommentID()));
		super.setObjectType(comment.getClass().toString());
		super.setWho(usr.getLotusUserFullName());

		super.saveBean();

	}

}
