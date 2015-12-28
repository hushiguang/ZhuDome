package come.zhu.com.hushiguang.zhudome.entity;


import android.app.Fragment;

/**
 * Created by ShiGuang on 2015/10/23.
 */
public class FragmentEntity {

    Fragment fragment;

    public FragmentEntity() {
    }

    public FragmentEntity(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    @Override
    public String toString() {
        return "FragmentEntity{" +
                "fragment=" + fragment.getClass().getName() +
                '}';
    }


}
