package projeto.combatecorrupcao.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.api.GoogleApiClient;

import projeto.combatecorrupcao.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DenunciaFragment extends Fragment implements  View.OnClickListener{

    EditText nome;
    EditText email;
    EditText Descricao;
    Button enviar;
    FloatingActionButton fbCamera;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final String TAG = "storeFile";
    ImageView imagePhoto;
    GoogleApiClient mGoogleApiClient;
    Bitmap imageBitmap=null;
    private int RESULT_GALERIA=12;
    private FloatingActionMenu fab;

    public DenunciaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_denuncia, container, false);

        email = (EditText) v.findViewById(R.id.email);
        Descricao = (EditText) v.findViewById(R.id.descricao);
        enviar = (Button) v.findViewById(R.id.buttEnviar);
        imagePhoto = (ImageView) v.findViewById(R.id.photo);
        imagePhoto.setVisibility(View.INVISIBLE);

      /*  Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        email.startAnimation(animation);
        */

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Toast.makeText(getActivity(), "\"Enviada com sucesso!\"", Toast.LENGTH_SHORT).show();
            }
        });
    /*    FloatingActionButton fbCamera = (FloatingActionButton) v.findViewById(R.id.camera);
        fbCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        fab = (FloatingActionMenu)v.findViewById(R.id.fabmais);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
              //  Toast.makeText(getActivity(), "Is menu opened? " + (b ? "true" : "false"), Toast.LENGTH_SHORT).show();
            }
        });
        fab.showMenuButton(true);
        fab.setClosedOnTouchOutside(true);

        com.github.clans.fab.FloatingActionButton fab1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab1);
        com.github.clans.fab.FloatingActionButton fab2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fab2);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        return v;
    }
    public void enviarDenuncia(String emai,String descString){
        if(imageBitmap==null){
            //requisição post sem bitmap
            // se sucesso  clearCampos();
        }else{
            //requisição post com bitmap
            // se sucesso  clearCampos();
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();

            imageBitmap = (Bitmap) extras.get("data");
            imagePhoto.setVisibility(View.VISIBLE);
            imagePhoto.setImageBitmap(imageBitmap);

        }else if(requestCode == RESULT_GALERIA && resultCode == getActivity().RESULT_OK){
            Uri imgUri = data.getData();
            String[] colArq = {MediaStore.Images.Media.DATA};
            Cursor curs = getActivity().getContentResolver().query(imgUri, colArq, null, null, null);
            curs.moveToFirst();
            int colIndex = curs.getColumnIndex(colArq[0]);
            String picpath = curs.getString(colIndex);
            Bitmap foto = BitmapFactory.decodeFile(picpath.toString());
            if(foto!=null){
                imagePhoto.setVisibility(View.VISIBLE);
                imagePhoto.setImageBitmap(foto);

            }

        }
    }
    public void clearCampos(){
        email.setText("");
        Descricao.setText("");
        imagePhoto.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onClick(View v) {
        String aux = "";

        switch( v.getId() ){
            case R.id.fab1:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GALERIA);// Intent intent = new Intent();
                break;
            case R.id.fab2:
                dispatchTakePictureIntent();
               // aux = "Fab 2 clicked";
                break;

        }

      //  Toast.makeText(getActivity(), aux, Toast.LENGTH_SHORT).show();
    }

}
