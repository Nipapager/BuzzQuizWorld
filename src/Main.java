import java.io.*;
import java.util.*;
import java.util.List;

public class Main {

    /**
     * Αυτή η συνάρτηση αντιγράφει από το αρχείο κειμένου "questions.txt" τις ερωτήσεις, τις πιθανές απαντήσεις,
     * τις σωστές απαντήσεις, τις κατηγορίες, τα ονόματα των εικόνων και τις αποθηκεύει σε αντικείμενα Questions.
     *
     * @param questionsList η λίστα που περιέχει αντικείμενα Questions με τις κατηγορίες, τις ερωτήσεις κάθε
     *                      κατηγορίας, τις πιθανές απαντήσεις , την σωστή απάντηση και το όνομα αρχείου της εικόνας.
     * @throws IOException εξαίρεση για το άνοιγμα του αρχείου.
     */
    public static void readQuestionsFromFile(List<Questions> questionsList) throws IOException {
        InputStream f = Main.class.getResourceAsStream("/resources/questions.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(f));
        String line;
        int numberOfLines = 83;// να βλαω συναρτηση που διαβαζει τον αριθμο σειρων

        for (int i = 0; i < numberOfLines; i++) {
            Questions aQuestion = new Questions();
            line = reader.readLine();
            String[] res = line.split("-");
            aQuestion.setCategory(res[0]);
            aQuestion.setQuestion(res[1]);
            for (int j = 2; j < 6; j++)
                aQuestion.setAnswers(res[j]);
            aQuestion.shuffleAnswers();                     // Αποθηκεύει τις απαντήσεις σε τυχαία σειρά.
            aQuestion.setCorrectAnswer(res[6]);
            aQuestion.setIconName(res[7]);
            questionsList.add(aQuestion);
        }
        Collections.shuffle(questionsList);                  //Αποθηκεύει τα αντικείμενα Questions σε τυχαία σειρά.

    }

    /**
     * Αυτή η μέθοδος υλοποιεί το παχνίδι και έχει διάρκεια 6 γύρων. Υπάρχουν 5 τύποι παιχνιδών το "Correct Answer", το
     * "Betting", το "Stop The Clock", το "QuickAnswer" και το "Thermometer". Ο τύπος παιχνιδιού επιλέγεται τυχαία σε κάθε
     * γύρο ανάλογα με τον αριθμό των παικτών (1 ή 2).
     *
     * @param questionsList η λίστα που περιέχει αντικείμενα Questions με τις κατηγορίες, τις ερωτήσεις κάθε
     *                      κατηγορίας, τις πιθανές απαντήσεις , την σωστή απάντηση και το όνομα αρχείου της εικόνας.
     * @throws InterruptedException χρειάζεται για τα αντικείμενα των κλάσεων που δηλώνονται και τις μεθόδους που χρησιμοποιούνται.
     */
    public static void Game(List<Questions> questionsList) throws InterruptedException {
        int laps = 0;
        Player player = new Player();   // Αντικείμενο της κλάσης Player για της καταγραφή των πόντων του παίκτη.
        Player player2 = new Player();
        CorrectAnswer correct_answer;
        Random ran = new Random();
        GUI gui = new GUI();
        gui.Game();
        while (laps < 6) {//Διάρκεια 6 γύρων.
            gui.setRound(laps + 1);
            int typeGame;
            if (gui.numberOfPlayers == 1)
                typeGame = ran.nextInt(2); // Για έναν παίκτη δεν παίζονται όλοι οι τύποι παιχνιδιού
            else
                typeGame = ran.nextInt(4);
            switch (typeGame) {
                case 0:                                 // Η πρώτη περίπτωση είναι ο τύπος παιχνιδιού "Correct Answer".
                    if (gui.numberOfPlayers == 1)
                        correct_answer = new CorrectAnswer(questionsList, gui, player);
                    else
                        correct_answer = new CorrectAnswer(questionsList, gui, player, player2); //Δημιουργία αντικειμένου κλάσης Correct Answer.

                    correct_answer.startRound();
                    break;
                case 1:                               // Η δεύτερη περίπτωση είναι ο τύπος παιχνιδιού "Betting".

                    Betting bet = new Betting(gui.numberOfPlayers, questionsList, gui, player, player2);
                    bet.startRound();
                    break;
                case 2:
                    StopTheClock stc = new StopTheClock(gui.numberOfPlayers, questionsList, gui, player, player2);
                    stc.startRound();
                    break;
                case 3:
                    QuickAnswer quickAnswer = new QuickAnswer(questionsList, gui, player, player2);
                    quickAnswer.startRound();
                    break;
                case 4:
                    Thermometer thermometer = new Thermometer(questionsList, gui, player, player2);
                    thermometer.startRound();
                    break;
            }
            laps++;
        }
        gui.endGame(player, player2);
        if (gui.numberOfPlayers == 1)
            SaveOneScoreToFile(gui, player);
        else
            SaveTwoScoresToFile(gui, player, player2);
    }


    /**
     * Αποθηκεύει το σκορ του παίκτη καθώς και το όνομά του στο αρχείο με όνομα "score.txt". Καλείται από την συνάρτηση Game
     * ανάλογα με των αριθμό των παικτών (1 ή 2).
     *
     * @param gui    το αντικείμενο GUI που δημιουργήθηκε.
     * @param player αντικείμενο του μοναδικού παίκτη που περιέχει τους πόντους του.
     */
    public static void SaveOneScoreToFile(GUI gui, Player player) {
        try {
            FileWriter fw = new FileWriter("src\\resources\\score.txt", true);
            fw.write(gui.GetPlayer1Name() + "with  Score : " + player.getGamePoints() + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Αποθηκεύει το σκορ των παικτών καθώς και τα ονόματά τους στο αρχείο με όνομα "score.txt". Καλείται από την συνάρτηση Game
     * ανάλογα με των αριθμό των παικτών (1 ή 2).
     *
     * @param gui     το αντικείμενο GUI που δημιουργήθηκε.
     * @param player1 αντικείμενο του πρώτου παίκτη που περιέχει τους πόντους του.
     * @param player2 αντικείμενο του δεύτερου παίκτη που περιέχει τους πόντους του.
     */
    public static void SaveTwoScoresToFile(GUI gui, Player player1, Player player2) {
        try {
            FileWriter fw = new FileWriter("src\\resources\\score.txt", true);
            fw.write(gui.GetPlayer1Name() + " with  Score : " + player1.getGamePoints() + "\n");
            fw.write(gui.GetPlayer2Name() + " with  Score : " + player2.getGamePoints() + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        //System.out.println("Hello, let's play Buzz!\n");
        List<Questions> questionsList = new ArrayList<>();

        readQuestionsFromFile(questionsList);
        Game(questionsList);

    }
}