package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.DocumentWriter;
import org.example.entity.Disease;
import org.example.entity.Doctor;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListDoctorsByDiseaseForm extends JDialog{
    private JComboBox profComboBox;
    private JTextField nameTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JPanel panel;

    List<Disease> diseases = new ArrayList<>();

    public ListDoctorsByDiseaseForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 200);
        errorMessageLabel.setVisible(false);
        diseases = BD.getAllDisease();
        List<String> profsName = diseases.stream().map(p -> p.name).collect(Collectors.toList());
        for(String prof : profsName)
            profComboBox.addItem(prof);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(createDocument())
                    dispose();
            }
        });
    }

    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public boolean createDocument(){
        try {
            if(nameTextField.getText().isEmpty()){
                showError("Имя файла не указано");
                return false;
            }
            List<Profession> professions = BD.getDoctorByDisease(diseases.get(profComboBox.getSelectedIndex()));

            DocumentWriter.professionDocument(professions, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        ListDoctorsByDiseaseForm dialog = new ListDoctorsByDiseaseForm(parent);
        dialog.setVisible(true);
    }
}
