package ro.ubbcluj.android.libraryapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;

public class ListBookItemsActivity extends AppCompatActivity {

    public static final List<Book> BOOKS;
    public static List<String> mainInformationBooks;
    public static ArrayAdapter<String> adapter;
    static{
        BOOKS=new ArrayList<>();
        BOOKS.addAll(Arrays.asList(
                new Book("Pe aripile Vantului","Margareth Michael","RAO",1990, 9.8,"Description"),
                new Book("Departe de lumea dezlantuita","Thomas Hardy","Adevarul", 2010, 9.4,"Description")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_items);

        mainInformationBooks=new ArrayList<>();
        for(Book b:BOOKS){
            mainInformationBooks.add(b.getMainInformation());
        }
        ListView listViewBooks=(ListView) findViewById(R.id.listViewBooks);
        adapter=new ArrayAdapter<String>(listViewBooks.getContext(),
                android.R.layout.simple_list_item_1,mainInformationBooks);
        listViewBooks.setAdapter(adapter);

    }

    public void goToAddBookActivity(View view){
        Intent intent=new Intent(this,AddBookActivity.class);
        startActivity(intent);
    }
}
