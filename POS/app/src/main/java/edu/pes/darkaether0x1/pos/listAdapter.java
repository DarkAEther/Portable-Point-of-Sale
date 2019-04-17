package edu.pes.darkaether0x1.pos;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class listAdapter extends BaseAdapter implements ListAdapter {
    private List<Item> list;
    private Context context;

    public listAdapter(List<Item> list, Context context)
    {
        this.list=list;
        this.context=context;

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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlist, null);
        }

        TextView one=view.findViewById(R.id.view1);
        TextView two=view.findViewById(R.id.view2);
        TextView name = view.findViewById(R.id.name_view);
        final Item a = list.get(position);

        one.setText(a.getcode());
        two.setText(a.getqty());
        name.setText(a.getname());

        return view;
    }
}
