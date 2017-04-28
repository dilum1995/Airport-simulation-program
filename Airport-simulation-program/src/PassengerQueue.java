
//Name - Dilum De Silva
//IIT - 2016142
//UoW - 16266371

import java.util.Scanner;

public class PassengerQueue {
	// maximum size of the queue
	static final int maxQSize = 20;

	// array to store the passengers who will be inserted to the queue
	Passenger[] arrayQueue = new Passenger[maxQSize];

	// indicates the last passenger
	private int last = 0;

	// indicates the first passenger
	private int first = last;

	// number of passengers, the max amount of passengers in the queue at a same
	// time and total passengers
	private int passengerCount = 0;
	private int maxPassengerCount = 0;
	private int totalPassengers = 0;

	// for the waiting time passengers in the queue
	private int maxWaiting = 0;
	private int minWaiting = 1000;
	private int totalWaiting = 0;
	private int avgWaiting;

	// method to get the count of passengers.
	public int getCount() {
		return passengerCount;
	}

	// method to get the max time
	public int getMaxTime() {
		return maxWaiting;
	}

	public int getMinTime() {
		return minWaiting;
	}

	public int getTotalTime() {
		return totalWaiting;
	}

	public int getAvgTime() {
		return avgWaiting;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public int getHead() {
		return first;
	}

	public int getTail() {
		return last;
	}

	public int getMaxCount() {
		return maxPassengerCount;
	}

	/* getter methods for the fields */

	// to check whether the queue is full
	boolean isFull() {
		return passengerCount == maxQSize;
	}

	// to check whether the queue is empty
	boolean isempty() {
		return passengerCount == 0;
	}

	// method add passengers
	void addPassengers() {

		// check whether the queue is not full
		if (!(isFull())) {

			// created a scanner object to get inputs from user
			Scanner inputsFromUser = new Scanner(System.in);

			// prompt for user first name and last name
			System.out.println("Please enter the passenger's first name");
			String fName = inputsFromUser.nextLine();
			System.out.println("Please enter the passenger's last name ");
			String lName = inputsFromUser.nextLine();

			// created a new passenger object for further use
			Passenger passenger1 = new Passenger(fName, lName);

			// save the passenger as the last position of the queue
			arrayQueue[last] = passenger1;

			// initially go for the next index of the circular queue
			last = (last + 1) % maxQSize;

			// increasing the count of passenger by 1
			passengerCount++;

			// adds the passenger to the passenger list
			Airport.passengerList.add(passenger1);

			// check whether the passenger count is greater than the maximum
			// count
			if (passengerCount > maxPassengerCount) {
				maxPassengerCount = passengerCount;
			}
			System.out.println("Passenger was added to the queue.");

		} else {
			System.err.println("Queue is full.\n"); // checks first if the queue
													// is full
		}
	}

	// to add passengers during the simulation part, this method will only
	// invoke during the simulation
	void addFromSimulation(Passenger pas) {

		// assigning the passenger to last index of the passenger Q array
		arrayQueue[last] = pas;

		// initially go to the next passenger
		last = (last + 1) % maxQSize;

		// maintaining the passenger count by increasing it by 1
		passengerCount++;

		if (passengerCount > maxPassengerCount) {
			maxPassengerCount = passengerCount;
		}

	}

	// to display all the passengers in the queue
	void display() {

		if (!(isempty())) {

			// creating a local variable called pFirst to store the first
			// element index
			int pFirst = first;

			for (int t = 1; t <= passengerCount; t++) {
				// to display the first passenger
				System.out.println("Passenger - " + arrayQueue[pFirst]);
				// to continue with the next passenger
				pFirst = (pFirst + 1) % maxQSize;
			}

		} else {
			// display a msg if the queue is empty

			System.err.println("Queue has no records");

		}

	}

	// to remove passengers
	void remove() {

		// checking whether the queue is not empty
		if (!(isempty())) {

			for (Passenger obj : Airport.passengerList) {
				if (obj.equals(arrayQueue[first])) {

					// remove the passenger from the passenger list
					Airport.passengerList.remove(Airport.passengerList.indexOf(obj));
					// here i'm rearrange the size of the passenger list to the
					// current size
					Airport.passengerList.trimToSize();
					break;
				}
			}

			// increase the first element by one,
			first = (first + 1) % maxQSize;

			// decrease the count of passengers by 1
			passengerCount--;
			System.out.println("\nPassenger was removed");

		} else {
			// display a message if there is no one to remove
			System.err.println("\nQueue is empty");
		}
	}

	// to remove passengers during the simulation
	void removeForSimulation() {

		if (!(isempty())) {
			first = (first + 1) % maxQSize;
			passengerCount--;

		} else {

		}
	}

	// to calculate the waiting time during the simulation
	void calculateWaitingTime() {

		// to generate an own delay to each passenger
		int processDelay = Airport.processingDelay();

		int x = 0;

		// i created a local variable called duplicateFirst to represent the
		// first passenger in the queue
		int duplicateFirst = first;

		while (x < passengerCount) {
			arrayQueue[duplicateFirst].setSecondsInQueue(arrayQueue[duplicateFirst].getSecondsInQueue() + processDelay);

			// generating the time as his own time + people who were before him
			// in the queue.
			duplicateFirst = (duplicateFirst + 1) % maxQSize;
			x++;
		}
	}

	public void checkTimes() {

		if (isempty()) {

		} else {
			if (arrayQueue[first].getSecondsInQueue() > maxWaiting) {
				// calculate the maximum delay
				maxWaiting = arrayQueue[first].getSecondsInQueue();
			}
			if (arrayQueue[first].getSecondsInQueue() < minWaiting) {
				// Calculate the minimum delay
				minWaiting = arrayQueue[first].getSecondsInQueue();
			}
			// Calculate the total delay
			totalWaiting += arrayQueue[first].getSecondsInQueue();
			totalPassengers++;

			// Average delay
			avgWaiting = totalWaiting / totalPassengers;
		}
	}

}
