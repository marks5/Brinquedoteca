package br.com.marks.brinquedoteca.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.marks.brinquedoteca.R;

public class PrincipalListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        context = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.principal_detail_container) != null) {
            mTwoPane = true;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_inicio:
                        if (mTwoPane) {
                            PrincipalDetailFragment fragment = new PrincipalDetailFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, PrincipalDetailActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.menu_perfil:
                        if (mTwoPane) {
                            PrincipalDetailFragment fragment = new PrincipalDetailFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, PrincipalDetailActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                }
            return false;
            }
        });

    }


}
