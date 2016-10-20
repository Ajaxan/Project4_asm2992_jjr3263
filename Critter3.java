package assignment4;

public class Critter3 extends Critter {
	
	/**
	 * This critter is essentially a bunny. It reproduces very often, moves very quickly, and runs from all its fights.
	 * This critter uses the same system of genetics as Craigs to become much more effective at navigating the world around it.
	 */
	
	@Override
	public String toString() { return "3"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter3() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String critter) {
		if(critter.equalsIgnoreCase("C")) {
			this.run(Critter.getRandomInt(8));
			return false;
		}
		if(critter.equalsIgnoreCase("3")) {
			this.run(Critter.getRandomInt(8));
			return false;
		}
		if(critter.equalsIgnoreCase("4")) {
			this.run(Critter.getRandomInt(8));
			return false;
		}
		return true;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (getEnergy() > 50) {
			Critter3 child = new Critter3();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
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

	public static void runStats(java.util.List<Critter> critter3) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : critter3) {
			Critter3 c = (Critter3) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + critter3.size() + " total Craigs    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * critter3.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * critter3.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * critter3.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * critter3.size()) + "% left   ");
		System.out.println();
	}
}