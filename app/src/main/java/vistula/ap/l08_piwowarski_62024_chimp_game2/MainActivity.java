package vistula.ap.l08_piwowarski_62024_chimp_game2;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int[] numbersap;
    int[] numberAfterPermutationap;
    int ColumnsButtonssap = 4;
    int RowsButtonsap = 4;
    int totalButtonsap = ColumnsButtonssap * RowsButtonsap;
    int screenWidthap;
    int screenHeightap;
    Button[][] buttonsap;
    int buttonsIDap[][];
    int leftap = 160;
    int topap = 220;
    int[] playerNumberap;
    int resultap;
    int currentButtonap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Ready();
    }
    private void Ready(){
        TextView Widthap = (TextView)findViewById(R.id.txtWidthap);
        TextView Heightap = (TextView)findViewById(R.id.txtHeightap);
        WindowManager windowManagerap = getWindowManager();
        Point sizeap = new Point();
        windowManagerap.getDefaultDisplay().getSize(sizeap);
        screenWidthap = sizeap.x;
        screenHeightap = sizeap.y;
        Widthap.setText(Integer.toString(screenWidthap));
        Heightap.setText(Integer.toString(screenHeightap));
        Spinner spinnerColumnsap = (Spinner)findViewById(R.id.SpinnerNumberColumnssap);
        Spinner spinnerRowsap = (Spinner)findViewById(R.id.SpinnerNumberRowsap);
        ColumnsButtonssap = Integer.parseInt(spinnerColumnsap.getSelectedItem().toString());
        RowsButtonsap = Integer.parseInt(spinnerRowsap.getSelectedItem().toString());
        totalButtonsap = ColumnsButtonssap * RowsButtonsap;
        numbersap = new int[totalButtonsap];
        for (int i = 0; i< totalButtonsap; i++){
            numbersap[i]=i+1;
        }
        numberAfterPermutationap = numbersap;
        playerNumberap = new int[totalButtonsap];
        buttonsap = new Button[RowsButtonsap][ColumnsButtonssap];
        buttonsIDap = new int[RowsButtonsap][ColumnsButtonssap];
        startButtonsap();
        resultap = 0;
    }
    private void startButtonsap(){
        Spinner spinnerColumnsap = (Spinner)findViewById(R.id.SpinnerNumberColumnssap);
        Spinner spinnerRowsap = (Spinner)findViewById(R.id.SpinnerNumberRowsap);
        ColumnsButtonssap = Integer.parseInt(spinnerColumnsap.getSelectedItem().toString());
        RowsButtonsap = Integer.parseInt(spinnerRowsap.getSelectedItem().toString());
        ConstraintLayout apconstraintLayout = (ConstraintLayout) findViewById(R.id.apconstraintLayout);
        apconstraintLayout.removeAllViews();
        ConstraintLayout.LayoutParams apparams;
        int buttonWidth = 150, buttonHeight = 100;

        WindowManager windowManager = getWindowManager();
        Point sizeap = new Point();
        windowManager.getDefaultDisplay().getSize(sizeap);
        screenWidthap = sizeap.x;
        screenHeightap = sizeap.y;
        leftap = Math.round((screenWidthap - buttonWidth * ColumnsButtonssap) / 2);

        // Create and place the buttons for the grid
        for (int i = 0; i < RowsButtonsap; i++) {
            for (int j = 0; j < ColumnsButtonssap; j++) {
                buttonsap[i][j] = new Button(this);
                buttonsap[i][j].setId(View.generateViewId());
                buttonsIDap[i][j] = buttonsap[i][j].getId();
                apparams = new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight);
                buttonsap[i][j].setLayoutParams(apparams);
                apconstraintLayout.addView(buttonsap[i][j]);
                ConstraintSet apset = new ConstraintSet();
                apset.clone(apconstraintLayout);
                if (j == 0) {
                    apset.connect(buttonsap[i][j].getId(), ConstraintSet.LEFT, apconstraintLayout.getId(), ConstraintSet.LEFT, leftap);
                } else {
                    apset.connect(buttonsap[i][j].getId(), ConstraintSet.LEFT, buttonsap[i][j - 1].getId(), ConstraintSet.RIGHT, 0);
                }
                if (i == 0) {
                    apset.connect(buttonsap[i][j].getId(), ConstraintSet.TOP, apconstraintLayout.getId(), ConstraintSet.TOP, topap);
                } else {
                    apset.connect(buttonsap[i][j].getId(), ConstraintSet.TOP, buttonsap[i - 1][j].getId(), ConstraintSet.BOTTOM, 0);
                }
                apset.applyTo(apconstraintLayout);
                buttonsap[i][j].setOnClickListener(apbuttonClickListener);
            }
        }

        Button skipButtonap = new Button(this);
        skipButtonap.setId(View.generateViewId());
        skipButtonap.setText("++");
        ConstraintLayout.LayoutParams skipParamsap = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        skipParamsap.width = buttonWidth;
        skipParamsap.height = buttonHeight;
        skipButtonap.setLayoutParams(skipParamsap);
        apconstraintLayout.addView(skipButtonap);

        ConstraintSet set = new ConstraintSet();
        set.clone(apconstraintLayout);
        set.connect(skipButtonap.getId(), ConstraintSet.TOP, buttonsap[RowsButtonsap - 1][ColumnsButtonssap - 1].getId(), ConstraintSet.BOTTOM, 0);
        set.connect(skipButtonap.getId(), ConstraintSet.START, apconstraintLayout.getId(), ConstraintSet.START, 0);
        set.connect(skipButtonap.getId(), ConstraintSet.END, apconstraintLayout.getId(), ConstraintSet.END, 0);
        set.applyTo(apconstraintLayout);

        skipButtonap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentButtonap < totalButtonsap) {
                    currentButtonap++;
                }
            }
        });
        numbersButtonsap();
    }

    final View.OnClickListener apbuttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Spinner spinnerColumnsap = (Spinner)findViewById(R.id.SpinnerNumberColumnssap);
            Spinner spinnerRowsap = (Spinner)findViewById(R.id.SpinnerNumberRowsap);
            ColumnsButtonssap = Integer.parseInt(spinnerColumnsap.getSelectedItem().toString());
            RowsButtonsap = Integer.parseInt(spinnerRowsap.getSelectedItem().toString());
            totalButtonsap = ColumnsButtonssap * RowsButtonsap;
            Button apb = (Button)view;
            if ((currentButtonap < totalButtonsap)&&(apb.getText().toString().equals("*"))){
                apb.setText(Integer.toString(++currentButtonap));
            }
        }
    };
    private void numbersButtonsap() {
        String strap;
        for (int i = 0; i < RowsButtonsap; i++) {
            for (int j = 0; j < ColumnsButtonssap; j++) {
                strap = Integer.toString(numberAfterPermutationap[i * ColumnsButtonssap + j]);
                buttonsap[i][j].setText(strap);
            }
        }
    }

    public void makePermutationap(View view){
        Ready();
        numberAfterPermutationap = ArrayPermutation.shuffleFisherYeats(numbersap);
        numbersButtonsap();
    }
    public void hideNumbersButtonsap(View view){
        Spinner spinnerColumnsap = (Spinner)findViewById(R.id.SpinnerNumberColumnssap);
        Spinner spinnerRowsap = (Spinner)findViewById(R.id.SpinnerNumberRowsap);
        ColumnsButtonssap = Integer.parseInt(spinnerColumnsap.getSelectedItem().toString());
        RowsButtonsap = Integer.parseInt(spinnerRowsap.getSelectedItem().toString());
        for (int i = 0; i< RowsButtonsap; i++){
            for (int j = 0; j< ColumnsButtonssap; j++){
                buttonsap[i][j].setText("*");
            }
        }
        currentButtonap = 0;
    }
    public void resultGameap(View view){
        Button buttonap;
        resultap = 0;
        Spinner spinnerColumnsap = (Spinner)findViewById(R.id.SpinnerNumberColumnssap);
        Spinner spinnerRowsap = (Spinner)findViewById(R.id.SpinnerNumberRowsap);
        ColumnsButtonssap = Integer.parseInt(spinnerColumnsap.getSelectedItem().toString());
        RowsButtonsap = Integer.parseInt(spinnerRowsap.getSelectedItem().toString());
        for (int i = 0; i< RowsButtonsap; i++){
            for (int j = 0; j< ColumnsButtonssap; j++){
                buttonap = (Button)findViewById(buttonsIDap[i][j]);
                String strNumberap = buttonap.getText().toString();
                try {
                    playerNumberap[i* ColumnsButtonssap +j] = Integer.parseInt(strNumberap);
                }catch (NumberFormatException e){
                    playerNumberap[i* ColumnsButtonssap +j] = 0;
                }
                resultap += (playerNumberap[i* ColumnsButtonssap +j] == numberAfterPermutationap[i* ColumnsButtonssap +j])?1:0;
            }
        }
        TextView textViewResultap = (TextView)findViewById(R.id.aptxtResult);
        textViewResultap.setText(Integer.toString(resultap));
        for (int i = 0; i< RowsButtonsap; i++) {
            for (int j = 0; j < ColumnsButtonssap; j++) {
                buttonap = (Button) findViewById(buttonsIDap[i][j]);
                String strResultap = Integer.toString(numberAfterPermutationap[i * ColumnsButtonssap + j]) + "," + Integer.toString(playerNumberap[i * ColumnsButtonssap + j]);
                buttonap.setText(strResultap);
            }
        }
    }
    public void NewGameap(View view){
        Ready();
    }
}