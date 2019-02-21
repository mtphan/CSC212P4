package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This represents a place in our text adventure.
 * @author jfoley
 *
 */
public class Place {
	/**
	 * This is a list of places we can get to from this place.
	 */
	private List<Exit> exits;
	/**
	 * This is the identifier of the place.
	 */
	private String id;
	/**
	 * What to tell the user about this place.
	 */
	private String description;
	/**
	 * Whether reaching this place ends the game.
	 */
	private boolean terminal;
	
	/**
	 * List of item in this place.
	 */
	private List<String> items;
	
	/**
	 * Internal only constructor for Place. Use {@link #create(String, String)} or {@link #terminal(String, String)} instead.
	 * @param id - the internal id of this place.
	 * @param description - the user-facing description of the place.
	 * @param terminal - whether this place ends the game.
	 */
	private Place(String id, String description, boolean terminal) {
		this.id = id;
		this.description = description;
		this.exits = new ArrayList<>();
		this.terminal = terminal;
		this.items = new ArrayList<>();
	}
	
	/**
	 * Create an exit for the user to navigate to another Place.
	 * @param exit - the description and target of the other Place.
	 */
	public void addExit(Exit exit) {
		this.exits.add(exit);
	}
	
	/**
	 * Add a single item to items list.
	 * @param i - name of item.
	 */
	public void addItem(String i) {
		this.items.add(i);
	}
	
	/**
	 * Add an array of items to items list.
	 * @param i - An ARRAY of item to add.
	 */
	public void addItem(String[] i) {
		this.items.addAll(Arrays.asList(i));
	}
	
	/**
	 * Add items using list. I never use it but I guess it would come in handy eventually.
	 * @param i - list of item to add.
	 */
	public void addItem(List<String> i) {
		this.items.addAll(i);
	}
	
	/**
	 * Remove all items from room.
	 */
	public void removeItems() {
		this.items.clear();
	}
	
	/**
	 * Find list of items in this room.
	 * @return list of items in the current room.
	 */
	public List<String> getItems() {
		return Collections.unmodifiableList(items);
	}
	
	/**
	 * For gameplay, whether this place ends the game.
	 * @return true if this is the end.
	 */
	public boolean isTerminalState() {
		return this.terminal;
	}
	
	/**
	 * The internal id of this place, for referring to it in {@link Exit} objects.
	 * @return the id.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * The narrative description of this place.
	 * @return what we show to a player about this place.
	 */
	public String getDescription() {
		return this.description;
	}
	
	public void printDescription() {
		// No items
		System.out.println(this.description);
		if (items.isEmpty()) {
			return;
		}
		
		// Items size = 1.
		System.out.print("Here you found ");
		if (items.size() == 1) {
			System.out.println(items.get(0) + ".");
			return;
		}
		
		// Items size > 1.
		for (String item : this.items) {
			if (items.indexOf(item) < items.size()-1) {
				System.out.print(item + ", ");
			} else {
				System.out.println("and " + item + ".");
			}
		}
	}
	
	/**
	 * Search through all exits and make secret exit visible.
	 * @return true if a secret exit is found.
	 */
	public boolean search() {
		boolean found = false;
		for (Exit e : this.exits) {
			// Exit.search() return true if it find some secret exit. Overridden in SecretExit.
			if (e.search()) {
				found = true;
			}
		}
		return found;
	}

	/**
	 * Get a view of the exits from this Place, for navigation.
	 * @return all the exits from this place, regardless of secrecy.
	 */
	public List<Exit> getAllExits() {
		return Collections.unmodifiableList(exits);
	}
	
	/**
	 * Get a view of the exits from this Place, for navigation.
	 * @return only exit that are not secret.
	 */
	public List<Exit> getVisibleExits() {
		List<Exit> visible = new ArrayList<Exit>();
		for (Exit exit : exits) {
			if (!exit.isSecret()) {
				visible.add(exit);
			}
		}
		return Collections.unmodifiableList(visible);
	}
	
	/**
	 * This is a terminal location (good or bad).
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is the description of the place.
	 * @return the Place object.
	 */
	public static Place terminal(String id, String description) {
		return new Place(id, description, true);
	}
	
	/**
	 * Create a place with an id and description.
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is what we show to the user.
	 * @return the new Place object (add exits to it).
	 */
	public static Place create(String id, String description) {
		return new Place(id, description, false);
	}
	
	/**
	 * Implements what we need to put Place in a HashSet or HashMap.
	 */
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/**
	 * Give a string for debugging what place is what.
	 */
	public String toString() {
		return "Place("+this.id+" with "+this.exits.size()+" exits.)";
	}
	
	/**
	 * Whether this is the same place as another.
	 */
	public boolean equals(Object other) {
		if (other instanceof Place) {
			return this.id.equals(((Place) other).id);
		}
		return false;
	}
	
}
