package com.esdnl.tsdoc.bean;

import com.awsd.personnel.Personnel;
import com.esdnl.tsdoc.manager.TrusteeGroupPersonnelManager;

public class TrusteeGroupBean {

	private int groupId;
	private String groupName;

	public TrusteeGroupBean() {

		this(0, null);
	}

	public TrusteeGroupBean(int groupId, String groupName) {

		super();
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public int getGroupId() {

		return groupId;
	}

	public void setGroupId(int groupId) {

		this.groupId = groupId;
	}

	public String getGroupName() {

		return groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrusteeGroupBean other = (TrusteeGroupBean) obj;
		if (groupId != other.groupId)
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		}
		else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return getGroupName();
	}

	public Personnel[] getMembership() throws TSDocException {

		return TrusteeGroupPersonnelManager.getTrusteeGroupMembershipBean(this);
	}

}
