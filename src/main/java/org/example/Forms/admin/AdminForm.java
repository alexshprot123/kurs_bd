package org.example.Forms.admin;

import org.example.Consts;
import org.example.Forms.admin.doctor.DoctorControllerForm;
import org.example.Forms.admin.documents.DocumentsForm;
import org.example.Forms.admin.patient.PatientControllerForm;
import org.example.Forms.doctor.DoctorForm;
import org.example.Forms.patient.PatientForm;
import org.example.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm extends JPanel {
    private static final String DOCTOR_FORM = "DOCTOR_FORM";
    private static final String PATIENT_FORM = "PATIENT_FORM";
    private static final String DOCUMENT_FORM = "DOCUMENT_FORM";
    private CardLayout cardLayout = new CardLayout();
    JButton doctorsButton;
    JButton patientButton;
    JButton documentsButton;
    JButton exitButton;
    JFrame frame;
    DoctorControllerForm doctorForm;
    PatientControllerForm patientForm;
    DocumentsForm documentsForm;
    private JPanel cardPanel;

    public AdminForm(JFrame frame){
        this.frame = frame;
        JPanel p = new JPanel();
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        setSize(-1, -1);
        JMenuBar mb = new JMenuBar();

        doctorsButton = new JButton("Сотрудники");
        patientButton = new JButton("Пациенты");
        documentsButton = new JButton("Справки");
        exitButton = new JButton("Выйти");

        mb.add(doctorsButton);
        mb.add(patientButton);
        mb.add(documentsButton);
        mb.add(exitButton);
        add(mb, BorderLayout.NORTH);

        cardPanel.setLayout(cardLayout);
        doctorForm = new DoctorControllerForm(frame);
        patientForm = new PatientControllerForm(frame);
        documentsForm = new DocumentsForm(frame);
        cardPanel.add(doctorForm, DOCTOR_FORM);
        cardPanel.add(patientForm, PATIENT_FORM);
        cardPanel.add(documentsForm, DOCUMENT_FORM);
        openDoctorController();

        doctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDoctorController();
            }
        });

        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPacientController();
            }
        });

        documentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDocumentController();
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }
    public void openDoctorController(){
        doctorsButton.setEnabled(false);
        patientButton.setEnabled(true);
        documentsButton.setEnabled(true);
        setForm(DOCTOR_FORM);
    }
    public void openPacientController(){
        patientButton.setEnabled(false);
        doctorsButton.setEnabled(true);
        documentsButton.setEnabled(true);
        setForm(PATIENT_FORM);
    }
    public void openDocumentController(){
        patientButton.setEnabled(true);
        doctorsButton.setEnabled(true);
        documentsButton.setEnabled(false);
        setForm(DOCUMENT_FORM);
    }
    public void exit(){
        MainForm.logout();
    }

    public void setForm(String windowName)
    {
        CardLayout layout = (CardLayout)(cardPanel.getLayout());
        updateForm(windowName);
        layout.show(cardPanel, windowName);
    }
    public void updateForm(){
        setForm(DOCTOR_FORM);
        frame.setSize(400, 600);
    }

    public void updateForm(String windowName){
        switch (windowName){
            case DOCTOR_FORM:
                doctorForm.updateForm();
                break;
            case PATIENT_FORM:
                patientForm.updateForm();
                break;
            case DOCUMENT_FORM:
                documentsForm.updateForm();
                break;
        }
    }
}
