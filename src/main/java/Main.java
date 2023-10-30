public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TagExtractorGUI gui = new TagExtractorGUI();
                gui.setVisible(true);
            }
        });
    }
}
