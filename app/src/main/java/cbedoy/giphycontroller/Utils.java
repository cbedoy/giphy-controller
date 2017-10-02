package cbedoy.giphycontroller;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GiphyController
 * <p>
 * Created by bedoy on 10/1/17.
 */

public class Utils
{
    private static Utils instance;

    private static final long SECOND_MILLIS = 1000;
    private static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final long MONT_MILLIS = 30 * DAY_MILLIS;
    private static final long YEARS_MILLIS = 12 * MONT_MILLIS;

    private final String EMOJI_REGEX = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";

    private boolean espressoTesting;

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();
        return instance;
    }

    public String extractURL(String input)
    {
        if (input.length() == 0)
            return null;

        int[] positionsOfUrl = positionsOfUrl(input);

        return input.substring(positionsOfUrl[0], positionsOfUrl[1]);
    }

    public int[] positionsOfUrl(String input)
    {
        LinkExtractor linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
                .build();
        Iterable<LinkSpan> links = linkExtractor.extractLinks(input);
        if (links.iterator().hasNext())
        {
            LinkSpan link = links.iterator().next();
            link.getType();        // LinkType.URL
            link.getBeginIndex();  // 17
            link.getEndIndex();

            return new int[]{link.getBeginIndex(), link.getEndIndex()};
        }
        else
            return new int[]{0, 0};
    }



    public boolean isJUnitTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);
        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public boolean isEspressoTesting(){
        return this.espressoTesting;
    }

    public void setEspressoTesting(boolean b){
        this.espressoTesting = b;
    }

    public int convertDpToPixel(int dp, Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }

    public int convertPixelToDp(int px, Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((px/displayMetrics.density)+0.5);
    }

    public Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }


    public String getTimeAgo(long time)
    {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();

        if (time > now){
            getTimeAgo(time / 10);
        }

        if (time <= 0) {
            return "";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a min ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " min ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else if (diff < 30 * DAY_MILLIS){
            return diff / DAY_MILLIS + " days ago";
        }else {
            return diff / MONT_MILLIS + " months ago";
        }
    }


    public String[] listToArray(List<String> list){
        String[] array = new String[list.size()];
        int index = 0;
        for (String element : list)
            array[index++] = element;

        return array;
    }

    public String getRootDomainURL(String url)
    {
        String extractURL = extractURL(url);
        if (extractURL != null && extractURL.length() > 0) {
            String[] domainKeys = url.split("/")[2].split("\\.");
            int length = domainKeys.length;
            int dummy = domainKeys[0].equals("www") ? 1 : 0;
            if (length - dummy == 2)
                return domainKeys[length - 2] + "." + domainKeys[length - 1];
            else {
                if (domainKeys[length - 1].length() == 2) {
                    return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
                } else {
                    return domainKeys[length - 2] + "." + domainKeys[length - 1];
                }
            }
        }else {
            return "";
        }
    }

    public Display screenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public int emojiCount(String text){
        Matcher matchEmo = Pattern.compile(EMOJI_REGEX).matcher(text);
        int count = 0;
        while (matchEmo.find()) {
            Log.d("emojiCount", matchEmo.group());
            count++;
        }
        return count;
    }
}
