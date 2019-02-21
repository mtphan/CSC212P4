package edu.smith.cs.csc212.p4;

import java.util.Objects;

/**
 * This class represents an exit from a Place to another Place.
 * @author jfoley
 *
 */
public class Exit {
	/**
	 * How do we describe this exit to a user, e.g., "A door with a spiderweb."
	 */
	protected String description;
	/**
	 * How do we identify the Place that this is going.
	 */
	protected String target;
	
	/**
	 * Printed when the exit is chosen. It's default is "".
	 */
	protected String triggerMessage;
	
	/**
	 * Create a new Exit.
	 * @param target - where it goes.
	 * @param description - how it looks.
	 */
	public Exit(String target, String description) {
		this.description = description;
		this.target = target;
		this.triggerMessage = "";
	}
	
	/**
	 * Create a new Exit with a trigger message.
	 * @param target - where it goes.
	 * @param description - how it looks.
	 * @param message - what is printed when it's chosen.
	 */
	public Exit(String target, String description, String message) {
		this.description = description;
		this.target = target;
		this.triggerMessage = message + "\n";
	}
	
	/**
	 * A getter for the description of this exit.
	 * @return how it looks.
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * A getter for the target place of this exit.
	 * @return where it goes.
	 */
	public String getTarget() {
		return this.target;
	}
	
	/**
	 * Getter for the trigger message of the exit/choice.
	 * @return the message printed when the exit is chosen.
	 */
	public String getTriggerMessage() {
		return this.triggerMessage;
	}
	
	/**
	 * Check whether exit is visible/secret. Overridden in some of its subclasses.
	 * @return always return false (Exit is always visible). To create a secret exit, use class {@link SecretExit}
	 */
	public boolean isSecret() {
		return false;
	}
	
	/**
	 * This method do nothing for normal exit. To create a secret exit, use class {@link SecretExit}
	 * @return always return false since the exit create by this class is never secret.
	 */
	public boolean search() {
		return false;
	}
	
	/**
	 * Make this debuggable when we print it for ourselves.
	 */
	public String toString() {
		return "Exit("+this.description+", "+this.target+")";
	}
	
	/**
	 * Make it so we can put this in a HashMap or HashSet.
	 */
	public int hashCode() {
		return Objects.hash(this.description, this.target);
	}
	
	/**
	 * This is a useful definition of being the same.
	 * @param other - another exit.
	 * @return if they go to the same place.
	 */
	public boolean goesToSamePlace(Exit other) {
		return this.target.equals(other.target);
	}
	
	/**
	 * The other half of hashCode that lets us put it in a HashMap or HashSet.
	 */
	public boolean equals(Object other) {
		if (other instanceof Exit) {
			Exit rhs = (Exit) other;
			return this.target.equals(rhs.target) && this.description.equals(rhs.description); 
		}
		return false;
	}

}
