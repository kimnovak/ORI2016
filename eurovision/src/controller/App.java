package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Country;

public class App {

	public static void main(String[] args) {
		ArrayList<String> linesSemi = null;
		ArrayList<String> linesFinal = null;
		ArrayList<Country> countries = new ArrayList<Country>();
		linesSemi = loadData("Semi-Final.csv");
		linesFinal = loadData("Final.csv");
		loadCountries(countries, linesSemi.get(0));
		fillMatrix(countries, linesSemi, 14);
		fillMatrix(countries, linesFinal, 16);		
	}

	/**
	 * Method adds countries read from file to the countries list. It assigns a
	 * name and an index to the country
	 * 
	 * @param countries
	 *            ArrayList of countries
	 * @param string
	 *            First line from the file that contains names of countries
	 */
	private static void loadCountries(ArrayList<Country> countries,
			String firstLine) {
		int i = -1; // index of the country
		String[] tokens = firstLine.split(",");
		// [tokens.lenght - 14] number of countries, 12 presents number of years in 1998 - 2009
		for (int j = 14; j < tokens.length; j++) {
			countries.add(new Country(tokens[j].trim(), -1, -1, ++i,
					new int[tokens.length - 14][12]));
		}
	}

	/**
	 * Calculates how many points has each country given by year
	 * @param countries
	 * @param linesSemi
	 * @param begginingColumn
	 */
	private static void fillMatrix(ArrayList<Country> countries,
			ArrayList<String> lines, int begginingColumn) {
		int year = -1;
		for (String s : lines) {
			if (s.startsWith("Year")) {
				continue; // skip the first line because it only contains column
							// headers
			}
			String[] tokens = s.split(",");
			if(tokens[0].contains("1998")){
				year = 0;
			}else if(tokens[0].contains("1999")){
				year = 1;
			}else if(tokens[0].contains("2000")){
				year = 2;
			}else if(tokens[0].contains("2001")){
				year = 3;
			}else if(tokens[0].contains("2002")){
				year = 4;
			}else if(tokens[0].contains("2003")){
				year = 5;
			}else if(tokens[0].contains("2004")){
				year = 6;
			}else if(tokens[0].contains("2005")){
				year = 7;
			}else if(tokens[0].contains("2006")){
				year = 8;
			}else if(tokens[0].contains("2007")){
				year = 9;
			}else if(tokens[0].contains("2008")){
				year = 10;
			}else if(tokens[0].contains("2009")){
				year = 11;
			}
			if(year == -1){
				System.exit(-1);
			}
			// 1st column contains the year
			// 2nd name of the country
			// find the country in the list of countries and assign points
			for (Country c : countries) {
				
				if (c.getName().equals(tokens[1].trim())) {
					int[][] pointsReceived = c.getPointsReceived();
					
					for (int i = begginingColumn; i < tokens.length; i++) {
						if (tokens[i].isEmpty()) {
							pointsReceived[i - begginingColumn][year] += 0;
						} else {
							pointsReceived[i - begginingColumn][year] += Integer.parseInt(tokens[i]);
						}
					}
					c.setPointsReceived(pointsReceived);
				}
			}
		}

	}

	/**
	 * Method that reads the file line by line and adds each line to the retVal
	 * ArrayList of Strings and returns created ArrayList
	 * 
	 * @param fileName
	 *            name of the File that is being read
	 * @return returns an ArrayList of Strings containing lines from the file
	 */
	private static ArrayList<String> loadData(String fileName) {
		ArrayList<String> retVal = null;
		try {
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			retVal = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				retVal.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}

}
