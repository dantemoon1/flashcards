package csumb.flashcards;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Flashcard.class}, version = 10)
public abstract class AppDb extends RoomDatabase {
    private static AppDb instance;
    private static final String DATABASE_NAME = "AppDb";
    public abstract FlashcardDao getFlashcardDao();
    public static synchronized AppDb getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDb.class,DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
}
