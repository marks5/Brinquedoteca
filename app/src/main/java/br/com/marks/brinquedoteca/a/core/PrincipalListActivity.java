package br.com.marks.brinquedoteca.a.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.activity.TelaContatoActivity;
import br.com.marks.brinquedoteca.a.activity.TelaCriancasActivity;
import br.com.marks.brinquedoteca.a.activity.TelaInicialActivity;
import br.com.marks.brinquedoteca.a.activity.TelaPerfilActivity;
import br.com.marks.brinquedoteca.a.activity.TelaSobreActivity;
import br.com.marks.brinquedoteca.a.fragment.TelaContatoFragment;
import br.com.marks.brinquedoteca.a.fragment.TelaCriancasFragment;
import br.com.marks.brinquedoteca.a.fragment.TelaInicialFragment;
import br.com.marks.brinquedoteca.a.fragment.TelaPerfilFragment;
import br.com.marks.brinquedoteca.a.fragment.TelaSobreFragment;

public class PrincipalListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Context context;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView tv_usuario;
    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(toolbar!=null) {
            toolbar.setTitle(getTitle());
            toolbar.setNavigationIcon(R.drawable.ic_dehaze);
        }



        context = getApplicationContext();

        if (findViewById(R.id.principal_detail_container) != null) {
            mTwoPane = true;
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.menu_inicio:
                        if (mTwoPane) {
                            TelaInicialFragment fragment = new TelaInicialFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, TelaInicialActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.menu_perfil:
                        if (mTwoPane) {
                            TelaPerfilFragment fragment = new TelaPerfilFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, TelaPerfilActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.menu_criancas:
                        if (mTwoPane) {
                            TelaCriancasFragment fragment = new TelaCriancasFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, TelaCriancasActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.contato:
                        if (mTwoPane) {
                            TelaContatoFragment fragment = new TelaContatoFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, TelaContatoActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                    case R.id.sobre:
                        if (mTwoPane) {
                            TelaSobreFragment fragment = new TelaSobreFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.principal_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, TelaSobreActivity.class);
                            context.startActivity(intent);
                        }
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
            return false;
            }
        });

        loadData();

    }

    private void loadData(){
        tv_usuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_usuario);
        tv_logout = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_logout);

        String usuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        tv_usuario.setText(usuario);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                telaLogin();
            }
        });
    }

    private void telaLogin(){
        finish();
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }


}
