package com.leo.events;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leo.events.accessibility.AccessibilityOperator;
import com.leo.events.accessibility.OpenAccessibilitySettingHelper;
import com.leo.events.accessibility.event.AccessibilityEvent;
import com.leo.events.accessibility.event.AccessibilityEventManaer;
import com.leo.events.accessibility.event.AccessibilityShareImageEvent;
import com.leo.events.accessibility.event.AccessibilityShareTextEvent;
import com.leo.events.accessibility.event.alipay.AccessibilityAlipayPayEvent;
import com.leo.events.accessibility.event.alipay.AliPayEvent;
import com.leo.events.appwidget.AppWidgetItemProvider;
import com.leo.events.model.Goods;
import com.leo.events.model.GoodsList;
import com.leo.events.net.AppEngine;
import com.leo.events.shareutil.ShareUtil;
import com.leo.events.shareutil.share.ImageDecoder;
import com.leo.events.shareutil.share.ShareListener;
import com.leo.events.shareutil.share.SharePlatform;
import com.leo.events.util.DimenUtils;
import com.leo.events.view.AppProgressCircleView;
import com.leo.events.view.AppProgressModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    ClipboardManager clip;

    private static final String IMAGE_BASE = "http://120.78.206.49:8080/jspxcms-9.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        AccessibilityOperator.getInstance().init(this);
        clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String money = "1111";
        final String title = getString(R.string.aaaa, money);
        int start = title.indexOf(money);
        SpannableString spannableString = new SpannableString(title);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, start, start + money.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        ((TextView) findViewById(R.id.tv_app_widget)).setText(spannableString);

        findViewById(R.id.tv_app_widget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppWidgetItemProvider.class);
                intent.setAction("com.example.action.CLICK");
                sendBroadcast(intent);
            }
        });
        findViewById(R.id.shared_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareText(MainActivity.this, SharePlatform.WX_TIMELINE, "你好", new ShareListener() {
                    @Override
                    public void shareSuccess() {
                        AccessibilityEventManaer.get().dispose();
                    }

                    @Override
                    public void shareFailure(Exception e) {
                        AccessibilityEventManaer.get().dispose();
                    }

                    @Override
                    public void shareCancel() {
                        AccessibilityEventManaer.get().dispose();
                    }
                });
                AccessibilityEventManaer.get().addEvent(new AccessibilityShareTextEvent(AccessibilityEvent.WECHAT_SHARE_TEXT));
            }

        });
        findViewById(R.id.shared_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("text", "nihaoaaaaaa");
                clip.setPrimaryClip(clipData);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.broad);

                ShareUtil.shareImage(MainActivity.this, SharePlatform.WX_TIMELINE, bitmap, new ShareListener() {
                    @Override
                    public void shareSuccess() {
                        AccessibilityEventManaer.get().dispose();
                    }

                    @Override
                    public void shareFailure(Exception e) {
                        AccessibilityEventManaer.get().dispose();
                    }

                    @Override
                    public void shareCancel() {
                        AccessibilityEventManaer.get().dispose();
                    }
                });
                AccessibilityEventManaer.get().addEvent(new AccessibilityShareImageEvent(AccessibilityEvent.WECHAT_SHARE_IMAGE));
            }
        });
        findViewById(R.id.shared_android_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.broad);
                File resultFile = null;
                String a = null;
                try {
                    resultFile = ImageDecoder.cacheFile(MainActivity.this);
                    FileOutputStream outputStream = new FileOutputStream(resultFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    a = insertImageToSystem(MainActivity.this, resultFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String finalA = a;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shareweipyq(MainActivity.this, finalA, "nihao");
                    }
                }, 1000);
            }
        });

        findViewById(R.id.shared_android_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.broad);
                File resultFile = null;
                String a = null;
                try {
                    resultFile = ImageDecoder.cacheFile(MainActivity.this);
                    FileOutputStream outputStream = new FileOutputStream(resultFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    a = insertImageToSystem(MainActivity.this, resultFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String finalA = a;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Uri[] uris = {Uri.parse(finalA), Uri.parse(finalA)};
                        shareweipyqSomeImg(MainActivity.this, uris);
                    }
                }, 1000);
            }
        });

        findViewById(R.id.goto_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");// 包名该有activity
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                startActivityForResult(intent, 0);
            }
        });


        findViewById(R.id.permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAccessibilitySettingHelper.jumpToSettingPage(MainActivity.this);
            }
        });

        findViewById(R.id.list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppEngine.getInstance().getAppService().getSimpleInfos()
                        .map(new Func1<JsonObject, GoodsList>() {
                            @Override
                            public GoodsList call(JsonObject jsonObject) {
                                String s = jsonObject.toString();
                                Gson gson = new Gson();
                                return gson.fromJson(s, GoodsList.class);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GoodsList>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("listinfo", e.getMessage());
                            }

                            @Override
                            public void onNext(GoodsList s) {
                                addGoods(s);
                            }
                        });
            }
        });

        findViewById(R.id.alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alipayClick();
//                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(MainActivity.this,TransformService.class);
//                            startForegroundService(intent);
//                        }
//                    }).start();
//                }
            }
        });


        findViewById(R.id.usage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    long end = calendar.getTimeInMillis();
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, calendar.getTimeInMillis(), end);
                    for (UsageStats usageStats : usageStatsList) {
//                        Log.d("mainactivity", usageStats.getPackageName() + (i++));
                        if (usageStats.getTotalTimeInForeground() > 0) {
                            TextView textView = new TextView(MainActivity.this);
                            PackageInfo packageInfo = packageInfos.get(usageStats.getPackageName());
                            if (packageInfo != null &&
                                    !((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) &&
                                    !((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)) {

                                Drawable drawable = packageInfo.applicationInfo.loadIcon(getPackageManager());
//                                Drawable drawable = getResources().getDrawable(resolveInfo.icon);
                                if (drawable != null) {
                                    drawable.setBounds(0, 0, DimenUtils.dp2px(40), DimenUtils.dp2px(40));
                                    textView.setCompoundDrawables(drawable, null, null, null);
                                }


                                CharSequence charSequence = "";
                                charSequence = packageInfo.applicationInfo.loadLabel(getPackageManager());

                                textView.setGravity(Gravity.CENTER_VERTICAL);
                                textView.setText(" " + charSequence + "   使用时间:" + getTime(usageStats.getTotalTimeInForeground()));
                                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(50)));
                                ((LinearLayout) findViewById(R.id.ll_usage)).addView(textView);
                                View view = new View(MainActivity.this);
                                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DimenUtils.dp2px(1)));
                                view.setBackgroundColor(Color.GRAY);
                                ((LinearLayout) findViewById(R.id.ll_usage)).addView(view);
                            }
                        }
                    }
                }
            }


        });

    }

    private String getTime(long time) {
        if (time >= 60 * 60 * 1000) {
            int hours = Math.round(time / (60 * 60 * 1000));
            long gapMin = time - (hours * 60 * 60 * 1000);
            int min = Math.round(gapMin / (60 * 1000));
            long gapSecond = gapMin - (min * 60 * 1000);
            int second = Math.round(gapSecond / 1000);
            return hours + "h " + min + "min " + second + "s";
        } else if (time >= 60 * 1000) {
            int min = Math.round(time / (60 * 1000));
            long gapSecond = time - (min * 60 * 1000);
            int second = Math.round(gapSecond / 1000);
            return min + "min " + second + "s";
        } else if (time >= 1000) {
            int second = Math.round(time / 1000);
            return second + "s";
        } else {
            DecimalFormat df = new DecimalFormat("0.000");
            return df.format((float) time / 1000) + "s";
        }
    }

    private void init() {


        findViewById(R.id.custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppProgressCircleView circleView = findViewById(R.id.custom_view);
                List<AppProgressModel> lists = new ArrayList<>();

                AppProgressModel appProgressModel = new AppProgressModel();
                appProgressModel.time = (10 * 60 * 1000) + (10 * 1000);
                appProgressModel.color = Color.BLUE;
                lists.add(appProgressModel);

                AppProgressModel appProgressModel2 = new AppProgressModel();
                appProgressModel2.time = (10 * 60 * 1000);
                appProgressModel2.color = Color.RED;
                lists.add(appProgressModel2);

                AppProgressModel appProgressModel3 = new AppProgressModel();
                appProgressModel3.time = (2 * 60 * 1000) + (40 * 1000);
                appProgressModel3.color = Color.CYAN;
                lists.add(appProgressModel3);

                AppProgressModel appProgressModel4 = new AppProgressModel();
                appProgressModel4.time = (5 * 60 * 1000) + (40 * 1000);
                appProgressModel4.color = Color.YELLOW;
                lists.add(appProgressModel4);

                circleView.setmModels(lists);
            }
        });

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> resolveInfos = new ArrayList<>();
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            hashMap.put(resolveInfo.activityInfo.packageName, resolveInfo);
        }

        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);
        for (PackageInfo installedPackage : installedPackages) {
            packageInfos.put(installedPackage.packageName, installedPackage);
        }

        FeatureInfo[] featureInfoArray = getPackageManager().getSystemAvailableFeatures();
        for (FeatureInfo featureInfo : featureInfoArray) {
            Log.e("featureinfo", featureInfo.toString());
        }


    }

    HashMap<String, FeatureInfo> featureInfos = new HashMap<>();
    HashMap<String, ResolveInfo> hashMap = new HashMap<>();
    HashMap<String, PackageInfo> packageInfos = new HashMap<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private void addGoods(GoodsList goodsList) {
        if (goodsList == null) {
            return;
        }
        if (goodsList.entity == null) {
            return;
        }
        if (goodsList.entity.entityData == null || goodsList.entity.entityData.isEmpty()) {
            return;
        }
        LinearLayout viewById = findViewById(R.id.container);

        List<Goods> goods = goodsList.entity.entityData;
        for (final Goods good : goods) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(good.title);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ShareWindow.get().dismiss();
                        }
                    }, 5000);
                    ShareWindow.get().show();
                    ClipData clipData = ClipData.newPlainText("text", good.title);
                    clip.setPrimaryClip(clipData);
                    ShareUtil.shareImage(MainActivity.this, SharePlatform.WX_TIMELINE, IMAGE_BASE + good.smallImage, new ShareListener() {
                        @Override
                        public void shareSuccess() {
                            AccessibilityEventManaer.get().dispose();
                            close();
                        }

                        @Override
                        public void shareFailure(Exception e) {
                            AccessibilityEventManaer.get().dispose();
                            close();
                        }

                        @Override
                        public void shareCancel() {
                            AccessibilityEventManaer.get().dispose();
                            close();
                        }
                    });
                    AccessibilityEventManaer.get().addEvent(new AccessibilityShareImageEvent(AccessibilityEvent.WECHAT_SHARE_IMAGE));
                }
            });
            viewById.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
        }


    }


    private void close() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShareWindow.get().dismiss();
            }
        }, 300);
    }


    /**
     * 拉起微信朋友圈发送单张图片
     */
    private void shareweipyq(Context context, String path, String content) {
//        Uri uriToImage = UriUtils.legalLocalUri(context,path);
        Uri uriToImage = Uri.parse(path);
        Intent shareIntent = new Intent();
        //发送图片到朋友圈
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        //发送图片给好友。
//        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareIntent.setComponent(comp);
        shareIntent.putExtra("Kdescription", content);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    /**
     * 拉起微信朋友圈发送多张图片
     */
    private void shareweipyqSomeImg(Context context, Uri[] uri) {
        Intent shareIntent = new Intent();
        //1调用系统分析
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //2添加图片数组
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (int i = 0; i < uri.length; i++) {
            imageUris.add(uri[i]);
        }

        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");

        //3指定选择微信
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareIntent.setComponent(componentName);

        //4开始分享
        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
    }

    private static String insertImageToSystem(Context context, File imagePath) {
        String url = "";
        try {
            url = MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath.getAbsolutePath(), imagePath.getName(), "你对图片的描述");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return url;
    }


    private void alipayClick() {
        AccessibilityEventManaer.get().addEvent(new AccessibilityAlipayPayEvent(MainActivity.this));
        AliPayEvent.get().task(MainActivity.this);
    }

}


