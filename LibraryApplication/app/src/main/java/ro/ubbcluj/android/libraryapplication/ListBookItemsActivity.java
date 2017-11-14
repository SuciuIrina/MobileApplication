package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.ubbcluj.android.libraryapplication.model.Book;

import static ro.ubbcluj.android.libraryapplication.R.id.listViewBooks;

public class ListBookItemsActivity extends AppCompatActivity {

    public static final List<Book> BOOKS;
    public static List<String> MAIN_INFORMATION_BOOKS;
    public static ArrayAdapter<String> ADAPTER;
    static{
        BOOKS=new ArrayList<>();
        BOOKS.addAll(Arrays.asList(
                new Book("Pe aripile Vantului","Margareth Michael","RAO",1990, 98,"Description"),
                new Book("Departe de lumea dezlantuita","Thomas Hardy","Adevarul", 2010, 94,"Description")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_items);

        MAIN_INFORMATION_BOOKS=new ArrayList<>();
        for(Book b:BOOKS){
            MAIN_INFORMATION_BOOKS.add(b.getMainInformation());
        }


        ListView listViewBooks=(ListView) findViewById(R.id.listViewBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent intent=new Intent(ListBookItemsActivity.this,BookDetailActivity.class);
             intent.putExtra("MOVIE_DETAIL",position);
             startActivity(intent);

         }});
        ADAPTER=new ArrayAdapter<String>(listViewBooks.getContext(),
                android.R.layout.simple_list_item_1,MAIN_INFORMATION_BOOKS);
        listViewBooks.setAdapter(ADAPTER);

    }

    public void goToAddBookActivity(View view){
        Intent intent=new Intent(this,AddBookActivity.class);
        startActivity(intent);
    }
}
