package edu.pes.darkaether0x1.pos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sales_Adapter extends BaseAdapter implements ListAdapter {
    private List<Item> list = new ArrayList<Item>();
    private Context context;

    public Sales_Adapter(List<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        //Handle TextView and display string from your list
        TextView code = view.findViewById(R.id.code_item);
        TextView qty = view.findViewById(R.id.quantity_item);
        TextView price = view.findViewById(R.id.price_item);
        TextView name = view.findViewById(R.id.nametext);

        Item a = list.get(position);
        code.setText(a.getcode());
        qty.setText(a.getqty());
        price.setText(a.getprice());
        name.setText(a.getname());
        //Handle buttons and add onClickListeners
        Button deleteBtn = view.findViewById(R.id.rem_item);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        return view;
    }
}