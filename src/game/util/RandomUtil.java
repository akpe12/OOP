package game.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
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
}
