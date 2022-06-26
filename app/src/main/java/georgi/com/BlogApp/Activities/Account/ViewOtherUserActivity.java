package georgi.com.BlogApp.Activities.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import georgi.com.BlogApp.Activities.Posts.CreateNewPostActivity;
import georgi.com.BlogApp.Activities.Posts.LatestPostsActivity;
import georgi.com.BlogApp.Activities.Posts.YourPostsActivity;
import georgi.com.BlogApp.Adapters.PostsAdapter;
import georgi.com.BlogApp.POJO.Post;
import georgi.com.BlogApp.R;
import georgi.com.BlogApp.Threads.Account.UserByUserUrl;
import georgi.com.BlogApp.Threads.Posts.PostsOnPage;
import georgi.com.BlogApp.Threads.Security.Logout;

import static georgi.com.BlogApp.Configs.ServerURLs.SERVER_URL;


public class ViewOtherUserActivity extends AppCompatActivity {

    private String className = this.getClass().getSimpleName();
    private String userUrl = null;

    private ProgressBar profilePicProgressBar;
    private ImageView profilePic;
    private TextView fullname, email;

    private List<Post> postList;
    private RecyclerView postsRecycler;
    private PostsAdapter postsAdapter;
    private GridLayoutManager gridLayout;

    private int page = 0;

    public boolean morePages = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_other_user);

        // Checks if this activity is started properly
        // (if the activity have extra String with name "userUrl").
        userUrl = getIntent().getStringExtra("userUrl");
        if(userUrl == null) finish();

        // Setting the toolbar and it title.
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_other_user_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Initializing empty post list
        postList = new ArrayList<>();

        postsRecycler = (RecyclerView) findViewById(R.id.view_other_user_recyclerView);

        // Setting the layoutManager to the postsRecycler.
        gridLayout = new GridLayoutManager(this, 1);
        postsRecycler.setLayoutManager(gridLayout);

        // Setting the adapter to the postsRecycler.
        postsAdapter = new PostsAdapter(this, postList);
        postsRecycler.setAdapter(postsAdapter);

        profilePicProgressBar = (ProgressBar) findViewById(R.id.view_other_user_image_progress_bar);

        profilePic = (ImageView) findViewById(R.id.view_other_user_image);

        fullname = (TextView) findViewById(R.id.view_other_user_fullName);
        email = (TextView) findViewById(R.id.view_other_user_email);

        // This thread is sending request to the server
        // with the above userUrl in order to get the user.
        UserByUserUrl userByUserUrl = new UserByUserUrl(this, profilePicProgressBar,
                profilePic, fullname, email);

        // Starting the thread with the userUrl as param.
        userByUserUrl.execute(userUrl);

        // Setting the latest 5 posts from server to the postsRecycler.
        getPostsOnPage();


        postsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                // If the last visible item position equals the list of posts size -1
                // update the postsRecycler with new 5 posts.
                if(gridLayout.findLastVisibleItemPosition() == postList.size() - 1) {
                    getPostsOnPage();
                }
            }
        });

    }

    // This method is starting thread that sends request
    // to the server for latest 5 posts by the userUrl of the user.
    // Clears all the old posts in the postList and adds the new ones.
    private void getPostsOnPage() {

        if(morePages) {
            PostsOnPage postsOnPage = new PostsOnPage(this, postsRecycler, className);
            postsOnPage.execute(SERVER_URL + "/" + userUrl + "/posts?page=" + page);

            page++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {

            case R.id.toolbar_latestPosts:
                intent = new Intent(this, LatestPostsActivity.class);
                break;

            case R.id.toolbar_createPost:
                intent = new Intent(this, CreateNewPostActivity.class);
                break;

            case R.id.toolbar_yourPosts:
                intent = new Intent(this, YourPostsActivity.class);
                break;

            case R.id.toolbar_account:
                intent = new Intent(this, AccountActivity.class);
                break;

            case R.id.toolbar_edit_account:
                intent = new Intent(this, EditAccountActivity.class);
                break;

            case R.id.toolbar_logout:
                Logout logout = new Logout(this);
                logout.execute();
                break;
        }

        if (intent != null) startActivity(intent);

        return super.onOptionsItemSelected(item);

    }
}
