package ro.ubbcluj.android.libraryapplication.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ro.ubbcluj.android.libraryapplication.model.Book;

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
            if (b.getId() == book.getId()) {
                newBook = book;
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

    public void delete(final int bookId) {
        for (Book b : repo) {
            if (b.getId() == bookId) {
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

    public List<Book> getRepo() {
        return repo;
    }

    public Book getBookById(final int id) {
        for (Book b : repo) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public Book getBookByIndex(Integer index){
        return repo.get(index);
    }
}
