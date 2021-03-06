package com.hci.lab430.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by lab430 on 16/7/20.
 */
public class PokemonWelcomeActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,EditText.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {

    TextView infoText;
    EditText nameEditText;
    RadioGroup optionGroup;
    Button confirmBtn;
    CheckBox hideCheckBox; // 可以使用這個成員變數來記住抓到的Checkbox物件
    boolean hide;
    int selectedOptionIndex = 0;

    String[] pokemonNames = new String[]{
            "小火龍",
            "傑尼龜",
            "妙蛙種子"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_welcome);

        infoText = (TextView)findViewById(R.id.info_text);
        nameEditText = (EditText)findViewById(R.id.name_editText);
        nameEditText.setOnEditorActionListener(this);

        confirmBtn = (Button)findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(this);

        optionGroup = (RadioGroup)findViewById(R.id.option_radioGrp);
        optionGroup.setOnCheckedChangeListener(this);

        hideCheckBox = (CheckBox)findViewById(R.id.checkBox);
        hideCheckBox.setOnCheckedChangeListener(this);
        // 實作類似以上的步驟去找到相對應的Checkbox元件
        // 並設定listener去listen被勾取的事件（可參考Button以及OptionGroup）
        // 設定TextView的text可以參考onClick裡infoText的邏輯
        hide = false;
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if(viewId == R.id.confirm_btn) {
            if(hide){
                infoText.setText(String.format("你好, 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s", pokemonNames[selectedOptionIndex]));
            }
            else {
                infoText.setText(String.format("你好, 訓練家%s 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s", nameEditText.getText(), pokemonNames[selectedOptionIndex]));
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        int radioGrpId = radioGroup.getId();
        if(radioGrpId == optionGroup.getId()) {
            switch(checkId) {
                case R.id.option1:
                    selectedOptionIndex = 0;
                    break;
                case R.id.option2:
                    selectedOptionIndex = 1;
                    break;
                case R.id.option3:
                    selectedOptionIndex = 2;
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton cbox, boolean check) {
        hide=hideCheckBox.isChecked();
//        Log.d("PokemonWelcomeActivity","test1");
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //dismiss virtual keyboard
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

            //simulate button clicked
            confirmBtn.performClick();
            return true;
        }
        return false;
    }

}
