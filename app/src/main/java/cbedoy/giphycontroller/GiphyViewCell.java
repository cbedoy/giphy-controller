package cbedoy.giphycontroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cbedoy.giphycontroller.model.GiphyImage;
import cbedoy.giphycontroller.model.ImagesMetadata;
import cbedoy.giphycontroller.model.ImagesSet;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class GiphyViewCell extends RecyclerView.Adapter {
    private List<CBGiphy> mImages;
    private Context mContext;
    private OnGIPHYViewCellListener mListener;

    public void setListener(OnGIPHYViewCellListener listener) {
        mListener = listener;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setImages(List<CBGiphy> images) {
        mImages = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giphy_cell, parent, false);
        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GifViewHolder viewHolder = (GifViewHolder) holder;

        CBGiphy cbGiphy = mImages.get(position);
        String fixedUrl = cbGiphy.getFixedUrl();

        viewHolder.resizeImageViewWithImage(cbGiphy);
        viewHolder.gifView.loadUrl(fixedUrl);

        viewHolder.holderView.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onSelectedGifItem(cbGiphy);
        });
        viewHolder.holderView.setOnLongClickListener(v -> {
            if (mListener != null)
                mListener.onLongPressedGifItem(cbGiphy);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    protected class GifViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gif_view_animated_view)
        WebView gifView;
        @BindView(R.id.gif_view_holder)
        View holderView;

        GifViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            gifView.setInitialScale(1);
            gifView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            gifView.setScrollbarFadingEnabled(false);
            gifView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            gifView.getSettings().setJavaScriptEnabled(true);
            gifView.getSettings().setLoadWithOverviewMode(true);
            gifView.getSettings().setUseWideViewPort(true);
            gifView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            gifView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            gifView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            gifView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            gifView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
        }

        private void resizeImageViewWithImage(CBGiphy giphy) {

            Display display = Utils.getInstance().screenSize(mContext);
            int width = display.getWidth();

            width /= 2;

            width -= Utils.getInstance().convertDpToPixel(8 * 2, mContext);

            int giphyWidth = giphy.getWidth();
            int giphyHeight = giphy.getHeight();

            int height = (giphyHeight * width) / giphyWidth;

            ViewGroup.LayoutParams layoutParams = gifView.getLayoutParams();
            layoutParams.height = height;

            gifView.setLayoutParams(layoutParams);
            holderView.setLayoutParams(layoutParams);

        }
    }



    public interface OnGIPHYViewCellListener {
        void onSelectedGifItem(CBGiphy image);

        void onLongPressedGifItem(CBGiphy image);
    }

}
