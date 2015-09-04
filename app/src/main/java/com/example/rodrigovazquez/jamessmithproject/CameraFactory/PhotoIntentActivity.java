/**
 * @author Rodrigo Vazquez./Google Developer Document.
 * @version 1.0
 * @see com.example.rodrigovazquez.jamessmithproject.MainActivity
 * @see com.example.rodrigovazquez.jamessmithproject.WebService.HomeService
 */

package com.example.rodrigovazquez.jamessmithproject.CameraFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.rodrigovazquez.jamessmithproject.Activitys.CreateActivity;
import com.example.rodrigovazquez.jamessmithproject.Helpers.FontHelper;
import com.example.rodrigovazquez.jamessmithproject.R;
import com.example.rodrigovazquez.jamessmithproject.WebService.HomeService;


public class PhotoIntentActivity extends AppCompatActivity {


    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";

    private ImageView mImageView;
    private Bitmap mImageBitmap;

    //Path absoluto que almacena la imagen o Bitmap segun sea el caso.
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private HomeService service;
    Typeface typeface;


    /**
     * Iniciamos  el servcio.
     */
    public PhotoIntentActivity(){
        service = new HomeService(PhotoIntentActivity.this);
    }


    /**
     * Nombre asignado al album de fotografias dentro del dispositivo.
     *
     * @return nombre del album.
     */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    /**
     * Creamos el albumn para almacenar las fotografias.
     *
     * @return el directorio que almacenara las fotografias.
     */
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("HousePortal", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    /**
     * Creamos la imagen y la guardamos en el directorio siguiendo la nomenclatura de Prefijo_Fecha y contenido.
     *
     * @return la imagen guardada en el directorio temporal.
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    /**
     * Creamos la imagen y seteamos el path.
     *
     * @return
     * @throws IOException
     */
    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    /**
     *
     */
    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if(bitmap != null){
            Toast.makeText(PhotoIntentActivity.this,"Save successful!!",Toast.LENGTH_SHORT).show();
        }
		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);

    }

    /**
     *
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    /**
     * Guarda una imagen en tamaño normal.
     * @param actionCode entero para diferenciar el manejo de la imagen (Comprimida / No comprimida).
     */
    private void dispatchTakePictureIntent(int actionCode) {

        mCurrentPhotoPath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        }

        startActivityForResult(takePictureIntent, actionCode);
    }

    /**
     * Setea la imagen al ImageView de la vista.
     * @param intent
     */
    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        if(saveSmallImage()){
            Toast.makeText(PhotoIntentActivity.this,"Save successful!!",Toast.LENGTH_SHORT).show();
        }
        mImageView.setImageBitmap(mImageBitmap);
    }

    /**
     * Guardamos la imagen en la ruta segun el build de compilacion.
     * @return falso o verdadero dependiendo si se guardo o no.
     */
    public Boolean saveSmallImage(){

        File file = null;
        FileOutputStream fileOutputStream = null;
        try {

            file = getAlbumDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
            File newFile = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, file);

            mCurrentPhotoPath = newFile.getAbsolutePath();

            fileOutputStream = new FileOutputStream(newFile);
            mImageBitmap.compress(Bitmap.CompressFormat.PNG,100, fileOutputStream);
            fileOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Obtiene la imagen de la ruta absoluta seteada previamente.
     * @return bitmap consultado.
     */
    public Bitmap decodeImage(){

        Bitmap bitmap = null;
        if(!mCurrentPhotoPath.isEmpty()) {
            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        }
        return bitmap;
    }

    /**
     * Metodo llamado dentro de ActivityResult.
     */
    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
        }
    }

    /**
     * Evento principal del boton captura imagen.
     */
    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
                }
            };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        mImageView = (ImageView) findViewById(R.id.profile_image);

        mImageBitmap = null;
        typeface = FontHelper.getTypeFace(typeface,PhotoIntentActivity.this);

        Button picBtn = (Button) findViewById(R.id.btnIntend);
        picBtn.setTypeface(typeface);


        setBtnListenerOrDisable(
                picBtn,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );


        //Determinamos cual sera el directorio a utilizar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    /**
     * Retornamos la imagen previamente de la camara.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

            case ACTION_TAKE_PHOTO_S: {
                if (resultCode == RESULT_OK) {
                    handleSmallCameraPhoto(data);
                }
                break;
            } // ACTION_TAKE_PHOTO_S
        }
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_picture, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //
        if (id == R.id.action_camera_save) {
            Bitmap bitmap = decodeImage();
            if(bitmap != null){
                service.uploadBitMap(bitmap);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(
                savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ?
                        ImageView.VISIBLE : ImageView.INVISIBLE
        );
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @param action  The Intent action to check for availability.
     * @return True if an Intent with the specified action can be sent and
     * responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }

}