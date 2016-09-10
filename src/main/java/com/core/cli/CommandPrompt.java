package com.core.cli;

import java.io.IOException;
import java.util.Scanner;

/*
 * Created by pritamkadam on 09/09/16.
*/

public class CommandPrompt {

    public static String run(String command) {
        String output="";
        try{

            Scanner scanner = new Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
            if(scanner.hasNext()) output = scanner.next();

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return output;
    }
}
