package com.gabrielvalforte.grimoire;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arcana {

    public String nome;
    public List<Magia> magias = new ArrayList<Magia>();

    public Arcana(String arcana, Context context) {
        this.nome = arcana;

        DB db = new DB(context);
        // copy assets DB to app DB.
        try {
            db.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        // get all locations
        if (db.open()) {
            this.magias = db.getMagias(this.nome);
        } else {
            // error opening DB.
        }
    }
}