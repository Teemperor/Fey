package de.teemperor.fey.fey;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.test.SyncBaseInstrumentation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout mContent;

    public static LearnFragment singleton;
    private Fragment fragment;

    public LearnFragment() {
        // Required empty public constructor
        singleton = this;
    }

    public void next() {
        mContent.removeAllViews();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null)
            fragmentTransaction.remove(fragment);

        LearnTask l = MainActivity.teacher.nextTask();
        if (l.getSymbolToLearn() != null) {
            fragment = InfoFragment.newInstance(l.getSymbolToLearn(), true);
        } else {
            fragment = QuizFragment.newInstance(l.getQuestion());
        }
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    // TODO: Rename and change types and number of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        mContent = (LinearLayout) view.findViewById(R.id.learn_container);

        next();

        return view;
    }

}
