package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Joshua Rothfus
 * jjr3263
 * 16445
 * Adi Miller
 * asm2992
 * 16480
 * Slip days used: <0>
 * Fall 2016
 */
//this critter makes twice the amount of babies at 3 times the rate of Craig

public class Critter1 extends Critter{

	@Override
	public String toString() { return "1"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter1() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 50) {
			Critter1 child = new Critter1();
			Critter1 child2= new Critter1();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
				child2.genes[k]=this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			while (child2.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child2.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child2.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
			reproduce(child2, Critter.getRandomInt(8)); //create a twin to each child born
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static void runStats(java.util.List<Critter> critter1s) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : critter1s) {
			Critter1 c = (Critter1) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + critter1s.size() + " total Critter1    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * critter1s.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * critter1s.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * critter1s.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * critter1s.size()) + "% left   ");
		System.out.println();
	}
}
