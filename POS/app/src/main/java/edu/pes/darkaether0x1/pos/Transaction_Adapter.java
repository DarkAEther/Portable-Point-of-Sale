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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transaction_Adapter  extends BaseAdapter implements ListAdapter {
    private List<transContent> list;
    private Context context;


    public Transaction_Adapter(List<transContent> list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.trans_list_item, null);
        }

        TextView id = view.findViewById(R.id.item);
        TextView date = view.findViewById(R.id.date);

        final transContent a = list.get(position);
        id.setText(a.getid());
        date.setText(a.getdate());
        return view;
    }

}
