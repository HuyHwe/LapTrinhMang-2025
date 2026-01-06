//import RMI.CharacterService;
//
//public class WSCharacterService {
//
//    public static void main(String[] args) {
//
//        CharacterService service = new CharacterService();
//        CharacterServicePortType port = service.getCharacterServicePort();
//
//        String studentCode = "YOUR_STUDENT_CODE";
//        String qCode = "vH69Ff0Y";
//
//        List<String> inputList = port.requestStringArray(studentCode, qCode);
//
//        Map<Integer, List<String>> groups = new HashMap<>();
//
//        for (String word : inputList) {
//            int vowelCount = countVowels(word);
//            groups.computeIfAbsent(vowelCount, k -> new ArrayList<>()).add(word);
//        }
//
//        List<String> result = new ArrayList<>();
//
//        for (List<String> words : groups.values()) {
//            Collections.sort(words);
//            result.add(String.join(", ", words));
//        }
//
//        port.submitCharacterStringArray(studentCode, qCode, result);
//    }
//
//    private static int countVowels(String s) {
//        int count = 0;
//        String vowels = "aeiouAEIOU";
//        for (char c : s.toCharArray()) {
//            if (vowels.indexOf(c) >= 0) count++;
//        }
//        return count;
//    }
//}
