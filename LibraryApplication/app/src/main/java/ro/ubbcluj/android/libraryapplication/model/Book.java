package ro.ubbcluj.android.libraryapplication.model;

/**
 * Created by dell on 11/13/2017.
 */

public class Book {
    private static int COUNTER=0;
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int yearOfPublishing;
    private String description;
    private double review;


    public Book(String title, String author, String publisher, int yearOfPublishing, double review,String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublishing = yearOfPublishing;
        this.review = review;
        this.id =COUNTER++;
        this.description=description;
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

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public double getReview() {
        return review;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public String getMainInformation(){
        return "Title: "+title+"\n Author: "+author+"\n Review: "+review;
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
                ", review=" + review +
                '}';
    }
}
