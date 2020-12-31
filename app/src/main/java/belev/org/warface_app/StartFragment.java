package belev.org.warface_app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import belev.org.warface_app.data.DataDbHelper;

public class StartFragment extends Fragment implements View.OnClickListener {

    public BillingClient billingClient;
    public MainActivity mainActivity;
    public SkuDetails skuDetailsLvlOne;
    public SkuDetails skuDetailsLvlTwo;
    public SkuDetails skuDetailsLvlThree;
    public SkuDetails skuDetailsLvlFour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);
        TextView textView = (TextView) view.findViewById(R.id.start_textView);
        String text = getResources().getString(R.string.start_text);
        textView.setText(Html.fromHtml(text));

        mainActivity = (MainActivity) getActivity();

        Button buttonDonateLvlOne = (Button) view.findViewById(R.id.button_donate_lvl_one);
        buttonDonateLvlOne.setOnClickListener(this);

        Button buttonDonateLvlTwo = (Button) view.findViewById(R.id.button_donate_lvl_two);
        buttonDonateLvlTwo.setOnClickListener(this);

        Button buttonDonateLvlThree = (Button) view.findViewById(R.id.button_donate_lvl_three);
        buttonDonateLvlThree.setOnClickListener(this);

        Button buttonDonateLvlFour = (Button) view.findViewById(R.id.button_donate_lvl_four);
        buttonDonateLvlFour.setOnClickListener(this);

        initBillingClient();

        return view;
    }

    public void initBillingClient() {

        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && list != null) {
                    for (Purchase purchase : list) {
                        handlePurchase(purchase);
                    }
                }
            }
        };

        billingClient = BillingClient.newBuilder(mainActivity)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    System.out.println("Ok connect billing client -------------->");

                    List<String> skuList = new ArrayList<String>();
                    skuList.add("donate_lvl_1");
                    skuList.add("donate_lvl_2");
                    skuList.add("donate_lvl_3");
                    skuList.add("donate_lvl_4");

                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);

                    billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                            skuDetailsLvlOne = list.get(0);
                            skuDetailsLvlTwo = list.get(1);
                            skuDetailsLvlThree = list.get(2);
                            skuDetailsLvlFour = list.get(3);
                        }
                    });
                } else {
                    System.out.println("Error connect billing client -------------->");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                System.out.println("Disconnected billing client -------------->");
            }
        });
    }

    public void handlePurchase(Purchase purchase) {
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        ConsumeResponseListener consumeResponseListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    System.out.println("The consume response ------->");
                }
            }
        };

        billingClient.consumeAsync(consumeParams, consumeResponseListener);
    }

    /*
    public View.OnClickListener buttonClickHandler(View v) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button click ------------------------------------>");

                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build();

                int responseCode = billingClient.launchBillingFlow(mainActivity, billingFlowParams).getResponseCode();
            }
        };

        return onClickListener;
    }
    */

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int responseCode;

        BillingFlowParams billingFlowParams;
        BillingFlowParams.Builder billingFlowParamsBuilder = BillingFlowParams.newBuilder();

        switch (view.getId()) {
            case R.id.button_donate_lvl_one:
                // showNotification();
                // startSheduller();
                // executeWork(mainActivity.getApplication());
                testLog();
                // billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlOne).build();
                break;
            case R.id.button_donate_lvl_two:
                billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlTwo).build();
                break;
            case R.id.button_donate_lvl_three:
                billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlThree).build();
                break;
            case R.id.button_donate_lvl_four:
                billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlFour).build();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        // responseCode = billingClient.launchBillingFlow(mainActivity, billingFlowParams).getResponseCode();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification() {

        String channelId = "channel_id_101";
        String channelDescription = "Chanell main";
        String name = "channel_name";
        String text = "Чем заняться в ближайшее время? В игре вас ожидает множество активностей! Читайте подробности, чтобы узнать о всех нововведениях.";
        int notifyId = 100;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mainActivity, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);

        NotificationManager notificationManager = (NotificationManager) mainActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        SpannableString spannableString = new SpannableString("Календарь новогодних событий в Warface");
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Notification notification = new Notification.Builder(mainActivity)
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


    public void testLog() {

        WorkManager workManager = WorkManager.getInstance(mainActivity);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker");

        try {
            List<WorkInfo> workInfoList = statuses.get();

            if (workInfoList.size() <= 0) {
                Log.e("CustomLogTag", "Empty work manager");
                PeriodicWorkCreator periodicWorkCreator = new PeriodicWorkCreator((Application) mainActivity.getApplicationContext());
                periodicWorkCreator.create();
            }
        } catch(ExecutionException | InterruptedException e) {
            Log.e("CustomLogTag", e.getMessage());
        }
    }
}
