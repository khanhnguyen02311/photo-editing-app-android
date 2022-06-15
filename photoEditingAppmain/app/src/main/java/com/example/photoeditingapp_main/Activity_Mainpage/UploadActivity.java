package com.example.photoeditingapp_main.Activity_Mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    _GlobalVariables gv;

    TextView nameText, infoText;
    ImageButton cancelBtn;
    Button uploadBtn;
    ImageView avatarImage, previewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        gv = (_GlobalVariables) getApplication();

        nameText = findViewById(R.id.name_box);
        infoText = findViewById(R.id.info_box);
        avatarImage = findViewById(R.id.avatar_box);
        previewImage = findViewById(R.id.image_preview);
        cancelBtn = findViewById(R.id.cancel_btn);
        uploadBtn = findViewById(R.id.upload_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameText.setText(bundle.getString("name"));
            Glide.with(this).load(Uri.parse(bundle.getString("image_uri"))).placeholder(R.drawable.stewdioplaceholder).into(previewImage);
            if (bundle.getString("avatar_uri").equals(""))
                Glide.with(this).load(R.drawable.stewdioplaceholder).into(avatarImage);
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("Loading");
                pd.show();

                gv.getFirestoreDB().collection("users").whereEqualTo("usr", gv.getLocalDB().getActiveUser().get(0)).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot snapshot) {
                                if (!snapshot.isEmpty()) {
                                    assert bundle != null;
                                    String name = "";
                                    Uri uri = Uri.parse(bundle.getString("image_uri"));
                                    Log.i("URI", uri.toString());

                                    String scheme = uri.getScheme();
                                    if (scheme.equals("file")) {
                                        name = uri.getLastPathSegment();
                                    } else if (scheme.equals("content")) {
                                        String[] proj = { MediaStore.Images.Media.TITLE };
                                        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                                        if (cursor != null && cursor.getCount() != 0) {
                                            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
                                            cursor.moveToFirst();
                                            name = cursor.getString(columnIndex);
                                        }
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                    }

                                    StorageReference rf = gv.getStorageDB().getReference().child("/USERIMAGES/"+gv.getLocalDB().getActiveUser().get(0)+Calendar.getInstance().getTimeInMillis());
                                    rf.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Log.i("TASKSNAPSHOT", taskSnapshot.getMetadata().toString());

                                            Map<String, Object> imageItem = new HashMap<>();
                                            imageItem.put("usr", gv.getLocalDB().getActiveUser().get(0));
                                            imageItem.put("image_uri", rf.getDownloadUrl().toString());
                                            imageItem.put("info", infoText.getText().toString());
                                            imageItem.put("timeadded", new Timestamp(Calendar.getInstance().getTime()));
                                            imageItem.put("like_amount", 0);

                                            gv.getFirestoreDB().collection("images").add(imageItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("NEWIMAGE", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    pd.dismiss();
                                                    final Handler handler = new Handler();
                                                    onBackPressed();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("NEWIMAGE", "Error adding document", e);
                                                    pd.dismiss();
                                                    e.printStackTrace();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            e.printStackTrace();
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
}