package ro.ubbcluj.android.libraryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ro.ubbcluj.android.libraryapplication.model.Book;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void createItem(View view){
        EditText titleText=(EditText)findViewById(R.id.titleText);
        EditText authorText=(EditText)findViewById(R.id.authorText);
        EditText publisherText=(EditText)findViewById(R.id.publisherText);
        EditText reviewText=(EditText) findViewById(R.id.ratingText);
        EditText yearText=(EditText)findViewById(R.id.yearText);
        EditText descriptionText=(EditText)findViewById(R.id.descriptionText);

        Book newBook=checkFileds(titleText,authorText,publisherText,reviewText,yearText,descriptionText);
        if(newBook!=null){
            ListBookItemsActivity.BOOKS.add(newBook);
            ListBookItemsActivity.MAIN_INFORMATION_BOOKS.add(newBook.getMainInformation());
            ListBookItemsActivity.ADAPTER.notifyDataSetChanged();
            finish();
        }
    }

    public Book checkFileds(EditText titleText,EditText authorText,EditText publisherText, EditText
                            reviewText,EditText yearText, EditText descriptionText){
        String title=titleText.getText()+"";
        String author=authorText.getText()+"";
        String publisher=publisherText.getText()+"";
        String description=descriptionText.getText()+"";
        String yearString=yearText.getText()+"";
        String reviewString=reviewText.getText()+"";

        int year=0;
        int review=0;
        boolean flag=true;

        if(title.equals("")){
            titleText.setError("Enter a book title!");
            flag=false;
        }
        if(author.equals("")){
            authorText.setError("Enter the name of the author !");
            flag=false;
        }
        if(publisher.equals("")){
            publisherText.setError("Enter the name of the publisher!");
            flag=false;
        }
        if(yearString.equals("")){
            yearText.setError("Enter the year of publication!");
        }else{
            try {
                year = Integer.parseInt(yearString);
            }catch(NumberFormatException e){
                yearText.setError("Enter a year!");
                flag=false;
            }
            if(year<1000 || year >2018){
                yearText.setError("Enter a valid year!");
                flag=false;
            }
        }

        if(reviewString.equals("")){
            reviewText.setError("Enter a grade for the review!");
        }else{
            try {
                review = Integer.parseInt(reviewString);
            }catch (NumberFormatException e){
                reviewText.setError("Enter a review between 0 and 100!");
                flag=false;
            }

            if(review<0 || review>100){
                reviewText.setError("Enter a review between 0 and 100!");
                flag=false;
            }
        }

        if(flag==true){
            return new Book(title,author,publisher,year,review,description);
        }
        return null;
    }
}
