package georgi.com.BlogApp.Activities.Posts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import georgi.com.BlogApp.Activities.Account.AccountActivity;
import georgi.com.BlogApp.Activities.Account.EditAccountActivity;
import georgi.com.BlogApp.R;
import georgi.com.BlogApp.Threads.Posts.PostById;
import georgi.com.BlogApp.Threads.Posts.EditPost;
import georgi.com.BlogApp.Threads.Security.Logout;


public class EditPostActivity extends AppCompatActivity {

    private ProgressBar postImageProgressBar;
    private ImageView postImage;
    private EditText title, description;
    private TextView date;

    private Button changeImage, editPostBut;

    // This variable is used to save the current post's id.
    private Long id;

    // Selected image uri is saved here.
    private Uri fileLocation;

    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_post);

        // Getting the id from intent extras.
        id = getIntent().getLongExtra("id", -1);
        if(id == -1) finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_post_toolbar);
        toolbar.setTitle("Edit Your Post");
        setSupportActionBar(toolbar);

        postImageProgressBar = (ProgressBar) findViewById(R.id.edit_post_image_progress_bar);
        postImage = (ImageView) findViewById(R.id.edit_post_image);
        title = (EditText) findViewById(R.id.edit_post_title);
        description = (EditText) findViewById(R.id.edit_post_desc);
        date = (TextView) findViewById(R.id.edit_post_date);
        changeImage = (Button) findViewById(R.id.edit_post_changeImageBut);
        editPostBut = (Button) findViewById(R.id.edit_post_button);

        // Thread to get the post details from the server.
        PostById postById = new PostById(this, date, title, description, postImageProgressBar, postImage);
        postById.execute(id);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Starting new application for getting image from the phone.
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, 1);
            }
        });

        editPostBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Sending the updated post to the server.
                EditPost editPost = new EditPost(context, fileLocation);
                editPost.execute("" + id, title.getText().toString(), description.getText().toString());
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK) {

            // Handling the image that is selected.

            // Setting the image uri.
            fileLocation = data.getData();

            // Changing current image with the selected.
            Glide.with(this)
                    .load(fileLocation)
                    .override(600, 600)
                    .into(postImage);
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
