package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;
import org.unipr.pills.R;

public class DaysNumberFragment extends DialogFragment {

    public interface OnInputListener{
        void sendInput(String input);
    }
    public OnInputListener mOnInputListener;

    private EditText inputNumber;
    private ImageButton minus;
    private ImageButton plus;
    private TextView cancel;
    private TextView set;
    Integer value;
    String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.number_of_days, container, false);
        inputNumber = view.findViewById(R.id.inputNumberOfDays);
        minus = view.findViewById(R.id.minusDay);
        plus = view.findViewById(R.id.plusDay);
        cancel = view.findViewById(R.id.cancelDay);
        set = view.findViewById(R.id.set);

        inputNumber.setEnabled(false);

        text = inputNumber.getText().toString();
        value = Integer.parseInt(text);


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value > 1){
                    value -= 1;
                    inputNumber.setText(value.toString());
                }

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    value += 1;
                    inputNumber.setText(value.toString());

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputNumber.getText().toString();
//                mOnInputListener.sendInput(input);
                inputNumber.setText(input);
                getDialog().dismiss();

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
        }
    }


}
