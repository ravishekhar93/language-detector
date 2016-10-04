package ds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FinalList {
	
	//Main Dictionary
	static public LinkedHashMap<String, ArrayList<Boolean>> repository = new LinkedHashMap<>();
	private LinkedHashMap<String, Integer> langKey = new LinkedHashMap<>();
	
	//Number of languages passed
	private int size;
	
	//=================Internal Functions=====================================
	private void addAllLanguages() throws IOException {
		for(String lang: langKey.keySet()){
			addLanguage(lang);
		}
	}
	
	private void addKeyword(String keyword, int langKey) {
		ArrayList<Boolean> temp = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			  temp.add(false);
			}
		//if keyword exists
		if (repository.containsKey(keyword)) {
			temp = repository.get(keyword);
			temp.set(langKey, true);
		}
		//if keyword does not exist
		else {
			for (int i = 0; i < temp.size(); i++) {
				if (i == langKey) {
					temp.set(i, true);
				} else
					temp.set(i, false);
			}
		}
		// add to repository
		repository.put(keyword, temp);
	}
	
	private void addLanguage(String languageName) throws IOException{
		BufferedReader br;
		String sCurrentLine;

		br = new BufferedReader(new FileReader("langSpec\\"+languageName+".txt"));
		while ((sCurrentLine = br.readLine()) != null) {
			//System.out.println(sCurrentLine);
			addKeyword(sCurrentLine.trim(), langKey.get(languageName));
		}
		br.close();
	}
	
	private String[] getCodeTokens(String code) {
		String[] dirtyTokens = code.split("[{}();,.\" \\[\\]]");
		ArrayList<String> cleanTokens = new ArrayList<>();
		
		for(int i=0;i<dirtyTokens.length;i++){
			String dirtyToken=dirtyTokens[i].trim();
			if(dirtyToken.length()!=0){
			cleanTokens.add(dirtyToken);
			}
		}
		return cleanTokens.toArray(new String[cleanTokens.size()]);
	}
	
	private int[] predictFromPattern(String code) {
		int[] weight = new int[size];
	/*	 Pattern semicolon = Pattern.compile(";");
		 Pattern main = Pattern.compile("public static void main \\(String");
		 Pattern cmain = Pattern.compile("main\\(\\)");
		 
		 */
		// Matcher matcher = semicolon.matcher(code);
		 //If semicolon doesnt exists in code
		/* System.out.println(matcher.matches());
		 if(!matcher.matches()){
			 weight[langKey.get("java")]-=500;
			 weight[langKey.get("cpp")]-=500;
			 weight[langKey.get("javascript")]-=500;
		 }
		
		 matcher = main.matcher(code);
		 //If main doesnt exists in code
		 if(matcher.matches()){
			 System.out.println("Must be in");
			 weight[langKey.get("java")]+=500;
		 }
		 
		 matcher = cmain.matcher(code);
		 //If main doesnt exists in code
		 if(matcher.matches()){
			 weight[langKey.get("cpp")]+=500;
		 }*/
		 
		 if(!code.contains(";")){
			 weight[langKey.get("java")]-=500;
			 weight[langKey.get("cpp")]-=500;
			 weight[langKey.get("javascript")]-=500;
		 }
		 
		 if(code.contains("public static void main(String")){
			 weight[langKey.get("java")]+=500;
		 }
		 
		 if(code.contains("void main()")){
			 weight[langKey.get("cpp")]+=500;
		 }
		 
		return weight;

	}

	public String predictLanguage(String code){
		
		String[] tokens=getCodeTokens(code);
		String prediction="";
		int[] countdown=predictFromPattern(code);
		
		for(int i=0; i<tokens.length;i++){
			ArrayList<Boolean> temp=repository.get(tokens[i]);
			if(temp!=null){
			for(int j=0; j<temp.size();j++){
				if(temp.get(j)==true)
				countdown[j]++;
			}
			}
		}
		
		int max=countdown[0];
		
		for(int i=1; i<countdown.length;i++){
			if(max<countdown[i]){
				max=countdown[i];
			}
		}
		
		for(int i=0; i<countdown.length;i++){
			if(countdown[i]==max){
				for (String o : langKey.keySet()) {
				      if (langKey.get(o).equals(i)) {
				    	  prediction+=" "+o;
				      }
				}
			}
		}
		
		return prediction;
		
	}
	
	//==============End of Internal Functions==================================
	
	public  String getInputFile(String fileName) throws IOException{
		BufferedReader br;
		StringBuilder code=new StringBuilder();
		String sCurrentLine;
		
		br = new BufferedReader(new FileReader("langSpec\\input\\"+fileName+".txt"));
		while ((sCurrentLine = br.readLine()) != null) {
			code.append(sCurrentLine+"\n");
		}
		br.close();
		return code.toString();
	}
	
	public FinalList(String langConfigFile) throws NumberFormatException, IOException{
		BufferedReader br;
		String sCurrentLine;
		int num=0;
		
		br = new BufferedReader(new FileReader("langSpec\\config\\"+langConfigFile+".txt"));
		while ((sCurrentLine = br.readLine()) != null) {
			String languages[] = sCurrentLine.split(",");
			for(String language: languages){
				langKey.put(language,num);
				++num;	
			}
		}
		br.close();
		size=num;
		addAllLanguages();
	}
	
	public static void main(String[] args) {
		
		try {
			
			FinalList list = new FinalList("languageList");
			
			String code = list.getInputFile("testFile");
		    
		    String result = list.predictLanguage(code);
		    System.out.println(result);
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
