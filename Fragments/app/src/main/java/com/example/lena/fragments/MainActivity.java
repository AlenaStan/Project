package com.example.lena.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lena.fragments.fragment.FriendListFragment;
import com.example.lena.fragments.fragment.MainScreenFragment;
import com.example.lena.fragments.fragment.MessageFragment;
import com.example.lena.fragments.fragment.NewsFragment;


public class MainActivity extends ActionBarActivity {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainScreenFragment mainscreen = new MainScreenFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mainscreen).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    fragmentTransaction = getSupportFragmentManager().beginTransaction();

    switch (item.getItemId()){

            case R.id.menu_friends:
                showFriends();
                return true;
            case R.id.menu_mess:
                showMessages();
                return true;
            case R.id.menu_news:
                showNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showFriends() {
        FriendListFragment friendList = new FriendListFragment();
        fragmentTransaction.replace(R.id.container, friendList).commit();
    }
    private void showMessages() {
        MessageFragment messageFragment = new MessageFragment();
        fragmentTransaction.replace(R.id.container, messageFragment).commit();
    }
    private void showNews() {
        NewsFragment newsFragment = new NewsFragment();
        fragmentTransaction.replace(R.id.container, newsFragment).commit();
    }

}
