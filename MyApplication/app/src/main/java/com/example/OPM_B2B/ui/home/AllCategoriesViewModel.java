package com.example.OPM_B2B.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.OPM_B2B.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<CategoryModel>> mCategoryData;

    public AllCategoriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    // Get vehicle list of data return type MutableLiveData
    MutableLiveData<List<CategoryModel>> getCategoryData() {
        mCategoryData = new MutableLiveData<>();
        loadAllCategoryData();
        return mCategoryData;
    }

    public LiveData<String> getText() {
        return mText;
    }

    private void loadAllCategoryData() {
        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link", "Oil"));
        categoryModelList.add(new CategoryModel("link", "Soap"));
        categoryModelList.add(new CategoryModel("link", "Grocery"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        categoryModelList.add(new CategoryModel("link", "HOME"));
        mCategoryData.postValue(categoryModelList);
    }

}