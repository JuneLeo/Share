package com.leo.events;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.events.accessibility.event.AccessibilityEvent;
import com.leo.events.accessibility.event.AccessibilityEventManaer;
import com.leo.events.accessibility.event.AccessibilityShareImageEvent;
import com.leo.events.accessibility.utils.AccessibilityUtil;
import com.leo.events.model.BaseModel;
import com.leo.events.model.Goods;
import com.leo.events.model.TaskModel;
import com.leo.events.shareutil.ShareUtil;
import com.leo.events.shareutil.share.ShareListener;
import com.leo.events.shareutil.share.SharePlatform;
import com.leo.events.view.CustomView;
import com.leo.events.view.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter {
        LayoutInflater inflater;
        List<BaseModel> data = new ArrayList<>();
        Context context;

        private Handler mHandler = new Handler(Looper.getMainLooper());
        ClipboardManager clip;
        private static final String IMAGE_BASE = "http://120.78.206.49:8080/jspxcms-9.0.0";

        public ProductAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == BaseModel.TYPE_ADAPTER_ONE) {
                return new ProductVH(inflater.inflate(R.layout.item_product, parent, false));
            } else if (viewType == BaseModel.TYPE_ADAPTER_TWO) {
                return new TaskVH(inflater.inflate(R.layout.item_task, parent, false));
            } else if (viewType == BaseModel.TYPE_ADAPTER_THREE) {
                return new CustomVH(inflater.inflate(R.layout.item_custom, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder h, int position) {
            if (h instanceof ProductVH) {
                final ProductVH holder = (ProductVH) h;
                final Goods goods = (Goods) data.get(position);
                if (!TextUtils.isEmpty(goods.title)) {
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(goods.title);
                } else {
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(goods.text)) {
                    holder.tvDes.setVisibility(View.VISIBLE);
                    holder.tvDes.setText(goods.text);
                } else {
                    holder.tvDes.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(goods.smallImage)) {
                    holder.ivBig.setVisibility(View.VISIBLE);
                    Picasso.with(holder.itemView.getContext())
                            .load(IMAGE_BASE + goods.smallImage)
                            .fit()
                            .into(holder.ivBig);
                } else {
                    holder.ivBig.setVisibility(View.GONE);
                }
                holder.ivLeft.setVisibility(View.GONE);
                holder.ivRight.setVisibility(View.GONE);
                holder.tvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AccessibilityEventManaer.get().dispose();
                                ShareWindow.get().dismiss();
                            }
                        }, 5000);
                        if (AccessibilityUtil.isAccessibilitySettingsOn(holder.itemView.getContext())) {
                            ShareWindow.get().show();
                        }
                        ClipData clipData = ClipData.newPlainText("text", goods.title + "\n" + goods.text);
                        clip.setPrimaryClip(clipData);
                        ShareUtil.shareImage(holder.itemView.getContext(), SharePlatform.WX_TIMELINE, IMAGE_BASE + goods.smallImage, new ShareListener() {
                            @Override
                            public void shareSuccess() {
                                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
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
            } else if (h instanceof TaskVH) {
                TaskVH holder = (TaskVH) h;
                final TaskModel taskModel = (TaskModel) data.get(position);
                holder.task.setText(taskModel.text);
                holder.task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskModel.run();
                    }
                });

            } else if (h instanceof CustomVH) {
//                ((CustomVH) h).mCustomView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
                Calendar calendar = Calendar.getInstance();
                ((CustomVH) h).mCustomView.start(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

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


        @Override
        public int getItemViewType(int position) {
            BaseModel baseModel = data.get(position);
            return baseModel.type;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<BaseModel> goods) {
            this.data = goods;
            notifyDataSetChanged();
        }

        public class ProductVH extends RecyclerView.ViewHolder {
            TextView tvShare;
            ImageView ivBig;
            SquareImageView ivLeft;
            SquareImageView ivRight;
            TextView tvTitle;
            TextView tvDes;

            public ProductVH(View itemView) {
                super(itemView);
                tvShare = itemView.findViewById(R.id.share);
                ivBig = itemView.findViewById(R.id.big);
                ivLeft = itemView.findViewById(R.id.left);
                ivRight = itemView.findViewById(R.id.right);
                tvTitle = itemView.findViewById(R.id.title_s);
                tvDes = itemView.findViewById(R.id.des);
            }
        }


        public class TaskVH extends RecyclerView.ViewHolder {
            public TextView task;

            public TaskVH(View itemView) {
                super(itemView);
                task = itemView.findViewById(R.id.task);
            }
        }

        public class CustomVH extends RecyclerView.ViewHolder {
            public CustomView mCustomView;

            public CustomVH(View itemView) {
                super(itemView);
                mCustomView = itemView.findViewById(R.id.custom);
            }
        }
    }