package com.se491.eggxact.ui.landingpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    HomeFragment homeFragment;
    CategoriesFragment categoriesFragment;
    FavoritesFragment favoritesFragment;
    RandomFragment randomFragment;
    ProfileFragment profileFragment;
    RecomendationsFragment recomendationsFragment;

    public FragmentAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }
                return homeFragment;
            case 1:
                if (categoriesFragment == null) {
                    categoriesFragment = CategoriesFragment.newInstance();
                }
                return categoriesFragment;
            case 2:
                if(randomFragment == null){
                    randomFragment = RandomFragment.newInstance();
                }
                return randomFragment;
            case 3:
                if (favoritesFragment == null) {
                    favoritesFragment = FavoritesFragment.newInstance();
                }
                return favoritesFragment;
            case 4:
                if (recomendationsFragment == null) {
                    recomendationsFragment = RecomendationsFragment.newInstance();
                }
                return recomendationsFragment;
            case 5:
            default:
                if (profileFragment == null) {
                    profileFragment = ProfileFragment.newInstance();
                }
                return profileFragment;
        }


    }

    // must return the number of fragments we're using otherwise they won't appear
    @Override
    public int getCount() {
        return 5;
    }

    // having title is optional
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Categories";
            case 2:
                return "Random";
            case 3:
                return "Favorites";
            case 4:
                return "Recommendations";
            case 5:
                return "Profile";
            default:
                return "";
        }
    }
}
