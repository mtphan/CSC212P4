package edu.smith.cs.csc212.p4;

import java.util.HashMap;
import java.util.Map;

/**
 * SpookyMansion, the game.
 * @author jfoley
 *
 */
public class PalatialPalace implements GameWorld {
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		return "mainDoor";
	}

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public PalatialPalace() {
		
		// Add areas
		Place mainDoor = insert(
				Place.create("mainDoor", "You are standing in front of the main entrance door of an old dilapidated castle.\n"
						+ "The door is twice as tall as you and there are two dragons carved on it. You have a feeling they are looking at you.\n"
						+ "You don't remember how you got here. Do you want to go in anyway?"));
		
		Place entranceHall = insert(
				Place.create("entranceHall", "You enter the entrance hall of the castle. It's huge!"));
		
		Place bedroom = insert(
				Place.create("bedroom", "This looks like the bedroom. There is absolutely nothing special about this room."));
		
		Place secondFloor = insert(
				Place.create("secondFloor", "You arrived at the second floor of the castle. It's even more old and dilapidated than you expected."));
		
		Place diningRoom = insert(
				Place.create("diningRoom", "This is the dining room. You hope the smell is just spoiled food."));
		
		Place kitchen = insert(
				Place.create("kitchen", "You are at what appears to be the kitchen of the castle.\n"
						+ "You try to convince yourself that the stains on the walls are just dust."));
		
		Place basement = insert(
				Place.create("basement", "This seems like the basement of the castle. The hole where you fell through provides a convenient light source."));
		
		Place balcony = insert(
				Place.create("balcony", "A balcony! The balcony looks towards the most beautiful garden you have ever seen.\n"
						+ "Looks like someone has been taking care of it."));
		
		Place ballroom = insert(
				Place.create("ballroom", "This must be a ballroom. There are red curtains and paintings on the walls."));
		
		Place dragon = insert(
				Place.create("dragon", "You are at a huge dungeon. It's dimly lit by scattered torches on the walls."));
		
		Place dragonQuestion = insert(
				Place.create("dragonQuestion", "Agree to let the dragon help you escape?"));
		
		Place death = insert(
				Place.terminal("death", "Too bad. You have died."));
		
		Place treasureRoom = insert(
				Place.terminal("treasureRoom", "You have found the treasure room. There are mountains of gold and exotic artifacts here.\n"
						+ "You haven't found the exit, but at least you are rich now?"));
		
		Place dragonEnd = insert(
				Place.terminal("dragonEnd", "The dragon carries you on its back and breaks you out of the castle. Both of you fly toward the sunset. How cinematic!"));
		
		// Add exits
		mainDoor.addExit(new Exit("entranceHall", "Let's do it!", "You muster up your courage and push the door open. As soon as you step through, the door seals shut behind you."));
		mainDoor.addExit(new Exit("death", "Nope, I'm getting out of here.",
				"You try to run as fast as you could away from the castle but got killed by the wilderness in the process.\n"
				+ "What else did you expect?"));
		
		entranceHall.addExit(new Exit("secondFloor", "There's a flight of stairs going up.",
				"You go up the stairs. It creaks loudly everytime you take a step."));
		entranceHall.addExit(new Exit("diningRoom", "There's a red door.", "You push the door open. The room behind greet you with the most rancid stench you have ever smelled in your life."));
		entranceHall.addExit(new Exit("bedroom", "There's a brown door. The door looks like it's about to collapsed.", "Dust and spiderwebs fall on you as you push the door open."));
		
		bedroom.addExit(new Exit("entranceHall", "Go back through the brown door.", "There is absolutely nothing special about the bedroom, so you decide to leave."));
		bedroom.addExit(new SecretExit("treasureRoom", "There's a trapdoor under the rug.", "As you make your way down, a sudden brilliant ray of golden light almost blinds you."));

		
		secondFloor.addExit(new Exit("entranceHall", "There's a flight of stairs going down.",
				"You decided to go back to the entrance hall. The stairs creak loudly everytime you take a step."));
		secondFloor.addExit(new Exit("ballroom", "Turn left.", "Behavioral research shows that people tend to turn left when lost in a maze.\n"
				+ "You are very predictable."));
		
		ballroom.addExit(new Exit("secondFloor", "Nothing to see here.", "You concluded that was the most boring ballroom you have ever been to your entire life."));
		
		secondFloor.addExit(new Exit("hallway0", "Turn right.", "Behavioral research shows that people tend to turn left when lost in a maze.\n"
				+ "Did you pick right because you are afraid I might take advantage of that and kill you on the left turn?"));
		
		diningRoom.addExit(new Exit("entranceHall", "There's that red door again.", "You can't bear the smell and have to leave."));
		diningRoom.addExit(new Exit("kitchen", "Another door ahead of you. This time it's green.",
				"Green door and red door in a room? Whoever designs this game has a really bad taste!"));
		
		kitchen.addExit(new Exit("diningRoom", "There's a green door.", "You pushed open the green door. You'd rather be in the smelly dining room."));
		kitchen.addExit(new TwoStateExit("basement", "Let's look around first...", "Did I tell you this castle is old and dilapilated? Well this castle is old and dilapidated.\n"
				+ "While looking around the floor underneath you breaks and you fall through.", "There's now a hole on the floor where you fell through. Jump down?",
				"A brave choice. The debris from your first fall cushions your landing."));
		
		basement.addExit(new LockedExit("dragon", "You have opened so many doors today. This door is, however, locked.", "Didn't I tell you it's locked?",
				"You have opened so many doors today, including this door!", "You push the door open...", "black key"));
		basement.addExit(new Exit("death", "Well, I guess I will rot here then.", "Wise choice. You rot in the basement."));
		basement.addExit(new SecretExit("entranceHall", "There's a switch on one of the wall.",
				"You flipped the switch and part of the wall slides open, creating a pathway. As you walk through, the wall closes behind you."));
		
		dragon.addExit(new LockedExit("dragonQuestion", "There's a huge dragon blocking your way. It doesn't notice you.", "You can't just shove the dragon aside you know.",
				"The dragon is still willing to help you.", "The dragon appreciates your gift very much. It decides to help you escape from the castle.", "rose"));
		dragon.addExit(new Exit("basement", "Go back. I don't trust dragons.", "You slowly retreat back to the basement."));
		
		dragonQuestion.addExit(new Exit("dragonEnd", "Yes"));
		dragonQuestion.addExit(new Exit("dragon", "No, I can do it myself.", "You are very confident in yourself."));
		
		int hallwayDepth = 3;
		int lastHallwayPart = hallwayDepth - 1;
		for (int i=0; i<hallwayDepth; i++) {
			Place hallwayPart = insert(Place.create("hallway"+i, "You take a leisure walk along the hallway. It's decorated with mirrors on both walls."));
			if (i == 0) {
				hallwayPart.addExit(new Exit("secondFloor", "Go left.", "You can't bear the anticipation of what's ahead and decided to turn back."));
			} else {
				hallwayPart.addExit(new Exit("hallway"+(i-1), "Go left.", "Too dark!"));
			}
			if (i != lastHallwayPart) {
				hallwayPart.addExit(new Exit("hallway"+(i+1), "Continue right.", "The hallway gets darker and darker as you continue onwards."));
			} else {
				hallwayPart.addExit(new Exit("balcony", "You see light...", "Let's there be light!"));
			}
		}

		balcony.addExit(new Exit("hallway"+lastHallwayPart, "Go back inside.", "There's no more to see here."));
		balcony.addExit(new Exit("death", "Jump down! I want to see the garden!",
				"You jump down from the second floor of a castle and severely injured yourself. There's no doctor around to treat your wound.\n"
				+ "Why did you do that?"));
		
		// Add items:
		diningRoom.addItem(new String[] {"a bowl of smelly soup", "a black key", "some candles"});
		kitchen.addItem(new String[] {"a rusty knife", "a cup of old tea"});
		balcony.addItem(new String[] {"a rose"});

		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
		checkDeadEnd();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}

	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}
	
	/**
	 * Check for no dead end.
	 */
	private void checkDeadEnd() {
		boolean deadEnd = false;
		// For every place:
		for (Place p : places.values()) {
			
			// Terminal room doesn't have exit, so we don't check it.
			if (!p.isTerminalState()) {
				
				// Only get the visible ones because otherwise the player would know there is a secret exit
				// if I forgot to put a visible exit in a room:
				if (p.getVisibleExits().isEmpty()) {
					deadEnd = true;
					// Print every exit with a missing place:
					System.err.println("Found dead-end at " + p.getId() + ".");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (deadEnd) {
			throw new RuntimeException("You have some dead-end somewhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);		
	}
}
