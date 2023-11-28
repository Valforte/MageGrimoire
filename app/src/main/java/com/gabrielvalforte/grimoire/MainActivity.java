package com.gabrielvalforte.grimoire;

import java.util.Locale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 mViewPager = null;
    SectionsPagerAdapter mSectionsPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getLifecycle());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager2)findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mSectionsPagerAdapter.getPageTitle(position));
            }
        }).attach();
    }

    public class SectionsPagerAdapter extends FragmentStateAdapter {

        public SectionsPagerAdapter(FragmentManager fm, Lifecycle lc) {
            super(fm, lc);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ListaMagiaFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            // Show 11 total pages.
            return 11;
        }

        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section0).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section5).toUpperCase(l);
                case 6:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 7:
                    return getString(R.string.title_section7).toUpperCase(l);
                case 8:
                    return getString(R.string.title_section8).toUpperCase(l);
                case 9:
                    return getString(R.string.title_section9).toUpperCase(l);
                case 10:
                    return getString(R.string.title_section10).toUpperCase(l);
            }
            return null;
        }
    }
}