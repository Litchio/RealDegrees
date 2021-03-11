package de.hskl.bitcoin.real_degrees.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.hskl.bitcoin.real_degrees.R;
import de.hskl.bitcoin.real_degrees.util.DBManager;
import de.hskl.bitcoin.real_degrees.util.Utilities;

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_valid, iv_university_logo, iv_state;

    private int row_id;
    private TextView tv_valid;

    private TextInputEditText tiet_address, tiet_name_student, tiet_birth_date,
            tiet_name_course, tiet_degree_title, tiet_grade, tiet_university_name, tiet_university_category,
            tiet_university_year, tiet_university_students, tiet_university_promotion, tiet_pin, tiet_state;

    private TextInputLayout til_address, til_name_student, til_birth_date, til_name_course,
            til_degree_title, til_grade, til_university_name, til_university_category,
            til_university_year, til_university_students, til_university_promotion, til_pin, til_state;

    private Button delete, back;

    private DBManager db;

    private final Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        db = DBManager.getInstance(this);

        Intent fromPrevious = getIntent();
        if (fromPrevious.hasExtra("rowid")) {
            row_id = fromPrevious.getIntExtra("rowid", 1);
        }

        doViewBinding();

        fillData(db.selectTested(row_id));
    }

    @Override
    public void onBackPressed() {
        Intent toMaster = new Intent(this, MasterViewActivity.class);
        startActivity(toMaster);
    }

    private void doViewBinding() {
        // TextView
        tv_valid = (TextView) findViewById(R.id.view_detail_tv_valid);

        // ImageViews
        iv_valid = (ImageView) findViewById(R.id.view_detail_iv_valid);
        iv_university_logo = (ImageView) findViewById(R.id.view_detail_iv_university_logo);
        iv_state = (ImageView) findViewById(R.id.view_detail_iv_state);

        // TextInputEditText
        tiet_address = (TextInputEditText) findViewById(R.id.view_detail_tiet_address);
        tiet_name_student = (TextInputEditText) findViewById(R.id.view_detail_tiet_name_student);
        tiet_birth_date = (TextInputEditText) findViewById(R.id.view_detail_tiet_birth_date);
        tiet_name_course = (TextInputEditText) findViewById(R.id.view_detail_tiet_name_course);
        tiet_degree_title = (TextInputEditText) findViewById(R.id.view_detail_tiet_degree);
        tiet_grade = (TextInputEditText) findViewById(R.id.view_detail_tiet_grade);
        tiet_university_name = (TextInputEditText) findViewById(R.id.view_detail_tiet_name_university);
        tiet_university_category = (TextInputEditText) findViewById(R.id.view_detail_tiet_university_category);
        tiet_university_year = (TextInputEditText) findViewById(R.id.view_detail_tiet_university_year);
        tiet_university_students = (TextInputEditText) findViewById(R.id.view_detail_tiet_university_students);
        tiet_university_promotion = (TextInputEditText) findViewById(R.id.view_detail_tiet_university_promotion);
        tiet_pin = (TextInputEditText) findViewById(R.id.view_detail_tiet_pin);
        tiet_state = (TextInputEditText) findViewById(R.id.view_detail_tiet_university_state);

        // TextInputLayout
        til_address = (TextInputLayout) findViewById(R.id.view_detail_til_address);
        til_name_student = (TextInputLayout) findViewById(R.id.view_detail_til_name);
        til_birth_date = (TextInputLayout) findViewById(R.id.view_detail_til_birth);
        til_name_course = (TextInputLayout) findViewById(R.id.view_detail_til_course);
        til_degree_title = (TextInputLayout) findViewById(R.id.view_detail_til_degree);
        til_grade = (TextInputLayout) findViewById(R.id.view_detail_til_grade);
        til_university_name = (TextInputLayout) findViewById(R.id.view_detail_til_university_name);
        til_university_category = (TextInputLayout) findViewById(R.id.view_detail_til_university_category);
        til_university_year = (TextInputLayout) findViewById(R.id.view_detail_til_university_year);
        til_university_students = (TextInputLayout) findViewById(R.id.view_detail_til_university_students);
        til_university_promotion = (TextInputLayout) findViewById(R.id.view_detail_til_university_promotion);
        til_pin = (TextInputLayout) findViewById(R.id.view_detail_til_pin);
        til_state = (TextInputLayout) findViewById(R.id.view_detail_til_university_state);

        // Button
        back = (Button) findViewById(R.id.detail_bu_back);
        delete = (Button) findViewById(R.id.detail_bu_delete);

        back.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void fillData(Cursor c) {
        int valid = c.getInt(c.getColumnIndex(DBManager.COLUMN_TESTED_VALID));

        // valid
        tiet_name_student.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_NAME)) +
                " " + c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_SURNAME)));
        tiet_birth_date.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_BIRTH)));
        tiet_university_name.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_UNIVERSITY_NAME)));

        String full_title = c.getString(c.getColumnIndex(DBManager.COLUMN_COURSE_NAME));
        String[] parts = full_title.split("\\(");
        tiet_degree_title.setText(parts[1].substring(0, parts[1].length() - 2));
        tiet_name_course.setText(parts[0]);

        tiet_grade.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_GRADE)));
        tiet_university_year.setText(String.valueOf(c.getString(c.getColumnIndex(DBManager.COLUMN_UNIVERSITY_START))));
        tiet_university_category.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_CATEGORY_NAME)));
        tiet_address.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_ADDRESS)));
        tiet_university_students.setText(String.valueOf(c.getInt(c.getColumnIndex(DBManager.COLUMN_UNIVERSITY_STUDENTS))));
        tiet_university_promotion.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_PROMOTION_VALUE)));
        tiet_pin.setText(c.getString(c.getColumnIndex(DBManager.COLUMN_TESTED_PIN)));
        tiet_state.setText(c.getString((c.getColumnIndex(DBManager.COLUMN_STATE_NAME))));

        // ImageViews
        String path = c.getString(c.getColumnIndex("path"));
        int id = this.getResources().getIdentifier(path, "drawable", this.getPackageName());
        iv_university_logo.setImageResource(id);

        String path2 = c.getString(c.getColumnIndex(DBManager.COLUMN_STATE_PATH));
        int id2 = this.getResources().getIdentifier(path2, "drawable", this.getPackageName());
        iv_state.setImageResource(id2);


        if (Utilities.isDarkModeOn(ctx)) {
            tv_valid.setTextColor(getResources().getColor(R.color.white));
            back.setTextColor(getResources().getColor(R.color.white));
            delete.setTextColor(getResources().getColor(R.color.white));
        }

        if (valid == 1) {
            iv_valid.setImageDrawable(getResources().getDrawable(R.drawable.ic_valid));
        }
        if (valid == 0) {
            // fraud
            iv_valid.setImageDrawable(getResources().getDrawable(R.drawable.ic_fraud));
            tv_valid.setTextColor(getResources().getColor(R.color.fraudColor));
            tv_valid.setPaintFlags(tv_valid.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // TextInputEditText
            tiet_name_student.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_birth_date.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_name_course.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_university_name.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_degree_title.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_grade.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_university_year.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_university_category.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_address.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_university_students.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_university_promotion.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_pin.setTextColor(getResources().getColor(R.color.fraudColor));
            tiet_state.setTextColor(getResources().getColor(R.color.fraudColor));

            tiet_name_student.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_birth_date.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_name_course.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_university_name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_degree_title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_grade.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_university_year.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_university_category.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_address.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_university_students.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_university_promotion.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_pin.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tiet_state.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            // TextInputLayout
            til_address.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_birth_date.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_name_course.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_degree_title.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_university_promotion.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_university_name.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_university_category.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_university_year.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_university_students.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_grade.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_name_student.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_pin.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
            til_state.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.fraudColor)));
        }
        tv_valid.setText(getResources().getStringArray(R.array.masterView_state)[valid]);
    }

    @Override
    public void onClick(View v) {
        if (v == back) {
            Intent toMaster = new Intent(ctx, MasterViewActivity.class);
            startActivity(toMaster);
        }
        if (v == delete) {
            showDeleteDialog();
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = buildDialog(
                String.valueOf(getText(R.string.detailView_dialog_heading)),
                String.valueOf(getText(R.string.detailView_dialog_subheading)));
        builder.setPositiveButton(String.valueOf(getText(R.string.detailView_button_delete)),
                (dialog, which) -> {
                    db.deleteFromTested(row_id);
                    Intent toMaster = new Intent(ctx, MasterViewActivity.class);
                    startActivity(toMaster);

                    final Toast deleted = Toast.makeText(ctx,
                            getText(R.string.detailView_dialog_deleted), Toast.LENGTH_LONG);
                    deleted.show();
                });
        builder.setNegativeButton(String.valueOf(getText(R.string.detailView_dialog_cancel)),
                (dialog, which) -> {
                });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();

        if (Utilities.isDarkModeOn(this)) {
            deleteDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ctx.getResources().getColor(R.color.white)
            );
            deleteDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ctx.getResources().getColor(R.color.white)
            );
        }
    }

    @NonNull
    protected AlertDialog.Builder buildDialog(@NonNull String title, @NonNull String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}
