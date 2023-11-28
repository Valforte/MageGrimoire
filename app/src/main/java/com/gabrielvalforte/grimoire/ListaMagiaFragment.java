package com.gabrielvalforte.grimoire;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaMagiaFragment extends ListFragment {
    private List<Magia> mItems;        // ListView items list
    private Arcana arcana;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        int id = args.getInt("index");
        // initialize the items list
        mItems = new ArrayList<Magia>();
        Resources resources = getResources();

        switch (id){
            case 0:
                arcana = new Arcana("Favoritos", getActivity());
                mItems = arcana.magias;
                break;
            case 1:
                arcana = new Arcana("Espaço", getActivity());
                mItems = arcana.magias;
                break;
            case 2:
                arcana = new Arcana("Espírito", getActivity());
                mItems = arcana.magias;
                break;
            case 3:
                arcana = new Arcana("Forças", getActivity());
                mItems = arcana.magias;
                break;
            case 4:
                arcana = new Arcana("Matéria", getActivity());
                mItems = arcana.magias;
                break;
            case 5:
                arcana = new Arcana("Mente", getActivity());
                mItems = arcana.magias;
                break;
            case 6:
                arcana = new Arcana("Morte", getActivity());
                mItems = arcana.magias;
                break;
            case 7:
                arcana = new Arcana("Primórdio", getActivity());
                mItems = arcana.magias;
                break;
            case 8:
                arcana = new Arcana("Sorte", getActivity());
                mItems = arcana.magias;
                break;
            case 9:
                arcana = new Arcana("Tempo", getActivity());
                mItems = arcana.magias;
                break;
            case 10:
                arcana = new Arcana("Vida", getActivity());
                mItems = arcana.magias;
                break;
        }

        // initialize and set the list adapter
        setListAdapter(new ListaMagiaAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        Magia item = mItems.get(position);

        // do something
        //Toast.makeText(getActivity(), item.getNome(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MagiaActivity.class);
        Bundle b = new Bundle();
        b.putInt("key", item.getId()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    public static ListaMagiaFragment newInstance(int pos) {
        ListaMagiaFragment f = new ListaMagiaFragment();
        Bundle args = new Bundle();
        args.putInt("index", pos);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onResume() {
        Bundle arg = this.getArguments();
        int id = arg.getInt("index");
        if (id == 0) {
            mItems = new ArrayList<Magia>();
            arcana = new Arcana("Favoritos", getActivity());
            mItems = arcana.magias;
            setListAdapter(new ListaMagiaAdapter(getActivity(), mItems));
        }
        super.onResume();
    }
}