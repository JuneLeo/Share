package com.leo.events.accessibility;

/**
 * Created by popfisher on 2017/7/10.
 */

public class AccessibilityNormalSample {
//        extends Activity implements View.OnClickListener {
//
//    private Handler mHandler = new Handler(Looper.getMainLooper());
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_accessibility_normal_sample);
//        initViews();
//    }
//
//    private void initViews() {
//        findViewById(R.id.normal_sample_back).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        final int id = v.getId();
//        switch (id) {
//            case R.id.normal_sample_back:
//                finish();
//                break;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                simulationClickByText();
//                simulationClickById();
//            }
//        }, 2000);
//    }
//
//    private void simulationClickByText() {
//        boolean result = AccessibilityOperator.getInstance().clickByText("复选框开关");
//        AccessibilityLog.printLog(result ? "复选框模拟点击成功" : "复选框模拟点击失败");
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickByText("单选按钮");
//                AccessibilityLog.printLog(result ? "单选按钮模拟点击成功" : "单选按钮模拟点击失败");
//            }
//        }, 2000);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickByText("OFF");
//                AccessibilityLog.printLog(result ? "OnOff开关模拟点击成功" : "OnOff开关模拟点击失败");
//            }
//        }, 4000);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickByText("退出本页面");
//                AccessibilityLog.printLog(result ? "退出本页面模拟点击成功" : "退出本页面模拟点击失败");
//            }
//        }, 6000);
//    }
//
//    private void simulationClickById() {
//        boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_checkbox");
//        AccessibilityLog.printLog(result ? "复选框模拟点击成功" : "复选框模拟点击失败");
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_radiobutton");
//                AccessibilityLog.printLog(result ? "单选按钮模拟点击成功" : "单选按钮模拟点击失败");
//            }
//        }, 2000);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_togglebutton");
//                AccessibilityLog.printLog(result ? "OnOff开关模拟点击成功" : "OnOff开关模拟点击失败");
//            }
//        }, 4000);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_back");
////                AccessibilityLog.printLog(result ? "退出本页面模拟点击成功" : "退出本页面模拟点击失败");
//                // 下面这个模拟点击系统返回键
//                boolean result = AccessibilityOperator.getInstance().clickBackKey();
//                AccessibilityLog.printLog(result ? "返回键模拟点击成功" : "返回键模拟点击失败");
//            }
//        }, 6000);
//    }
}
