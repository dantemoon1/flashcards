package csumb.flashcards;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Flashcards")
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    @NonNull public Integer uid;
    private String answer;
    private String question;
    private boolean complete;

    public Flashcard(String question, String answer) {
        this.answer = answer;
        this.question = question;
        this.complete = false;
    }

    public Flashcard(Context c, int questionId, int answerId) {
        this(c.getString(questionId), c.getString(answerId));
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String toString(){
        return "Question: " + this.getQuestion() + " Answer: " + this.getAnswer() + " complete: " + this.isComplete();
    }

}

