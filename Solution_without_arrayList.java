import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    /************* Start Build Tree class **********************/
    static class tree {
        public tree[] childs = new tree[27];
        public char key;
        tree(){
            for(int i = 0; i < 27; i++){
                this.childs[i] = null;
            }
        }
        tree(char data){
            for(int i = 0; i < 27; i++){
                this.childs[i] = null;
            }
            this.key = data;
        }
    }
    /************* End Build Tree class **********************/
    
    /************* Start insert function **********************/
    static tree insert(tree root, String word){
        tree tmp = root;
        for(int i = 0; i < word.length(); i++){
            int index;
            if(i == 0)
                index = (int)word.charAt(i) - 'A';
            else 
                index = (int)word.charAt(i) - 'a';

            if(tmp.childs[index] == null){
                tree newNode = new tree(word.charAt(i));
                tmp.childs[index] = newNode;
            }
            tmp = tmp.childs[index];
        }
        tree node = new tree('$');
        tmp.childs[26] = node;

        return root;
    }
    /************* End insert function **********************/
    
    /************* Start search function **********************/
    static boolean search(tree root, String word){
        tree tmp = root;
        for(int i = 0; i < word.length(); i++){
            int index;
            if(i == 0)
                index = (int)word.charAt(i) - 'A';
            else
                index = (int)word.charAt(i) - 'a';

            if(tmp.childs[index] == null)
                return false;
            else{
                tmp = tmp.childs[index];
            }
        }
        if(tmp.childs[26] == null || tmp.childs[26].key != '$')
            return false;
        return true;
    }
    /************* End search function **********************/
    
    /************* Start delete function **********************/
    static tree delete(tree root, String word){
        
        if(!search(root, word))
            return root;
        
        tree tmp, p = new tree();
        int index;
        int j = 0;
        while(j != word.length()){
            tmp = root;
            for(int i = 0; i < (word.length() - j); i++){
                if(i == 0)
                    index = (int)word.charAt(i) - 'A';
                else
                    index = (int)word.charAt(i) - 'a';
                
                p = tmp;
                tmp = tmp.childs[index];
            }
            if(j == 0)
                tmp.childs[26] = null;
            
            int count = 0;
            for(int i = 0; i < 27; i++){
                if(tmp.childs[i] != null)
                    count++;
            }
            
            if(count == 0){
                for(int i = 0; i < 27; i++){
                    if(p.childs[i] != null && p.childs[i].key == tmp.key){
                        p.childs[i] = null;
                        break;
                    }
                }
            }
            else
                return root;
            
            j++;    
        }
        return root;
    }
    /************* End delete function **********************/
    
    /************* Start startwith function **********************/
    static boolean startwith(tree root, String word){
        tree tmp = root;
        int index;
        for(int i = 0; i < word.length(); i++){
            if(i == 0)
                index = (int)word.charAt(i) - 'A';
            else
                index = (int)word.charAt(i) - 'a';
            
            if(tmp.childs[index] == null)
                return false;
            else{
                tmp = tmp.childs[index];
            }
        }
        
        printWord(tmp, word);
        return true;
        
    }
    
    static void printWord(tree root, String word){
        tree tmp = root;
        
        
        if(tmp.childs[26] != null && tmp.childs[26].key == '$')
            System.out.println(word);
        
        for(int i = 0; i < 27; i++){
            if(tmp.childs[i] != null){
                String tmpWord = word;
                tmpWord += tmp.childs[i].key;
                printWord(tmp.childs[i], tmpWord);
            }
        }
    }
    /************* End startwith function **********************/
    
    /************* Start longest function **********************/
    static void longest(tree root){
        String newWord = "";
        newWord = getAllStrings(root, newWord, "");
        System.out.println(newWord);
    }
    
    static String getAllStrings(tree root, String word,String longWord){
        if(root.childs[26] != null && root.childs[26].key == '$'){
            if(longWord.length() < word.length())
                longWord = word;
        }
        
        for(int i = 0; i < 27; i++){
            if(root.childs[i] != null){
                String tmpWord = word;
                tmpWord += root.childs[i].key;
                longWord = getAllStrings(root.childs[i], tmpWord, longWord);
            }
        }
        return longWord;
    }
    /************* End longest function **********************/
    
    /********************* Start main *******************/
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        tree root = new tree();
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        String s;
        String[] commands = new String[2];
        for(int i = 0; i <= N; i++){
            s = in.nextLine();
            commands = s.split(" ");
            switch(commands[0]){
                case "insert":
                    root = insert(root, commands[1]);
                    break;
                case "search":
                    System.out.println(search(root, commands[1]) ? "YES" : "NO");
                    break;
                case "delete":
                    root = delete(root, commands[1]);
                    break;
                case "startwith":
                    startwith(root, commands[1]);
                    break;
                case "longest":
                    longest(root);
                    break;
                // default:
                //     System.out.println("Wrong command");
            }
        }
    }
    /********************* End main *******************/
}