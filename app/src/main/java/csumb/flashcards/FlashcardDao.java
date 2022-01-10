package csumb.flashcards;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Insert
    void insertCard(Flashcard card);

    @Update
    void updateCard(Flashcard card);

    @Delete
    void deleteCard(Flashcard card);

    @Query("SELECT * FROM Flashcards")
    List<Flashcard> listCards();

}
