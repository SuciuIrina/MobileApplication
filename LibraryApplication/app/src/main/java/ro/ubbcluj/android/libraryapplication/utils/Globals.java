package ro.ubbcluj.android.libraryapplication.utils;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;

/**
 * Created by dell on 12/19/2017.
 */

public class Globals {
    private static final List<Book> BOOKS;
    private static List<String> MAIN_INFORMATION_BOOKS;
    private static ArrayAdapter<String> BOOK_ADAPTER;


    static {
        BOOKS = new ArrayList<>();
        BOOKS.addAll(Arrays.asList(
                new Book("Pe aripile Vantului", "Margareth Michael", "RAO", "1990-12-01", 98, "Description"),
                new Book("Departe de lumea dezlantuita", "Thomas Hardy", "Adevarul", "2010-12-01", 94, "Description")));
    }

    public static Integer getBookListSize(){
        return BOOKS.size();
    }

    public static Book getBook(Integer index){
        return BOOKS.get(index);
    }

    public  static void addBook(Book b){
        BOOKS.add(b);
    }

    public  static void  removeBook(Integer index){
        BOOKS.remove(index);
    }

    public static List<Book> getBOOKS() {
        return BOOKS;
    }

    public static List<String> getMainInformationBooks() {
        return MAIN_INFORMATION_BOOKS;
    }

    public static void setMainInformationBooks(List<String> mainInformationBooks) {
        MAIN_INFORMATION_BOOKS = mainInformationBooks;
    }

    public static ArrayAdapter<String> getBookAdapter() {
        return BOOK_ADAPTER;
    }

    public static void setBookAdapter(ArrayAdapter<String> bookAdapter) {
        BOOK_ADAPTER = bookAdapter;
    }
}
