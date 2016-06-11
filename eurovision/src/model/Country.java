package model;

/**
 * Class that models countries that compete in eurovision. The idea is to assign
 * index to every country so that each country can hold information how many
 * points has other country presented it with each year. Example: Albania has
 * index 0 so if you look up Montenegro and get pointsReceived[0][i] you'll get
 * how many points has Albania given to Montenegro and index i would be in which
 * year (starting from 1998 i = 0 ending with 2009 i = 12
 * 
 * @author Novak
 *
 */
public class Country {
	private String name;
	private int rank;
	private int numberOfNeighbours;
	private int index;
	private int[][] pointsReceived;
	public Country() {
		super();
	}
	public Country(String name, int rank, int numberOfNeighbours, int index,
			int[][] pointsReceived) {
		super();
		this.name = name;
		this.rank = rank;
		this.numberOfNeighbours = numberOfNeighbours;
		this.index = index;
		this.pointsReceived = pointsReceived;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getNumberOfNeighbours() {
		return numberOfNeighbours;
	}
	public void setNumberOfNeighbours(int numberOfNeighbours) {
		this.numberOfNeighbours = numberOfNeighbours;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int[][] getPointsReceived() {
		return pointsReceived;
	}
	public void setPointsReceived(int[][] pointsReceived) {
		this.pointsReceived = pointsReceived;
	}
	
	
	
}
