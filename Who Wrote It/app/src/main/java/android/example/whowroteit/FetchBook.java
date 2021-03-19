package android.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class FetchBook extends AsyncTask<String, Void, String> { //<input parameter, progress gauge, result type>
    private TextView mTitleText;
    private TextView mAuthorText;

    public FetchBook(TextView mTitleText, TextView mAuthorText){
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
    }

    @Override
    protected String doInBackground(String... params) {
        return NetworkUtils.getBookInfo(params[0]);
    }

    //The onPostExecute() method takes a String as a parameter and returns void .
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Use the built-in Java JSON classes ( JSONObject and JSONArray )
        //to obtain the JSON array of results items
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            String title = null;
            String authors = null;
            int i = 0;

            //iterate itemsArray
            for (i=0; i < itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                }catch (Exception e){
                    e.printStackTrace();
                }
                i++; //move to next item
                //If both a title and author exist, update the TextViews and return
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                }
            }
            mTitleText.setText("No Result Found");
            mAuthorText.setText("");

        }catch (Exception e){
            mTitleText.setText("No Result Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

}
