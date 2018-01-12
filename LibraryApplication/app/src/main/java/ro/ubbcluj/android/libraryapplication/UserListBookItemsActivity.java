package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.repository.AppDatabase;
import ro.ubbcluj.android.libraryapplication.repository.BookRepository;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class UserListBookItemsActivity extends AppCompatActivity {

    private Button mLogOutButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_book_items);

        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(UserListBookItemsActivity.this, LoginActivity.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mLogOutButton=(Button)findViewById(R.id.signOutButton);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                mGoogleSignInClient.signOut();

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
                Book b=dataSnapshot.getValue(Book.class);
                boolean flag=false;

                for(Book book:Globals.bookRepository.getAllBooks()){
                    if(book.getFirebaseKey().equals(b.getFirebaseKey())){
                        flag=true;
                    }
                }

                if(!flag){
                    Globals.bookRepository.add(b);
                    Globals.getMainInformationBooks().add(b.mainInfo());
                    Globals.getBookAdapter().notifyDataSetChanged();
                }

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
}
