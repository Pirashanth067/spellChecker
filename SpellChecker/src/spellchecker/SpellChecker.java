/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spellchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static spellchecker.Spelling.corpus;

/**
 *
 * @author Pirashanth
 */
public class SpellChecker {
    static String corpus="corpus-challenge5.txt";
    static List<String> records;
    String word;
    
  SpellChecker() {}

  
  //function to remove Duplicate in HashSet after counted the frequency 
  public static <T> void removeDuplicate(List <T> list) {
    HashSet <T> h = new HashSet<T>(list);
    list.clear();
    list.addAll(h);
  }
    
  //reading file function
   List<String> readFile(String filename,Boolean check)
  {      
        records = new ArrayList<String>(); 
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename)); //read file 
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] arr = line.split(" ");//split word using space 
                 for ( String ss : arr) 
                 {
                     ss = ss.replaceAll("[^a-zA-Z0-9\\-']",""); //clearing unwanted symbols from words
                     ss = ss.replaceAll("--", " ");
                     ss = ss.replaceAll("''", " ");
                     ss = ss.replaceAll("\\d", "");

                     if(ss.length()>2){
                        String[] newarr = ss.split(" ");
                        if(newarr.length>2)
                        {
                            for ( String mystring : newarr) 
                            {
                                    records.add(mystring); //adding filtered word in Hashmap
                                    
                            }
                        }
                        else{
                            records.add(ss);
                        }
                     }
                }
            }
            if(check==true){
                Set<String> distinct = new HashSet<>(records);
                for (String s: distinct) {
                    System.out.println(s + ": " + Collections.frequency(records, s));//display the hashmap and frequency 
                }
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
        
  }
  
  public void comparewords(String word){
    this.word=word;
    int set=0;
    int wordcount=0,inputcount=0;
    String wordlowercase;
    List<String> lexicographicallist = new ArrayList<String>();
    List<Integer> countlist = new ArrayList<Integer>();
    word = word.replace(" " , "");
    Set<String> distinct = new HashSet<>(records);
    for (String s: distinct) {
        wordcount=s.length();
        inputcount=word.length();
        
        //for equal words
        if(s.equals(word)){
            String slowercase=s.toLowerCase();
            System.out.println(slowercase);
            set=1;
            break;
            
            //One character in the string gets deleted incorrectly and One character in the string is incorrectly replaced by another one
        }else if(wordcount-1==inputcount){
            int count1=0,count2=0,firstvalue=0,secondvalue=0,totalsum=0;
             try{
                for(int i=0;i<inputcount;i++){//looping from front to compare each letter
                    if(s.charAt(i)== word.charAt(i)){
                        count1++;//count the match letter
                    }else if(s.charAt(i)!= word.charAt(i)){ //when the word is not match it will store the value and break the loop
                        firstvalue=count1;
                        break;
                    }
                }
                for(int i=wordcount-1;i>=0;i--){//looping from behind to compare each letter
                    if(s.charAt(i)== word.charAt(i-1)){//matching the letters
                        count2++;//count the match letter
                    }else if(s.charAt(i)!= word.charAt(i-1)){//when the word is not match it will store the value and break the loop
                        secondvalue=count2;
                        break;
                    }
                }
            } catch(StringIndexOutOfBoundsException e){
            }
            totalsum=firstvalue+secondvalue;//adding both compared value
            if(wordcount==totalsum+1){//if compared value one letter less than counted letter
                String slowercase=s.toLowerCase();//changing to lower case
                lexicographicallist.add(slowercase);
                countlist.add(Collections.frequency(records, s));
                set=3;
            }
        }else if(wordcount+1==inputcount){//The user ends up inserting one extra character somewhere in the string
            int count1=0,count2=0,firstvalue=0,secondvalue=0,totalsum=0;
            char letter1=':',letter2=':';
            String slowercase=s.toLowerCase(); // changing string to lowercase to display as lowercase
            String letterstr1= "";
            String letterstr2= "";;
            for(int i=0;i<inputcount;i++){//looping from front to compare each letter
                 try{
                    if(s.charAt(i)== word.charAt(i)){//matching the letters
                        letter1=word.charAt(i);
                        letterstr1=Character.toString(letter1);
                        count1++;
                    }else if(s.charAt(i)!= word.charAt(i)){//when the word is not match 

                        if(letter1==word.charAt(i)){//if the word has 2 unmatched letter contineously it will store both values and break the loop
                            letter2=word.charAt(i);
                            letterstr2=Character.toString(letter2);
                        }
                        firstvalue=count1;
                        break;
                    }
                   } catch(StringIndexOutOfBoundsException e){
                   }
            }

            for(int i=wordcount-1;i>=0;i--){//looping from behing to compare each letter
                try{
                if(s.charAt(i)== word.charAt(i+1)){//matching the letters
                    count2++;
                }else if(s.charAt(i)!= word.charAt(i-1)){
                    
                        secondvalue=count2;
                        break;
                    
                }
                } catch(StringIndexOutOfBoundsException e){
                }
            }
            totalsum=firstvalue+secondvalue-1;
            if (letterstr1.equals(letterstr2)){//if the both letters same
                if(wordcount==totalsum){ //and if worldcout and totalsum is same 
                    lexicographicallist.add(slowercase);
                    countlist.add(Collections.frequency(records, s));
                    set=4;
                    break;
            }else{
                if(wordcount==totalsum){
                    lexicographicallist.add(slowercase);
                    countlist.add(Collections.frequency(records, s));
                    set=4;
                    break;
                }
            }
        }
    }else if(wordcount==inputcount){//While typing hurriedly, the user ends up swapping one pair of consecutive characters
            String slowercase=s.toLowerCase();
            int count=0;
            try {
                for(int i=0;i<wordcount;i++){//mathching the whole word one by one in for loop
                    if(s.charAt(i)== word.charAt(i)){//counting matching letter
                        count++;
                    }else if(s.charAt(i)== word.charAt(i+1) && s.charAt(i+1)== word.charAt(i)){//if there are 2 letters continuesly doesnt match 
                        //also first letter of the input word match with 2nd letter of the real word and
                        //  second letter of the input word match with first letter of the real word
                        count=count+2; //add 2 in the counts
                    }
                }
                } catch(StringIndexOutOfBoundsException e){
                }
            
            if(count==wordcount || count+1==wordcount){
                lexicographicallist.add(slowercase);
                countlist.add(Collections.frequency(records, s));
                set=2;
      } 
     }
   }
    wordlowercase=word.toLowerCase();
    if( set!=1){
    lexicographical(lexicographicallist,wordlowercase,countlist);
    lexicographicallist.clear();
   }
    
  }
  
  
   public void lexicographical(List<String> list,String word,List<Integer> count){
    Collections.sort(list);
    List<Integer> countlist = new ArrayList<Integer>();
    if(list.isEmpty()){
        System.out.printf("word is not available in the dictionary: %s%n%n", word);
    }else{
        for(String s : list){
            countlist.add(Collections.frequency(records, s));
        }
        System.out.println(list.get(countlist.indexOf(Collections.max(countlist))));
    }

   }
}