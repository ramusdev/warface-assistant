package belev.org.warface_app;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsDetailsFragment extends Fragment {

    private StatisticsDetailsViewModel mViewModel;

    public static StatisticsDetailsFragment newInstance() {
        return new StatisticsDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StatisticsDetailsViewModel.class);

        // StatisticsUserParser statisticsUserParser = new StatisticsUserParser();
        // statisticsUserParser.pars();
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new StatisticsParser());
    }

}