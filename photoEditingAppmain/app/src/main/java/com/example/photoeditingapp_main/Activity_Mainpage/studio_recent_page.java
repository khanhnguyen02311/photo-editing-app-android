package com.example.photoeditingapp_main.Activity_Mainpage;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes.SquareImageView;
import com.example.photoeditingapp_main._Classes._AccountGridViewAdapter;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class studio_recent_page extends Fragment {

    _GlobalVariables gv;
    ArrayList<GeneralPictureItem> imageItems;
    _AccountGridViewAdapter gridViewAdapter;
    ExpandableGridView gridView;

    TextView albumAmount;
    TextView textPhoto;

    MaterialButton deselectBtn;
    MaterialButton selectBtn;
    ImageButton imageBtnMore;

    BottomNavigationView bottomNavigationView;
    BottomNavigationView bottomNavigationView2;

    MenuItem menuItemSave;
    MenuItem menuItemShare;
    MenuItem menuItemBlank;

    RelativeLayout relativeLayoutTitle;
    RelativeLayout relativeLayoutSelectedImage;

    boolean isFirstSetUp = true;

    public studio_recent_page() { }

    public static studio_recent_page newInstance() { return new studio_recent_page(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = (_GlobalVariables) requireActivity().getApplication();
        imageItems = gv.getLocalDB().getImagesFromStudio();
    }

    @Override
    public void onResume() {
        //reset layouts
        if (relativeLayoutSelectedImage.getVisibility() == View.VISIBLE) {
            relativeLayoutTitle.setVisibility(View.VISIBLE);
            relativeLayoutSelectedImage.setVisibility(View.GONE);
        }
        if (bottomNavigationView2.getVisibility() == View.VISIBLE) showBottomNavMainPage();
        isFirstSetUp = true;

        imageItems = gv.getLocalDB().getImagesFromStudio();
        gridViewAdapter = new _AccountGridViewAdapter(getContext(), imageItems);
        gridView.setAdapter(gridViewAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_studio_recent_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumAmount = view.findViewById(R.id.amount_photos_text_recent_page);
        textPhoto = view.findViewById(R.id.photos_text_recent_page);

        deselectBtn = view.findViewById(R.id.deselect_button_recent_page);
        selectBtn = view.findViewById(R.id.select_button_recent_page);

        imageBtnMore = view.findViewById(R.id.more_button_recent_page);

        relativeLayoutTitle = view.findViewById(R.id.relativeLayout_studio_recent_page_title);
        relativeLayoutSelectedImage = view.findViewById(R.id.relativeLayout_studio_recent_page_selected_image);

        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_mainpage);
        bottomNavigationView2 = requireActivity().findViewById(R.id.bottom_navigation_mainpage_selected_image);

        menuItemSave = bottomNavigationView2.getMenu().getItem(1);
        menuItemShare = bottomNavigationView2.getMenu().getItem(2);
        menuItemBlank = bottomNavigationView2.getMenu().getItem(0);


        menuItemBlank.setEnabled(false);
        menuItemBlank.setVisible(false);

        gridView = view.findViewById(R.id.grid_view_recent_page);
        gridViewAdapter = new _AccountGridViewAdapter(getContext(), imageItems);
        gridView.setAdapter(gridViewAdapter);

        // Item click in grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MaterialCardView cardView = view.findViewById(R.id.cardView_custom_item_gridview_account_page);
                SquareImageView squareImageView = view.findViewById(R.id.imageView_custom_item_gridview_account_page);
                _AccountGridViewAdapter adapter1 = ((_AccountGridViewAdapter) adapterView.getAdapter());

                // Set animation for image selected
                if (!adapter1.getSelected(i)) {
                    startAnimation(squareImageView, cardView);
                    adapter1.setSelected(i, true);
                } else {
                    endAnimation(squareImageView, cardView);
                    adapter1.setSelected(i, false);
                }
                if (adapter1.getPositionSelectedItems().size() != 0) {
                    if (isFirstSetUp) {
                        relativeLayoutTitle.setVisibility(View.GONE);
                        relativeLayoutSelectedImage.setVisibility(View.VISIBLE);
                        isFirstSetUp = false;
                        showBottomNavSelectImage();
                    }
                    if(adapter1.getPositionSelectedItems().size() == 1) {
                        menuItemSave.setVisible(true);
                        menuItemShare.setVisible(true);
                    }
                    else {
                        if(menuItemSave.isEnabled()) {
                            menuItemSave.setVisible(false);
                            menuItemShare.setVisible(false);
                        }
                    }
                    setText(adapter1.getPositionSelectedItems().size(), albumAmount, textPhoto);
                }
                else {
                    relativeLayoutTitle.setVisibility(View.VISIBLE);
                    relativeLayoutSelectedImage.setVisibility(View.GONE);
                    isFirstSetUp = true;
                    showBottomNavMainPage();
                }

                bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_photo:
                                Intent designActivity = new Intent(getActivity(), DesignActivity.class);
                                designActivity.putExtra("image_uri",
                                        Objects.requireNonNull(imageItems.get(adapter1.getPositionSelectedItems().get(0)).getImageUri()));
                                designActivity.putExtra("image_id",
                                        Integer.toString(imageItems.get(adapter1.getPositionSelectedItems().get(0)).getId()));
                                //float[] listConfig = imageItems.get(adapter1.getPositionSelectedItems().get(0)).g
                                startActivity(designActivity);
                                break;
                            case R.id.save_photo: break;
                            case R.id.share_photo: break;
                            case R.id.delete_photo: break;
                            case R.id.blank: default: break;
                        }
                        return true;
                    }
                });
            }
        });

        // Set button more
        imageBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.more_button_menu, popupMenu.getMenu());

                popupMenu.getMenu().removeItem(R.id.rename_album);
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
                                gridView.setNumColumns(gridView.getNumColumns() - 2);
                                break;
                            case R.id.zoom_out:
                                gridView.setNumColumns(gridView.getNumColumns() + 2);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });

        // Set button deselect
        deselectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutTitle.setVisibility(View.VISIBLE);
                relativeLayoutSelectedImage.setVisibility(View.GONE);

                List<Integer> positionSelectedItems = gridViewAdapter.getPositionSelectedItems();
                for (int i = 0; i < positionSelectedItems.size(); i++) {
                    View view = gridView.getChildAt(positionSelectedItems.get(i));
                    if (view != null) {
                        endAnimation(view.findViewById(R.id.imageView_custom_item_gridview_account_page), view.findViewById(R.id.cardView_custom_item_gridview_account_page));
                    }
                }
                gridViewAdapter.clearSelected();
                isFirstSetUp = true;
                showBottomNavMainPage();
            }
        });

        // Set button select
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> positionDeselectedItems = gridViewAdapter.getPositionDeselectedItems();
                for (int i = 0; i < positionDeselectedItems.size(); i++) {
                    View view= gridView.getChildAt(positionDeselectedItems.get(i));
                    if (view != null) {
                        startAnimation(view.findViewById(R.id.imageView_custom_item_gridview_account_page), view.findViewById(R.id.cardView_custom_item_gridview_account_page));
                    }
                }
                if (gridViewAdapter.getPositionSelectedItems().size() == 1) {
                    menuItemSave.setVisible(false);
                    menuItemShare.setVisible(false);
                }
                gridViewAdapter.setAllSelected();
                gridViewAdapter.notifyDataSetChanged();
                setText(gridViewAdapter.getPositionSelectedItems().size(),albumAmount,textPhoto);
            }
        });

        // Set up  bottom navigation
        /*BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_mainpage_selected_image);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_photo:
                        break;
                    case R.id.save_photo:
                        break;
                    case R.id.share_photo:
                        break;
                    case R.id.delete_photo:

                }
                return false;
            }
        });*/
    }

    private void setText(int size, TextView albumAmount, TextView textPhoto) {
        String amount = String.valueOf(size);
        albumAmount.setText(amount);
        textPhoto.setText("Selected");
    }

    private void showBottomNavSelectImage() {
        bottomNavigationView.setVisibility(View.GONE);
        bottomNavigationView2.setVisibility(View.VISIBLE);
        bottomNavigationView2.setSelectedItemId(R.id.blank);
    }

    private void showBottomNavMainPage() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView2.setVisibility(View.GONE);
    }

    // Start animation when select image item
    private void startAnimation(SquareImageView squareImageView, MaterialCardView cardView) {
        squareImageView.setCornerRadius(32);
        ObjectAnimator animator = ObjectAnimator.ofArgb(cardView, "strokeColor", Color.parseColor("#505050"));
        animator.setDuration(100);
        animator.start();
    }

    // End animation when deselect image item
    private void endAnimation(SquareImageView squareImageView, MaterialCardView cardView) {
        squareImageView.setCornerRadius(0);
        ObjectAnimator animator = ObjectAnimator.ofArgb(cardView, "strokeColor", Color.parseColor("#00505050"));
        animator.setDuration(100);
        animator.start();
    }
}