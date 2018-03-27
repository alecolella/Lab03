package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dictionary {
	
	private List <String> dizionario;
	
	private String lingua;
	
	
	
	private int numErrate;
	List<RichWord> parole = new LinkedList<RichWord>();
	
	public Dictionary() {
		
		dizionario = new ArrayList<String>();
		parole = new ArrayList<RichWord>();
		numErrate = 0;
		
	}
	
	public void loadDictionary(String language) {
		if (language.toLowerCase().equals("italiano"))
			lingua = "rsc/Italian.txt";
		else if (language.toLowerCase().equals("english"))
			lingua = "rsc/English.txt";
		try {
			FileReader fr = new FileReader(lingua);
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				// Aggiungere parola alla struttura dati
				dizionario.add(word);
			}
			br.close();
		} catch (IOException e){
			System.out.println("Errore nella lettura del file");
		}		
		
	}
	
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		for(String s : inputTextList) {
			if(dizionario.contains(s)) {
				RichWord parola = new RichWord(s, true);
				parole.add(parola);
			}else {
				RichWord parola = new RichWord(s, false);
				parole.add(parola);
			}
		}
		
		return parole;
	}


	public int getNumErrori() {
		return numErrate;
	}
	
	
	
	
	public String stampaErrate() {
		String errate = "";
		for(RichWord r : parole) {
			if(r.isCorretta()==false) {
				errate+=r.getParola()+"\n";
				numErrate++;
			}
			
		}
		return errate;
	}

	public void clear() {
		dizionario.clear();
		numErrate = 0;
		parole.clear();
		
		
	}

}
