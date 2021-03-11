package de.hskl.bitcoin.real_degrees.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hskl.bitcoin.real_degrees.R;
import de.hskl.bitcoin.real_degrees.util.CustomMasterAdapter;
import de.hskl.bitcoin.real_degrees.util.CustomMasterAdapterDarkMode;
import de.hskl.bitcoin.real_degrees.util.DBManager;
import de.hskl.bitcoin.real_degrees.util.Utilities;

public class MasterViewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private DBManager db;

    private ListView master;

    private LinearLayout instructions;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_master);

        this.doViewBinding();
        setMaster();
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent toScan = new Intent(this, ScanActivity.class);
            startActivity(toScan);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void doViewBinding() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(this.getResources().getColor(R.color.layoutColor));
        fab.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_add_white_48));
        fab.setOnClickListener(this);

        instructions = (LinearLayout) findViewById(R.id.master_view_hint);

        TextView tv_manual = (TextView) findViewById(R.id.master_tv_manual);

        master = (ListView) findViewById(R.id.master);
        registerForContextMenu(master);
        master.setOnItemClickListener(this);
        master.setOnItemClickListener(this);

        if (Utilities.isDarkModeOn(this)) {
            tv_manual.setTextColor(getResources().getColor(R.color.white));
            ImageView logo = (ImageView) findViewById(R.id.master_iv_logo);
            logo.setImageDrawable(getResources().getDrawable(R.drawable.logo_rd_loading_dark));
        }
    }

    private void setMaster() {
        db = DBManager.getInstance(this);
        int itemLayout = R.layout.adadpter_tested;

        Cursor cursor = db.selectAllTested(Utilities.getLanguage());
        if (cursor.getCount() > 0) {
            instructions.setVisibility(View.INVISIBLE);
            String[] from = new String[]{
                    DBManager.COLUMN_TESTED_VALID,
                    DBManager.COLUMN_TESTED_NAME,
                    DBManager.COLUMN_TESTED_SURNAME,
                    DBManager.COLUMN_UNIVERSITY_NAME,
                    DBManager.COLUMN_COURSE_NAME,
                    DBManager.COLUMN_TESTED_GRADE
            };
            int[] to = new int[]{
                    R.id.adapter_iv_state,
                    R.id.adapter_tv_state,
                    R.id.adapter_tv_student_name,
                    R.id.adapter_tv_student_university,
                    R.id.adapter_tv_student_course,
                    R.id.adapter_tv_student_grade};

            if (Utilities.isDarkModeOn(this)) {
                CustomMasterAdapterDarkMode adapter = new CustomMasterAdapterDarkMode(this, itemLayout, cursor, from, to, 0);
                master.setAdapter(adapter);

            } else {
                CustomMasterAdapter adapter = new CustomMasterAdapter(this, itemLayout, cursor, from, to, 0);
                master.setAdapter(adapter);
            }
        } else {
            instructions.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == master) {
            int row_id = db.selectRowIDBySelection(position);
            Intent toDetailView = new Intent(this, DetailViewActivity.class);
            toDetailView.putExtra("rowid", row_id);
            db.close();
            startActivity(toDetailView);
        }
    }

    @Override
    public void onBackPressed() {
        Intent toHomeScreen = new Intent(Intent.ACTION_MAIN);
        toHomeScreen.addCategory(Intent.CATEGORY_HOME);
        startActivity(toHomeScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}