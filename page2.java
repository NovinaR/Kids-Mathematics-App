package com.example.kids;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class page2 extends AppCompatActivity {

    Button b4,b6,b7,b10;
    ImageView que;
    TextView que1;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;

        ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

        String quizData[][] = {
                // {"Image Name", "Right Answer", "Choice1", "Choice2", "Choice3"}
                {"b7", "4+3", "3+3", "3+2", "7+2"},
                {"c10", "12-2", "10-1", "10-3", "11-2"},
                {"a6", "10-4", "8-1", "10-5", "15-7"},
                {"p4", "2+2", "4+1", "1+2", "0+2"},
                {"d2", "4-2", "2-1", "5-2", "10-6"},
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_page1);

            b4=findViewById(R.id.b4);
            b6=findViewById(R.id.b6);
            b7=findViewById(R.id.b7);
            b10=findViewById(R.id.b10);
            que=findViewById(R.id.que);
            que1=findViewById(R.id.que1);

            // Create quizArray from quizData.
            for (int i = 0; i < quizData.length; i++) {
                // Prepare array.
                ArrayList<String> tmpArray = new ArrayList<>();
                tmpArray.add(quizData[i][0]); // Image Name
                tmpArray.add(quizData[i][1]); // Right Answer
                tmpArray.add(quizData[i][2]); // Choice1
                tmpArray.add(quizData[i][3]); // Choice2
                tmpArray.add(quizData[i][4]); // Choice3

                // Add tmpArray to quizArray.
                quizArray.add(tmpArray);
            }

            showNextQuiz();
        }
        public void showNextQuiz() {

            // Update quizCountLabel.
            que1.setText("____ + OR - ____ = ??");

            // Generate random number between 0 and 4 (quizArray's size -1)
            Random random = new Random();
            int randomNum = random.nextInt(quizArray.size());

            // Pick one quiz set.
            ArrayList<String> quiz = quizArray.get(randomNum);

            // Set Image and Right Answer.
            // Array format: {"Image Name", "Right Answer", "Choice1", "Choice2", "Choice3"}
            que.setImageResource(
                    getResources().getIdentifier(quiz.get(0), "drawable", getPackageName()));
            rightAnswer = quiz.get(1);

            // Remove "Image Name" from quiz and shuffle choices.
            quiz.remove(0);
            Collections.shuffle(quiz);

            // Set choices.
            b4.setText(quiz.get(0));
            b6.setText(quiz.get(1));
            b7.setText(quiz.get(2));
            b10.setText(quiz.get(3));

            // Remove this quiz from quizArray.
            quizArray.remove(randomNum);

        }
        public void checkAnswer(View view) {

            // Get pushed button.
            Button answerBtn = findViewById(view.getId());
            String btnText = answerBtn.getText().toString();

            String alertTitle;

            if (btnText.equals(rightAnswer)) {
                // Correct!!
                alertTitle = "Correct!";
                rightAnswerCount++;

            } else {
                // Wrong
                alertTitle = "Wrong...";
            }

            // Create Dialog.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(alertTitle);
            builder.setMessage("Answer : " + rightAnswer);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (quizArray.size() < 1) {
                        // quizArray is empty.
                        showResult();

                    } else {
                        quizCount++;
                        showNextQuiz();
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
        public void showResult() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Result");
            builder.setMessage(rightAnswerCount + " / 5");
            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();
                }
            });
            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();
        }
    }

