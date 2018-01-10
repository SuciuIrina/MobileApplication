package ro.ubbcluj.android.libraryapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by dell on 11/13/2017.
 */
@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firebaseKey;
    private String title;
    private String author;
    private String publisher;
    private String yearOfPublishing; //year-month-day
    private String description;
    private int rating;


    public Book(String title, String author, String publisher, String yearOfPublishing, int rating, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublishing = yearOfPublishing;
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(String yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int review) {
        this.rating = review;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public String getMainInformation() {
        return "Title: " + title + "\n Author: " + author + "\n Rating: " + rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", yearOfPublishing=" + yearOfPublishing +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}
