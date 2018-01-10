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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.firebase.FirebaseServiceBooks;
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
            mainInformationBooks.add(b.mainInfo());
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

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("books");

        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               Book book=dataSnapshot.getValue(Book.class);

               for(int i=0;i<Globals.bookRepository.getAllBooks().size();i++){
                   if(Globals.bookRepository.getBookByIndex(i).getFirebaseKey().equals(dataSnapshot.getKey())){
                       Globals.bookRepository.update(book);
                       Globals.getMainInformationBooks().set(i,book.mainInfo());
                       Globals.getBookAdapter().notifyDataSetChanged();
                   }
               }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key=dataSnapshot.getKey();

                for(int i=0;i<Globals.bookRepository.getAllBooks().size();i++){
                    if(Globals.bookRepository.getBookByIndex(i).getFirebaseKey().equals(dataSnapshot.getKey())){
                        Globals.bookRepository.delete(key);
                        Globals.getMainInformationBooks().remove(i);
                        Globals.getBookAdapter().notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
