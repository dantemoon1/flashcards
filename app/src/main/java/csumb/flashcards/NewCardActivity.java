package csumb.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import csumb.flashcards.databinding.ActivityNewCardBinding;

public class NewCardActivity extends AppCompatActivity {

    private AppDb AppDb;

    ActivityNewCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        binding = ActivityNewCardBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        AppDb = AppDb.getInstance(this);
        binding.saveButton.setOnClickListener(v1 -> saveCard(v1));
    }

    public void saveCard(View v) {
        String question = binding.questionEditText.getText().toString();
        String answer = binding.answerEditText.getText().toString();
        FlashcardDao flashcardDao = AppDb.getFlashcardDao();
        if(question.length() == 0 || answer.length() == 0) {
            Toast.makeText(this, R.string.enter_qa_error, Toast.LENGTH_LONG).show();
            return;
        }
        Flashcard fc = new Flashcard(question, answer);

        // save card here!
        flashcardDao.insertCard(fc);
        // go back!
        startActivity(new Intent(this, MainActivity.class));

    }
}