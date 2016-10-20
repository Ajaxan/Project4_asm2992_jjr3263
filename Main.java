/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     */
    public static void main(String[] args) throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvalidCritterException, ClassNotFoundException, IllegalAccessException { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        boolean run = true;
        
        while(run) {
        	if(kb.hasNext()) {
        		String command = kb.next();
        		if(command.equalsIgnoreCase("quit")) {
            		System.out.print("Thanks for using CritterWorld!");
            		run = false;
            	} else if (command.equalsIgnoreCase("show")) {
            		Critter.displayWorld();
            	} else if (command.equalsIgnoreCase("step")) {
            		if(kb.hasNextInt()) {
            			int steps = Integer.parseInt(kb.next());
            			while(steps > 0) {
            				Critter.worldTimeStep();
            				steps--;
            			}
            		} else {
            			Critter.worldTimeStep();
            		}
            	} else if (command.equalsIgnoreCase("seed")) {
            		if(kb.hasNext()) {
            			Critter.setSeed(Integer.parseInt(kb.next()));
            		} else {
            			System.out.print("No seed included! Ignoring command.");
            		}
            	} else if (command.equalsIgnoreCase("make")) {
            		if(kb.hasNext()) {
            			String critterType = kb.next();

            			if(kb.hasNextInt()) {
            				int critters = Integer.parseInt(kb.next());

                			while(critters > 0) {
                				try {
    								Critter.makeCritter(critterType);
    							} catch (InvalidCritterException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
    								System.out.println("error processing: make " + critterType + " " + critters);
    								break;
    							}
                				critters--;
                			}            			
            			} else {
            				try {
								Critter.makeCritter(critterType);
							} catch (InvalidCritterException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
								System.out.print("error processing: make " + critterType);
							}
            			}
            			
            		} else {
            			System.out.print("No critter type included! Ignoring command.");
            		}
            		
            	} else if (command.equalsIgnoreCase("stats")) {
            		if(kb.hasNext()) {
            			String critterType = kb.next();
            			try {
            				Class<?> critterClassType= Class.forName(myPackage+"."+critterType);
            				List<Critter> list = Critter.getInstances(critterType);
            				Class<?>[] types = {List.class};
            				Method runStats = critterClassType.getMethod("runStats", types);
            				runStats.invoke(null, list);
						} catch (InvalidCritterException e) {
							System.out.print("error processing: stats " + critterType);
						}
            		} else {
            			System.out.print("No critter type included! Ignoring command");
            		}
            	}
        	}
        	
        }
        
        /* Write your code above */
        System.out.flush();

    }
}
