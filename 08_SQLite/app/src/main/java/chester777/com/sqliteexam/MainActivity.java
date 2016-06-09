package chester777.com.sqliteexam;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView id;
    EditText productNameText;
    EditText quantityText;
    ListView listView;

    DBCursorAdapter dbCursorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init() {

        listView = (ListView)findViewById(R.id.listView);

        id = (TextView)findViewById(R.id.id);
        productNameText = (EditText)findViewById(R.id.productName);
        quantityText = (EditText)findViewById(R.id.quantity);

        updateList();
    }

    public void newProduct(View v) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int quantity = Integer.parseInt(quantityText.getText().toString());
        Product product = new Product(productNameText.getText().toString(), quantity);
        dbHandler.addProduct(product);
    }

    public void findProduct(View v) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Product product = dbHandler.findProduct(productNameText.getText().toString());

        if( product != null )
        {
            id.setText(String.valueOf(product.getId()));
            productNameText.setText(String.valueOf(product.getProductName()));
            quantityText.setText(String.valueOf(product.getQuantity()));
        } else {
            Toast.makeText(this, "No Match Founded", Toast.LENGTH_SHORT).show();
        }
    }

    public void delProduct(View v) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        boolean result = dbHandler.delProduct(productNameText.getText().toString());

        if(result) {
            id.setText("");
            productNameText.setText("");
            quantityText.setText("");
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Match Founded", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateProduct(View v) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Product product = new Product(productNameText.getText().toString(), Integer.parseInt(quantityText.getText().toString()));
        boolean result = dbHandler.updateProduct(product);

        if(result) {
            id.setText("");
            productNameText.setText("");
            quantityText.setText("");
            Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Match Founded", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Cursor cursor = dbHandler.findAll();

        if(dbCursorAdapter == null) {
            dbCursorAdapter = new DBCursorAdapter(this, cursor, false);
            listView.setAdapter(dbCursorAdapter);
        }
        dbCursorAdapter.changeCursor(cursor);
    }
}
