package ro.ubbcluj.android.libraryapplication.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

/**
 * Created by dell on 1/10/2018.
 */

public class FirebaseServiceBooks {
    private FirebaseDatabase database=FirebaseDatabase.getInstance();

    public FirebaseServiceBooks() {

    }

    public void addBook(Book book){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("books");

        String key=bookRef.push().getKey();
        book.setFirebaseKey(key);
        bookRef.child(key).setValue(book);
    }

    public void updateBook(Book book){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("books");

        bookRef.child(book.getFirebaseKey()).setValue(book);
    }

    public void deleteBook(String key){
        DatabaseReference ref=database.getReference("server");
        DatabaseReference bookRef=ref.child("books");

        bookRef.child(key).removeValue();
    }
}
