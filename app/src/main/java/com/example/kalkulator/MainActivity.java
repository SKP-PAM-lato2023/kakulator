package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // okienko na wynik
            TextView result = findViewById(R.id.result);

            //pobieram kliknięty przycisk
            Button button = findViewById(view.getId());
            CharSequence buttonText = button.getText();

            // usuwanie zera wiodacego
            if(result.getText().equals("0")){
                // czy kliknieto .
                if(buttonText.equals(".")){
                    result.append(buttonText);
                } else if (!Character.isDigit(buttonText.charAt(0))) {
                    return;
                }else{
                    result.setText(buttonText);
                }
            }else if (!Character.isDigit(buttonText.charAt(0))
                    && !Character.isDigit(result.getText().charAt(result.getText().length()-1))) {
                return;
            }
            else{
                result.append(buttonText);
            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ustawienie listnerow
        findViewById(R.id.bK).setOnClickListener(new ButtonListener());
        findViewById(R.id.b0).setOnClickListener(new ButtonListener());
        findViewById(R.id.b1).setOnClickListener(new ButtonListener());
        findViewById(R.id.b2).setOnClickListener(new ButtonListener());
        findViewById(R.id.b3).setOnClickListener(new ButtonListener());
        findViewById(R.id.b4).setOnClickListener(new ButtonListener());
        findViewById(R.id.b5).setOnClickListener(new ButtonListener());
        findViewById(R.id.b6).setOnClickListener(new ButtonListener());
        findViewById(R.id.b7).setOnClickListener(new ButtonListener());
        findViewById(R.id.b8).setOnClickListener(new ButtonListener());
        findViewById(R.id.b9).setOnClickListener(new ButtonListener());
        findViewById(R.id.bM).setOnClickListener(new ButtonListener());
        findViewById(R.id.bMin).setOnClickListener(new ButtonListener());
        findViewById(R.id.bD).setOnClickListener(new ButtonListener());
        findViewById(R.id.bPlus).setOnClickListener(new ButtonListener());

        findViewById(R.id.bR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView result = findViewById(R.id.result);
                String resultText = result.getText().toString();

                // walidacja wyrażenia (czy konczy się liczbą)
                if(!Character.isDigit(resultText.charAt(resultText.length()-1))){
                    return;
                }

                // szukam liczb
                String[] elements = resultText.split("[+,\\-, /,*]");
                List<Double> numbers = new ArrayList<>();
                for(String s: elements)
                    numbers.add(Double.parseDouble(s));

                // szukam operatorów
                Pattern pattern = Pattern.compile("[+,\\-, /,*]");
                Matcher matcher = pattern.matcher(resultText);
                List<String> operators = new ArrayList<>();
                while(matcher.find()){
                    operators.add(matcher.group());
                }

                double outcome = numbers.get(0);

                for(int i=0; i<operators.size(); i++){
                    switch (operators.get(i)){
                        case "+":
                            outcome += numbers.get(i+1);
                            break;
                        case "-":
                            outcome -= numbers.get(i+1);
                            break;
                        case "*":
                            outcome *= numbers.get(i+1);
                            break;
                        case "/":
                            if(numbers.get(i+1) == 0)
                                Toast.makeText(getBaseContext(), "Nie dziel przez 0!", Toast.LENGTH_SHORT).show();
                            else
                                outcome /= numbers.get(i+1);
                            break;
                    }
                }

                result.setText(Double.valueOf(outcome).toString());

            }
        });
    }
}