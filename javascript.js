function compile(srcf) {
    srcf = '>++++++++[<+++++++++>-]<.>++++[<+++++++>-]<+.+++++++..+++.>>++++++[<+++++++>-]<++.------------.>++++++[<+++++++++>-]<+.<.+++.------.--------.>>>++++[<++++++++>-]<+.'
    valid = '-+<>[],.'
    code = []
    for (let i = 0; i < srcf.length; i++) {
        for (let j = 0; j < srcf[i].length;j++) {
            if (valid.indexOf(srcf[i][j])!=-1) {
                code.push(srcf[i][j])
            }
        }
    }
    code = srcf.split('')

    check = 0
    pl = {}
    pr = {}
    pair = []
    for (let i = 0; i < code.length; i++) {
        if (code[i]==='[') {
            check++
            pair.push(i)
        }
        else if (code[i]===']') {
            check--
            if (check < 0) {
                console.log('# of "[" and "]" does not match')
                return
            }
            p2 = pair.pop()
            pl[p2] = i
            pr[i] = p2
        }
    }
    if (check != 0){
        console.log('# of "[" and "]" does not match')
        return
    }

    TAPE_SIZE = 30000
    MAX_INT = 256
    tape = new Array(TAPE_SIZE).fill(0)
    input_container = []
    output_container = []
    current = 0
    index = 0
    while (index < code.length) {
        if (code[index]==='+') {
            tape[current] = (tape[current]+MAX_INT+1)%MAX_INT
        }
        else if (code[index]==='-') {
            tape[current] = (tape[current]+MAX_INT-1)%MAX_INT
        }
        else if (code[index]==='<') {
            current = (current + TAPE_SIZE - 1) % TAPE_SIZE
        }
        else if (code[index]==='>') {
            current = (current + TAPE_SIZE + 1) % TAPE_SIZE
        }
        else if (code[index]==='[') {
            if (tape[current]==0)
                index = pl[index]
        }
        else if (code[index]===']') {
            index = pr[index]-1
        }
        else if (code[index]===',') {
        }
        else if (code[index]==='.') {
            output_container.push(String.fromCharCode(tape[current]))
        }
        index++
    }
    return output_container.join('')
}

console.log(compile())