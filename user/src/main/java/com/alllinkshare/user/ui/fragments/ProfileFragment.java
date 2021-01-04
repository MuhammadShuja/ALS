package com.alllinkshare.user.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.alllinkshare.core.utils.GifSizeFilter;
import com.alllinkshare.core.utils.Glide4Engine;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.City;
import com.alllinkshare.user.models.Country;
import com.alllinkshare.user.models.SpinnerItem;
import com.alllinkshare.user.models.State;
import com.alllinkshare.user.models.User;
import com.alllinkshare.user.repos.CityRepository;
import com.alllinkshare.user.repos.CountryRepository;
import com.alllinkshare.user.repos.StateRepository;
import com.alllinkshare.user.repos.UserRepository;
import com.alllinkshare.user.ui.adapters.SpinnerAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSION = 111;
    private static final int REQUEST_CODE_PICKER = 222;

    private TextInputEditText inputPhoneNumber, inputEmail, inputFirstName, inputLastName,
            inputAddress, inputStreetAddress, inputPincode;
    private Spinner countrySpinner, stateSpinner, citySpinner;
    private CircleImageView profilePicture;

    private SpinnerAdapter countriesSpinnerAdapter, statesSpinnerAdapter, citiesSpinnerAdapter;
    private int SELECTED_COUNTRY, SELECTED_STATE, SELECTED_CITY;

    private User userProfile = null;

    private Form<Object> form = new Form<>();
    private Swal swal;
    private View rootView;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initDialog();
        initForm();

        return rootView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK) {
            List<Uri> imagesPicked = Matisse.obtainResult(data);
            uploadProfilePicture(imagesPicked.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPicker();
            } else {
                Toast.makeText(
                        getActivity(),
                        getResources().getString(R.string.user_fragment_profile_storage_permission_error),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initDialog(){
        swal = new Swal(getContext());
    }

    private void initForm(){
        initFormValidation();
        initUpdateProfile();
        initUpdateProfilePicture();
    }

    private void initFormValidation(){
        profilePicture = rootView.findViewById(R.id.profile_picture);

        inputPhoneNumber = rootView.findViewById(R.id.phone_number);
        inputEmail = rootView.findViewById(R.id.email);
        inputFirstName = rootView.findViewById(R.id.first_name);
        inputLastName = rootView.findViewById(R.id.last_name);
        inputAddress = rootView.findViewById(R.id.address);
        inputStreetAddress = rootView.findViewById(R.id.street_address);
        inputPincode = rootView.findViewById(R.id.pincode);

        List<ValidationRule> firstNameValidationRules = new ArrayList<>();
        firstNameValidationRules.add(ValidationRule.Required((TextView) rootView.findViewById(R.id.first_name_error),"First name is required"));
        form.addField(inputFirstName, firstNameValidationRules);

        List<ValidationRule> lastNameValidationRules = new ArrayList<>();
        lastNameValidationRules.add(ValidationRule.Required((TextView) rootView.findViewById(R.id.last_name_error),"Last name is required"));
        form.addField(inputLastName, lastNameValidationRules);

        initCountriesSpinner();
        initStatesSpinner();
        initCitiesSpinner();

        loadProfile();
        loadCountries();
    }

    private void initCountriesSpinner(){
        countriesSpinnerAdapter = new SpinnerAdapter(Objects.requireNonNull(getContext()), new ArrayList<SpinnerItem>());

        countrySpinner = rootView.findViewById(R.id.country_spinner);
        countrySpinner.setAdapter(countriesSpinnerAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    SELECTED_COUNTRY = Objects.requireNonNull(countriesSpinnerAdapter.getItem(i)).getId();
                    loadStates();
                }
                else{
                    SELECTED_COUNTRY = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initStatesSpinner(){
        statesSpinnerAdapter = new SpinnerAdapter(Objects.requireNonNull(getContext()), new ArrayList<SpinnerItem>());

        stateSpinner = rootView.findViewById(R.id.state_spinner);
        stateSpinner.setAdapter(statesSpinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    SELECTED_STATE = Objects.requireNonNull(statesSpinnerAdapter.getItem(i)).getId();

                    loadCities();
                }
                else{
                    SELECTED_STATE = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initCitiesSpinner(){
        citiesSpinnerAdapter = new SpinnerAdapter(Objects.requireNonNull(getContext()), new ArrayList<SpinnerItem>());

        citySpinner = rootView.findViewById(R.id.city_spinner);
        citySpinner.setAdapter(citiesSpinnerAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    SELECTED_CITY = Objects.requireNonNull(citiesSpinnerAdapter.getItem(i)).getId();
                }
                else{
                    SELECTED_CITY = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadProfile(){
        UserRepository.getInstance().getProfile(new UserRepository.ProfileReadyListener() {
            @Override
            public void onProfileReady(User profile) {
                userProfile = profile;

                Glide
                        .with(Objects.requireNonNull(getActivity()))
                        .load(profile.getProfilePicture())
                        .into(profilePicture);

                inputPhoneNumber.setText(profile.getMobileNumber());
                inputEmail.setText(profile.getEmail());
                inputFirstName.setText(profile.getFirstName());
                inputLastName.setText(profile.getLastName());
                inputAddress.setText(profile.getAddress());
                inputStreetAddress.setText(profile.getStreetAddress());
                inputPincode.setText(profile.getPinCode());
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void loadCountries(){
        swal.progress("Loading");

        CountryRepository.getInstance().getCountries(new CountryRepository.DataReadyListener() {
            @Override
            public void onDataReady(List<Country> countryList) {
                List<SpinnerItem> items = new ArrayList<>();

                int position = 0, index = 0;
                for(Country country : countryList){
                    items.add(new SpinnerItem(country.getId(), country.getName()));
                    if(userProfile != null){
                        if(country.getId() == userProfile.getCountry().getId()){
                            position = index + 1;
                        }
                    }
                    index++;
                }

                items.add(0, new SpinnerItem(-1, "Select"));
                countriesSpinnerAdapter.setData(items);

                if(position > 0) {
                    countrySpinner.setSelection(position);
                }

                swal.dismiss();
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void loadStates(){
        if(SELECTED_COUNTRY < 1) return;

        swal.progress("Loading states");

        StateRepository.getInstance().getStates(SELECTED_COUNTRY, new StateRepository.DataReadyListener() {
            @Override
            public void onDataReady(List<State> stateList) {
                List<SpinnerItem> items = new ArrayList<>();

                int position = 0, index = 0;
                for(State state : stateList){
                    items.add(new SpinnerItem(state.getId(), state.getName()));

                    if(userProfile != null){
                        if(state.getId() == userProfile.getState().getId()){
                            position = index + 1;
                        }
                    }
                    index++;
                }

                items.add(0, new SpinnerItem(-1, "Select"));
                statesSpinnerAdapter.setData(items);

                if(position > 0) {
                    stateSpinner.setSelection(position);
                }

                swal.dismiss();
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void loadCities(){
        if(SELECTED_STATE < 1) return;

        swal.progress("Loading cities");

        CityRepository.getInstance().getCities(SELECTED_STATE, new CityRepository.DataReadyListener() {
            @Override
            public void onDataReady(List<City> cityList) {
                List<SpinnerItem> items = new ArrayList<>();

                int position = 0, index = 0;
                for(City city : cityList){
                    items.add(new SpinnerItem(city.getId(), city.getName()));

                    if(userProfile != null){
                        if(city.getId() == userProfile.getCity().getId()){
                            position = index + 1;
                        }
                    }
                    index++;
                }

                items.add(0, new SpinnerItem(-1, "Select"));
                citiesSpinnerAdapter.setData(items);

                if(position > 0) {
                    citySpinner.setSelection(position);
                }

                swal.dismiss();
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void initUpdateProfile(){
        rootView.findViewById(R.id.btn_update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(form.isValid()){
                    swal.progress("Updateing");
                    User user = new User(
                            null,
                            Objects.requireNonNull(inputFirstName.getText()).toString().trim(),
                            Objects.requireNonNull(inputLastName.getText()).toString().trim(),
                            null,
                            null,
                            null,
                            null,
                            Objects.requireNonNull(inputAddress.getText()).toString().trim(),
                            Objects.requireNonNull(inputStreetAddress.getText()).toString().trim(),
                            Objects.requireNonNull(inputPincode.getText()).toString().trim(),
                            new City(SELECTED_CITY, null, null),
                            new State(SELECTED_STATE, null, null),
                            new Country(SELECTED_COUNTRY, null, null)
                    );

                    UserRepository.getInstance().updateProfile(user, new UserRepository.ProfileReadyListener() {
                        @Override
                        public void onProfileReady(User user) {
                            userProfile = user;
                            swal.success("Profile has been updated successfully");
                        }

                        @Override
                        public void onFailure(String error) {
                            swal.error(error);
                        }
                    });
                }
            }
        });
    }

    private void initUpdateProfilePicture(){
        rootView.findViewById(R.id.btn_edit_profile_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if(hasPermissions(getActivity(), PERMISSIONS)){
                    showPicker();
                }else{
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSION);
                }
            }
        });
    }

    private void showPicker(){
        Matisse.from(ProfileFragment.this)
                .choose(MimeType.ofImage())
                .capture(true)
                .captureStrategy(new CaptureStrategy(false, Objects.requireNonNull(getContext()).getPackageName()+".fileprovider"))
                .countable(true)
                .maxSelectable(1)
                .theme(R.style.ImagePickerTheme)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_PICKER);
    }

    private void uploadProfilePicture(Uri uri){
        swal.progress("Updating profile picture");
        try {
            File file = new File(getRealPathFromURI(uri));
            File compressedFile = new Compressor(getContext()).compressToFile(file);

            API.updateProfilePicture(compressedFile, new Listeners.ProfilePictureListener() {
                @Override
                public void onSuccess(String profilePicture) {
                    Glide
                            .with(getContext())
                            .load(profilePicture)
                            .placeholder(R.drawable.profile_picture_placeholder)
                            .into(
                                    (ImageView) rootView.findViewById(R.id.profile_picture)
                            );
                    swal.dismiss();
                }

                @Override
                public void onFailure(String error) {
                    swal.error(error);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            swal.error(e.getMessage());
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}