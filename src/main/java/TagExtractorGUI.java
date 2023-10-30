import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class TagExtractorGUI extends JFrame {
    private JTextArea textArea;
    private JButton selectTextFileButton;
    private JButton extractTagsButton;
    private File textFile;
    private Map<String, Integer> tagFrequencyMap;

    public TagExtractorGUI() {
        setTitle("Tag Extractor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        selectTextFileButton = new JButton("Select Text File");
        extractTagsButton = new JButton("Extract Tags");

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(selectTextFileButton);
        panel.add(extractTagsButton);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        selectTextFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    textFile = fileChooser.getSelectedFile();
                }
            }
        });

        extractTagsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFile != null) {
                    tagFrequencyMap = extractTagsFromFile(textFile);
                    displayTags();
                } else {
                    textArea.setText("Select a text file first.");
                }
            }
        });
    }

    private Map<String, Integer> extractTagsFromFile(File textFile) {
        Map<String, Integer> tagFrequencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty()) {
                        tagFrequencyMap.put(word, tagFrequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tagFrequencyMap;
    }

    private void displayTags() {
        textArea.setText("");
        for (Map.Entry<String, Integer> entry : tagFrequencyMap.entrySet()) {
            textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TagExtractorGUI gui = new TagExtractorGUI();
            gui.setVisible(true);
        });
    }
}

