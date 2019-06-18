package com.esdnl.colaboration.bean;

public class DiscussionGroupBean {
	private int id;
	private DiscussionBean discussion;
	private String group_name;
	private GroupCommentBean[] comments;

	public DiscussionGroupBean(){
		this.id = 0;
		this.discussion = null;
		this.group_name = null;
		this.comments = null;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public DiscussionBean getDiscussion(){
		return this.discussion;
	}
	
	public void setDiscussion(DiscussionBean discussion){
		this.discussion = discussion;
	}
	
	public String getGroupName(){
		return this.group_name;
	}
	
	public void setGroupName(String group_name){
		this.group_name = group_name;
	}
	
	public GroupCommentBean[] getComments(){
		return this.comments;
	}
	
	public void setComments(GroupCommentBean[] comments){
		this.comments = comments;
	}
	
	public String toString(){
		return this.getGroupName();
	}
	                         
}
