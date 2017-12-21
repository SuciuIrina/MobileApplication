package ro.ubbcluj.android.libraryapplication.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.User;

/**
 * Created by dell on 12/20/2017.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id=:id")
    User getUserByID(int id);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User users);

    @Update
    void update(User users);
}
