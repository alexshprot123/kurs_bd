package org.example;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Consts {
    public static final String GENDER_MALE = "муж";
    public static final String GENDER_FEMALE = "жен";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_DOCTOR = "doctor";
    public static final String ROLE_PATIENT = "patient";
    public static final int MAX_PATIENT_IN_ONE_DAY = 8;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final Color BACKGROUND_COLOR = new Color(158, 162, 227);
    public static final Color MINI_PANEL_COLOR = new Color(103, 108, 190);
}
