package edu.smith.cs.csc212.p4;

/**
 * @author Minh Phuong
 * Exit that can only be found using the search method.
 */
public class SecretExit extends Exit {
	/**
	 * Whether or not the exit is hidden.
	 */
	private boolean hidden;
	
	/**
	 * Create a secret exit that only appear when using the search command.
	 * @param target - Where does it go to?
	 * @param description - What does it look like?
	 */
	public SecretExit(String target, String description) {
		super(target, description);
		this.hidden = true;
	}

	/**
	 * Create a secret exit that only appear when using the search command.
	 * @param target - Where does it go to
	 * @param description - What does it look like?
	 * @param message - What does it say when you open it?
	 */
	public SecretExit(String target, String description, String message) {
		super(target, description, message);
		this.hidden = true;
	}
	
	/**
	 * Check whether exit is visible/secret.
	 * @return true if exit is hidden.
	 */
	@Override
	public boolean isSecret() {
		return this.hidden;
	}
	
	/**
	 * Change secret state/hidden state of the exit to false.
	 * @return true if a secret exit is found.
	 */
	@Override
	public boolean search() {
		if (this.hidden) {
			this.hidden = false;
			return true;
		}
		return false;
	}
}