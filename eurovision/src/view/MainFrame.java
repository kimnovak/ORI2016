package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Country;
import model.LinearRegression;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Country> countries;
	public MainFrame(final ArrayList<Country> countries, final LinearRegression lr){
		this.countries = countries;
		setSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setTitle("Eurovision Prediction");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final PanelZemlje pz = new PanelZemlje(countries);
		panel.add(pz, BorderLayout.WEST);
		ImageIcon icon = new ImageIcon("images/img.png"); 
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		thumb.setSize(800, 200);
		thumb.setPreferredSize(new Dimension(800, 200));
		panel.add(thumb, BorderLayout.NORTH);
		
		JButton btn = new JButton("Predict");
		final JTextField text = new JTextField();
		final ArrayList<Country> countriesSemiFinal = new ArrayList<Country>();
		final ArrayList<Country> countriesSemiFinal2 = new ArrayList<Country>();
		final ArrayList<Country> countriesQualifed = new ArrayList<Country>();
		btn.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if(pz.getListRadioButton().get(0).isSelected()){
				  //prvo polufinale
				  for(int i = 0; i < pz.getListCheckBox().size(); i++) {
					  if(pz.getListCheckBox().get(i).isSelected()){
						  Country country = getCountryById(i);
						  countriesSemiFinal.add(country);
					  }
				  }
				  String s = "";
				  HashMap<Country, Integer[]> nON = new HashMap<Country, Integer[]>();
				  HashMap<Country, Double> map = new HashMap<Country, Double>();
				  for(Country c: countriesSemiFinal) {
					  Integer[] neighbours = new Integer[countriesSemiFinal.size()];
					  int i = 0;
					  for(Country neighbour: countriesSemiFinal) {
						
							  if(c.getIndexOfNeighbours().contains(neighbour.getIndex())){
								  neighbours[i] = neighbour.getIndex();
								  i++;
							  }
						  
					  }
					  nON.put(c, neighbours);
				  }
				  HashMap<Country, Integer> nONi = new HashMap<Country, Integer>();
				  for (Country c : nON.keySet()){
					  int brojac = 0;
					  for(Integer t : nON.get(c)){
						  if(t != null && t > 0){
							  brojac++;
						  }
					  }
					  nONi.put(c, brojac);
				  }
				  //umesto u string strpati ih za predikciju
				  for(Country c: nONi.keySet()) {
					  double p = lr.predict(nONi.get(c));
					  map.put(c, p);
					  s += c.getName() + " " + nONi.get(c) + ", " + "\n";
					  
				  }
				  ArrayList<Double> zaSort = new ArrayList<Double>();
				  
				  for(Country c: map.keySet()) { 
					  if(!c.getName().equals("Germany") && !c.getName().equals("United Kingdom") && !c.getName().equals("Spain")){
						  zaSort.add(map.get(c));
					  }
				  }
				  /*for(Double d: zaSort) {
					  Collections.sort(zaSort);
				  }*/
				  Collections.sort(zaSort);
				  for(Double d: zaSort){
					  System.out.println(d);
				  }
				  Double granica = zaSort.get(9);
				  int brojac = 0;
				  for(Country c: map.keySet()) {
					  if(map.get(c) <= granica) {
						  if(brojac < 10){
							  if(!c.getName().equals("Germany") && !c.getName().equals("United Kingdom") && !c.getName().equals("Spain")){
								  countriesQualifed.add(c);
								  brojac++;
							  }
						  }else{
							  break;
						  }
					  }
				  }
				  s += "---------------------- \n Kvalifikovani: \n";
				  
				  for(Country c: countriesQualifed) {
					  s += c.getName() + "\n";
				  }
				  text.setText(s);
			  }else if(pz.getListRadioButton().get(1).isSelected()){ 
				  //drugo polufinale
				  for(int i = 0; i < pz.getListCheckBox().size(); i++) {
					  if(pz.getListCheckBox().get(i).isSelected()){
						  Country country = getCountryById(i);
						  if(!countriesSemiFinal.contains(country))
							  countriesSemiFinal2.add(country);
					  }
				  }
				 
				  String s = "";
				  HashMap<Country, Integer[]> nON = new HashMap<Country, Integer[]>();
				  HashMap<Country, Double> map = new HashMap<Country, Double>();
				  for(Country c: countriesSemiFinal2) {
					  Integer[] neighbours = new Integer[countriesSemiFinal2.size()];
					  int i = 0;
					  for(Country neighbour: countriesSemiFinal2) {
						
							  if(c.getIndexOfNeighbours().contains(neighbour.getIndex())){
								  neighbours[i] = neighbour.getIndex();
								  i++;
							  }
						  
					  }
					  nON.put(c, neighbours);
				  }
				  HashMap<Country, Integer> nONi = new HashMap<Country, Integer>();
				  for (Country c : nON.keySet()){
					  int brojac = 0;
					  for(Integer t : nON.get(c)){
						  if(t != null && t > 0){
							  brojac++;
						  }
					  }
					  nONi.put(c, brojac);
				  }
				  //umesto u string strpati ih za predikciju
				  for(Country c: nONi.keySet()) {
					  double p = lr.predict(nONi.get(c));
					  map.put(c, p);
					  s += c.getName() + " " + nONi.get(c) + ", " + "\n";
					  
				  }
				  ArrayList<Double> zaSort = new ArrayList<Double>();
				  
				  for(Country c: map.keySet()) { 
					  if(!c.getName().equals("Germany") && !c.getName().equals("United Kingdom") && !c.getName().equals("Spain")){
						  zaSort.add(map.get(c));
					  }
				  }
				  /*for(Double d: zaSort) {
					  Collections.sort(zaSort);
				  }*/
				  Collections.sort(zaSort);
				  for(Double d: zaSort){
					  System.out.println(d);
				  }
				  Double granica = zaSort.get(9);
				  int brojac = 0;
				  for(Country c: map.keySet()) {
					  if(map.get(c) <= granica) {
						  if(brojac < 10){
							  if(!c.getName().equals("Germany") && !c.getName().equals("United Kingdom") && !c.getName().equals("Spain")){
								  countriesQualifed.add(c);
								  brojac++;
							  }
						  }else{
							  break;
						  }
					  }
				  }
				  s += "---------------------- \n Kvalifikovani: \n";
				  
				  for(Country c: countriesQualifed) {
					  s += c.getName() + "\n";
				  }
				  text.setText(s);
				  
			  }else{
				  //finale
				  //rangirati opet pozvati predict sve isto u sustini samo lista kvalifikovanih
				  String s = "";
				  HashMap<Country, Integer[]> nON = new HashMap<Country, Integer[]>();
				  HashMap<Country, Double> map = new HashMap<Country, Double>();
				  for(Country c: countriesQualifed) {
					  Integer[] neighbours = new Integer[countriesQualifed.size()];
					  int i = 0;
					  for(Country neighbour: countriesQualifed) {
						
							  if(c.getIndexOfNeighbours().contains(neighbour.getIndex())){
								  neighbours[i] = neighbour.getIndex();
								  i++;
							  }
						  
					  }
					  nON.put(c, neighbours);
				  }
				  HashMap<Country, Integer> nONi = new HashMap<Country, Integer>();
				  for (Country c : nON.keySet()){
					  int brojac = 0;
					  for(Integer t : nON.get(c)){
						  if(t != null && t > 0){
							  brojac++;
						  }
					  }
					  nONi.put(c, brojac);
				  }
				  //umesto u string strpati ih za predikciju
				  for(Country c: nONi.keySet()) {
					  double p = lr.predict(nONi.get(c));
					  map.put(c, p);
					  s += c.getName() + " " + nONi.get(c) + "\n";
					  
				  }
				  ArrayList<Double> zaSort = new ArrayList<Double>();
				  
				  for(Country c: map.keySet()) { 
					  
						  zaSort.add(map.get(c));
					 
				  }
				  
				
				  Double najbolji = Collections.min(zaSort);
				 
				  for(Country c: map.keySet()) {
					  if(map.get(c) == najbolji) {
						  s += "najbolji je: " + c.getName();
						  break;
					  }
				  }
				  s += "---------------------- \n";
				  
			/*	  for(Country c: countriesQualifed) {
					  s += c.getName() + "\n";
				  }*/
				  text.setText(s);
			  }
			  
		  }

		private Country getCountryById(int i) {
			Country retVal = null;
			for(Country c: countries) {
				if(c.getIndex() == i){
					retVal = c;
					break;
				}
			}
			return retVal;
		}
		});
		panel.add(btn, BorderLayout.SOUTH);
		panel.add(text, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(panel);
		this.add(scrollPane);
	}
}
