package com.example.myapplication.ui.home;

import android.graphics.Color;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.CategoryAdapter;
import com.example.myapplication.CategoryModel;
import com.example.myapplication.GridProductViewAdapter;
import com.example.myapplication.HorizonantleProductScrollModel;
import com.example.myapplication.HorizontalProductScrollAdapter;
import com.example.myapplication.R;
import com.example.myapplication.SliderAdapter;
import com.example.myapplication.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllCategoriesFragment extends Fragment {

    private AllCategoriesViewModel allCategoriesViewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    /////////////Banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel>sliderModelList;
    private int currentPage =2;
    private Timer timer;
    final  private long DELAY_TIME=3000;
    final  private long PERIOD_TIME=3000;

    ///////////Banner Slider

    //////////StripSlider
    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;
    //////StripSlider

    ///////Horizontal_Scroll_Layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllButton;
    private RecyclerView horizontalRecylerView;

    //////Horizontal_Scroll_Layout
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        allCategoriesViewModel = new ViewModelProvider(this).get(AllCategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_categories, container, false);



        /*final TextView textView = root.findViewById(R.id.text_home);
        allCategoriesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        recyclerView = root.findViewById(R.id.all_categories_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel(R.drawable.logo, "Foods"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Biscuit"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Namkin"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Noodles and Pasta"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Beverages"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Dairy"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Kitchen"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Chocolate"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "PersonalCare"));
        categoryModelList.add(new CategoryModel(R.drawable.grocery, "Soap"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ///////////Baner Slider
        bannerSliderViewPager=root.findViewById(R.id.bannerSlider_view_pager);
        sliderModelList =new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"green"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"green"));
        sliderModelList.add(new SliderModel(R.drawable.grocery,"green"));




        sliderModelList.add(new SliderModel(R.drawable.gst,"green"));
        sliderModelList.add(new SliderModel(R.drawable.g2,"green"));
        sliderModelList.add(new SliderModel(R.drawable.g3,"green"));
        sliderModelList.add(new SliderModel(R.drawable.aad,"green"));
        sliderModelList.add(new SliderModel(R.drawable.g1,"green"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"green"));



        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"green"));
        sliderModelList.add(new SliderModel(R.drawable.grocery,"green"));
        sliderModelList.add(new SliderModel(R.drawable.gst,"green"));


        SliderAdapter sliderAdapter=new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener= new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage=i;

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if(i == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }

            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerSlideShow();
             //   startBannerSlideShow();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }
                return false;
            }
        });
    ////////Banner Slider

        //////////StripSlider
        stripAdImage = root.findViewById(R.id.strip_add_image);
        stripAdContainer=root.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.drawable.g4);
        stripAdContainer.setBackgroundColor(Color.BLACK);
        //////////StripSlider

        //////Horizontal_Scroll_Layout

        horizontalLayoutTitle=root.findViewById(R.id.horizon_scroll_layout_title);
        horizontalViewAllButton=root.findViewById(R.id.horizon_viewall_layout_Button);
        horizontalRecylerView=root.findViewById(R.id.horizon_scroll_layout_RecylerView);

        List<HorizonantleProductScrollModel> horizonantleProductScrollModelList=new ArrayList<>();
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));
        horizonantleProductScrollModelList.add(new HorizonantleProductScrollModel(R.drawable.product,"MariaLite","Healthy_Wealthy","Rs.30/-"));

       HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizonantleProductScrollModelList);
       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
       linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       horizontalRecylerView.setLayoutManager(linearLayoutManager);

       horizontalRecylerView.setAdapter(horizontalProductScrollAdapter);
       horizontalProductScrollAdapter.notifyDataSetChanged();

     ////////////////horizon


        //////grid product layout
        TextView gridLayoutTitle =root.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutButton =root.findViewById(R.id.grid_product_layout_Button);
        GridView gridView =root.findViewById(R.id.grid_product_layout_gridView);

        gridView.setAdapter(new GridProductViewAdapter(horizonantleProductScrollModelList));


        //////grid product layout
        return root;
    }
    ////////Banner
    private void pageLooper(){
      if(currentPage==sliderModelList.size() -2){
          currentPage=2;
          bannerSliderViewPager.setCurrentItem(currentPage,false);
      }else{

          }if(currentPage==1){
            currentPage=sliderModelList.size() -3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }

    }

    private void startBannerSlideShow(){
        Handler handler=new Handler();
        Runnable update=new Runnable() {
            @Override
            public void run() {
                if(currentPage >=sliderModelList.size()){
                    currentPage=1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
             handler.post(update);
            }
        } ,DELAY_TIME,PERIOD_TIME
        );
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }
    ///////Banner
}