package fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import org.unipr.pills.R;

public class QuantityFragment extends DialogFragment {

    private EditText inputQuantity;
    private ImageButton minus;
    private ImageButton plus;
    Double value;
    String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_quantity, container, false);
        inputQuantity = view.findViewById(R.id.inputDialogQuantity);
        minus = view.findViewById(R.id.minus);
        plus = view.findViewById(R.id.plus);

        text = inputQuantity.getText().toString();
        value = Double.parseDouble(text);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value > 0.25){
                    value -= 0.25;
                    inputQuantity.setText(value.toString());
                }

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    value += 0.25;
                    inputQuantity.setText(value.toString());

            }
        });

        return view;
    }


}
