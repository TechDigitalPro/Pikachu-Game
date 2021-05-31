package com.onet.onet.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onet.onet.R;

import java.util.ArrayList;
import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.ViewHolder>{

    Context mContext;
    private List<Object> mAdsItems = new ArrayList<>();

    public AdsAdapter(Context context, List<Object> mItems) {
        this.mContext = context;
        this.mAdsItems.addAll(mItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ads_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AdsItemModel adsItemModel = (AdsItemModel) mAdsItems.get(position);

        holder.name.setText(adsItemModel.getAppName());
        holder.description.setText(adsItemModel.getAppDescription());
        holder.icon.setImageResource(mContext.getResources().getIdentifier(adsItemModel.getAppIcon(), "drawable", mContext.getPackageName()));

        holder.adsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, adsItemModel.getAppName()+" -- "+ String.valueOf(position + 1) + "  clicked", Toast.LENGTH_LONG).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + adsItemModel.getPackageName()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + adsItemModel.getPackageName()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mAdsItems.size() > 0) return mAdsItems.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private View adsView;
        private ImageView icon;
        private TextView name;
        private TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adsView = itemView;
            icon = adsView.findViewById(R.id.ad_icon);
            name = adsView.findViewById(R.id.ad_name);
            description = adsView.findViewById(R.id.ad_description);
        }
    }
}
