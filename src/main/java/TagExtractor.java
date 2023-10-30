import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class TagExtractor {
    public Map<String, Integer> extractTagsFromFile(File textFile, File stopWordsFile) {
        Map<String, Integer> tagFrequencyMap = new HashMap<>();
        Set<String> stopWords = loadStopWords(stopWordsFile);
        String text = readTextFromFile(textFile);
        String[] words = text.split("\\s+");

        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!stopWords.contains(word) && !word.isEmpty()) {
                tagFrequencyMap.put(word, tagFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return tagFrequencyMap;
    }

    private String readTextFromFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Set<String> loadStopWords(File stopWordsFile) {
        Set<String> stopWords = new HashSet<>();
        try {
            String content = readTextFromFile(stopWordsFile);
            String[] lines = content.split("\n");
            for (String line : lines) {
                stopWords.add(line.trim().toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stopWords;
    }
}
