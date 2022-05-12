package com.example.photoeditingapp_main.Activity_Mainpage;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.AlbumItem;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studio_album_item_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studio_album_item_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studio_album_item_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studio_album_item_page.
     */
    // TODO: Rename and change types and number of parameters
    public static studio_album_item_page newInstance(String param1, String param2) {
        studio_album_item_page fragment = new studio_album_item_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_studio_album_item_page, container, false);
    }
    TextView albumName;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get Album item send from my album fragment
        AlbumItem albumItem= (AlbumItem) getArguments().getSerializable("album");

        // Set album name
        albumName=view.findViewById(R.id.album_title);
        albumName.setText(albumItem.getAlbumName());

        // Set button up
        ImageButton imageButtonBack= view.findViewById(R.id.back_image_button_album_page);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_studio_album_item_page_to_studio_page);
            }
        });

        // Set List image from albumItem
        List<Integer> imageList= new ArrayList<Integer>() {
        };
        for (   int i=0;i<albumItem.getAlbumImages().size();i++){
                imageList.add(albumItem.getAlbumImages().get(i).getImage());
        }

        // Set grid view
        ExpandableGridView gridView=view.findViewById(R.id.grid_view_album_item_page);
        gridView.setAdapter(new _adapter_gridview_account_page(getContext(),imageList));
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle= new Bundle();
                bundle.putInt("clicked_photo", (Integer) parent.getAdapter().getItem(position)); // Send image clicked to photo page
                bundle.putSerializable("album",albumItem);  // Send album item to photo page
                Navigation.findNavController(view).navigate(R.id.action_studio_album_item_page_to_studio_photo,bundle); // Navigate to photo page
            }
        });*/



        // Set button more
        ImageButton imageButtonMore=view.findViewById(R.id.more_button_album_page);
        imageButtonMore.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.more_button_menu,popupMenu.getMenu());

                if(gridView.getNumColumns()==1){
                    popupMenu.getMenu().findItem(R.id.zoom_in).setVisible(false);
                }
                else if(gridView.getNumColumns()==5){
                    popupMenu.getMenu().findItem(R.id.zoom_out).setVisible(false);
                }
                else {
                    popupMenu.getMenu().findItem(R.id.zoom_in).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.zoom_out).setVisible(true);
                }
                // Set icon for popup menu
                popupMenu.setForceShowIcon(true);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.zoom_in:
                                gridView.setNumColumns(gridView.getNumColumns()-1);
                                break;
                            case R.id.zoom_out:
                                gridView.setNumColumns(gridView.getNumColumns()+1);
                                break;
                            case R.id.rename_album:
                                showPopupRenameAlbum(Gravity.CENTER,albumItem);
                                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                            default:
                                break;
                        }
                                return true;
                    }
                });
            }
        });


    }

    private void showPopupRenameAlbum(int gravity, AlbumItem albumItem) {
        // Create an instance of the dialog fragment and show it
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set the layout for the dialog
        dialog.setContentView(R.layout._dialog_rename_album);

        Window window = dialog.getWindow();
        /*if (window != null) {
            return;
        }*/

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Set the dialog gravity
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER==gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        EditText editText=dialog.findViewById(R.id.editTextRenameAlbum);
        editText.setText(albumItem.getAlbumName());
        TextView textViewSave=dialog.findViewById(R.id.textViewRenameAlbumSave);
        TextView textViewCancel=dialog.findViewById(R.id.textViewRenameAlbumCancel);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.getText().toString().isEmpty()){
                    textViewSave.setClickable(false);
                    textViewSave.setTextColor(getResources().getColor(R.color.app_light_gray));
                }
                else {
                    textViewSave.setClickable(true);
                    textViewSave.setTextColor(getResources().getColor(R.color.app_teal_accent_dark));
                }
            }
        });

        textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumItem.setAlbumName(editText.getText().toString());
                albumName.setText(albumItem.getAlbumName());
                dialog.dismiss();
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}