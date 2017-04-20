/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Marty
 */
public class Index {
    private static final ArrayList<String> stopwords = new ArrayList<>(Arrays.asList("a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"));
    private static final Map<String, List<Integer>> index = new HashMap<>();
    private static final Map<String, List<Integer>> encryptedIndex = new HashMap<>();
    private static final Map<Integer, String> fileEncoding = new HashMap<>();
    private static final Random rng = new Random();
    private static AES cipher;
    
    // Finds difference between set A and set B (A - B) and returns a new list
    public static <T> List<T> Difference(Collection<T> A, Collection<T> B) {
        List<T> diff = new ArrayList<>(A);

        diff.removeAll(B);
        return diff;
    }
    
    
    // Finds the union between two collections and returns a new list
    public static <T> List<T> union(Collection<T> list1, Collection<T> list2) {
        Set<T> set = new HashSet<>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<>(set);
    }
    
    // Finds the intersection between two collections and returns a new list
    public static <T> List<T> intersection(Collection<T> list1, Collection<T> list2) {
        List<T> list = new ArrayList<>();
    
        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static int GetKeyFromValue(Map<Integer, String> m, String value){
        int key = rng.nextInt();
        for (Entry<Integer, String> entry : m.entrySet()) {
            if (entry.getValue().equals(value)) {
                key = entry.getKey();
            }
        }
        return key;
    }
        
    /**
    * Indexes the given file, or if a directory is given,
    * recurses over files and directories found under the given directory.
    */
    static void indexDocs(Path path) throws IOException, NoSuchAlgorithmException {
        //  TO DO:
        //      - only index appropriate files (txt, html, json, etc)
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        if(file.toString().toLowerCase().endsWith(".txt"))
                            //indexDoc(file);
                            indexEncryptedDoc(file);
                    } catch (IOException ignore) {
                        // don't index files that can't be read.
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            //indexDoc(path);
            indexEncryptedDoc(path);
        }
    }
    /** Indexes a single document */
    static void indexEncryptedDoc(Path file) throws IOException, NoSuchAlgorithmException {
        // Encrypt content of file
        // add randomness to content and name?
        
        List<Integer> files = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(file)) {
            List<String> content = getContent(stream);
            int f = rng.nextInt();
            if(!fileEncoding.containsValue(file.getFileName().toString())) { 
                f = rng.nextInt();
                while(fileEncoding.containsKey(f))
                    f = rng.nextInt();
                fileEncoding.put(f, file.getFileName().toString());
            }
            else {
                f = GetKeyFromValue(fileEncoding, file.getFileName().toString());
            }
            files.add(f);
            
            for(String s : content)
            {
                String encrypted = new String(Base64.encodeBase64(cipher.hashBytes(cipher.encryptString(s))));
                if(encryptedIndex.containsKey(encrypted)) {
                    if(!encryptedIndex.get(encrypted).contains(f))
                        encryptedIndex.get(encrypted).add(f);
                }
                else {
                    encryptedIndex.put(encrypted, files);
                }
            }
            System.out.println("\tAdded " + file.toString());
        }
    }
                       
    /** Indexes a single document */
    static void indexDoc(Path file) throws IOException, NoSuchAlgorithmException {
        // Encrypt content of file
        // add randomness to content and name?
        
        List<Integer> files = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(file)) {
            List<String> content = getContent(stream);
            int f = rng.nextInt();
            if(!fileEncoding.containsValue(file.getFileName().toString())) { 
                f = rng.nextInt();
                while(fileEncoding.containsKey(f))
                    f = rng.nextInt();
                fileEncoding.put(f, file.getFileName().toString());
            }
            else {
                f = GetKeyFromValue(fileEncoding, file.getFileName().toString());
            }
            files.add(f);
            
            for(String s : content)
            {
                if(index.containsKey(s)) {
                    if(!index.get(s).contains(f))
                        index.get(s).add(f);
                }
                else {
                    index.put(s, files);
                }
            }
            System.out.println("\tAdded " + file.toString());
        }
    }
    
    // Reads a document line by line (split by space), and returns a list of strings of all unique words
    // and removes stop words
    static List<String> getContent(InputStream stream) throws IOException{
        BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
        String line = buf.readLine();
        String[] splitLine;
        List<String> content = new ArrayList<>();
        while(line != null){ 
            splitLine = line.split("\\s+");
            for(String word : splitLine){
                word = word.toLowerCase().replaceAll("[^a-z]", "");
                if(!stopwords.contains(word) && !content.contains(word) && word.length() > 2) {
                    content.add(word); 
                }
            }
            line = buf.readLine(); 
        } 
        return content;
    }
    
    // Splits the query into a list by space and returns a list of strings
    private static List<String> SplitQuery(String q) {
        List<String> queries = new ArrayList<>();
        queries.addAll(Arrays.asList(q.split("\\s+")));
        return queries;
    }
    
    // Handle UI for displaying results of a query
    private static void DisplayResults(List<Integer> res) {
        // DISPLAY RESULTS
        if(!res.isEmpty()) {
            System.out.println("\t" + res.size() + " Results: ");
            for(int r : res) {
                System.out.println("\t\t" + fileEncoding.get(r));
            }
        }
        else {
            System.out.println("\tNo results found");
        }
    }
    
    public static void DeleteFiles(String dir, String type) {
        // Lists all files in folder
        File folder = new File(dir);
        File fList[] = folder.listFiles();
        for (File f : fList) {
            if (f.toString().endsWith(type)) {
                boolean success = f.delete();
                if(success == false)
                    System.err.println("Error deleting " + f.toString());
            }
        }
    }
    
    public static void MergeEncryptedSegments(String IndexPath) throws FileNotFoundException, IOException {
        Map<Integer, String> enc = new HashMap<>();
        Map<Integer, Map<String, List<Integer>>> segs = new HashMap<>();
        Map<String, List<Integer>> fa = new HashMap<>();
        Map<String, List<String>> ind = new HashMap<>();
        List<Integer> fi = new ArrayList<>();
        List<String> fis = new ArrayList<>();
        List<Integer> fil = new ArrayList<>();
        List<Integer> fs = new ArrayList<>();
        File folder = new File(IndexPath);
        File fList[] = folder.listFiles();
        BufferedReader buf;
        String line;
        String[] splitLine, content;
        int k = 0, fen = rng.nextInt();
        
        // read the index files
        for (File f : fList) {
            buf = new BufferedReader(new FileReader(f));
            if (f.toString().endsWith(".eencoding")) {
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    enc.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                    line = buf.readLine();
                }            
            }
            else if(f.toString().endsWith(".esegment")) {
                k++;
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    content = splitLine[1].split(",");
                    for(String c : content) {
                        fi.add(Integer.parseInt(c));
                    }
                    fa.put(splitLine[0], fi);
                    line = buf.readLine();
                    fi = new ArrayList<>();
                }      
                segs.put(k, fa);
            }
            buf.close();
        }
        
        // merge the segments into one map
        for(int s : segs.keySet()){
            for(String n : segs.get(s).keySet()){
                fis = new ArrayList<>();
                for(int i : segs.get(s).get(n)) {
                    if(!fis.contains(enc.get(i))) {
                        fis.add(enc.get(i));
                    }
                }

                if(!ind.containsKey(n)) {
                    ind.put(n, fis);
                }
                else {
                    for(String h : fis){
                        if(!ind.get(n).contains(h)){
                            ind.get(n).add(h);
                        }
                    }
                }
            }
        }
        
        // add the index to the overall index and encoding to encoding list
        for(String l : ind.keySet()){
            fil = new ArrayList<>();
            for(String en : ind.get(l)){
                if(!fileEncoding.containsValue(en)) { 
                    fen = rng.nextInt();
                    while(fileEncoding.containsKey(fen))
                        fen = rng.nextInt();
                    fileEncoding.put(fen, en);
                }
                else {
                    fen = GetKeyFromValue(fileEncoding, en);
                }
                fil.add(fen);
            }
            encryptedIndex.put(l, fil);
        }
        
        DeleteFiles(IndexPath, ".esegment");
        DeleteFiles(IndexPath, ".eencoding");
    }
        
    public static void MergeSegments(String IndexPath) throws FileNotFoundException, IOException {
        Map<Integer, String> enc = new HashMap<>();
        Map<Integer, Map<String, List<Integer>>> segs = new HashMap<>();
        Map<String, List<Integer>> fa = new HashMap<>();
        Map<String, List<String>> ind = new HashMap<>();
        List<Integer> fi = new ArrayList<>();
        List<String> fis = new ArrayList<>();
        List<Integer> fil = new ArrayList<>();
        List<Integer> fs = new ArrayList<>();
        File folder = new File(IndexPath);
        File fList[] = folder.listFiles();
        BufferedReader buf;
        String line;
        String[] splitLine, content;
        int k = 0, fen = rng.nextInt();
        
        // read the index files
        for (File f : fList) {
            buf = new BufferedReader(new FileReader(f));
            if (f.toString().endsWith(".encoding")) {
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    enc.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                    line = buf.readLine();
                }            
            }
            else if(f.toString().endsWith(".segment")) {
                k++;
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    content = splitLine[1].split(",");
                    for(String c : content) {
                        fi.add(Integer.parseInt(c));
                    }
                    fa.put(splitLine[0], fi);
                    line = buf.readLine();
                    fi = new ArrayList<>();
                }      
                segs.put(k, fa);
            }
            buf.close();
        }
        
        // merge the segments into one map
        for(int s : segs.keySet()){
            for(String n : segs.get(s).keySet()){
                fis = new ArrayList<>();
                for(int i : segs.get(s).get(n)) {
                    if(!fis.contains(enc.get(i))) {
                        fis.add(enc.get(i));
                    }
                }

                if(!ind.containsKey(n)) {
                    ind.put(n, fis);
                }
                else {
                    for(String h : fis){
                        if(!ind.get(n).contains(h)){
                            ind.get(n).add(h);
                        }
                    }
                }
            }
        }
        
        // add the index to the overall index and encoding to encoding list
        for(String l : ind.keySet()){
            fil = new ArrayList<>();
            for(String en : ind.get(l)){
                if(!fileEncoding.containsValue(en)) { 
                    fen = rng.nextInt();
                    while(fileEncoding.containsKey(fen))
                        fen = rng.nextInt();
                    fileEncoding.put(fen, en);
                }
                else {
                    fen = GetKeyFromValue(fileEncoding, en);
                }
                fil.add(fen);
            }
            index.put(l, fil);
        }
        
        DeleteFiles(IndexPath, ".segment");
        DeleteFiles(IndexPath, ".encoding");
    }
    
    public static void ReadEncryptedSegments(String IndexPath) throws IOException {
        File folder = new File(IndexPath);
        File fList[] = folder.listFiles();
        BufferedReader buf;
        String line;
        String[] splitLine;
        String[] content;
        List<Integer> fi = new ArrayList<>();
        
        for (File f : fList) {
            buf = new BufferedReader(new FileReader(f));
            if (f.toString().endsWith(".eencoding")) {
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    fileEncoding.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                    line = buf.readLine();
                }            
            }
            else if(f.toString().endsWith(".esegment")) {
                line = buf.readLine();
                while(line != null){ 
                    fi = new ArrayList<>();
                    splitLine = line.split(":");
                    content = splitLine[1].split(",");
                    for(String c : content) {
                        if(!fi.contains(Integer.parseInt(c)))
                            fi.add(Integer.parseInt(c));
                    }
                    if(!encryptedIndex.containsKey(splitLine[0]))
                        encryptedIndex.put(splitLine[0], fi);
                    else {
                        for(Integer file : fi){
                            if(!encryptedIndex.get(splitLine[0]).contains(file)){
                                encryptedIndex.get(splitLine[0]).add(file);
                            }
                        }
                    }
                        
                    line = buf.readLine();
                }         
            }
            buf.close();
        }
    }
        
    public static void ReadSegments(String IndexPath) throws IOException {
        File folder = new File(IndexPath);
        File fList[] = folder.listFiles();
        BufferedReader buf;
        String line;
        String[] splitLine;
        String[] content;
        List<Integer> fi = new ArrayList<>();
        
        for (File f : fList) {
            buf = new BufferedReader(new FileReader(f));
            if (f.toString().endsWith(".encoding")) {
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    fileEncoding.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                    line = buf.readLine();
                }            
            }
            else if(f.toString().endsWith(".segment")) {
                line = buf.readLine();
                while(line != null){ 
                    splitLine = line.split(":");
                    content = splitLine[1].split(",");
                    for(String c : content) {
                        fi.add(Integer.parseInt(c));
                    }
                    index.put(splitLine[0], fi);
                    line = buf.readLine();
                }         
            }
            buf.close();
        }
    }
        
    // Saves the index into a a file on disk (along with the file encoding)
    public static void saveOnDisk(Path indexPath, boolean create) {
        String indexSegment;
        String filesEncoding;
        int seg = 0;
        if(create == true) {
            DeleteFiles(indexPath.toString(), ".segment");
            DeleteFiles(indexPath.toString(), ".encoding");
        }
        else {
            if(indexPath.toFile().listFiles().length < 10){
                while(new File(indexPath.toString() + "\\index_" + seg + ".segment").exists()){
                    seg++;
                } 
            }
            else {
                // Merge segments
                seg = 0;
                DeleteFiles(indexPath.toString(), ".segment");
                DeleteFiles(indexPath.toString(), ".encoding");
            }
        }
        indexSegment = indexPath.toString() + "\\index_" + seg + ".segment";
        filesEncoding = indexPath.toString() + "\\encoding_" + seg + ".encoding"; 
        try (PrintWriter writer = new PrintWriter(indexSegment, "UTF-8")) {
            int i;
            for(String k : index.keySet()) {
                writer.print(k + ":");
                i = 0;
                for(int v : index.get(k)) {
                    writer.print( i == 0 ? v : "," + v);
                    if(i == 0)
                        i++;
                }
                writer.println();
            }
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
        try(PrintWriter writer = new PrintWriter(filesEncoding, "UTF-8")){
            for(int encoding : fileEncoding.keySet()){
                writer.println(encoding + ":" + fileEncoding.get(encoding));
            }
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }
    
        // Saves the index into a a file on disk (along with the file encoding)
    public static void saveEncryptedOnDisk(Path indexPath, boolean create) {
        String indexSegment;
        String filesEncoding;
        int seg = 0;
        if(create == true) {
            DeleteFiles(indexPath.toString(), ".esegment");
            DeleteFiles(indexPath.toString(), ".eencoding");
        }
        else {
            if(indexPath.toFile().listFiles().length < 10){
                while(new File(indexPath.toString() + "\\index_" + seg + ".esegment").exists()){
                    seg++;
                } 
            }
            else {
                // Merge segments
                seg = 0;
                DeleteFiles(indexPath.toString(), ".esegment");
                DeleteFiles(indexPath.toString(), ".eencoding");
            }
        }
        indexSegment = indexPath.toString() + "\\index_" + seg + ".esegment";
        filesEncoding = indexPath.toString() + "\\encoding_" + seg + ".eencoding"; 
        try (PrintWriter writer = new PrintWriter(indexSegment, "UTF-8")) {
            int i;
            for(String k : encryptedIndex.keySet()) {
                writer.print(k + ":");
                i = 0;
                for(int v : encryptedIndex.get(k)) {
                    writer.print( i == 0 ? v : "," + v);
                    if(i == 0)
                        i++;
                }
                writer.println();
            }
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
        try(PrintWriter writer = new PrintWriter(filesEncoding, "UTF-8")){
            for(int encoding : fileEncoding.keySet()){
                writer.println(encoding + ":" + fileEncoding.get(encoding));
            }
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // TODO code application logic here
        //cipher = new AES(128);
        //cipher.saveKey("key.dat");
        cipher = new AES("key.dat");
        String query, docsPath, indexPath, s;
        boolean create;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> res = new ArrayList<>();
        List<String> andQueries = new ArrayList<>(), 
                     queries = new ArrayList<>(), 
                     notQueries = new ArrayList<>(),
                     orQueries = new ArrayList<>();
        List<List<Integer>> notTemp = new ArrayList<>(), 
                            notRes = new ArrayList<>(),
                            andRes = new ArrayList<>(), 
                            orRes = new ArrayList<>() ;
        List<Integer> orFinal = new ArrayList<>(), 
                      notFinal = new ArrayList<>(),
                      andFinal = new ArrayList<>();
        
        ////////// DIRECTORY FOR DOCUMENT I/O //////////
        
        System.out.print("Would you like to update an exisiting index (y/n): ");
        s = br.readLine();
        create = !s.equalsIgnoreCase("y");

        
        System.out.print("Enter directory for index: ");
        indexPath = br.readLine();
        System.out.print("Enter directory for documents: ");
        docsPath = br.readLine();

        if (docsPath == null || !(new File(docsPath).exists())) {
            System.err.println("Directory doesn't exist");
            System.exit(1);
        }
        
        final Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
            System.exit(1);
        } 
        
        Date start = new Date();
        
        // Read index segments if it exists
        
        if(create == false && (new File(indexPath)).listFiles().length >= 6) {
            MergeEncryptedSegments(indexPath);
            //MergeSegments(indexPath);
        }
                
        ////////// CREATE INDEX //////////

        try {
            indexDocs(docDir);
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
        } catch(IOException e) {
            System.out.println(" caught a " + e.getClass() +
                               "\n with message: " + e.getMessage());
        }
        
        ////////// SAVE INDEX SEGMENT //////////
        
        //saveOnDisk(Paths.get(indexPath), create);
        saveEncryptedOnDisk(Paths.get(indexPath), create);
        
        ////////// SEARCH QUERY IN INDEX //////////
        
        if((new File(indexPath)).listFiles().length < 5) {
            ReadEncryptedSegments(indexPath);
            //ReadSegments(indexPath);
        }
        
        
        System.out.print("Enter a query to be searched (q to quit, start the word with | for or , and ! for not): ");
        query = br.readLine();
        queries = SplitQuery(query);
        
        
        ////////// SPLIT QUERIES BASED ON OPRATION //////////
        
        for(String q : queries) {
                if(q.startsWith("!")) {
                    notQueries.add(q.replaceAll("\\!", ""));
                }
                else if(q.startsWith("|")) {
                    orQueries.add(q.replaceAll("\\|", ""));
                }
                else {
                    andQueries.add(q);
                }
        }
        
        
        ////////// REPEAT QUERIES UNTIL USER QUITS //////////
        while(!query.equals("q")) {
            
            ////////// SEARCH IN MEMORY //////////
            
            // Find results for each query and store them temporarily
            for(String nQ : notQueries) {
//                if(index.containsKey(nQ))
//                {
//                    notTemp.add(index.get(nQ));
//                }
                String enQ = new String(Base64.encodeBase64(cipher.hashBytes(cipher.encryptString(nQ))));
                if(encryptedIndex.containsKey(enQ)) {
                    notTemp.add(encryptedIndex.get(enQ));
                }
            }
            // Find difference between all files and results of not queries
            List<Integer> files = new ArrayList<>();
            files.addAll(fileEncoding.keySet());
            for(List<Integer> qT : notTemp) {
                notRes.add(Difference(files, qT));
            }
            
            for(String aQ : andQueries) {
                String eaQ = new String(Base64.encodeBase64(cipher.hashBytes(cipher.encryptString(aQ))));
                if(encryptedIndex.containsKey(eaQ)) {
                    andRes.add(encryptedIndex.get(eaQ));
                }
//                if(index.containsKey(aQ))
//                    andRes.add(index.get(aQ));
            }
            
            for(String oQ : orQueries) {
                String eoQ = new String(Base64.encodeBase64(cipher.hashBytes(cipher.encryptString(oQ))));
                if(encryptedIndex.containsKey(eoQ)) {
                    orRes.add(encryptedIndex.get(eoQ));
                }
//                if(index.containsKey(oQ))
//                    orRes.add(index.get(oQ));
            }
            
            // combine and operator results
            int ind = 0;    
            for(List<Integer> aQ : andRes) {
                if(ind == 0)
                {
                    andFinal = union(andFinal, aQ);
                    ind++;
                }
                andFinal = intersection(andFinal, aQ);
            }
                             
            // combine or operator results
            
            ind = 0;
            for(List<Integer> oQ : orRes) {
                orFinal = union(orFinal, oQ);
            }
                        
            // combine not operator results
            ind = 0;
            for(List<Integer> nQ : notRes) {
                if(ind == 0)
                {
                    notFinal = union(notFinal, nQ);
                    ind++;
                }
                notFinal = intersection(notFinal, nQ);
            }
            
            // Find intersection between and results & not results
            if(andFinal.isEmpty()) {
                if(!notFinal.isEmpty())
                    res.addAll(notFinal);
//                    for(Integer i : andFinal){
//                        if(!res.contains(i)){
//                            res.add(i);
//                        }
//                    }
            }
            else if(notFinal.isEmpty()) {
                res.addAll(andFinal);
//                for(Integer i : notFinal){
//                    if(!res.contains(i)){
//                        res.add(i);
//                    }
//                }
            }
            else {
                res.addAll(intersection(andFinal, notFinal));
//                List<Integer> intersect = intersection(andFinal, notFinal);
//                for(Integer i : intersect){
//                    if(!res.contains(i)){
//                        res.add(i);
//                    }
//                }
            }
            // Find union between the results and or result
            if(!orFinal.isEmpty() && orFinal != null) {
                res = union(res, orFinal);
//                List<Integer> uni = union(res, orFinal);
//                for(Integer i : uni){
//                    if(!res.contains(i)){
//                        res.add(i);
//                    }
//                }
            }
            
            // Display Results
            
            DisplayResults(res);

            
            // CLEAR QUERIES AND RESULTS
            queries.clear();
            orQueries.clear();
            andQueries.clear();
            notQueries.clear();
            notFinal.clear();
            andFinal.clear();
            orFinal.clear();
            notTemp.clear();
            andRes.clear();
            notRes.clear();
            orRes.clear();
            res.clear();
            
            // CHECK IF THERE IS MORE QUERIES
            System.out.print("Enter a query to be searched (q to quit, start the word with | for or , and ! for not): ");
            query = br.readLine();
            queries = SplitQuery(query);
            for(String q : queries) {
                if(q.startsWith("!")) {
                    notQueries.add(q.replaceAll("\\!", ""));
                }
                else if(q.startsWith("|")) {
                    orQueries.add(q.replaceAll("\\|", ""));
                }
                else {
                    andQueries.add(q);
                }
            }
        }
        br.close();
    }
}
