package com.esdnl.colaboration.bean;

import java.util.*;
import java.text.*;

import com.esdnl.servlet.FormElementFormat;

public class GroupCommentBean {

	private int id;
	private DiscussionGroupBean group;
	private String comment;
	private Date comment_date;
	
	public GroupCommentBean(){
		this.id = 0;
		this.group = null;
		this.comment = null;
		this.comment_date = null;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public DiscussionGroupBean getGroup(){
		return this.group;
	}
	
	public void setGroup(DiscussionGroupBean group){
		this.group = group;
	}
	
	public String getComment(){
		return this.comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public Date getCommentDate(){
		return this.comment_date;
	}
	
	public String getFormattedCommentDate(){
		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getCommentDate());
	}
	
	public void setCommentDate(Date comment_date){
		this.comment_date = comment_date;
	}
	
	public String toString(){
		return this.getComment();
	}
}
