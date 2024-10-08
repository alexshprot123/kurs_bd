package org.example.Forms.doctor;

import org.example.BD;
import org.example.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorForm extends JPanel{
    private JPanel cardPanel;
    private static final String PERSON_CARD_FORM = "PERSON_CARD_FORM";
    private static final String PATIENT_FORM = "PATIENT_FORM";
    private static final String APPOINT_FORM = "APPOINT_FORM";
    private CardLayout cardLayout = new CardLayout();
    JButton personCardButton;
    JButton patientButton;
    JButton appointButton;
    JButton exitButton;
    JFrame frame;
    PersonCardDoctorForm personCardForm;
    PatientOfDoctorForm patientForm;
    AppointDoctorForm appointDoctorForm;

    public DoctorForm(JFrame frame){
        this.frame = frame;
        JPanel p = new JPanel();
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        setSize(-1, -1);
        JMenuBar mb = new JMenuBar();

        personCardButton = new JButton("Личная карточка");
        patientButton = new JButton("Пациенты");
        appointButton = new JButton("Записи");
        exitButton = new JButton("Выйти");

        mb.add(personCardButton);
        mb.add(patientButton);
        mb.add(appointButton);
        mb.add(exitButton);
        add(mb, BorderLayout.NORTH);

        cardPanel.setLayout(cardLayout);
        personCardForm = new PersonCardDoctorForm(frame);
        patientForm = new PatientOfDoctorForm(frame);
        appointDoctorForm = new AppointDoctorForm(frame);
        cardPanel.add(personCardForm, PERSON_CARD_FORM);
        cardPanel.add(patientForm, PATIENT_FORM);
        cardPanel.add(appointDoctorForm, APPOINT_FORM);
        openPersonCardController();

        personCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPersonCardController();
            }
        });

        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPacientController();
            }
        });

        appointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAppointController();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }
    public void openPersonCardController(){
        personCardButton.setEnabled(false);
        patientButton.setEnabled(true);
        appointButton.setEnabled(true);
        setForm(PERSON_CARD_FORM);
    }
    public void openPacientController(){
        patientButton.setEnabled(false);
        personCardButton.setEnabled(true);
        appointButton.setEnabled(true);
        setForm(PATIENT_FORM);
    }
    public void openAppointController(){
        patientButton.setEnabled(true);
        personCardButton.setEnabled(true);
        appointButton.setEnabled(false);
        setForm(APPOINT_FORM);
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
        setForm(PERSON_CARD_FORM);
        frame.setSize(400, 600);
    }
    public void updateForm(String windowName){
        switch (windowName){
            case PERSON_CARD_FORM:
                personCardForm.updateForm();
                break;
            case PATIENT_FORM:
                patientForm.updateForm();
                break;
            case APPOINT_FORM:
                appointDoctorForm.updateForm();
                break;
        }
    }
}
