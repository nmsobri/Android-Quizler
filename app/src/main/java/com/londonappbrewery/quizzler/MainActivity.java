package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button trueButton;
    private Button falseButton;
    private TextView textQuestion;
    private TextView textScore;
    private ProgressBar progressBar;
    private int questionIndex;
    private int correctAnswer;

    private TrueFalse[] questionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    final int PROGRESSBAR_INCREMENT = (int) Math.ceil(100.0f / questionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            this.correctAnswer = savedInstanceState.getInt("correctAnswer");
            this.questionIndex = savedInstanceState.getInt("questionIndex");
        } else {
            this.questionIndex = 0;
        }

        this.trueButton = findViewById(R.id.true_button);
        this.falseButton = findViewById(R.id.false_button);
        this.textQuestion = findViewById(R.id.question_text_view);
        this.textScore = findViewById(R.id.score);
        this.progressBar = findViewById(R.id.progress_bar);

        this.textScore.setText(this.correctAnswer + "/" + questionBank.length);
        this.textQuestion.setText(this.questionBank[this.questionIndex].getQuestionID());

        this.trueButton.setOnClickListener(v -> {
            this.checkAnswer(true);
            this.nextQuestion();
        });

        this.falseButton.setOnClickListener(v -> {
            this.checkAnswer(false);
            this.nextQuestion();
        });
    }

    public void nextQuestion() {
        this.questionIndex = (this.questionIndex + 1) % questionBank.length;

        if (this.questionIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setMessage("You score " + this.correctAnswer + " points");
            alert.setCancelable(false);

            alert.setPositiveButton("Close Application", (di, i) -> {
                finish();
            });

            alert.show();
        }

        TrueFalse question = this.questionBank[questionIndex];
        this.textQuestion.setText(question.getQuestionID());
        this.textScore.setText(this.correctAnswer + "/" + questionBank.length);
        this.progressBar.incrementProgressBy(PROGRESSBAR_INCREMENT);
    }

    public void checkAnswer(boolean userAnswer) {
        boolean answer = this.questionBank[this.questionIndex].isAnswer();

        if (userAnswer == answer) {
            this.correctAnswer++;
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("correctAnswer", this.correctAnswer);
        outState.putInt("questionIndex", this.questionIndex);
    }
}
