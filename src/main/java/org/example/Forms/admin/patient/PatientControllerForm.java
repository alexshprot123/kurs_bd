package org.example.Forms.admin.patient;

import org.example.BD;
import org.example.Forms.admin.doctor.modal.AddDoctorForm;
import org.example.Forms.admin.doctor.modal.ChangeDoctorForm;
import org.example.Forms.admin.doctor.modal.DeleteDoctorForm;
import org.example.Forms.admin.doctor.modal.InfoDoctorForm;
import org.example.Forms.admin.patient.modal.AddPatientForm;
import org.example.Forms.admin.patient.modal.DeletePatientForm;
import org.example.Forms.admin.patient.modal.InfoPatientForm;
import org.example.entity.Patient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientControllerForm  extends JPanel{
    private JPanel panel;
    private JButton addButton;
    private JButton infoButton;
    private JButton deleteButton;
    private JList list1;
    JFrame frame;
    List<Patient> patients = new ArrayList<>();

    public PatientControllerForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        List<String> strs = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            strs.add("string");
        }
        list1.setListData(strs.toArray());
        updateButton();
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatientForm();
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPatientForm();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatientForm();
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButton();
            }
        });
    }
    public void addPatientForm(){
        AddPatientForm.showDialog(frame);
        updateForm();
    }
    public void deletePatientForm(){
        Patient patient =  patients.get(list1.getSelectedIndex());
        DeletePatientForm.showDialog(frame, patient);
        updateForm();
    }
    public void infoPatientForm(){
        Patient patient =  patients.get(list1.getSelectedIndex());
        InfoPatientForm.showDialog(frame, patient);
        updateForm();
    }

    public void updateButton(){
        if(list1.getSelectedIndex() >= 0){
            deleteButton.setEnabled(true);
            infoButton.setEnabled(true);
        }else{
            deleteButton.setEnabled(false);
            infoButton.setEnabled(false);
        }
    }

    public void updateList(){
        patients = BD.getAllPatient();
        List<String> names = patients.stream().map(d -> d.name).collect(Collectors.toList());
        list1.setListData(names.toArray());
    }

    public void updateForm(){
        updateButton();
        updateList();
    }
}
