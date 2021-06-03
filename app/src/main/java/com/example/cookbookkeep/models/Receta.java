package com.example.cookbookkeep.models;

public class Receta {

    public String nombre, tiempo, porcion, calorias, categoria, rating, ingredientes, preparacion;

    public Receta() {
    }

    public Receta(String nombre, String tiempo, String porcion, String calorias, String categoria, String rating, String ingredientes, String preparacion) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.porcion = porcion;
        this.calorias = calorias;
        this.categoria = categoria;
        this.rating = rating;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }
}
