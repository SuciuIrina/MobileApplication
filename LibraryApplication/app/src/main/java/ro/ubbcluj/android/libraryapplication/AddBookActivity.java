package ro.ubbcluj.android.libraryapplication;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class AddBookActivity extends AppCompatActivity {

    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener dateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        TextView yearText = (TextView) findViewById(R.id.dateTextView);
        yearText.setText("Click to select a date");

        initializeYearSelection();

    }

    public void createItem(View view) {
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText authorText = (EditText) findViewById(R.id.authorText);
        EditText publisherText = (EditText) findViewById(R.id.publisherText);
        EditText reviewText = (EditText) findViewById(R.id.ratingText);
        TextView yearText = (TextView) findViewById(R.id.dateTextView);
        EditText descriptionText = (EditText) findViewById(R.id.descriptionText);


        Book newBook = checkFileds(titleText, authorText, publisherText, reviewText, yearText, descriptionText);
        if (newBook != null) {
            FirebaseServiceBooks firebaseServiceBooks=new FirebaseServiceBooks();
            firebaseServiceBooks.addBook(newBook);

            Globals.bookRepository.add(newBook);
            Globals.getMainInformationBooks().add(newBook.mainInfo());
            Globals.getBookAdapter().notifyDataSetChanged();
            finish();
        }

    }

    public Book checkFileds(EditText titleText, EditText authorText, EditText publisherText, EditText
            reviewText, TextView yearText, EditText descriptionText) {
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

        if (flag == true) {
            return new Book(title, author, publisher, yearString, review, description);
        }
        return null;
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
                        AddBookActivity.this,
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
                String date = formatDate(year, month, day);
                dateTextView.setText(date);
            }
        };
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
