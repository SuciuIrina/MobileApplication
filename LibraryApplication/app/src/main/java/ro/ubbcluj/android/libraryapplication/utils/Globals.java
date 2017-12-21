package ro.ubbcluj.android.libraryapplication.utils;

import android.widget.ArrayAdapter;

import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;
import ro.ubbcluj.android.libraryapplication.repository.BookRepository;
import ro.ubbcluj.android.libraryapplication.repository.UserRepository;
import ro.ubbcluj.android.libraryapplication.repository.WhishlistRepository;

/**
 * Created by dell on 12/19/2017.
 */

public class Globals {
    public static BookRepository bookRepository;
    public static UserRepository userRepository;
    public static WhishlistRepository whishlistRepository;
    private static List<String> MAIN_INFORMATION_BOOKS;
    private static ArrayAdapter<String> BOOK_ADAPTER;



    public static Book getBookById(Integer id){
        return  bookRepository.getBookById(id);
    }

    public static Book getBookByIndex(Integer index){
        return bookRepository.getBookByIndex(index);
    }

    public  static void addBook(Book b){

        bookRepository.add(b);
    }

    public  static void  removeBook(Integer id){

        bookRepository.delete(id);
    }

    public static void updateBook(Book book){
        bookRepository.update(book);
    }

    public static List<Book> getBOOKS() {
        return bookRepository.getRepo();
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
