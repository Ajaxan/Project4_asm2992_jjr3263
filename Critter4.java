package assignment4;

public class Critter4  extends Critter {
	
	/**
	 * This critter is essentially a rock. It is nearly unbeatable as it has a constant energy of ten thousand.
	 * Also, it doesn't move or reproduce so if for some reason all rocks do get beaten, they will not come back.
	 */
	private static int fights = 0;
	@Override
	public String toString() { return "4"; }
	
	public Critter4() {
		this.setEnergy(10000);
	}
	
	public boolean fight(String critter) {
		fights++;
		return true;
	}

	@Override
	public void doTimeStep() {
		this.setEnergy(10000);
		/* this critter is literally a rock */
	}

	public static void runStats(java.util.List<Critter> craigs) {

		System.out.print("" + craigs.size() + " total Rocks    ");
		System.out.print("" + fights + " total creatures hit rocks.    ");
		System.out.println();
	}

}
