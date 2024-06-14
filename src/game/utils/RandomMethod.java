package game.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomMethod {
	private static ThreadLocalRandom random;
	
	public static double nextDouble(double start, double end) {
		random = ThreadLocalRandom.current();
		
		if (start == end) return start;
		else return random.nextDouble(start, end);
	}
	
	public static float nextFloat(float start, float end) {
		random = ThreadLocalRandom.current();
		
		if (start == end) return start;
		else return random.nextFloat(start, end);
	}
	
	public static int nextInt(int start, int end) {
		random = ThreadLocalRandom.current();
		
		if (start == end) return start;
		else return random.nextInt(start, end);
	}
	
}
