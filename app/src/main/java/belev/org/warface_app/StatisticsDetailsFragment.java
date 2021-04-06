package belev.org.warface_app;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatisticsDetailsFragment extends Fragment {

    private StatisticsDetailsViewModel mViewModel;
    private View view;

    public static StatisticsDetailsFragment newInstance() {
        return new StatisticsDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_details, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StatisticsDetailsViewModel.class);

        final TextView experience = view.findViewById(R.id.data_experience);
        final TextView nickname = view.findViewById(R.id.data_nickname);
        final TextView clanname = view.findViewById(R.id.data_clanname);
        final TextView rank = view.findViewById(R.id.data_rank);
        final TextView kill = view.findViewById(R.id.data_kill);
        final TextView pvp = view.findViewById(R.id.data_pvp);
        final TextView friendlykills = view.findViewById(R.id.data_friendlykills);
        final TextView death = view.findViewById(R.id.data_death);

        StatisticsUser user = mViewModel.loadUser();
        experience.setText(String.valueOf(user.getExperience()));
        nickname.setText(user.getNickname());
        clanname.setText(user.getClanname());
        rank.setText(String.valueOf(user.getRankid()));
        kill.setText(String.valueOf(user.getKill()));
        pvp.setText(String.valueOf(user.getPvp()));
        friendlykills.setText(String.valueOf(user.getFriendlykills()));
        death.setText(String.valueOf(user.getDeath()));



        // Log.d("MyTag", user.getPvp());
        // TaskRunner taskRunner = new TaskRunner();
        // taskRunner.executeAsync(new StatisticsParser());
    }

}