package ro.ubbcluj.android.libraryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.model.Whishlist;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class ViewChartActivity extends AppCompatActivity {
    private Book book;
    private int position;

    private List<Whishlist> whishlistBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chart);

        position = getIntent().getIntExtra("BOOK_POSITION", -1);
        book = Globals.getBookByIndex(position);
        whishlistBook = Globals.whishlistRepository.getWhishlistByBookId(this.book.getId());

        List<String> dates = new ArrayList<>();
        Map<String, Integer> chartData = new HashMap<>();

        for (Whishlist w : whishlistBook) {
            if (!dates.contains(w.getDate())) {
                dates.add(w.getDate());
            }
        }

        for (String d : dates) {
            int counter = 0;
            for (Whishlist w : whishlistBook) {
                if (w.getDate().equals(d)) {
                    counter++;
                }
            }
            chartData.put(d, counter);
        }

        for (Integer i : chartData.values()) {
            System.out.println(i);
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        int i = 0;
        for (Integer value : chartData.values()) {
            entries.add(new BarEntry(value, i));
            i++;
        }

        BarDataSet dataset = new BarDataSet(entries, "Number of users that bought the book");

        ArrayList<String> labels = new ArrayList<String>();
        for(String s:chartData.keySet()){
            labels.add(s);
        }

        BarChart chart = new BarChart(getApplicationContext());
        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }
}
