package csumb.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import csumb.flashcards.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //private List<Flashcard> flashcardList;

    private static final Random RANDOM = new Random();
    private int currentFlashcardId;
    private int cardsRemaining;
    private Flashcard currentFlashcard;
    private Toast currentAnswerToast;
    private Button markCorrectButton;
    private Button showAnswerButton;
    private Button randomCardButton;
    private Button resetCardsButton;

    private AppDb AppDb;
    List<Flashcard> cardlist;
    private Button updateviewbutton;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppDb = AppDb.getInstance(this);
        markCorrectButton = binding.markCorrectButton;
        markCorrectButton.setOnClickListener(v -> markComplete());
        showAnswerButton = binding.showAnswerButton;
        showAnswerButton.setOnClickListener(v -> showAnswer());
        randomCardButton = binding.randomCardButton;
        randomCardButton.setOnClickListener(v -> pickRandomFlashcard());
        resetCardsButton = binding.resetCardsButton;
        resetCardsButton.setOnClickListener(v -> resetCards());
        binding.addCardButton.setOnClickListener(v -> addCard(v));
        //updateviewbutton = binding.updateviewbutton;
        //updateviewbutton.setOnClickListener(v -> updateCardView(v));
        // ORDER BELOW CANNOT CHANGE
        resetCards();
        pickRandomFlashcard();
        // ORDER ABOVE CANNOT CHANGE
        //updateCardView(view);
    }


    public void addCard(View v) {
        Intent intent = new Intent(this, NewCardActivity.class);
        startActivity(intent);
    }

    private void loadQuestions() {
        /*flashcardList = Arrays.asList(
                new Flashcard(this, R.string.question_compiler , R.string.answer_compiler ),
                new Flashcard(this, R.string.question_https , R.string.answer_https ),
                new Flashcard(this, R.string.question_os , R.string.answer_os ),
                new Flashcard(this, R.string.question_rdbms, R.string.answer_rdbms )
        );*/
        FlashcardDao flashcardDao = AppDb.getFlashcardDao();
        cardlist = flashcardDao.listCards();
        if(cardlist.isEmpty()){
            Flashcard card1 = new Flashcard(this, R.string.question_compiler , R.string.answer_compiler);
            Flashcard card2 = new Flashcard(this, R.string.question_https , R.string.answer_https);
            Flashcard card3 = new Flashcard(this, R.string.question_os, R.string.answer_os);
            Flashcard card4 = new Flashcard(this, R.string.question_rdbms, R.string.answer_rdbms);
            flashcardDao.insertCard(card1);
            flashcardDao.insertCard(card2);
            flashcardDao.insertCard(card3);
            flashcardDao.insertCard(card4);
        }
    }

    @SuppressLint("SetTextI18n")
    private void resetCards() {
        FlashcardDao flashcardDao = AppDb.getFlashcardDao();
        cardlist = flashcardDao.listCards();
        if(cardlist.isEmpty()){
            //Toast.makeText(getApplicationContext(), "its empty", Toast.LENGTH_LONG).show();
            loadQuestions();
        } else {
            //loadQuestions();
            cardlist = flashcardDao.listCards();
            //Toast.makeText(getApplicationContext(), "not empty", Toast.LENGTH_LONG).show();

            for (Flashcard f : cardlist){
                f.setComplete(false);
                flashcardDao.updateCard(f);
                //Toast.makeText(getApplicationContext(), f.toString(), Toast.LENGTH_LONG).show();
            }
            toggleButtons(true);
            pickRandomFlashcard();

        }
        cardlist = flashcardDao.listCards();
        //new code above old code below
        cardsRemaining = cardlist.size();
        updateRandomButton();
    }

    private void updateRandomButton() {
        randomCardButton.setText(getString(R.string.random_button_text, cardsRemaining));
    }

    private void toggleButtons(boolean enable) {
        markCorrectButton.setEnabled(enable);
        showAnswerButton.setEnabled(enable);
        randomCardButton.setEnabled(enable);
    }

    private void showAnswer() {
        if(currentAnswerToast != null) {
            currentAnswerToast.show();
        } else {
            Toast.makeText(getApplicationContext(), "WTF!?", Toast.LENGTH_LONG).show();
        }
    }
    private void markComplete() {
        FlashcardDao flashcardDao = AppDb.getFlashcardDao();
        if(cardsRemaining == 1 && currentFlashcard != null) {
            // turn off mark correct, show answer, and random card buttons
            toggleButtons(false);
            binding.questionTextView.setText(R.string.cards_finished_text);
        } else {
            currentFlashcard.setComplete(true);
            flashcardDao.updateCard(currentFlashcard);
            pickRandomFlashcard();
        }
        cardsRemaining--;
        updateRandomButton();

    }

    private void pickRandomFlashcard() {
        if(cardsRemaining == 1) {
            return;
        }
        FlashcardDao flashcardDao = AppDb.getFlashcardDao();
        cardlist = flashcardDao.listCards();
        int random;
        // can't look at the same card twice in a row unless it's the last card
        // and can't look at complete cards
        do {
            random = RANDOM.nextInt(cardlist.size());
        } while (random == currentFlashcardId ||
                cardlist.get(random).isComplete());
        currentFlashcardId = random;
        currentFlashcard = cardlist.get(currentFlashcardId);
        binding.questionTextView.setText(currentFlashcard.getQuestion());
        currentAnswerToast = Toast.makeText(getApplicationContext(),
                currentFlashcard.getAnswer(), Toast.LENGTH_LONG);
    }

    /*public void updateCardView(View v){
        //this is all my code for testing
        //Toast.makeText(getApplicationContext(), "WTF!?", Toast.LENGTH_LONG).show();
        FlashcardDao flashcardDAO = AppDb.getFlashcardDao();
        List<Flashcard> cards = flashcardDAO.listCards();
        TextView output = binding.AppdbOutputTextView;
        StringBuilder sb = new StringBuilder();
        for (Flashcard f : cards){
            sb.append(f);
            sb.append("\n\n");
        }
        output.setText(sb.toString());
    }*/
}