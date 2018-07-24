/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urlopener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class urls {
    public static void print(String filename) {
        //print all urls from a file
        // arg1 filename - eg "filename.txt"
        File file = new File("data/" + filename);
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; sc.hasNextLine(); i++) {
            
            String nextLine = sc.nextLine();
            
            if( i>=1 ) {
                //make sure to exclude first line of the file, which is the browser
                System.out.println( i + ". " + nextLine);
            }
        }
    }
    
    public static ArrayList<String> get(String filename) {
        //return all urls from a file
        // arg1 filename - eg "filename.txt"
        ArrayList<String> tempurls = new ArrayList<String>();
        
        File file = new File("data/" + filename);
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; sc.hasNextLine(); i++) {
            
            String nextLine = sc.nextLine();
            
            if( i>=1 ) {
                //make sure to exclude first line of the file, which is the browser
                tempurls.add(nextLine);
            }
        }
        
        return tempurls;
    }
    
    public static String browser(String filename) {
        //return browser (1st line) from a file
        
        File file = new File("data/" + filename);
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(filename);
        return(sc.nextLine());
    }
    
    public static String chooseBundle() {
        //print all lines from file 'bundlelist.txt'
        
        ArrayList<String> bundles = new ArrayList<String>();
        
        File file = new File("data/bundlelist.txt");
        
        console.clearScreen();
        
        //read file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; sc.hasNextLine(); i++) {
            String nextLine = sc.nextLine();
            
            System.out.println( (i+1) + " > " + nextLine); //print nextline to screen
            bundles.add(nextLine); //add nextline to bundles array
        }
        
        System.out.println(""); //
        
        return bundles.get(Integer.parseInt(console.getUserInput("Select a bundle")) - 1); //get user input, conver to number
    }
    
    public static void add(String bundle) {
        //add urls to a bundle
        boolean restartLoop = true;
        ArrayList<String> tempurls = new ArrayList<String>();
        
        while(restartLoop) {
            console.clearScreen();
            System.out.println("(Type 'exit' to stop adding urls)\n");

            //print old urls in array
            print(bundle);
            
            System.out.println("\nNew Urls:");
            
            //print current urls in array
            for(int i = 0; i < tempurls.size(); i++) {
                System.out.println( (i+1) + ". " + tempurls.get(i));
            }
            
            System.out.println(""); //newline

            String input = console.getUserInput("Type a url");
            if(input.equals("exit")){
                restartLoop = false;
            } else {
                tempurls.add(input);
            }

        }

        //append to file now
        console.clearScreen();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(new File("data/" + bundle), true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Urlopener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //start a new line
        writer.println("");
        
        for (String url : tempurls) {
            if(!url.equals(tempurls.get(tempurls.size()-1))) {
                writer.println(url); //if not last item
            } else {
                writer.print(url); //if last item
            }
        }
        writer.close();
    }
    public static void change(String bundle) {
        //change url at specified location
        ArrayList<String> tempurls = get(bundle);
        String browser = browser(bundle);
        
        console.clearScreen();

        //print old urls in array
        print(bundle);
        System.out.println("");

        int index = Integer.parseInt(console.getUserInput("Pick a url to alter")) - 1;
        System.out.println("");

        String newurl = console.getUserInput("Enter the new url");

        tempurls.set(index, newurl);

        //write to file now
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("data/" + bundle, "UTF-8");
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
    }
    public static void delete(String bundle) {
        //delete url at specified location
        ArrayList<String> tempurls = get(bundle);
        String browser = browser(bundle);
        
        console.clearScreen();

        //print old urls in array
        print(bundle);
        System.out.println("");

        int index = Integer.parseInt(console.getUserInput("Pick a url to delete")) - 1;
        System.out.println("");

        tempurls.remove(index);

        //write to file now
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("data/" + bundle, "UTF-8");
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
    }
}
