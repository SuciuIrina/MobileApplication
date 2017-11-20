package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ro.ubbcluj.android.libraryapplication.model.Book;

public class BookDetailActivity extends AppCompatActivity {
    private Book book;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        position = getIntent().getIntExtra("MOVIE_DETAIL", -1);
        book = ListBookItemsActivity.BOOKS.get(position);

        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        EditText yearText = (EditText) findViewById(R.id.yearText);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);

        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
        reviewText.setText(String.valueOf(book.getRating()));
        yearText.setText(String.valueOf(book.getYearOfPublishing()));
        descriptionText.setText(book.getDescription());
    }


    public void sendMail(View view) {
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        EditText yearText = (EditText) findViewById(R.id.yearText);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);

        String title = titleText.getText() + "";
        String author = authorText.getText() + "";
        String publisher = publisherText.getText() + "";
        String description = descriptionText.getText() + "";
        String yearString = yearText.getText() + "";
        String reviewString = reviewText.getText() + "";

        String[] emails = {"suciuirinacj@yahoo.com"};
        String subject = "Book " + title + " details";
        String message = "Title: " + title + "\nAuthor: " + author + " \nPublisher: " + publisher +
                "\nYear of publishing: " + yearString + "\nRating: " + reviewString +
                "\nDescription: " + description;
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, emails);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    public void saveInformation(View view) {
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        EditText yearText = (EditText) findViewById(R.id.yearText);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);

        String title = titleText.getText() + "";
        String author = authorText.getText() + "";
        String publisher = publisherText.getText() + "";
        String description = descriptionText.getText() + "";
        String yearString = yearText.getText() + "";
        String reviewString = reviewText.getText() + "";

        int year = 0;
        int review = 0;
        boolean flag = true;

        if (title.equals("")) {
            titleText.setError("Enter a book title!");
            flag = false;
        }
        if (author.equals("")) {
            authorText.setError("Enter the name of the author !");
            flag = false;
        }
        if (publisher.equals("")) {
            publisherText.setError("Enter the name of the publisher!");
            flag = false;
        }
        if (yearString.equals("")) {
            yearText.setError("Enter the year of publication!");
        } else {
            try {
                year = Integer.parseInt(yearString);
            } catch (NumberFormatException e) {
                yearText.setError("Enter a year!");
                flag = false;
            }
            if (year < 1000 || year > 2018) {
                yearText.setError("Enter a valid year!");
                flag = false;
            }
        }

        if (reviewString.equals("")) {
            reviewText.setError("Enter a grade for the review!");
        } else {
            try {
                review = Integer.parseInt(reviewString);
            } catch (NumberFormatException e) {
                reviewText.setError("Enter a review between 0 and 100!");
                flag = false;
            }

            if (review < 0 || review > 100) {
                reviewText.setError("Enter a review between 0 and 100!");
                flag = false;
            }
        }

        if (flag) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setRating(review);
            book.setPublisher(publisher);
            book.setYearOfPublishing(year);

            ListBookItemsActivity.BOOKS.set(position, book);
            ListBookItemsActivity.MAIN_INFORMATION_BOOKS.set(position, book.getMainInformation());
            ListBookItemsActivity.ADAPTER.notifyDataSetChanged();
            finish();
        }
    }
}
