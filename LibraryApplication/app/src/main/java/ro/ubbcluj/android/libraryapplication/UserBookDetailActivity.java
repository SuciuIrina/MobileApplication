package ro.ubbcluj.android.libraryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class UserBookDetailActivity extends AppCompatActivity {
    private Book book;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_detail);

        position = getIntent().getIntExtra("BOOK_DETAIL", -1);
        book = Globals.bookRepository.getBookByIndex(position);

        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView authorText = (TextView) findViewById(R.id.authorText);
        TextView publisherText = (TextView) findViewById(R.id.publisherText);
        TextView reviewText = (TextView) findViewById(R.id.ratingText);
        TextView yearText = (TextView) findViewById(R.id.yearText);
        TextView descriptionText = (TextView) findViewById(R.id.descriptionText);

        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
        reviewText.setText(String.valueOf(book.getRating()));
        yearText.setText(book.getYearOfPublishing());
        descriptionText.setText(book.getDescription());


    }
}
