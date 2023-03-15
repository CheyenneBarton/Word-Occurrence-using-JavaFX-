package application;
	
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;

public class Main extends Application {
	
	static HashMap<String, Integer> freq = new HashMap<>();
	Button b;
	
	@Override
	public void start(Stage Stage) {
		try {
			//set title for the stage
			Stage.setTitle("Word Occurence for the Raven Poem");
			
			//create a label
			Label L = new Label("Top 20 words that occur in the Raven Poem");
			
			//create a button
			b = new Button("Click for Results");
			
			//create a stack pane
			TilePane p = new TilePane();
		
			//creat a textarea
			TextArea text = new TextArea();
					
			//add label
			p.getChildren().add(L);
			
			//add button
			p.getChildren().add(b);
			
			//add textarea
			p.getChildren().add(text);
			
			//action event
			EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {  
			
	            public void handle(ActionEvent event)
	            {
	            	if(event.getSource() ==b) 
	            	{
	            		//getting path to the file
	                	Path path = Paths.get("ravenpoem.txt");
	                	
	                	try {
	                		//put the file into a string
	                		String poem = Files.readString((path));
	                		
	                		//convert string to lowercase
	                		poem = poem.toLowerCase();
	                		
	                		//use Regex to only keep letters
	              	      	Pattern p = Pattern.compile("[a-z]+");
	              	      	Matcher m = p.matcher(poem);

	              	      	//each call to find() will find the next word in the string
	              	      	while (m.find()) {
	              	      		//seperated and formatted words are put into a string
	              	      		String word = m.group();	      		
	              	        
	              	        	//get the words
	              	            Integer f = freq.get(word);
	              	            
	              	            //if word is found
	              	            if  (f == null)
	              	            {
	              	            	freq.put(word, 1);
	              	            } 
	              	        //if same word is found, increase count
	              	            else 
	              	            {
	              	            	freq.put(word, f+1);
	              	            }
	              	      	}
	              	      	
	              	      //sort the HashMap to top 20 and in descending order
	              	      freq.entrySet().stream()
	                      .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) 
	                      .limit(20)
	              	      .forEach(i -> text.appendText(i.getKey() + " = " + i.getValue() + "\n"));
	              	     
	              	      
	                	} catch (IOException xIo) {
	                        xIo.printStackTrace();
	                	}
	        		}
	            }
			};
			
			//when button is pressed
			b.setOnAction(handler);
			
			//setting up the scence
			Scene scene = new Scene(p,300,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage.setScene(scene);
			Stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) {
		//launch the application
		launch(args);
	}
}
