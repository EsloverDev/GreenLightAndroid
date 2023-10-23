package com.example.greenlightproject.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenlightproject.casosDeUso.PerfilesCasosDeUso;
import com.example.greenlightproject.modelo.Perfil;
import com.example.greenlightproject.repository.AdminSQLiteOpenHelper;
import com.example.greenlightproject.repository.perfilesRepository;
import com.google.android.material.snackbar.Snackbar;

import com.example.greenlightproject.R;
import com.example.greenlightproject.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button btnVerProd;
    private EditText et_nombre, et_password, et_email, et_telefono, et_pais, et_ciudad, et_localidad, et_documento;
    private ActionBar actionBar;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnVerProd = view.findViewById(R.id.btnSQL);
        ListView lv = view.findViewById(R.id.listViewData);
        if(btnVerProd!=null) {
            btnVerProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Desea ver los perfiles guardados?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Si",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });
        }

        refreshList();

        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.savefreepik);

    }

    //El siguiente método recupera información de la base de datos SQLite y la devuelve en una lista
    List<String> getDataUsersSQL() {
        // Se crea una lista llamada "array" e inicializada como nula. Esta lista se utilizará para
        // almacenar los datos recuperados de la base de datos.
        List<String> array = null;
        //Se crea una instancia de AdminSQLiteOpenHelper, que es una clase que extiende SQLiteOpenHelper
        // y se utiliza para gestionar la base de datos SQLite. El constructor de esta clase recibe un
        // contexto (en este caso, el contexto actual).
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(requireContext());

        //Se obtiene una instancia de base de datos de solo lectura (SQLiteDatabase) utilizando el método
        // getReadableDatabase() de la instancia dbHelper. Esto abre o crea la base de datos si es necesario.
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        //Se verifica si la instancia de la base de datos es diferente de nula, lo que indica que la
        // base de datos se abrió correctamente
        if (dbRead !=null) {
            //Se ejecuta una consulta SQL en la base de datos utilizando el método rawQuery. La consulta
            // selecciona varios campos de la tabla "perfiles" en la base de datos. El resultado se
            // almacena en un objeto Cursor llamado "c"
            Cursor c = dbRead.rawQuery("SELECT nombre, email, telefono, pais, ciudad, localidad FROM perfiles", null);
            //Se verifica si el cursor tiene datos. Si es así, se inicia un bucle que recorre los resultados
            if (c.moveToFirst()) {
                //Se crea una instancia de ArrayList<String> para almacenar los datos recuperados de
                // la base de datos
                array = new ArrayList<>();
                //Se recorren los resultados del cursor utilizando un bucle do-while. Para cada fila
                // de datos, se extraen los valores de las columnas y se concatenan en una cadena llamada
                // "columnRes". Luego, esta cadena se agrega a la lista "array" y se muestra en la consola
                // con System.out.println()
                do {
                    String columnRes ="Nombre: " + c.getString(0) + " E-mail: " + c.getString(1) + " Teléfono: " + c.getString(2) + " País: " + c.getString(3) + " Ciudad: " + c.getString(4) + " Localidad: " + c.getString(5);
                    array.add(columnRes);
                    System.out.println("get data usuario: " + columnRes);
                } while (c.moveToNext());
            }
            //Se cierra el cursor después de haber recorrido todos los resultados
            c.close();
            //Se cierra la base de datos después de haber recuperado los datos
            dbRead.close();
        }
        //Finalmente, la lista "array" que contiene los datos recuperados de la base de datos se devuelve
        // como resultado de la función.
        return array;
    }

    private void setListData(List<String> your_array_list) {
        ListView lv = (ListView) root.findViewById(R.id.listViewData);

        if (your_array_list == null) {
            your_array_list = new ArrayList<>();
            lv.setEmptyView(root.findViewById(android.R.id.empty));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                root.getContext(),
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);
    }

    void refreshList(){
        List<String> your_array_list = getDataUsersSQL();
        setListData(your_array_list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favoriteId:
                Toast.makeText(getContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.shareId:
                Toast.makeText(getContext(), "Compartiendo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.find:
                Toast.makeText(getContext(), "Buscando", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
