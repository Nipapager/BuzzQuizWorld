import java.util.List;

/**
 * κλάση Betting η οποία υλοποιεί τον τύπο παιχνιδιού "Ποντάρισμα".
 */

public class Betting {
    private GUI gui;
    private Player player1,player2;
    private final int numberOfUsers; // Ο αριθμός των ομάδων-παιχτών. προς το παρον είναι 1
    private final int []bettingPoints; // Οι πόντοι που ποντάρει η κάθε ομάδα σε κάθε γύρω
    private List<Questions> questionsList; // το ArrayList questionList της main
    private String []playerName;

    /**
     * Constructor της κλάσης StopTheClock ο οποίος έχει ως ορισμα
     * @param numberOfUsers : τον αριθμό τον παιχτών numberOfUsers
     * @param questionsList : την questionList που δημιουργήθηκε στην main,
     * @param gui : το αντικείμενο gui της κλάσης GUI,
     * @param player1
     * @param player2 : τα αντικείμενα player1 και player2 που αντιστοιχούν την πρώτη ομάδα του παιχνιδιού και τη 2η αν αυτή υπάρχει.
     */
    Betting(int numberOfUsers, List<Questions> questionsList, GUI gui, Player player1, Player player2){
        this.questionsList = questionsList;
        this.numberOfUsers = numberOfUsers;
        bettingPoints = new int[numberOfUsers];
        this.gui = gui;
        this.player1 = player1;
        this.player2 = player2;
        playerName = new String[numberOfUsers];
        playerName[0] = gui.GetPlayer1Name();
        if (numberOfUsers==2)
            playerName[1] = gui.GetPlayer2Name();
    }

    /**
     * Η μέθοδος startRound αναπαριστά έναν ολόκληρο γύρο "πονταρίσματος" με 6 ερωτήσης.
     * Εμφανίζεται αρχικά η κατηγορία της ερώτησης και ο παίχτης διαλέγει τους πόντους που θέλει να ποντάρει
     * έπειτα εμφανίζονται οι 4 πιθανές απαντήσεις και ο χρήστης επιλέγει αυτή που θεωρεί σωστή.
     * Η διαδικασία αυτή επαναλαμβάνεται 6 φορές, όσες είναι και οι ερωτήσεις του κάθε γύρου
     * Επιλέγεται κάθε φορά το πρώτο στοιχείο της λίστας questionsList διαγράφεται όταν
     * ο χρήστης απαντήσει στην ερώτηση και μεταβληθούν οι πόντοι του
     * με αυτόν τον τρόπο εξασφαλίζεται ότι δεν θα ξαναεμφανιστεί αυτή η ερώτηση ξανά κατά τη διάρκεια του παιχνιδιού
     */

    public void startRound() throws InterruptedException {

        String typeOfGame = "Betting";
        gui.SetTypeOfGame(typeOfGame);     // Ορίζεται ο τύπος παιχνιδιού του γύρου ως Betting
        int numberOfQuestion = 1;
        while (numberOfQuestion < 7) {
            gui.SetNumberOfQuestions("Question : " +numberOfQuestion+ " / 6"); // Εμφάνιση του αριθμού της ερώτησης του γύρου
            gui.EnterPressed=false;
            gui.MessageNextQuestion();  // Ζητείται να πατηθεί το κουμπί Enter για να εμφανιστεί η κατηγορία της επόμενης ερώτησης.

            while(!gui.EnterPressed)   // Το πρόγραμμα περιμένει μέχρι να πατηθεί το Enter
                Thread.sleep(1);

            gui.HideIcon();

            gui.showCategory(questionsList.get(0).getCategory()); // Εμφανίζεται η κατηγορία της ερώτησης

            for (int pl=0; pl<numberOfUsers; pl++) {
                gui.askPointsBet(playerName[pl] + ":  How many points you want to bet");
                bettingPoints[pl] = gui.getPointsBet();     // Εμφανίζονται οι πόντοι που επιτρέπεται να ποντάρει η κάθε ομάδα σε μορφή κουμπιών
                gui.deletePointsButtons();
            }

            gui.hide_Or_Show_Answers_Questions(true);

            gui.setGUI_Question_Answers(questionsList.get(0));  // Αφού ποντάρουν οι ομάδες εμφανίζεται και η ερώτηση με τις 4 πιθανές απατήσεις της

            if(!questionsList.get(0).getIconName().equals("noImage")) // Εμφανίζεται και η εικόνα της ερώτησης εάν αυτή υπάρχει
                gui.checkIcon(questionsList.get(0).getIconName());

            gui.SetMessage("   ");      // Το panel που ζητάει να πατηθεί το κουμπί Enter για να εμφανιστεί η επόμενη ερώτηση, γίνεται κενό.


            if(numberOfUsers == 1) {        // Εάν παίζει μόνο 1 παίχτης (ή ομάδα) το πρόγραμμα περιμένει την απάντησή της για να συνεχίσει την εκτέλεση
                while(!gui.player1hasAnswered)
                    Thread.sleep(1);
            }
            if(numberOfUsers == 2) {        // Εάν παίζουν 2 παίχτες (ή ομάδες) το πρόγραμμα περιμένει την απάντησή και των 2 για να συνεχίσει την εκτέλεση
                while (!gui.player1hasAnswered || !gui.player2hasAnswered)
                    Thread.sleep(1);
            }


            if(gui.IsAnswerOfPlayer1True)       // Εάν η απάντηση του πρώτου παίχτη (ή ομάδας) είναι σωστή
                player1.addGamePoints(bettingPoints[0]);    // τότε προστίθενται στο σκορ του οι πόντοι που πόνταρε
            else
                player1.addGamePoints(-1*bettingPoints[0]); // εάν είναι λάθος αφερούνται από το σκορ του οι πόντοι που πόνταρε

            if (numberOfUsers == 2) {
                if (gui.IsAnswerOfPlayer2True)   // Εάν η απάντηση του δεύτερου παίχτη (ή ομάδας) είναι σωστή
                    player2.addGamePoints(bettingPoints[1]);    // τότε προστίθενται στο σκορ του οι πόντοι που πόνταρε
                else
                    player2.addGamePoints(-1 * bettingPoints[1]);   // εάν είναι λάθος αφερούνται από το σκορ του οι πόντοι που πόνταρε
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