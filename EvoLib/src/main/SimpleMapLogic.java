package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


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
		}
	}
	
	//help method to simulate the migration from source to target
	private void migrate(Field source, Field target){
		//simulate migration for all species
		int[] newMigration = target.getMigrations();
		int[] population = source.getPopulation();
		for (int i = 0; i < species.length; i++) {
			if(Math.random() < species[i].getMovementChance()){
				newMigration[i] += (int) (source.getPopulation()[i]*0.1);
				population[i] -= (int) (source.getPopulation()[i]*0.1);
			}
		}
		target.setMigrations(newMigration);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateGrowth(Field field) {
		//if(field.getPopulation()[0]!=0)
		//	System.out.println("blaaa");
		LandType landType = field.getArea().getLandType();
		simulateNewMigration(field);
		simulateProcreation(field);
		//simulateResourceHandling(field, landType);
		//simulateDying(field, landType);
		simulateCollision(field);
	}
	
	//help methods----------------------------------------------------------------
	//help method to simulate the migration into this field
	private void simulateNewMigration(Field field) {
		int[] population = field.getPopulation();
		int[] migration = field.getMigrations();
		for (int i = 0; i < population.length; i++) {
			int mig = migration[i];
			population[i]+=mig;
			migration[i]=0;
		}
	}
	
	//help method to simulate the procreation
	private void simulateProcreation(Field field) {
		int[] population = field.getPopulation();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			//population[i] *= species[i].getProcreation();
			population[i]+=1;
		}
	}
	
	//help method to simulate the resource-handling
	private void simulateResourceHandling(Field field, LandType landType) {
		int[] population = field.getPopulation();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			int resources = landType.getResources()*species[i].getIntelligence();
			int demand = species[i].getResourceDemand()*population[i];
			//population[i] = (population[i]*resources)/demand;
			//if(demand > resources)
			//	population[i]=demand/population[i];
		}
	}
	
	//help method to simulate the dying
	private void simulateDying(Field field, LandType landType) {
		int[] population = field.getPopulation();
		int enemies = landType.getNaturalEnemies();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			if(enemies > species[i].getStrength()*population[i]){ //the species not is stronger?
				//DIE!
				//population[i] /= 20;
				population[i] = 0;
			}
			if(population[i]>5000)
				population[i]=4999;
		}
	}
	
	//help method to simulate the collision
	private void simulateCollision(Field field){
		int[] speciesPop = field.getPopulation();
		int max = 0;
		for (int i = 1; i < speciesPop.length; i++) {
			if(speciesPop[i] > speciesPop[max])
				max = i;
		}
		for (int i = 0; i < speciesPop.length; i++) {
			if(i==max) continue;
			speciesPop[i]=-speciesPop[i];
		}
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
	
	/**
	 * generiert einen Landtype abhaengig vom uebergebenen FieldType
	 * Die Parameter sind zwar zufaellig, jedoch wird die Verteilung vom
	 * FieldType beeinflusst. 
	 * */
	public static LandType randomLandType(FieldType type){
		Random r = new Random();
		//FieldType parameters
		double minTemp=r.nextGaussian();
		double maxTemp=r.nextGaussian();
		double naturalEnemies=r.nextGaussian();
		double resources=r.nextGaussian();
		
		double minTempStdDeviation=4;
		double maxTempStdDeviation=4;
		double naturalEnemiesStdDeviation=2;
		double resourcesStdDeviation=2;
		
		
		//transform the random variable here(linear transformation)
		switch (type) {
		case LAND:
			minTemp=minTemp * minTempStdDeviation + 10;
			maxTemp=maxTemp * maxTempStdDeviation + 20;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 800; 
			resources = resources * resourcesStdDeviation + 10;
			break;
		case WATER:	
			minTemp=minTemp * minTempStdDeviation + 0;
			maxTemp=maxTemp * maxTempStdDeviation + 30;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 800; 
			resources = resources * resourcesStdDeviation + 10;
			break;
		case DESERT:		
			minTemp=minTemp * minTempStdDeviation + 40;
			maxTemp=maxTemp * maxTempStdDeviation + 70;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 800; 
			resources = resources * resourcesStdDeviation + 10;
			break;
		case JUNGLE:		
			minTemp=minTemp * minTempStdDeviation + 20;
			maxTemp=maxTemp * maxTempStdDeviation + 40;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 800; 
			resources = resources * resourcesStdDeviation + 10;
			break;
		case ICE:	
			minTemp=minTemp * minTempStdDeviation - 20;
			maxTemp=maxTemp * maxTempStdDeviation + 5;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 800; 
			resources = resources * resourcesStdDeviation + 10;
			break;
		default:
			throw new RuntimeException("FieldType nicht gueltig");
		}
		return new LandType((int)minTemp, (int)maxTemp, type, (int)naturalEnemies, (int)resources);
	}
	
	public static void changeSpecies(Species s, PossibleUpdates update){
		switch (update) {
		case LANDSPECIES:
			s.setWater(false);
			s.setSocial(s.getSocial()+3);
			break;
		case WATESPECIES:
			s.setWater(false);
			s.setSocial(s.getSocial()-3);
			break;
		case CARNIVORE:
			s.setStrength(s.getStrength()+5);
			s.setResourceDemand(s.getResourceDemand()+5);
			break;
		case HERBIVORE:
			s.setStrength(s.getStrength()-5);
			s.setResourceDemand(s.getResourceDemand()-5);
			break;
		case ENDOSKELETON:
			s.setStrength(s.getStrength()-5);
			s.setAgility(s.getAgility()+3);
			s.setMovementChance(s.getMovementChance()+0.05);
			break;
		case EXOSKELETON:
			s.setStrength(s.getStrength()+5);
			s.setAgility(s.getAgility()-3);
			s.setMovementChance(s.getMovementChance()-0.05);
			break;
		case RSTRATEGIST:
			s.setProcreation(s.getProcreation()+5);
			s.setStrength(s.getStrength()-5);
			break;
		case KSTRATEGIST:
			s.setProcreation(s.getProcreation()-5);
			s.setStrength(s.getStrength()+5);
			break;
		case THINFUR:
			s.setMinTemp(s.getMinTemp()+20);
			s.setMaxTemp(s.getMaxTemp()+20);
			break;
		case THICKFUR:
			s.setMinTemp(s.getMinTemp()-20);
			s.setMaxTemp(s.getMaxTemp()-20);
			break;
		default:
			throw new RuntimeException("Type is not valid");
		}
	}
	
	/**
	 * Erstellt eine Spezies mit vorgegebener standartkonfiguration.
	 * */
	public static Species getStandartSpecies(String name){
		int intelligence = 25;
		int agility = 25;
		int strength = 25;
		int social = 25;
		int procreation = 25;
		int minTemp = 10;
		int maxTemp = 20;
		int resourceDemand = 15;
		double movementChance = 0.1;
		int visibillity = 3;
		boolean water = false;
		return new Species(name, intelligence, agility, strength, social, 
				procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water);
	}
}
