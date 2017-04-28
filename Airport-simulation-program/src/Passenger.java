//Name - Dilum De Silva
//IIT - 2016142
//UoW - 16266371

/* Passenger Class to create a passenger*/

public class Passenger {

	private String firstName;
	private String lastName;
	private int secondsInQueue;

	public Passenger(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;

	}

	/*
	 * We're not intializing the seconds in queue in constructor because it
	 * calculates during the simulation
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setSecondsInQueue(int secondsInQueue) {
		this.secondsInQueue = secondsInQueue;
	}

	public String getLastName() {
		return lastName;
	}

	public int getSecondsInQueue() {
		return secondsInQueue;
	}

	/* Getters and setters for the private fields */

	public String toReport() {
		return String.format("%-15s%-15s%-15s", firstName, lastName, secondsInQueue);
	}

	/*
	 * toReport is the method used to print the string representation of the
	 * object in the report
	 */

	@Override
	public String toString() {
		return String.format("%-15s%-15s", firstName, lastName);
	}

	// A String formation of the Object
}
