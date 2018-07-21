package urlopener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/********
 * Command Line Program that opens up multiple tabs so that you don't have to in a specified browser
 * 
 * 
 * 
 * 
 * 
 * @author lili3
 */

public class Urlopener {
    
    //global variables
    private static String browser;
    private static String urls = "";

    public static void main(String[] args) {
        stuffbyliangIntro();
        //loadBrowser();
        openSites();
    }

    public static void openSites() {
        clearScreen();
        
        //ask for file containing urls
        String filename = getUserInput("Please enter a filename (eg urls.txt)");
        File file = new File(filename);
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (sc.hasNextLine()) {
            urls += " " + sc.nextLine();
        }
        
        
        //run command line to open urls
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c","start " + urls});
        } catch (IOException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /* Unused function loadBrowser
    public static void loadBrowser() {
        //load browser from file browser.txt
        File file = new File("browser.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (sc.hasNextLine()) {
            browser = sc.nextLine();
        }
        
    }
    */

    public static void stuffbyliangIntro() {
        //Stufffbyliang Intro
        clearScreen();
        System.out.println("####################################\n\n\n\n"
                         + "    Program made by Liang Liu\n"
                         + "     http://stuffbyliang.com\n"
                         + " This program opens up multiple tabs\n"
                         + "of websites so that you dont have to\n"
                         + "         type out each url.\n\n\n\n"
                         + "####################################\n");
        pause();
    }

    public static String getUserInput(String message) {
        //get user input
        // arg1: message to be displayed to user
        
        Scanner sc = new Scanner(System.in);
        
        //show a message if arg is defined
        if (message != "") {
            System.out.println(message);
        }
        System.out.print("> ");
        String input;
        input = sc.nextLine();
        
        return input;
    }

    public static void clearScreen() {
        //clear console screen
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void pause() {
        // pause console screen
        Scanner sc = new Scanner(System.in);
        System.out.print("Press any key to continue . . . ");
        sc.nextLine();
    }

}