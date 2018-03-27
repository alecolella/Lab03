package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	private Dictionary model;
	private List<String> input = new ArrayList<String>();

    public void setDictionary(Dictionary model) {
		this.model = model;
		
		choiceBox.getItems().add("English");
        choiceBox.getItems().add("Italiano");
        choiceBox.setValue("English"); //visualizzazione di default
        
        
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label labelNumError;

    @FXML
    private Button btnClear;

    @FXML
    private Label labelTimeError;

    @FXML
    void doClearText(ActionEvent event) {
    	
    	//clear struttura dati
    	model.clear();
    	//cancella dalle caselle di testo
    	labelNumError.setDisable(true);
    	labelTimeError.setDisable(true);
    	txtInput.clear();
    	txtOutput.clear();
    	
    	

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long inizio = System.nanoTime();
    	
    	//abilita label errori e elimina vecchio output
    	labelNumError.setDisable(false);
    	labelTimeError.setDisable(false);    	
    	txtOutput.clear();
    	input.clear();
    	model.clear();
    	//memorizzare lingua selezionata
    	String language = choiceBox.getValue();    	
    	model.loadDictionary(language);
    	//richiama spellChecker del model
    	String inputUtente = txtInput.getText();
    	   //controllo string vuota
    	    if (inputUtente == null || inputUtente.length() == 0) {    	    	
			txtInput.setText("Insert one or more words in the selected language.");
			long stop = System.nanoTime();
	    	double tempo= (stop-inizio)/Math.pow(10, 9);
	    	labelTimeError.setText("Spell Check completed in "+tempo+" seconds");
			return;
		}
    	
    	    String inputFinale = inputUtente.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]\\\"", "");
    	    StringTokenizer st = new StringTokenizer(inputFinale.toLowerCase());
    		while(st.hasMoreTokens()) {
        		String token = st.nextToken();
        		input.add(token);
        	}
    		model.spellCheckText(input);
    	
    		//4.restituisce elenco parole errate
    		txtOutput.setText(model.stampaErrate());
    		
    		//5.settare label con numero di errori 
    		labelNumError.setText("The text contains " +model.getNumErrori()+" errors");
    		
    		//6.settare label con tempo impiegato da spellcheck
    		long stop = System.nanoTime();
        	double tempo= (stop-inizio)/Math.pow(10, 9);
        	labelTimeError.setText("Spell Check completed in "+tempo+" seconds");

    	
    	

    }
    
   

    @FXML
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert labelNumError != null : "fx:id=\"labelNumError\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert labelTimeError != null : "fx:id=\"labelTimeError\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        
        
        

    }

	
}

