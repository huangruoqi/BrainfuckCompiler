#include <iostream>
#include <fstream>
#include <string>
#include <unordered_map>
#include <deque>
using namespace std;

int main(int argc, char** argv) {
    if (argc != 2) {
        cout << "Please enter filename as 1 commandline argument\n";
        return 0;
    }
    ifstream srcf(argv[1]);
    string code;
    string line;
    string valid = "+-<>.,[]";
    if (srcf.is_open()){
        while (getline(srcf, line)) {
            for (int i = 0; i < line.length(); i++) {
                if (valid.find(line[i])!=string::npos)
                    code.push_back(line[i]);
            }
        }
    }
    srcf.close();

    int check = 0;
    unordered_map<int, int> pl;
    unordered_map<int, int> pr;
    deque<int> pair;
    for (int i = 0;i < code.length(); i++) {
        if (code[i]=='['){
            check++;
            pair.push_back(i);
        }
        else if (code[i]==']'){
            check--;
            if (check < 0) {
                cout << "# of \"[\" and \"]\" does not match\n";
                return 0;
            }
            int p2 = (int) pair.back();
            pair.pop_back();
            pl[p2] = i;
            pr[i] = p2;
        }
        
    }
    if (check != 0) {
        cout << "# of \"[\" and \"]\" does not match\n";
        return 0;
    }
    const int TAPE_SIZE = 30000;
    const int MAX_INT = 256;
    int tape[TAPE_SIZE]{0};
    int current = 0;
    int index = 0;
    while (index < int(code.length())) {
        if (code[index]=='+') tape[current] = (tape[current]+MAX_INT+1)%MAX_INT;
        else if (code[index]=='-') tape[current] = (tape[current]+MAX_INT-1)%MAX_INT;
        else if (code[index]=='<') current = (current+TAPE_SIZE-1)%TAPE_SIZE;
        else if (code[index]=='>') current = (current+TAPE_SIZE+1)%TAPE_SIZE;
        else if (code[index]==',') {
            char a; cin >> a;
            tape[current] = a;
        }
        else if (code[index]=='.') {
            cout << (char)tape[current];
        }
        else if (code[index]=='[') {
            if (tape[current]==0) {
                index = pl[index];
            }
        }
        else if (code[index]==']') {
            index = pr[index]-1;
        }
        index++;
    }
    return 0;
}