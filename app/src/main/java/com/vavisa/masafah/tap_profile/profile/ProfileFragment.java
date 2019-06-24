package com.vavisa.masafah.tap_profile.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;
import com.vavisa.masafah.login.LoginActivity;
import com.vavisa.masafah.tap_profile.TermsAndCondition.TermsAndConditions;
import com.vavisa.masafah.tap_profile.profile.model.EditProfileModel;
import com.vavisa.masafah.tap_profile.profile.model.UpdateProfileResponseM;
import com.vavisa.masafah.tap_profile.shipment_history.ShipmentHistoryFragment;
import com.vavisa.masafah.util.GridSpaceItemDecoration;
import com.vavisa.masafah.util.KeyboardUtil;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.VerifyYourNumberActivity;
import com.vavisa.masafah.verify_phone_number.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, ProfileView {

    private View fragment;
    private CircleImageView user_image, user_image_update;
    private TextView user_name, user_email, user_phone;
    private RecyclerView profileGridView;
    private List<Profile> profileItems;
    private ConstraintLayout profileLayout;
    private Button logout;
    private User user;
    private ProfilePresenter presenter;
    private boolean isImageChanged = false;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_profile, container, false);
            initViews();

        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(1).setChecked(true);
        }

        return fragment;
    }

    private void initViews() {

        Toolbar toolbar = fragment.findViewById(R.id.profile_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("");

        profileLayout = fragment.findViewById(R.id.profile_layout);
        user_image = fragment.findViewById(R.id.company_image);
        user_name = fragment.findViewById(R.id.user_name);
        user_email = fragment.findViewById(R.id.user_email);
        user_phone = fragment.findViewById(R.id.user_phone);
        profileGridView = fragment.findViewById(R.id.profile_items);
        logout = fragment.findViewById(R.id.logout_button);

        logout.setOnClickListener(this);

        presenter = new ProfilePresenter();
        presenter.attachView(this);
        presenter.getUserProfile();

        profileGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        profileGridView.addItemDecoration(new GridSpaceItemDecoration(25));

        setupProfileItems();

        profileGridView.setAdapter(new ProfileAdapter());

        profileLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int height = profileLayout.getHeight();
                        profileLayout.setTranslationY(-(height / 3));
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void user(User user) {
        this.user = user;

        user_name.setText(user.getFullname());
        user_email.setText(user.getEmail());
        user_phone.setText(user.getMobile());
        Glide.with(this)
                .load(user.getProfile_image())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(user_image);

    }

    @Override
    public void updateProfileResponse(UpdateProfileResponseM updateProfileResponseM) {

        this.user = updateProfileResponseM.getUser();

        user_name.setText(user.getFullname());
        user_email.setText(user.getEmail());
        user_phone.setText(user.getMobile());
        Glide.with(this)
                .load(user.getProfile_image())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(user_image);
    }

    @Override
    public void changeMobileResponse(String otp) {
        Intent i = new Intent(getContext(), VerifyYourNumberActivity.class);
        //i.putExtra("update_mobile",);
        startActivity(i);
    }

    private void updateDialog() {

        final DialogPlus dialogPlus =
                DialogPlus.newDialog(getActivity())
                        .setGravity(Gravity.BOTTOM)
                        .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                        .setContentHolder(new ViewHolder(R.layout.profile_view))
                        .create();

        ImageView close_btn = (ImageView) dialogPlus.findViewById(R.id.imageView);
        Button update_btn = (Button) dialogPlus.findViewById(R.id.update_btn);
        user_image_update = (CircleImageView) dialogPlus.findViewById(R.id.user_image_update);
        FloatingActionButton add_image_btn = (FloatingActionButton) dialogPlus.findViewById(R.id.add_image_btn);
        EditText fullName_ed = (EditText) dialogPlus.findViewById(R.id.full_name);
        Button edit_mobile_btn = (Button) dialogPlus.findViewById(R.id.edit_mobile_btn);
        EditText email_ed = (EditText) dialogPlus.findViewById(R.id.email);

        fullName_ed.setText(user.getFullname());
        email_ed.setText(user.getEmail());
        Glide.with(this)
                .load(user.getProfile_image())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(user_image_update);

        update_btn.setOnClickListener(v -> {
            checkChangesFields(fullName_ed, email_ed);
            KeyboardUtil.hideKeyboardFrom(getContext(), update_btn);
            dialogPlus.dismiss();
        });

        edit_mobile_btn.setOnClickListener(v -> {
            showEditMobileDialog();
        });

        add_image_btn.setOnClickListener(v -> addNewImage());

        close_btn.setOnClickListener(v -> dialogPlus.dismiss());

        dialogPlus.show();
    }

    private void showEditMobileDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_alert_edit_mobile, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView mobile_required_hint = view.findViewById(R.id.mobile_required_hint);
        EditText mobile_ed_ = view.findViewById(R.id.mobile_edit_ed);
        Button cancel_btn = view.findViewById(R.id.cancel_button);
        Button edit_btn = view.findViewById(R.id.edit_btn);

        cancel_btn.setOnClickListener(v -> dialog.dismiss());

        edit_btn.setOnClickListener(v -> {
            String mobile_str = mobile_ed_.getText().toString();
            if (TextUtils.isEmpty(mobile_str) || mobile_str.length() < 8)
                mobile_required_hint.setTextColor(Color.RED);
            else {
                EditProfileModel editProfileModel = new EditProfileModel(mobile_str);
                presenter.changeMobile(editProfileModel);
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    private void checkChangesFields(EditText fullName_ed, EditText email_ed) {

        String fullName_str, email_str;
        fullName_str = fullName_ed.getText().toString();
        email_str = email_ed.getText().toString();

        EditProfileModel editProfileModel = new EditProfileModel(fullName_str, email_str);
        if (isImageChanged) {
            String image_str = presenter.getImageBase64FromView(user_image_update);
            editProfileModel.setImage(image_str);
        }

        presenter.updateProfile(editProfileModel);
    }

    private void addNewImage() {

        String[] items = {getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder select_pic_alert = new AlertDialog.Builder(getContext());
        select_pic_alert.setTitle(getString(R.string.select_picture_from));
        select_pic_alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {

                if (index == 0) {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera_intent.resolveActivity(getContext().getPackageManager()) != null)
                        startActivityForResult(camera_intent, PICK_IMAGE_CAMERA);
                } else
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_IMAGE_GALLERY);
            }
        });

        select_pic_alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;
        isImageChanged = true;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PICK_IMAGE_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    break;

                case PICK_IMAGE_GALLERY:
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            Glide.with(getContext()).asBitmap().load(bitmap).into(user_image_update);
        }
    }

    private class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemIcon;
        private TextView itemName;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.item_icon);
            itemName = itemView.findViewById(R.id.item_name);
        }

        public void bindData(Profile profile) {
            itemIcon.setImageResource(profile.getImage());
            itemName.setText(profile.getName());
        }
    }

    private class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

        @NonNull
        @Override
        public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v =
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.profile_list_item, viewGroup, false);

            return new ProfileViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i) {
            profileViewHolder.bindData(profileItems.get(i));

            final int position = profileViewHolder.getAdapterPosition();

            profileViewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Fragment fragment = null;
                            switch (position) {
                                case 0:
                                    updateDialog();
                                    break;
                                case 1:
                                    changeLanguagePopMenu();
                                    break;
                                case 2:
                                    fragment = new ShipmentHistoryFragment();
                                    switchFragment(fragment);
                                    break;
                                case 3:
                                    fragment = new TermsAndConditions();
                                    switchFragment(fragment);
                                    break;
                            }
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return profileItems.size();
        }
    }

    private void changeLanguagePopMenu() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.select_language));
        String[] languages = new String[]{"English", "العربية"};

        int lan_pos = (Preferences.getInstance().getString("lan").equals("English")) ? 0 : 1;

        alert.setSingleChoiceItems(languages, lan_pos, (dialog, pos) -> {
            if (pos != lan_pos) {
                Preferences.getInstance().putString("lan", languages[pos]);
                Intent i = getContext().getPackageManager()
                        .getLaunchIntentForPackage(getContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else
                dialog.dismiss();
        });

        alert.show();
    }

    private void setupProfileItems() {
        profileItems = new ArrayList<>();
        Profile profile = new Profile();

        profile.setImage(R.drawable.ic_account_circle_primary_24dp);
        profile.setName(getString(R.string.my_details));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_language_primary_24dp);
        profile.setName(getString(R.string.language));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_box);
        profile.setName(getString(R.string.shipment_history));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_document);
        profile.setName(getString(R.string.termsandconditions));

        profileItems.add(profile);
    }

    private class Profile {
        int image;
        String name;

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
