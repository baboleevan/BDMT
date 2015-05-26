
package kr.co.cashqc;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.cashqc.gcm.Util;

import static kr.co.cashqc.gcm.Util.getPhoneNumber;

/**
 * @author Jung-Hum Cho Created by anp on 14. 11. 18..
 */
public class LoginActivity extends BaseActivity {

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

        if (Util.loadSharedPreferencesBoolean(this, "point_autologin")) {

            cbAutoLogin.setChecked(true);
            loginWork();
        }

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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join:
                startActivity(new Intent(this, JoinActivity.class));
                break;
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
            if (!mDialog.isShowing())
                mDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String baseUrl = "http://cashq.co.kr/m/login_json.php?userid=";
            JSONParser parser = new JSONParser();
            return parser.getJSONObjectFromUrl(baseUrl + params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            super.onPostExecute(object);

            String success = null;
            String msg = null;

            try {
                if (object.has("success")) {
                    success = object.getString("success");
                }
                msg = object.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if ("true".equals(success)) {

                Util.saveSharedPreferences_boolean(mThis, "point_autologin", isAutoLogin);

                Intent intent = new Intent(LoginActivity.this, PointActivity.class);
                intent.putExtra("phoneNum", etPhoneNum.getText().toString());
                startActivity(intent);

            } else {

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                intent.putExtra("phoneNum", etPhoneNum.getText().toString());
                CustomDialog joinDialog = new CustomDialog(LoginActivity.this, msg,
                        LoginActivity.this, intent);
                joinDialog.show();
            }

            if (mDialog.isShowing())
                mDialog.dismiss();
        }

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        killer.removeActivity(this);
    }
}
