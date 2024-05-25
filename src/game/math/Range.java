package game.math;

public class Range<T> {
	public T start; 
	public T end; 
	
	public Range(T start, T end) {
		this.start = start;
		this.end = end;
	}
	
	public Range(T start) {
		this.start = start;
		this.end = start;
	}
}
