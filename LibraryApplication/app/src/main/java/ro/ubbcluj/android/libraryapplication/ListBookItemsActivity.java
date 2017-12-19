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
import ro.ubbcluj.android.libraryapplication.utils.Globals;

import static ro.ubbcluj.android.libraryapplication.R.id.listViewBooks;

public class ListBookItemsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_items);

        List<String> mainInformationBooks= new ArrayList<>();
        for (Book b : Globals.getBOOKS()) {
            mainInformationBooks.add(b.getMainInformation());
        }
        Globals.setMainInformationBooks(mainInformationBooks);
        ListView listViewBooks = (ListView) findViewById(R.id.listViewBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListBookItemsActivity.this, BookDetailActivity.class);
                intent.putExtra("MOVIE_DETAIL", position);
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
