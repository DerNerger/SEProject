package main;

import java.util.ArrayList;
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
				if(population[i]>0 && population[i]<10)
					newMigration[i]+=1;
			}
		}
		target.setMigrations(newMigration);
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void simulateGrowth(Field field) {
		LandType landType = field.getArea().getLandType();
		simulateNewMigration(field);
		simulateProcreation(field);
		simulateResourceHandling(field, landType);
		simulateDying(field, landType);
		simulateTempreture(field, landType);
		simulateCollision(field);
	}
	
	//help methods----------------------------------------------------------------
	//help method to simulate the migration into this field
	private void simulateNewMigration(Field field) {
		int[] population = field.getPopulation();
		int[] migration = field.getMigrations();
		for (int i = 0; i < population.length; i++) {
			int mig = migration[i];
			//if(Math.random()*100 < species[i].getSocial())
			population[i]+=mig;
			migration[i]=0;
		}
	}
	
	//help method to simulate the procreation
	private void simulateProcreation(Field field) {
		int[] population = field.getPopulation();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			double newPop =  population[i] * 0.001*species[i].getProcreation();
			if(newPop<1 && newPop>=0.001) newPop = 1;
			population[i]+=newPop;
		}
	}
	
	//help method to simulate the resource-handling
	private void simulateResourceHandling(Field field, LandType landType) {
		int[] population = field.getPopulation();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			int resources = landType.getResources()*species[i].getIntelligence()*1000;
			int demand = species[i].getResourceDemand()*population[i];
			if(demand > resources){
				
				//not enough resources for all
				population[i] = resources / species[i].getResourceDemand();
				if(species[i].getSocial()<50)
					population[i] =  (int) ((1-(1/100.0 * (50-species[i].getSocial()))) * population[i]);
				
			}
		}
	}
	
	//help method to simulate the dying from natural enemies
	private void simulateDying(Field field, LandType landType) {
		int[] population = field.getPopulation();
		int enemies = landType.getNaturalEnemies();
		for (int i = 0; i < population.length; i++) {
			if(population[i]==0)continue;
			if(enemies > species[i].getAgility()){ //the species not is stronger?
				//DIE!
				population[i] *= Math.random();
			}
		}
	}
	
	//simulate the temperature
	private void simulateTempreture(Field field, LandType landType) {
		int[] population = field.getPopulation();
		int minTemp = landType.getMinTemp();
		int maxTemp = landType.getMaxTemp();
		for (int i = 0; i < population.length; i++) {
			int u = 0;
			int o = 0;
			
			if(species[i].getMinTemp() < minTemp)
				u = minTemp;
			else 
				u = species[i].getMinTemp();
			
			if(species[i].getMaxTemp() > maxTemp)
				o = maxTemp;
			else 
				o = species[i].getMaxTemp();
			
			float fak = (o-u) / (float)(maxTemp - minTemp);
			population[i]*=fak;
			
			if(!species[i].isWater() && landType.getFieldType()==FieldType.WATER)
				population[i]=0;
		}
	}
	
	//help method to simulate the collision
	private void simulateCollision(Field field){
		int[] speciesPop = field.getPopulation();
		int[] strength=new int[speciesPop.length];
		int max =0;
		for (int i = 0; i < speciesPop.length; i++) {
			//calculate the strength
			if(speciesPop[i]==0) continue;
			strength[i]=8*species[i].getStrength()+species[i].getIntelligence()+species[i].getSocial();
			//calcualte the killability
			if(strength[i]>strength[max]) max =i;
			
		}
		//kill!!
		for (int i= 0; i < speciesPop.length; i++) {
			if(i!=max) speciesPop[i]=0;
		}
		for(int i =0;i<speciesPop.length;i++){
			if(speciesPop[i]<0)speciesPop[i]=0;
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
		
		//split the areas
		ArrayList<Area> water = new ArrayList<>();
		ArrayList<Area> land = new ArrayList<>();
		for(Area area : areas){
			if(area.getLandType().getFieldType()==FieldType.WATER)
				water.add(area);
			else
				land.add(area);
		}
		if(water.size()<4 || land.size()<4)
			throw new RuntimeException("Es sind nicht genug areas zum spawn verfuegbar");
		
		//get spawnPoints
		ArrayList<Area> spawnAreas = new ArrayList<>();	
		Random rand = new Random();
		for(int i = 0; i< species.length; i++){
			if(species[i].isWater()){
				//spawn in water
				int nextRand = rand.nextInt(water.size()-1);
				while(spawnAreas.contains(water.get(nextRand)))
					nextRand = rand.nextInt(water.size()-1);
				spawnAreas.add(water.get(nextRand));
			} else {
				//spawn in land
				int nextRand = rand.nextInt(land.size()-1);
				while(spawnAreas.contains(land.get(nextRand)))
					nextRand = rand.nextInt(land.size()-1);
				spawnAreas.add(land.get(nextRand));
			}
		}
		
		//spawn the species
		for (int i = 0; i < species.length; i++) {
			Area spawnHere = spawnAreas.get(i);
			spawnHere.setItEasyFor(species[i]);
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
	 * {@inheritDoc}
	 * */
	public int[] generatePoints(long[] populationDiff) {
		int[] points = new int[populationDiff.length];
		for (int i = 0; i < points.length; i++) {
			if(populationDiff[i]<0) continue; //negative growth
			points[i] = (int) populationDiff[i] / 10;
		}
		return points;
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
		
		double minTempStdDeviation=3;
		double maxTempStdDeviation=3;
		double naturalEnemiesStdDeviation=5;
		double resourcesStdDeviation=4;
		
		
		//transform the random variable here(linear transformation)
		switch (type) {
		case LAND:
			minTemp=minTemp * minTempStdDeviation + 10;
			maxTemp=maxTemp * maxTempStdDeviation + 20;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 30; 
			resources = resources * resourcesStdDeviation + 30;
			break;
		case WATER:	
			minTemp=minTemp * minTempStdDeviation + 0;
			maxTemp=maxTemp * maxTempStdDeviation + 20;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 20; 
			resources = resources * resourcesStdDeviation + 40;
			break;
		case DESERT:		
			minTemp=minTemp * minTempStdDeviation + 30;
			maxTemp=maxTemp * maxTempStdDeviation + 60;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 10; 
			resources = resources * resourcesStdDeviation + 15;
			break;
		case JUNGLE:		
			minTemp=minTemp * minTempStdDeviation + 15;
			maxTemp=maxTemp * maxTempStdDeviation + 40;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 60; 
			resources = resources * resourcesStdDeviation + 80;
			break;
		case ICE:	
			minTemp=minTemp * minTempStdDeviation - 25;
			maxTemp=maxTemp * maxTempStdDeviation + 0;
			naturalEnemies = naturalEnemies * naturalEnemiesStdDeviation + 10; 
			resources = resources * resourcesStdDeviation + 15;
			break;
		default:
			throw new RuntimeException("FieldType nicht gueltig");
		}
		if(minTemp>maxTemp) minTemp=maxTemp;
		if(naturalEnemies<0) naturalEnemies = 0;
		if(resources<0) resources = 0;
		return new LandType((int)minTemp, (int)maxTemp, type, (int)naturalEnemies, (int)resources);
	}
	
	public static void changeSpecies(Species s, PossibleUpdates update,boolean reverse){
		SkillLogic skillLogic = SkillLogic.getSkillLogic();
		SkillValue sv=skillLogic.getSkillValue(update, reverse);
		if(reverse) s.unskill(update);
		else s.skill(update);
		sv.processOnSpecies(s);
		
	}
	
	/**
	 * Erstellt eine Spezies mit vorgegebener standartkonfiguration.
	 * */
	public static Species getStandartSpecies(String name){
		int intelligence = 10;
		int agility = 10;
		int strength = 10;
		int social = 10;
		int procreation = 10;
		int minTemp = 3;
		int maxTemp = 21;
		int resourceDemand = 10;
		double movementChance = 0.1;
		int visibillity = 5;
		boolean water = false;
//		int intelligence = 10;
//		int agility = 90;
//		int strength = 10;
//		int social = 10;
//		int procreation = 60;
//		int minTemp = -4;
//		int maxTemp = 50;
//		int resourceDemand = 10;
//		double movementChance = 0.9;
//		int visibillity = 5;
//		boolean water = false;
		return new Species(name, intelligence, agility, strength, social, 
				procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water);
	}
}
