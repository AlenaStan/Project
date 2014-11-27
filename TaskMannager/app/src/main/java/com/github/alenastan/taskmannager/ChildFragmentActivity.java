package com.github.alenastan.taskmannager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by lena on 12.11.2014.
 */
public class ChildFragmentActivity extends FragmentActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        setContentView(R.layout.activity_childfragment);
        fragmentManager = getSupportFragmentManager();
        setupFragment();
    }

    private void setupFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        ParentFragment parentFragment1 = new ParentFragment();
        Bundle args = new Bundle();
        args.putString("text", "Hi I am Parent Fragment");
        parentFragment1.setArguments(args);
        fragmentTransaction.add(R.id.frameLayout, parentFragment1);

        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        int count = fragmentManager.getBackStackEntryCount();
        if (count == 0) {
            // get Current Fragment
            Fragment currentFragment = fragmentManager
                    .findFragmentByTag("ParentFragment");

            // get ChildFragmentManager
            FragmentManager chilFragmentManager = currentFragment
                    .getChildFragmentManager();

            int childFragmentsCount = chilFragmentManager
                    .getBackStackEntryCount();

            // removing child fragments stack

            if (childFragmentsCount == 0) {
                super.onBackPressed();
            } else {
                chilFragmentManager.popBackStack();
            }
        } else {
            // removing replaced fragments stack
            fragmentManager.popBackStack();
        }
    }
}
