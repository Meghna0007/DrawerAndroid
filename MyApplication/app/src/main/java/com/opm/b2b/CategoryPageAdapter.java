package com.opm.b2b;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryPageAdapter extends RecyclerView.Adapter {

    private List<CategoryPageModel> categoryPageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    public CategoryPageAdapter(List<CategoryPageModel> categoryPageModelList) {
        this.categoryPageModelList = categoryPageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
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
        private int currentPage ;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private  List<SliderModel> arrangeList;

        public BannersliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.bannerSlider_view_pager);


        }

        private void setBannerSliderViewPager(List<SliderModel> sliderModelList) {
            currentPage=2;
            if(timer!=null){
                timer.cancel();
            }
            arrangeList=new ArrayList<>();
            for(int x=0;x<sliderModelList.size();x++){
                arrangeList.add(x,sliderModelList.get(x));
            }
            arrangeList.add(0,sliderModelList.get(sliderModelList.size() -2));
            arrangeList.add(1,sliderModelList.get(sliderModelList.size() -1));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));
            SliderAdapter sliderAdapter = new SliderAdapter(arrangeList);
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
                        pageLooper(arrangeList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(arrangeList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangeList);
                    stopBannerSlideShow();
                    //   startBannerSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(arrangeList);
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
                View grid_ProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewholder(grid_ProductView);

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
                String resource = categoryPageModelList.get(position).getResource();
                String color = categoryPageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewholder) holder).setStripAd(resource, color);
                break;
            case CategoryPageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutcolor =categoryPageModelList.get(position).getBackgroundColor();
                String horizontallayouttitle = categoryPageModelList.get(position).getTitle();
                List<WishlistModel>viewAllProductList=categoryPageModelList.get(position).getViewAllProductList();
                List<HorizonantleProductScrollModel> horizonantleProductScrollModelList = categoryPageModelList.get(position).getHorizonantleProductScrollModelList();
                ((HorizontalProductViewholder) holder).setHorizontalProductLayout(horizonantleProductScrollModelList, horizontallayouttitle,layoutcolor,viewAllProductList);
                break;
            case CategoryPageModel.GRID_PRODUCT_VIEW:
                String gridLayoutColor=categoryPageModelList.get(position).getBackgroundColor();
                String grid_layouttitle = categoryPageModelList.get(position).getTitle();
                List<HorizonantleProductScrollModel> gridProductScrollModelList = categoryPageModelList.get(position).getHorizonantleProductScrollModelList();
                ((GridProductViewholder) holder).setGridProductLayout(gridProductScrollModelList, grid_layouttitle,gridLayoutColor);
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

        private void setStripAd(String resource, String color) {
            // stripAdImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(stripAdImage);
            System.out.println("color " + color);
            try {
                stripAdContainer.setBackgroundColor(Color.parseColor(color));
            } catch (Exception e) {
                stripAdContainer.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

    }


    public class HorizontalProductViewholder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllButton;
        private RecyclerView horizontalRecylerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container_);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizon_scroll_layout_title);
            horizontalViewAllButton = itemView.findViewById(R.id.horizon_viewall_layout_Button);
            horizontalRecylerView = itemView.findViewById(R.id.horizon_scroll_layout_RecylerView);
            horizontalRecylerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList, String title,String color,List<WishlistModel>viewAllProductList) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);

            for (final HorizonantleProductScrollModel model:horizonantleProductScrollModelList) {
                if (!model.getProductid().isEmpty() && model.getProductTitle().isEmpty()){
                 firebaseFirestore.collection("PRODUCTS")
                         .document(model.getProductid())
                         .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                       model.setProductTitle(task.getResult().getString("product_title"));
                        model.setProductTitle(task.getResult().getString("product_image_1"));
                        model.setProductTitle(task.getResult().getString("product_price"));

                      WishlistModel wishlistModel=viewAllProductList
                              .get(horizonantleProductScrollModelList.indexOf(model));
                      wishlistModel.setProductTitle(task.getResult().getString("product_title"));
                        wishlistModel.setProductPrice(task.getResult().getString("product_price"));
                        wishlistModel.setProductImage(task.getResult().getString("product_image_1"));
                        wishlistModel.setCuttedPrice(task.getResult().getString("cuttedPrice"));
                        /*wishlistModel.(task.getResult().getBoolean("COD"));*/
                        wishlistModel.setInStock(task.getResult().getLong("stock_quantity")>0);

                        if (horizonantleProductScrollModelList.indexOf(model)==horizonantleProductScrollModelList.size() -1){
                            if (horizontalRecylerView.getAdapter()!=null){
                                horizontalRecylerView.getAdapter().notifyDataSetChanged();
                            }
                        }




                    }else {
                        /////////do nothing
                    }
                     }
                 });
                }
            }
            if (horizonantleProductScrollModelList.size() > 8) {
                horizontalViewAllButton.setVisibility(View.VISIBLE);

                horizontalViewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistModelList=viewAllProductList;
                        Intent viewAllIntent=new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        viewAllIntent.putExtra("displayName",title);
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
        private ConstraintLayout container__;
        private TextView gridLayoutTitle;
        private Button gridLayoutButton;
        private GridLayout gridProductLayout;

        public GridProductViewholder(@NonNull View itemView) {

            super(itemView);
            container__ = itemView.findViewById(R.id.container__);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutButton = itemView.findViewById(R.id.grid_product_layout_Button);
            gridProductLayout = itemView.findViewById(R.id.gridLayout);

        }

        private void setGridProductLayout(List<HorizonantleProductScrollModel> horizonantleProductScrollModelList, String title, String color) {
            container__.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for (final HorizonantleProductScrollModel model:horizonantleProductScrollModelList) {
                if (!model.getProductid().isEmpty() && model.getProductTitle().isEmpty()){
                    firebaseFirestore.collection("PRODUCTS")
                            .document(model.getProductid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                model.setProductTitle(task.getResult().getString("product_title"));
                                model.setProductTitle(task.getResult().getString("product_image_1"));
                                model.setProductTitle(task.getResult().getString("product_price"));



                                if (horizonantleProductScrollModelList.indexOf(model)==horizonantleProductScrollModelList.size() -1){
                                    setGridData(title,horizonantleProductScrollModelList);
                                    if (!title.equals("")) {
                                        gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ViewAllActivity.horizonantleProductScrollModelList = horizonantleProductScrollModelList;
                                                Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                                                viewAllIntent.putExtra("layout_code", 1);
                                                //TODO this is a sample data ..actually there should be deal of day collection that shud be passed here
                                                viewAllIntent.putExtra("displayName", title);
                                                viewAllIntent.putExtra("collectionName", "Rice");
                                                itemView.getContext().startActivity(viewAllIntent);
                                            }
                                        });

                                    }

                                }




                            }else {
                                /////////do nothing
                            }
                        }
                    });
                }
            }


        setGridData(title,horizonantleProductScrollModelList);
    }
    private void setGridData(String title,final List<HorizonantleProductScrollModel> horizonantleProductScrollModelList){
        for (int x = 0; x < 4; x++) {
            ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.hS_product_Image);
            TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.hS_product_title);
            TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.hS_product_Description);
            TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.hS_product_Price);
            Glide.with(itemView.getContext()).load(horizonantleProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.fksmall)).into(productImage);

            productTitle.setText(horizonantleProductScrollModelList.get(x).getProductTitle());
            productDescription.setText(horizonantleProductScrollModelList.get(x).getProductdescription());
            productPrice.setText("Rs." + horizonantleProductScrollModelList.get(x).getProductprice() + "/-");

            gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
            if (!title.equals("")) {
                int finalX = x;
                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("product_id_",horizonantleProductScrollModelList.get(finalX).getProductid());
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }


        }
    }



    }}











