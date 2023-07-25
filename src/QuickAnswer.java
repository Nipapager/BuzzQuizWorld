import java.util.List;
import javax.swing.*;
/**
 * Αυτή η κλάση υλοποιεί τον τύπο παιχνιδιού "Quick Answer".
 */
public class QuickAnswer {
    private int count;
    private GUI gui;
    private List<Questions> questionsList;
    Player player1,player2;

    /**
     * Κατσκευαστής αντικειμένου QuickAnswer.
     * @param questions questions λίστα με αντικείμενα Questions, που περιέχουν τις ερωτήσεις, τις πιθανές απαντήσεις, την σωστή απάντηση,
     * την κατηγορία και τον όνομα της εικόνας (εφόσον υπάρχει).
     * @param gui το αντικείμενο κλάσης GUI που δηλώθηκε στην κλάση Main.
     * @param player1 το αντικείμενο Player του πρώτου παίκτη που περίεχει τους πόντους του.
     * @param player2 το αντικείμενο Player του δεύτερου παίκτη που περίεχει τους πόντους του.
     */
    public QuickAnswer(List <Questions> questions, GUI gui,Player player1, Player player2) {
        this.questionsList = questions;         // Αποθήκευση των ερωτήσεων στην private λίστα της κλάσης.
        this.gui=gui;
        this.player1=player1;
        this.player2=player2;
    }


    /**
     * Συνάρτηση που καλείται απο την Main με σκοπό να ξεκίησει ο γύρος με τύπο "Correct Answer".
     * Αυτός ο τύπος έχει νόημα για δύο παίκτες μόνο καθώς ο πρώτος που θα απαντήσει σωστά κερδίζει 1000 πόντους, ενώ
     * ο δεύτερος κερδίζει 500 (αν απαντήσει και αυτός σωστά). Όπως όλοι οι τύποι έτσι και αυτός έχει 6 ερωτήσεις.
     * Επιπλέον εμφανίζονται στο GUI τα δευτερόλεπτα από την στιγμή μου εμφανίστηκε η ερώτηση.
     * Επιλέγεται κάθε φορά για ερώτηση το πρώτο στοιχείο της λίστας questionsList και διαγράφεται όταν ο παίκτης απαντήσει
     * και αλλάξουν οι πόντοι του. Με αυτόν τον τρόπο εξασφαλίζεται ότι η ίδια ερώτηση δεν θα εμφανιστεί ξανα, κατα την διάρκεια
     * του παιχνιδιού.
     * @throws InterruptedException για την μέθοδο Thread.sleep()
     */
    public void startRound() throws InterruptedException {
        Timer timerOfPlayer1 = new Timer(100, e -> gui.setTimeOfPlayer1(count += 100));

        Timer timerOfPlayer2 = new Timer(100, e -> gui.setTimeOfPlayer2(count += 100));

        int numberOfQuestions = 1;
        String typeOfGame = "Quick Answer";
        gui.SetTypeOfGame(typeOfGame);

        while(numberOfQuestions<7) {
            gui.MessageNextQuestion();
            while(!gui.EnterPressed)
                Thread.sleep(1);
            gui.HideIcon();
            count=0;
            timerOfPlayer1.start();
            timerOfPlayer2.start();

            gui.SetNumberOfQuestions("   Question : " + numberOfQuestions + " / 6"); // Εμφάνιση στα γραφικά για ενημέρωση του χρήστη
            gui.hide_Or_Show_Answers_Questions(true);

            gui.setGUI_Question_Answers(questionsList.get(0));
            if(!questionsList.get(0).getIconName().equals("noImage"))
                gui.checkIcon(questionsList.get(0).getIconName());
            gui.SetMessage("   ");


            while(!gui.player1hasAnswered || !gui.player2hasAnswered){
                gui.SetTimeMessage("Time Of Player 1: "+ gui.getTimeOfPlayer1()/1000.0+ "      Time Of Player 2: "+ gui.getTimeOfPlayer2()/1000.0);
                Thread.sleep(1);
                if(gui.player1hasAnswered){
                    timerOfPlayer1.stop();
                }
                if(gui.player2hasAnswered)
                    timerOfPlayer2.stop();
            }

            // gui.SetTimeMessage("Time Of Player 1: "+ gui.getTimeOfPlayer1()/1000.0+ "      Time Of Player 2: "+ gui.getTimeOfPlayer2()/1000.0);
            if(gui.IsAnswerOfPlayer1True && gui.IsAnswerOfPlayer2True){
                if(gui.getTimeOfPlayer1()<gui.getTimeOfPlayer2()){
                    player1.addGamePoints(1000);
                    player2.addGamePoints(500);
                }
                else{
                    player1.addGamePoints(500);
                    player2.addGamePoints(1000);
                }
            }
            else if(gui.IsAnswerOfPlayer1True)
                player1.addGamePoints(1000);
            else if(gui.IsAnswerOfPlayer2True)
                player2.addGamePoints(1000);

            gui.showPoints(player1,player2);

            questionsList.remove(0);        // Διαγραφή αντικειμένου από το ArrayList.
            numberOfQuestions++ ;
        }
        gui.EnterPressed=false;
        gui.MessageNextQuestion();
        while(!gui.EnterPressed)
            Thread.sleep(1);
        gui.HideIcon();
        gui.HideTimer();
    }
}