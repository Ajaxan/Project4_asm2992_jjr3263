/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static List<Critter> dead = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	protected void setEnergy(int x) {energy = x;}
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		this.setEnergy(this.getEnergy()-Params.walk_energy_cost);
		switch (direction) {
			case 0: {
				this.x_coord++; 
				break;
			}
			case 1: {
				this.x_coord++;
				this.y_coord--; 
				break;
			}
			case 2: {
				this.y_coord--;
				break;
			}
			case 3: {
				this.y_coord--;
				this.x_coord--;
				break;
			}
			case 4: {
				this.x_coord--;
				break;
			}
			case 5: {
				this.x_coord--;
				this.y_coord++;
				break;
			}
			case 6: {
				this.y_coord++;
				break;
			}
			case 7: {
				this.y_coord++;
				this.x_coord++;
				break;
			}
		}
		if(this.x_coord > Params.world_width) {
			this.x_coord-=Params.world_width;
		}
		if(this.x_coord < 0) {
			this.x_coord+=Params.world_width;
		}
		if(this.y_coord > Params.world_height) {
			this.y_coord-=Params.world_height;
		}
		if(this.y_coord < 0) {
			this.y_coord+=Params.world_height;
		}
	}
	
	protected final void run(int direction) {
		this.setEnergy(this.getEnergy()-Params.run_energy_cost);
		switch (direction) {
			case 0: {
				this.x_coord+=2; 
				break;
			}
			case 1: {
				this.x_coord+=2;
				this.y_coord-=2; 
				break;
			}
			case 2: {
				this.y_coord-=2;
				break;
			}
			case 3: {
				this.y_coord-=2;
				this.x_coord-=2;
				break;
			}
			case 4: {
				this.x_coord-=2;
				break;
			}
			case 5: {
				this.x_coord-=2;
				this.y_coord+=2;
				break;
			}
			case 6: {
				this.y_coord+=2;
				break;
			}
			case 7: {
				this.y_coord+=2;
				this.x_coord+=2;
				break;
			}
		}
		if(this.x_coord > Params.world_width) {
			this.x_coord-=Params.world_width;
		}
		if(this.x_coord < 0) {
			this.x_coord+=Params.world_width;
		}
		if(this.y_coord > Params.world_height) {
			this.y_coord-=Params.world_height;
		}
		if(this.y_coord < 0) {
			this.y_coord+=Params.world_height;
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.getEnergy() < Params.min_reproduce_energy || this.getEnergy() == 0) {
			return;
		}
		System.out.println("Baby Time: " + this.getEnergy() + " Energy");
		offspring.setEnergy(this.getEnergy()/2);
		this.setEnergy(this.getEnergy()-offspring.getEnergy());
		System.out.println("New Energy: " + this.getEnergy() + "\nOffspring Energy: " + offspring.getEnergy());
		switch (direction) {
			case 0: {
				offspring.x_coord++; 
				break;
			}
			case 1: {
				offspring.x_coord++;
				offspring.y_coord--; 
				break;
			}
			case 2: {
				offspring.y_coord--;
				break;
			}
			case 3: {
				offspring.y_coord--;
				offspring.x_coord--;
				break;
			}
			case 4: {
				offspring.x_coord--;
				break;
			}
			case 5: {
				offspring.x_coord--;
				offspring.y_coord++;
				break;
			}
			case 6: {
				offspring.y_coord++;
				break;
			}
			case 7: {
				offspring.y_coord++;
				offspring.x_coord++;
				break;
			}	
		}
		if(this.x_coord > Params.world_width) {
			this.x_coord-=Params.world_width;
		}
		if(this.x_coord < 0) {
			this.x_coord+=Params.world_width;
		}
		if(this.y_coord > Params.world_height) {
			this.y_coord-=Params.world_height;
		}
		if(this.y_coord < 0) {
			this.y_coord+=Params.world_height;
		}
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)	 * @param critter_class_name
	 * @throws InvalidCritterException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			Class<?> critterType= Class.forName("assignment4."+critter_class_name); //gets the name of the critter class
			Constructor<?> newConstructor = critterType.getConstructor();
			Object object = newConstructor.newInstance();
			Critter critter  = (Critter)object;
			critter.x_coord=getRandomInt(Params.world_width); //random x value
			critter.y_coord=getRandomInt(Params.world_height);//random y value
			critter.energy=Params.start_energy;
			population.add(critter);
		}
		catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		catch(IllegalAccessException e){
			throw new InvalidCritterException(critter_class_name);
		} 
		catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		//System.out.print("I got here");
		try{
			for (Critter c: population){
				//System.out.print("I got here too");
				if (Class.forName("assignment4."+critter_class_name).isInstance(c)) {
					//System.out.print("I even got here");
						result.add(c);
					}
			}
		}
		catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			System.out.println(crit.getEnergy());
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidCritterException {
		for(Critter c: population) {
			c.doTimeStep();
		}
		for(Critter c1: population) {
			for(Critter c2: population) {
				if(c1.x_coord == c2.x_coord && c1.y_coord == c2.y_coord && !(c1.equals(c2))) {
					if(c1 instanceof Algae) {
						dead.add(c1);
						c2.setEnergy(c2.getEnergy() + c1.getEnergy()/2);
					} else if(c2 instanceof Algae){
						dead.add(c2);
						c1.setEnergy(c1.getEnergy() + c2.getEnergy()/2);
					} else {
						System.out.println("A Fight has occured at (" + c1.x_coord + "," + c1.y_coord + ") -- " + c1.toString() + " vs " +c2.toString());
						
							int critter1Strength = 0;
							int critter2Strength = 0;
							if(c1.fight(c2.toString())) {
								if(c1.getEnergy() <= 0) {
									critter1Strength = 0;
								} else {
									critter1Strength = Critter.getRandomInt(c1.getEnergy());
								}
							} else {
								critter1Strength = 0;
							}
							if(c2.fight(c1.toString())) {
								if(c2.getEnergy() <= 0) {
									critter2Strength = 0;
								} else {
									critter2Strength = Critter.getRandomInt(c2.getEnergy());
								}
								
							} else {
								critter2Strength = 0;
							}
							if(c1.x_coord == c2.x_coord && c1.y_coord == c2.y_coord) {
								if(critter1Strength > critter2Strength) {
									System.out.println(c1.toString() + " Wins! (First Critter)");
									c2.setEnergy(0);
									dead.add(c2);
									c1.setEnergy(c1.getEnergy() + c2.getEnergy()/2);
								} else {
									System.out.println(c2.toString() + " Wins! (Second Critter)");
									dead.add(c1);
									c1.setEnergy(0);
									c2.setEnergy(c2.getEnergy() + c1.getEnergy()/2);
								}
							}
					}
				}
			}
		}
		System.out.println("Babies: "+ babies.toString());
		for(Critter c: babies) {
			population.add(c);
		}
		babies.clear();
		for(Critter c: population) {
			if(c.getEnergy() <= 0) {
				dead.add(c);
			}
		}
		//for(Critter c: dead) {
		//	System.out.println(c.toString());
		//}
		System.out.println("Population: "+population.toString());
		population.removeAll(dead);
		System.out.println("Population: "+population.toString());
		dead.clear();
		
		
		for(Critter c: population) {
			if(c instanceof Algae) {
				//System.out.print(c.toString() + "PhotoSynthezied!\n");
				c.setEnergy(c.getEnergy()+Params.photosynthesis_energy_amount);
			}
		}
		
		int makeAlgae = Params.refresh_algae_count;
		while(makeAlgae > 0) {
			Critter.makeCritter("Algae");
			makeAlgae--;
		}
	}
	
	public static void displayWorld() {
		System.out.print("+");
		for(int x=0;x<Params.world_width;x++){
			System.out.print("-");
		}
		System.out.print("+\n");
		
		for(int y=0; y<Params.world_height; y++){
			String set=" "; //create a set variable that we will later print
			System.out.print("|");
			for (int x=0;x<Params.world_width;x++){
				set = " ";
				for(Critter c: population){
					if(x==c.x_coord && y==c.y_coord){
						set=c.toString();
						//System.out.print(c.toString());
					}
				}
				System.out.print(set);
			}
			
			System.out.print("|\n");
		}
		System.out.print("+");
		for(int x=0;x<Params.world_width;x++){
			System.out.print("-");
		}
		System.out.print("+\n");
		
		
	
	}
}
