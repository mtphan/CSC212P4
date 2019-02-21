package edu.smith.cs.csc212.p4;

/**
 * @author Minh Phuong
 * Exit that displays different description and trigger message the second time.
 */
public class TwoStateExit extends Exit {
	
	/**
	 * Description at the second state.
	 */
	private String updateDescription;
	
	/**
	 * Trigger message at second state.
	 */
	private String updateMessage;
	
	/**
	 * Whether or not the exit has been updated to state 2.
	 */
	private boolean isUpdated;

	/**
	 * Create an exit that change its description as soon as you walk through.
	 * @param target - where does it go to?
	 * @param description - what does it look like?
	 * @param newDescription - what is the description at state 2?
	 */
	public TwoStateExit(String target, String description, String newDescription) {
		super(target, description);
		this.updateDescription = newDescription + "\n";
		this.updateMessage = this.triggerMessage;
	}

	/**
	 * Create an exit that changes its description and trigger message as soon as you walk through.
	 * @param target
	 * @param description
	 * @param message
	 */
	public TwoStateExit(String target, String description, String message, String newDescription, String newMessage) {
		super(target, description, message);
		this.updateDescription = newDescription;
		this.updateMessage = newMessage + "\n";
	}
	
	/**
	 * Update description and trigger message.
	 */
	public void update() {
		this.description = this.updateDescription;
		this.triggerMessage = this.updateMessage;
		this.isUpdated = true;
	}
	
	/**
	 * Check whether or not the exit can still be update (only update once).
	 * @return true if the exit can be update to new message and trigger message.
	 */
	public boolean updatable() {
		return (!isUpdated);
	}

}