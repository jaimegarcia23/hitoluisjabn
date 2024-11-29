package com.example.hitoluisja;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterSelectionActivity extends AppCompatActivity {

    private Button warriorButton, mageButton, gunnerButton, sorcererButton, confirmSelectionButton;
    private String selectedCharacter = "";
    private String username; // Para almacenar el nombre de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_selection);

        // Obtener el nombre de usuario pasado desde MainActivity
        username = getIntent().getStringExtra("USERNAME");

        // Inicialización de los botones
        warriorButton = findViewById(R.id.warriorButton);
        mageButton = findViewById(R.id.mageButton);
        gunnerButton = findViewById(R.id.gunnerButton);
        sorcererButton = findViewById(R.id.sorcererButton);
        confirmSelectionButton = findViewById(R.id.confirmSelectionButton);

        // Asignación de listeners para cada botón de personaje
        warriorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCharacter = "Warrior";
                Toast.makeText(CharacterSelectionActivity.this, "Warrior selected!", Toast.LENGTH_SHORT).show();
            }
        });

        mageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCharacter = "Mage";
                Toast.makeText(CharacterSelectionActivity.this, "Mage selected!", Toast.LENGTH_SHORT).show();
            }
        });

        gunnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCharacter = "Gunner";
                Toast.makeText(CharacterSelectionActivity.this, "Gunner selected!", Toast.LENGTH_SHORT).show();
            }
        });

        sorcererButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCharacter = "Sorcerer";
                Toast.makeText(CharacterSelectionActivity.this, "Sorcerer selected!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del botón de confirmación
        confirmSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCharacter.isEmpty()) {
                    Toast.makeText(CharacterSelectionActivity.this, "Please select a character!", Toast.LENGTH_SHORT).show();
                } else {
                    // Si el personaje ha sido seleccionado, se pasa a la actividad del juego
                    Intent intent = new Intent(CharacterSelectionActivity.this, GameActivity.class);
                    intent.putExtra("USERNAME", username); // Pasar el nombre de usuario
                    intent.putExtra("SELECTED_CHARACTER", selectedCharacter); // Pasar el personaje seleccionado
                    startActivity(intent);
                }
            }
        });
    }
    // Guardar el personaje seleccionado en SharedPreferences
    private void saveCharacterData(String character) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedCharacter", character);
        editor.apply();
    }
}
