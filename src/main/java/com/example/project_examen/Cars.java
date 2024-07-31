package com.example.project_examen;

public class Cars {
    private final int id;
    private final String immatriculation;
    private final String marque;
    private final String modele;

    public Cars(int id, String immatriculation, String marque, String modele) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
    }

    public int getId() {
        return id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }
}
