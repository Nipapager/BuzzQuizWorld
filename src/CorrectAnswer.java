import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * Αυτή η κλάση υλοποιεί τον τύπο παιχνιδιού "Correct Answer".
 */

public class CorrectAnswer {
    private GUI gui;
    private  List<Questions> questionsList;
    Player player1,player2;      //αντικείμενο της κλάσης Player για την προσθήκη πόντων στον παίκτη.

    /**
     *
     * @param questions λίστα με αντικείμενα Questions, που περιέχουν τις ερωτήσεις, τις πιθανές απαντήσεις, την σωστή απάντηση,
     * την κατηγορία και τον όνομα της εικόνας (εφόσον υπάρχει).
     * @param gui το αντικείμενο κλάσης GUI που δηλώθηκε στην κλάση Main.
     * @param player1 αντικείμενο Player του μοναδικού παίκτη που περίεχει τους πόντους του.
     */
    public CorrectAnswer(List <Questions> questions, GUI gui,Player player1) {
        this.questionsList = questions;         // Αποθήκευση των ερωτήσεων στην private λίστα της κλάσης.
        this.gui=gui;
        this.player1=player1;
    }

    /**
     * Κατασκευαστής αντικειμένου CorrectAnswer.
     * @param questions questions λίστα με αντικείμενα Questions, που περιέχουν τις ερωτήσεις, τις πιθανές απαντήσεις, την σωστή απάντηση,
     * την κατηγορία και τον όνομα της εικόνας (εφόσον υπάρχει).
     * @param gui το αντικείμενο κλάσης GUI που δηλώθηκε στην κλάση Main.
     * @param player1 το αντικείμενο Player του πρώτου παίκτη που περίεχει τους πόντους του.
     * @param player2 το αντικείμενο Player του δεύτερου παίκτη που περίεχει τους πόντους του.
     */
    public CorrectAnswer(List <Questions> questions, GUI gui,Player player1, Player player2) {
        this.questionsList = questions;         // Αποθήκευση των ερωτήσεων στην private λίστα της κλάσης.
        this.gui=gui;
        this.player1=player1;
        this.player2=player2;
    }

    /**
     * Συνάρτηση που καλείται απο την Main με σκοπό να ξεκίησει ο γύρος με τύπο "Correct Answer".
     * Κάθε πάικτης που απαντάει σωστά στην ερώτηση κερδίζει 1000 πόντους και εμφανίζονται τα κατάλληλα μηνύματα στο GUI.
     * Όπως όλοι οι τύποι έτσι και αυτός έχει 6 ερωτήσεις και μπορεί να υλοποιηθεί για έναν ή περισσότερους παίκτες.
     * Επιλέγεται κάθε φορά για ερώτηση το πρώτο στοιχείο της λίστας questionsList και διαγράφεται όταν ο παίκτης απαντήσει
     * και αλλάξουν οι πόντοι του. Με αυτόν τον τρόπο εξασφαλίζεται ότι η ίδια ερώτηση δεν θα εμφανιστεί ξανα, κατα την διάρκεια
     * του παιχνιδιού.
     * @throws InterruptedException για την μέθοδο Thread.sleep()
     */

    public void startRound() throws InterruptedException {
        int numberOfQuestions = 1;
        String typeOfGame = "Correct Answer";
        gui.SetTypeOfGame(typeOfGame);
        while(numberOfQuestions<7) {
            gui.SetNumberOfQuestions("Question : " +numberOfQuestions+ " / 6"); // Εμφάνιση στα γραφικά για ενημέρωση του χρήστη
            gui.MessageNextQuestion();

            while(!gui.EnterPressed)
                Thread.sleep(1);
            gui.HideIcon();
            gui.hide_Or_Show_Answers_Questions(true);

            gui.setGUI_Question_Answers(questionsList.get(0));

            if(!questionsList.get(0).getIconName().equals("noImage"))
                gui.checkIcon(questionsList.get(0).getIconName());

            gui.SetMessage("   ");

            if(gui.numberOfPlayers==1) {
                while(!gui.player1hasAnswered)
                    Thread.sleep(1);
            }
            if(gui.numberOfPlayers==2) {
                while (!gui.player1hasAnswered || !gui.player2hasAnswered)
                    Thread.sleep(1);
            }

            while (!gui.answerChecked){
                Thread.sleep(1);
            }

            if(gui.IsAnswerOfPlayer1True){
                player1.addGamePoints(1000);

            }
            if(gui.IsAnswerOfPlayer2True)
                player2.addGamePoints(1000);
            if(gui.numberOfPlayers==1)
                gui.showPoints(player1);
            else
                gui.showPoints(player1,player2);

            questionsList.remove(0);        // Διαγραφή αντικειμένου από το ArrayList.
            numberOfQuestions++;
        }
        gui.EnterPressed=false;
        gui.MessageNextQuestion();
        while(!gui.EnterPressed)
            Thread.sleep(1);
        gui.HideIcon();
    }

}