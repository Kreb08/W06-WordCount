package ch.hsr.mge.wordcount.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import ch.hsr.mge.wordcount.data.SerializableList;
import ch.hsr.mge.wordcount.data.WordCount;


/**
 * Implementiert das Interface IWordCounter.
 *
 * @author Peter Buehler
 */
public class WordCounter implements IWordCounter {

	@Override
	public SerializableList<WordCount> countWords(String text) {
		
		// Texttrennzeichen, etc ersetzen
		// Absichtlich "schlechter" Algorithmus
		text = text.replace(".", "");
		text = text.replace(",", "");
		text = text.replace("\"", "");
		text = text.replace("-", "");
		text = text.replace("=", "");
		text = text.replace("|", "");
		
		// String aufteilen und Zaehler setzen
		Hashtable<String, WordCount> hash = new Hashtable<>();
		String[] words = text.split("\\s+");
		for (String word: words) {
			
			// keine Sonderzeichen, nur Kleinschreibung
			word = word.trim().toLowerCase(Locale.getDefault());
			if (word.length() == 0) {
				continue;
			}
			
			WordCount wc = hash.get(word);
			if (wc == null) {
				wc = new WordCount(word);
				hash.put(word, wc);
			}
			wc.increment();
		}
		
		// Zaehler sortieren
		Collection<WordCount> counters = hash.values();
		List<WordCount> list = new ArrayList<WordCount>( counters );
		Collections.sort(list);
		
		// nur 20 hoechste Zaehler als Resultat
		int end = list.size();
		if (end > 20 ){
			end = 20;
		}
		SerializableList<WordCount> result = new SerializableList<>();
		for (int i=0; i<end; i++) {
			result.add(list.get(i));
		}

		return result;
	}
}
