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

    static Teacher teacher = new Teacher();

    int currentView = -1;

    public boolean switchView(int id) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (id) {
            case R.id.navigation_dict:
                if (currentView == R.id.navigation_dict)
                    return true;
                currentView = R.id.navigation_dict;
                //fragment = InfoFragment.newInstance(teacher.getSymbols().get(0).getSymbol(), false);
                fragment = DictSymbolFragment.newInstance(1);
                break;
            case R.id.navigation_learn:
                if (currentView == R.id.navigation_learn)
                    return true;
                currentView = R.id.navigation_learn;
                fragment = LearnFragment.newInstance("a", "b");
                break;
            case R.id.navigation_settings:
                if (currentView == R.id.navigation_settings)
                    return true;
                currentView = R.id.navigation_settings;
                fragment = SettingsFragment.newInstance("a", "b");
                break;
        }
        fragmentTransaction.add(R.id.content, fragment);
        mContent.removeAllViews();
        fragmentTransaction.commit();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return switchView(item.getItemId());
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
            teacher.setDict(SymbolDict.singleton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mContent = (FrameLayout) findViewById(R.id.content);

        navigation.setSelectedItemId(R.id.navigation_learn);

        switchView(R.id.navigation_learn);
    }

}
