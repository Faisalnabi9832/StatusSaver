package adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragments.Videosfragment;
import fragments.imagefragment;
import fragments.savefragment;


public class ViewPageradapter extends FragmentPagerAdapter {
    public ViewPageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new imagefragment();
        } else if (position == 1) {
            return new Videosfragment();
        }else {
            return new savefragment();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "images";
        } else if (position == 1) {
            return "Videos";
        }else if (position==2){
            return "saved";
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
