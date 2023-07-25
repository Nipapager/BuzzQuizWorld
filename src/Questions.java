import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Η κλάση Questions περιλαμβάνει
 * 1 ερώτηση
 * 4 πιθανές απαντήσεις
 * την κατηγορία της ερώτησης
 * την σωστή απάντηση
 */
public class Questions {

    private String question;
    public  List<String> answers;
    private String category;
    private String correctAnswer;
    private String iconName;

    /**
     * Constructor της κλάσης χωρίς όρισμα
     * αρχικοποιεί την λίστα answers
     */
    public Questions(){
        answers = new ArrayList<>();
    }

    /**
     * έχει ως όρισμα μια συμβολοσειρα question
     * δίνει στην private μεταβλητή question την συμβολοσειρά question που έχει ως όρισμα
     */
    public void setQuestion(String question){
        this.question = question;
    }

    /**
     * @return επιστρέφει την ερώτηση.
     */
    public String getQuestion() { return question; }

    /**
     * ορίζει την κατηγορία της ερώτησης
     * έχει ως όρισμα μια συμβολοσειρα category
     * δίνει στην private μεταβλητή category την συμβολοσειρά category που έχει ως όρισμα
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * @return Επιστρέφει την κατηγορία.
     */
    public String getCategory() { return category; }

    /**
     * προσθέτει στο ArrayList answers την απάντηση answer που έχει ως όρισμα
     * @param answer συμβολοσειρά με μια πιθανή απάντηση.
     */
    public void setAnswers(String answer){
        answers.add(answer);
    }

    /**
     * Ορίζει τη σωστή απάντηση της ερώτησης.
     * δίνει στην private μεταβλητη correctAnswer την συμβολοσειρά correctAnswer που έχει ως όρισμα.
     * @param correctAnswer συμβολοσειρά με την σωστή απάντηση.
     */
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    /**
     * @return επιστρέφει τη σωστή απάντηση.
     */
    public String getCorrectAnswer() { return correctAnswer; }

    /**
     * ανακατεύει το ArrayList με τις απαντήσεις
     */
    public void shuffleAnswers() { Collections.shuffle(answers); }

    /** Getter για την μεταβλητή iconName
     * @return το όνομα της εικόνας ή διαφορετικά τη συμβολοσειρά "noImage".
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * Setter για την μεταβλητή iconName
     * @param iconName το όνομα της εικόνας ή διαφορετικά η συμβολοσειρά "noImage".
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

}