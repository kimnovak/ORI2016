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
		ArrayList<Country> countries = null;
		linesSemi = loadData("Semi-Final.csv");
		linesFinal = loadData("Final.csv");
		fillMatrix(countries, linesSemi, 14);
		fillMatrix(countries, linesFinal, 17);
	}
	
	/**
	 * 
	 * @param countries
	 * @param linesSemi
	 * @param begginingColumn
	 */
	private static void fillMatrix(ArrayList<Country> countries,
			ArrayList<String> linesSemi, int begginingColumn) {
		
	}

	/**
	 * Method that reads the file line by line and adds each line to
	 * the retVal ArrayList of Strings and returns created ArrayList
	 * 
	 * @param fileName name of the File that is being read
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
