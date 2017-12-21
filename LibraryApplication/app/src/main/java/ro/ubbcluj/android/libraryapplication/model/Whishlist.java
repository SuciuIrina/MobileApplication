package ro.ubbcluj.android.libraryapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by dell on 12/20/2017.
 */
@Entity(tableName = "whishlist")
public class Whishlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int bookId;
    private String date;

    public Whishlist(int userId, int bookId, String date) {
        this.userId = userId;
        this.bookId = bookId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Whishlist{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", date='" + date + '\'' +
                '}';
    }
}
