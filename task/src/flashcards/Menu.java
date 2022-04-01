package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    ArrayList<Flashcard> flashcards = new ArrayList<>();
    ArrayList<String> logs = new ArrayList<>();
    public void addToLog(String msg) {
        logs.add(msg);
    }
    public String add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The card:");
        addToLog("The card:");
        String term = "";
        String definition = "";
        term = scanner.nextLine();
        addToLog(term);
        //find if term exists in flashcards
        for (Flashcard card: flashcards) {
            if(term.equals(card.getTerm())) {
                addToLog("The card \"" + term + "\" already exists");
                return "The card \"" + term + "\" already exists";
            }
        }
        System.out.println("The definition of the card:");
        addToLog("The definition of the card:");
        definition = scanner.nextLine();
        addToLog(definition);
        //find if definition exists in flashcards
        for (Flashcard card: flashcards) {
            if(definition.equals(card.getDefinition())) {
                addToLog("The definition \"" + definition + "\" already exists");
                return "The definition \"" + definition + "\" already exists";
            }
        }
        Flashcard flashcard = new Flashcard(term, definition);
        flashcards.add(flashcard);
        addToLog("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        return "The pair (\"" + term + "\":\"" + definition + "\") has been added.";
    }
    public void update(Flashcard flashcard, String newDefinition) {
        String term = flashcard.getTerm();
        for(Flashcard card: flashcards) {
            if(card.getTerm().equals(term) && card.getTerm().equals(flashcard.getDefinition())) {
                flashcards.remove(card);
            }
        }
        flashcards.add(new Flashcard(term, newDefinition));
    }
    public String remove() {
        System.out.println("Which card?");
        addToLog("Which card?");
        Scanner scanner = new Scanner(System.in);
        String term = scanner.nextLine();
        addToLog(term);
        for (Flashcard card: flashcards) {
            if (card.getTerm().equals(term)) {
                flashcards.remove(card);
                addToLog("The card has been removed.");
                return "The card has been removed.";
            }
        }
        addToLog("Can't remove \"" + term + "\": there is no such card.");
        return "Can't remove \"" + term + "\": there is no such card.";
    }
    public void importCards(String path) {
        File file = new File(path);
        String term = "";
        String definition = "";
        String mistakes = "";
        int cardCounter = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                term = scanner.nextLine();
                if(!scanner.hasNext()) {
                    break;
                } else {
                definition = scanner.nextLine();
                List<Flashcard> toDelete = new ArrayList<>();
                for (Flashcard flashcard : flashcards) {
                    if(flashcard.getTerm().equals(term)) {
                        toDelete.add(flashcard);
                    }
                }
                for (Flashcard flashcard : toDelete) {
                    flashcards.remove(flashcard);
                }
                mistakes = scanner.nextLine();
                int num = 0;
                try{
                    num = Integer.parseInt(mistakes);
                }
                catch (NumberFormatException ex){
                    addToLog(mistakes + " is not a number");
                    System.out.println(mistakes + " is not a number");
                }
                cardCounter++;
                flashcards.add(new Flashcard(term,definition, num));
                }}
        } catch (FileNotFoundException e) {
            addToLog("File not found");
            System.out.println("File not found");
        }
        addToLog(cardCounter + " cards have been loaded.");
        System.out.println(cardCounter + " cards have been loaded.");
    }
    public void exportCards(String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file); // overwrites the file
        int cardCount = 0;
        for (Flashcard card: flashcards) {
            writer.write(card.getTerm() + "\n");
            writer.write(card.getDefinition() + "\n");
            writer.write(card.getMistakes() + "\n");
            cardCount++;
        }
        writer.close();
        addToLog(cardCount + " cards have been saved.");
        System.out.println(cardCount + " cards have been saved.");
    }
    public void ask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many times to ask?");
        addToLog("How many times to ask?");
        String stringNumber = scanner.nextLine();
        addToLog(stringNumber);
        int number = 0;
        try{
            number = Integer.parseInt(stringNumber);
        }
        catch (NumberFormatException ex){
            addToLog(stringNumber + " is not a number");
            System.out.println(stringNumber + " is not a number");
        }
        Random random = new Random();
        int maximum = flashcards.size() - 1;
        int minimum = 0;
        int range = maximum - minimum + 1;

        for (int i = 0; i < number; i++) {
            int randomNum =  random.nextInt(range) + minimum;
            Flashcard currentFlashcard = flashcards.get(randomNum);
            String correctTerm = "";
            System.out.println("Print the definition of \"" + currentFlashcard.getTerm() + "\": ");
            addToLog("Print the definition of \"" + currentFlashcard.getTerm() + "\": ");
            String userInput = scanner.nextLine();
            addToLog(userInput);
            //checks if user puts a definition for other term
            for (Flashcard card: flashcards) {
                if(userInput.equals(card.getDefinition())) {
                    correctTerm = card.getTerm();
                }
            }
            if(userInput.equals(currentFlashcard.getDefinition())) {
                System.out.println("Correct!");
                addToLog("Correct!");
            } else if(!correctTerm.equals("")){
                currentFlashcard.setMistakes(currentFlashcard.getMistakes() + 1);
                System.out.println("Wrong. The right answer is \"" + currentFlashcard.getDefinition() + "\", but your definition is correct for \"" + correctTerm + "\"");
                addToLog("Wrong. The right answer is \"" + currentFlashcard.getDefinition() + "\", but your definition is correct for \"" + correctTerm + "\"");
            } else {
                currentFlashcard.setMistakes(currentFlashcard.getMistakes() + 1);
                System.out.println("Wrong. The right answer is \"" + currentFlashcard.getDefinition() + "\"");
                addToLog("Wrong. The right answer is \"" + currentFlashcard.getDefinition() + "\"");
            }

        }
    }
    public void exit() {
        addToLog("Bye bye!");
        System.out.println("Bye bye!");
        System.exit(0);
    }
    public void saveLog(String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file); // overwrites the file
        for(String log : logs) {
            writer.write(log + "\n");
        }
        writer.close();
        addToLog("The log has been saved.");
        System.out.println("The log has been saved.");
    }
    public void hardestCard() {
        List<Flashcard> hardCards = new ArrayList<>();
        int max = 0;
        for (Flashcard card : flashcards) {
            if (max < card.getMistakes()) {
                max = card.getMistakes();
            }
        }
        for (Flashcard card : flashcards) {
            if (max == card.getMistakes() && max!=0) {
                hardCards.add(card);
            }
        }
        String msg = "";
        if (hardCards.size() == 1) {
            msg = "The hardest card is ";
        } else if (max == 0) {
            msg = "There are no cards with errors.";
        } else {
            msg = "The hardest cards are";
        }
        String terms = "";
        String helper = "";
        for (Flashcard card : hardCards) {
            if (hardCards.size() == 1) {
                terms = "\"" + card.getTerm() + "\"";
            } else {
                if(terms.equals("")) {
                    helper = " ";
                } else {
                    helper = ", ";
                }
                terms = terms + helper + "\"" + card.getTerm() + "\"";
            }
        }
        terms = terms + ". ";
        if(hardCards.size() == 1) {
            msg = msg + terms + "You have " + max + " errors answering it.";
        } else if(hardCards.size() > 1) {
            msg = msg + terms + "You have " + max + " errors answering them.";
        }
        addToLog(msg);
        System.out.println(msg);
    }
    public void resetStats() {
        for (Flashcard flashcard : flashcards) {
            flashcard.setMistakes(0);
        }
        addToLog("Card statistics have been reset.");
        System.out.println("Card statistics have been reset.");
    }
}
