package ro.ubbcluj.android.libraryapplication.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.User;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;

/**
 * Created by dell on 12/19/2017.
 */
@Database(entities = {Book.class,User.class, Whishlist.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract BookDao bookDao();
    public abstract UserDao userDao();
    public abstract WhishlistDao whishlistDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "list-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
