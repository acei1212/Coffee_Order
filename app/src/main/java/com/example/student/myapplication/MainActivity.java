package com.example.student.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements My_Dialog_Fragment.能處理確定取消,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = "Mainacitvity";
    private ListView mListView;
    private List<Coffee> mCoffeeList = new ArrayList<>();


    //getter
    public List<Coffee> getmCoffeeList() {
        return mCoffeeList;
    }

    private static final String FILENAME = "coffeelist.ser";

    private void save() {
        ObjectOutputStream oos = null;
        try {
            try {
                //openFileOutput()繼承自 Context , Activity 繼承自 Context
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);//開啟寫入檔案
                oos = new ObjectOutputStream(fos); //將檔案交給物件輸出資料流
                oos.writeObject(mCoffeeList); //將物件寫入
                Log.d(TAG, "存檔成功");
            } finally {
                if (oos != null) {
                    oos.close();//關閉檔案
                }
            }
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void restore() {
        //openFileInput() 繼承自 context , Activity 繼承自 context
        FileInputStream fis = null;
        try {
            try {
                fis = openFileInput(FILENAME); //開啟讀入檔案
                ObjectInputStream ois = new ObjectInputStream(fis); //將檔案交給物件輸入資料流
                mCoffeeList = (List) ois.readObject(); //讀取物件
                Log.d(TAG, "讀檔成功");
            } finally {
                if (fis != null) {
                    fis.close(); //關閉檔案
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Log.d(TAG, e.toString());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        restore();
        ((MyListAdapter) mListView.getAdapter()).notifyDataSetChanged(); //更新ListView
    }

    protected void onStop() {
        super.onStop();
        save();
    }

    @Override
    public void 處理確定(Coffee coffee) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Snackbar.make(fab, "收到確定 coffee = " + coffee, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
        Log.d(TAG, "收到確定 coffee = " + coffee);
        //add coffee
        mCoffeeList.add(coffee);
        MyListAdapter myListAdapter = (MyListAdapter) mListView.getAdapter();
        myListAdapter.notifyDataSetChanged(); //透過 adapter 通知 listview 更新畫面
    }

    @Override
    public void 處理取消() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Snackbar.make(fab, "收到取消", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                DialogFragment dialog = new My_Dialog_Fragment();
                dialog.show(getSupportFragmentManager(), "MyDialogFragment");
            }
        });
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setEmptyView(findViewById(R.id.empty));
        mListView.setAdapter(new MyListAdapter(this));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterview, View view, int position, long id) {
        Log.d(TAG, "OnItemSelected, position =" + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Snackbar.make(fab, "點選了第" + position + "項", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
