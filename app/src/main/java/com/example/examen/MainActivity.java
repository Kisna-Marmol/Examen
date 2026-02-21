package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.examen.clases.ApiService;
import com.example.examen.clases.Dialog;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText txtNombreProducto, txtCodigoBarras, txtPrecioCosto, txtPorcentajeGanancia, txtPrecioVenta;
    CheckBox Activo;
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

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });
    }
    private void validarCampos()
    {
        if(txtNombreProducto.getText().toString().trim().equals("")||
                txtCodigoBarras.getText().toString().trim().equals("")||
                txtPrecioCosto.getText().toString().trim().equals("")||
                txtPorcentajeGanancia.getText().toString().trim().equals("")||
                txtPrecioVenta.getText().toString().trim().equals(""))
        {
            Dialog.toast(MainActivity.this,"Debe de Ingresar todos los Campos");
        }
        else
        {
            //crearUser();
            crearProducto();
        }
    }

    private void crearProducto()
    {
        String nombre_producto = txtNombreProducto.getText().toString();
        String codigo_barras = txtCodigoBarras.getText().toString();
        Double precio_costo = Double.valueOf(txtPrecioCosto.getText().toString());
        Integer porcentaje_ganancia = Integer.valueOf(txtPorcentajeGanancia.getText().toString());
        Double precio_venta = Double.valueOf(txtPrecioVenta.getText().toString());
        //String telefono = txtTelefono.getText().toString();
        //String dni = txtDNI.getText().toString().trim();//etDni.getText().toString().trim();
        boolean estado= Activo.isChecked();

        Log.d("PRODUCTO", "Activo: " + estado);

        ApiService.guardarDatos(nombre_producto, codigo_barras, precio_costo, porcentaje_ganancia, precio_venta, estado, new ApiService.ApiCallback()
                {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("CREAR_PRODUCTO_RESPONSE", response);
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getBoolean("success")) {
                                int idNuevo = res.getInt("id");
                                Dialog.toast(MainActivity.this, "✅ Producto guardado. ID: " + idNuevo);

                                // ✅ Mostrar datos en txtResultado
                                String resultado =
                                        "━━━━━━━━━━━━━━━━━━━━━\n" +
                                                "✅ PRODUCTO GUARDADO\n" +
                                                "━━━━━━━━━━━━━━━━━━━━━\n" +
                                                "ID:              " + idNuevo + "\n" +
                                                "Nombre:          " + txtNombreProducto.getText().toString() + "\n" +
                                                "Código de Barras:" + txtCodigoBarras.getText().toString() + "\n" +
                                                "Precio Costo:    $" + txtPrecioCosto.getText().toString() + "\n" +
                                                "% Ganancia:      " + txtPorcentajeGanancia.getText().toString() + "%\n" +
                                                "Precio Venta:    $" + txtPrecioVenta.getText().toString() + "\n" +
                                                "Estado:          " + (Activo.isChecked() ? "✅ Activo" : "❌ Inactivo") + "\n" +
                                                "━━━━━━━━━━━━━━━━━━━━━";

                                // ⚠️ Debe ejecutarse en el hilo principal (UI thread)
                                runOnUiThread(() -> {
                                    txtResultado.setText(resultado);

                                    // ✅ Limpiar campos para poder guardar otro producto
                                    txtNombreProducto.setText("");
                                    txtCodigoBarras.setText("");
                                    txtPrecioCosto.setText("");
                                    txtPorcentajeGanancia.setText("");
                                    txtPrecioVenta.setText("");
                                    Activo.setChecked(false);
                                });


                                setResult(RESULT_OK);


                            } else {
                                String error = res.optString("error", "Error desconocido");
                                Dialog.toast(MainActivity.this, "❌ Error: " + error);
                                runOnUiThread(() -> txtResultado.setText("❌ Error al guardar:\n" + error));
                            }
                        } catch (Exception e) {
                            Dialog.toast(MainActivity.this, "❌ Error procesando respuesta: " + e.getMessage());
                            runOnUiThread(() -> txtResultado.setText("❌ Excepción:\n" + e.getMessage()));
                        }
                    }
                    @Override
                    public void onError(String error)
                    {
                        // AGREGA ESTA LÍNEA
                        Log.e("CREAR_USER_ERROR", error);
                        //progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,
                                "❌ Error: " + error,
                                Toast.LENGTH_LONG).show();
                        //Dialog.msgbox(UserActivity.this,"Error","Error "+error,R.drawable.error);
                    }
                }
        );
        //Dialog.msgbox(UserActivity.this,"Información","Usuario "+nombreu+" creado Satisfactoriamente",R.drawable.ok);
    }
}