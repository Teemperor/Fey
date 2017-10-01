package de.teemperor.fey.fey;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mContent.removeAllViews();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_dict:
                    fragment = InfoFragment.newInstance("a", "b");
                    break;
                case R.id.navigation_learn:
                    fragment = LearnFragment.newInstance("a", "b");
                    break;
                case R.id.navigation_settings:
                    fragment = SettingsFragment.newInstance("a", "b");
                    break;
            }
            fragmentTransaction.add(R.id.content, fragment);
            fragmentTransaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream input = getAssets().open("joyo.csv");
            SymbolDict.singleton = new SymbolDict("joyo");
            SymbolDict.singleton.parseCSV(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mContent = (FrameLayout) findViewById(R.id.content);
    }

}
