package com.hci.lab430.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hci.lab430.myapplication.model.OwningPokemonInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by lab430 on 16/7/28.
 */
public class PokemonDetailActivity extends CustomizedActivity {

    //constant
    public final static int updateData = 1;
    public final static int removeFromList = 2;

    OwningPokemonInfo mData;
    String packageName;
    Resources mRes;
    Picasso mPicasso;
    Intent returnIntent;

    //UI
    ImageView appearanceImg;
    TextView nameText;
    TextView levelText;
    TextView currentHPText;
    TextView maxHPText;
    TextView type1Text;
    TextView type2Text;
    TextView[] skillsText = new TextView[OwningPokemonInfo.maxNumSkills];
    ProgressBar HPProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = getIntent().getParcelableExtra(OwningPokemonInfo.parcelKey);
        mRes = getResources();
        packageName = getPackageName();
        mPicasso = Picasso.with(this);
        returnIntent = new Intent();

        setView();
    }

    //setup UI elements
    private void setView() {
        setContentView(R.layout.detail_view);
        appearanceImg = (ImageView)findViewById(R.id.detail_appearance_img);
        nameText = (TextView)findViewById(R.id.name_text);
        levelText = (TextView)findViewById(R.id.level_text);
        currentHPText = (TextView)findViewById(R.id.currentHP_text);
        maxHPText = (TextView)findViewById(R.id.maxHP_text);
        type1Text = (TextView)findViewById(R.id.type_1_text);
        type2Text = (TextView)findViewById(R.id.type_2_text);

        for(int i = 0;i < OwningPokemonInfo.maxNumSkills;i++) {
            int skillTextId = mRes.getIdentifier(String.format("skill_%d_text",i + 1), "id", packageName);
            skillsText[i] = (TextView) findViewById(skillTextId);
        }
        HPProgressBar = (ProgressBar)findViewById(R.id.HP_progressBar);

        //bind with data
        mPicasso.load(mData.getDetailImgId()).into(appearanceImg);
        nameText.setText(mData.getName());
        levelText.setText(String.valueOf(mData.getLevel()));
        currentHPText.setText(String.valueOf(mData.getCurrentHP()));
        maxHPText.setText(String.valueOf(mData.getMaxHP()));
        if(mData.getType_1() != -1) {
            type1Text.setText(OwningPokemonInfo.typeNames[mData.getType_1()]);
        }
        else {
            type1Text.setText("");
        }

        if(mData.getType_2() != -1) {
            type2Text.setText(OwningPokemonInfo.typeNames[mData.getType_2()]);
        }
        else {
            type2Text.setText("");
        }
        for(int i = 0;i < mData.getSkill().length;i++) {
            if(mData.getSkill()[i] != null) {
                skillsText[i].setText(mData.getSkill()[i]);
            }
            else {
                skillsText[i].setText("");
            }
        }
        HPProgressBar.setProgress((int)(((float) mData.getCurrentHP() / mData.getMaxHP()) * 100));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_detail_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_save) {
            returnIntent.putExtra(OwningPokemonInfo.nameKey, mData.getName());
            setResult(removeFromList, returnIntent);
            onBackPressed();
            return true;
        }
        else if(itemId == R.id.action_level_up){
            //implement some logic here
            mData.setLevel(mData.getLevel()+1);
            levelText.setText(String.valueOf(mData.getLevel()));
            returnIntent.putExtra(OwningPokemonInfo.parcelKey,mData);
            setResult(updateData,returnIntent);
            Log.d("PokemonDetailActivity","the result is:"+ String.valueOf(mData.getLevel()));
            return true;
        }
        else if(itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

}
