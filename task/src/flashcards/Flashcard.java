package flashcards;

public class Flashcard {
    private String term;
    private String definition;
    private int mistakes;

    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.mistakes = 0;
    }
    public Flashcard(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }
    public String getTerm() {
        return term;
    }
    public String getDefinition() {
        return definition;
    }
    public void setMistakes(int m) {
        this.mistakes = m;
    }

    public int getMistakes() {
        return mistakes;
    }
}
