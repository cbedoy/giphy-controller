package cbedoy.giphycontroller.backend;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cbedoy.giphycontroller.CBGiphy;
import cbedoy.giphycontroller.GiphyViewCell;
import cbedoy.giphycontroller.IGiphy;
import cbedoy.giphycontroller.R;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class GiphyViewController extends Fragment implements IGiphy.IGiphyViewController
{
    private IGiphy.IGiphyPresenter presenter;

    public void setPresenter(IGiphy.IGiphyPresenter presenter) {
        this.presenter = presenter;
    }

    private RecyclerView mRecyclerView;
    private GiphyViewCell mViewCell;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.giphy_view_controller, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);

        mRecyclerView = view.findViewById(R.id.giphy_controller_recycler_view);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mViewCell = new GiphyViewCell();
        mViewCell.setImages(new ArrayList<>());
        mViewCell.setContext(getActivity());
        mViewCell.setListener(new GiphyViewCell.OnGIPHYViewCellListener() {
            @Override
            public void onSelectedGifItem(CBGiphy image) {
                open(image);
            }

            @Override
            public void onLongPressedGifItem(CBGiphy image) {
                share(image);
            }
        });
        mRecyclerView.setAdapter(mViewCell);



    }

    private void share(CBGiphy image) {
        List<Intent> targetShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            System.out.println("Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;
                Log.i("Package Name", packageName);

                if (packageName.contains("com.twitter.android") || packageName.contains("com.facebook.katana")
                        || packageName.contains("com.whatsapp") || packageName.contains("com.google.android.apps.plus")
                        || packageName.contains("com.google.android.talk") || packageName.contains("com.slack")
                        || packageName.contains("com.google.android.gm") || packageName.contains("com.facebook.orca")
                        || packageName.contains("com.yahoo.mobile") || packageName.contains("com.skype.raider")
                        || packageName.contains("com.android.mms")|| packageName.contains("com.linkedin.android")
                        || packageName.contains("com.google.android.apps.messaging")) {
                    Intent intent = new Intent();

                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.putExtra("AppName", resInfo.loadLabel(pm).toString());
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, image.getFixedUrl());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }
            }
            if (!targetShareIntents.isEmpty()) {
                Collections.sort(targetShareIntents, (o1, o2) -> o1.getStringExtra("AppName").compareTo(o2.getStringExtra("AppName")));
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            } else {
                Toast.makeText(getActivity(), "No app to share.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void open(CBGiphy image) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (presenter != null){
            presenter.loadTreading();
        }
    }

    @Override
    public void onLoadingTreading(List<CBGiphy> data) {
        getActivity().runOnUiThread(() -> {
            mViewCell.setImages(data);
            mViewCell.notifyDataSetChanged();
        });

    }
}
