import sys
import array
from collections import deque

def main():
    if len(sys.argv)!=2:
        print("Please enter filename as 1 commandline argument")
        exit(0)
    
    srcf = open(sys.argv[1], 'r')
    
    valid = '-+<>[],.'
    code = []
    for i in srcf: 
        for j in i: 
            if j in valid: 
                code.append(j)
    
    check = 0
    pl = {}
    pr = {}
    pair = deque()
    for i in range(len(code)):
        if code[i]=='[':
            check+=1
            pair.append(i)
        elif code[i]==']':
            check-=1
            if check < 0:
                print('# of "[" and "]" does not match')
                exit(0)
            p2 = pair.pop()
            pl[p2] = i
            pr[i] = p2
    
    if check != 0:
        print('# of "[" and "]" does not match')
        exit(0)
    
    n = len(code)
    TAPE_SIZE = 30000
    MAX_INT = 256
    tape = array.array('i', (0 for _ in range(TAPE_SIZE)))
    input_container = deque()
    current = 0
    index = 0
    while index < n:
        if code[index]=='+': tape[current] = (tape[current]+1)%MAX_INT
        elif code[index]=='-': tape[current] = (tape[current]-1)%MAX_INT
        elif code[index]=='<': current = (current-1)%TAPE_SIZE
        elif code[index]=='>': current = (current+1)%TAPE_SIZE
        elif code[index]==',':
            if len(input_container)==0: 
                user_input = ''
                while (len(user_input.strip())==0):
                    user_input = input()
                for i in user_input:
                    input_container.append(i)
            tape[current] = ord(input_container.popleft())
        elif code[index]=='.': print(chr(tape[current]), end='')
        elif code[index]=='[': 
            if tape[current] == 0:
                index = pl[index]
        elif code[index]==']':
            index = pr[index]-1
        index+=1

    
    
        
if __name__ == '__main__':
    main()
