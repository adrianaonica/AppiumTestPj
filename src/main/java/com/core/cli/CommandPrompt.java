package com.core.cli;

import com.core.logger.CustomLogger;

import java.io.IOException;
import java.util.Scanner;

/*
 * Created by pritamkadam on 09/09/16.
*/

public class CommandPrompt {

    public static String run(String command) {
        String output="";
        try{
            CustomLogger.log.debug("Executing command : " + command);
            Scanner scanner = new Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
            if(scanner.hasNext()) output = scanner.next();

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

        CustomLogger.log.debug("Output of command => " + command + " is => " + output);
        return output;
    }
}
