package info.xudshen.jandan.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Method;

import info.xudshen.jandan.R;
import info.xudshen.jandan.domain.enums.ImageQuality;
import info.xudshen.jandan.utils.ClipboardHelper;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.adapter.AppAdapters;

/**
 * Created by xudshen on 16/2/28.
 */
public class JandanSettingActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_FILTER_CHANGED = 0x01;
    public static final int RESULT_FILTER_NOT_CHANGED = 0x02;

    public static final String FILTER_XX_GT = "filterXXgt";
    public static final String FILTER_XX_GT_OO = "filterXXgtOO";
    public static final String SETTING_IMAGE_QUALITY = "imageQuality";

    public static final String SETTING_USERNAME = "userName";
    public static final String SETTING_USEREMAIL = "userEmail";

    public static boolean getSettingFilterXXgtOO(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(JandanSettingActivity.FILTER_XX_GT_OO, false);
    }

    public static int getSettingFilterXXgt(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int filterXXgt = preferences.getInt(JandanSettingActivity.FILTER_XX_GT, 0);
        return filterXXgt == 0 ? Integer.MAX_VALUE : filterXXgt;
    }

    public static ImageQuality getSettingImageQuality(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (Integer.valueOf(preferences.getString(JandanSettingActivity.SETTING_IMAGE_QUALITY, "2"))) {
            case 1: {
                return ImageQuality.HIGH;
            }
            case 2: {
                return ImageQuality.MEDIUM;
            }
            case 3: {
                return ImageQuality.LOW;
            }
            default: {
                return ImageQuality.MEDIUM;
            }
        }
    }

    public static String getSettingUsername(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(JandanSettingActivity.SETTING_USERNAME, "");
    }

    public static void setSettingUsername(Context context, String username) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SETTING_USERNAME, username);
        editor.apply();
    }

    public static String getSettingUseremail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(JandanSettingActivity.SETTING_USEREMAIL, "");
    }

    public static void setSettingUseremail(Context context, String username) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SETTING_USEREMAIL, username);
        editor.apply();
    }

    private boolean previousFilterXXgtOO;
    private int previousFilterXXgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jandan_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.activity_setting));

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.activity_main_content, new JandanSettingFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        previousFilterXXgt = getSettingFilterXXgt(getApplicationContext());
        previousFilterXXgtOO = getSettingFilterXXgtOO(getApplicationContext());
    }

    private void setFilterResult() {
        boolean isChanged = false;
        if (previousFilterXXgt != getSettingFilterXXgt(getApplicationContext())) {
            isChanged = true;
        }
        if (previousFilterXXgtOO != getSettingFilterXXgtOO(getApplicationContext())) {
            isChanged = true;
        }
        Intent returnIntent = new Intent();
        setResult(isChanged ? RESULT_FILTER_CHANGED : RESULT_FILTER_NOT_CHANGED, returnIntent);
    }

    private void setImageQuality() {
        AppAdapters.IMAGE_QUALITY = getSettingImageQuality(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setFilterResult();
            setImageQuality();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setFilterResult();
        setImageQuality();
        super.onBackPressed();
    }

    public static class JandanSettingFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            initSummary(getPreferenceScreen());
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
            findPreference("aboutAuthor").setOnPreferenceClickListener(preference1 -> {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.preference_about_author))
                        .setMessage("Android Developer\nEmail:xudshen@hotmail.com")
                        .setNegativeButton("Weibo", (dialog, which) -> {
                            HtmlHelper.openInBrowser(getActivity(), "http://weibo.com/2395897932");
                        })
                        .setNeutralButton("Email", (dialog, which) -> {
                            ClipboardHelper.copy(getActivity(), "xudshen@hotmail.com");
                            Toast.makeText(getActivity(), "Copied", Toast.LENGTH_LONG).show();
                        })
                        .setPositiveButton("Github", (dialog, which) -> {
                            HtmlHelper.openInBrowser(getActivity(), "https://github.com/xudshen");
                        }).show();
                return true;
            });
            findPreference("clearCache").setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", getActivity().getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updatePrefSummary(findPreference(key));
        }

        private void initSummary(Preference p) {
            if (p instanceof PreferenceGroup) {
                PreferenceGroup pGrp = (PreferenceGroup) p;
                for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                    initSummary(pGrp.getPreference(i));
                }
            } else {
                updatePrefSummary(p);
            }
        }

        private void updatePrefSummary(Preference p) {
            if (p instanceof ListPreference) {
                ListPreference listPref = (ListPreference) p;
                p.setSummary(listPref.getEntry());
            }
            if (p instanceof EditTextPreference) {
                EditTextPreference editTextPref = (EditTextPreference) p;
                p.setSummary(editTextPref.getText());
            }
            if (p instanceof MultiSelectListPreference) {
                EditTextPreference editTextPref = (EditTextPreference) p;
                p.setSummary(editTextPref.getText());
            }
        }
    }
}
