package com.coordisapce.navigationsample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.coordispace.fragment.IndoorFragment;
import com.coordispace.listener.PoiEventListener;

import java.util.ArrayList;

/**
 * Created by jeong on 2017-06-15.
 */

public class NavigationActivity extends Activity implements PoiEventListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ArrayList<Integer> mapIds = new ArrayList<>();
        mapIds.add(38);
        //Fragment change
        FragmentManager fragmentManager = getFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.fl_ips);
        if(f != null && f instanceof IndoorFragment) {
            fragmentManager.beginTransaction().replace(R.id.fl_ips, IndoorFragment.newInstance(mapIds)).commit();
        }
        else {
            fragmentManager.beginTransaction().add(R.id.fl_ips, IndoorFragment.newInstance(mapIds)).commit();
        }
        fragmentManager.beginTransaction().addToBackStack(null);

        //call Activity
        /*
        Intent intent = new Intent(MainActivity.this, IntroActivity.class);
        intent.putExtra(IntroActivity.KEY_MAPINDEX_LIST, mapIds);
        startActivity(intent);
        finish();
        */
    }

    @Override
    public void onEvent(int i) {
        // 이벤트 설정된 Room 에 진입 시 발생하는 콜백함수
        Log.i("NavigationActivity", "EVENT : " + i);
    }
}
