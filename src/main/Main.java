package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Main {
	
	private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/loo.txt");
	private static final Scanner KEYBOARD = new Scanner(System.in);
	
	public void main() {
		
		// READING THE INPUT
		loop : while (true) {
			final char option;
			// STANDARD MENU
			if (FILE.exists() && !FILE.isDirectory()) {
				System.out.println("Enter the desired option:");
				System.out.println("1. Add a new loo:");
				System.out.println("2. View all loos:");
				System.out.println("3. Find closest loo:");
				System.out.println("4. Update the state of cleanliness:");
				System.out.println("5. Update opening hours:");
				System.out.println("6. Delete a loo:");
				System.out.println("7. Exit:");
				option = KEYBOARD.nextLine().charAt(0);
			// EXCEPTION MENU
			} else { 
				System.out.println("Enter the desired option:");
				System.out.println("1. Add a new loo:");
				System.out.println("7. Exit:");
				option = KEYBOARD.nextLine().charAt(0);
			}
			
			// SWITCH OPTIONS
			switch(option) {
			   case '1':
				   addNewLoo();
				   break;   
			   case '2':
				   viewAllLoos();
				   break;
			   case '3':
				   findClosestLoo();
				   break;
			   case '4':
				   updateStateCleanliness();
				   break;
			   case '5':
				   updateOpeningHours();
				   break;
			   case '6':
				   deleteLoo();
				   break;
			   case '7':
				   System.out.println("Successful exit!");
				   break loop;
			   default:
				   System.out.println("Invalid option. Please enter your choice again!");
				   break;
			} 
			
			// ASK FOR ENTER OTHER OPTION
			System.out.println("Do you wish to continue (y/n)?");
			final String again = KEYBOARD.nextLine();
			if (!again.startsWith("y") && !again.startsWith("Y")) {
				break loop;
			}
		} 
		
		// CLOSING THE INPUT
		//KEYBOARD.close();
	}
	
	private int createId() {
	    String last = "";
	    String line;
	    final int id;
	    try {
			final BufferedReader input = new BufferedReader(new FileReader(FILE));
			while ((line = input.readLine()) != null) { 
			    last = line;
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    if (last.equals("")) {
	    	id = 1;
	    } else {
	    	id = Integer.parseInt(last.split(";")[0]) + 1;
	    }
		return id;
	}
	
	private void writeFile(String toWrite) {
		final Writer writer;
		try {
			writer = new BufferedWriter(new FileWriter(FILE, true));
			writer.append(toWrite + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkLongitude(String longitude) {
		final String pattern = "^(-?)[0-9]{1,3}\\.[0-9]{6}$";
		if(longitude.matches(pattern)) {
			Integer longitude_num = Integer.parseInt(longitude.split("\\.")[0]);	
			return (longitude_num <= 180 && longitude_num >=-180);
			
		}else
			return false;
		
	}
	
	private boolean checkLatitude(String latitude) {
		final String pattern = "^(-?)[0-9]{1,2}\\.[0-9]{6}$";
		if(latitude.matches(pattern)) {
			Integer latitude_num = Integer.parseInt(latitude.split("\\.")[0]);	
			return (latitude_num <= 90 && latitude_num >=-90);
			
		}else
			return false;
		
	}
	
	private boolean isDoublePositive(String number) {
		try {
	        final Double aux = Double.parseDouble(number);
	        return aux >= 0.0;
	    } catch (NumberFormatException e) {
	       	return false;
	    }
	}
	
	private void addNewLoo() {
		// CREATING THE FILE (IF NOT EXISTS)
		try {
			FILE.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loopAdd : while (true) {
			// ENTERING VALUES OF THE LOO TO ADD
			System.out.println("Please enter the loo longitude:");
			String longitude = KEYBOARD.nextLine();
			while (!checkLongitude(longitude)) {
				System.out.println("Invalid longitude");
				System.out.println("Please enter the loo longitude:");
				longitude = KEYBOARD.nextLine();
			}
			System.out.println("Please enter the loo latitude:");
			String latitude = KEYBOARD.nextLine();
			while (!checkLatitude(latitude)) {
				System.out.println("Invalid latitude");
				System.out.println("Please enter the loo latitude:");
				latitude = KEYBOARD.nextLine();
			}
			System.out.println("How clean is it?");
			String cleanliness = KEYBOARD.nextLine();
			while (!cleanliness.equals("") && !cleanliness.matches("[0-5]")) {
				System.out.println("Invalid cleanliness value! Please type an integer ranging from 0 to 5 or just press Enter");
				System.out.println("How clean is it?");
				cleanliness = KEYBOARD.nextLine();
			}
			System.out.println("Does it have a handicapped unit? (Y/n)");
			String handicapped = KEYBOARD.nextLine();
			while (!handicapped.equals("Y") && !handicapped.equals("n")) {
				System.out.println("Invalid value! Please type 'Y' or 'n' and press Enter.");
				System.out.println("Does it have a handicapped unit? (Y/n)");
				handicapped = KEYBOARD.nextLine();
			}
			System.out.println("Does it have a baby unit? (Y/n)");
			String baby = KEYBOARD.nextLine();
			while (!baby.equals("Y") && !baby.equals("n")) {
				System.out.println("Invalid value! Please type 'Y' or 'n' and press Enter.");
				System.out.println("Does it have a baby unit? (Y/n)");
				baby = KEYBOARD.nextLine();
			}
			System.out.println("Please enter the loo price:");
			String price = KEYBOARD.nextLine();
			while (!isDoublePositive(price)) {
				System.out.println("Invalid value! Please type a number >= 0 and press Enter");
				System.out.println("Please enter the loo price:");
				price = KEYBOARD.nextLine();
			}
			System.out.println("[Optional] What time are the loos open:");
			String open = KEYBOARD.nextLine();
			while (open.length() > 100) {
				System.err.println("Invalid input, please use less than 100 characters, this value is optional");
				System.out.println("[Optional] What time are the loos open:");
				open = KEYBOARD.nextLine();
			}
			
			//Just to add a blank character if no input is provided
			open = open.length() == 0 ? " " : open;
			
			// WRITING IN THE FILE THE NEW LOO
			final int id = createId();
			writeFile(id + ";" + longitude + ";" + latitude + ";" +  cleanliness + ";" + handicapped + 
					";" + baby + ";" + price + ";" + open);
			
			// ASK TO ADD ANOTHER LOO
			System.out.println("Do you want to add another loo (Y/N)?");
			final String addAnother = KEYBOARD.nextLine();
			if (!(addAnother.startsWith("Y") || addAnother.startsWith("y"))) {
				break loopAdd;
			}
		}
	}

	private void viewAllLoos() {
		// READ ALL LOOS
	    try {
	    	String line;
			final BufferedReader input = new BufferedReader(new FileReader(FILE));
			while ((line = input.readLine()) != null) { 
			    String[] lineInputs = line.split(";");
			    System.out.println("<" + lineInputs[0] + ">" +
			    		"<" + lineInputs[1] + ">" + 
			    		"<" + lineInputs[2] + ">" +
			    		"<" + lineInputs[3] + ">" +
			    		"<" + lineInputs[4] + ">" +
			    		"<" + lineInputs[5] + ">" +
			    		"<" + lineInputs[6] + ">" +
			    		"<" + lineInputs[7] + ">");
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void findClosestLoo() {
		// ENTER THE VALUES
		System.out.println("Please enter your location (Longitude, Latitude):");
		String longitude_latitude = KEYBOARD.nextLine();
		
		final String regex = "^\\((-?)[0-9]{1,3}\\.[0-9]{6}, (-?)[0-9]{1,2}\\.[0-9]{6}\\)$";
		//int length = longitude_latitude.length() - 1;
		
		//Removing the brackets
		String longitude_latitude_brackets = longitude_latitude.substring(1, longitude_latitude.length()-1);
		String[] split = longitude_latitude_brackets.split(", ");
		
		
		while (!(longitude_latitude.matches(regex) && checkLongitude(split[0]) && checkLatitude(split[1]))) {
			System.out.println("Please enter your coordinates again using coordinate standards, e.g: (53.370221, -6.216369)");
			System.out.println("Please enter your location (Longitude, Latitude):");
			longitude_latitude = KEYBOARD.nextLine();
			longitude_latitude_brackets = longitude_latitude.substring(1, longitude_latitude.length()-1);
			split = longitude_latitude_brackets.split(", ");
		}
		
		
		
		// SEARCH THE CLOSEST
	    try {
	    	String line;
	    	String[] closest = new String[8];
	    	double distanceClosest = Double.MAX_VALUE;
			final BufferedReader input = new BufferedReader(new FileReader(FILE));
			while ((line = input.readLine()) != null) { 
			    String[] lineInputs = line.split(";");
			    final double latitude = Double.parseDouble(split[1]);
			    final double longitude = Double.parseDouble(split[0]);
			    final double latitudeRead = Double.parseDouble(lineInputs[2]);
			    final double longitudeRead = Double.parseDouble(lineInputs[1]);
			    final double distance = Math.hypot(latitude-latitudeRead, longitude-longitudeRead);
			    if (distance < distanceClosest) {
			    	distanceClosest = distance;
			    	closest = lineInputs;
			    }
			}			
			System.out.println("To The Loo. Find the closest loo at (<" + closest[1] + ">, <" + closest[2] + ">). "
					+ "It's <" + closest[3] + "> (according to its rate: 0 -> disgusting, 5 -> perfectly clean). "
					+ "Hanidcapped unit: <" + closest[4] + ">. Baby unit: <" + closest[5] + ">. Opening hours are <" + closest[7] + ">");
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateStateCleanliness() {
		System.out.println("Please enter the ID of the loo you would like to provide cleanliness updates:");
		String id = KEYBOARD.nextLine();
		String[] loo;
		loopCleanliness : while (true) {
		    try {
		    	String line;
				final BufferedReader input = new BufferedReader(new FileReader(FILE));
				while ((line = input.readLine()) != null) { 
				    String[] lineInputs = line.split(";");
				    if (id.equals(lineInputs[0])) {
				    	loo = lineInputs;
				    	break loopCleanliness;
				    }
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Invalid ID! Do you wish to try again ? (y/n)");
			final String again = KEYBOARD.nextLine();
			if (!again.startsWith("y") && !again.startsWith("Y")) {
				break loopCleanliness;
			}
			System.out.println("Please enter the ID of the loo you would like to provide cleanliness updates:");
			id = KEYBOARD.nextLine();
		}
	}
	
	private void updateOpeningHours() {
		System.out.println("Please enter the ID of the loo you would like to provide opening hours updates.");
		String id = KEYBOARD.nextLine();
		String[] loo;
		loopOpening : while (true) {
		    try {
		    	String line;
				final BufferedReader input = new BufferedReader(new FileReader(FILE));
				while ((line = input.readLine()) != null) { 
				    String[] lineInputs = line.split(";");
				    if (id.equals(lineInputs[0])) {
				    	loo = lineInputs;
				    	break loopOpening;
				    }
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Invalid ID! Do you wish to try again ? (y/n)");
			final String again = KEYBOARD.nextLine();
			if (!again.startsWith("y") && !again.startsWith("Y")) {
				break loopOpening;
			}
			System.out.println("please enter the ID of the loo you would like to provide opening hours updates.");
			id = KEYBOARD.nextLine();
		}
	}
	
	private void deleteLoo() {
		System.out.println("please enter the ID of the loo you would like to delete");
		String id = KEYBOARD.nextLine();
		String[] loo;
		loopDelete : while (true) {
		    try {
		    	String line;
				final BufferedReader input = new BufferedReader(new FileReader(FILE));
				while ((line = input.readLine()) != null) { 
				    String[] lineInputs = line.split(";");
				    if (id.equals(lineInputs[0])) {
				    	loo = lineInputs;
				    	break loopDelete;
				    }
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Invalid ID! Do you wish to try again ? (y/n)");
			final String again = KEYBOARD.nextLine();
			if (!again.startsWith("y") && !again.startsWith("Y")) {
				break loopDelete;
			}
			System.out.println("please enter the ID of the loo you would like to delete");
			id = KEYBOARD.nextLine();
		}
	}
}
