package come.zhu.com.hushiguang.zhudome.AppBase;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by ShiGuang on 2015/10/23.
 */
public class FragmentHelper {

    /***
     * 启动想对应的fragment的
     *
     * @param fragment
     * @param containerId
     */
    public static void startFragmentAdd(Fragment fragment, Activity mActivity,int containerId) {
        //得到FragmentManager 管理fraggment
        android.app.FragmentManager fragmentManager = mActivity.getFragmentManager();
        // 去开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // 根据标签去得到一个具体TAG(fragment的名字)的fragment对象
        Fragment to_fragment = fragmentManager.findFragmentByTag(fragment
                .getClass().getName());
        // 当拿到的fragment对象不为空
        if (to_fragment != null) {
            // 去遍历拿到堆栈的所有的Fragment对象
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                // 根据索引下标拿到堆栈的BackStackEntry对象
                android.app.FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
                /**
                 * 判断当前的fragment对象的名字是否和堆栈的
                 * BackStackEntry.getName(获取通过创建该条目时，提供给FragmentTransaction.addToBackStack（字符串）这个名字)
                 * popBackStack
                 *          将该fragment弹到管理的栈顶然后显示
                 *          0或1 -> POP_BACK_STACK_INCLUSIVE
                 */
                if (fragment.getClass().getName().equals(entry.getName())) {
                    fragmentManager.popBackStack(entry.getName(), 1);
                }
            }
        }

        // 将该Fragment添加至后台堆栈 popBackStack那的是这个fragment的名字，要匹配对应
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        //将该fragment替换到堆栈的顶部 然后提交事务 commitAllowingStateLoss作用commit
        fragmentTransaction.replace(containerId, fragment,
                fragment.getClass().getName()).commitAllowingStateLoss();
    }

}
