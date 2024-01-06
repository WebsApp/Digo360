package com.wapss.digo360.activity;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.google.android.gms.common.util.IOUtils.copyStream;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.wapss.digo360.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    CircleImageView iv_profile;
    private final int Camera_Req_Code = 100;
    File mediaFile;
    private String mediaPath;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    String mCurrentPhotoPath = "", image;
    private final int MY_CAMERA_REQUEST_CODE = 101;
    ImageView back,profile_faq;

    ImageView address_edite,college_edite,btn_camera,btn_edite;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        btn_edite = findViewById(R.id.btn_edite);
        btn_camera = findViewById(R.id.btn_camera);
        iv_profile = findViewById(R.id.iv_profile);
        profile_faq = findViewById(R.id.profile_faq);
        back = findViewById(R.id.back);
        college_edite = findViewById(R.id.college_edite);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        address_edite = findViewById(R.id.address_edite);
        address_edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, AddressPage.class));
            }
        });
        college_edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, CollegePage.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("page","my_profile");
//                Intent intent = new Intent(MyProfile.this,MainActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
                onBackPressed();
            }
        });

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                try {
                    // Creating file
                    mediaFile = null;
                    try {
                        mediaFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d(TAG, "Error occurred while creating the file");
                    }
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
                    // Copying
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    // callComplain(deviceToken, mediaFile);
                } catch (Exception e) {
                    Log.d(TAG, "onActivityResult: " + e.toString());
                }
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
        profile_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, HelpPage.class));
            }
        });
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermission()) {
                    takePictureCamera();
                }
            }
        });

    }

    private boolean checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MyProfile.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Camera_Req_Code) {
                assert data != null;
                Bitmap thumbnail = (Bitmap) (data.getExtras().get("data"));
                iv_profile.setImageBitmap(thumbnail);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), thumbnail, "val", null);
                Uri uri = Uri.parse(path);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Log.d("path", path);
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                iv_profile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
                iv_profile.setImageURI(uri);
                mediaFile = new File(mediaPath);
                Log.d("mediaPath", mediaPath);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No image Capture to upload", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ///takePictureCamera();
            takePictureCamera();
        } else {
            Toast.makeText(MyProfile.this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePictureCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, Camera_Req_Code);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}