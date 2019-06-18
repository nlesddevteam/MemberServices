<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="H1N1-ADMIN-VIEW,H1N1-PRINCIPAL-VIEW">
	<esd:PermissionRedirect permissions="H1N1-ADMIN-VIEW" page="adminView.html" />
	<esd:PermissionRedirect permissions="H1N1-PRINCIPAL-VIEW" page="principalView.html" />
</esd:SecurityCheck>
