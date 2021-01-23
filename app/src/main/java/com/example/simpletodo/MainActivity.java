package com.example.simpletodo;

import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;    //this import keeps disappearing all the time!


/*
DESIGN NOTES
CRUD Pattern: Create, Read, Update, Delete

*related to the Model-View-Controller Pattern*
Model - structured data; database
    *Coded out in xml
View - UI; shows the data fom the model
    *Coded out in Java code
Controller:
    - Rendering: Writes data from model to the UI of view
    - Persistence: loads and saves model data
        *handled by file system; loads to model
    - Mutation: adds and removes model items
    *Also coded in Java code
Wireframe Basic Layout for To-Do List
    Top: title and app name
    Middle: list of items; press and hold for removing items
    Bottom: inputs text; add button to add into items
 */
public class MainActivity extends AppCompatActivity {

    // Rendering Items Code
    List<String> toDoItems;
    Button buttonAdd;
    EditText editToDoItems;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //attaches Item objects to actual item references in XML
        buttonAdd = findViewById(R.id.buttonAdd);
        editToDoItems = findViewById(R.id.editToDoItems);
        rvItems = findViewById(R.id.rvItems);
        editToDoItems.setText("");     //IT WORKSSSS; Time to MUTATE these with the 'Adapter'

        //List of items created
        loadItems();

        //here is the position of item that is long-pressed
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete the item from model
                toDoItems.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        }
        ItemsAdapter itemsAdapter = new ItemsAdapter(toDoItems, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String todoItem = editToDoItems.getText().toString();

                //add item to model
                toDoItems.add(todoItem);

                //notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(toDoItems.size() - 1);
                editToDoItems.setText("");
                Toast.makeText(getApplicationContext(), "Item was added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile()
    {
        return new File(getFilesDir(),"data.txt");
    }
    //function will load items by reading data from 'data.txt'
    private void loadItems()
    {
        try {
            toDoItems = new ArrayList<>(FileUtils.readLines(getDataFile()), Charset.defaultCharset());
        }catch (IOException e)
        {
            Log.e(tag: "MainActivity", msg: "Error reading items", e);
        }
    }
    //function saves items by writing to 'data.txt' file
    private void saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(), items);
        }catch (IOException e)
        {
            Log.e(tag: "MainActivity", msg: "Error writing items", e);
        }
    }

}