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
		loadRegion(countries, linesSemi, linesFinal);
		fillMatrix(countries, linesSemi, 14);
		fillMatrix(countries, linesFinal, 16);
		findNeighbours(countries);
		addNeighbours(countries);
		//38 scg 37 srb 29 cg
		System.out.println(countries.get(37).getIndexOfNeighbours());
		for(Country c: countries){
			System.out.println(c.getName() + " -- " + c.getRegion());
		}
		
	}
	/**
	 * For each country check which country from the list belongs to the same region
	 * If they belong to the same region add them as neighbours.
	 * @param countries
	 */
	private static void addNeighbours(ArrayList<Country> countries) {
		for(Country c: countries){
			for(Country neighbour: countries){
				if((c.getRegion()).equals(neighbour.getRegion())){
					if(c.getIndex() != neighbour.getIndex()){ //country cannot be its own neighbour
						if(!c.getIndexOfNeighbours().contains(neighbour.getIndex())){
							//Exception is Serbia and Montenegro which shouldn't be counted as neighbour to Serbia or Montenegro
							if(c.getName().equals("Serbia") || c.getName().equals("Montenegro")){
								if(!neighbour.getName().equals("Serbia & Montenegro")){
									ArrayList<Integer> neighbourIndex = c.getIndexOfNeighbours();
									neighbourIndex.add(neighbour.getIndex());
									c.setIndexOfNeighbours(neighbourIndex);
								}
							}else{
								ArrayList<Integer> neighbourIndex = c.getIndexOfNeighbours();
								neighbourIndex.add(neighbour.getIndex());
								c.setIndexOfNeighbours(neighbourIndex);
							}
						}
					}
				}
			}
		}
	}
	private static void loadRegion(ArrayList<Country> countries, ArrayList<String> linesSemi, ArrayList<String> linesFinal) {
		for(String line: linesSemi){
			String[] tokens = line.split(",");
			for(Country c: countries){
				if(c.getName().equals(tokens[1])){
					if(c.getRegion() == null){
						c.setRegion(tokens[2].trim());
					}
				}
			}
		}
		for(String line: linesFinal){
			String[] tokens = line.split(",");
			for(Country c: countries){
				if(c.getName().equals(tokens[1])){
					if(c.getRegion() == null){
						c.setRegion(tokens[2].trim());
					}
				}
			}
		}
		countries.get(38).setRegion("Former Yugoslavia");
	}
	/**
	 * If one country has given current country points for 6 or more years in the competition that country is counted as a "neighbour"
	 * @param countries
	 */
	private static void findNeighbours(ArrayList<Country> countries) {
		int[][] pointsMatrix = new int[countries.size()][countries.size()];
		for(Country c: countries){
			int yearPoints = 0; 
			/*
			 * yearPoints - represents number of years in which one country has given points to the current country
			 * Example:
			 * If current country is Albania, while iterating through all countries
			 * if a country voted for Albania yearPoints will be incremented.
			 * This is performed for all years of Eurovision contest.
			 */
			int[][] pointsReceived = c.getPointsReceived();
			for(int i = 0; i < pointsReceived.length; i++) {
				yearPoints = 0;
				for(int j = 0; j < 12; j++){
					if(pointsReceived[i][j] > 0){
						if(c.getIndex() == 38){
							pointsReceived[37][i]++;
							pointsReceived[29][i]++;
						}
						yearPoints++;
					}
					
				}
				pointsMatrix[c.getIndex()][i] = yearPoints;
			}
		}
		for(int i = 0; i < countries.size(); i++){
			for(int j = 0; j < countries.size(); j++){
				if(pointsMatrix[i][j] > 5){
					ArrayList<Integer> list = countries.get(i).getIndexOfNeighbours();
					if(!list.contains(j)){
						list.add(j);
						countries.get(i).setIndexOfNeighbours(list);
					}
				}
			}
		}
	
	}

	/**
	 * Adds countries read from file to the countries list, assigning each country a
	 * name and an index.
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
					new int[tokens.length - 14][12], new ArrayList<Integer>(), null));
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
	 * Reads the file line by line and adds each line to the retVal
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
