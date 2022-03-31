package com.mto.topquizz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.mto.topquizz.R;
import com.mto.topquizz.model.Question;
import com.mto.topquizz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textQuestion;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int numberOfQuestion;

    private int score;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private QuestionBank generateQuestions() {

        Question question1 = new Question("Quelle est la date de création du groupe Dire Strait",
                Arrays.asList("1987", "1975", "1977", "1876"),
                2);

        Question question2 = new Question("Qui interprète Still Loving You ?",
                Arrays.asList("Fiction", "Capricorne", "Scorpion", "Sauron"),
                2);

        Question question3 = new Question("Complétez le titre : Can't Help Falling...",
                Arrays.asList("In death", "For you", "In love", "In light"),
                2);

        Question question4 = new Question("Sting à interprété : ",
                Arrays.asList("Born To Run", "Englishman in New York", "Dancing In The Dark", "Your song"),
                1);

        Question question5 = new Question("En quelle année le dernier album des Bee gees est sorti ?",
                Arrays.asList("1991", "2011", "2003", "2001"),
                5);

        Question question6 = new Question("What is the country top-level domain in Belgium ?",
                Arrays.asList(".bg", ".be", ".bm", ".bondage"),
                1);

        Question question7 = new Question("Une guitare possède ",
                Arrays.asList("5 cordes", "7 cordes", "6 cordes", "4 cordes"),
                2);

        Question question8 = new Question("Pour chanter, on utilise",
                Arrays.asList("Un micro-casque USB", "Un trépied", "Un micro", "Un micro-onde"),
                2);

        Question question9 = new Question("Qu'est qu'un arpège",
                Arrays.asList("Une seule note", "Un série de notes", "Une partition", "Un instrument"),
                1);

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5, question6, question7, question8, question9));

    }

    private void displayQuestion(final Question question) {

        textQuestion.setText(question.getQuestion());
        button1.setText(question.getChoiceList().get(0));
        button2.setText(question.getChoiceList().get(1));
        button3.setText(question.getChoiceList().get(2));
        button4.setText(question.getChoiceList().get(3));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        numberOfQuestion = 4;
        mQuestionBank = this.generateQuestions();

        score = 0;

        textQuestion = findViewById(R.id.textQuestion);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setTag(0);
        button2.setTag(1);
        button3.setTag(2);
        button4.setTag(3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
        MediaPlayer game_music = MediaPlayer.create(GameActivity.this, R.raw.game_music);
        game_music.start();

    }

    @Override
    public void onClick(View view) {

        MediaPlayer good_answer = MediaPlayer.create(GameActivity.this, R.raw.click_sound);

        int responseIndex = (int) view.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {

            // good answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            score++;
            good_answer.start();

        } else {

            // Wrong answer
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();

        }

        if (--numberOfQuestion == 0) {

            // End of Game
            endGame();

        } else {

            mCurrentQuestion = mQuestionBank.getQuestion();
            this.displayQuestion(mCurrentQuestion);
        }

    }

    private void endGame() {

        // Display Dialog box with Builder library
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done !")
                .setMessage("Your score is " + score)
                // Positive button is generally button "Yes" or "OK"...
                .setPositiveButton("Ok", (dialogInterface, i) -> {

                    // End of activity
                    Intent intent = new Intent();
                    intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                    setResult(RESULT_OK, intent);
                    finish();

                })

                .create() // Create dialog box
                .show(); // Show Dialog Box

    }

}