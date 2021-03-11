package de.hskl.bitcoin.real_degrees.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import de.hskl.bitcoin.real_degrees.R;
import de.hskl.bitcoin.real_degrees.util.DBManager;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class ScanActivity extends AppCompatActivity{
    private boolean hasBeenScanned = false;

    private DBManager db;

    private String[] resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        db = DBManager.getInstance(this);

        resultSet = new String[7];
        //qr code scanner object
        IntentIntegrator qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                makeText(this, getResources().getText(R.string.scanActivity_error), LENGTH_LONG).show();
                Intent toMaster = new Intent(this, MasterViewActivity.class);
                startActivity(toMaster);
            } else {
                //if qr contains data
                if (hasBeenScanned) {
                    // has already been scanned
                    Intent toMaster = new Intent(this, MasterViewActivity.class);
                    startActivity(toMaster);
                } else {
                    try {
                        //converting the data to json
                        JSONObject obj = new JSONObject(result.getContents());
                        //setting values to textviews
                        // scanned = obj.getString("address");
                        if (obj.has("address") &&
                                obj.has("Vorname") &&
                                obj.has("Nachname") &&
                                obj.has("Geburtsdatum") &&
                                obj.has("Universität") &&
                                obj.has("Studiengang") &&
                                obj.has("Endnote")) {
                            // QR-Code has all required information
                            hasBeenScanned = true;

                            // int university_name,
                            resultSet[0] = obj.getString("address");
                            // String name,
                            resultSet[1] = obj.getString("Vorname");
                            //  String surname,
                            resultSet[2] = obj.getString("Nachname");
                            // String birth,
                            resultSet[3] = obj.getString("Geburtsdatum");
                            // int university_id
                            resultSet[4] = obj.getString("Universität");
                            // int course_id
                            resultSet[5] = obj.getString("Studiengang");
                            // double grade
                            resultSet[6] = obj.getString("Endnote");
                            // get correct university_id from [4]
                            int university_id = db.getUniversityByName(resultSet[4]);
                            // get correct course_id from [5]
                            int course_id = db.getCourseByName(resultSet[5], university_id);

                            long error = db.insertFormIntoTested(
                                    university_id,
                                    course_id,
                                    resultSet[1],
                                    resultSet[2],
                                    resultSet[3],
                                    resultSet[6],
                                    resultSet[0]
                            );
                            if (error != -1) {
                                // everything went okay
                                Intent toPIN = new Intent(this, PINActivity.class);
                                startActivity(toPIN);
                            } else {
                                makeText(this,
                                        getResources().getString(R.string.scanActivity_error),
                                        LENGTH_LONG).show();
                                Intent toMaster = new Intent(this, MasterViewActivity.class);
                                startActivity(toMaster);
                            }

                        } else {
                            // no mapping values
                            makeText(this,
                                    getResources().getString(R.string.scanActivity_not_readable),
                                    LENGTH_LONG).show();
                            Intent toMaster = new Intent(this, MasterViewActivity.class);
                            startActivity(toMaster);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // if control comes here
                        // that means the encoded format not matches
                        // in this case you can display whatever data is available on the qrcode
                        // to a toast

                        makeText(this,
                                getResources().getString(R.string.scanActivity_not_readable),
                                LENGTH_LONG).show();
                        Intent toMaster = new Intent(this, MasterViewActivity.class);
                        startActivity(toMaster);
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        Intent toMaster = new Intent(this, MasterViewActivity.class);
        startActivity(toMaster);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}