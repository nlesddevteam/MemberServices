package com.nlesd.payroll;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class FundsTransferFile {

	public static void main(String[] args) {

		ArrayList<String> lines = new ArrayList<String>();

		try {
			String line = "";
			String[] parts = null;

			BufferedReader br = new BufferedReader(new FileReader("c:/subs/Stud_Asst_Upload.csv"));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();

			PrintWriter pw = new PrintWriter(new FileWriter("c:/subs/stud_asst_eft_01.txt"));
			//line 1
			line = lines.get(0).replace(",", "").trim();
			pw.println(line);

			for (int i = 1; i < lines.size() - 2; i++) {
				parts = lines.get(i).split(",");

				pw.println(formatLine(parts));
			}

			pw.println(lines.get(lines.size() - 2).replace(",", ""));
			pw.println(lines.get(lines.size() - 1).replace(",", ""));

			pw.close();
		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String formatLine(String[] parts) {

		String line = "";
		int ln = Integer.parseInt(parts[0]);

		if (ln == 1) {
			line = parts[0] + parts[1] + StringUtils.rightPad(parts[2], 30) + parts[3];
		}
		else if (ln == 2) {
			line = StringUtils.rightPad(parts[0], 71) + StringUtils.rightPad("N", 9);
		}
		else if (ln % 2 != 0) { // odd lines
			line = parts[0] + parts[1] + " " + parts[2].substring(1) + "0 "
					+ StringUtils.rightPad(parts[3] + parts[4] + parts[5], 28) + parts[6];
		}
		else if (ln % 2 == 0) {
			line = parts[0] + parts[1] + StringUtils.rightPad(parts[2], 30) + StringUtils.rightPad(parts[3].trim(), 28)
					+ StringUtils.rightPad("N", 9);
		}

		System.out.println(line);

		return line;
	}
}
