/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urlopener;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lili3
 */
public class console {
    public static String getUserInput(String message) {
        //get user input
        // arg1: message to be displayed to user
        
        Scanner sc = new Scanner(System.in);
        
        //show a message if arg is defined
        if (!"".equals(message)) {
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
