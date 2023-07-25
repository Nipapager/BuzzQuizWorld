import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class GUI{
    private JTextField textUsername, textUsername2;
    private JFrame frame;
    private JButton b1, b2, b3, b4;
    private ImageIcon icon;
    private JPanel iconPanel,nextQuestionMessagePanel,player1panel,questionPanel;
    private JLabel ansOfPlayer1,ansOfPlayer2,pointsOf1,pointsOf2,numberOfQuestions,typeOfGame,round,p1, p2,timeLabel,iconLabel,category,question;
    private JLabel message,player2Answer1,player2Answer2,player2Answer3,player2Answer4,player1Answer1,player1Answer2,player1Answer3,player1Answer4;
    private String answerOfPlayer1, answerOfPlayer2, correctAns, player1Name, player2Name;
    public int numberOfPlayers, pointsBet,key;
    private int timeOfPlayer1,timeOfPlayer2;
    public boolean player1hasAnswered,player2hasAnswered,IsAnswerOfPlayer1True,IsAnswerOfPlayer2True,EnterPressed=false, answerChecked = false,startButtonPressed, pointsButtonPressed;
    private long answerTime1, answerTime2, secLeft1, secLeft2;

    public GUI (){

    }
    public void Game () throws InterruptedException {

        // Εμφάνιση ενός μικρού παραθύρου 200x300 στο οποίο ζητείται από τον χρήστη να πατήσει το κουμπί "START"
        frame = new JFrame("Buzz Quiz World");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(false);
        JPanel pressToStartPanel = new JPanel();
        pressToStartPanel.setLayout(new GridLayout(2,0));
        pressToStartPanel.setBackground(Color.cyan);
        JLabel hello = new JLabel("Hello let's play Buzz!",SwingConstants.CENTER);
        hello.setFont(new Font("Calibri", Font.PLAIN, 25));
        JButton startButton = new JButton("START");
        startButton.setFocusable(false);
        pressToStartPanel.add(hello);
        pressToStartPanel.add(startButton);
        frame.add(pressToStartPanel);
        pressToStartPanel.setVisible(true);
        frame.setVisible(true);
        startButtonPressed=false;

        // Εάν πατήσει ο χρήστης ο κουμπί START τότε η boolean μεταβλητή startButtonPressed γίνεται true
        startButton.addActionListener(arg0 -> startButtonPressed=true);

        // Το πρόγραμμα περιμένει έως ότου πατηθεί το START
        while (!startButtonPressed)
            Thread.sleep(1);

        // Όταν πατηθεί το START, διαγράφεται το αρχικό panel και το παράθυρο από 300x200 που ήταν, πλέον καλύπτει όλη την οθόνη
        pressToStartPanel.setVisible(false);
        frame.remove(pressToStartPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setResizable(false);

        // Στο πάνω μέρος του GUI εμφανίζεται ένα ροζ panel που γράφει το όνομα του παιχνιδιού "Buzz Quiz World"
        JLabel hello_label = new JLabel("Buzz Quiz World");
        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.PINK);
        startPanel.add(hello_label);
        frame.add(startPanel, BorderLayout.PAGE_START);

        // στο αριστερό μέρος του GUI εμφανίζεται ένα κίτρινο panel στο οποίο αργότερα θα εμφανίζονται οι 4 πιθανές απαντήσεις που μπορεί να δώσει
        // ο πρώτος παίχτης (ή ομάδα) μαζί με τα αντίστοιχα κουμπιά που πρέπει να πλητρολογίσει για την κάθε απάντηση
        player1panel = new JPanel();
        player1panel.setLayout(new GridLayout(0, 2));
        player1panel.setBackground(Color.YELLOW);
        player1panel.setVisible(false);
        frame.add(player1panel, BorderLayout.LINE_START);

        JLabel ALabel = new JLabel("   A   ");
        player1panel.add(ALabel);
        player1Answer1 = new JLabel("     ");
        player1panel.add(player1Answer1);
        JLabel BLabel = new JLabel("   B   ");
        player1panel.add(BLabel);
        player1Answer2 = new JLabel("      ");
        player1panel.add(player1Answer2);
        JLabel CLabel = new JLabel("   C   ");
        player1panel.add(CLabel);
        player1Answer3 = new JLabel("    ");
        player1panel.add(player1Answer3);
        JLabel DLabel = new JLabel("   D   ");
        player1panel.add(DLabel);
        player1Answer4 = new JLabel("    ");
        player1panel.add(player1Answer4);




        // στη μέση του GUI δημιουργείται ένα πάνελ με όνομα mainPanel το οποίο χωρίζεται σε 3 panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 0));


        /* το πρώτο μέρος του mainPanel που βρίσκεται στην κορυφή είναι το panel1 και περιέχει άλλα 5 panels
         1) typeGamePanel: για τον τύπο του παιχνιδιού (Betting, CorrectAnswer κλπ)
         2) roundPanel: εμφανίζει τον γύρο που βρίσκεται το παιχνίδι την εκάστοτε στιγμή (1 έωσ 6)
         3) numOfQuesPanel: εμφανίζει τον αριθμό της ερώτησης του συγκεκριμένου γύρου που βρίσκεται το παιχνίδι
         4) emptyPanel1: όταν το τύπος παιχνιδιού είναι QuickAnswer εμφανίζει τα δευτερόλεπτα που πέρασαν μέχρι να απαντήσει ο κάθε παίχτης (ή ομάδα)
         ενώ στους υπόλοιπους τύπους παιχνιδιών είναι κενό
         5) ένα κενό panel όπου χρησιμοποιείται για αισθητικούς λόγους, για να χωρίζει το panel1 από το panel2
         */
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5,0));

        JPanel typeGamePanel = new JPanel();
        typeGamePanel.setBackground(Color.cyan);

        JPanel roundPanel = new JPanel();
        roundPanel.setBackground(Color.cyan);

        JPanel numOfQuesPanel = new JPanel();
        numOfQuesPanel.setBackground(Color.cyan);

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBackground(Color.cyan);

        timeLabel= new JLabel(" ",SwingConstants.CENTER);
        timeLabel.setFont(new Font("Calibri", Font.PLAIN, 25));
        emptyPanel1.add(timeLabel);

        typeOfGame = new JLabel("   ",SwingConstants.CENTER);
        typeOfGame.setFont(new Font("Calibri", Font.PLAIN, 25));
        typeGamePanel.add(typeOfGame);

        numberOfQuestions = new JLabel("   ",SwingConstants.CENTER);
        numberOfQuestions.setFont(new Font("Calibri", Font.PLAIN, 25));
        numOfQuesPanel.add(numberOfQuestions);

        round = new JLabel("   ",SwingConstants.CENTER);
        round.setFont(new Font("Calibri", Font.PLAIN, 25));
        roundPanel.add(round);

        panel1.add(typeGamePanel);
        panel1.add(roundPanel);
        panel1.add(numOfQuesPanel);
        panel1.add(emptyPanel1);
        typeGamePanel.setVisible(true);
        numOfQuesPanel.setVisible(true);






        /* το δεύτερο μέρος του mainPanel που βρίσκεται στην μέση είναι το panel2 και περιέχει άλλα 3 panels:
         1) categoryPanel: για την κατηγορία της ερώτησης
         2) questionPanel: για την ερώτηση
         3) nextQuestionMessagePanel: εμφανίζει το μήνυμα "Press ENTER for next question..." όταν και οι 2 παίχτες απαντάνε
         ή το χρονόμετρο των 5 δευτερολέπτων όταν ο τύπος παιχνιδιού είναι Stop The Clock
         */
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,0));
        JPanel categoryPanel = new JPanel();
        questionPanel = new JPanel();
        questionPanel.setVisible(true);
        nextQuestionMessagePanel = new JPanel();

        message = new JLabel("   ",SwingConstants.CENTER);
        message.setFont(new Font("Calibri", Font.PLAIN, 35));
        nextQuestionMessagePanel.add(message);

        category = new JLabel("   ",SwingConstants.CENTER);
        category.setFont(new Font("Calibri", Font.PLAIN, 35));
        categoryPanel.add(category);

        question = new JLabel("   ",SwingConstants.CENTER);
        question.setFont(new Font("Calibri", Font.PLAIN, 25));
        questionPanel.add(question);


        panel2.add(categoryPanel);
        panel2.add(questionPanel);
        panel2.add(nextQuestionMessagePanel);



        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.setVisible(true);

        frame.add(mainPanel);


        AskNumberOfPlayers();

        /* το τρίτο μέρος του mainPanel που βρίσκεται στην μέση είναι το panel3 και περιέχει
        - 2 panels αν ο αριθμός των παιχτών (ή ομάδων) είναι 1:
        1) player1infoPanel : αυτό επίσης χωρίζεται σε 3 panels
                α) p1 : εμφανίζει το όνομα της ομάδας
                β) ansOfPlayer1: εμφανίζει εάν η απάντηση της ομάδας είναι συστή ή λάθος
                γ) pointsOf1: τους συνολικούς πόντους που έχει μαζέψει η ομάδα 1
         2) iconPanel: εμφανίζει την εικόνα που περιέχεται με την εκάστοτε ερώτηση (εάν υπάρχει)
         - Εάν ο αριθμός των ομάδων είναι 2 τότε μαζί με τα παραπάνω panels προστίθεται ένα ακόμα panel στο panel3:
         3)  player2infoPanel : αυτό επίσης χωρίζεται σε 3 panels
                α) p2 : εμφανίζει το όνομα της ομάδας
                β) ansOfPlayer2: εμφανίζει εάν η απάντηση της ομάδας είναι συστή ή λάθος
                γ) pointsOf2: τους συνολικούς πόντους που έχει μαζέψει η ομάδα 2
         */
        JPanel panel3 = new JPanel();
        JPanel player1infoPanel = new JPanel();
        player1infoPanel.setBackground(Color.green);
        player1infoPanel.setLayout(new GridLayout(3,0));
        p1 = new JLabel(" ", SwingConstants.CENTER);
        p1.setVisible(false);
        p1.setFont(new Font("Calibri", Font.PLAIN, 30));
        player1infoPanel.add(p1);
        ansOfPlayer1 = new JLabel("   ",SwingConstants.CENTER);
        ansOfPlayer1.setFont(new Font("Calibri", Font.PLAIN, 25));
        player1infoPanel.add(ansOfPlayer1);
        pointsOf1 = new JLabel("     ",SwingConstants.CENTER);
        pointsOf1.setFont(new Font("Calibri", Font.PLAIN, 25));
        player1infoPanel.add(pointsOf1);
        if (this.numberOfPlayers==2)
            panel3.setLayout(new GridLayout(0, 3));
        else
            panel3.setLayout(new GridLayout(0,2));

        panel3.add(player1infoPanel);
        iconPanel = new JPanel();
        iconPanel.setBackground(Color.green);
        iconLabel = new JLabel();
        iconPanel.add(iconLabel);
        panel3.add(iconPanel);



        /* Εάν οι ομάδες που παίζουν είναι 2 τότε δημιουργείται ένα νέο κόκκινο panel στη δεξιά μεριά του
           παραθύρου στο οποίο αργότερα θα εμφανίζονται οι 4 πιθανές απαντήσεις που μπορεί να δώσει
        // ο δεύετερος παίχτης (ή ομάδα) μαζί με τα αντίστοιχα κουμπιά που πρέπει να πλητρολογίσει για την κάθε απάντηση
         */
        if (this.numberOfPlayers == 2) {
            JPanel player2panel = new JPanel();
            player2panel.setLayout(new GridLayout(0, 2));
            player2panel.setBackground(Color.RED);
            frame.add(player2panel, BorderLayout.LINE_END);
            JLabel oneLabel = new JLabel("   1   ");
            player2panel.add(oneLabel);
            player2Answer1 = new JLabel("      ");
            player2panel.add(player2Answer1);
            JLabel twoLabel = new JLabel("   2   ");
            player2panel.add(twoLabel);
            player2Answer2 = new JLabel("       ");
            player2panel.add(player2Answer2);
            JLabel threeLabel = new JLabel("   3   ");
            player2panel.add(threeLabel);
            player2Answer3 = new JLabel("      ");
            player2panel.add(player2Answer3);
            JLabel fourLabel = new JLabel("   4   ");
            player2panel.add(fourLabel);
            player2Answer4 = new JLabel("      ");
            player2panel.add(player2Answer4);

            JPanel player2infoPanel = new JPanel();
            player2infoPanel.setBackground(Color.green);
            player2infoPanel.setLayout(new GridLayout(3,0));
            p2 = new JLabel(" ",SwingConstants.CENTER);
            p2.setFont(new Font("Calibri", Font.PLAIN, 30));
            p2.setVisible(false);
            player2infoPanel.add(p2);
            ansOfPlayer2 = new JLabel("   ",SwingConstants.CENTER);
            ansOfPlayer2.setFont(new Font("Calibri", Font.PLAIN, 25));
            player2infoPanel.add(ansOfPlayer2);
            pointsOf2 = new JLabel("  ", SwingConstants.CENTER);
            pointsOf2.setFont(new Font("Calibri", Font.PLAIN, 25));

            player2infoPanel.add(pointsOf2);
            panel3.add(player2infoPanel);
        }


        mainPanel.add(panel3);
        hide_Or_Show_Answers_Questions(false);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //KENO
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //KENO

            }
            @Override
            public void keyPressed(KeyEvent e) {
                key = e.getKeyCode();
                if (key == KeyEvent.VK_A) {
                    player1hasAnswered = true;
                    answerTime1 = System.currentTimeMillis();
                    answerOfPlayer1 = player1Answer1.getText();
                    if (numberOfPlayers == 1)
                        AnswerCheck();
                    if (player2hasAnswered)
                        AnswerCheck();
                } else if (key == KeyEvent.VK_B) {
                    player1hasAnswered = true;
                    answerTime1 = System.currentTimeMillis();
                    answerOfPlayer1 = player1Answer2.getText();
                    if (numberOfPlayers == 1)
                        AnswerCheck();
                    if (player2hasAnswered)
                        AnswerCheck();
                } else if (key == KeyEvent.VK_C) {
                    player1hasAnswered = true;
                    answerTime1 = System.currentTimeMillis();
                    answerOfPlayer1 = player1Answer3.getText();
                    if (numberOfPlayers == 1)
                        AnswerCheck();
                    if (player2hasAnswered)
                        AnswerCheck();
                } else if (key == KeyEvent.VK_D) {
                    player1hasAnswered = true;
                    answerTime1 = System.currentTimeMillis();
                    answerOfPlayer1 = player1Answer4.getText();
                    if (numberOfPlayers == 1)
                        AnswerCheck();
                    if (player2hasAnswered)
                        AnswerCheck();
                } else if ((key == KeyEvent.VK_NUMPAD1 || key== KeyEvent.VK_1 ) && numberOfPlayers == 2) {
                    player2hasAnswered = true;
                    answerTime2 = System.currentTimeMillis();
                    answerOfPlayer2 = player2Answer1.getText();
                    if (player1hasAnswered)
                        AnswerCheck();
                } else if ((key == KeyEvent.VK_NUMPAD2 || key== KeyEvent.VK_2 ) && numberOfPlayers == 2) {
                    player2hasAnswered = true;
                    answerTime2 = System.currentTimeMillis();
                    answerOfPlayer2 = player2Answer2.getText();
                    if (player1hasAnswered)
                        AnswerCheck();
                } else if ((key == KeyEvent.VK_NUMPAD3 || key== KeyEvent.VK_3 ) && numberOfPlayers == 2) {
                    player2hasAnswered = true;
                    answerTime2 = System.currentTimeMillis();
                    answerOfPlayer2 = player2Answer3.getText();
                    if (player1hasAnswered)
                        AnswerCheck();
                } else if ((key == KeyEvent.VK_NUMPAD4 || key== KeyEvent.VK_4 ) && numberOfPlayers == 2) {
                    player2hasAnswered = true;
                    answerTime2 = System.currentTimeMillis();
                    answerOfPlayer2 = player2Answer4.getText();
                    if (player1hasAnswered)
                        AnswerCheck();
                } else if (key == KeyEvent.VK_ENTER) {
                    EnterPressed=true;
                }
            }
        });


        // Ζητείται από τον χρήστη (ή τους χρήστες αν είναι 2) να γράψουν το όνομα της ομάδας τους
        NameOfPlayers();
        player1Name = textUsername.getText();
        player2Name = textUsername2.getText();
        p1.setText(player1Name);
        if (numberOfPlayers==2) {
            p2.setText(player2Name);
            p2.setVisible(true);
        }
        p1.setVisible(true);


        CreateMenuBar();
        frame.setVisible(true);
    }       ///end of constructor

    /**
     * Εμφανίζει στο GUI το παρακάτω μήνυμα.
     */
    public void MessageNextQuestion(){
        this.message.setText("Press ENTER for next question...");
    }

    /**
     * Καλείται από την κλάση Betting με σκοπό ο χρήστης να δει την κατηγορία της ερώτησης για να ποντάρει τους ανάλογους πόντους.
     * @param cat το όνομα της κατηγορίας.
     */
    public void showCategory(String cat){
        this.category.setText(cat);
        this.category.setVisible(true);
    }

    /**
     * Αυτή η συνάρτηση εμφανίζει και εξαφανίζει σπό το GUI την ερώτηση, την κατηγορία καθώς και τις πιθανές απαντήσεις ανάλογα
     * με την τιμή του ορίσματος.
     * @param b λογική μεβαλητή με τιμή true ή false
     */
    public void hide_Or_Show_Answers_Questions(boolean b){
        this.category.setVisible(b);
        this.question.setVisible(b);
        player1Answer1.setVisible(b);
        player1Answer2.setVisible(b);
        player1Answer3.setVisible(b);
        player1Answer4.setVisible(b);
        ansOfPlayer1.setVisible(b);
        pointsOf1.setVisible(b);
        if(numberOfPlayers==2) {
            player2Answer1.setVisible(b);
            player2Answer2.setVisible(b);
            player2Answer3.setVisible(b);
            player2Answer4.setVisible(b);
            ansOfPlayer2.setVisible(b);
            pointsOf2.setVisible(b);
        }
    }

    /**
     * Μέθοδος που εμφανίζει τον αριθμό της ερώτησης που βρίσκεται το παιχνίδι
     * @param questions_plus_number : συμβολοσειρά "Question: Αριθμός_Ερώτησης
     */
    public void SetNumberOfQuestions(String questions_plus_number){
        this.numberOfQuestions.setText(questions_plus_number);
    }

    /** Μέθοδος εμφανίζει στο GUI τον τύπο παιχνιδιού του συγκεκριμένου γύρου
     * @param type : συμβολοσειρά type που δίνεται από την κλάση που αναπαριστά τον τύπο παιχνιδιού
     */
    public void SetTypeOfGame(String type){
        this.typeOfGame.setText(type);
    }


    /**
     * Μέθοδος που εμφανίζει στο GUI ένα χρονόμετρο για την κάθε ομάδα.
     * @param type : συμβολοσειρά type που δίνεται από την κάση QuickAnswer και μεφανίζει "Time Of Player 1: Χρόνος_Ομάδας1      Time Of Player 2: Χρόνος_Ομάδας2"
     */
    public void SetTimeMessage(String type){
        this.timeLabel.setText(type);
    }

    /** Μέθοδος που αλλάζει το μήνυμα που υπάρχει στο panel nextQuestionMessagePanel
     *
     * @param aMessage το μήνυμα που θα εμφανίζεται
     */
    public void SetMessage(String aMessage){
        this.message.setText(aMessage);
    }

    /** Μέθοδος που στο questionPanel εμφανίζει μήνυμα ερώτηση προς τον χρήστη
     *
     * @param askPoints : μήνυμα που ρωτάει τον χρήστη το ποσό των πόντων που θέλει να ποντάρει
     *                  στο type game Betting
     */
    public void askPointsBet(String askPoints){
        this.question.setText(askPoints);
        this.question.setVisible(true);
    }

    /** Μέθοδος που εμφανίζει στο GUI στο panel nextQuestionMessagePanel 4 κουμπιά με τους πόντους
     * που μπορεί να ποντάρει ο χρήστης σύμφωνα με τους κανόνες του παιχνιδιού (250, 500, 750 ή 1000)
     * @return pointsBet : επιστρέφει τους πόντους που επέλεξε ο χρήστης να ποντάρει
     * @throws InterruptedException για την μέθοδο Thread.sleep()
     */
    public int getPointsBet() throws InterruptedException {
        nextQuestionMessagePanel.setVisible(false);
        nextQuestionMessagePanel.remove(message);
        nextQuestionMessagePanel.setLayout(new GridLayout(1,4));
        pointsButtonPressed = false;
        b1 = new JButton("250");
        b2 = new JButton("500");
        b3 = new JButton("750");
        b4 = new JButton("1000");
        b1.setFocusable(false);
        b2.setFocusable(false);
        b3.setFocusable(false);
        b4.setFocusable(false);
        nextQuestionMessagePanel.add(b1);
        nextQuestionMessagePanel.add(b2);
        nextQuestionMessagePanel.add(b3);
        nextQuestionMessagePanel.add(b4);
        nextQuestionMessagePanel.setVisible(true);
        b1.addActionListener(arg0 -> {
            pointsBet = 250;
            pointsButtonPressed = true;
        });
        b2.addActionListener(arg0 -> {
            pointsBet = 500;
            pointsButtonPressed = true;
        });
        b3.addActionListener(arg0 -> {
            pointsBet = 750;
            pointsButtonPressed = true;
        });
        b4.addActionListener(arg0 -> {
            pointsBet = 1000;
            pointsButtonPressed = true;
        });
        while (!pointsButtonPressed)
            Thread.sleep(1);

        return pointsBet;
    }

    /* ΑΦού ο χρήστης επιλέξει τους πόντους που θέλει να ποντάρει, στη συνέχεια καλείται αυτή η
     * μέθοδος για να σβήσει από το GUI τα 4 κουμπία
     */
    public void deletePointsButtons(){
        nextQuestionMessagePanel.setVisible(false);
        nextQuestionMessagePanel.remove(b1);
        nextQuestionMessagePanel.remove(b2);
        nextQuestionMessagePanel.remove(b3);
        nextQuestionMessagePanel.remove(b4);
        nextQuestionMessagePanel.setLayout(new GridLayout(0,1));
        message.setText("   ");
        nextQuestionMessagePanel.add(message);
        nextQuestionMessagePanel.setVisible(true);
    }

    /** Μεθοδος που ορίζει τον γύρο που βρίσκεται το παιχνίδι την εκάστοτε στιγμή
     * @param round : ο γύρος του παιχνιδιού
     */
    public void setRound(int round){this.round.setText("Round: "+round); }


    /* Μέθοδος που χρησιμοποιείται όταν ο τύπος παιχνιδιού είναι Stop The Clock
       εμφανίζει ένα χρονόμετρο 5 δευτερολέπτων μέσα στο οποίο οι χρήστες έχουν τη δυνατότητα να απαντήσουν
       στην ερώτηση. Εάν περάσουν τα 5 δευτερόλεπτα και δεν απαντήσει κάποιο χρήστης τότε εμφανίζεται στο
       panel του "No answer". Όταν κάποιος χρήστης απαντήσει τότε υπολογίζονται τα δευτερόλεπτα που απέμειναν
       μέχρι το τέλος του χρονομέτρου, για να υπολογιστούν μετά οι πόντοι που κέρδισε
     */
    public void setTimer(){
        int sec = 4;
        long temp;
        nextQuestionMessagePanel.setVisible(false);
        nextQuestionMessagePanel.remove(message);
        JLabel timerLabel = new JLabel("5",SwingConstants.CENTER);
        timerLabel.setFont(new Font("Calibri", Font.PLAIN, 25));
        nextQuestionMessagePanel.add(timerLabel);

        nextQuestionMessagePanel.setVisible(true);
        player1hasAnswered=false;
        if (numberOfPlayers == 1)
            player2hasAnswered=true;
        long start = System.currentTimeMillis();
        temp = start+1000;
        while ((System.currentTimeMillis() < start + 5*1000) && (!player1hasAnswered || !player2hasAnswered)) {
            if (System.currentTimeMillis() > temp) {
                timerLabel.setText(String.valueOf(sec));
                sec--;
                temp+=1000;
            }


        }
        if (!player1hasAnswered){
            answerOfPlayer1 = " ";
        }
        if (!player2hasAnswered){
            answerOfPlayer2 = " ";
        }

        player1hasAnswered=true;
        player2hasAnswered=true;
        AnswerCheck();
        if (!answerOfPlayer1.equals(" "))
            secLeft1 = (start + 5*1000) - answerTime1;
        if (!answerOfPlayer2.equals(" "))
            secLeft2 = (start + 5*1000) - answerTime2;
        nextQuestionMessagePanel.setVisible(false);
        nextQuestionMessagePanel.remove(timerLabel);
        nextQuestionMessagePanel.add(message);
        nextQuestionMessagePanel.setVisible(true);
    }

    /**
     * Ένας setter της μεταβλητής secLeft1.
     * @param sec τα δευτερόλεπτα
     */
    public void setSecLeft1(long sec){
        this.secLeft1=sec;
    }

    /** Μεθοδος που επιστρέφει τα δευερόλεπτα που απέμειναν μέχρι να τελειώσει ο χρόνος μετά την
     * απάντηση του πρώτου χρήστη
     * @return secLeft1: τα δευτερόλεπτα
     */
    public long getSecLeft1(){
        return secLeft1;
    }

    /** Μεθοδος που επιστρέφει τα δευερόλεπτα που απέμειναν μέχρι να τελειώσει ο χρόνος μετά την
     * απάντηση του δεύτερου χρήστη
     * @return secLeft2: τα δευτερόλεπτα
     */
    public long getSecLeft2(){
        return secLeft2;
    }

    /**
     * Η συνάρτηση αυτή καλείται από τις κλάσεις τύπων παιχνιδιών και σκοπός της είναι να εμφανιστούν στο frame η ερώτηση,
     * οι πιθανές απαντήσεις και η κατηγορία καθώς και να αποηθηκευτεί και η σωστή απάντηση για να γίνει παρακάτω ο έλεγχος
     * ορθότητας.
     * @param aQuest αντικείμενο της κλάσης Question που περιέχει την ερώτηση, την κατηγορία, τις απαντήσεις, και
     * την σωστή απάντηση.
     */

    public void setGUI_Question_Answers(Questions aQuest) {
        EnterPressed=false;
        this.category.setText(aQuest.getCategory());
        this.question.setText(aQuest.getQuestion());
        player1hasAnswered=false;
        player1Answer1.setText(aQuest.answers.get(0));
        player1Answer2.setText(aQuest.answers.get(1));
        player1Answer3.setText(aQuest.answers.get(2));
        player1Answer4.setText(aQuest.answers.get(3));
        correctAns = aQuest.getCorrectAnswer();
        if (this.numberOfPlayers == 2) {
            player2hasAnswered=false;
            player2Answer1.setText(aQuest.answers.get(0));
            player2Answer2.setText(aQuest.answers.get(1));
            player2Answer3.setText(aQuest.answers.get(2));
            player2Answer4.setText(aQuest.answers.get(3));
        }
    }

    /**
     * Η συνάρτηση αυτή καλέιται από τον κατασκευαστή με σκοπό ο χρήστης να επιλέξει αν θα πάιξουν ένας ή δύο παίκτες.
     * Εμφανίζεται στην οθόνη ένα JOptionPane και υπάρχουν οι επιλογές 1 και 2 που επιλέγονται με το ποντίκι.
     * Το αποτέλεσμα θα αποθηκευτεί στην μεταβλητή numberOfPlayers και στην συνέχεια θα εμφανιστεί το frame του παιχνιδιού.
     */
    public void AskNumberOfPlayers(){
        Object[] options={1,2};
        int x= JOptionPane.showOptionDialog(frame,"Select the number of Players","Number of PLayers.",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        if(x==0) {
            this.numberOfPlayers = 1;
            questionPanel.setVisible(true);
            player1panel.setVisible(true);
        }
        else if(x==1) {
            this.numberOfPlayers = 2;
            questionPanel.setVisible(true);
            player1panel.setVisible(true);
        }
        else
            frame.setVisible(false);
    }

    /**
     * Η συνάρτηση αυτή εμφανίζει στο GUI τους συνολικούς πόντους του παίκτη.
     * @param player1 αντικείμενο της κλάσης Player που αντιστοιχεί στον έναν και μοναδικό παίκτη.
     */
    public void showPoints(Player player1){
        pointsOf1.setText("Total Points: "+ player1.getGamePoints());
    }

    /**
     * Η συνάρτηση αυτή εμφανίζει στο GUI τους συνολικούς πόντους των παικτών.
     * @param player1 αντικείμενο της κλάσης Player που αντιστοιχεί στον πρώτο παίκτη.
     * @param player2 αντικείμενο της κλάσης Player που αντιστοιχεί στο δεύτερο παίκτη.
     */
    public void showPoints(Player player1, Player player2){
        pointsOf1.setText("Total Points:  "+ player1.getGamePoints());
        pointsOf2.setText("Total Points:  "+ player2.getGamePoints());
    }

    /**
     * Η συνάρτηση αυτή ελέγχει αν η απάντηση του κάθε παίκτη είναι σωστή και εμφανίζει το κατάλληλο μήνυμα στο GUI
     * ανάλογα με το αποτέλεσμα. Καλείται από τις κλάσεις των τύπων παιχνιδιών.
     */
    public void AnswerCheck() {
        if(numberOfPlayers==1) {
            if (answerOfPlayer1.equals(correctAns)) {
                ansOfPlayer1.setText("Correct Answer!");
                IsAnswerOfPlayer1True = true;
            }else if (answerOfPlayer1.equals(" ")){
                ansOfPlayer1.setText("No answer!");
                IsAnswerOfPlayer1True = false;
            } else {
                ansOfPlayer1.setText("Incorrect Answer!");
                IsAnswerOfPlayer1True = false;
            }

        }
        if(numberOfPlayers==2) {
            if (answerOfPlayer1.equals(correctAns)) {
                ansOfPlayer1.setText("Correct Answer!");
                IsAnswerOfPlayer1True = true;
            } else if (answerOfPlayer1.equals(" ")) {
                ansOfPlayer1.setText("No answer!");
                IsAnswerOfPlayer1True = false;
            } else {
                ansOfPlayer1.setText("Incorrect Answer!");
                IsAnswerOfPlayer1True = false;
            }
            if (answerOfPlayer2.equals(correctAns)) {
                ansOfPlayer2.setText("Correct Answer!");
                IsAnswerOfPlayer2True = true;
            } else if (answerOfPlayer2.equals(" ")) {
                ansOfPlayer2.setText("No answer!");
                IsAnswerOfPlayer2True = false;
            } else {
                ansOfPlayer2.setText("Incorrect Answer!");
                IsAnswerOfPlayer2True = false;
            }
        }
        answerChecked = true;
    }

    /**
     * Setter για την μεταβλητη timeOfPlayer1
     * @param timeOfPlayer1
     */
    public void setTimeOfPlayer1(int timeOfPlayer1) {
        this.timeOfPlayer1 = timeOfPlayer1;
    }

    /**
     * Setter για την μεταβλητη timeOfPlayer2
     * @param timeOfPlayer2
     */
    public void setTimeOfPlayer2(int timeOfPlayer2) {
        this.timeOfPlayer2 = timeOfPlayer2;
    }

    /**
     * Getter για την μεταβλητη timeOfPlayer1
     * @return την μεταβήτη timeOfPlayer1
     */
    public int getTimeOfPlayer1() {
        return timeOfPlayer1;
    }

    /**
     * Getter για την μεταβλητη timeOfPlayer2
     * @return την μεταβήτη timeOfPlayer2
     */
    public int getTimeOfPlayer2() {
        return timeOfPlayer2;
    }

    /**
     * Η συνάρτηση αυτή καλείται από της κλάσεις τύπων παιχνιδιών και ελέγχει αν στην ερώτηση που θα εμφανιστεί
     * υπάρχει εικόνα, αν υπάρχει την εμφανίζει στο frame.
     * @param iconName το όνομα της εικόνας σε String πχ."cup.jpg" ή αν δεν υπάρχει εικόνα το iconName = "noImage"
     */
    public void checkIcon(String iconName){
        String imgName = "images/"+iconName;
        URL imageURL = getClass().getResource(imgName);
        if (imageURL != null) {
            icon = new ImageIcon(new ImageIcon(imageURL).getImage().getScaledInstance(iconPanel.getWidth(), iconPanel.getHeight(), Image.SCALE_DEFAULT));
        }

        iconLabel.setIcon(icon);
        iconLabel.setVisible(true);
    }

    /**
     * Εξαφανίζει την εικόνα από το frame ώστε να μην εμφανίζεται στην επόμενη ερώτηση.
     */
    public void HideIcon(){
        iconLabel.setVisible(false);
    }
    public void HideTimer() {timeLabel.setVisible(false);}
    public String GetPlayer1Name(){
        return player1Name;
    }
    public String GetPlayer2Name(){
        return player2Name;
    }

    /**
     * Η συνάρτηση αυτή εμφανίζει ένα JOptionPane στην οθόνη με σκοπό οι παίκτες να συπμληρώσουν τα ψευδώνυμα τους για
     * το παιχνίδι.
     */
    public void NameOfPlayers() {
        textUsername = new JTextField(15);
        textUsername2 = new JTextField(15);
        JPanel namesPanel = new JPanel(new GridLayout(0,4));
        JLabel askName1 = new JLabel("Name of Player 1:");
        namesPanel.add(askName1);
        namesPanel.add(textUsername);


        if(numberOfPlayers==2){
            JLabel askName2 = new JLabel(" Name of Player 2:");
            namesPanel.add(askName2);
            namesPanel.add(textUsername2);
        }

        JOptionPane.showMessageDialog(null, namesPanel);

    }

    /**
     * Η συνάρτηση δημιουργεί ένα JMenuBar στο frame με σκοπό να διευκολύνει τον χρήστη και να του δώσει επιπλέον επιλογές.
     * Υπάρχουν 3 JMenu αντικείμενα: a) menuHistory που εμφανίζει τα σκορ των παικτών που έχουν παίξει το παχνίδι
     * b) menuFile που δίνει την δυαντότητα στον χρήστη να τερματίσει το πρόγραμμα και c) menuSolutions που περίέχει της
     * λύσεις των ερωτήσεων.
     */
    public void CreateMenuBar(){
        JMenuBar menubar = new JMenuBar();
        JMenu menuHistory = new JMenu("History");
        JMenuItem  score_records = new JMenuItem("Score Records");
        score_records.addActionListener(e -> {
            File file = new File("src\\resources\\score.txt");
            if(!Desktop.isDesktopSupported()){
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        menuHistory.add(score_records);
        menubar.add(menuHistory);
        JMenu menuFile = new JMenu("File");
        JMenuItem close = new JMenuItem("Close");
        menuFile.add(close);
        close.addActionListener(e -> System.exit(1));
        menubar.add(menuFile);
        JMenu menuSolutions = new JMenu("Solutions");
        JMenuItem seeSolutions = new JMenuItem("See Solutions");
        seeSolutions.addActionListener(e -> {
            File file = new File("src\\resources\\solutions.txt");
            if(!Desktop.isDesktopSupported()){
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        menuSolutions.add(seeSolutions);
        menubar.add(menuSolutions);
        frame.setJMenuBar(menubar);
    }

    /**
     * Η μέθοδος καλείται όταν το παιχνίδι φτάσει στο ΄τελος του, δηλαδή όταν ολοκληρωθούν και οι 6 γύροι.
     * Εμφανίζεται ένα ευχαριστήριο μήνυμα προς τους χρήστες που έπαιξαν το παιχνίδι.
     * Εάν οι ομάδες είναι 2 τότε εμφανίζει και το όνομα της ομάδας που κέρδισε ή DRAW σε περίπτωση ισοπαλίας
     * στο κάτω μέρος εμφανίζονται οι τελικοί πόντοι της ομάδας (ή των ομάδων).
     * @param player1 : αντικείμενο που αντιστοιχεί στην ομάδα 1. Χρησιμοποιείται για τη σύγκριση των τελικών πόντων
     * @param player2 : αντικείμενο που αντιστοιχεί στην ομάδα 2. Χρησιμοποιείται για τη σύγκριση των τελικών πόντων
     */
    public void endGame(Player player1, Player player2){
        frame.setVisible(false);
        frame.getContentPane().removeAll();
        frame.repaint();

        JPanel mainEndPanel = new JPanel();
        mainEndPanel.setLayout(new GridLayout(2,0));

        if (numberOfPlayers==1){
            JPanel endPanel1 = new JPanel();
            endPanel1.setBackground(Color.cyan);

            JLabel messageThanks = new JLabel("Thanks for playing!", SwingConstants.CENTER);
            messageThanks.setFont(new Font("Calibri", Font.PLAIN, 80));

            endPanel1.add(messageThanks);
            mainEndPanel.add(endPanel1);

        }

        if (numberOfPlayers==2) {
            JPanel endPanel1 = new JPanel();
            endPanel1.setLayout(new GridLayout(2, 0));
            endPanel1.setBackground(Color.cyan);


            JLabel messageThanks = new JLabel("Thanks for playing!", SwingConstants.CENTER);
            messageThanks.setFont(new Font("Calibri", Font.PLAIN, 80));

            JLabel messagePlayerWin;

            if (player1.getGamePoints() > player2.getGamePoints()) {
                messagePlayerWin = new JLabel(player1Name + " won!", SwingConstants.CENTER);
                messagePlayerWin.setFont(new Font("Calibri", Font.PLAIN, 80));
            }
            else if ((player1.getGamePoints() < player2.getGamePoints())){
                messagePlayerWin = new JLabel(player2Name + " won!", SwingConstants.CENTER);
                messagePlayerWin.setFont(new Font("Calibri", Font.PLAIN, 80));
            }
            else{
                messagePlayerWin = new JLabel(" DRAW", SwingConstants.CENTER);
                messagePlayerWin.setFont(new Font("Calibri", Font.PLAIN, 80));
            }

            endPanel1.add(messageThanks);
            endPanel1.add(messagePlayerWin);
            mainEndPanel.add(endPanel1);
        }

        JLabel messageTotalPoints1 = new JLabel("Total Points: ", SwingConstants.CENTER);
        messageTotalPoints1.setFont(new Font("Calibri", Font.PLAIN, 50));
        p1.setFont(new Font("Calibri", Font.PLAIN, 50));
        pointsOf1.setFont(new Font("Calibri", Font.PLAIN, 50));



        if (numberOfPlayers==1) {
            JPanel endPanel2 = new JPanel();
            endPanel2.setLayout(new GridLayout(3,0));

            endPanel2.setBackground(Color.green);
            endPanel2.add(p1);
            endPanel2.add(messageTotalPoints1);
            endPanel2.add(pointsOf1);
            endPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
            mainEndPanel.add(endPanel2);

        }

        if (numberOfPlayers==2) {
            JPanel endPanel2 = new JPanel();
            endPanel2.setLayout(new GridLayout(3,2));
            JLabel messageTotalPoints2 = new JLabel("Total Points: ", SwingConstants.CENTER);
            messageTotalPoints2.setFont(new Font("Calibri", Font.PLAIN, 50));
            endPanel2.setBackground(Color.green);
            p2.setFont(new Font("Calibri", Font.PLAIN, 50));
            pointsOf2.setFont(new Font("Calibri", Font.PLAIN, 50));
            endPanel2.add(p1);
            endPanel2.add(p2);
            endPanel2.add(messageTotalPoints1);
            endPanel2.add(messageTotalPoints2);
            endPanel2.add(pointsOf1);
            endPanel2.add(pointsOf2);
            endPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
            mainEndPanel.add(endPanel2);

        }

        frame.add(mainEndPanel);
        frame.setVisible(true);


    }

}