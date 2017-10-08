package de.teemperor.fey.fey;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Symbol symbolToDisplay;

    private TextView symbol, meanings, readings;
    private boolean showNext;

    public InfoFragment() {
        // Required empty public constructor
    }

    private String joinString(String s, List<String> items) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            result.append(items.get(i));
            if (i < items.size() - 1) {
               result.append(s);
            }
        }
        return result.toString();
    }
    private void setSymbol(Symbol r) {
        String symbolText = joinString(", ", r.getSymbols());
        symbol.setText(symbolText);
        meanings.setText("• " + joinString("\n• ", r.getMeanings()));
        readings.setText("• " + joinString("\n• ", r.getReadings()));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(Symbol s, boolean showNext) {
        InfoFragment fragment = new InfoFragment();
        fragment.symbolToDisplay = s;
        fragment.showNext = showNext;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        args.putString(ARG_PARAM2, "y");
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
        View view =  inflater.inflate(R.layout.fragment_info, container, false);

        symbol = (TextView) view.findViewById(R.id.symbol);
        meanings = (TextView) view.findViewById(R.id.meaningsText);
        readings = (TextView) view.findViewById(R.id.readingsText);
        Button nextButton = (Button) view.findViewById(R.id.nextButton);
        if (showNext) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LearnFragment.singleton.next();
                }
            });
        } else {
            nextButton.setVisibility(View.INVISIBLE);
        }

        setSymbol(symbolToDisplay);

        return view;
    }


}
