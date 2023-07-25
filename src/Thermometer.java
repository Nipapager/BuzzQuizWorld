import java.util.List;

public class Thermometer {
    private GUI gui;
    private Player player1;
    private Player player2;
    private int correct1;
    private int correct2;
    private List<Questions> questionsList; // το ArrayList questionList της main


    /**
     * Constructor της κλάσης Thermometer ο οποίος έχει ως ορισμα
     * @param questionsList : την questionList που δημιουργήθηκε στην main,
     * @param gui : το αντικείμενο gui της κλάσης GUI,
     * @param player1
     * @param player2 : τα αντικείμενα player1 και player2 που αντιστοιχούν την πρώτη ομάδα του παιχνιδιού και τη 2η αν αυτή υπάρχει.
     */
    Thermometer(List<Questions> questionsList, GUI gui, Player player1, Player player2){
        this.questionsList = questionsList;
        this.correct1 = 0;
        this.correct2 = 0;
        this.gui = gui;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void startRound() throws InterruptedException {
        String typeOfGame = "Thermometer";
        gui.SetTypeOfGame(typeOfGame); // Ορίζεται ο τύπος παιχνιδιού του γύρου ως Thermometer
        int numberOfQuestion = 1;
        while (numberOfQuestion < 7) {
            gui.SetNumberOfQuestions("Question : " +numberOfQuestion + " / 6"); // Εμφάνιση στα γραφικά για ενημέρωση του χρήστη
            gui.EnterPressed = false;
            gui.MessageNextQuestion();  // Ζητείται να πατηθεί το κουμπί Enter για να εμφανιστεί η κατηγορία της επόμενης ερώτησης.

            while(!gui.EnterPressed)   // Το πρόγραμμα περιμένει μέχρι να πατηθεί το Enter
                Thread.sleep(1);
            gui.HideIcon();
            gui.hide_Or_Show_Answers_Questions(true);
            gui.setGUI_Question_Answers(questionsList.get(0)); // Αφού ποντάρουν οι ομάδες εμφανίζεται και η ερώτηση με τις 4 πιθανές απατήσεις της

            if(!questionsList.get(0).getIconName().equals("noImage"))  // Εμφανίζεται και η εικόνα της ερώτησης εάν αυτή υπάρχει
                gui.checkIcon(questionsList.get(0).getIconName());

            gui.SetMessage("   ");


            // το πρόγραμμα περιμένει την απάντησή και των 2 ομάδων για να συνεχίσει την εκτέλεση
            while (!gui.player1hasAnswered || !gui.player2hasAnswered)
                Thread.sleep(1);

            if(gui.IsAnswerOfPlayer1True)       // Εάν η απάντηση του πρώτου παίχτη (ή ομάδας) είναι σωστή
                correct1++;                     // αυξάνονται οι σωστές απαντήσεις του κατά 1

            if(gui.IsAnswerOfPlayer2True)   // Εάν η απάντηση του δεύτερου παίχτη (ή ομάδας) είναι σωστή
                correct2++;                 // αυξάνονται οι σωστές απαντήσεις του κατά 1

            if (correct1>=5)                    // Εάν η πρώτη ομάδα απάντησε σε 5 ερωτήσεις του γύρου Thermometer σωστά
                player1.addGamePoints(5000);    // τότε κερδίζει 5000 πόντους
            if (correct2>=5)                    // Εάν η δεύτερη ομάδα απάντησε σε 5 ερωτήσεις του γύρου Thermometer σωστά
                player2.addGamePoints(5000);    // τότε κερδίζει 5000 πόντους

            gui.showPoints(player1,player2); // Εμφάνιση των πόντων των 2 ομάδων

            questionsList.remove(0);        // Διαγραφή αντικειμένου από το ArrayList.
            numberOfQuestion++;

        }
        gui.HideIcon();
    }
}