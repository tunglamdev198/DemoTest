package com.example.demotest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter<ThiSinh> {
    private Context context;
    private int resource;
    private List<ThiSinh> thiSinhs;
    private LayoutInflater inflater;
    public Adapter(Context context, int resource, List<ThiSinh> thiSinhs) {
        super(context, resource, thiSinhs);
        this.context = context;
        this.resource = resource;
        this.thiSinhs = thiSinhs;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(resource,parent,false);

            holder.txtSBD = convertView.findViewById(R.id.txtSBD);
            holder.txtHoTen = convertView.findViewById(R.id.txtHoTen);
            holder.txtTongDiem = convertView.findViewById(R.id.txtTongDiem);
            convertView.setTag(holder);
        }
        else {
            holder =(ViewHolder) convertView.getTag();
        }

        ThiSinh thiSinh = thiSinhs.get(position);
        holder.txtSBD.setText(thiSinh.getSoBaoDanh());
        holder.txtHoTen.setText(thiSinh.getHoTen());
        holder.txtTongDiem.setText(String.valueOf(thiSinh.tongDiem()));

        return convertView;

    }

    public class ViewHolder{
        private TextView txtSBD;
        private TextView txtHoTen;
        private TextView txtTongDiem;
    }
}
