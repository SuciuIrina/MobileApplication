package ro.ubbcluj.android.libraryapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ro.ubbcluj.android.libraryapplication.model.Book;
import ro.ubbcluj.android.libraryapplication.utils.Globals;

public class ViewChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chart);

        Map<String, Integer> chartData = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String[] days = new String[6];
        days[0] = sdf.format(date);

        cal.add(Calendar.DAY_OF_YEAR, -6);

        // loop adding one day in each iteration
        for(int i = 0; i< 6; i++){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            date=cal.getTime();
            days[i]=sdf.format(date);
        }

        List<Book> list=Globals.bookRepository.getAllBooks();
        for(String day:days){
            int counter=0;
            for(Book b: list){
                String d=b.getYearOfPublishing();
                if(b.getYearOfPublishing().equals(day)){
                    counter++;
                }
            }
            chartData.put(day,counter);
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        int i = 0;
        //sort the chart data by key (by the date)
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(chartData);

        for (Integer value : treeMap.values()) {
            entries.add(new BarEntry(value, i));
            i++;
        }

        BarDataSet dataset = new BarDataSet(entries, "Number of books published in the last 7 days");

        ArrayList<String> labels = new ArrayList<String>();
        for(String s:days){
            labels.add(s);
        }

        BarChart chart = new BarChart(getApplicationContext());
        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }
}
