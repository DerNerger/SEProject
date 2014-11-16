package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.management.RuntimeErrorException;

/**
 * @author Felix Kibellus
 * Einfache Klasse zum Testen der Populationsveraenderung
 * Mit der SimpleMapLogic koennen Abwanderung in andere Fields und das
 * Wachstum bzw die Kollisionen innerhalb eines Fields simuliert werden.
 * Achtung: Diese Klasse dient nur zu Testzwecken und wird spaeter durch
 * eine komplexere Klasse mit den richtigen Algorithmen ersetzt.
 * */
public class SimpleMapLogic implements IMapLogic {
	
	private static final int startPopulation = 100;
	
	private Species[] species;

	public SimpleMapLogic(Species[] species){
		this.species = species;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateMigrations(Field[][] fields) {
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if(i!=fields.length-1){
					//migrate to right
					migrate(fields[i][j], fields[i+1][j]);
				}
				if(j!=0){
					//migrate to top
					migrate(fields[i][j], fields[i][j-1]);
				}
				if(j!=fields[i].length-1){
					//migrate to bottom
					migrate(fields[i][j], fields[i][j+1]);
				}
				if(i!=0) {
					//migrate to left
					migrate(fields[i][j], fields[i-1][j]);
				}
			}
			int x = 0;
		}
	}
	
	//help method to simulate the migration from source to target
	private void migrate(Field source, Field target){
		//simulate migration for all species
		//TODO: primitive algorithm to test
		int[] newMigration = target.getMigrations();
		for (int i = 0; i < species.length; i++) {
			if(Math.random() < species[i].getMovementChance())
				newMigration[i] += (int) (source.getPopulation()[i]*0.1);
		}
		target.setMigrations(newMigration);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateGrowth(Field field) {
		LandType landType = field.getArea().getLandType();
		int[] newPolulation = new int[species.length];
		int[] dying = simulateDying(field, landType);
		int[] resources = simulateResourceHandling(field, landType);
		int[] procreations =  simulateProcreation(field);
		int[] collisions = simulateCollision(field);
		int[] migrations = simulateNewMigration(field);
		
		for (int i = 0; i < species.length; i++) {
			newPolulation[i] = field.getPopulation()[i];
			newPolulation[i] -= dying[i];
			newPolulation[i] += resources[i];
			newPolulation[i] += procreations[i];
			newPolulation[i] += collisions[i];
			newPolulation[i] += migrations[i];
		}
		field.setPopulation(newPolulation);
	}
	
	//help methods----------------------------------------------------------------
	//help method to simulate the dying
	private int[] simulateDying(Field field, LandType landType) {
		int[] dying = new int[species.length];
		int enemies = landType.getNaturalEnemies();
		
		for (int i = 0; i < dying.length; i++) {
			int strengthDifference = species[i].getStrength() - enemies;
			if(strengthDifference < 0)//the species is not stronger?//DIE!
				dying[i] = (int) (field.getPopulation()[i] * -strengthDifference/100.0);
			
			if( field.getPopulation()[i] > dying[i])
				dying[i] = field.getPopulation()[i];
		}
		return dying;
	}
	
	//help method to simulate the resource-handling
	private int[] simulateResourceHandling(Field field, LandType landType) {
		int[] growth = new int[species.length];
		for (int i = 0; i < growth.length; i++) {
			int demand = species[i].getResourceDemand();
			int efficiency = species[i].getIntelligence();
			int resources = landType.getResources();
			int population = field.getPopulation()[i];
			if(resources*efficiency > population * demand)
				growth[i]=(int) (population*=0.1);
			else
				growth[i]=(int) (population*=(-0.1));
		}
		return growth;
	}
	
	//help method to simulate the procreation
	private int[] simulateProcreation(Field field) {
		int[] growth = new int[species.length];
		//TODO: implement this algorithm
		return growth;
	}
	
	//help method to simulate the collision
	private int[] simulateCollision(Field field){
		int[] growth = new int[species.length];
		int[] speciesPop = field.getPopulation();
		int max = 0;
		for (int i = 0; i < speciesPop.length; i++) {
			if(speciesPop[i] > speciesPop[max])
				max = i;
		}
		for (int i = 0; i < speciesPop.length; i++) {
			if(i==max) continue;
			//growth[i]=-speciesPop[i];TODO: Here is a bug
		}
		
		return growth;
	}
	
	private int[] simulateNewMigration(Field field) {
		int[] migrations = new int[species.length];
		for (int i = 0; i < migrations.length; i++) {
			int mig = field.getMigrations()[i];
			if(mig!=0){
				migrations[i]+=mig;
			}
		}
		field.setMigrations(new int[species.length]);
		return migrations;
	}
	//end help methods--------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void spawnSpecies(Area[] areas) {
		if(areas.length < species.length)
			throw new RuntimeException("More species than areas");
		
		//get spawnPoints
		ArrayList<Integer> rands = new ArrayList<>();	
		Random rand = new Random();
		while(rands.size()<4){
			int nextRand = rand.nextInt(areas.length-1);
			if(!rands.contains(nextRand))
				rands.add(nextRand);
		}
		
		//spawn the species
		for (int i = 0; i < species.length; i++) {
			Area spawnHere = areas[rands.get(i)];
			spawnHere.spawnSpecies(i, this);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void spawnSpecies(Field[] fields, int playerNumber){
		Random rand = new Random();
		int randInt = rand.nextInt(fields.length);
		int[] population = new int[species.length];
		population[playerNumber] = startPopulation;
		fields[randInt].setPopulation(population);
		System.out.println("Spawned species");
	}
}
