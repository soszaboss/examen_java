package com.example.project_examen;

public class Routes {
    private final int id;
    private final String villeDepart;
    private final String villeArrivee;
    private final String conducteur;
    private final String voiture;
    private final String dateDepart;
    private final String dateArrivee;

    public Routes(int id, String villeDepart, String villeArrivee, String conducteur, String voiture, String dateDepart, String dateArrivee) {
        this.id = id;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.conducteur = conducteur;
        this.voiture = voiture;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
    }

    public int getId() {
        return id;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public String getConducteur() {
        return conducteur;
    }

    public String getVoiture() {
        return voiture;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }
}
