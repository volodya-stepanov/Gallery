package com.razrabotkin.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class PhotosActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String mFolderName;     //TODO: Здесь должно храниться название папки, которая открыта в данный момент.

    /**Курсор для доступа к результатам запроса изображений с SD-карты*/
    private Cursor cursor;

    /**Индекс колонки для ID-шников уменьешнных копий изображения*/
    private int columnIndex;
    private final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //Проверяем наличие разрешения на чтение данных с SD-карты
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        mFolderName = "Галерея";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //НАЧАЛО Загрузка изображений с SD-карты

                    // Создаём массив, содержащий одну запись - колонку ID уменьшенной копии изображения
                    String[] projection = {MediaStore.Images.Thumbnails._ID};

                    // Создаём курсор, соответвтующий SD-карте
                    cursor = managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                            projection,     // Какие колонки возвращать
                            null,
                            null,
                            MediaStore.Images.Thumbnails.IMAGE_ID);

                    // Получаем индекс колонки для ID уменьшенной копии изображения
                    columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

                    gridView = (GridView) findViewById(R.id.gridView);
                    gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
                    //gridView.setAdapter(gridAdapter);
                    gridView.setAdapter(new ImageAdapter(this));

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                            //Create intent
                            Intent intent = new Intent(PhotosActivity.this, DetailsActivity.class);
                            intent.putExtra("title", item.getTitle());
                            intent.putExtra("image", item.getImage());

                            //Start details activity
                            startActivity(intent);
                        }
                    });

                    //КОНЕЦ Загрузка изображений с SD-карты
                }
            }
        }
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));    //TODO: Падает вот здесь при попытке второго выполнения цикла
            imageItems.add(new ImageItem(bitmap, "Image#" + i, imgs.getResourceId(i, -1)));
        }
        return imageItems;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Получаем идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Выполняем обработку нажатия выбранного пункта меню
        switch (id){
            case R.id.action_slide_show:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_selection:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_view:
                // Не нужно добавлять обработку нажатия
                return true;
            case R.id.action_view_grid:
                //TODO: Реализовать логику
                Toast.makeText(this, "Ну типо изменили на таблицу", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                return true;
            case R.id.action_view_list:
                //TODO: Реализовать логику
                Toast.makeText(this, "Ну типо изменили на список", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                return true;
            case R.id.action_sorting_name:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_sorting_date:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_sorting_shooting_date:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_sorting_size:
                //TODO: Добавить обработку нажатия
                return true;
            case R.id.action_hide:
                showHideDialog();
                return true;
            case R.id.action_exclude:
                showExcludeDialog();
                return true;
            case R.id.action_rename:
                showRenameDialog();
                return true;
            case R.id.action_change_date:
                showChangeDateDialog();
                return true;
            case R.id.action_create_shortcut:
                Toast.makeText(this, "Ярлык для \"" + mFolderName + "\" создан", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Изменяет вид галереи на таблицу
     */
    private void setViewGrid() {

    }

    /**
     * Изменяет вид галереи на список
     */
    private void setViewList() {

    }

    /**
     * Показывает диалоговое окно со спиннером для исключения той или иной папки
     */
    private void showExcludeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Создаем экземпляр класса Spinner
        // Рассмотрен пример для диалога, поэтому в данном случае спиннер создается именно так
        final Spinner spinner = new Spinner(this);

        //TODO: Установить margin

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.folder_paths, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Устанавливаем адаптер спиннеру
        spinner.setAdapter(adapter);

        builder
                .setTitle("Исключить")
                .setMessage("Исключение папки исключит все вложенные папки. Для скрытия одноуровневых папок удобнее исключить родительскую папку:")
                .setView(spinner)
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
                                String selectedPath = spinner.getSelectedItem().toString();
                                Toast.makeText(getApplicationContext(), "Вы выбрали папку " + selectedPath, Toast.LENGTH_SHORT).show();   //TODO: Это сообщение здесь не нужно, реализовать логику
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

    /**
     * Показывает диалоговое окно для переименования папки
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
                                String newFolderName = input.getText().toString();
                                Toast.makeText(getApplicationContext(), "Папка " + mFolderName + " переименована в " + newFolderName, Toast.LENGTH_SHORT).show();   //TODO: Это сообщение здесь не нужно, реализовать логику
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Показывает диалоговое окно с предупреждением о скрытии изображения
     */
    private void showHideDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Скрыть")
                .setMessage("Эта команда спрячет все аудио и видеофайлы и изображения в папке и подпапках, создав в ней файл \".nomedia\". Чтобы скрыть эту папку только в QuickPick, используйте команду \"Исключить\".\n\nСкрыть все равно?") //TODO: В ресурс
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
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    class ImageAdapter extends BaseAdapter {

        private Context context;

        public ImageAdapter(Context localContext){
            context = localContext;
        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SquareImageView imageView;
            if (convertView == null) {
                imageView = new SquareImageView(context);

                // Перемещаем курсор на текущую позицию
                cursor.moveToPosition(position);

                // Получаем текущее значение запрошенной колонки
                int imageID = cursor.getInt(columnIndex);

                // Устанавливаем содержимое изображения на основе URI
                imageView.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));
            } else {
                imageView = (SquareImageView) convertView;
            }

            return imageView;
        }
    }
}
