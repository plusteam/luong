package com.app.hinh.pencel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung-DamDe-DeTien on 13/08/2016.
 */
public class AccountResponse {
    @SerializedName("data")
    @Expose
    private List<Account> data = new ArrayList<Account>();

   // private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The data
     */

    public List<Account> getData() {
        return data;
    }

    /**
     *
     *
     * @param data
     *     The data
     */

    public void setData(List<Account> data) {
        this.data = data;
    }


}

