import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Αυτή η κλάση ελέγχει τισ συνάρτησεις getQuestion, getCorrectAnswer, getCategory και getIconName.
 */
class QuestionsTest {

    @Test
    void TestQuestionCategoryAnswerImage() throws IOException {
        Questions aQuestion = new Questions();
        InputStream f = Main.class.getResourceAsStream("/resources/questions.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(f));
        String line = reader.readLine();
        String[] res = line.split("-");
        aQuestion.setCategory(res[0]);
        aQuestion.setQuestion(res[1]);
        aQuestion.setCorrectAnswer(res[6]);
        aQuestion.setIconName(res[7]);

        assertEquals("Which nut is used to make dynamite?",aQuestion.getQuestion());
        assertEquals("Peanuts", aQuestion.getCorrectAnswer());
        assertEquals("Food",aQuestion.getCategory());
        assertEquals("noImage",aQuestion.getIconName());
    }
}