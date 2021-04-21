package belev.org.warface_app;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SettingsFragment extends Fragment {

    private View view;
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
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

        SettingsViewModel mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        TextView textView = view.findViewById(R.id.text_about);
        textView.setText(mViewModel.getText().getValue());
    }

    public void initAction() {
        final Button button = view.findViewById(R.id.button_submit);

        SettingsViewModel mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        String name = mViewModel.getPreferencesName();

        if (name.isEmpty()) {
            button.setOnClickListener(listenerAddUser());
        } else {
            button.setOnClickListener(listenerDeleteUser());
        }

        // Hide one button on en region
        Locale locale = MyApplicationContext.getAppContext().getResources().getConfiguration().getLocales().get(0);
        if (!locale.toString().equals("ru_RU")) {
            RadioButton radioButtonBravo = view.findViewById(R.id.button_server_bravo);
            radioButtonBravo.setVisibility(View.GONE);
            RadioButton radioButtonCharli = view.findViewById(R.id.button_server_charli);
            radioButtonCharli.setTag(2);
        }
    }

    public void hideSuccessMessage() {
        final TextView textError = view.findViewById(R.id.text_error);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textError.setText("");
            }
        }, TimeUnit.SECONDS.toMillis(5));
    }

    public View.OnClickListener listenerAddUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = view.findViewById(R.id.edit_text);
                final TextView textError = view.findViewById(R.id.text_error);
                final Button button = view.findViewById(R.id.button_submit);
                final String name = editText.getText().toString();
                final RadioGroup radioGroup = view.findViewById(R.id.radio_group_server);

                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(radioButtonId);
                String server = (String) radioButton.getTag();

                if (name.isEmpty()) {
                    textError.setText("Ошибка: поле пусто!");
                    textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_red));
                    hideSuccessMessage();
                } else {
                    final TaskRunner.TaskRunnerCallback<Integer> task = new TaskRunner.TaskRunnerCallback<Integer>() {
                        @Override
                        public void execute(Integer serverResponseStatus) {
                            if (serverResponseStatus == 200) {
                                textError.setText("Пользователь успешно добавлен!");
                                textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_green));
                                editText.setText(name);
                                editText.setEnabled(false);
                                button.setText("Удалить");
                                SettingsViewModel mViewModel = new ViewModelProvider(SettingsFragment.this).get(SettingsViewModel.class);
                                mViewModel.setPreferencesName(name);
                                button.setOnClickListener(listenerDeleteUser());

                                // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                transaction.replace(R.id.containerView, new StatisticsFragment()).commit();
                            } else if (serverResponseStatus == 400){
                                textError.setText("Ошибка: пользователь не найден!");
                                textError.setTextColor(MyApplicationContext.getAppContext().getResources().getColor(R.color.error_red));
                                hideSuccessMessage();
                            }
                        }
                    };

                    TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
                    Callable statisticsParser = new StatisticsParser(name, server);
                    taskRunner.executeAsync(statisticsParser, task);
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
                hideSuccessMessage();

                SettingsViewModel mViewModel = new ViewModelProvider(SettingsFragment.this).get(SettingsViewModel.class);
                mViewModel.setPreferencesName("");

                editText.setText("");
                editText.setEnabled(true);
                button.setText("Добавить");

                button.setOnClickListener(listenerAddUser());
            }
        };
    }

    public void initUser() {
        SettingsViewModel mViewModel = new ViewModelProvider(SettingsFragment.this).get(SettingsViewModel.class);
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