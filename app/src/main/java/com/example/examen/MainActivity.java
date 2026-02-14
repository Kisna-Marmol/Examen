package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreProducto, txtCodigoBarras, txtPrecioCosto, txtPorcentajeGanancia, txtPrecioVenta;
    EditText Activo;
    Button btnGuardar;
    EditText txtResultado;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombreProducto = findViewById(R.id.txtNombreProducto);
        txtCodigoBarras = findViewById(R.id.txtCodigoBarras);
        txtPrecioCosto = findViewById(R.id.txtPrecioCosto);
        txtPorcentajeGanancia = findViewById(R.id.txtPorcentajeGanancia);
        txtPrecioVenta = findViewById(R.id.txtPrecioVenta);
        Activo = findViewById(R.id.chcActivo);
        txtResultado = findViewById(R.id.txtResultado);

        btnGuardar = findViewById(R.id.btnGuardar);
    }
}