package ro.ubbcluj.android.libraryapplication.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

/**
 * Created by dell on 12/19/2017.
 */

public class BookRepository {
    private List<Book> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public BookRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;

        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                repo.addAll(appDatabase.bookDao().getAll());
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(final Book book) {
        repo.add(book);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.bookDao().insertAll(book);
            }
        });
    }

    public void update(final Book book) {
        Book newBook = null;
        for (Book b : repo) {
            if (b.getFirebaseKey().equals(book.getFirebaseKey())) {
                newBook = book;
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPublisher(book.getPublisher());
                b.setYearOfPublishing(book.getYearOfPublishing());
                b.setDescription(book.getDescription());
                b.setRating(b.getRating());
                break;
            }
        }

        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setPublisher(book.getPublisher());
        newBook.setYearOfPublishing(book.getYearOfPublishing());
        newBook.setDescription(book.getDescription());
        newBook.setRating(book.getRating());


        final Book bookUpdated = newBook;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.bookDao().update(bookUpdated);
            }
        });

    }

    public void delete(final String firebaseKey) {
        for (Book b : repo) {
            if (b.getFirebaseKey().equals(firebaseKey) ) {
                repo.remove(b);
                final Book bookDelete = b;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.bookDao().delete(bookDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Book> getAllBooks() {
        return repo;
    }

    public Book getBookByKey(final String key) {
        for (Book b : repo) {
            if (b.getFirebaseKey().equals(key)) {
                return b;
            }
        }
        return null;
    }

    public Book getBookByIndex(Integer index){
        return repo.get(index);
    }
}
