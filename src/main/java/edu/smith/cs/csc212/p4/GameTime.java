package edu.smith.cs.csc212.p4;

/**
 * Class for time passing inside the game
 * @author Minh Phuong
 */
public class GameTime {
	
	/**
	 * Stores in-game time.
	 */
	private int startHour;

	/**
	 * How long the player has spent in game.
	 */
	private int duration;
	
	/**
	 * Create game clock.
	 * @param h - what time does the game start
	 */
	public GameTime(int h) {
		// Restrict start hour within 00:00 - 23:00.
		this.startHour = Math.abs(h%24);
		this.duration = 0;
	}
	
	/**
	 * Get the current hour in game.
	 * @return current time.
	 */
	public int getHour() {
		return (this.startHour + this.duration) % 24;
	}
	
	/**
	 * Get total time the player spent in game
	 * @return total time
	 */
	public int getDuration() {
		return this.duration;
	}
	
	/**
	 * Increase the current total time by 1.
	 */
	public void increaseHour() {
		this.duration += 1;
	}
	
	/**
	 * Decrease the current total time by 1.
	 */
	public void decreaseHour() {
		this.duration -= 1;
	}
	
	public void printHour() {
		System.out.println(">> TIME: [" + getHour() + ":00]");
	}
}