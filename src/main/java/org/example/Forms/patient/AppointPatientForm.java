package org.example.Forms.patient;

import org.example.BD;
import org.example.Forms.doctor.modal.InfoAppointForm;
import org.example.MainForm;
import org.example.entity.Appointed;
import org.example.entity.Doctor;
import org.example.entity.MedUser;
import org.example.entity.Patient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointPatientForm extends JPanel {
    private JList list1;
    private JButton infoButton;
    private JPanel panel;
    JFrame frame;
    List<Appointed> appointed = new ArrayList<>();

    public AppointPatientForm(JFrame frame){
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
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoDoctorForm();
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButton();
            }
        });
    }
    public void updateList(){
        MedUser user = MainForm.getCurUser();
        if(user == null)
            return;
        Patient patient = BD.getPatientById(user.targetId);
        appointed = BD.getAppointedByPatientID(patient.id);
        List<String> names = appointed.stream().map(a -> "[" + a.date + "] " + a.doctor.profession.name).collect(Collectors.toList());
        list1.setListData(names.toArray());
    }
    public void updateForm(){
        updateList();
        updateButton();
    }
    public void profDoctorForm(){

    }

    public void updateButton(){
        if(list1.getSelectedIndex() >= 0){
            infoButton.setEnabled(true);
        }else{
            infoButton.setEnabled(false);
        }
    }

    public void infoDoctorForm(){
        Appointed a = appointed.get(list1.getSelectedIndex());
        InfoAppointForm.showDialog(frame, a);
    }
}
