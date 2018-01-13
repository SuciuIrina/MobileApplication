package ro.ubbcluj.android.libraryapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ro.ubbcluj.android.libraryapplication.firebase.FirebaseServiceBooks;
import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class BookDetailActivity extends AppCompatActivity {
    private Book book;
    private int position;

    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener dateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        position = getIntent().getIntExtra("BOOK_DETAIL", -1);
        book = Globals.bookRepository.getBookByIndex(position);

        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        TextView yearText = (TextView) findViewById(R.id.dateTextView);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);

        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
        reviewText.setText(String.valueOf(book.getRating()));
        yearText.setText(book.getYearOfPublishing());
        descriptionText.setText(book.getDescription());

        initializeYearSelection();
    }


    public void sendMail(View view) {
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        TextView yearText = (TextView) findViewById(R.id.dateTextView);
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

    public void deleteBook(View view){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you want to delete this book?");
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Book book=Globals.bookRepository.getBookByIndex(position);

                FirebaseServiceBooks firebaseServiceBooks=new FirebaseServiceBooks();
                firebaseServiceBooks.deleteBook(book.getFirebaseKey());

                Globals.bookRepository.delete(book.getFirebaseKey());
                Globals.getMainInformationBooks().remove(position);
                Globals.getBookAdapter().notifyDataSetChanged();
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();

    }

    public void saveInformation(View view) {
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        TextView yearText = (TextView) findViewById(R.id.dateTextView);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);

        String title = titleText.getText() + "";
        String author = authorText.getText() + "";
        String publisher = publisherText.getText() + "";
        String description = descriptionText.getText() + "";
        String yearString = yearText.getText() + "";
        String reviewString = reviewText.getText() + "";


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
            book.setYearOfPublishing(yearString);

            FirebaseServiceBooks firebaseServiceBooks=new FirebaseServiceBooks();
            firebaseServiceBooks.updateBook(book);

            Globals.bookRepository.update(book);
            Globals.getMainInformationBooks().set(position, book.mainInfo());
            Globals.getBookAdapter().notifyDataSetChanged();
            finish();
        }
    }

    public void initializeYearSelection(){
        dateTextView=(TextView) findViewById(R.id.dateTextView);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = formatDate(year,month,day);
                dateTextView.setText(date);
            }
        };
    }

    public void viewChart(View view){
        Intent intent=new Intent(this,ViewChartActivity.class);
        intent.putExtra("BOOK_POSITION",position);
        startActivity(intent);
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}
