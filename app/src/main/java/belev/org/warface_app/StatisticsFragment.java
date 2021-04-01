package belev.org.warface_app;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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

    // private StatisticsViewModel mViewModel;
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

        initAction();
        initUser();

        StatisticsViewModel mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        TextView textView = view.findViewById(R.id.text_about);
        textView.setText(mViewModel.getText().getValue());
    }

    public void initAction() {
        final EditText editText = view.findViewById(R.id.edit_text);
        final TextView textError = view.findViewById(R.id.text_error);
        final Button button = view.findViewById(R.id.button_submit);

        StatisticsViewModel mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        String name = mViewModel.getPreferencesName();

        if (name.isEmpty()) {
            button.setOnClickListener(listenerAddUser());
        } else {
            button.setOnClickListener(listenerDeleteUser());
        }

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

    public View.OnClickListener listenerAddUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.edit_text);
                TextView textError = view.findViewById(R.id.text_error);
                Button button = view.findViewById(R.id.button_submit);
                String name = editText.getText().toString();

                if (name.isEmpty()) {
                    textError.setText("Ошибка: поле пусто!");
                    textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_red));
                } else {
                    textError.setText("Пользователь успешно добавлен!");
                    textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_green));

                    editText.setText(name);
                    editText.setEnabled(false);
                    button.setText("Удалить");

                    StatisticsViewModel mViewModel = new ViewModelProvider(StatisticsFragment.this).get(StatisticsViewModel.class);
                    mViewModel.setPreferencesName(name);

                    button.setOnClickListener(listenerDeleteUser());
                }
            }
        };
    }

    public View.OnClickListener listenerDeleteUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = view.findViewById(R.id.button_submit);
                EditText editText = view.findViewById(R.id.edit_text);
                TextView textError = view.findViewById(R.id.text_error);

                textError.setText("Пользователь успешно удален!");
                textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_green));

                StatisticsViewModel mViewModel = new ViewModelProvider(StatisticsFragment.this).get(StatisticsViewModel.class);
                mViewModel.setPreferencesName("");

                editText.setText("");
                editText.setEnabled(true);
                button.setText("Добавить");

                button.setOnClickListener(listenerAddUser());
            }
        };
    }

    public void initUser() {
        StatisticsViewModel mViewModel = new ViewModelProvider(StatisticsFragment.this).get(StatisticsViewModel.class);
        String user = mViewModel.getPreferencesName();
        Button button = view.findViewById(R.id.button_submit);
        EditText editText = view.findViewById(R.id.edit_text);

        if (!user.isEmpty()) {
            editText.setText(user);
            editText.setEnabled(false);
            button.setText("Удалить");
        }
    }
}