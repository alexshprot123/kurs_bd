package org.example;

import org.example.Forms.admin.AdminForm;
import org.example.Forms.admin.doctor.DoctorControllerForm;
import org.example.Forms.admin.doctor.modal.*;
import org.example.Forms.admin.patient.PatientControllerForm;
import org.example.Forms.doctor.DoctorForm;
import org.example.Forms.login.LoginForm;
import org.example.Forms.patient.PatientForm;
import org.example.entity.MedUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MainForm extends JFrame{
    private static MainForm instance;
    private final static String LOGIN_FORM = "LOGIN_FORM";
    private CardLayout cardLayout = new CardLayout();
    private JPanel panel;
    private JPanel cardPanel;
    private JButton button1;
    private MedUser curUser;

    LoginForm loginForm;
    AdminForm adminForm;
    DoctorForm doctorForm;
    PatientForm patientForm;



    public MainForm(){
        instance = this;
        setContentPane(panel);
        ImageIcon img = new ImageIcon("icon.png");
        setIconImage(img.getImage());
        initColors();
        JMenuBar mb = new JMenuBar();

        setJMenuBar(mb);

        cardPanel.setLayout(cardLayout);
        loginForm = new LoginForm(this);
        adminForm = new AdminForm(this);
        doctorForm = new DoctorForm(this);
        patientForm = new PatientForm(this);
        cardPanel.add(loginForm, LOGIN_FORM);
        cardPanel.add(adminForm, Consts.ROLE_ADMIN);
        cardPanel.add(doctorForm, Consts.ROLE_DOCTOR);
        cardPanel.add(patientForm, Consts.ROLE_PATIENT);
//        cardPanel.add(new PatientControllerForm(this), PATIENT_FORM);
        setForm(LOGIN_FORM);
        setVisible(true);
    }

    private void initColors(){
        panel.setBackground(Consts.BACKGROUND_COLOR);
        cardPanel.setBackground(Consts.BACKGROUND_COLOR);
    }

    public void setForm(String windowName)
    {
        CardLayout layout = (CardLayout)(cardPanel.getLayout());
        updateForm(windowName);
        layout.show(cardPanel, windowName);
    }

    public void updateForm(String windowName){
        switch (windowName){
            case LOGIN_FORM:
                loginForm.updateForm();
                break;
            case Consts.ROLE_ADMIN:
                adminForm.updateForm();
                break;
            case Consts.ROLE_DOCTOR:
                doctorForm.updateForm();
                break;
            case Consts.ROLE_PATIENT:
                patientForm.updateForm();
                break;
        }
    }

    public static void logout(){
        instance.curUser = null;
        instance.setForm(LOGIN_FORM);
    }
    public static void openForm(MedUser user){
        instance.curUser = user;
        switch (user.role){
            case Consts.ROLE_ADMIN:
                openAdminForm();
                return;
            case Consts.ROLE_DOCTOR:
                openDoctorForm();
                return;
            case Consts.ROLE_PATIENT:
                openPatientForm();
                return;
        }

    }
    public static void openAdminForm(){
        instance.setForm(Consts.ROLE_ADMIN);
    }
    public static void openDoctorForm(){
        instance.setForm(Consts.ROLE_DOCTOR);
    }
    public static void openPatientForm(){
        instance.setForm(Consts.ROLE_PATIENT);
    }
    public static MedUser getCurUser(){
        return instance.curUser;
    }
}
