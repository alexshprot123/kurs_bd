package org.example.Forms.doctor.modal;

import org.example.Consts;
import org.example.entity.Appointed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class InfoAppointForm extends JDialog{
    private JTextField fioTextField;
    private JTextField dataTextField;
    private JTextField profTextField;
    private JButton backButton;
    private JPanel panel;
    Appointed appointed;

    public InfoAppointForm(JFrame parent, Appointed appointed){
        super(parent, "", true);
        this.appointed = appointed;
        setContentPane(panel);
        setSize(400, 300);

        fioTextField.setText(appointed.patient.name);
        profTextField.setText(appointed.doctor.profession.name);
        dataTextField.setText(appointed.date.format(Consts.DATE_FORMAT));


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    public static void showDialog(JFrame parent, Appointed appointed) {
        InfoAppointForm dialog = new InfoAppointForm(parent, appointed);
        dialog.setVisible(true);
    }
}
