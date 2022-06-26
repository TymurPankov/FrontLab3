package georgi.com.BlogApp.Threads.Posts;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import georgi.com.BlogApp.Activities.Posts.PostActivity;
import georgi.com.BlogApp.Helpers.HttpRequest;
import georgi.com.BlogApp.Helpers.PreferencesHelper;
import georgi.com.BlogApp.POJO.ErrorHandler;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static georgi.com.BlogApp.Configs.ServerURLs.DELETE_COMMENT_URL;

public class DeleteComment extends AsyncTask<Void, Void, ErrorHandler> {

    private Context context;

    // That is the id of post from who the comment is.
    private Long postId;

    private Long commentId;

    public DeleteComment(Context context, Long commentId, Long postId) {
        this.context = context;
        this.commentId = commentId;
        this.postId = postId;
    }

    @Override
    protected ErrorHandler doInBackground(Void... voids) {

        try {

            // Creating the request.
            HttpRequest httpRequest = new HttpRequest(DELETE_COMMENT_URL,
                    new PreferencesHelper(context).getCookie(), "POST");

            // longs[0] is the id for the comment to delete.
            httpRequest.addStringField("commentId", "" + commentId);

            // Sending the request and getting the response.
            String response = httpRequest.sendTheRequest();

            Gson gson = new Gson();
            // Converting the JSON response to ErrorHandler.
            return gson.fromJson(response, ErrorHandler.class);

        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ErrorHandler errorHandler) {

        if(errorHandler.getError()) {
            Log.e("Error", errorHandler.getError_msg());
        }

        else {
            ((PostActivity) context).deleteCommentById(commentId);
        }
    }
}
