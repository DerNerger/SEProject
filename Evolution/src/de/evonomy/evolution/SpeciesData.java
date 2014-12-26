package de.evonomy.evolution;

import java.io.Serializable;
import java.util.LinkedList;

public class SpeciesData implements Serializable{
	private LinkedList<Long> population;
	private long max;
	private int number;
	public SpeciesData(int number) {
		this.number=number;
		max=0;
		population=new LinkedList<Long>();
	}
	public void setMax(int max){
		this.max=max;
	}
	public void addPopulation(long pop){
		if(pop>max) max=pop;
		population.add(pop);
	}
	public long getMax(){
		return max;
	}
	public LinkedList<Long> getPopulation(){
		return population;
	}
	public int getPlayerNumber(){
		return number;
	}
}
