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

        // final TextView userid = view.findViewById(R.id.data_userid);
        final TextView experience = view.findViewById(R.id.data_experience);
        final TextView nickname = view.findViewById(R.id.data_nickname);
        final TextView clanname = view.findViewById(R.id.data_clanname);
        final TextView rank = view.findViewById(R.id.data_rank);
        final TextView kill = view.findViewById(R.id.data_kill);
        final TextView pvp = view.findViewById(R.id.data_pvp);

        StatisticsUser user = mViewModel.loadUser();
        // userid.setText(user.getUserid());
        experience.setText(Integer.toString(user.getExperience()));
        nickname.setText(user.getNickname());
        clanname.setText(user.getClanname());
        rank.setText(Integer.toString(user.getRankid()));
        kill.setText(Integer.toString(user.getKill()));
        pvp.setText(Double.toString(user.getPvp()));


        // TaskRunner taskRunner = new TaskRunner();
        // taskRunner.executeAsync(new StatisticsParser());
    }

}