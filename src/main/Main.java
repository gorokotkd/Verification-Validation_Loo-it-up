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
	
	private static final File FILE = new File(System.getProperty("user.dir") + "\\src\\main\\loo.txt");
	private static final Scanner KEYBOARD = new Scanner(System.in);
	
	public static void main(String[] args) {
		
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
		} 
		
		// CLOSING THE INPUT
		KEYBOARD.close();
	}
	
	private static int createId() {
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
	    	id = 0000000001;
	    } else {
	    	id = Integer.parseInt(last.split(";")[0]) + 1;
	    }
		return id;
	}
	
	private static void writeFile(String toWrite) {
		final Writer writer;
		try {
			writer = new BufferedWriter(new FileWriter(FILE, true));
			writer.append(toWrite + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean checkLongitude(String longitude) {
		final Double number = Double.parseDouble(longitude);
		final int integerPlaces = longitude.indexOf('.');
		final int decimalPlaces = longitude.length() - integerPlaces - 1;
		if (decimalPlaces == 6 && number >= -180.0 && number <= 180.0 && isDouble(longitude)) {
			return true;
		}
		return false;
	}
	
	private static boolean checkLatitude(String latitude) {
		final Double number = Double.parseDouble(latitude);
		final int integerPlaces = latitude.indexOf('.');
		final int decimalPlaces = latitude.length() - integerPlaces - 1;
		if (decimalPlaces == 6 && number >= -90.0 && number <= 90.0 && isDouble(latitude)) {
			return true;
		}
		return false;
	}
	
	private static boolean isDouble(String number) {
		try {
	        Double.parseDouble(number);
	        return true;
	    } catch (NumberFormatException e) {
	       	return false;
	    }
	}
	
	private static boolean isDoublePositive(String number) {
		try {
	        final Double aux = Double.parseDouble(number);
	        if (aux >= 0.0) {
	        	return true;	        	
	        } else {
	        	return false;
	        }
	    } catch (NumberFormatException e) {
	       	return false;
	    }
	}
	
	private static void addNewLoo() {
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
			while (!cleanliness.equals("") || !cleanliness.matches("[1-5]")) {
				System.out.println("Invalid cleanliness value! Please type an integer ranging from 0 to 5 or just press Enter");
				System.out.println("How clean is it?");
				cleanliness = KEYBOARD.nextLine();
			}
			System.out.println("Does it have a handicapped unit? (Y/n)");
			String handicapped = KEYBOARD.nextLine();
			while (!handicapped.equals("Y") || !handicapped.equals("n")) {
				System.out.println("Invalid value! Please type ‘Y’ or ‘n’ and press Enter.");
				System.out.println("Does it have a handicapped unit? (Y/n)");
				handicapped = KEYBOARD.nextLine();
			}
			System.out.println("Does it have a baby unit? (Y/n)");
			String baby = KEYBOARD.nextLine();
			while (!baby.equals("Y") || !baby.equals("n")) {
				System.out.println("Invalid value! Please type ‘Y’ or ‘n’ and press Enter.");
				System.out.println("Does it have a baby unit? (Y/n)");
				baby = KEYBOARD.nextLine();
			}
			System.out.println("Please enter the loo price:");
			String price = KEYBOARD.nextLine();
			while (isDoublePositive(price)) {
				System.out.println("Invalid value! Please type a number >= 0 and press Enter");
				System.out.println("Please enter the loo price:");
				price = KEYBOARD.nextLine();
			}
			System.out.println("[Optional] What time are the loos open:");
			String open = KEYBOARD.nextLine();
			while (open.length() > 100) {
				System.out.println("“Invalid input, please use less than 100 characters, this value is optional");
				System.out.println("[Optional] What time are the loos open:");
				open = KEYBOARD.nextLine();
			}
			
			// WRITING IN THE FILE THE NEW LOO
			final int id = createId();
			writeFile(id + ";" + longitude + ";" + latitude + ";" +  cleanliness + ";" + handicapped + 
					";" + baby + ";" + price + ";" + open);
			
			// ASK TO ADD ANOTHER LOO
			System.out.println("Do you want to add another loo (Y/N)?");
			final String addAnother = KEYBOARD.nextLine();
			if (!addAnother.startsWith("Y") && !addAnother.startsWith("y")) {
				break loopAdd;
			}
		}
	}

	private static void viewAllLoos() {
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
	
	private static void findClosestLoo() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateStateCleanliness() {
		// TODO Auto-generated method stub
		
	}
	
	private static void updateOpeningHours() {
		// TODO Auto-generated method stub
		
	}
	
	private static void deleteLoo() {
		// TODO Auto-generated method stub
		
	}
}
