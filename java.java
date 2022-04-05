import java.io.*;
import java.util.*;

public class java {
    public static void print(String s) {
        System.out.println(s);
    }
    public static void main(String[] args) throws IOException{
        if (args.length != 1) {
            print("Please enter filename as 1 commandline argument\n");
            System.exit(0);
        }
        File f = new File(args[0]);
        ArrayList<Integer> code = new ArrayList<>();
        String valid = "+-<>.,[]";
        if (f.exists()) {
            Scanner srcf = new Scanner(f);
            while (srcf.hasNextLine()) {
                String line = srcf.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (valid.indexOf(line.charAt(i))!=-1) {
                        code.add((int)line.charAt(i));
                    }
                }
            }
            srcf.close();
        }
        int check = 0;
        HashMap<Integer, Integer> pl = new HashMap<>();
        HashMap<Integer, Integer> pr = new HashMap<>();
        ArrayDeque<Integer> pair = new ArrayDeque<>();
        for (int i = 0; i < code.size(); i++) {
            if (code.get(i)==(int)'['){
                check++;
                pair.push(i);
            }
            if (code.get(i)==(int)']'){
                check--;
                if (check < 0) {
                    print("# of \"[\" and \"]\" does not match\n");
                    System.exit(0);
                }
                int p2 = (int) pair.pop();
                pl.put(p2, i);
                pr.put(i, p2);
            }
        }
        if (check != 0) {
            print("# of \"[\" and \"]\" does not match\n");
            System.exit(0);
        }

        final int TAPE_SIZE = 30000;
        final int MAX_INT = 256;
        int[] tape = new int[TAPE_SIZE];
        ArrayDeque<Character> input_container = new ArrayDeque<>();
        int current = 0;
        int index = 0;
        Scanner scan = new Scanner(System.in);

        while (index < code.size()) {
            if (code.get(index)==(int)'+') {
                tape[current] = (tape[current]+MAX_INT+1)%MAX_INT;
            }
            else if (code.get(index)==(int)'-') {
                tape[current] = (tape[current]+MAX_INT-1)%MAX_INT;
            }
            else if (code.get(index)==(int)'<') {
                current = (current+TAPE_SIZE-1)%TAPE_SIZE;
            }
            else if (code.get(index)==(int)'>') {
                current = (current+TAPE_SIZE+1)%TAPE_SIZE;
            }
            else if (code.get(index)==(int)',') {
                if (input_container.isEmpty()){
                    String user_input = "";
                    while (user_input.strip().length()==0) {
                        user_input = scan.nextLine();
                    }
                    for (int i = 0; i < user_input.length(); i++) {
                        input_container.addLast(user_input.charAt(i));
                    }
                }
                tape[current] = (int)input_container.removeFirst();
            }
            else if (code.get(index)==(int)'.') {
                System.out.print((char)tape[current]);
            }
            else if (code.get(index)==(int)'[') {
                if (tape[current]==0) {
                    index = pl.get(index);
                }
            }
            else if (code.get(index)==(int)']') {
                index = pr.get(index) - 1;
            }
            index++;
        }
    }
}