package com.razrabotkin.android.gallery;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
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

}
