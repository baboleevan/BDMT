
package com.anp.bdmt;

import static com.anp.bdmt.gcm.Util.getPhoneNumber;

import com.anp.bdmt.gcm.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Jung-Hum Cho Created by anp on 15. 1. 22..
 */
public class SplLoginActivity extends BaseActivity {

    private EditText etPhoneNum;

    private CheckBox cbAutoLogin;

    private Activity mThis = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        killer.addActivity(this);
        mDialog = new CustomDialog(this);

        cbAutoLogin = (CheckBox)findViewById(R.id.auto_login);
        etPhoneNum = (EditText)findViewById(R.id.field_phone);
        etPhoneNum.setText(getPhoneNumber(this));

        // if (Util.loadSharedPreferencesBoolean(this, "point_autologin")) {
        //
        // cbAutoLogin.setChecked(true);
        // loginWork();
        // }

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWork();
            }
        });
    }

    private void loginWork() {

        String phoneNum = etPhoneNum.getText().toString();

        boolean isAutoLogin = cbAutoLogin.isChecked();

        if ("".equals(phoneNum)) {

            Toast.makeText(mThis, "핸드폰번호를 입력해주세요.", Toast.LENGTH_SHORT).show();

        } else {

            new LoginTask(isAutoLogin).execute(phoneNum);

        }
    }

    private class LoginTask extends AsyncTask<String, Void, JSONObject> {

        private LoginTask(boolean isAutoLogin) {
            this.isAutoLogin = isAutoLogin;
        }

        private boolean isAutoLogin;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String phoneNum = params[0];

            String url = "http://cashq.co.kr/m/login_json.php?userid=" + phoneNum;

            return new JsonParser().getJSONObjectFromUrl(url);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                if (jsonObject.getBoolean("success")) {

                    String phoneNum = jsonObject.getString("user_id").replace("-", "");

                    Util.saveSharedPreferences_boolean(mThis, "spl_autologin", isAutoLogin);

                    Intent intent = new Intent(mThis, SplActivity.class);

                    intent.putExtra("phoneNum", phoneNum);

                    startActivity(intent);

                } else {

                    String msg = jsonObject.getString("msg");
                    Toast.makeText(mThis, msg, Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        killer.removeActivity(this);
    }
}
