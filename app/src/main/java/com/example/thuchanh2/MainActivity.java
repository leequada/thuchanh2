package com.example.thuchanh2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuchanh2.Control.databaseDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    ArrayList<City> arrayList = new ArrayList<>();
    AdapterListView adapterListView;
    ListView listView;
    EditText name , amount , dates;
    Spinner city;
    TextView totalamount;
    Button add,update,delete,search;
    int pos;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        DatePickerDialog.OnDateSetListener setListener;
        amount = findViewById(R.id.amount);
        totalamount = findViewById(R.id.totalamount);
        dates = findViewById(R.id.dates);
        city = findViewById(R.id.Spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.citys,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);
        add= findViewById(R.id.addbtn);
        update = findViewById(R.id.updatebtn);
        delete = findViewById(R.id.deletebtn);
        search = findViewById(R.id.searchbtn);
        listView = findViewById(R.id.listview);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"-"+month+"-"+year;
                        dates.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        final databaseDAO database = new databaseDAO(this,"City.sqlite",null,1);
        database.queryData("CREATE TABLE IF NOT EXISTS " +
                "Citys(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name varchar(200) , City varchar(200) , Dates date , amount float) ");
        //database.queryData("INSERT INTO Citys VALUES(null,'Le Thi Thuy Dung','Ha Noi','5-28-2021',100000)");
        final List<City> array = database.SearchbyName("");
        int sum =0;
        for(City c: array){
            arrayList.add(c);
            sum += Integer.parseInt(c.getAmount());
        }
        totalamount.setText(sum+" VND");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City c= arrayList.get(position);
                name.setText(c.getName());
                amount.setText(c.getAmount());
                dates.setText(c.getDates());
                idd = Integer.parseInt(c.getId());
                pos = position;
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                String amounts = amount.getText().toString();
                String ct = city.getSelectedItem().toString();
                String date = dates.getText().toString();
                City c = new City(idd+"",ten,ct,date,amounts);
                int result = database.update(c);
                if(result >0){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    arrayList.set(pos,c);

                }
                adapterListView.notifyDataSetChanged();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = database.delete(idd+"");
                if(result > 0){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    arrayList.remove(pos);

                }
                adapterListView.notifyDataSetChanged();

            }
        });
        adapterListView = new AdapterListView(arrayList);
        listView.setAdapter(adapterListView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                String amounts = amount.getText().toString();
                String ct = city.getSelectedItem().toString();
                String date = dates.getText().toString();
                City c = new City("",ten,ct,date,amounts);
                Long result = database.Add(c);
                if(result > 0){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    if(arrayList != null)
                    {arrayList.clear();}
                    List<City> array = database.SearchbyName("");
                    for(City cs: array){
                        arrayList.add(cs);
                    }

                }
                adapterListView.notifyDataSetChanged();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = name.getText().toString();
                if(arrayList != null)
                {arrayList.clear();}
                List<City> array = database.SearchbyName(ten);
                for(City c: array){
                    arrayList.add(c);
                }
                adapterListView.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.titleinfo:
                startActivity(new Intent(MainActivity.this, Info_activity.class));
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}