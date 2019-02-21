package edu.smith.cs.csc212.p4;

/**
 * Class for all locked exit in the game.
 * @author Minh Phuong
 */
public class LockedExit extends TwoStateExit {
	
	/**
	 * Whether the exit is locked or not. Start off as true.
	 */
	private boolean isLocked = true;
	
	/**
	 * Name of the appropriate key to open the door.
	 */
	private String key;

	/**
	 * Create a locked exit that changes its description and trigger message as soon as you walk through.
	 * All locked exit change its description (from locked to unlocked).
	 * @param target - where does it go to?
	 * @param description - what does it look like?
	 * @param message - The message that appear when the exit is used
	 * @param newDescription - what is the description at state 2?
	 * @param newMessage - Message at state 2?
	 * @param keyName - name of the item used to remove obstacle
	 */
	public LockedExit(String target, String description, String message, String newDescription, String newMessage, String keyName) {
		super(target, description, message, newDescription, newMessage);
		this.key = keyName;
	}

	/**
	 * Get the key name to open the door.
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Check if the door is locked or not
	 * @return true if door is locked.
	 */
	public boolean isLocked() {
		return this.isLocked;
	}
	
	/**
	 * Check whether or not the exit can still be update (only update once).
	 * @return true if the exit can be update to new message and trigger message. For locked exit, as long as the exit is locked, you can't update it.
	 */
	public boolean updatable() {
		return (!isLocked);
	}
	
	/**
	 * Try to unlock the door
	 * @param keyName - name of the item used to unlock the door.
	 * @return true if door is unlocked, false if not.
	 */
	public boolean unlock(String keyName) {
		if (keyName.contains(this.key)) {
			this.isLocked = false;
			return true;
		}
		return false;
	}

}