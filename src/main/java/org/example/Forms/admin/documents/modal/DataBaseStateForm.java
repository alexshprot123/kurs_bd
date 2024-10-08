package org.example.Forms.admin.documents.modal;

import org.example.BD;
import org.example.DocumentWriter;
import org.example.entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseStateForm extends JDialog{
    private JTextField nameTextField;
    private JLabel errorMessageLabel;
    private JButton backButton;
    private JButton okButton;
    private JPanel panel;

    public DataBaseStateForm(JFrame parent){
        super(parent, "", true);
        setContentPane(panel);
        setSize(400, 200);
        errorMessageLabel.setVisible(false);

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
            List<Integer> counts = new ArrayList<>();
            counts.add(BD.getAllDisease().size());
            counts.add(BD.getAllDoctors().size());
            counts.add(BD.getAllPatient().size());
            counts.add(BD.getAllProfessions().size());
            counts.add(BD.getAllAppointed().size());
            counts.add(BD.getAllMonitors().size());
            DocumentWriter.dataBaseDocument(counts, nameTextField.getText());
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            System.out.println(dateTimeParseException.getMessage());
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent) {
        DataBaseStateForm dialog = new DataBaseStateForm(parent);
        dialog.setVisible(true);
    }
}
