package belev.org.warface_app;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DonateFragment extends Fragment implements View.OnClickListener, PurchasesUpdatedListener {

    public BillingClient billingClient;
    public MainActivity mainActivity;
    public SkuDetails skuDetailsLvlOne;
    public SkuDetails skuDetailsLvlTwo;
    public SkuDetails skuDetailsLvlThree;
    public SkuDetails skuDetailsLvlFour;
    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_donate, container, false);
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

        if (mainActivity.isNetworkAvailable()) {
            initBillingClient();
        }

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

    public void handlePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
                        @Override
                        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

                        }
                    });
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int responseCode;

        if (! mainActivity.isNetworkAvailable()) {
            Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
            FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
            toolbar.setTitle(getResources().getString(R.string.error_connection));
            return;
        }

        BillingFlowParams billingFlowParams = null;
        BillingFlowParams.Builder billingFlowParamsBuilder = BillingFlowParams.newBuilder();

        switch (view.getId()) {
            case R.id.button_donate_lvl_one:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlOne).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_two:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlTwo).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_three:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlThree).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            case R.id.button_donate_lvl_four:
                try {
                    billingFlowParams = billingFlowParamsBuilder.setSkuDetails(skuDetailsLvlFour).build();
                } catch (IllegalArgumentException e) {
                    // Log.e("CustomLogTag", "SKU not loaded");
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        try {
            responseCode = billingClient.launchBillingFlow(mainActivity, billingFlowParams).getResponseCode();
        } catch (NullPointerException e) {
            // Log.e("CustomLogTag", "SKU null pointer");
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            showMessageSuccess();
            handlePurchases(purchases);
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            showMessageCancel();
        } else {
            showMessageError();
        }
    }

    public void showMessageSuccess() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_message_userdeleted);
                Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.orange));
                snackbar.show();
            }
        }, 1000);
    }

    public void showMessageCancel() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_message_userdeleted);
                Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.orange));
                snackbar.show();
            }
        }, 1000);
    }

    public void showMessageError() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_message_userdeleted);
                Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.orange));
                snackbar.show();
            }
        }, 1000);
    }
}
