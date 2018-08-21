package com.giiso.submmited.ui.widget.swipRecycleView;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.giiso.submmited.R;
import com.giiso.submmited.bean.TypeSynthesis;
import com.giiso.submmited.ui.base.adapter.BaseRecyclerAdapter;
import com.giiso.submmited.utils.StringUtils;
import com.giiso.submmited.utils.glide.GlideUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/13.
 */

public class SwipRecycleViewAdapter extends BaseRecyclerAdapter<TypeSynthesis> {

    private final int VIEW_TYPE_ONE = 1;
    private final int VIEW_TYPE_TIP = 0x111;
    private final int VIEW_TYPE_BANNER = 3;
    private final int VIEW_TYPE_NONE = 0;
    private final int VIEW_TYPE_THREE = 2;
    private final int VIEW_TYPE_VIDEO = 0x112;
    private final int VIEW_TYPE_DYNAMIC = 0x113;
    public static int MAX = Integer.MAX_VALUE;

    public SwipRecycleViewAdapter(Context context, int mode) {
        super(context, mode);
    }


    @Override
    public int getItemViewType(int position) {
//        int viewType = super.getItemViewType(position);
//        if (viewType != VIEW_TYPE_NORMAL)
//            return viewType;
        TypeSynthesis typeSynthesis = mItems.get(position);
        if ("0".equals(typeSynthesis.getIsType())) {
            if (typeSynthesis.getType() == 1) {
                return VIEW_TYPE_ONE;
            } else if (typeSynthesis.getType() == 2) {
                return VIEW_TYPE_THREE;
            } else {
                return VIEW_TYPE_NONE;
            }
        } else if ("banner".equals(typeSynthesis.getIsType())) {
            return VIEW_TYPE_BANNER;
        } else if ("1".equals(typeSynthesis.getIsType())) {
            return VIEW_TYPE_VIDEO;
        } else if ("2".equals(typeSynthesis.getIsType())) {
            return VIEW_TYPE_DYNAMIC;
        } else {
            return typeSynthesis.getType();
        }
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        if (type == VIEW_TYPE_THREE) {
            return new SwipRecycleViewAdapter.TrackNewsNoPictureViewHolder(mInflater.inflate(R.layout.item_list_tracknews_nopicture, parent, false));
        } else if (type == VIEW_TYPE_TIP) {
            return new SwipRecycleViewAdapter.TipViewHolder(mInflater.inflate(R.layout.item_list_tip, parent, false));
        } else if (type == VIEW_TYPE_ONE) {
            return new SwipRecycleViewAdapter.TrackNewsNormalViewHolder(mInflater.inflate(R.layout.item_collect_history, parent, false));
        } else if (type == VIEW_TYPE_NONE) {
            return new SwipRecycleViewAdapter.TrackNewsNormalViewHolder(mInflater.inflate(R.layout.item_collect_history, parent, false));
        } else if (type == VIEW_TYPE_VIDEO) {
            return new SwipRecycleViewAdapter.SpeechHolderVideo(mInflater.inflate(R.layout.item_collection_video, parent, false));
        } else if (type == VIEW_TYPE_DYNAMIC) {
            return new SwipRecycleViewAdapter.SpeechHolderDynamic(mInflater.inflate(R.layout.item_collection_dynamic, parent, false));
        }
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final TypeSynthesis item, final int position) {

        if (holder instanceof SwipRecycleViewAdapter.TipViewHolder) {
            SwipRecycleViewAdapter.TipViewHolder viewHolder = (SwipRecycleViewAdapter.TipViewHolder) holder;
            viewHolder.tvTip.setText(item.getTitle());
        } else if (holder instanceof SwipRecycleViewAdapter.TrackNewsNoPictureViewHolder) {
            SwipRecycleViewAdapter.TrackNewsNoPictureViewHolder viewHolder = (SwipRecycleViewAdapter.TrackNewsNoPictureViewHolder) holder;
            viewHolder.tvSource.setText(item.getSite());
            viewHolder.tvTitle.setText(item.getTitle());
            viewHolder.v_divider.setVisibility(View.GONE);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = df.parse(item.getTime());
                calendar.setTime(date);
                viewHolder.tvTime.setText(StringUtils.formatSomeAgoDemand(calendar));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Util.isOnMainThread()) {
                try {
                    Activity activity = (Activity) mContext;
                    if (!activity.isFinishing()) {
                        if (mContext != null)
                            if (item.getImage() != null && item.getImage().size() > 0) {
                                GlideUtil.glideImgWifiSwitchCenterCrop(mContext, "" + item.getImage().get(0), viewHolder.iv_img_one);
                            }
                        if (item.getImage() != null && item.getImage().size() > 1) {
                            GlideUtil.glideImgWifiSwitchCenterCrop(mContext, "" + item.getImage().get(1), viewHolder.iv_img_two);
                        }

                        if (item.getImage() != null && item.getImage().size() > 2) {
                            GlideUtil.glideImgWifiSwitchCenterCrop(mContext, "" + item.getImage().get(2), viewHolder.iv_img_three);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //说明是ApplicationContext
                }
            }
            viewHolder.tv_propagation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(position, MAX);
                }
            });
        } else if (holder instanceof TrackNewsNormalViewHolder) {
            SwipRecycleViewAdapter.TrackNewsNormalViewHolder viewHolder = (SwipRecycleViewAdapter.TrackNewsNormalViewHolder) holder;
            viewHolder.tvSource.setText(item.getSite());
            viewHolder.tvTitle.setText(item.getTitle());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = df.parse(item.getTime());
                calendar.setTime(date);
                viewHolder.tvTime.setText(StringUtils.formatSomeAgoDemand(calendar));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (item.getType() != VIEW_TYPE_ONE) {
                viewHolder.ivImg.setVisibility(View.GONE);
            } else if (Util.isOnMainThread()) {
                viewHolder.ivImg.setVisibility(View.VISIBLE);
                try {
                    Activity activity = (Activity) mContext;
                    if (!activity.isFinishing()) {
                        if (mContext != null) {
                            GlideUtil.glideImgWifiSwitchCenterCrop(mContext,""+item.getImage().get(0),viewHolder.ivImg);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //说明是ApplicationContext
                }
            }
//            if (mReadState.already(item.getNid())) {
//                viewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.list_title_read_color));
//            } else {
//                viewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.list_title_color));
//            }

            viewHolder.tv_propagation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(position, MAX);
                }
            });
        } else if (holder instanceof SpeechHolderVideo) {
            SwipRecycleViewAdapter.SpeechHolderVideo viewHolder = (SwipRecycleViewAdapter.SpeechHolderVideo) holder;
            viewHolder.tvTitle.setText(item.getTitle());
            viewHolder.tv_source.setText(item.getSite());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = df.parse(""+item.getTime());
                calendar.setTime(date);
                viewHolder.tv_time.setText(StringUtils.formatSomeAgoDemand(calendar));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Activity activity = (Activity) mContext;
                if (!activity.isFinishing()) {
                    if (mContext != null) {
                        GlideUtil.glideImgWifiSwitchCenterCrop(mContext, "" + item.getImageUrl(), viewHolder.ivImg);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                //说明是ApplicationContext
            }
        } else if (holder instanceof SpeechHolderDynamic) {
            SwipRecycleViewAdapter.SpeechHolderDynamic viewHolder = (SwipRecycleViewAdapter.SpeechHolderDynamic) holder;
            viewHolder.tv_details.setText(item.getContent());
            viewHolder.ll_voice.setVisibility(View.GONE);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = df.parse(item.getCreateTime());
                calendar.setTime(date);
                viewHolder.tv_time.setText(StringUtils.formatSomeAgoDemand(calendar));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    static class TrackNewsNoPictureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_collection)
        TextView tvTime;
        @BindView(R.id.tv_propagation)
        TextView tv_propagation;
        @BindView(R.id.iv_img_one)
        ImageView iv_img_one;
        @BindView(R.id.iv_img_two)
        ImageView iv_img_two;
        @BindView(R.id.iv_img_three)
        ImageView iv_img_three;
        @BindView(R.id.v_divider)
        View v_divider;

        public TrackNewsNoPictureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TrackNewsNormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.item_del_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_collection)
        TextView tvCollection;
        @BindView(R.id.tv_propagation)
        TextView tv_propagation;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_text)
        TextView text_del;

        public TrackNewsNormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tip)
        TextView tvTip;

        public TipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SpeechHolderVideo extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_source)
        TextView tv_source;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public SpeechHolderVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SpeechHolderDynamic extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_details)
        TextView tv_details;
        @BindView(R.id.tv_long)
        TextView tv_long;
        @BindView(R.id.tv_second)
        TextView tv_second;
        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.ll_voice)
        LinearLayout ll_voice;

        public SpeechHolderDynamic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     *
     */
    public interface OnClickListener {
        void onTextClick(int position);
    }
}
