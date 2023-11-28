package com.gabrielvalforte.grimoire;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MagiaActivity extends AppCompatActivity {

    Magia magia = new Magia();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        int value = b.getInt("key");
        setContentView(R.layout.activity_detalhe_magia);

        DB db = new DB(this);
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        if (db.open()) {
            magia = db.getMagia(value);
        } else {
            // error opening DB.
        }
        TextView t = new TextView(this);
        //nome
        t = (TextView)findViewById(R.id.tvMdNome);
        t.setText(magia.getNomeFull());
        //pagina
        t = (TextView)findViewById(R.id.tvMdPag);
        t.setText("Pág. " + magia.getPag());
        //pratica
        t = (TextView)findViewById(R.id.tvMdPraticaD);
        t.setText(" " + magia.getPratica());
        //acao
        t = (TextView)findViewById(R.id.tvMdAcaoD);
        t.setText(" " + magia.getAcao());
        //duracao
        t = (TextView)findViewById(R.id.tvMdDuracaoD);
        t.setText(" " + magia.getDuracao());
        //aspecto
        t = (TextView)findViewById(R.id.tvMdAspectoD);
        t.setText(" " + magia.getAspecto());
        //custo
        t = (TextView)findViewById(R.id.tvMdCustoD);
        t.setText(" " + magia.getCusto());
        //descricao
        t = (TextView)findViewById(R.id.tvMdDescri);
        t.setText(magia.getDescri());

        //nome classico
        t = (TextView)findViewById(R.id.tvMdNomeCl);
        t.setText(magia.getNomeClassico());
        //parada de dados
        t = (TextView)findViewById(R.id.tvMdParadaD);
        t.setText(magia.getParada());
        //descricao classico
        t = (TextView)findViewById(R.id.tvMdDescriCl);
        t.setText(magia.getDescriCl());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe_magia, menu);
        this.menu = menu;

        if (magia.isFav()) {
            menu.findItem(R.id.fav).setIcon(getResources().getDrawable(R.drawable.ic_action_important));
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.fav:
                ToggleFav(magia);
                return true;
        }
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public boolean ToggleFav(Magia magia) {
        DB db = new DB(this.getApplicationContext());
        try{
            db.create();
        }  catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        if (db.open()) {
            db.ChangeFav(magia.getId(), magia.isFav());
            if (magia.isFav()){
                menu.findItem(R.id.fav).setIcon(getResources().getDrawable(R.drawable.ic_action_not_important));
                magia.setFav(false);
            } else {
                menu.findItem(R.id.fav).setIcon(getResources().getDrawable(R.drawable.ic_action_important));
                magia.setFav(true);
            }
        } else {
            // error opening DB.
        }
        return true;
    }

}