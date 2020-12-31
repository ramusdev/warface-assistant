package belev.org.warface_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import androidx.annotation.RequiresApi;

public class NotificationShower {

    private Context context;

    public NotificationShower(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show() {

        String channelId = "channel_id_100";
        String channelDescription = "channel main";
        String name = "news_channel_name";
        String text = "Чем заняться в ближайшее время? В игре вас ожидает множество активностей! Читайте подробности, чтобы узнать о всех нововведениях.";
        int notifyId = 100;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        SpannableString spannableString = new SpannableString("Календарь новогодних событий в Warface");
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_warface_icon)
                // .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setChannelId(channelId)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                // .setStyle(new Notification
                // .BigTextStyle()
                // .bigText(text))
                .setContentTitle(spannableString)
                .setContentText(text)
                .build();

        notificationManager.notify(notifyId, notification);
    }
}
