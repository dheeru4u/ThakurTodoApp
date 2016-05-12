package com.codepath.dkthaku.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
       //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
      // items.add("First Item");
        //  items.add("Second Item");
        Log.d(TAG, "onCreate: Items list"+items);
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String iTemTxt = etNewItem.getText().toString();
        Log.d(TAG, "onAddItem: iTemTxt "+iTemTxt);
        itemsAdapter.add(iTemTxt);
        etNewItem.setTag("");
        writeItems();
    }

    private void setupListViewListener1(){
        Log.d(TAG, "setupListViewListener:Items "+items);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
                Log.d(TAG, "setupListViewListener. onItemLongClick: @@@@@@@"+pos);
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items= new ArrayList<String>();
            e.printStackTrace();
        }
    }
    private void setupListViewListener() {
        Log.d(TAG, "setupListViewListener:Items in "+items);
        try {
            lvItems.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapter,
                                                       View item, int pos, long id) {
                            // Remove the item within array at position
                            Log.d(TAG, "onItemLongClick: removed" + pos);
                            items.remove(pos);
                            // Refresh the adapter
                            itemsAdapter.notifyDataSetChanged();
                            // Return true consumes the long click event (marks it handled)
                            writeItems(); // <---- Add this line
                            Log.d(TAG, "onItemLongClick: After writes");
                            itemsAdapter.notifyDataSetChanged();
                            return true;
                        }

                    });
        }catch (Exception e){  e.printStackTrace();

        }
    }
    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

