package ro.ubbcluj.android.libraryapplication.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.User;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;

/**
 * Created by dell on 12/20/2017.
 */
@Dao
public interface WhishlistDao {

    @Query("SELECT * FROM whishlist")
    List<Whishlist> getAll();

    @Insert
    void insertAll(Whishlist... whishlists);

    @Delete
    void delete(Whishlist whishlist);

    @Update
    void update(Whishlist whishlist);
}
