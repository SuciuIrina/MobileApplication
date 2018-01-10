package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.User;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;
import ro.ubbcluj.android.libraryapplication.repository.AppDatabase;
import ro.ubbcluj.android.libraryapplication.repository.BookRepository;
import ro.ubbcluj.android.libraryapplication.repository.UserRepository;
import ro.ubbcluj.android.libraryapplication.repository.WhishlistRepository;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class ListBookItemsActivity extends AppCompatActivity {

    private Button mLogOutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_items);

        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(ListBookItemsActivity.this, LoginActivity.class));
                }
            }
        };


        mLogOutButton=(Button)findViewById(R.id.signOutButton);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
            }
        });

        TextView textView=(TextView) findViewById(R.id.connectionTextView);
        textView.setText(mAuth.getCurrentUser().getEmail());

        if (Globals.bookRepository == null) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            Globals.bookRepository = new BookRepository(appDatabase);
        }

        List<String> mainInformationBooks = new ArrayList<>();
        for (Book b : Globals.bookRepository.getAllBooks()) {
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void goToAddBookActivity(View view) {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }
}
