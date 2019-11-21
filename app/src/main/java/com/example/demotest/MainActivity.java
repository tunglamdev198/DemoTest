package com.example.demotest;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private ListView lvThiSinh;
    private FloatingActionButton btnAdd;
    private Adapter adapter;
    private List<ThiSinh> thiSinhs;
    private int index;
    private MyDatabase myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViews() {
        edtSearch = findViewById(R.id.edtSearch);
        lvThiSinh = findViewById(R.id.lvThiSinh);
        btnAdd = findViewById(R.id.btnAdd);
        myDB = new MyDatabase(this);
        thiSinhs = generate();
        Collections.sort(thiSinhs,ThiSinh.comparator);
        adapter = new Adapter(this, R.layout.item_thisinh, thiSinhs);
        lvThiSinh.setAdapter(adapter);
        registerForContextMenu(lvThiSinh);

    }

    private List<ThiSinh> generate() {
        if(myDB.getAll().size()==0) {
            ThiSinh thiSinh1 = new ThiSinh("GHA1", "Nguyen Tung Lam", 8, 7, 6);
            ThiSinh thiSinh2 = new ThiSinh("GHA2", "Pham Van Chuyen", 8, 5, 7);
            ThiSinh thiSinh3 = new ThiSinh("GHA1", "Nguyen Van Anh", 6, 9, 8);
            ThiSinh thiSinh4 = new ThiSinh("GHA1", "Tran Lam", 7, 8, 7);
            ThiSinh thiSinh5 = new ThiSinh("GHA1", "Sơn Tùng", 7, 9, 9);
            myDB.insert(thiSinh1);
            myDB.insert(thiSinh2);
            myDB.insert(thiSinh3);
            myDB.insert(thiSinh4);
            myDB.insert(thiSinh5);
            myDB.insert(thiSinh1);
            myDB.insert(thiSinh2);
            myDB.insert(thiSinh3);
            myDB.insert(thiSinh4);
            myDB.insert(thiSinh5);
            List<ThiSinh> thiSinhs = myDB.getAll();
            return thiSinhs;
        }
        else {
            return myDB.getAll();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvThiSinh) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index = info.position;
        switch (item.getItemId()) {
            case R.id.mnuSua:
                break;

            case R.id.mnuXoa:
                final double tongDiem = thiSinhs.get(index).tongDiem();
                int dem = 0;
                for(int i=0;i<thiSinhs.size();i++){
                    if(thiSinhs.get(i).tongDiem()<tongDiem){
                        dem++;
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xóa")
                        .setMessage("Bạn có muốn xóa "+dem+"TS có tổng điểm nhỏ hơn "+tongDiem+ " điểm không?")
                        .setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       for(int i=thiSinhs.size()-1;i>=0;i--){
                           if(thiSinhs.get(i).tongDiem()<tongDiem){
                               myDB.delete(i+1);
                           }
                       }
                        thiSinhs.clear();
                        thiSinhs.addAll(myDB.getAll());
                        loadData();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng Dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadData(){
        if (adapter == null) {
            adapter = new Adapter(this, R.layout.item_thisinh, thiSinhs);
            lvThiSinh.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void filter(CharSequence key){
        List<ThiSinh> filter = new ArrayList<>();
        for(ThiSinh ts : thiSinhs){
            if(ts.getHoTen().toLowerCase().contains(key)){
                filter.add(ts);
            }
        }
        lvThiSinh.setAdapter(new Adapter(this,R.layout.item_thisinh,filter));
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDB.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDB.closeDB();
    }
}
