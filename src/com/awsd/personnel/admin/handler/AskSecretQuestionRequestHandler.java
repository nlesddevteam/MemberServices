package com.awsd.personnel.admin.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.personnel.SecretQuestion;
import com.awsd.personnel.SecretQuestionDB;
import com.awsd.security.User;
import com.awsd.servlet.LoginNotRequiredRequestHandler;

public class AskSecretQuestionRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		Personnel p = null;
		String path = "";
		String question = "", answer = "";
		SecretQuestion sq = null;

		System.err.println(request.getRequestURI() + "?" + request.getQueryString());

		if ((request.getParameter("op") != null) && request.getParameter("op").equals("ASK")) {
			if (request.getParameter("uid") == null) {
				request.setAttribute("msg", "User ID is required");
			}
			else {
				try {
					p = PersonnelDB.getPersonnel(request.getParameter("uid"));

					sq = SecretQuestionDB.getSecretQuestion(p);

					if (sq == null) {
						request.setAttribute("msg", "No secret question set for " + request.getParameter("uid"));
					}
					else {
						request.setAttribute("USERID", "" + p.getPersonnelID());
						request.setAttribute("SECRETQUESTION", sq);
					}
				}
				catch (PersonnelException e) {
					request.setAttribute("msg", "User ID could not be found.");
				}
				catch (SQLException e) {
					request.setAttribute("msg", "User ID could not be found.");
				}
			}

			path = "askSecretQuestion.jsp";
		}
		else if ((request.getParameter("op") != null) && request.getParameter("op").equals("CONFIRMED")) {

			if (request.getParameter("uid") == null) {
				request.setAttribute("msg", "UserID is required.");
				path = "askSecretQuestion.jsp";
			}
			else if (request.getParameter("answer") == null) {
				request.setAttribute("msg", "Answer is required.");
				path = "askSecretQuestion.jsp";
			}
			else {
				answer = request.getParameter("answer");
				try {
					p = PersonnelDB.getPersonnel(request.getParameter("uid"));

					sq = SecretQuestionDB.getSecretQuestion(p);

					if (sq == null) {
						request.setAttribute("msg", "No secret question set for " + request.getParameter("uid"));
						path = "askSecretQuestion.jsp";
					}
					else if (!sq.getAnswer().equals(answer)) {
						request.setAttribute("msg", "Your answer does not match the secret question answer on record.");
						path = "askSecretQuestion.jsp";
					}
					else {
						System.err.println("PASSED!!");
						session = request.getSession(true);
						session.setAttribute("usr", new User(p.getUserName(), p.getPassword()));
						path = "changePassword.jsp";
					}
				}
				catch (SQLException e) {
					request.setAttribute("msg", "Your answer does not match the secret question answer on record.");
				}
			}
		}

		return path;
	}
}