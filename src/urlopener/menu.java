/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urlopener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lili3
 */
public class menu {
    public static void mainMenu() {
        /*
        Main menu function
        */
        
        Boolean endProgram = false;
        
        while(!endProgram) {
            console.clearScreen();
            System.out.println(""
                    + "1 > Create new url bundle\n"
                    + "2 > Configure existing url bundle\n"
                    + "3 > Delete exisiting url bundle\n"
                    + "4 > Open url bundle\n"
                    + "5 > Exit program\n");

            String message = "Please enter an option";
            switch(console.getUserInput(message)) {
                case "1":
                    newBundle();
                    break;
                case "2":
                    configureBundle();
                    break;
                case "3":
                    deleteBundle();
                    break;
                case "4":
                    openSites();
                    break;
                case "5":
                    endProgram = true;
                    break;
                default:
                    System.out.println("Not a valid option.");
                    console.pause();
            }
            
        }
        
        
    }
    
    public static void newBundle() {
        String browser = "";
        ArrayList<String> tempurls = new ArrayList<String>();
        
        Boolean restartLoop, back;
        back = false;
        
        //add browser
        do {
            restartLoop = false;
            
            console.clearScreen();
            System.out.println(""
            + "1 > Chrome\n"
            + "2 > Firefox\n"
            + "3 > Back\n");

            String message = "Please enter an option";
            
            switch(console.getUserInput(message)) {
                case "1":
                    browser = "chrome";
                    break;
                case "2":
                    browser = "firefox";
                    break;
                case "3":
                    back = true;
                    break;
                default:
                    System.out.println("Not a valid option.");
                    restartLoop = true;
                    console.pause();
            }
        } while(restartLoop);
        
        //add urls
        if(!back) {
            restartLoop = true;
            while(restartLoop) {
                console.clearScreen();
                System.out.println("(Type 'exit' to stop adding urls)\n");

                //print current urls in array
                for(int i = 0; i < tempurls.size(); i++) {
                    System.out.println( (i+1) + ". " + tempurls.get(i));
                }

                String input = console.getUserInput("Type a url");
                if(input.equals("exit")){
                    restartLoop = false;
                } else {
                    tempurls.add(input);
                }

            }

            //write to file now
            console.clearScreen();
            String filename = console.getUserInput("Enter a name for the url bundle");

            PrintWriter writer = null;
            try {
                writer = new PrintWriter("data/" + filename + ".txt", "UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
            }

            writer.println(browser);
            for (String url : tempurls) {
                if(!url.equals(tempurls.get(tempurls.size()-1))) {
                    writer.println(url); //if not last item
                } else {
                    writer.print(url); //if last item
                }
            }
            writer.close();
            
            //append into a file called bundlelist.txt the new name of the file
            try {
                writer = new PrintWriter(new FileOutputStream(new File("data/bundlelist.txt"), true));
            } catch (IOException ex) {
                Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.println(filename);
            writer.close();
            
        }
    }
    
    public static void configureBundle() {
        String bundle = urls.chooseBundle() + ".txt";
        
        Boolean restartLoop;

        
        do {
            restartLoop = true;
            
            console.clearScreen();
            
            System.out.println("Current Urls:");
            urls.print(bundle);
            System.out.println("");
            
            System.out.println(""
            + "1 > Add Url\n"
            + "2 > Change Url\n"
            + "3 > Delete Url\n"
            + "4 > Back\n");

            String message = "Please enter an option";
            
            switch(console.getUserInput(message)) {
                case "1":
                    urls.add(bundle);
                    break;
                case "2":
                    urls.change(bundle);
                    break;
                case "3":
                    urls.delete(bundle);
                    break;
                case "4":
                    restartLoop = false;
                    break;
                default:
                    System.out.println("Not a valid option.");
                    console.pause();
            }
        } while(restartLoop);
        
    }
    public static void deleteBundle() {
        //ask for file containing urls
        String bundlename = urls.chooseBundle();
        ArrayList<String> tempbundles = new ArrayList<String>();
        
        File file = new File("data/" + bundlename + ".txt");
        file.delete();
        
        //delete from bundle list
        file = new File("data/bundlelist.txt");
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(sc.hasNextLine()) {
            tempbundles.add(sc.nextLine());
        }

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; sc.hasNextLine(); i++) {
            if(sc.nextLine().equals(bundlename)) {
                tempbundles.remove(i);
            }
        }
        
        //write to file now
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("data/bundlelist.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String x : tempbundles) {
            writer.println(x);
        }
        writer.close();
        
    }

    public static void openSites() {
        //ask for file containing urls
        String filename = urls.chooseBundle() + ".txt";
        File file = new File("data/" + filename);
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Urlopener.urls = "";
        while (sc.hasNextLine()) {
            Urlopener.urls += " " + sc.nextLine();
        }
        
        
        //run command line to open urls
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c","start " + Urlopener.urls});
        } catch (IOException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
