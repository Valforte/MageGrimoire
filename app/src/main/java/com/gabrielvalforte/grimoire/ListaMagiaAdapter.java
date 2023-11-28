package com.gabrielvalforte.grimoire;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListaMagiaAdapter extends ArrayAdapter<Magia> {

    public ListaMagiaAdapter(Context context, List<Magia> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        Magia magia = getItem(position);
        String nome = "";
        for(int i = 0; i < magia.getNivel(); i++)
            nome += "●";
        nome += " " + magia.getNome();
        if(magia.getNivelAd() != null && !magia.getNivelAd().isEmpty())
            nome += " (" + magia.getNivelAd() + ") ";
        viewHolder.tvTitle.setText(nome);
        viewHolder.tvDescription.setText("Pág. " + magia.getPag() + " - " + magia.getAspecto() + " - " /*+ magia.getAcao() + " - " + magia.getDuracao() + " - "*/ + magia.getCusto());


        return convertView;
    }

    private static class ViewHolder {
        TextView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
    }

    public void refreshItems()
    {
        try {
            this.notifyDataSetChanged();
        } catch (Exception e) {}

    }
}