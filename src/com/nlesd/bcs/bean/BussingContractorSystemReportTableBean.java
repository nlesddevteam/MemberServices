package com.nlesd.bcs.bean;

import java.io.Serializable;

public class BussingContractorSystemReportTableBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String tableName;
	private String tableTitle;
	private String isActive;
	private String shortName;
	private String joinTables;
	private String joinWhere;
	private int parentTable;
	private String joinSelect;
	private String joinHeader;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableTitle() {
		return tableTitle;
	}
	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getJoinTables() {
		return joinTables;
	}
	public void setJoinTables(String joinTables) {
		this.joinTables = joinTables;
	}
	public String getJoinWhere() {
		return joinWhere;
	}
	public void setJoinWhere(String joinWhere) {
		this.joinWhere = joinWhere;
	}
	public int getParentTable() {
		return parentTable;
	}
	public void setParentTable(int parentTable) {
		this.parentTable = parentTable;
	}
	public String getJoinSelect() {
		return joinSelect;
	}
	public void setJoinSelect(String joinSelect) {
		this.joinSelect = joinSelect;
	}
	public String getJoinHeader() {
		return joinHeader;
	}
	public void setJoinHeader(String joinHeader) {
		this.joinHeader = joinHeader;
	}
	@Override
	public boolean equals(Object obj) {
		return (this.tableName.equals(((BussingContractorSystemReportTableBean) obj).tableName) && (this.tableName
				.equals(((BussingContractorSystemReportTableBean) obj).tableName)));
	}
}
