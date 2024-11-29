package com.example.hitoluisja;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hitoluisja.database.UserRankingManager;
import com.example.hitoluisja.database.User;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitoluisja.database.User;
import com.example.hitoluisja.database.UserRankingManager;

public class GameActivity extends AppCompatActivity {

    private TextView scoreTextView, timerTextView, levelTextView;
    private Button clickButton;
    private String username, selectedCharacter;
    private int score = 0;
    private int level = 1;
    private int timeLeft = 30; // Tiempo inicial en segundos
    private boolean secondLifeUsed = false; // Para controlar la segunda vida del Mage
    private CountDownTimer countDownTimer;  // Definimos el temporizador fuera del método
    private boolean isFrozen = false;

    private UserRankingManager userRankingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        userRankingManager = new UserRankingManager(this);

        // Obtener los datos enviados desde la actividad anterior
        username = getIntent().getStringExtra("USERNAME");
        selectedCharacter = getIntent().getStringExtra("SELECTED_CHARACTER");

        // Inicializar vistas
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        levelTextView = findViewById(R.id.levelTextView);
        clickButton = findViewById(R.id.clickButton);
        View buttonSave = findViewById(R.id.buttonSave);

        // Mostrar nombre de usuario en la interfaz
        scoreTextView.setText(username + "'s Score: " + score);
        levelTextView.setText("Level: " + level);

        // Iniciar el temporizador
        startGameTimer();

        // Configurar el botón de clic
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickAction();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
                Toast.makeText(GameActivity.this, "Datos guardados.", Toast.LENGTH_SHORT).show();
                // Finalizar la actividad y abrir el ranking
                finish();
                startActivity(new Intent(GameActivity.this, RankingActivity.class));
            }
        });
    }

    private void handleClickAction() {
        if (timeLeft <= 0) {
            // Si el tiempo ha terminado, no permitir más clics
            clickButton.setEnabled(false);
            return;
        }

        switch (selectedCharacter) {
            case "Warrior":
                // El Warrior aumenta los puntos por dos cada clic
                score += 2;
                break;

            case "Mage":
                score += 1;
                break;

            case "Gunner":
                // El Gunner comienza con 50 puntos
                if (score == 0) {
                    score = 50;  // Se asignan 50 puntos iniciales
                } else {
                    score += 1;  // Después de eso, gana 1 punto por clic
                }
                break;

            case "Sorcerer":
                // El Sorcerer congela el tiempo cada 120 puntos
                score += 1;
                if (score % 120 == 0 && score != 0 && !isFrozen) {
                    isFrozen = true;  // Congelar el tiempo
                    Toast.makeText(GameActivity.this, "Time frozen for 5 seconds!", Toast.LENGTH_SHORT).show();
                    // Congelar el temporizador por 5 segundos
                    new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // No hacemos nada mientras el tiempo está congelado
                        }

                        @Override
                        public void onFinish() {
                            isFrozen = false;  // Descongelar el tiempo
                            startGameTimer();  // Reiniciar el temporizador con la duración original
                        }
                    }.start();
                    return;  // No sumar puntos mientras el tiempo está congelado
                }
                break;

            default:
                score += 1; // Por defecto, sumar 1 punto
                break;
        }

        // Verificar si se ha alcanzado el umbral de 100 clics para subir de nivel
        if (score % 100 == 0 && score != 0) {
            levelUp();
        }

        // Actualizar la interfaz con el nuevo puntaje
        scoreTextView.setText(username + "'s Score: " + score);
    }


    private void startGameTimer() {
        // Cancelar cualquier temporizador previo antes de reiniciar
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Temporizador de juego que cuenta atrás
        countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isFrozen) {
                    timeLeft = (int) (millisUntilFinished / 1000);
                    timerTextView.setText("Time: " + timeLeft + "s");
                }
            }

            @Override
            public void onFinish() {
                if (selectedCharacter.equals("Mage") && !secondLifeUsed) {
                    secondLifeUsed = true; // Activar la segunda vida
                    timeLeft = 30;  // Se añaden 30 segundos al tiempo restante
                    Toast.makeText(GameActivity.this, "Second life activated! +30 seconds.", Toast.LENGTH_SHORT).show();
                    startGameTimer();  // Reiniciar el temporizador con el nuevo tiempo
                } else {
                    // Aquí puedes mostrar un mensaje cuando el tiempo se acabe
                    timerTextView.setText("Game Over!");
                    Toast.makeText(GameActivity.this, "Game Over! Your score: " + score, Toast.LENGTH_LONG).show();
                    // Deshabilitar el botón cuando el juego se termine
                    clickButton.setEnabled(false);
                }
            }
        }.start();
    }

    private void levelUp() {
        // Subir de nivel cada vez que se superen 100 clics
        level++;
        timeLeft += 15; // Añadir 15 segundos al tiempo restante
        levelTextView.setText("Level: " + level);  // Actualizar el nivel en la interfaz
        Toast.makeText(GameActivity.this, "Level Up! +15 seconds!", Toast.LENGTH_SHORT).show();

        // Reiniciar el temporizador con los nuevos segundos
        startGameTimer();
    }

    private void saveUserData() {
        User user = new User(username, score, level, selectedCharacter);
        userRankingManager.addUser(user);
    }


}
