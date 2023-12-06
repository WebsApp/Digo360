package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.wapss.digo360.R;
import com.wapss.digo360.response.BannerResponse;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context Mcontext;
    private List<BannerResponse.Result> theSlideItemsModelClassList;

    public BannerAdapter(Context Mcontext, List<BannerResponse.Result> theSlideItemsModelClassList) {
        this.Mcontext = Mcontext;
        this.theSlideItemsModelClassList = theSlideItemsModelClassList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.slider_layout,null);

        ImageView featured_image = sliderLayout.findViewById(R.id.image_view);

        Picasso.with(Mcontext)
                .load(theSlideItemsModelClassList.get(position).getImageUrl())
                .into(featured_image);

//        featured_image.setImageResource(theSlideItemsModelClassList.get(position).getImage());
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return theSlideItemsModelClassList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
