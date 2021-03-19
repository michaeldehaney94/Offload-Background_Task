package android.example.whowroteit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    // Member variable that stores the search string.
    private String mQueryString;

    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        this.mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }

    //onLoading
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


}
