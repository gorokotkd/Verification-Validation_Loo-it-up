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
			final String longitude = KEYBOARD.nextLine();
			System.out.println("Please enter the loo latitude:");
			final String latitude = KEYBOARD.nextLine();
			System.out.println("How clean is it?");
			final String cleanliness = KEYBOARD.nextLine();
			System.out.println("Does it have a handicapped unit? (Y/N)");
			final String handicapped = KEYBOARD.nextLine();
			System.out.println("Does it have a baby unit? (Y/N)");
			final String baby = KEYBOARD.nextLine();
			System.out.println("Please enter the loo price:");
			final String price = KEYBOARD.nextLine();
			System.out.println("[Optional] What time are the loos open:");
			final String open = KEYBOARD.nextLine();
			
			// WRITING IN THE FILE THE NEW LOO
			final int id = createId();
			writeFile(id + ";" + longitude + ";" + latitude + ";" +  cleanliness + ";" + handicapped + 
					";" + baby + ";" + price + ";" + open);
			
			// ASK TO ADD ANOTHER LOO
			System.out.println("Do you want to add another loo (Y/N)?");
			final String addAnother = KEYBOARD.nextLine();
			if (!addAnother.equals("Y") && !addAnother.equals("y")) {
				break loopAdd;
			}
		}
	}

	private static void viewAllLoos() {
		// TODO Auto-generated method stub
		
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
