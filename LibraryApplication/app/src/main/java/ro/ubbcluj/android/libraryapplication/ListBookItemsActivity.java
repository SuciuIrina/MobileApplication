package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.User;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;
import ro.ubbcluj.android.libraryapplication.repository.AppDatabase;
import ro.ubbcluj.android.libraryapplication.repository.BookRepository;
import ro.ubbcluj.android.libraryapplication.repository.UserRepository;
import ro.ubbcluj.android.libraryapplication.repository.WhishlistRepository;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

import static ro.ubbcluj.android.libraryapplication.R.id.listViewBooks;

public class ListBookItemsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_items);

        if (Globals.bookRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.bookRepository = new BookRepository(appDatabase);
        }

        if (Globals.userRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.userRepository = new UserRepository(appDatabase);
            if (Globals.userRepository.getRepo().size() == 0) {
                Globals.userRepository.add(new User("irina_suciu", "Irina", "Suciu", "irina@gmail.com"));
                Globals.userRepository.add(new User("coman_nicu", "Nicolae", "Coman", "nicu@gmail.com"));
                Globals.userRepository.add(new User("andrei_suciu", "Andrei", "Suciu", "andrei@gmail.com"));
                Globals.userRepository.add(new User("maria_suciu", "Maria", "Suciu", "maria@gmail.com"));
                Globals.userRepository.add(new User("ioana_suciu", "Ioana", "Suciu", "ioana@gmail.com"));
                Globals.userRepository.add(new User("elena_suciu", "Elena", "Suciu", "elena@gmail.com"));
                Globals.userRepository.add(new User("tiberiu_suciu", "Tiberiu", "Suciu", "tiberiu@gmail.com"));
            }
        }

        if (Globals.whishlistRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.whishlistRepository = new WhishlistRepository(appDatabase);
            if (Globals.whishlistRepository.getRepo().size() == 0) {
                Globals.whishlistRepository.add(new Whishlist(1, 1, "2017-12-01"));
                Globals.whishlistRepository.add(new Whishlist(2, 1, "2017-12-01"));
                Globals.whishlistRepository.add(new Whishlist(3, 1, "2017-12-02"));
                Globals.whishlistRepository.add(new Whishlist(4, 1, "2017-12-03"));
                Globals.whishlistRepository.add(new Whishlist(5, 1, "2017-12-04"));
                Globals.whishlistRepository.add(new Whishlist(6, 1, "2017-12-04"));
                Globals.whishlistRepository.add(new Whishlist(7, 1, "2017-12-04"));
                Globals.whishlistRepository.add(new Whishlist(1, 2, "2017-12-01"));
                Globals.whishlistRepository.add(new Whishlist(2, 2, "2017-12-01"));
                Globals.whishlistRepository.add(new Whishlist(3, 2, "2017-12-01"));
                Globals.whishlistRepository.add(new Whishlist(4, 2, "2017-12-02"));
                Globals.whishlistRepository.add(new Whishlist(5, 2, "2017-12-03"));
                Globals.whishlistRepository.add(new Whishlist(6, 2, "2017-12-06"));
                Globals.whishlistRepository.add(new Whishlist(7, 2, "2017-12-06"));
                Globals.whishlistRepository.add(new Whishlist(1, 3, "2017-12-03"));
                Globals.whishlistRepository.add(new Whishlist(2, 3, "2017-12-03"));
                Globals.whishlistRepository.add(new Whishlist(3, 3, "2017-12-03"));
                Globals.whishlistRepository.add(new Whishlist(4, 3, "2017-12-04"));
                Globals.whishlistRepository.add(new Whishlist(5, 3, "2017-12-05"));
                Globals.whishlistRepository.add(new Whishlist(6, 3, "2017-12-05"));
                Globals.whishlistRepository.add(new Whishlist(7, 3, "2017-12-06"));
            }
        }

        List<String> mainInformationBooks = new ArrayList<>();
        for (Book b : Globals.getBOOKS()) {
            mainInformationBooks.add(b.getMainInformation());
        }
        Globals.setMainInformationBooks(mainInformationBooks);
        ListView listViewBooks = (ListView) findViewById(R.id.listViewBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListBookItemsActivity.this, BookDetailActivity.class);
                intent.putExtra("BOOK_DETAIL", position);
                startActivity(intent);

            }
        });
        ArrayAdapter bookAdapter = new ArrayAdapter<String>(listViewBooks.getContext(),
                android.R.layout.simple_list_item_1, Globals.getMainInformationBooks());
        Globals.setBookAdapter(bookAdapter);
        listViewBooks.setAdapter(Globals.getBookAdapter());

    }

    public void goToAddBookActivity(View view) {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }
}
