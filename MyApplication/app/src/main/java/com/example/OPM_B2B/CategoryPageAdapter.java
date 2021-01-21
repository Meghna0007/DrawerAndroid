package com.example.OPM_B2B;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryPageAdapter extends RecyclerView.Adapter {

    private List<CategoryPageModel> categoryPageModelList;

    public CategoryPageAdapter(List<CategoryPageModel> categoryPageModelList) {
        this.categoryPageModelList = categoryPageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (categoryPageModelList.get(position).getType()) {

            case 0:
                return CategoryPageModel.BANNER_SLIDER;

            case 1:
                return CategoryPageModel.STRIP_AD_BANNER;

            case 2:
                return CategoryPageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return CategoryPageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;


        }
    }

    public class BannersliderViewholder extends RecyclerView.ViewHolder {

        private ViewPager bannerSliderViewPager;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannersliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.bannerSlider_view_pager);


        }

        private void setBannerSliderViewPager(List<SliderModel> sliderModelList) {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int i) {
                    currentPage = i;

                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (i == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(sliderModelList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelList);
                    stopBannerSlideShow();
                    //   startBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(sliderModelList);
                    }
                    return false;
                }
            });


        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            } else {

            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }

        }

        private void startBannerSlideShow(List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   handler.post(update);
                               }
                           }, DELAY_TIME, PERIOD_TIME
            );
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case CategoryPageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slidingadd_layout, parent, false);
                return new BannersliderViewholder(bannerSliderView);


            case CategoryPageModel.STRIP_AD_BANNER:
                View stripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewholder(stripAdView);

            case CategoryPageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewholder(horizontalProductView);
            case CategoryPageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewholder(gridProductView);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (categoryPageModelList.get(position).getType()) {
            case CategoryPageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelLists = categoryPageModelList.get(position).getSliderModelList();
                ((BannersliderViewholder) holder).setBannerSliderViewPager(sliderModelLists);
                break;
            case CategoryPageModel.STRIP_AD_BANNER:
                int resource = categoryPageModelList.get(position).getResource();
                String color = categoryPageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewholder) holder).setStripAd(resource, color);
                break;
            case CategoryPageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontallayouttitle = categoryPageModelList.get(position).getTitle();
                List<HorizonantleProductScrollModel> horizonantleProductScrollModelList = categoryPageModelList.get(position).getHorizonantleProductScrollModelList();
                ((HorizontalProductViewholder) holder).setHorizontalProductLayout(horizonantleProductScrollModelList, horizontallayouttitle);
                break;
            case CategoryPageModel.GRID_PRODUCT_VIEW:
                String gridlayouttitle = categoryPageModelList.get(position).getTitle();
                List<HorizonantleProductScrollModel> gridProductScrollModelList = categoryPageModelList.get(position).getHorizonantleProductScrollModelList();
                ((GridProductViewholder) holder).setGridProductLayout(gridProductScrollModelList, gridlayouttitle);
                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return categoryPageModelList.size();
    }

    public class StripAdBannerViewholder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewholder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_add_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(int resource, String color) {
            stripAdImage.setImageResource(resource);
            System.out.println("color " + color);
            try {
                stripAdContainer.setBackgroundColor(Color.parseColor(color));
            } catch (Exception e) {
                stripAdContainer.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

    }


    public class HorizontalProductViewholder extends RecyclerView.ViewHolder {
        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllButton;
        private RecyclerView horizontalRecylerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);

            horizontalLayoutTitle = itemView.findViewById(R.id.horizon_scroll_layout_title);
            horizontalViewAllButton = itemView.findViewById(R.id.horizon_viewall_layout_Button);
            horizontalRecylerView = itemView.findViewById(R.id.horizon_scroll_layout_RecylerView);
        }

        private void setHorizontalProductLayout(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList, String title) {
            horizontalLayoutTitle.setText(title);
            if (horizonantleProductScrollModelList.size() > 8) {
                horizontalViewAllButton.setVisibility(View.VISIBLE);

                horizontalViewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalViewAllButton.setVisibility(View.INVISIBLE);
            }


            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizonantleProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecylerView.setLayoutManager(linearLayoutManager);

            horizontalRecylerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }


    public class GridProductViewholder extends RecyclerView.ViewHolder {
        private TextView gridLayoutTitle;
        private Button gridLayoutButton;
        private GridView gridView;

        public GridProductViewholder(@NonNull View itemView) {

            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutButton = itemView.findViewById(R.id.grid_product_layout_Button);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }

        private void setGridProductLayout(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList, String title) {
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductViewAdapter(horizonantleProductScrollModelList));
            gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }
}











