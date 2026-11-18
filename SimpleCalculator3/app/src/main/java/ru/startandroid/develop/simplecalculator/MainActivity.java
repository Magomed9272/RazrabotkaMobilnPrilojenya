package ru.startandroid.develop.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tvResult;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String currentOperation = "";
    private boolean isNewNumber = true;
    private boolean isOperationClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        // Цифры
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        // Операции
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSub).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDiv).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);

        // Доп функции
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnSign).setOnClickListener(this);
        findViewById(R.id.btnPercent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Цифры
        if (id == R.id.btn0 || id == R.id.btn1 || id == R.id.btn2 ||
                id == R.id.btn3 || id == R.id.btn4 || id == R.id.btn5 ||
                id == R.id.btn6 || id == R.id.btn7 || id == R.id.btn8 || id == R.id.btn9) {

            Button btn = (Button) v;
            String digit = btn.getText().toString();

            if (isNewNumber) {
                tvResult.setText(digit);
                isNewNumber = false;
                isOperationClicked = false;
            } else {
                String currentText = tvResult.getText().toString();
                if (!currentText.equals("0") || !digit.equals("0")) {
                    if (currentText.equals("0")) {
                        tvResult.setText(digit);
                    } else {
                        tvResult.setText(currentText + digit);
                    }
                }
            }
            return;
        }

        // Десятичная точка
        if (id == R.id.btnDot) {
            String currentText = tvResult.getText().toString();
            if (isNewNumber) {
                tvResult.setText("0.");
                isNewNumber = false;
            } else if (!currentText.contains(".")) {
                tvResult.setText(currentText + ".");
            }
            return;
        }

        // Операции
        if (id == R.id.btnAdd || id == R.id.btnSub ||
                id == R.id.btnMultiply || id == R.id.btnDiv) {

            if (!isOperationClicked) {
                firstNumber = Double.parseDouble(tvResult.getText().toString());
                Button btn = (Button) v;
                currentOperation = btn.getText().toString();
                isOperationClicked = true;
                isNewNumber = true;
            } else {
                Button btn = (Button) v;
                currentOperation = btn.getText().toString();
            }
            return;
        }

        // Равно
        if (id == R.id.btnEquals) {
            if (!currentOperation.isEmpty()) {
                secondNumber = Double.parseDouble(tvResult.getText().toString());
                double result = calculate();
                String resultStr = formatResult(result);
                tvResult.setText(resultStr);
                firstNumber = result;
                currentOperation = "";
                isNewNumber = true;
                isOperationClicked = false;
            }
            return;
        }

        // Очистка
        if (id == R.id.btnClear) {
            firstNumber = 0;
            secondNumber = 0;
            currentOperation = "";
            tvResult.setText("0");
            isNewNumber = true;
            isOperationClicked = false;
            return;
        }

        // Смена знака
        if (id == R.id.btnSign) {
            double current = Double.parseDouble(tvResult.getText().toString());
            current = -current;
            tvResult.setText(formatResult(current));
            return;
        }

        // Процент
        if (id == R.id.btnPercent) {
            double current = Double.parseDouble(tvResult.getText().toString());
            current = current / 100;
            tvResult.setText(formatResult(current));
            if (!isOperationClicked) {
                firstNumber = current;
            }
            return;
        }
    }

    private double calculate() {
        switch (currentOperation) {
            case "+": return firstNumber + secondNumber;
            case "-": return firstNumber - secondNumber;
            case "×": return firstNumber * secondNumber;
            case "÷":
                if (secondNumber == 0) {
                    tvResult.setText(R.string.error_division_by_zero);
                    return 0;
                }
                return firstNumber / secondNumber;
            default: return secondNumber;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        String str = String.valueOf(result);
        if (str.length() > 12) {
            str = str.substring(0, 12);
        }
        return str;
    }
}