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

    @SerializedName("visible")
    @Expose
    private String visible;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEncargado() {
        return encargado;
    }

    public String getComents() {
        return coments;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getContenido() {
        return contenido;
    }

    public boolean getVisible() {
        if (visible.equals("0")){
            return false;
        }else {
            return true;
        }
    }

    public boolean getEstado() {
        if (estado.equals("0")){
            return false;
        }else {
            return true;
        }

    }

}