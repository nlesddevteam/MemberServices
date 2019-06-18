package com.esdnl.survey.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.survey.bean.SurveyException;
import com.esdnl.survey.bean.SurveyResponseAnswerBean;
import com.esdnl.survey.bean.SurveySectionQuestionBean;
import com.esdnl.util.StringUtils;

public class SurveyAnalysisManager {

	public static TreeMap<String, String> getStopWords() throws SurveyException {

		TreeMap<String, String> words = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			words = new TreeMap<String, String>();

			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.survey_pkg.get_stopwords; end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				words.put(rs.getString("word"), null);
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String, String> getStopWords(): " + e);
			throw new SurveyException("Can not extract SurveyBean from DB.", e);
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

		return words;
	}

	public static SortedSet<Entry<String, Integer>> getHistogram(SurveySectionQuestionBean question)
			throws SurveyException {

		TreeMap<String, Integer> histogram = new TreeMap<String, Integer>();

		TreeMap<String, String> stopwords = SurveyAnalysisManager.getStopWords();

		Pattern wordBreakPattern = Pattern.compile("[\\p{Punct}\\s}]");

		try {

			ArrayList<SurveyResponseAnswerBean> answers = SurveyResponseAnswerManager.getSurveyResponseAnswerBeanList(question);

			for (SurveyResponseAnswerBean answer : answers) {
				if (!StringUtils.isEmpty(answer.getBody())) {
					String[] words = wordBreakPattern.split(answer.getBody().trim().toLowerCase());
					for (int i = 0; i < words.length; i++) {
						if (StringUtils.isEmpty(words[i]) || stopwords.containsKey(words[i]) || (words[i].length() <= 3)
								|| (words[i].charAt(0) < 97) || (words[i].charAt(0) > 122))
							continue;

						if (histogram.containsKey(words[i]))
							histogram.put(words[i], new Integer(histogram.get(words[i]).intValue() + 1));
						else
							histogram.put(words[i], new Integer(1));

						if ((i + 1) < words.length) {
							if (!StringUtils.isEmpty(words[i + 1]) && !stopwords.containsKey(words[i + 1])
									&& (words[i + 1].length() > 3) && (words[i + 1].charAt(0) >= 97) && (words[i + 1].charAt(0) <= 122)) {
								if (histogram.containsKey(words[i] + " " + words[i + 1]))
									histogram.put(words[i] + " " + words[i + 1],
											new Integer(histogram.get(words[i] + " " + words[i + 1]).intValue() + 1));
								else
									histogram.put(words[i] + " " + words[i + 1], new Integer(1));
							}
						}
					}
				}
			}

		}
		catch (SurveyException e) {
			System.err.println("TreeMap<String, Integer> getHistogram(SurveySectionQuestionBean question) " + e);
			throw e;
		}

		return entriesSortedByValues(histogram);
	}

	private static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {

			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {

				return -1 * e1.getValue().compareTo(e2.getValue());
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
}
