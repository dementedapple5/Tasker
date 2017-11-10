package com.example.usuario.tasker.pojoObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskPojo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("encargado")
    @Expose
    private String encargado;
    @SerializedName("coments")
    @Expose
    private String coments;
    @SerializedName("prioridad")
    @Expose
    private String prioridad;
    @SerializedName("contenido")
    @Expose
    private String contenido;
    @SerializedName("estado")
    @Expose
    private String estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getEstado() {
        if (estado.equals("0")){
            return false;
        }else {
            return true;
        }

    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}