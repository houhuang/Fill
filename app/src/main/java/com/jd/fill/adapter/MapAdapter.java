package com.jd.fill.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.fill.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by houhuang on 18/3/13.
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapHolder> {

    private Context mContext;

    private int mTotalLevel = 0;
    private int mCurrentLevel = 0;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private View mHeaderView;
    private View mFooterView;


    public OnMapItemClickListener listener;
    public void setOnMapItemClickListener(OnMapItemClickListener listener)
    {
        this.listener = listener;
    }

    public MapAdapter(Context context, int totalLevel, int curLevel)
    {
        mContext = context;
        mTotalLevel = totalLevel;
        mCurrentLevel = curLevel;
    }

    public class MapHolder extends RecyclerView.ViewHolder
    {
        private View mContentView;
        private TextView mLevelTextView;

        public MapHolder(final View view)
        {
            super(view);

            mContentView = (View) view.findViewById(R.id.map_content);
            mLevelTextView = (TextView) view.findViewById(R.id.map_text);

            if (view == mHeaderView || view == mFooterView)
            {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                        {
                            if (view == mHeaderView)
                                listener.OnClickHeaderOrFooter(view, true);
                            if (view.equals(mFooterView))
                                listener.OnClickHeaderOrFooter(view, false);
                        }
                    }
                });
            }else
            {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.OnClickItem(v, getItemPosition(MapHolder.this));
                    }
                });
            }
        }
    }

    @Override
    public MapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new MapHolder(mHeaderView);
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new MapHolder(mFooterView);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
        return new MapHolder(view);
    }

    @Override
    public void onBindViewHolder(MapHolder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
            return;

        if (position > mCurrentLevel)
        {
            holder.mContentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.map_item_content_nopass));
        }else if (position < mCurrentLevel)
        {
            holder.mContentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.map_item_content_pass));
        }else
        {
            holder.mContentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_paht1));
        }

        int text = position;
        if (mHeaderView != null)
            --text;
        holder.mLevelTextView.setText("" + (text + 1));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == TYPE_FOOTER
                            || getItemViewType(position) == TYPE_HEADER) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (mHeaderView != null && position == 0)
            return TYPE_HEADER;
        if (mFooterView != null && position == getItemCount() - 1)
            return TYPE_FOOTER;

        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        int count = mTotalLevel;
        if (mHeaderView != null)
            ++count;
        if (mFooterView != null)
            ++count;

        return count;
    }

    private int getItemPosition(RecyclerView.ViewHolder holder)
    {
        int pos = holder.getAdapterPosition();
        if (mHeaderView != null)
            return pos - 1;
        return pos;
    }

    public void setHeaderView(View headerView)
    {
        mHeaderView = headerView;
    }

    public View getHeaderView()
    {
        return mHeaderView;
    }

    public void setFooterView(View footerView)
    {
        mFooterView = footerView;
    }

    public View getFooterView()
    {
        return mFooterView;
    }

    public interface OnMapItemClickListener
    {
        void OnClickItem(View view, int position);
        void OnClickHeaderOrFooter(View view, boolean isHeader);
    }
}