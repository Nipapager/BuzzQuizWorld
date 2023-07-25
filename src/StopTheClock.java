import java.util.List;

public class StopTheClock {
    private final int numberOfUsers; // Ο αριθμός των ομάδων-παιχτών. προς το παρον είναι 1
    private GUI gui;
    private Player player1;
    private Player player2;
    private List<Questions> questionsList; // το ArrayList questionList της main

    /**
     * Constructor της κλάσης StopTheClock ο οποίος έχει ως ορισμα
     * @param numberOfUsers : τον αριθμό τον παιχτών numberOfUsers
     * @param questionsList : την questionList που δημιουργήθηκε στην main,
     * @param gui : το αντικείμενο gui της κλάσης GUI,
     * @param player1
     * @param player2 : τα αντικείμενα player1 και player2 που αντιστοιχούν την πρώτη ομάδα του παιχνιδιού και τη 2η αν αυτή υπάρχει.
     */
    StopTheClock(int numberOfUsers, List<Questions> questionsList, GUI gui, Player player1, Player player2){
        this.questionsList = questionsList;
        this.numberOfUsers = numberOfUsers;
        this.gui = gui;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void startRound() throws InterruptedException {

        String typeOfGame = "Stop The Clock";
        gui.SetTypeOfGame(typeOfGame); // Ορίζεται ο τύπος παιχνιδιού του γύρου ως Stop The Clock
        int numberOfQuestion = 1;
        while (numberOfQuestion < 7) {
            gui.SetNumberOfQuestions("Question : " +numberOfQuestion+ " / 6"); // Εμφάνιση του αριθμού της ερώτησης του γύρου
            gui.EnterPressed=false;
            gui.MessageNextQuestion();  // Ζητείται να πατηθεί το κουμπί Enter για να εμφανιστεί η κατηγορία της επόμενης ερώτησης.

            while(!gui.EnterPressed)   // Το πρόγραμμα περιμένει μέχρι να πατηθεί το Enter
                Thread.sleep(1);

            gui.HideIcon();

            gui.hide_Or_Show_Answers_Questions(true);
            gui.setGUI_Question_Answers(questionsList.get(0)); // Αφού ποντάρουν οι ομάδες εμφανίζεται και η ερώτηση με τις 4 πιθανές απατήσεις της

            if(!questionsList.get(0).getIconName().equals("noImage"))  // Εμφανίζεται και η εικόνα της ερώτησης εάν αυτή υπάρχει
                gui.checkIcon(questionsList.get(0).getIconName());

            gui.setTimer();     // Καλείται η μέθοδος setTimer με την οποία εμφανίζεται στο Panel ένα χρόνομετρο που μετράει αντίστροφα 5 δευτερόλεπτα

            if(gui.numberOfPlayers==1) {        // Εάν παίζει μόνο 1 παίχτης (ή ομάδα) το πρόγραμμα περιμένει την απάντησή της για να συνεχίσει την εκτέλεση
                while(!gui.player1hasAnswered)
                    Thread.sleep(1);
            }
            if(gui.numberOfPlayers==2) {        // Εάν παίζουν 2 παίχτες (ή ομάδες) το πρόγραμμα περιμένει την απάντησή και των 2 για να συνεχίσει την εκτέλεση
                while (!gui.player1hasAnswered || !gui.player2hasAnswered)
                    Thread.sleep(1);
            }

            if(gui.IsAnswerOfPlayer1True)       // Εάν η απάντηση του πρώτου παίχτη (ή ομάδας) είναι σωστή
                player1.addGamePoints(0.2*gui.getSecLeft1());   // προστίθενται οι ανάλογοι πόντοι σύμφωνα με τους κανόνες του παιχνιδιού

            if (numberOfUsers == 2) {       // Εάν η απάντηση του δεύτερου παίχτη (ή ομάδας) είναι σωστή
                if (gui.IsAnswerOfPlayer2True)
                    player2.addGamePoints(0.2 * gui.getSecLeft2()); // προστίθενται οι ανάλογοι πόντοι σύμφωνα με τους κανόνες του παιχνιδιού
            }

            if(gui.numberOfPlayers==1)
                gui.showPoints(player1);    // Εμφάνιση των πόντων της πρώτης ομάδας
            else
                gui.showPoints(player1,player2);    // Εμφάνιση των πόντων της δεύτερης ομάδας

            questionsList.remove(0);        // Διαγραφή του αντικειμένου question που χρησιμοποιήθηκε από το ArrayList.
            numberOfQuestion++;

        }
    }
}