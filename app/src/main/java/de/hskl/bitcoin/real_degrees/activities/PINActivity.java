package de.hskl.bitcoin.real_degrees.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.iota.jota.IotaAPI;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.TrytesConverter;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import de.hskl.bitcoin.real_degrees.R;
import de.hskl.bitcoin.real_degrees.util.DBManager;
import de.hskl.bitcoin.real_degrees.util.Utilities;

import static android.widget.Toast.makeText;

public class PINActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private DBManager db;

    private String pin_value,
            pubkeystring,
            recreatedString,
            birthdate,
            name,
            surname,
            university_name,
            course_name,
            grade;

    private TextView tv_valid;

    private volatile String messageFromTangle, address;

    private TextInputEditText tiet_pin_field;

    private TextInputLayout til_pin_field;

    private Button ok, back;

    private int row_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);


        readDataBase();
        doViewBinding();
        checkDarkMode();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (v == back) {
            db.deleteFromTested(row_id);
            Intent toMaster = new Intent(this, MasterViewActivity.class);
            startActivity(toMaster);
        }
        if (v == ok) {
            pin_value = String.valueOf(tiet_pin_field.getText());
            if (pin_value.matches(getString(R.string.pinActivity_ed_pin)) || pin_value.length() == 0) {
                makeText(this,
                        getResources().getString(R.string.pinActivity_error),
                        Toast.LENGTH_SHORT).show();
            } else {
                if (checkEncryption()) {
                    Intent toDetail = new Intent(this, DetailViewActivity.class);
                    toDetail.putExtra("rowid", row_id);
                    startActivity(toDetail);
                }
            }
        }
    }

    private void checkDarkMode() {
        if (Utilities.isDarkModeOn(this)) {
            tv_valid.setTextColor(getResources().getColor(R.color.white));
            back.setTextColor(getResources().getColor(R.color.white));
            ok.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void readDataBase() {
        db = DBManager.getInstance(this);
        Cursor cursor = db.getLastTested();

        address = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_TESTED_ADDRESS));

        row_id = cursor.getInt(cursor.getColumnIndex(DBManager.COLUMN_TESTED_ROW_ID));

        name = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_TESTED_NAME));
        surname = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_TESTED_SURNAME));


        birthdate = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_TESTED_BIRTH));
        university_name = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_UNIVERSITY_NAME));
        course_name = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_COURSE_NAME));
        grade = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_TESTED_GRADE));
        pubkeystring = cursor.getString(cursor.getColumnIndex(DBManager.COLUMN_UNIVERSITY_PUBLIC_KEY));

        cursor.close();
    }

    private void doViewBinding() {
        // Buttons
        ok = findViewById(R.id.pinactivity_bu_ok);
        back = findViewById(R.id.pinactivity_bu_back);
        ok.setOnClickListener(this);
        back.setOnClickListener(this);

        // TextInputEditTexts
        tiet_pin_field = (TextInputEditText) findViewById(R.id.pinactivity_ed_pin);
        TextInputEditText tiet_name = (TextInputEditText) findViewById(R.id.masterView_tiet_name);
        TextInputEditText tiet_surname = (TextInputEditText) findViewById(R.id.masterView_tiet_surname);
        tiet_pin_field.setOnFocusChangeListener(this);

        // TextView
        tv_valid = (TextView) findViewById(R.id.view_detail_tv_valid);
        tiet_name.setText(name);
        tiet_surname.setText(surname);

        til_pin_field = (TextInputLayout) findViewById(R.id.pinactivity_til_pin);
        tiet_pin_field.setText(getResources().getString(R.string.pinActivity_ed_pin));

    }

    private String readMessageFromHash() {
        ProgressDialog progressDialog = ProgressDialog.show(this,
                getString(R.string.pinActivity_loading_title),
                getString(R.string.pinActivity_loading_message),
                true);
        progressDialog.setCancelable(false);

        final String[] s = new String[1];
        Thread thread = new Thread(() -> {
            try {
                //Your code goes here
                IotaAPI api = new IotaAPI.Builder()
                        .protocol("https")
                        .host("nodes.iota.org")
                        .port(443)
                        .build();
                try {
                    List<Transaction> response = api.findTransactionObjectsByBundle(address);
                    org.iota.jota.model.Bundle bundle = new org.iota.jota.model.Bundle(response);

                    String test = "";
                    if (response.size() != 0) {
                        test = TrytesConverter.trytesToAscii(response.get(0).getSignatureFragments().substring(0, 2186));
                    }
                    if (response.size() > 1) {
                        test = TrytesConverter.trytesToAscii(response.get(1).getSignatureFragments().substring(0, 2186));
                    }

                    s[0] = test;
                } catch (ArgumentException e) {
                    // Handle error
                    e.printStackTrace();
                    s[0] = "Error";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (thread.isAlive()) {
            thread.interrupt();
        }
        return s[0];
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkEncryption() {
        // build input String
        String input = concatInputString();

        // get Message
        messageFromTangle = readMessageFromHash();
        if (messageFromTangle == null || messageFromTangle.matches("ERROR") || messageFromTangle.matches("")) {
            messageFromTangle = readMessageFromHash();
            Log.d("TEST", "message -1: " + messageFromTangle);
            if (messageFromTangle == null || messageFromTangle.matches("ERROR") || messageFromTangle.matches("")) {
                readMessageFromHash();
            }
        }
        //   read Hash from Address
        if (messageFromTangle == null) {
            readMessageFromHash();
        }

        //  do Algorithm
        recreatedString = doSHA256(input);

        // added fallback
        if (messageFromTangle == null) {
            messageFromTangle = db.repeatedScan(address, recreatedString);
        }

        // encrpyt message from Tangle
        String delimiter = "&";
        String[] parts;
        if (messageFromTangle != null) {
            parts = messageFromTangle.split(delimiter);
            String hash = parts[0];
            if (parts.length > 1) {
                String receivedSignature = parts[1];
                doRSA(hash, receivedSignature);
            }
        }
        boolean valid = this.compareHashValues();

        // save into local DB
        db.updateTested(
                pin_value,
                messageFromTangle,
                recreatedString,
                valid
        );

        db.close();

        return true;
    }

    private String concatInputString() {
       /* String data = "{\r\n" +
                "	\"Vorname\": \"" + name + "\",\r\n" +
                "	\"Nachname\": \"" + surname + "\",\r\n" +
                "	\"Geburtsdatum\": \"" + birthdate + "\",\r\n" +
                "	\"Universität\": \"" + university_name + "\",\r\n" +
                "	\"Studiengang\": \"" + course_name + "\",\r\n" +
                "	\"Endnote\": \"" + grade + "\",\r\n" +
                "	\"Pin\": \"" + pin_value + "\"\r\n" +
                "}";*/
        return "{\r\n" +
                "	\"Vorname\": \"" + name + "\",\r\n" +
                "	\"Nachname\": \"" + surname + "\",\r\n" +
                "	\"Geburtsdatum\": \"" + birthdate + "\",\r\n" +
                "	\"Universität\": \"" + university_name + "\",\r\n" +
                "	\"Studiengang\": \"" + course_name + "\",\r\n" +
                "	\"Endnote\": \"" + grade + "\",\r\n" +
                "	\"Pin\": \"" + pin_value + "\"\r\n" +
                "}";
    }

    private boolean compareHashValues() {
        if (messageFromTangle == null || recreatedString == null) {
            return false;
        }
        String delimiter = "&";
        String[] parts = messageFromTangle.split(delimiter);
        if (parts.length < 2) {
            return false;
        }
        String hash = parts[0];
        if (hash.length() != recreatedString.length()) {
            return false;
        }
        for (int i = 0; i < hash.length(); i++) {
            /*
            a value 0 if x==y
            a value less than 0 if x<y.
            a value greater than 0 if x>y
             */
            if (hash.charAt(i) != recreatedString.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    private void doRSA(String hash, String receivedSignature) {

        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        X509EncodedKeySpec keySpecX509 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(pubkeystring));
        }
        RSAPublicKey pubKey = null;
        try {
            assert kf != null;
            pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (
                InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //Creating a Signature object
        Signature sign = null;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Initializing the signature
        try {
            assert sign != null;
            sign.initVerify(pubKey);
        } catch (
                InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] hashBytes = hash.getBytes();
        try {
            sign.update(hashBytes);
        } catch (
                SignatureException e) {
            e.printStackTrace();
        }

        //Verify the signature
        try {
            // show valid
            // show fraud
            sign.verify(receivedSignature.getBytes());
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String doSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            til_pin_field.setHint(getString(R.string.pinActivity_ed_pin));
            tiet_pin_field.setText("");

            if (Utilities.isDarkModeOn(this)) {
                til_pin_field.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.headings)));
            }
        } else {
            til_pin_field.setHintAnimationEnabled(true);
            til_pin_field.setHint(getString(R.string.pinActivity_ed_pin));


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        db.deleteFromTested(row_id);
        Intent toMaster = new Intent(this, MasterViewActivity.class);
        startActivity(toMaster);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageFromTangle = null;
        db.close();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // readStuffFromInternet();
    }
}