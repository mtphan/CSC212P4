package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		GameWorld game = new PalatialPalace();
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();
		
		List<String> inventory = new ArrayList<>();
		
		// Initialize game clock.
		GameTime clock = new GameTime(ThreadLocalRandom.current().nextInt(24));

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(place);
			here.printDescription();
			
			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}
			
			clock.printHour();
			
			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();
			
			for (int i=0; i<exits.size(); i++) {
			    Exit e = exits.get(i);
				System.out.println(" ["+i+"] " + e.getDescription());
			}

			// Figure out what the user wants to do.
			List<String> words = input.getUserWords(">");
			if (words.size() == 0) {
				System.out.println("Must type something!");
				continue;
			} else if (words.size() > 1) {
				System.out.println("Only give me 1 word at a time!");
				continue;
			}
			
			// Get the word they typed as lowercase, and no spaces.
			String action = words.get(0).toLowerCase().trim();
			
			if (action.contains("quit") || action.equals("q")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}
			
			// Allowing player to open their inventory
			if (action.contains("inventory") || action.equals("i") || action.equals("stuff")) {
				if (inventory.isEmpty()) {
					System.out.println("You have nothing on you. Use 'take' command when you see something you want to take.");
					continue;
				}
				System.out.println("You have:");
				for (String item : inventory) {
					System.out.println("      " + item);
				}
				continue;
			}
			
			clock.increaseHour();
			
			// Allowing player to search
			if (action.contains("search") || action.equals("s")) {
				System.out.println("You start to search around...");
				
				// If some thing is found, print etc...
				if (here.search()) {
					System.out.println("You have found a secret path!");
				} else {
					System.out.println("There is nothing suspicious here.");
				}
				continue;
			}
			
			// Allowing player to take items.
			if (action.contains("take") || action.equals("t")) {
				if (here.getItems().isEmpty()) {
					System.out.println("Uhm... There are nothing but debris here.");
					continue;
				}
				// Get user's choices
				List<String> userChoices = input.multipleChoicesConfirm("What do you want to take?", here.getItems());
				
				// If user didn't choose anything or typed garbage in
				if (userChoices.isEmpty()) {
					System.out.println("You decided to take nothing. You don't own this place after all.");
					continue;
				}
				System.out.println("Taken:");
				for (String i : userChoices) {
					System.out.println("      " + i);
				}
				// Add all stuff to inventory.
				inventory.addAll(userChoices);
				// Remove item from the place.
				here.removeItems(userChoices);
				
				continue;
			}
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				// Typo doesn't count as a move
				clock.decreaseHour();
				continue;
			}
			
			if (exitNum < 0 || exitNum > exits.size()-1) {
				System.out.println("I don't know what to do with that number!");
				// This doesn't increase the clock.
				clock.decreaseHour();
				continue;
			}
						
			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			
			// For all locked exits:
			if (exits.get(exitNum) instanceof LockedExit) {
				LockedExit locked = (LockedExit) exits.get(exitNum);
				// If locked...
				if (locked.isLocked()) {
					System.out.print(exits.get(exitNum).getTriggerMessage());
					// ...and inventory is empty.
					if (inventory.isEmpty()) {
						System.out.println("You don't have anything to remove the obstacle.");
						continue;
					}
					// The code only gets here if inventory is not empty...
					// Print list of choice to confirm user's decision.
					String itemUsed = input.singleChoiceConfirm("What are you going to remove the obstacle with?", inventory);
					if (locked.unlock(itemUsed)) {
						// Used the correct item.
						System.out.println("You try using the " + locked.getKey() + ". It works!");
						// Items can only be used successfully once.
						inventory.remove(itemUsed);
						locked.update();
					// Use wrong item.	
					} else {
						System.out.println("In desperation, you try to use " + itemUsed + ". It doesn't work at all.");
						continue;
					}
				}
			}

			// Print trigger message.
			System.out.print(exits.get(exitNum).getTriggerMessage());
			place = destination.getTarget();
			
			// Update any two state exits. Normal TwoStateExit doesn't have new place to update to.
			if (exits.get(exitNum) instanceof TwoStateExit) {
				if (((TwoStateExit) exits.get(exitNum)).updatable()) {
					((TwoStateExit) exits.get(exitNum)).update();
				}
			}
		}
		
		// Print total time
		System.out.println("YOU SPENT " + clock.getDuration() + " HOURS WANDERING IN THE GAME.");
		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println(">>> GAME OVER <<<");
	}

}
