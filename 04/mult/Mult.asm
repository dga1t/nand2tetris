// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.

// Start R2 at 0.
@R2
M=0

@R0
D=M
@x
M=D   // x = R0

@R1
D=M
@y
M=D   // y = R1

@i
M=1   // i = 1

@sum
M=0   // sum = 0

(LOOP)
  @i
  D=M
  @y
  D=D-M 
  @STOP
  D;JGT // if i > y goto STOP

  @sum
  D=M
  @x
  D=D+M
  @sum
  M=D   // sum = sum + x
  @i
  M=M+1
  @LOOP
  0;JMP

(STOP)
  @sum
  D=M
  @R2
  M=D   // RAM[2] = sum

(END)
  @END
  0;JMP