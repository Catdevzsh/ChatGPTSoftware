import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.tools.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitCloneGUI extends JFrame {

    private JTextField urlField;
    private JButton compileButton, grabUrlButton, cloneButton;
    private JTextArea readmeArea;

    public GitCloneGUI() {
        setTitle("Git Clone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel urlLabel = new JLabel("Repository URL:");
        urlField = new JTextField(30);
        compileButton = new JButton("Compile");
        grabUrlButton = new JButton("Grab URL");
        cloneButton = new JButton("Clone");

        inputPanel.add(urlLabel);
        inputPanel.add(urlField);
        inputPanel.add(compileButton);
        inputPanel.add(grabUrlButton);
        inputPanel.add(cloneButton);

        add(inputPanel, BorderLayout.NORTH);

        readmeArea = new JTextArea();
        readmeArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(readmeArea);

        add(scrollPane, BorderLayout.CENTER);

        compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Compile code
                compileCode();
            }
        });

        grabUrlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Grab URL
                String url = grabUrl();
                if (!url.isEmpty()) {
                    displayMessage("Grabbed URL: " + url);
                } else {
                    displayError("Please enter a valid URL.");
                }
            }
        });

        cloneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clone repository
                cloneRepo();
            }
        });

        setLocationRelativeTo(null);
    }

    private void compileCode() {
        // Compile code
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null, "MyFile.java");
            if (result == 0) {
                displayMessage("Compilation successful.");
            } else {
                displayError("Compilation failed.");
            }
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    private String grabUrl() {
        // Get the URL from the urlField text field
        return urlField.getText().trim();
    }

    private void cloneRepo() {
        // Clone the repository
        String url = grabUrl();
        if (!url.isEmpty()) {
            try {
                Git.cloneRepository()
                        .setURI(url)
                        .setDirectory(new File("/path/to/destination"))
                        .call();
                displayMessage("Repository cloned successfully.");
            } catch (GitAPIException e) {
                displayError(e.getMessage());
            }
        } else {
            displayError("Please enter a valid repository URL.");
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void displayError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GitCloneGUI gui = new GitCloneGUI();
            gui.setVisible(true);
        });
    }
}
// Flames AI 20XX
