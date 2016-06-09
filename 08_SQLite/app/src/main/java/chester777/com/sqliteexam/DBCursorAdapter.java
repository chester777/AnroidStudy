package chester777.com.sqliteexam;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DBCursorAdapter extends CursorAdapter{

    public DBCursorAdapter(Context context, Cursor c, Boolean autorequry) {
        super(context, c, autorequry);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final TextView listId = (TextView)view.findViewById(R.id.listId);
        final TextView listProductName = (TextView)view.findViewById(R.id.listProductName);
        final TextView listQuantity = (TextView)view.findViewById(R.id.listQuantity);

        listId.setText("ID : " + cursor.getString(cursor.getColumnIndex("_id")));
        listProductName.setText("Product Name : " + cursor.getString(cursor.getColumnIndex("productName")));
        listQuantity.setText("Quantity : " + cursor.getString(cursor.getColumnIndex("quantity")));
    }
}
