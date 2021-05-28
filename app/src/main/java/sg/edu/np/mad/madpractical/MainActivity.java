package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Main Activity";
    private User user;
    DBHandler mDBHandler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Main Activity Created");
        mDBHandler = new DBHandler(this, null);

        TextView name = findViewById(R.id.txtName);
        TextView lorem = findViewById(R.id.lorem);
        Button followBtn = findViewById(R.id.btnFollow);

        Intent intent = getIntent();

        if (intent != null) {
            int userIndex = intent.getIntExtra("id", 0);
            user = ListActivity.userList.get(userIndex);
            Log.v("User ", user.name);
            name.setText(user.name);
            lorem.setText(user.description);
            followBtn.setText(user.isFollowed() ? "Unfollow" : "Follow");
        }
        else {
            user = new User("hi", "hi", 1, false);
        }

        followBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                user.setFollowed(!user.isFollowed());
                mDBHandler.updateUser(user);

                followBtn.setText(user.isFollowed() ? "Unfollow" : "Follow");
                Toast toast = Toast.makeText(
                        MainActivity.this,
                        !user.isFollowed() ? "Unfollowed" : "Followed",
                        Toast.LENGTH_SHORT
                );
                toast.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Destroy");
    }
}