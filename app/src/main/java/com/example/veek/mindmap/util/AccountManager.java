package com.example.veek.mindmap.util;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.example.veek.mindmap.model.Account;

import java.io.UnsupportedEncodingException;

/**
 * Crafted by veek on 10.01.16 with love ♥
 */
public class AccountManager {
    private static AccountManager ourInstance = new AccountManager();
    private Context context;

    public static AccountManager getInstance() {
        return ourInstance;
    }

    private AccountManager() {
    }

    Account currentAccount;

    public void init(Context context) {
        this.currentAccount = new Account();
        this.context = context;
        loadAccount();
    }

    public void loadAccount() {
        for (String filename :
                context.fileList()) {

            try {
                String decodedName = new String(Base64.decode(filename, Base64.URL_SAFE), "UTF-8");
                addMindMap(Long.valueOf(decodedName.substring(0, 13)), decodedName.substring(14));
                Log.d("accountTest", Long.valueOf(decodedName.substring(0, 13)) + decodedName.substring(14));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    public void addMindMap(long id, String name) {
        currentAccount.getMaps().add(new Pair<>(name, id));
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}
