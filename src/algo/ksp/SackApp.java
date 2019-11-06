package algo.ksp;

import java.util.LinkedList;
import java.util.Random;

public class SackApp {

	public static void main(String[] args) {
		final int numOfPlunder = 5;
		Sack[] jacksPlunder = generatePlunder(numOfPlunder);
		@SuppressWarnings("unchecked")
		LinkedList<Sack>[] sortedPlunder = new LinkedList[Grain.values().length];
		for (int i = 0; i < Grain.values().length; i++) {
			sortedPlunder[i] = new LinkedList<Sack>();
		}
		for (int i = 0; i < numOfPlunder; i++) {
			sortedPlunder[jacksPlunder[i].getType().ordinal()].add(jacksPlunder[i]);
		}
		for (int i = 0; i < Grain.values().length; i++) {
			double totalPounds = 0;
			for (Sack sk : sortedPlunder[i]) {
				totalPounds += sk.getWeight();
			}
			System.out.println("Jack plundered " + totalPounds + " pounds of " + Grain.values()[i]);
		}
	}

	public static Sack[] generatePlunder(int howMany) {
		Random generator = new Random();
		Sack grain[] = new Sack[howMany];
		for (int i = 0; i < howMany; i++) {
			grain[i] = new Sack (
			Grain.values()[generator.nextInt(Grain.values().length)],
			generator.nextDouble() * 100 );
		}
		return grain ;
	}
}
