package nsu.sber.infrastructure.util;

import nsu.sber.domain.port.TransliteratorPort;
import org.springframework.stereotype.Component;

@Component
public class Transliterator implements TransliteratorPort {
    private static final char[] rus = {
            'А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ы','Э','Ю','Я',
            'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ы','э','ю','я'
    };

    private static final String[] lat = {
            "A","B","V","G","D","E","Yo","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","Kh","Ts","Ch","Sh","Sch","Y","E","Yu","Ya",
            "a","b","v","g","d","e","yo","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","kh","ts","ch","sh","sch","y","e","yu","ya"
    };

    @Override
    public String translit(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < rus.length; i++) {
                if (c == rus[i]) {
                    result.append(lat[i]);
                    found = true;
                    break;
                }
            }
            if (!found) result.append(c);
        }
        return result.toString();
    }
}
