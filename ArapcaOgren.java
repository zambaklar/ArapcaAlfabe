import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArapcaOgren extends JFrame {
    private JLabel kelimeLabel;
    private JLabel anlamLabel;
    private JTextField anlamKutusu;
    private JButton kontrolButonu;
    private JButton[] secimButonlari;

    private Map<Character, String> letterAnlamMap;
    private Random random;

    public ArapcaOgren() {
        setTitle("Arapça Öğren");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel letterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        kelimeLabel = new JLabel();
        kelimeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        letterPanel.add(kelimeLabel);

        mainPanel.add(letterPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        anlamLabel = new JLabel("Anlam:");
        anlamKutusu = new JTextField(15);
        kontrolButonu = new JButton("Kontrol");

        panel.add(anlamLabel);
        panel.add(anlamKutusu);
        panel.add(kontrolButonu);

        mainPanel.add(panel, BorderLayout.CENTER);

        secimButonlari = new JButton[5];
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        for (int i = 0; i < 5; i++) {
            secimButonlari[i] = new JButton();
            secimButonlari[i].setVisible(false);
            secimButonlari[i].addActionListener(new ButtonClickListener());
            buttonPanel.add(secimButonlari[i]);
        }
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        letterAnlamMap = createLetterAnlamMap();
        random = new Random();

        kontrolButonu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KontrolAnlam();
            }
        });

        displayRandomLetter();

        setVisible(true);
    }

    private Map<Character, String> createLetterAnlamMap() {
        Map<Character, String> map = new HashMap<>();
        map.put('ا', "elif");
        map.put('ب', "be");
        map.put('ت', "te");
        map.put('ث', "se");
        map.put('ج', "cim");
        map.put('ح', "ha");
        map.put('خ', "hi");
        map.put('د', "dal");
        map.put('ذ', "zal");
        map.put('ر', "ra");
        map.put('ز', "ze");
        map.put('س', "sin");
        map.put('ش', "şın");
        map.put('ص', "sad");
        map.put('ض', "dad");
        map.put('ط', "tı");
        map.put('ظ', "zı");
        map.put('ع', "ayın");
        map.put('غ', "ğayn");
        map.put('ف', "fe");
        map.put('ق', "kaf");
        map.put('ك', "kef");
        map.put('ل', "lam");
        map.put('م', "mim");
        map.put('ن', "nun");
        map.put('ه', "he");
        map.put('و', "vav");
        map.put('ي', "ye");
        return map;
    }

    private void displayRandomLetter() {
        Object[] letters = letterAnlamMap.keySet().toArray();
        char randomLetter = (char) letters[random.nextInt(letters.length)];
        kelimeLabel.setText(String.valueOf(randomLetter));
        anlamKutusu.setText("");

        int[] indices = generateRandomIndices();

        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                secimButonlari[i].setText(letterAnlamMap.get(randomLetter));
            } else {
                char randomOption = (char) letters[indices[i - 1]];
                secimButonlari[i].setText(letterAnlamMap.get(randomOption));
            }
            secimButonlari[i].setVisible(true);
        }
    }

    private int[] generateRandomIndices() {
        int[] indices = new int[4];
        int count = 0;
        while (count < 4) {
            int randomIndex = random.nextInt(letterAnlamMap.size());
            if (randomIndex != kelimeLabel.getText().charAt(0)) {
                indices[count] = randomIndex;
                count++;
            }
        }
        return indices;
    }

    private void KontrolAnlam() {
        char displayedLetter = kelimeLabel.getText().charAt(0);
        String enteredAnlam = anlamKutusu.getText().trim();
        String correctAnlam = letterAnlamMap.get(displayedLetter);

        if (enteredAnlam.equalsIgnoreCase(correctAnlam)) {
            JOptionPane.showMessageDialog(this, "Doğru!");
        } else {
            JOptionPane.showMessageDialog(this, "Yanlis. Doğru cevap: " + correctAnlam);
        }

        displayRandomLetter();
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String selectedOption = clickedButton.getText();
            String correctAnlam = letterAnlamMap.get(kelimeLabel.getText().charAt(0));

            if (selectedOption.equalsIgnoreCase(correctAnlam)) {
                JOptionPane.showMessageDialog(ArapcaOgren.this, "Doğru!");
            } else {
                JOptionPane.showMessageDialog(ArapcaOgren.this, "Yanlis. Doğru cevap: " + correctAnlam);
            }

            displayRandomLetter();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArapcaOgren();
            }
        });
    }
}