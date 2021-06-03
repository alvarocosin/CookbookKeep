package com.example.cookbookkeep.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.cookbookkeep.fragments.ListaFragment;
import com.example.cookbookkeep.fragments.NuevaRecetaFragment;
import com.example.cookbookkeep.fragments.PerfilFragment;
import com.example.cookbookkeep.R;
import com.example.cookbookkeep.fragments.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new RecyclerViewFragment())
                .commit();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itemAnadir:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new NuevaRecetaFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.itemPerfil:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new PerfilFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.itemLista:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new ListaFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}