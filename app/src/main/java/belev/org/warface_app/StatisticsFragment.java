package belev.org.warface_app;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel mViewModel;
    private View view;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
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
        mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);

        final TextView experience = view.findViewById(R.id.data_experience);
        final TextView nickname = view.findViewById(R.id.data_nickname);
        final TextView clanname = view.findViewById(R.id.data_clanname);
        final TextView rank = view.findViewById(R.id.data_rank);
        final TextView kill = view.findViewById(R.id.data_kill);
        final TextView friendlykills = view.findViewById(R.id.data_friendlykills);
        final TextView death = view.findViewById(R.id.data_death);
        final TextView playtimeh = view.findViewById(R.id.data_playtimeh);
        final TextView playtimem = view.findViewById(R.id.data_playtimem);
        final TextView pvp = view.findViewById(R.id.data_pvp);
        final TextView pvpall = view.findViewById(R.id.data_pvpall);
        final TextView pvpwins = view.findViewById(R.id.data_pvpwins);
        final TextView pvplost = view.findViewById(R.id.data_pvplost);
        final TextView pvpwl = view.findViewById(R.id.data_pvpwl);
        final TextView pvpfavorite = view.findViewById(R.id.data_pvpfavorite);
        final TextView pve = view.findViewById(R.id.data_pve);
        final TextView pveall = view.findViewById(R.id.data_pveall);
        final TextView pvewins = view.findViewById(R.id.data_pvewins);
        final TextView pvelost = view.findViewById(R.id.data_pvelost);
        final TextView pvekill = view.findViewById(R.id.data_pvekill);
        final TextView pvefriendlykills = view.findViewById(R.id.data_pvefriendlykills);
        final TextView pvedeath = view.findViewById(R.id.data_pvedeath);
        final TextView pvefavorite = view.findViewById(R.id.data_pvefavorite);

        StatisticsUser user = mViewModel.loadUser();
        experience.setText(String.valueOf(user.getExperience()));
        nickname.setText(user.getNickname());
        clanname.setText(user.getClanname());
        rank.setText(String.valueOf(user.getRankid()));
        kill.setText(String.valueOf(user.getKill()));
        pvp.setText(String.valueOf(user.getPvp()));
        friendlykills.setText(String.valueOf(user.getFriendlykills()));
        death.setText(String.valueOf(user.getDeath()));
        playtimeh.setText(String.valueOf(user.getPlaytimeh()));
        playtimem.setText(String.valueOf(user.getPlaytimem()));
        pvpall.setText(String.valueOf(user.getPvpall()));
        pvpwins.setText(String.valueOf(user.getPvpwins()));
        pvplost.setText(String.valueOf(user.getPvplost()));
        pvpwl.setText(String.valueOf(user.getPvpwl()));
        pvpfavorite.setText(user.getFavoritPVP());
        pve.setText(String.valueOf(user.getPve()));
        pveall.setText(String.valueOf(user.getPveall()));
        pvewins.setText(String.valueOf(user.getPvewins()));
        pvelost.setText(String.valueOf(user.getPvelost()));
        pvekill.setText(String.valueOf(user.getPvekill()));
        pvefriendlykills.setText(String.valueOf(user.getPvefriendlykills()));
        pvedeath.setText(String.valueOf(user.getPvedeath()));
        pvefavorite.setText(user.getFavoritPVE());





        // Log.d("MyTag", user.getPvp());
        // TaskRunner taskRunner = new TaskRunner();
        // taskRunner.executeAsync(new StatisticsParser());
    }

}