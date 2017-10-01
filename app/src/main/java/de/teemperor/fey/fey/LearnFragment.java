package de.teemperor.fey.fey;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


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

    private TextView question;
    private Button[] answerOptions;

    private int answerIndex = 0;
    private int textColorOriginal = 0;
    private boolean displayingAnswer = false;
    private int rightColor = Color.parseColor("#27d500");
    private int wrongColor = Color.parseColor("#d50003");

    public LearnFragment() {
        // Required empty public constructor
        answerOptions = new Button[6];
    }

    private void setSymbol(Symbol s) {
        displayingAnswer = false;
        question.setText(s.getSymbol().get(0));
        answerIndex = new Random().nextInt(answerOptions.length);
        answerOptions[answerIndex].setText(s.getMeanings().get(0));
        question.setTextColor(textColorOriginal);

        for (Button b : answerOptions) {
            b.setEnabled(true);
            b.setTextColor(textColorOriginal);
        }
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

    public void pressedAnswer(int index) {
        boolean correct = index == answerIndex;;
        displayingAnswer = true;

        answerOptions[answerIndex].setTextColor(rightColor);
        question.setTextColor(correct ? rightColor : wrongColor);

        if (index == answerIndex) {
            for (Button b : answerOptions) {
                b.setEnabled(false);
            }
        } else {
            answerOptions[index].setTextColor(wrongColor);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setSymbol(SymbolDict.singleton.getRandom());
            }
        }, correct ? 500 : 2500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        question = (TextView) view.findViewById(R.id.questionText);
        answerOptions[0] = (Button) view.findViewById(R.id.button1);
        answerOptions[1] = (Button) view.findViewById(R.id.button2);
        answerOptions[2] = (Button) view.findViewById(R.id.button3);
        answerOptions[3] = (Button) view.findViewById(R.id.button4);
        answerOptions[4] = (Button) view.findViewById(R.id.button5);
        answerOptions[5] = (Button) view.findViewById(R.id.button6);

        textColorOriginal = answerOptions[0].getCurrentTextColor();

        int j = 0;
        for (Button b : answerOptions) {
            b.setMaxWidth(b.getWidth());
            b.setMaxHeight(b.getHeight());
            final int i = j;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!displayingAnswer)
                        pressedAnswer(i);
                }
            });
            j++;
        }

        setSymbol(SymbolDict.singleton.getRandom());

        return view;
    }

}
