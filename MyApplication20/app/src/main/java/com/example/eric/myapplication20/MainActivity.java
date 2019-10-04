/**
 * @author Eric Voong
 * @Description: Main Activity for Roman Numeral Converter App
 * @Date: 01/24/18 (mm/dd/yy)
 */

package com.example.eric.myapplication20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static Roman r = new Roman();
    public static TextView inputText;
    public static TextView answer;
    public static int[] numbers = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button0};
    public boolean plus = false;
    public boolean minus = false;
    public boolean convert = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        answer = findViewById(R.id.answer);

        setNumericOnClickListener();

        /**
         * @description deletes the last index of the input text
         */
        findViewById(R.id.buttonDEL).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (inputText.getText().length() > 0) {
                    inputText.setText(inputText.getText().subSequence(0, inputText.getText().length() - 1));
                    for (int id : numbers) {
                        if (numCheck(inputText, ((Button) findViewById(id)).getText().toString()))
                            enable(id);
                        else
                            disable(id);
                    }
                }

            }
        });

        /**
         * @description resets answer text, input text, and numbers / roman numerals when clicked
         */
        findViewById(R.id.buttonC).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((Button) findViewById(R.id.buttonMinus)).setTextColor(getResources().getColor(R.color.orange));
                ((Button) findViewById(R.id.buttonPlus)).setTextColor(getResources().getColor(R.color.orange));
                for (int id = 0; id < numbers.length - 1; id++) {
                    enable(numbers[id]);
                }
                r = new Roman();
                inputText.setText("");
                answer.setText("");
            }
        });

        /**
         * @description utilise the onEqual function when clicked
         */
        findViewById(R.id.buttonEqual).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onEqual();
            }
        });

        /**
         * @description activates addition when pressed
         */
        findViewById(R.id.buttonPlus).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onEqual();
                ((Button) findViewById(R.id.buttonPlus)).setTextColor(getResources().getColor(R.color.colorAccent));
                if (answer.getText().length() > 0) {
                    plus = true;
                    minus = false;
                }
            }
        });

        /**
         * @description activates subtraction when pressed
         */
        findViewById(R.id.buttonMinus).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onEqual();
                ((Button) findViewById(R.id.buttonMinus)).setTextColor(getResources().getColor(R.color.colorAccent));
                if (answer.getText().length() > 0) {
                    minus = true;
                    plus = false;
                }
            }
        });

        /**
         * @description switches converter from decimal to roman numerals and vice versa depending on what is currently being converted if the switch button is clicked
         */
        findViewById(R.id.switcher).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switches converter to roman to decimals
                if (convert) {
                    ((TextView) findViewById(R.id.from)).setText("ROMAN");
                    ((TextView) findViewById(R.id.to)).setText("DECIMAL");

                    // switch numbers to roman numerals
                    for (int i = 0; i < numbers.length - 1; i++) {
                        Button button = findViewById(numbers[i]);
                        button.setText(Roman.letters2.get(Roman.letters2.size() - i - 1).toString());
                    }

                    //removes the "0" button since there's no value for 0 in roman numerals
                    Button zero = findViewById(numbers[9]);
                    zero.setVisibility(View.INVISIBLE);
                    zero.setText("");
                    convert = false;

                    // converts input text to roman numerals
                    if (inputText.getText().length() > 0)
                        inputText.setText(r.convertToString(Integer.valueOf(inputText.getText().toString())));

                    // converts answer to decimals
                    if (answer.getText().length() > 0 & answer.getText().toString() != "error" && answer.getText().toString() != "0")
                        answer.setText(String.valueOf(r.convertToInt(answer.getText().toString())));
                }
                // switches converter to decimal to roman numerals
                else if (!convert) {
                    ((TextView) findViewById(R.id.from)).setText("DECIMAL");
                    ((TextView) findViewById(R.id.to)).setText("ROMAN");

                    // switches roman numerals to numbers
                    for (int i = 0; i < numbers.length - 1; i++) {
                        Button button = findViewById(numbers[i]);
                        button.setText(String.valueOf(i + 1));
                    }

                    //renables "0" button
                    Button zero = findViewById(numbers[9]);
                    zero.setVisibility(View.VISIBLE);
                    zero.setText("0");
                    convert = true;

                    // switches input text to decimals
                    if (inputText.getText().length() > 0)
                        inputText.setText(String.valueOf(r.convertToInt((inputText.getText().toString()))));

                    //switches answer to numerals
                    if (answer.getText().length() > 0 & answer.getText().toString() != "error")
                        answer.setText(r.convertToString(Integer.valueOf(answer.getText().toString())));
                }
            }
        });
    }

    /**
     *
     * @param x is the current inputted text
     * @param i is the string being appended
     * @return true or false depending if the new input text would be a valid roman numeral
     */
    public boolean numCheck(TextView x, String i) {

        // checks if numeral being appended is valid
        if (convert)
            return r.set(Integer.valueOf(x.getText() + i));

            // checks if roman numeral being appended is valid
        else if (!convert)
            return r.set(x.getText() + i);
        else
            return false;
    }

    /**
     * @description appends a number or roman numeral to the input text when clicked
     */
    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button) v;
                inputText.setText(inputText.getText().toString() + button.getText());
                for (int id : numbers) {
                    if (numCheck(inputText, ((Button) findViewById(id)).getText().toString()))
                        enable(id);
                    else
                        disable(id);
                }
            }

        };
        for (int id : numbers) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * @param id is the id of the button
     * @descrition enables a button for use
     */
    public void enable(int id) {
        ((Button) findViewById(id)).setTextColor(getResources().getColor(R.color.orange));
        findViewById(id).setEnabled(true);
    }

    /**
     * @param id is the id of the button
     * @descrition disable a button for no use
     */
    public void disable(int id) {
        ((Button) findViewById(id)).setTextColor(getResources().getColor(R.color.gray));
        findViewById(id).setEnabled(false);
    }


    /**
     * @description determines the answers and converts it depending on the current converter nd resets input, and operators
     */
    public void onEqual() {
        //  resets operators
        boolean valid = true;
        ((Button) findViewById(R.id.buttonMinus)).setTextColor(getResources().getColor(R.color.orange));
        ((Button) findViewById(R.id.buttonPlus)).setTextColor(getResources().getColor(R.color.orange));

        // re-enble valid numbers or roman numerals
        for (int id = 0; id < numbers.length - 1; id++) {
            enable(numbers[id]);
        }
        disable(numbers[9]);

        if (inputText.getText().length() > 0) {

            // when addition is activated, it will add the input text to the current answer
            if (plus) {
                if (convert) {
                    if (!r.add(Integer.valueOf(inputText.getText().toString())))
                        valid = false;
                } else if (!convert) {
                    if (!r.add(inputText.getText().toString()))
                        valid = false;
                }
            }

            // when subtraction is activate, it will subtract the current answer by the input text
            if (minus) {
                if (convert) {
                    if (!r.subtract(Integer.valueOf(inputText.getText().toString())))
                        valid = false;
                } else if (!convert) {
                    if (!r.subtract(inputText.getText().toString()))
                        valid = false;
                }
            }

            // if subtraction and addition are not active then input just be converted
            else if (!plus & !minus) {
                // converts a
                if (convert)
                    r = new Roman(Integer.valueOf(inputText.getText().toString()));
                else if (!convert)
                    r = new Roman(inputText.getText().toString());
            }
            inputText.setText("");
        }

        // converts the answer into a roman numeral
        if (convert)
            answer.setText(r.toRoman());
        // converts the answer into a decimal
        else if (!convert && r.toInt() != 0)
            answer.setText(String.valueOf(r.toInt()));

        // if the answer is not a valid roman numeral answer text will displays an error message
        if (!valid) {
            r = new Roman();
            answer.setText("Roman Numerals must be from 1 - 49999");
        }

        // reset operators
        plus = false;
        minus = false;
    }

}