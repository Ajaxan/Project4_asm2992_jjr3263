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
	
	
	/**
	 * This method handles what happens when a Critter decides to run/move 
	 * inside the world. Everything from energy costs to world wrap 
	 * boundaries are settled here.
	 * 
	 * @param direction The direction in which the Critter wishes to move.
	 */
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
	/**
	 * This method handles what happens when a Critter decides to run/move 
	 * inside the world. Everything from energy costs to world wrap 
	 * boundaries are settled here.
	 * 
	 * @param direction The direction in which the Critter wishes to move.
	 */
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
	
	/**
	 * This method handles the reproducing of a critter in the world. It also makes sure that any 
	 * coordinates wrap around the world appropriately.
	 * 
	 * @param offspring 	When a critter calls this method it must pass to us the offspring it created
	 * 						so that we can add it to the list of new babies and decide where to place it 
	 * 						in the world.
	 * @param direction		This parameter is used to determine where the parent wants its offspring 
	 * 						placed around it.
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.getEnergy() < Params.min_reproduce_energy || this.getEnergy() == 0) {
			return;
		}
		offspring.setEnergy(this.getEnergy()/2);
		this.setEnergy(this.getEnergy()-offspring.getEnergy());
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
			Class<?> critterType= Class.forName(myPackage+"."+critter_class_name); //gets the name of the critter class
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
		try{
			for (Critter c: population){
				if (Class.forName(myPackage+"."+critter_class_name).isInstance(c)) {
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
	/**
	 * This is the main event loop method. Whenever the simulation is 
	 * run this method is called. It handles all parts of the simulation.
	 * 
	 * @throws InvalidCritterException 	This exception is thrown when an 
	 * 									invalid critter class is called.
	 */
	public static void worldTimeStep() throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidCritterException {
		runTimeSteps();
		runEncounters();
		runRestCosts();
		runPhotosynthesis();
		runAddBabies();
		runRemoveDead();
	}
	
	/**
	 * This method handles each critters doTimeStep() method which determines 
	 * what a Critter will do inside the simulation. Often this includes 
	 * moving or reproducing.
	 */
	public static void runTimeSteps() {
		for(Critter c: population) {
			c.doTimeStep();
		}
	}
	/**
	 * This method is the most intense part of the code. It handles all interactions 
	 * between Critters. This includes handling fights, as in how each creature will 
	 * respond to them, and whether they are capable of responding how they want to.
	 */
	public static void runEncounters() {
		for(Critter c1: population) {
			for(Critter c2: population) {
				int cx1=c1.x_coord;
				int cy1=c1.y_coord;
				int cx2=c2.x_coord;
				int cy2=c2.y_coord;
				if(cx1 == cx2 && cy1 == cy2 && !(c1.equals(c2))) {
					boolean decision1=c1.fight(c2.toString());
					boolean decision2= c2.fight(c1.toString());
					int critter1Strength = 0;
					int critter2Strength = 0;
					if(!(isRunSafe(c1))){ 											//check if run is safe
						c1.x_coord=cx1; 											//if not change coordinates back to original and they must fight
						c1.y_coord=cy1;
					}
					if(!(isRunSafe(c2))){
						c2.x_coord=cx2;
						c2.y_coord=cy2;
					}
					
					if(c1.x_coord == c2.x_coord && c1.y_coord == c2.y_coord) { 		//Re-check to make sure no one ran.
						if(decision1) {												// Determine each critters fighting power
							if(c1.getEnergy() <= 0) {
								critter1Strength = 0;
							} else {
								critter1Strength = Critter.getRandomInt(c1.getEnergy());
							}
						} else {
							critter1Strength = 0;
						}
						if(decision2) {												// Determine each critters fighting power
							if(c2.getEnergy() <= 0) {
								critter2Strength = 0;
							} else {
								critter2Strength = Critter.getRandomInt(c2.getEnergy());
							}

						} else {
							critter2Strength = 0;
						}
						if(critter1Strength > critter2Strength) { 					//Fight!
							c1.setEnergy(c1.getEnergy() + c2.getEnergy()/2);
							c2.setEnergy(0);
							dead.add(c2);
							
						} else {
							c2.setEnergy(c2.getEnergy() + c1.getEnergy()/2);
							c1.setEnergy(0);
							dead.add(c1);
						}
					}
				}
			}
		}
	}
	/**
	 * This method simply applies the rest cost to each Critter during each WorldTimeStep.
	 */
	public static void runRestCosts() {
		for(Critter c: population) {
			c.setEnergy(c.getEnergy()-Params.rest_energy_cost);
		}
	}
	
	/**
	 * This method handles Algae in the simulation. It applies photosynthesis energy 
	 * gained to algae and also adds new algae to the world.
	 * 
	 * @throws InvalidCritterException 	If an incorrect String is used as a critter it 
	 * 									will throw this error and ask for new input, 
	 * 									ignoring old input.
	 */
	public static void runPhotosynthesis() throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidCritterException {
		for(Critter c: population) {
			if(c instanceof Algae) {
				c.setEnergy(c.getEnergy()+Params.photosynthesis_energy_amount);
			}
		}
		int makeAlgae = Params.refresh_algae_count;
		while(makeAlgae > 0) {
			Critter.makeCritter("Algae");
			makeAlgae--;
		}
	}
	/**
	 * This adds all babies made during world time step to the population list
	 * and then it clears the babies list for the next WorldTimeStep.
	 */
	public static void runAddBabies() {
		for(Critter c: babies) {
			population.add(c);
		}
		babies.clear();
	}
	/**
	 * This method finds all creatures with no energy left, declares them dead 
	 * and removes them from the population list.
	 */
	public static void runRemoveDead() {
		for(Critter c: population) {
			if(c.getEnergy() <= 0) {
				dead.add(c);
			}
		}
		population.removeAll(dead);
		dead.clear();
	}
	
	/**
	 * This method checks to make sure the space a critter has moved to is 
	 * not occupied currently.
	 * @param critter 	This is the critter we are checking to make sure is not 
	 * 					sharing a space with any other critters
	 * @return			We return true or false depending on whether the space 
	 * 					is occupied by another Critter
	 */
	public static boolean isRunSafe(Critter critter){
		for(Critter c: population){
			if (!(c==critter)){
				if (c.x_coord==critter.x_coord && c.y_coord==critter.y_coord){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * This method displays the world and each critter in it by using symbols 
	 * to represent all Critters.
	 */
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
