package org.example.Forms.patient;

import org.example.Consts;
import org.example.Forms.doctor.PatientOfDoctorForm;
import org.example.Forms.doctor.PersonCardDoctorForm;
import org.example.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientForm extends JPanel {
    private JPanel cardPanel;
    private static final String PERSON_CARD_FORM = "PERSON_CARD_FORM";
    private static final String APPOINT_FORM = "APPOINT_FORM";
    private CardLayout cardLayout = new CardLayout();
    JButton personCardButton;
    JButton appointButton;
    JButton exitButton;
    JFrame frame;
    PersonCardPatientForm personCardForm;
    AppointPatientForm appointPatientForm;
    public PatientForm(JFrame frame){
        this.frame = frame;
        JPanel p = new JPanel();
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        setSize(-1, -1);
        JMenuBar mb = new JMenuBar();

        personCardButton = new JButton("Личная карточка");
        appointButton = new JButton("Записи");
        exitButton = new JButton("Выйти");

        mb.add(personCardButton);
        mb.add(appointButton);
        mb.add(exitButton);
        add(mb, BorderLayout.NORTH);

        cardPanel.setLayout(cardLayout);
        personCardForm = new PersonCardPatientForm(frame);
        appointPatientForm = new AppointPatientForm(frame);
        cardPanel.add(personCardForm, PERSON_CARD_FORM);
        cardPanel.add(appointPatientForm, APPOINT_FORM);
        openPersonCardController();

        personCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPersonCardController();
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
        appointButton.setEnabled(true);
        setForm(PERSON_CARD_FORM);
    }
    public void openAppointController(){
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
            case APPOINT_FORM:
                appointPatientForm.updateForm();
                break;
        }
    }
}
