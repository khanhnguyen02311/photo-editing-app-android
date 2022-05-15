package com.example.photoeditingapp_main.Activity_Mainpage;

import static java.lang.Thread.sleep;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.AlbumItem;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;
import com.example.photoeditingapp_main._Classes.SquareImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;

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
    MaterialButton DeselectButton;
    LinearLayout LinearLayoutTitle;
    ImageButton imageButtonBack;
    boolean isFirstSetUp = true;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get Album item send from my album fragment
        AlbumItem albumItem = (AlbumItem) getArguments().getSerializable("album");

        // Set relative layout title
        LinearLayoutTitle = view.findViewById(R.id.studio_album_item_page_title_layout);

        // Set album name
        albumName = view.findViewById(R.id.album_title);
        albumName.setText(albumItem.getAlbumName());

        // Set button up
        imageButtonBack = view.findViewById(R.id.back_image_button_album_page);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_studio_album_item_page_to_studio_page);
            }
        });

        // Set button deselect
        DeselectButton = view.findViewById(R.id.deselect_button_album_page);

        // Set button more
        ImageButton imageButtonMore = view.findViewById(R.id.more_button_album_page);

        // Set grid view
        ExpandableGridView gridView = view.findViewById(R.id.grid_view_album_item_page);
        _adapter_gridview_account_page adapter = new _adapter_gridview_account_page(getContext(), albumItem.getAlbumImages());
        gridView.setAdapter(adapter);

        // Item click in grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MaterialCardView cardView = view.findViewById(R.id.cardView_custom_item_gridview_account_page);
                SquareImageView squareImageView = view.findViewById(R.id.imageView_custom_item_gridview_account_page);
                _adapter_gridview_account_page adapter1 = ((_adapter_gridview_account_page) adapterView.getAdapter());

                // Set animation for image selected
                if (!adapter1.getSelected(i)) {
                    startAnimation(squareImageView, cardView);
                    adapter1.setSelected(i, true);
                } else {
                    endAnimation(squareImageView, cardView);
                    adapter1.setSelected(i, false);
                }
                if (adapter1.getPositionSelectedItems().size() != 0) {
                    if(isFirstSetUp) {
                    LinearLayoutTitle.setVisibility(View.INVISIBLE);
                    imageButtonMore.setVisibility(View.INVISIBLE);
                    DeselectButton.setVisibility(View.VISIBLE);
                    isFirstSetUp = false;
                    showBottomNavSelectImage();
                    }
                }
                else {
                LinearLayoutTitle.setVisibility(View.VISIBLE);
                imageButtonMore.setVisibility(View.VISIBLE);
                DeselectButton.setVisibility(View.INVISIBLE);
                isFirstSetUp = true;
                showBottomNavMainPage();
                }
            }
        });

        // Set button more
        imageButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.more_button_menu, popupMenu.getMenu());

                if (gridView.getNumColumns() == 1) {
                    popupMenu.getMenu().findItem(R.id.zoom_in).setVisible(false);
                } else if (gridView.getNumColumns() == 5) {
                    popupMenu.getMenu().findItem(R.id.zoom_out).setVisible(false);
                } else {
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
                                gridView.setNumColumns(gridView.getNumColumns() - 1);
                                break;
                            case R.id.zoom_out:
                                gridView.setNumColumns(gridView.getNumColumns() + 1);
                                break;
                            case R.id.rename_album:
                                showPopupRenameAlbum(Gravity.CENTER, albumItem);
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });

        // Set button deselect
        DeselectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutTitle.setVisibility(View.VISIBLE);
                imageButtonMore.setVisibility(View.VISIBLE);
                DeselectButton.setVisibility(View.INVISIBLE);

                List<Integer> positionSelectedItems = adapter.getPositionSelectedItems();
                for (int i = 0; i < positionSelectedItems.size(); i++) {
                    View view=gridView.getChildAt(positionSelectedItems.get(i));
                    if (view != null) {
                        endAnimation(view.findViewById(R.id.imageView_custom_item_gridview_account_page), view.findViewById(R.id.cardView_custom_item_gridview_account_page));
                    }
                }
                adapter.clearSelected();
                isFirstSetUp = true;
                showBottomNavMainPage();
            }
        });
    }

    private void showBottomNavSelectImage() {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_mainpage);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setVisibility(View.GONE);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_mainpage_selected_image_menu);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void showBottomNavMainPage() {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_mainpage);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setVisibility(View.GONE);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_mainpage_menu);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private void startAnimation(SquareImageView squareImageView, MaterialCardView cardView) {
        squareImageView.setCornerRadius(32);
        ObjectAnimator animator = ObjectAnimator.ofArgb(cardView, "strokeColor", Color.parseColor("#646464"));
        animator.setDuration(200);
        animator.start();
    }

    private void endAnimation(SquareImageView squareImageView, MaterialCardView cardView) {
        squareImageView.setCornerRadius(0);
        ObjectAnimator animator = ObjectAnimator.ofArgb(cardView, "strokeColor", Color.parseColor("#00646464"));
        animator.setDuration(200);
        animator.start();
    }

    // Show popup rename album
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
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText editText = dialog.findViewById(R.id.editTextRenameAlbum);
        editText.setText(albumItem.getAlbumName());
        TextView textViewSave = dialog.findViewById(R.id.textViewRenameAlbumSave);
        TextView textViewCancel = dialog.findViewById(R.id.textViewRenameAlbumCancel);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.getText().toString().isEmpty()) {
                    textViewSave.setClickable(false);
                    textViewSave.setTextColor(getResources().getColor(R.color.app_light_gray));
                } else {
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
