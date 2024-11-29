package com.example.hitoluisja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonStartGame, buttonContactUs, buttonViewRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazamos las vistas
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonContactUs = findViewById(R.id.buttonContactUs);
        buttonViewRanking = findViewById(R.id.buttonViewRanking);

        loadUserData();

        // Configuramos el botón para "Start Game"
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();

                if (username.isEmpty()) {
                    editTextUsername.setError("Por favor, ingresa tu nombre.");
                    return;  // Si no se ingresa el nombre, no se hace nada más
                }

                saveUserData(username);

                // Pasar a la siguiente actividad con el nombre de usuario
                Intent intent = new Intent(MainActivity.this, CharacterSelectionActivity.class);
                intent.putExtra("USERNAME", username);  // Pasar el nombre de usuario
                startActivity(intent);
            }
        });

        // Configuramos el botón "Contact Us"
        buttonContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL de contacto de la empresa (puedes reemplazar esto con la URL real)
                String contactUrl = "https://www.tuempresa.com/contacto"; // Cambia la URL por la del contacto de tu empresa
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contactUrl));
                startActivity(intent);
            }
        });

        // Configuramos el botón "Ver Ranking"
        buttonViewRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrimos la actividad de Ranking
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });
    }
    // Guardar los datos del usuario
    private void saveUserData(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username); // Guardar el nombre de usuario
        editor.putString("selectedCharacter", ""); // Iniciar sin personaje seleccionado
        editor.putInt("score", 0); // Iniciar con puntaje 0
        editor.putInt("level", 1); // Iniciar con nivel 1
        editor.putInt("time", 100); // Iniciar con tiempo 100 segundos
        editor.apply(); // Guardar los cambios
    }

    // Cargar los datos del usuario
    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if (!username.isEmpty()) {
            editTextUsername.setText(username); // Si ya tiene un nombre guardado, lo cargamos
        }
    }
}
