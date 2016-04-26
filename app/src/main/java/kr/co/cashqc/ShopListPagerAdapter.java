
package kr.co.cashqc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * The type Tabs pager adapter.
 */
public class ShopListPagerAdapter extends FragmentStatePagerAdapter {

    public ShopListPagerAdapter(FragmentManager fm, double lat, double lng, int distance,
            String type, boolean life) {
        super(fm);
        mLat = lat;
        mLng = lng;
        mDistance = distance;
        mType = type;
        mLife = life;
    }

    private boolean mLife;

    private String mType;

    private double mLat, mLng;

    private int mDistance;

    @Override
    public Fragment getItem(int position) {
        // position++;
        // return new ShopListFragment(position);

        Fragment slf = new ShopListFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P

        // if (SALE_ZONE) {
        // args.putInt("mType", position);
        // } else {
        // args.putInt("mType", position + 1);
        // }

        if (mLife) {
            switch (position) {
                case 0:
                    args.putString("mType", "W09");
                    break;
                case 1:
                    args.putString("mType", "W10");
                    break;
                case 2:
                    args.putString("mType", "W11");
                    break;
                case 3:
                    args.putString("mType", "W12");
                    break;
                case 4:
                    args.putString("mType", "W13");
                    break;
                case 5:
                    args.putString("mType", "W14");
                    break;
                case 6:
                    args.putString("mType", "W15");
                    break;
                case 7:
                    args.putString("mType", "W16");
                    break;
                case 8:
                    args.putString("mType", "W18");
                    break;
                case 9:
                    args.putString("mType", "W19");
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    args.putString("mType", "W01");
                    break;
                case 1:
                    args.putString("mType", "W02");
                    break;
                case 2:
                    args.putString("mType", "W03");
                    break;
                case 3:
                    args.putString("mType", "W04");
                    break;
                case 4:
                    args.putString("mType", "W05");
                    break;
                case 5:
                    args.putString("mType", "W06");
                    break;
                case 6:
                    args.putString("mType", "W07");
                    break;
                case 7:
                    args.putString("mType", "W08");
                    break;
            }
        }

        args.putDouble("lat", mLat);
        args.putDouble("lng", mLng);
        args.putInt("distance", mDistance);
        slf.setArguments(args);

        return slf;
    }

    @Override
    public int getCount() {

        if (mLife) {
            return 10;
        } else {
            return 8;
        }
    }

    // @Override
    // public Fragment getItem(int position) {
    // return ShopListFragment.newInstance(position+1);
    // }

    // @Override
    // public CharSequence getPageTitle(int position) {
    // String[] mTabs = {"치킨", "피자", "중식", "한식", "닭발", "야식", "족발", "일식"};
    // return mTabs[position];
    // }

}
