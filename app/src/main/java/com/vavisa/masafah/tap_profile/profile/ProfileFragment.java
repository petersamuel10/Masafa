package com.vavisa.masafah.tap_profile.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;
import com.vavisa.masafah.helpers.OTP.CountryModel;
import com.vavisa.masafah.helpers.OTP.OTPViews;
import com.vavisa.masafah.helpers.OTP.SendOTPPresenter;
import com.vavisa.masafah.login.LoginActivity;
import com.vavisa.masafah.login.LoginModel;
import com.vavisa.masafah.tap_profile.TermsAndCondition.TermsAndConditions;
import com.vavisa.masafah.tap_profile.myAddresses.AddressesFragment;
import com.vavisa.masafah.tap_profile.profile.model.EditProfileModel;
import com.vavisa.masafah.tap_profile.shipment_history.ShipmentHistoryFragment;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.GridSpaceItemDecoration;
import com.vavisa.masafah.util.KeyboardUtil;
import com.vavisa.masafah.util.Preferences;
import com.vavisa.masafah.verify_phone_number.VerifyYourNumberActivity;
import com.vavisa.masafah.verify_phone_number.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;
import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, ProfileView, OTPViews {

    private View fragment;
    private CircleImageView user_image, user_image_update;
    private TextView user_name, user_email, user_phone;
    private RecyclerView profileGridView;
    private List<Profile> profileItems;
    private ConstraintLayout profileLayout;
    private Button logout;
    private User user;
    private ProfilePresenter presenter;
    private SendOTPPresenter otpPresenter;
    private ArrayList<CountryModel> countriesList;
    private boolean isImageChanged = false;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    // for change mobile number
    private String country_id;
    private String country_code, edited_mobile_number;
    private DialogPlus my_details_dialog;

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

    @Override
    public void onStart() {
        super.onStart();

        // to git when came back from verify updated mobile number
        user_phone.setText(Preferences.getInstance().getString("mobile"));

    }

    private void updateDialog() {

        my_details_dialog =
                DialogPlus.newDialog(getActivity())
                        .setGravity(Gravity.BOTTOM)
                        .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                        .setContentHolder(new ViewHolder(R.layout.profile_view))
                        .create();

        ImageView close_btn = (ImageView) my_details_dialog.findViewById(R.id.imageView);
        Button update_btn = (Button) my_details_dialog.findViewById(R.id.update_btn);
        user_image_update = (CircleImageView) my_details_dialog.findViewById(R.id.user_image_update);
        FloatingActionButton add_image_btn = (FloatingActionButton) my_details_dialog.findViewById(R.id.add_image_btn);
        EditText fullName_ed = (EditText) my_details_dialog.findViewById(R.id.full_name);
        EditText email_ed = (EditText) my_details_dialog.findViewById(R.id.email);
        EditText phone_ed = (EditText) my_details_dialog.findViewById(R.id.phone);
        Button country_code_btn = (Button) my_details_dialog.findViewById(R.id.country_code);
        Button edit_mobile_btn = (Button) my_details_dialog.findViewById(R.id.edit_mobile_btn);

        fullName_ed.setText(user.getFullname());
        email_ed.setText(user.getEmail());
        phone_ed.setText(user.getMobile());
        country_code_btn.setText(user.getCountryModel().getCountry_code());
        country_id = user.getCountryModel().getId();
        Glide.with(this)
                .load(user.getProfile_image())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(user_image_update);

        update_btn.setOnClickListener(v -> {
            checkChangesFields(fullName_ed, email_ed);
            KeyboardUtil.hideKeyboardFrom(getContext(), update_btn);
            my_details_dialog.dismiss();
        });
        edit_mobile_btn.setOnClickListener(v -> {
            if (edit_mobile_btn.getText().equals(getString(R.string.edit_mobile))) {
                otpPresenter.getCountries();
                phone_ed.setEnabled(true);
                country_code_btn.setEnabled(true);
                edit_mobile_btn.setText(R.string.update_mobile_number);
            } else if (user.getMobile().equals(phone_ed.getText()))
                showMessage(getString(R.string.number_not_changed));
            else {
                country_code = country_code_btn.getText().toString();
                edited_mobile_number = phone_ed.getText().toString();
                otpPresenter.sendOtp(getActivity(), country_code_btn.getText().toString() + phone_ed.getText().toString());
            }
        });
        country_code_btn.setOnClickListener(v -> countryCodeAlert(country_code_btn));
        add_image_btn.setOnClickListener(v -> addNewImage());
        close_btn.setOnClickListener(v -> my_details_dialog.dismiss());

        my_details_dialog.show();
    }

    private void countryCodeAlert(Button country_code_btn) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.select_country));
        String[] countries_name = new String[countriesList.size()];
        for (int i = 0; i < countriesList.size(); i++) {
            countries_name[i] = countriesList.get(i).getName();
        }

        alert.setSingleChoiceItems(countries_name, 0, (dialog, position) -> {
            dialog.dismiss();
            country_code_btn.setText(countriesList.get(position).getCountry_code());
            country_id = countriesList.get(position).getId();

        });
        alert.create().show();
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

        if (Connectivity.checkInternetConnection())
            presenter.updateProfile(editProfileModel);
        else
            showErrorConnection();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                HashMap<String, String> player_id = new HashMap<>();
                player_id.put("player_id", Constants.ONE_SIGNAL_TOKEN);
                if (Connectivity.checkInternetConnection())
                    presenter.logout(player_id);
                else
                    showErrorConnection();
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
    public void updateProfileResponse(User user) {

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
    public void changeMobileResponse(String updated_mobile, String otp) {
        Intent i = new Intent(getContext(), VerifyYourNumberActivity.class);
        i.putExtra("update_mobile", updated_mobile);
        Log.d("resend_otp", otp);
        startActivity(i);
    }

    @Override
    public void logout() {

        getActivity()
                .getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public void handleVerification_id(String verification_id) {
        LoginModel loginModel =
                new LoginModel(edited_mobile_number,
                        country_id,
                        Constants.ONE_SIGNAL_TOKEN,
                        2,
                        country_code,
                        verification_id);
        my_details_dialog.dismiss();
        Intent intent = new Intent(getContext(), VerifyYourNumberActivity.class);
        intent.putExtra("update_mobile", loginModel);
        startActivity(intent);

    }

    @Override
    public void countries(ArrayList<CountryModel> countriesList) {
        this.countriesList = countriesList;
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
        if (Connectivity.checkInternetConnection())
            presenter.getUserProfile();
        else
            showErrorConnection();

        otpPresenter = new SendOTPPresenter();
        otpPresenter.attachView(this);

        profileGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        profileGridView.addItemDecoration(new GridSpaceItemDecoration(25));

        setupProfileItems();

        profileGridView.setAdapter(new ProfileAdapter());


        profileLayout.post(() -> {
            int height = profileLayout.getHeight();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) profileLayout.getLayoutParams();
            layoutParams.topMargin = -(height / 3);
            profileLayout.setLayoutParams(layoutParams);
        });
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
                                    fragment = new AddressesFragment();
                                    switchFragment(fragment);
                                    break;
                                case 2:
                                    fragment = new ShipmentHistoryFragment();
                                    switchFragment(fragment);
                                    break;
                                case 3:
                                    fragment = new TermsAndConditions();
                                    switchFragment(fragment);
                                    break;
                                case 4:
                                    changeLanguagePopMenu();
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
        profile.setImage(R.drawable.ic_location_on_primary_24dp);
        profile.setName(getString(R.string.my_address));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_box);
        profile.setName(getString(R.string.shipment_history));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_document);
        profile.setName(getString(R.string.termsandconditions));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_language_primary_24dp);
        profile.setName(getString(R.string.language));

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
