package belev.org.warface_app;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel mViewModel;
    private View view;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistics_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submitAction();
        mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        final TextView textView = view.findViewById(R.id.text_about);
        textView.setText(mViewModel.getText().getValue());
    }

    public void submitAction() {
        final EditText editText = view.findViewById(R.id.edit_text);
        final TextView textError = view.findViewById(R.id.text_error);
        final Button button = view.findViewById(R.id.button_submit);

        // final Animation animationFadeIn = AnimationUtils.loadAnimation(MyApplicationContext.getAppContext(), android.R.anim.fade_in);
        // final Animation animationFadeOut = AnimationUtils.loadAnimation(MyApplicationContext.getAppContext(), android.R.anim.fade_out);
        // final AnimationSet animationSet = new AnimationSet(false);
        // animationSet.addAnimation(animationFadeIn);
        // animationSet.addAnimation(animationFadeOut);
        // textError.setAnimation(animationSet);
        // textError.startAnimation(AnimationUtils.loadAnimation(MyApplicationContext.getAppContext(), android.R.anim.fade_in));
        // textError.setAnimation(AnimationUtils.loadAnimation(MyApplicationContext.getAppContext(), R.anim.fade_in));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if (text.isEmpty()) {
                    textError.setText("Ошибка: поле пусто!");
                } else {
                    textError.setText("Пользователь успешно добавлен!");
                    textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_green));
                }

                // Log.d("MyTag", editText.getText().toString());
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = textError.getText().toString();

                if (event.getAction() == KeyEvent.ACTION_DOWN && !text.isEmpty()) {
                    textError.setText("");
                }
                return false;
            }
        });
    }



}