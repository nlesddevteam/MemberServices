package com.awsd.travel.bean;

import com.awsd.personnel.Personnel;

public class Division {

	private int id;
	private String name;
	private Personnel assistantDirector;

	private DivisionTravelBudget budget;

	public Division() {

	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Personnel getAssistantDirector() {

		return assistantDirector;
	}

	public void setAssistantDirector(Personnel assistantDirector) {

		this.assistantDirector = assistantDirector;
	}

	public DivisionTravelBudget getBudget() {

		return budget;
	}

	public void setBudget(DivisionTravelBudget budget) {

		this.budget = budget;
	}

	public String toString() {

		return this.name;
	}

	public boolean equals(Division div) {

		return this.getId() == div.getId();
	}

}
