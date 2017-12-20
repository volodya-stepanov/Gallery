package com.razrabotkin.android.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    Bitmap mBitmap;
    private String mImageName;     //TODO: Здесь должно храниться название картинки, которая открыта в данный момент.

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String title = getIntent().getStringExtra("title");
        int imageId = getIntent().getIntExtra("imageId", -1);
        //mBitmap = getIntent().getParcelableExtra("image");
        mBitmap = BitmapFactory.decodeResource(getResources(), imageId);

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(mBitmap);

        mImageName = "Картинка 1";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        //Находим пункт меню с объектом ShareActionProvider
        MenuItem shareItem = menu.findItem(R.id.action_share);

        // Получаем и сохраняем ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        if(mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
        
		MenuItem deleteItem = menu.findItem(R.id.action_delete);

		SpannableString s = new SpannableString("Удалить");
		s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
		deleteItem.setTitle(s);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Получаем идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Выполняем обработку нажатия выбранного пункта меню
        switch (id){
//            case R.id.action_share:
//                //TODO: Добавить обработку нажатия
//                return true;
            case R.id.action_delete:
                //TODO: Добавить обработку нажатия
                Toast.makeText(this, "Ну типо удалили фотку", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_slide_show:
                Toast.makeText(this, "Ну типо запустили слайд-шоу", Toast.LENGTH_SHORT).show();
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_copy:
                Toast.makeText(this, "Сдесь должна открыться вторая операция для авторизации", Toast.LENGTH_SHORT).show();
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_details:
                //TODO: Добавить обработку нажатия
                showDetailsDialog();
                return true;
//            case R.id.action_turn:
//                //TODO: Добавить обработку нажатия
//                return true;
            case R.id.action_edit:
                Toast.makeText(this, "Сдесь должна открыться отдельная операция для редактирования фотки", Toast.LENGTH_SHORT).show();
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_set_as:
                onSetAsClicked();
                return true;
            case R.id.action_move_to:
                openMainActivity();
                return true;
            case R.id.action_copy_to:
                return true;
            case R.id.action_rename:
                showRenameDialog();
                return true;
            case R.id.action_show_on_map:
                Toast.makeText(this, "Изображение не содержит геоданных!", Toast.LENGTH_SHORT).show();
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Сдесь должна открыться операция с настройками", Toast.LENGTH_SHORT).show();
                //TODO: Добавить обработку нажатия
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final TextView detailsGrid = new TextView(this);
        detailsGrid.setText("Вместо этого текста здесь будет таблица, в которой будут перечислены свойства изображения");

        builder
                .setTitle("1/1")
                .setView(detailsGrid)
                .setNeutralButton("Исправить дату",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showChangeDateDialog();
                                dialogInterface.cancel();
                            }
                        })
                .setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Открывает окно главной операции для выбора папки, куда переместить/копировать текущую картинку
     */
    private void openMainActivity() {
        //TODO: Всё это здесь не нужно, реализовать логику
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Выберите папку для перемещения или копирования", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Вызывается при выборе пункта "Установить как"
     */
    private void onSetAsClicked() {
        Intent intent = createSetAsIntent();
        startActivity(Intent.createChooser(intent, getText(R.string.app_name)));
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
        
		if(bitmapPath != null){
            Uri bitmapUri = Uri.parse(bitmapPath);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
		}

        return shareIntent;
    }

    /**
     * Создаёт интент для того, чтобы установить текущее изображение в качестве обоев, аватарки абонента и т.д.
     * @return Интент с текущим изображением, чтобы установить его в качестве чего-нибудь
     */
    public Intent createSetAsIntent() {
        Intent setAsIntent = new Intent(Intent.ACTION_ATTACH_DATA);
        setAsIntent.setType("image/jpeg");

        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);

        if(bitmapPath != null){
            Uri bitmapUri = Uri.parse(bitmapPath);
            setAsIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        }

        return setAsIntent;
    }

    /**
     * Показывает диалоговое окно для переименования изображения
     */
    private void showRenameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);

        builder
                .setTitle("Переименовать")
                .setView(input)
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                .setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newImageName = input.getText().toString();
                                Toast.makeText(getApplicationContext(), "Изображение " + mImageName + " переименовано в " + newImageName, Toast.LENGTH_SHORT).show();   //TODO: Это сообщение здесь не нужно, реализовать логику
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Показывает диалоговое окно с предупреждением об исправлении даты
     */
    private void showChangeDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Исправить дату")
                .setMessage("Попробуйте установить в свойствах файла дату создания на основе времени съёмки из EXIF для правильной сортировки.\n\nПродолжить исправление?") //TODO: В ресурс
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                .setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Ну типо исправили дату", Toast.LENGTH_SHORT).show();   //TODO: Это сообщение здесь не нужно, реализовать логику
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
