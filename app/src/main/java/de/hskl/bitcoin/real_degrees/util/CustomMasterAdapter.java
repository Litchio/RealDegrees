package de.hskl.bitcoin.real_degrees.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import de.hskl.bitcoin.real_degrees.R;

public class CustomMasterAdapter extends CursorAdapter {

    LayoutInflater LayoutInflater;
    int itemLayout;
    String[] from;
    int[] to;

    public CustomMasterAdapter(@NonNull Context ctx, int itemLayout, @NonNull Cursor c,
                               @NonNull String[] from, @NonNull int[] to, int flags) {
        super(ctx, c, flags);
        LayoutInflater = android.view.LayoutInflater.from(ctx);
        this.itemLayout = itemLayout;
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public View newView(@NonNull Context ctx, @NonNull Cursor c, @NonNull ViewGroup parent) {
        return LayoutInflater.inflate(itemLayout, parent, false);

    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void bindView(@NonNull View v, @NonNull Context ctx, @NonNull Cursor c) {
        // Image + State
        int valid = c.getInt(c.getColumnIndex(from[0]));
        ImageView iv_state = (ImageView) v.findViewById(to[0]);
        iv_state.setImageDrawable(null);

        TextView tv_state = (TextView) v.findViewById(to[1]);
        // Name + " " + Surname
        String name_surname = c.getString(c.getColumnIndex(from[1]))
                + " "
                + c.getString(c.getColumnIndex(from[2]));
        TextView tv_name = (TextView) v.findViewById(to[2]);
        tv_name.setText(name_surname);

        // university
        String uni_name = c.getString(c.getColumnIndex(from[3]));
        TextView tv_university = (TextView) v.findViewById(to[3]);
        tv_university.setText(uni_name);

        // course
        String course = c.getString(c.getColumnIndex(from[4]));
        TextView tv_course = (TextView) v.findViewById(to[4]);
        tv_course.setText(course);

        // grade
        String grade = c.getString(c.getColumnIndex(from[5]));
        TextView tv_grade = (TextView) v.findViewById(to[5]);
        tv_grade.setText(grade);

        // valid
        tv_state.setText(ctx.getResources().getStringArray(R.array.masterView_state)[valid]);

        if (valid == 1) {
            // valid
            iv_state.setImageDrawable(null);
            iv_state.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_valid));
        } else if (valid == 0) {
            // fraud
            iv_state.setImageDrawable(null);
            iv_state.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_fraud));

            tv_name.setTextColor(ctx.getResources().getColor(R.color.fraudColor));
            // tv_country.setTextColor(ctx.getResources().getColor(R.color.fraudColor));
            tv_university.setTextColor(ctx.getResources().getColor(R.color.fraudColor));
            tv_course.setTextColor(ctx.getResources().getColor(R.color.fraudColor));
            tv_grade.setTextColor(ctx.getResources().getColor(R.color.fraudColor));
            tv_state.setTextColor(ctx.getResources().getColor(R.color.fraudColor));

            tv_name.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // tv_country.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_university.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_course.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_grade.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_state.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
