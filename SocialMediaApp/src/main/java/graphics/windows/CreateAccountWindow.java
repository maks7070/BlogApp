package graphics.windows;

import graphics.DatabaseConnection;
import graphics.windows.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateAccountWindow extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton createAccount;
    private JLabel informationLabel;
    private JButton backButton;


    public CreateAccountWindow()
    {
        super("Create account");
        setSize(400,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        usernameField = new JTextField(20);
        passwordField1 = new JPasswordField(20);
        passwordField2 = new JPasswordField(20);
        createAccount = new JButton("Create account");
        informationLabel = new JLabel("");
        backButton = new JButton("Back");


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
        panel.add(passwordField1,gbc);
        gbc.gridy++;
        panel.add(new JLabel("Confirm password:"), gbc);
        gbc.gridy++;
        panel.add(passwordField2,gbc);
        gbc.gridy++;
        panel.add(informationLabel,gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(createAccount,gbc);
        gbc.gridy++;
        panel.add(backButton,gbc);

        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password1 = new String(passwordField1.getPassword());
                String password2 = new String(passwordField2.getPassword());

                if(username.isEmpty() || password1.isEmpty() || password2.isEmpty()){
                    informationLabel.setText("The information given is wrong");
                    revalidate();
                    repaint();
                }
                else if(!password1.equals(password2))
                {
                    informationLabel.setText("The passwords do not match");
                    revalidate();
                    repaint();
                }
                else{
                    createAccount(username,password1);

                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginWindow lw = new LoginWindow();
            }
        });





        add(panel);
        setVisible(true);
    }

    public void createAccount(String username, String password){
        System.out.println("Creating account");
        if(checkUsernameExists(username))
        {
            informationLabel.setText("This username is already taken");
        }else{
            addUserTotheDatabase(username,password);
        }
    }


    public boolean checkUsernameExists(String username)
    {
        try(Connection conn = DatabaseConnection.getConnection()){
            String query = "select count(*) from users where username=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1)>0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public void addUserTotheDatabase(String username, String password)
    {
        try(Connection conn = DatabaseConnection.getConnection())
        {
            String query = "insert into users (username, password) values (?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.executeUpdate();
            System.out.println("User added");
        }catch (Exception e){
         e.printStackTrace();
        }
    }


    //TODO add password security policy
    //TODO add password hashing
    //TODO add sql injection prevention

}
