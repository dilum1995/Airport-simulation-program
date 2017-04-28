
//Name - Dilum De Silva
//IIT - 2016142
//UoW - 16266371

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

// class which includes the main method
public class Airport {

	// created a scanner object to get user inputs
	private static Scanner input = new Scanner(System.in);

	// created a arraylist to hold passenger list
	static ArrayList<Passenger> passengerList = new ArrayList<Passenger>();

	// creating two queue objects from passenger class
	static PassengerQueue queue_1 = new PassengerQueue();
	static PassengerQueue queue_2 = new PassengerQueue();

	public static void main(String[] args) {

		// calling the method which displays the main menu for the program
		displayMenu();
	}

	// method to display the menu of the airport program
	static void displayMenu() {

		boolean flag = true; // Boolean variable to check if the user wants to
								// continue;

		while (flag == true) {

			// menu of the Airport program

			System.out.println("\n----------Airport Program---------");
			System.out.println();

			// selections of the Airport program
			System.out.println("V: View the passengerQueue");
			System.out.println("A: Add a passenger to the passengerQueue");
			System.out.println("D: Delete passenger from passengerQueue");
			System.out.println("S: Store passengerArray data into a plain text file");
			System.out.println("L: Load passenger data back from the file into the passengerArray");
			System.out.println("R: Run the simulation and produce report\n");

			System.out.println("X: Exit from the program\n");

			System.out.print("Please enter your selection to continue : ");

			// catching and handling exceptions using a try catch block
			try {
				// get user selected input
				String selectionInput = input.nextLine();

				// to make sure user has inputed a valid selection (validation)
				if (selectionInput.length() == 1) {
					// getting only the first character of the user input and
					// assigning
					// it to the variable called userInput
					char userInput = selectionInput.charAt(0);

					// both upper case and lower case are consider in a
					// selection
					switch (userInput) {
					case 'v':
					case 'V':
						System.out.println("\n");
						// perform the function according to the user selection

						// function to display the passengers in queue_1
						System.out.println("\nPassenger details in the Queue-1\n");
						queue_1.display();

						// function to display the passengers in queue_2
						System.out.println("\n\nPassenger details in the Queue-2\n");
						queue_2.display();

						break; // breaking the flow to avoid continuing other
								// cases

					case 'a':
					case 'A':

						System.out.println("\n");
						// to make sure both queues are not filled with
						// passengers
						if (!(queue_1.isFull() && queue_2.isFull())) {

							// to compare the count of two queues before adding
							// passengers and
							// add the latest passenger to queue which has the
							// lower count of passengers.

							if (queue_1.getCount() > queue_2.getCount()) {
								queue_2.addPassengers();
							} else {

								queue_1.addPassengers();
							}

							// display a message if both queues have filed their
							// slots
						} else {

							System.err.println("Both queues are full for this moment please wait...");

						}

						break;

					case 'd':
					case 'D':
						System.out.println("\n");
						// check whether any passenger count of a queue is
						// greater than other queue
						if (queue_1.getCount() > queue_2.getCount()) {
							// remove the passenger from the queue which has the
							// greater passenger count
							queue_1.remove();
						} else {
							queue_2.remove();
						}

						break;

					case 's':
					case 'S':
						System.out.println("\n");
						// to store the data in a .dat file
						storeData();

						break;

					case 'l':
					case 'L':
						System.out.println("\n");
						// to load passengers to passenger Array from the same
						// .dat file
						loadData();
						break;

					case 'r':
					case 'R':
						// to run the simulation part
						runSimulation();
						flag = false;
						break;

					case 'x':
					case 'X':
						// confirming whether user wants to exit or continue
						// back with the program
						System.out.println("\nYour selection was exit from the program\n");
						System.err.println("To confirm your selection");
						System.out.println("Type 'Y'-yes | 'N'-no");

						String exitConfirm = input.nextLine();

						if (exitConfirm.equalsIgnoreCase("y")) {
							System.err.println("\n\n****Program Terminated****");
							System.exit(0);
							break;
						} else if (exitConfirm.equalsIgnoreCase("n")) {
							System.out.println("");
							continue;
						}

					default:
						// set a message to inform invalid inputs
						System.out.println("Invalid input");
						break;
					}
				}

				// false block of the 'if' condition which used to validate the
				// user input
				else {
					System.err.println("Invalid Input. Input should contain only one character !!!");
				}

			} catch (Exception e) {
				System.out.println();
				e.printStackTrace();
			}

		}

	}

	// method which has used to store data into a text file
	private static void storeData() throws IOException, NullPointerException {

		// creating a fileWrite to write to the file
		FileWriter fileWritter1 = null;
		Scanner scanner2;

		try {
			// creating a file object from file class to hold the text file
			File file = new File("passengers.dat");

			// Appending the file
			fileWritter1 = new FileWriter(file.getAbsoluteFile(), true);

			// used java tokenizer to identify each string as a token
			StringTokenizer stringTokens1;

			// an enhance for loop to go through each passenger in the arraylist
			for (Passenger newPaseenger : passengerList) {

				scanner2 = new Scanner(file.getAbsoluteFile());

				boolean flag1 = false;

				while (scanner2.hasNextLine()) {

					// assigning each passenger to string variable to check
					// whether that passenger has recorded.
					String token = scanner2.nextLine();
					stringTokens1 = new StringTokenizer(token);
					String tFirstName = stringTokens1.nextToken();
					String tLastName = stringTokens1.nextToken();

					// if the passenger already in the file here i'm changing
					// the value of the boolean variable to true
					if (newPaseenger.getFirstName().equals(tFirstName)
							&& newPaseenger.getLastName().equals(tLastName)) {
						flag1 = true;

					}

				}

				// if the passenger not in the file or mismatched with the
				// records here i'm writing that passenger into the file.
				if (!flag1) {
					fileWritter1.write("\n" + newPaseenger.getFirstName() + " " + newPaseenger.getLastName());
				}
			}

			// display a massage to the user to get to know about passenger has
			// been stored in the file
			System.out.println("Passenger has been stored successfully to the file");

			// catching if there is any exceptions
		} catch (IOException e) {

		} catch (NoSuchElementException e) {

		} finally {
			// at the end closing the file writer
			fileWritter1.close();
		}
	}

	// method which has used to load data from the text file
	private static void loadData() throws IOException {

		// creating a file reader,buffer reader
		FileReader fileReader1 = null;
		BufferedReader bufferReader1 = null;
		StringTokenizer stringTokens2;

		try {
			// created a file object from java file class to hold the file.
			File file = new File("passengers.dat");
			fileReader1 = new FileReader(file.getAbsoluteFile());
			bufferReader1 = new BufferedReader(fileReader1);

			// here i'm reading the file line by line using the readLine method
			// and assigning the value of each line to a local variable called
			// line
			String line = bufferReader1.readLine();

			while (line != null) {
				// a variable to make sure whether the passenger in the Q
				boolean inTheQ = false;

				// here i'm splitting the value of each line by the space and
				// assigning it to string tokens.
				stringTokens2 = new StringTokenizer(line, " ");

				// assigning the values of the string tokens to variables
				String firstn = stringTokens2.nextToken();
				String lastn = stringTokens2.nextToken();

				for (Passenger aPassenger : passengerList) {
					if (aPassenger.getFirstName().equals(firstn) && aPassenger.getLastName().equals(lastn)) {
						inTheQ = true;
					}
				}
				if (!inTheQ) {
					passengerList.add(new Passenger(firstn, lastn));
				}

				// after the allocation read the next line.
				line = bufferReader1.readLine();
			}

			System.out.println("Data was loaded from file successfully");

			// catching if there is any exceptions
		} catch (IOException e) {
			// to display a error massage if an error occurred during data load
			// from the file.
			System.err.println("An error occurred while loading try agin in a while");
		} catch (NoSuchElementException e) {

		}
		// at the end i'm closing the fileReader and the Buffered Reader
		finally {
			fileReader1.close();
			bufferReader1.close();

		}
	}

	// method which has used to run simulation part
	private static void runSimulation() {

		PrintWriter pw = null;
		try {

			// to load the data from text file
			loadData();

			// checking for duplicate records in the queue and passengerList
			duplicateRecords(queue_1.arrayQueue);
			duplicateRecords(queue_2.arrayQueue);

			Passenger[] ListtoArray = new Passenger[passengerList.size()];
			ListtoArray = passengerList.toArray(ListtoArray);
			// Converting the arrayList to an array

			File report = new File("report.dat"); // To hold the text file

			pw = new PrintWriter(report);

			pw.println(
					"\n\n------------------------------------Simulation Report-----------------------------------\n");
			pw.println(String.format("%-60s%-60s%n", "\t\tRecords of the Q1", "Records of the Q2"));
			pw.print(String.format("%-15s%-15s%-15s", "First Name", "Last Name", "Seconds In Queue\t\t|"));
			pw.println(String.format("%-15s%-15s%-15s%n", "\tFirst Name", "Last Name", "Seconds In Queue"));

			System.out.println(
					"\n\n------------------------------------Simulation Report-----------------------------------\n");
			System.out.println(String.format("%-60s%-60s%n", "\t\tRecords of the Q1", "Records of the Q2"));
			System.out.print(String.format("%-15s%-15s%-15s", "First Name", "Last Name", "Seconds In Queue\t\t|"));
			System.out.println(String.format("%-15s%-15s%-15s%n", "\tFirst Name", "Last Name", "Seconds In Queue"));

			// Printing the headings in the report and the to the console

			int i = 0;

			OuterLoop: while (i < ListtoArray.length) {

				int noOfArrivers = dice();
				int j = 0;
				A: while (j < noOfArrivers) {
					if (queue_1.isFull() && queue_2.isFull()) {
						break A;
					} else {
						if (i < ListtoArray.length) {

							if (queue_1.getCount() > queue_2.getCount()) {
								queue_2.addFromSimulation(ListtoArray[i]);
							} else {
								queue_1.addFromSimulation(ListtoArray[i]);
							}

							i++;
							j++;
						} else {
							break OuterLoop;
						}

					}

				}

				queue_1.calculateWaitingTime();
				queue_2.calculateWaitingTime();
				addToReport(pw);
				queue_1.checkTimes();
				queue_2.checkTimes();
				queue_1.removeForSimulation();
				queue_2.removeForSimulation();
			}

			while (!queue_1.isempty()) {

				queue_1.calculateWaitingTime();
				queue_2.calculateWaitingTime();
				addToReport(pw);
				queue_1.checkTimes();
				queue_2.checkTimes();
				queue_1.removeForSimulation();
				queue_2.removeForSimulation();
			}

			// Same process continues until the queue is empty

			addStatistics(pw);
			// Adding the final results

			System.out.println("\n\n");
			System.out.println("Simulation Report has successfully generated as report.dat");

		} catch (IOException e) {
			System.err.println("Sorry an error occurred during the simulation");
		} finally {
			pw.close();
		}

	}

	// method which is checking for duplications in queue and passengerList
	private static void duplicateRecords(Passenger[] arrayQueue) {

		for (Passenger queueObj : arrayQueue) {
			inner: for (Passenger passengerobj : passengerList) {
				if (passengerobj.equals(queueObj)) {

					// in here i'm deleting if there is any duplicate records
					passengerList.remove(passengerList.indexOf(passengerobj));
					// after deleting the duplicated record rearrange the size
					// of the arrayList to the current size
					passengerList.trimToSize();
					break inner;
				}
			}
		}

	}

	// method to print the statistics in the report and to the console as well
	private static void addStatistics(PrintWriter pw) {

		pw.println(
				"\n-----------------------------------------Summary of the delay in queues---------------------------------------------------\n");

		pw.print(String.format("%-45s%s", " Maximum length of the queue_1 attained : ", queue_1.getMaxCount() + "\t"));
		pw.println(String.format("%-45s%-15s", "\tMaximum length of the queue_2 attained : ", queue_2.getMaxCount()));

		pw.print(String.format("%-45s%s", " Maximum delay in Q1 : ", queue_1.getMaxTime() + "\t"));
		pw.println(String.format("%-45s%-15s", "\tMaximum delay in Q2 : ", queue_2.getMaxTime()));

		pw.print(String.format("%-45s%s", " Minimum delay in Q1 : ", queue_1.getMinTime() + "\t"));
		pw.println(String.format("%-45s%-15s", "\tMinimum delay in Q2 : ", queue_2.getMinTime()));

		pw.print(String.format("%-45s%s", " Average delay of Q1 : ", queue_1.getAvgTime() + "\t"));
		pw.println(String.format("%-45s%-15s", "\tAverage delay of Q2 : ", queue_2.getAvgTime()));

		System.out.println(
				"\n-----------------------------------------Summary of the delay in queues---------------------------------------------------\n");

		System.out.print(
				String.format("%-45s%-4s", " Maximum length of the queue_1 attained : ", queue_1.getMaxCount() + "\t"));
		System.out.println(
				String.format("%-45s%-15s", "\tMaximum length of the queue_2 attained : ", queue_2.getMaxCount()));

		System.out.print(String.format("%-45s%-4s", " Maximum delay in Q1 : ", queue_1.getMaxTime() + "\t"));
		System.out.println(String.format("%-45s%-15s", "\tMaximum delay in Q2 : ", queue_2.getMaxTime()));

		System.out.print(String.format("%-45s%-4s", " Minimum delay in Q1 : ", queue_1.getMinTime() + "\t"));
		System.out.println(String.format("%-45s%-15s", "\tMinimum delay in Q2 : ", queue_2.getMinTime()));

		System.out.print(String.format("%-45s%-4s", " Average delay of Q1 : ", queue_1.getAvgTime() + "\t"));
		System.out.println(String.format("%-45s%-15s", "\tAverage delay of Q2 : ", queue_2.getAvgTime()));

	}

	// method to add records to the report
	private static void addToReport(PrintWriter pw) {

		if (!queue_1.isempty()) {

			pw.print(queue_1.arrayQueue[queue_1.getHead()].toReport() + "\t");
			System.out.print(queue_1.arrayQueue[queue_1.getHead()].toReport() + "\t");
		} else {
			pw.print(String.format("%-15s%-15s%-15s", "", "", "") + "\t");
			System.out.print(String.format("%-15s%-15s%-15s", "", "", "") + "\t");
		}
		// printing the passengers who are about to leave.
		if (!queue_2.isempty()) {

			pw.println("\t" + queue_2.arrayQueue[queue_2.getHead()].toReport());
			System.out.println("\t" + queue_2.arrayQueue[queue_2.getHead()].toReport());
		} else {
			pw.println("\t" + String.format("%-15s%-15s%-15s", "", "", ""));
			System.out.println("\t" + String.format("%-15s%-15s%-15s", "", "", ""));
		}

	}

	// To generate number of arrives using 1 six sided dice
	static int dice() {
		return (int) (1 + 6 * Math.random());
	}

	// to generating processing delay using 3 six sided dices.
	static int processingDelay() {
		return (dice() + dice() + dice());
	}

}
