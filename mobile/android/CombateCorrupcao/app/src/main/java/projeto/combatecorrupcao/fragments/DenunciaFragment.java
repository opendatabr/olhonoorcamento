package projeto.combatecorrupcao.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

    private static final int REQUEST_PERMISSIONS_CODE = 123;
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
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.CAMERA;

        if ( ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(permissions,2);
            }
            Log.d(TAG,"erro de permissão");
            Toast.makeText(getActivity(), "Permita a aplicação utilizar camera.", Toast.LENGTH_LONG).show();
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }
        // Here, thisActivity is the current activity
     /*   if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        4);


            }
        }*/

       /* String[] permissions = new String[1];
        permissions[0] = Manifest.permission.CAMERA;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(), "Precisamos de sua permissão para que você utilize sua câmera para tirar foto.", Toast.LENGTH_LONG).show();
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                }
               // getActivity().requestPermissions(permissions,0);
            }
            Log.d(TAG, "erro de permissão");
            Toast.makeText(getActivity(), "Permita-nos utilizar a sua câmera.", Toast.LENGTH_LONG).show();
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }*/

    //}

   /* @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch( requestCode ){
            case 1:
                for( int i = 0; i < permissions.length; i++ ){

                    if( permissions[i].equalsIgnoreCase( Manifest.permission.CAMERA )
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED ){

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }*/
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
