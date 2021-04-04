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

    public StatisticsUser() {

    }

    public String getUserid() {
        return user_id;
    }

    public void setUserid(String user_id) {
        this.user_id = user_id;
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

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getFriendlykills() {
        return friendly_kills;
    }

    public void setFriendlykills(int friendly_kills) {
        this.friendly_kills = friendly_kills;
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
}
