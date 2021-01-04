package com.alllinkshare.user.repos;

import android.util.Log;

import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.State;

import java.util.ArrayList;
import java.util.List;

public class StateRepository {
    private static final String TAG = "Repo/State";

    private static StateRepository instance = null;

    private static List<State> items = new ArrayList<>();

    public static synchronized StateRepository getInstance(){
        if(instance == null){
            instance = new StateRepository();
        }
        return instance;
    }

    private StateRepository(){
        Log.d(TAG, "New instance created...");
    }

    public void getStates(final int countryId, final StateRepository.DataReadyListener listener){
        API.states(countryId, new Listeners.StatesListener() {
            @Override
            public void onSuccess(List<State> states) {
                items = states;
                listener.onDataReady(states);
            }

            @Override
            public void onFailure(String error) {
                listener.onFailure(error);
            }
        });

        Log.d(TAG, "Get states completed...");
    }

    public State getStateById(int id){
        for(State State : items){
            if(State.getId() == id){
                return State;
            }
        }
        return null;
    }

    public interface DataReadyListener{
        void onDataReady(List<State> stateList);
        void onFailure(String error);
    }
}