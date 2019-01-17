package com.flashdin.fastandroidnetworkingnew.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by flashdin on 10/11/17.
 */

@Getter
@Setter
public class TUsers {

    private int userId;
    private String userName;
    private Date userDate;
    private String userPhoto; //blob
    private String userPhotopath;

}
