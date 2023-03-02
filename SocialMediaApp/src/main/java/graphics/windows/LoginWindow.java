package graphics.windows;



import graphics.DatabaseConnection;
import people.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow extends JFrame implements ActionListener
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel informationLabel;

    public LoginWindow(){
        super("Login");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        informationLabel = new JLabel("");

        loginButton.addActionListener(this);
        createAccountButton.addActionListener(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Username: "),gbc);
        gbc.gridy++;
        panel.add(usernameField,gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"),gbc);
        gbc.gridy++;
        panel.add(passwordField,gbc);
        gbc.gridy++;
        panel.add(informationLabel,gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton,gbc);
        gbc.gridy++;
        panel.add(createAccountButton,gbc);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);
        setVisible(true);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            System.out.println("Login button pressed");
            if(!checkUser(usernameField.getText(),passwordField.getText()))
            {
                informationLabel.setText("The provided infomation is wrong");
                revalidate();
                repaint();
            }
            else{
                dispose();
                System.out.println("Logged in");
                User u = new User(usernameField.getText(),passwordField.getToolTipText());
                UserWindow uw = new UserWindow(u);


            }
        }
        else if(e.getSource() == createAccountButton){
            System.out.println("Creation button pressed");
            dispose();
            CreateAccountWindow caw = new CreateAccountWindow();
        }
    }


    public boolean checkUser(String username, String password)
    {
        boolean isValid = false;
        try(Connection conn = DatabaseConnection.getConnection())
        {
            String sql = "select * from users where username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                String hashedPassword = rs.getString("password");
                if(hashedPassword.equals(password)){
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }



}
