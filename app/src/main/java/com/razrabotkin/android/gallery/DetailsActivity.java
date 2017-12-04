package com.razrabotkin.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    Bitmap mBitmap;

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String title = getIntent().getStringExtra("title");
        mBitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(mBitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        //Находим пункт меню с объектом ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Получаем и сохраняем ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if(mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Получаем идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Выполняем обработку нажатия выбранного пункта меню
        switch (id){
            case R.id.action_share:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_delete:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_slide_show:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_copy:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_details:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_turn:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_edit:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_set_as:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_move_to:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_copy_to:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_rename:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_show_on_map:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_settings:
                //TODO: Добавить обработку нажатия
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Создает интент для того, чтобы поделиться текущим изображением
     * @return Интент с текущим изображением, чтобы поделиться им
     */
    private Intent createShareIntent(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);   //TODO: Чем это заменить?
        shareIntent.setType("image/jpeg");

        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);

        return shareIntent;
    }
}
