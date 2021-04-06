package belev.org.warface_app;

public class StatisticsUser {
    private String user_id;
    private String nickname;
    private int experience;
    private int rank_id;
    private int clan_id;
    private String clan_name;
    private int kill;
    private int friendly_kills;
    private int kills;
    private int death;
    private double pvp;
    private int pve_kill;
    private int pve_friendly_kills;
    private int pve_kills;
    private int pve_death;
    private double pve;
    private int playtime;
    private int playtime_h;
    private int playtime_m;
    private String favoritPVP;
    private String favoritPVE;
    private int pve_wins;
    private int pvp_wins;
    private int pvp_lost;
    private int pve_lost;
    private int pve_all;
    private int pvp_all;
    private double pvpwl;

    public StatisticsUser() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public String getUserid() {
        return user_id;
    }

    public void setUserid(String user_id) {
        this.user_id = user_id;
    }

    public int getRankid() {
        return rank_id;
    }

    public void setRankid(int rank_id) {
        this.rank_id = rank_id;
    }

    public int getClanid() {
        return clan_id;
    }

    public void setClanid(int clan_id) {
        this.clan_id = clan_id;
    }

    public String getClanname() {
        return clan_name;
    }

    public void setClanname(String clan_name) {
        this.clan_name = clan_name;
    }

    public int getFriendlykills() {
        return friendly_kills;
    }

    public void setFriendlykills(int friendly_kills) {
        this.friendly_kills = friendly_kills;
    }

    public int getPvekill() {
        return pve_kill;
    }

    public void setPvekill(int pve_kill) {
        this.pve_kill = pve_kill;
    }

    public int getPvefriendlykills() {
        return pve_friendly_kills;
    }

    public void setPvefriendlykills(int pve_friendly_kills) {
        this.pve_friendly_kills = pve_friendly_kills;
    }

    public int getPvekills() {
        return pve_kills;
    }

    public void setPvekills(int pve_kills) {
        this.pve_kills = pve_kills;
    }

    public int getPvedeath() {
        return pve_death;
    }

    public void setPvedeath(int pve_death) {
        this.pve_death = pve_death;
    }

    public double getPve() {
        return pve;
    }

    public void setPve(double pve) {
        this.pve = pve;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public int getPlaytimeh() {
        return playtime_h;
    }

    public void setPlaytimeh(int playtime_h) {
        this.playtime_h = playtime_h;
    }

    public int getPlaytimem() {
        return playtime_m;
    }

    public void setPlaytimem(int playtime_m) {
        this.playtime_m = playtime_m;
    }

    public String getFavoritPVP() {
        return favoritPVP;
    }

    public void setFavoritPVP(String favoritPVP) {
        this.favoritPVP = favoritPVP;
    }

    public String getFavoritPVE() {
        return favoritPVE;
    }

    public void setFavoritPVE(String favoritPVE) {
        this.favoritPVE = favoritPVE;
    }

    public int getPvewins() {
        return pve_wins;
    }

    public void setPvewins(int pve_wins) {
        this.pve_wins = pve_wins;
    }

    public int getPvpwins() {
        return pvp_wins;
    }

    public void setPvpwins(int pvp_wins) {
        this.pvp_wins = pvp_wins;
    }

    public int getPvplost() {
        return pvp_lost;
    }

    public void setPvplost(int pvp_lost) {
        this.pvp_lost = pvp_lost;
    }

    public int getPvelost() {
        return pve_lost;
    }

    public void setPvelost(int pve_lost) {
        this.pve_lost = pve_lost;
    }

    public int getPveall() {
        return pve_all;
    }

    public void setPveall(int pve_all) {
        this.pve_all = pve_all;
    }

    public int getPvpall() {
        return pvp_all;
    }

    public void setPvpall(int pvp_all) {
        this.pvp_all = pvp_all;
    }

    public double getPvpwl() {
        return pvpwl;
    }

    public void setPvpwl(double pvpwl) {
        this.pvpwl = pvpwl;
    }

}
