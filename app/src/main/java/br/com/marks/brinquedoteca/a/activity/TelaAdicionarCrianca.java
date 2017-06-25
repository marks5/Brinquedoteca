package br.com.marks.brinquedoteca.a.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import br.com.marks.brinquedoteca.R;
import br.com.marks.brinquedoteca.a.model.Crianca;
import de.hdodenhof.circleimageview.CircleImageView;

public class TelaAdicionarCrianca extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();

    private EditText edt_nome_crianca;
    private EditText edt_idade_crianca;
    private EditText edt_identidade_crianca;
    private EditText edt_nome_responsavel_crianca;

    private Button btn_add_crianca;

    private Crianca crianca;
    Crianca criancaUpd = new Crianca();

    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_crianca);
        initSetup();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://brinquedoteca-d1d94.appspot.com/");

        try {
            Bundle bundle = getIntent().getExtras();
            crianca = bundle.getParcelable("model");
            montarCrianca(crianca);
        }catch (Exception e){
            Log.i("naohacrianca",e.getMessage());
        }

        salvarCrianca();
    }

    private void initSetup(){
        edt_nome_crianca = (EditText) findViewById(R.id.edt_nome_crianca);
        edt_idade_crianca = (EditText) findViewById(R.id.edt_idade_crianca);
        edt_identidade_crianca = (EditText) findViewById(R.id.edt_identidade_crianca);
        edt_nome_responsavel_crianca = (EditText) findViewById(R.id.edt_nome_responsavel_crianca);
        circleImageView = (CircleImageView) findViewById(R.id.civ_crianca);
        btn_add_crianca = (Button) findViewById(R.id.btn_add_crianca);
    }

    private void montarCrianca(Crianca crianca){
        btn_add_crianca.setText("ATUALIZAR CRIANÇA");
        Picasso.with(TelaAdicionarCrianca.this).load(crianca.getUrlImagem()).into(circleImageView);
        edt_idade_crianca.setText(String.format("%s",crianca.getIdade()));
        edt_nome_crianca.setText(String.format("%s",crianca.getNome()));
        edt_identidade_crianca.setText(String.format("%s",crianca.getIdentidade()));
        edt_nome_responsavel_crianca.setText(String.format("%s",crianca.getNomeResponsavel()));
        circleImageView.setAlpha(1);
    }

    private void salvarCrianca(){
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_add_crianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criancaUpd.idade = Integer.parseInt(edt_idade_crianca.getText().toString());
                criancaUpd.nome = edt_idade_crianca.getText().toString();
                criancaUpd.nomeResponsavel = edt_nome_responsavel_crianca.getText().toString();
                criancaUpd.identidade = edt_identidade_crianca.getText().toString();
            }
        });
    }

    private void tirarFoto() {
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "farmaliv_profile" + new Date().getTime() + ".png");
        Uri uri = Uri.fromFile(file);
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = null;
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Picasso.with(TelaAdicionarCrianca.this).load(data.toURI()).into(circleImageView);
                    user.Imagem_URL = uri.toString();
                    updateUser(user.Imagem_URL);
                }
                break;
        }
    }


}
