package com.example.veek.mindmap.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Crafted by veek on 10.01.16 with love ♥.
 */
public class Account {
    ArrayList<Pair<String, Long>> maps = new ArrayList<>();
    long accountId;

    public Account() {
        Random random = new Random();
        accountId = System.currentTimeMillis() >> random.nextInt();
    }

    public long getAccountId() {
        return accountId;
    }

    public String getMapNameById(long id) {
        for (Pair<String, Long> map :
                maps) {
            if (map.second == id) {
                return map.first;
            }
        }
        return "no map";
    }

    public ArrayList<Pair<String, Long>> getMaps() {
        return maps;
    }

}
