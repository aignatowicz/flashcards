package flashcards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        String fileImport = "";
        boolean isNext = false;
        for (String arg : args) {
            if(isNext) {
                fileImport = arg;
                break;
            }
            if(arg.equals("-import")) {
                isNext = true;
                continue;
            }
        }
        if(!fileImport.equals("")) {
            menu.importCards(fileImport);
        }
        String fileExport = "";
        isNext = false;
        for (String arg : args) {
            if(isNext) {
                fileExport = arg;
                break;
            }
            if(arg.equals("-export")) {
                isNext = true;
                continue;
            }
        }
        //added these two because the log wanted to have two additional lines
        //and these lines were "log saved" and the next "input the action..." so my logic
        //would rather say these shouldnt be logged but idk
        menu.addToLog("");
        menu.addToLog("");
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats)");
            menu.addToLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats)");
            String action = scanner.nextLine();
            menu.addToLog(action);
            if(action.equals("add")) {
                System.out.println(menu.add());
            }
            if(action.equals("remove")) {
                System.out.println(menu.remove());
                continue;
            }
            if(action.equals("import"))  {
                System.out.println("File name:");
                menu.addToLog("File name:");
                String name = scanner.nextLine();
                menu.addToLog(name);
                menu.importCards(name);
                continue;
            }
            if(action.equals("export")) {
                System.out.println("File name:");
                menu.addToLog("File name:");
                String name = scanner.nextLine();
                menu.addToLog(name);
                menu.exportCards(name);
                continue;
            }
            if(action.equals("ask")) {
                menu.ask();
                continue;
            }
            if(action.equals("exit")) {
                if(!fileExport.equals("")) {
                    menu.exportCards(fileExport);
                }
                menu.exit();
            }
            if(action.equals("log")) {
                System.out.println("File name:");
                menu.addToLog("File name:");
                String name = scanner.nextLine();
                menu.addToLog(name);
                menu.saveLog(name);
                continue;
            }
            if(action.equals("hardest card")) {
                menu.hardestCard();
                continue;
            }
            if(action.equals("reset stats")) {
                menu.resetStats();
                continue;
            }
        }

    }
}
