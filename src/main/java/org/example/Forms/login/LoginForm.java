package org.example.Forms.login;

import org.example.BD;
import org.example.MainForm;
import org.example.entity.MedUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoginForm extends JPanel {
    private JTextField loginTextField;
    private JTextField passwordTextField;
    private JButton enterButton;
    private JPanel panel;
    JFrame frame;

    public LoginForm(JFrame frame){
        this.frame = frame;
        add(panel);
        setSize(-1, -1);
        setVisible(true);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }
    public void login(){
        try {
            MedUser user = BD.getUser(loginTextField.getText(), passwordTextField.getText());
            if(user == null)
                throw new Exception("Неправильный логин или пароль");
            MainForm.openForm(user);
        }catch (Exception ex){
            System.out.println("Неправильный логин или пароль");
        }
    }
    public void updateForm(){
        loginTextField.setText("");
        passwordTextField.setText("");
        frame.setSize(400, 200);
    }
}
